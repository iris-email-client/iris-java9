package br.unb.cic.iris.persistence.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import br.unb.cic.iris.persistence.IrisPersistenceException;
import br.unb.cic.iris.persistence.PersistenceConfiguration;
import br.unb.cic.iris.persistence.jdbc.internal.DefaultJdbcConfig;

public class DbUtil {
	private static String DB_URL;

	private static DbUtil instance;

	private PersistenceConfiguration config;

	private DbUtil() throws IrisPersistenceException {
		setConfig(new DefaultJdbcConfig());
	}

	public static DbUtil instance() throws IrisPersistenceException {
		if (instance == null) {
			instance = new DbUtil();
		}
		return instance;
	}

	public void setConfig(PersistenceConfiguration config) throws IrisPersistenceException {
		this.config = config;
		if (config.reset()) {
			config.doReset();
		}
		DB_URL = "jdbc:sqlite:" + config.getPath();
		execute(CREATE_TABLE_FOLDER);
		execute(CREATE_TABLE_MESSAGE);
	}

	public Connection connect() throws IrisPersistenceException {
		// System.out.println("Connecting to database: "+DB_URL);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL);
		} catch (SQLException e) {
			throw new IrisPersistenceException("Could not connect to database: " + e.getMessage(), e);
		}
		return conn;
	}

	public void execute(String sql) throws IrisPersistenceException {
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new IrisPersistenceException("Could not execute query: " + e.getMessage(), e);
		}
	}

	private static final String CREATE_TABLE_FOLDER = "CREATE TABLE IF NOT EXISTS folder(" + "  id    TEXT PRIMARY KEY,"
			+ "  name  TEXT" + ");";

	private static final String CREATE_TABLE_MESSAGE = "CREATE TABLE IF NOT EXISTS message("
			+ "id       TEXT PRIMARY KEY," + "_from    TEXT," + "_to       TEXT," + "cc       TEXT," + "bcc      TEXT,"
			+ "subject  TEXT," + "message  TEXT," + "_date     TIMESTAMP," + "folderid INTEGER,"
			+ "FOREIGN KEY(folderid) REFERENCES folder(id)" + ");";
}
