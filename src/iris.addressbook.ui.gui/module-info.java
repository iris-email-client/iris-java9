module iris.addressbook.ui.gui {
	requires iris.core;
	requires iris.addressbook.api;	
	requires iris.ui.gui;	
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.addressbook.ui.gui.command.AddressBookGuiCommand;
	
}