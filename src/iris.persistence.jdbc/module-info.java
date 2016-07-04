module iris.persistence.jdbc {
	requires public java.sql;	
	requires public sqlite.jdbc;
	
	requires iris.model.simple;
	requires public iris.core;
	requires public iris.persistence;	
		
	exports br.unb.cic.iris.persistence.jdbc;	
	
	uses br.unb.cic.iris.model.EntityFactory;
		
	provides br.unb.cic.iris.persistence.DAOFactory with br.unb.cic.iris.persistence.jdbc.DaoFactoryJDBC;
}
