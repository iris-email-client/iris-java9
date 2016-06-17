module iris.persistence.jdbc {
	requires java.sql;	
	requires sqlite.jdbc;
	requires iris.model.simple;
	requires public iris.persistence;
		
	exports br.unb.cic.iris.persistence.jdbc;	
	
	uses br.unb.cic.iris.model.EntityFactory;
		
	provides br.unb.cic.iris.persistence.DAOFactory with br.unb.cic.iris.persistence.jdbc.DaoFactoryJDBC;
}
