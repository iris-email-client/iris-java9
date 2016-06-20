module iris.addressbook.persistence.jdbc {
	requires java.sql;	
	requires sqlite.jdbc;
	
	requires iris.addressbook.api;
	requires iris.addressbook.model.simple;	
		
	//exports br.unb.cic.iris.persistence.jdbc;	
	
	//uses br.unb.cic.iris.model.EntityFactory;
		
	//provides br.unb.cic.iris.persistence.DAOFactory with br.unb.cic.iris.persistence.jdbc.DaoFactoryJDBC;
}
