module iris.addressbook.ui.cli {
	requires iris.core;
	requires iris.addressbook.api;
	requires iris.ui.cli;
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.addressbook.ui.cli.command.AddressBookConsoleCommand;
	
}