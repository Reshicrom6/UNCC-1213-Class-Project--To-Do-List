/**
 * Simple JDBC connection manager and schema initializer.
 *
 * Defaults to using a local SQLite database file (todo.db) so you can start
 * persisting data without installing a database server. You can override the
 * JDBC URL via config/db.properties.
 *
 * Expected properties (optional):
 *  - jdbc.url = jdbc:sqlite:todo.db (default)
 *  - jdbc.user = (unused for SQLite)
 *  - jdbc.password = (unused for SQLite)
 *
 * Requires the SQLite JDBC driver on the classpath (e.g., lib/sqlite-jdbc-<ver>.jar).
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnector {

	private static final String DEFAULT_URL = "jdbc:sqlite:todo.db";
	private static String jdbcUrl = DEFAULT_URL;
	private static String jdbcUser = null;
	private static String jdbcPassword = null;

	static {
		// Try to load driver explicitly (optional for JDBC 4+)
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Throwable ignored) {
			// Driver auto-loading may still work; continue.
		}

		// Load optional properties from config/db.properties or db.properties
		loadProperties();

		// Ensure DB file directory exists for SQLite URL of the form jdbc:sqlite:path/to/file.db
		ensureSqliteDirectory();

		// Initialize schema on first load
		try (Connection conn = getConnection()) {
			if (conn != null) {
				enableForeignKeys(conn);
				initializeSchema(conn);
			}
		} catch (SQLException e) {
			System.err.println("Failed to initialize database schema: " + e.getMessage());
		}
	}

	private static void loadProperties() {
		Properties props = new Properties();
		String[] candidates = new String[] {
				"config/db.properties",
				"db.properties",
				// In case someone placed it under src; not ideal, but try.
				"src/db.properties"
		};
		for (String path : candidates) {
			try (InputStream in = new FileInputStream(path)) {
				props.load(in);
				break; // loaded successfully
			} catch (Exception ignored) {
			}
		}

		String url = props.getProperty("jdbc.url");
		if (url != null && !url.trim().isEmpty()) {
			jdbcUrl = url.trim();
		}
		String user = props.getProperty("jdbc.user");
		if (user != null && !user.trim().isEmpty()) {
			jdbcUser = user.trim();
		}
		String pass = props.getProperty("jdbc.password");
		if (pass != null) {
			jdbcPassword = pass;
		}
	}

	private static void ensureSqliteDirectory() {
		// If using SQLite with a file path (not :memory:), ensure parent dir exists.
		if (jdbcUrl != null && jdbcUrl.startsWith("jdbc:sqlite:")) {
			String path = jdbcUrl.substring("jdbc:sqlite:".length());
			if (path != null && !path.isEmpty() && !":memory:".equalsIgnoreCase(path)) {
				File f = new File(path);
				File parent = f.getAbsoluteFile().getParentFile();
				if (parent != null && !parent.exists()) {
					//noinspection ResultOfMethodCallIgnored
					parent.mkdirs();
				}
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		if (jdbcUser != null || jdbcPassword != null) {
			return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
		} else {
			return DriverManager.getConnection(jdbcUrl);
		}
	}

	private static void enableForeignKeys(Connection conn) {
		// Needed for SQLite to enforce foreign keys
		try (Statement st = conn.createStatement()) {
			st.execute("PRAGMA foreign_keys = ON");
		} catch (SQLException ignored) {}
	}

	private static void initializeSchema(Connection conn) throws SQLException {
	String createTasks = """
		CREATE TABLE IF NOT EXISTS tasks (
		    id INTEGER PRIMARY KEY AUTOINCREMENT,
		    name TEXT NOT NULL,
		    description TEXT,
		    date_text TEXT,
		    time_text TEXT,
		    deadline_date_text TEXT,
		    deadline_time_text TEXT,
		    category_type TEXT NOT NULL,
		    custom_category TEXT,
		    is_complete INTEGER NOT NULL DEFAULT 0
		);
		""";

		String createUsers = """
				CREATE TABLE IF NOT EXISTS users (
					id INTEGER PRIMARY KEY AUTOINCREMENT,
					name TEXT NOT NULL UNIQUE
				);
				""";

		String createTaskUsers = """
				CREATE TABLE IF NOT EXISTS task_users (
					task_id INTEGER NOT NULL,
					user_id INTEGER NOT NULL,
					PRIMARY KEY (task_id, user_id),
					FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
					FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
				);
				""";

		try (Statement st = conn.createStatement()) {
			st.execute(createTasks);
			st.execute(createUsers);
			st.execute(createTaskUsers);
		}
	}
}
