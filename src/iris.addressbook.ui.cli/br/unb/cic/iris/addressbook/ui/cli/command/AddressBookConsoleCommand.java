package br.unb.cic.iris.addressbook.ui.cli.command;

import java.security.InvalidParameterException;
import java.util.List;

import br.unb.cic.iris.addressbook.AddressBookManager;
import br.unb.cic.iris.addressbook.model.AddressBookEntry;
import br.unb.cic.iris.cli.command.ConsoleAbstractMailCommand;
import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.exception.EmailUncheckedException;
import br.unb.cic.iris.util.EmailValidator;

/***
 * added by dAddressBookConsole
 */
public class AddressBookConsoleCommand extends ConsoleAbstractMailCommand {
	public static final String COMMAND_NAME = "adb";

	@Override
	public String explain() {
		StringBuilder sb = new StringBuilder();
		sb.append("(adb add <name> <email>) - add an address book entry (name=email)");
		sb.append("\n(adb list) - show the address book entries");
		sb.append("\n(adb delete <name> - delete an address book entry\n");
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	protected void handleExecute() throws EmailException {
		switch (parameters.get(0)) {
		case "list":
			listAll();
			break;
		case "add":
			add();
			break;
		case "delete":
			delete();
			break;
		default:
			throw new EmailException(parameters.get(0) + " is an invalid command");
		}
	}

	private void listAll() throws EmailException {		
		List<AddressBookEntry> entries = AddressBookManager.instance().findAll();
		System.out.println("-----------------------------------------------------");
		System.out.println("Address Book:");
		System.out.println("-----------------------------------------------------");
		for (AddressBookEntry e : entries) {
			System.out.println(" " + e.getNick() + " -> " + e.getAddress());
		}
		System.out.println("-----------------------------------------------------");
	}

	private void delete() throws EmailException {
		if (parameters.size() == 2) {
			String name = parameters.get(1);
			AddressBookManager.instance().delete(name);
		} else {
			throw new InvalidParameterException();
		}
	}

	private void add() throws EmailException {
		if (parameters.size() == 3) {
			String name = parameters.get(1);
			String email = parameters.get(2);
			if (!EmailValidator.validate(email)) {
				throw new EmailUncheckedException("Invalid email: "+email);
			}
			AddressBookManager.instance().save(name, email);
		} else {
			throw new InvalidParameterException();
		}
	}
}