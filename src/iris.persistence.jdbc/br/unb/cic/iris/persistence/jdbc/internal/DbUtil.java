package br.unb.cic.iris.persistence.jdbc.internal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import br.unb.cic.iris.persistence.PersistenceException;

public class DbUtil {
	private static String DB_PATH = System.getProperty("user.home") + "/.iris/iris.db";
	private static String DB_URL = "jdbc:sqlite:" + DB_PATH;

	public DbUtil() throws PersistenceException{
		execute(CREATE_TABLE_FOLDER);
		execute(CREATE_TABLE_MESSAGE);
	}
	
	protected Connection connect() throws PersistenceException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL);
		} catch (SQLException e) {
			throw new PersistenceException("Could not connect to database: " + e.getMessage(), e);
		}
		return conn;
	}

	public void execute(String sql) throws PersistenceException {
		try (Connection conn = this.connect(); 
				Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new PersistenceException("Could not execute query: " + e.getMessage(), e);
		}
	}
	
	
	
	private static final String CREATE_TABLE_FOLDER = 
			"CREATE TABLE IF NOT EXISTS folder("
			+"  id    TEXT PRIMARY KEY," 
			+"  name  TEXT"
			+");";

	private static final String CREATE_TABLE_MESSAGE =
			"CREATE TABLE IF NOT EXISTS message("
			  +"id       TEXT PRIMARY KEY," 
			  +"_from    TEXT,"
			  +"_to       TEXT,"
			  +"cc       TEXT,"
			  +"bcc      TEXT,"
			  +"subject  TEXT,"
			  +"message  TEXT,"
			  +"_date     TIMESTAMP," 
			  +"folderid INTEGER,"
			  +"FOREIGN KEY(folderid) REFERENCES folder(id)"
			+");";
	
	
}
