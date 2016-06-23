module iris.addressbook.ui.gui {
	requires java.desktop;
	requires iris.core;
	requires iris.ui.gui;
	requires iris.addressbook.api;	
	
	provides br.unb.cic.iris.command.MailCommand with br.unb.cic.iris.addressbook.ui.gui.command.AddressBookGuiCommand;
	
}