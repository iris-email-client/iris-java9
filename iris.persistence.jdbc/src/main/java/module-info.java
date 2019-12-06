module iris.persistence.jdbc {
	requires transitive java.sql;	
	requires transitive sqlite.jdbc;
	
	requires iris.model.simple;
	requires iris.persistence;	
	requires iris.core;	
		
	exports br.unb.cic.iris.persistence.jdbc;	
	
	uses br.unb.cic.iris.model.EntityFactory;
		
	provides br.unb.cic.iris.persistence.DAOFactory with br.unb.cic.iris.persistence.jdbc.DaoFactoryJDBC;
}
