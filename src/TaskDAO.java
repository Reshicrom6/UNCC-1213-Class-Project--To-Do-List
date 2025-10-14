import TaskClasses.*;
import DateClasses.Date;
import TimeClasses.Time;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of TaskDAOInterface for persisting tasks and users.
 */
public class TaskDAO implements TaskDAOInterface {

	// region Task CRUD
	@Override
	public Task save(Task task) {
		if (task == null) return null;
		try (Connection conn = DatabaseConnector.getConnection()) {
			if (task.getTaskId() > 0) {
				if (update(task)) {
					return task;
				} else {
					return null;
				}
			}
			String sql = "INSERT INTO tasks(name, description, date_text, time_text, deadline_date_text, deadline_time_text, category_type, custom_category, is_complete) VALUES(?,?,?,?,?,?,?,?,?)";
			try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				bindTask(ps, task);
				ps.executeUpdate();
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						int id = rs.getInt(1);
						task.setTaskId(id);
					}
				}
			}

			// Save users mapping (ensure users exist first)
			saveTaskUsers(conn, task);
			return task;
		} catch (SQLException e) {
			System.err.println("Task save failed: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Task findById(int taskId) {
		String sql = "SELECT * FROM tasks WHERE id = ?";
		try (Connection conn = DatabaseConnector.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, taskId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Task t = mapTask(rs);
					t.setUsers(loadUsersForTask(conn, t.getTaskId()));
					return t;
				}
			}
		} catch (SQLException e) {
			System.err.println("findById failed: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Task> findAll() {
		String sql = "SELECT * FROM tasks ORDER BY id";
		List<Task> result = new ArrayList<>();
		try (Connection conn = DatabaseConnector.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Task t = mapTask(rs);
				t.setUsers(loadUsersForTask(conn, t.getTaskId()));
				result.add(t);
			}
		} catch (SQLException e) {
			System.err.println("findAll failed: " + e.getMessage());
		}
		return result;
	}

	@Override
	public List<Task> findByName(String name) {
		String sql = "SELECT * FROM tasks WHERE LOWER(name) LIKE ? ORDER BY id";
		List<Task> result = new ArrayList<>();
		try (Connection conn = DatabaseConnector.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, "%" + name.toLowerCase() + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Task t = mapTask(rs);
					t.setUsers(loadUsersForTask(conn, t.getTaskId()));
					result.add(t);
				}
			}
		} catch (SQLException e) {
			System.err.println("findByName failed: " + e.getMessage());
		}
		return result;
	}

	@Override
	public List<Task> findByCategory(Category category) {
		String sql = "SELECT * FROM tasks WHERE category_type = ? AND (custom_category = ? OR (? IS NULL AND custom_category IS NULL)) ORDER BY id";
		List<Task> result = new ArrayList<>();
		try (Connection conn = DatabaseConnector.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, category.getCategoryType().name());
			String custom = category.getCategoryType() == TaskCategory.OTHER ? category.getCustomCategory() : null;
			ps.setString(2, custom);
			ps.setString(3, custom);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Task t = mapTask(rs);
					t.setUsers(loadUsersForTask(conn, t.getTaskId()));
					result.add(t);
				}
			}
		} catch (SQLException e) {
			System.err.println("findByCategory failed: " + e.getMessage());
		}
		return result;
	}

	@Override
	public boolean update(Task task) {
		if (task == null || task.getTaskId() <= 0) return false;
		String sql = "UPDATE tasks SET name=?, description=?, date_text=?, time_text=?, deadline_date_text=?, deadline_time_text=?, category_type=?, custom_category=?, is_complete=? WHERE id=?";
		try (Connection conn = DatabaseConnector.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			bindTask(ps, task);
			ps.setInt(10, task.getTaskId());
			int rows = ps.executeUpdate();

			// refresh task_users mapping: delete and recreate
			try (PreparedStatement del = conn.prepareStatement("DELETE FROM task_users WHERE task_id=?")) {
				del.setInt(1, task.getTaskId());
				del.executeUpdate();
			}
			saveTaskUsers(conn, task);
			return rows > 0;
		} catch (SQLException e) {
			System.err.println("update failed: " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean delete(int taskId) {
		String sql = "DELETE FROM tasks WHERE id=?";
		try (Connection conn = DatabaseConnector.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, taskId);
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			System.err.println("delete failed: " + e.getMessage());
			return false;
		}
	}
	// endregion

	// region User ops
	@Override
	public User saveUser(User user) {
		if (user == null) return null;
		// Upsert-ish: try insert, if unique conflict then fetch existing id.
		try (Connection conn = DatabaseConnector.getConnection()) {
			Integer existingId = findUserIdByName(conn, user.getName());
			if (existingId != null) {
				user.setUserId(existingId);
				return user;
			}
			String sql = "INSERT INTO users(name) VALUES(?)";
			try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, user.getName());
				ps.executeUpdate();
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						user.setUserId(rs.getInt(1));
					}
				}
			}
			return user;
		} catch (SQLException e) {
			System.err.println("saveUser failed: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<User> findAllUsers() {
		List<User> result = new ArrayList<>();
		String sql = "SELECT id, name FROM users ORDER BY name";
		try (Connection conn = DatabaseConnector.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				User u = new User(rs.getString("name"));
				u.setUserId(rs.getInt("id"));
				result.add(u);
			}
		} catch (SQLException e) {
			System.err.println("findAllUsers failed: " + e.getMessage());
		}
		return result;
	}

	@Override
	public User findUserById(int userId) {
		String sql = "SELECT id, name FROM users WHERE id=?";
		try (Connection conn = DatabaseConnector.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					User u = new User(rs.getString("name"));
					u.setUserId(rs.getInt("id"));
					return u;
				}
			}
		} catch (SQLException e) {
			System.err.println("findUserById failed: " + e.getMessage());
		}
		return null;
	}
	// endregion

	// region Utility
	@Override
	public void clearAll() {
		try (Connection conn = DatabaseConnector.getConnection(); Statement st = conn.createStatement()) {
			st.executeUpdate("DELETE FROM task_users");
			st.executeUpdate("DELETE FROM tasks");
			st.executeUpdate("DELETE FROM users");
		} catch (SQLException e) {
			System.err.println("clearAll failed: " + e.getMessage());
		}
	}

	@Override
	public int getTaskCount() {
		String sql = "SELECT COUNT(*) FROM tasks";
		try (Connection conn = DatabaseConnector.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println("getTaskCount failed: " + e.getMessage());
		}
		return 0;
	}
	// endregion

	// region Helpers
	private void bindTask(PreparedStatement ps, Task task) throws SQLException {
		// Index mapping 1..9 as in INSERT/UPDATE statements above
		ps.setString(1, task.getName());
		ps.setString(2, task.getDescription());
		ps.setString(3, task.getDate() == null ? null : task.getDate().toString());
		ps.setString(4, task.getTime() == null ? null : task.getTime().toString());
		if (task.getDeadline() != null) {
			ps.setString(5, task.getDeadline().getDate().toString());
			ps.setString(6, task.getDeadline().getTime().toString());
		} else {
			ps.setString(5, null);
			ps.setString(6, null);
		}
		ps.setString(7, task.getCategory() == null ? TaskCategory.OTHER.name() : task.getCategory().getCategoryType().name());
		ps.setString(8, task.getCategory() != null && task.getCategory().getCategoryType() == TaskCategory.OTHER ? task.getCategory().getCustomCategory() : null);
		ps.setInt(9, task.completed() ? 1 : 0);
	}

	private Task mapTask(ResultSet rs) throws SQLException {
		String name = rs.getString("name");
		String description = rs.getString("description");
		String dateText = rs.getString("date_text");
		String timeText = rs.getString("time_text");
		String dDate = rs.getString("deadline_date_text");
		String dTime = rs.getString("deadline_time_text");
		String catType = rs.getString("category_type");
		String customCat = rs.getString("custom_category");
		boolean isComplete = rs.getInt("is_complete") == 1;

		Category category;
		try {
			TaskCategory tc = TaskCategory.valueOf(catType);
			if (tc == TaskCategory.OTHER && customCat != null && !customCat.isEmpty()) {
				category = new Category(TaskCategory.OTHER, customCat);
			} else {
				category = new Category(tc);
			}
		} catch (Exception e) {
			category = new Category(TaskCategory.OTHER, customCat);
		}

		Date date = dateText != null ? Date.parse(dateText) : null;
		Time time = timeText != null ? Time.parse(timeText) : null;
		Task t;
		if (date != null && time != null) {
			t = new Task(name, category, time, date, isComplete);
		} else if (date != null) {
			// Time default
			t = new Task(name, category, new Time(), date, isComplete);
		} else {
			// Fallback default date/time
			t = new Task(name, category, new Time(), new Date(), isComplete);
		}
		t.setDescription(description != null ? description : "");
		if (dDate != null && dTime != null) {
			t.setDeadline(new DeadLine(Date.parse(dDate), Time.parse(dTime)));
		}
		t.setTaskId(rs.getInt("id"));
		return t;
	}

	private void saveTaskUsers(Connection conn, Task task) throws SQLException {
		if (task.getUsers() == null || task.getUsers().isEmpty()) return;
		for (User u : task.getUsers()) {
			int userId = ensureUser(conn, u);
			try (PreparedStatement ps = conn.prepareStatement("INSERT OR IGNORE INTO task_users(task_id, user_id) VALUES(?,?)")) {
				ps.setInt(1, task.getTaskId());
				ps.setInt(2, userId);
				ps.executeUpdate();
			}
		}
	}

	private List<User> loadUsersForTask(Connection conn, int taskId) throws SQLException {
		List<User> users = new ArrayList<>();
		String sql = "SELECT u.id, u.name FROM users u JOIN task_users tu ON u.id = tu.user_id WHERE tu.task_id = ? ORDER BY u.name";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, taskId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					User u = new User(rs.getString("name"));
					u.setUserId(rs.getInt("id"));
					users.add(u);
				}
			}
		}
		return users;
	}

	private Integer findUserIdByName(Connection conn, String name) throws SQLException {
		String sql = "SELECT id FROM users WHERE name = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, name);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return null;
	}

	private int ensureUser(Connection conn, User user) throws SQLException {
		if (user.getUserId() > 0) return user.getUserId();
		Integer id = findUserIdByName(conn, user.getName());
		if (id != null) return id;
		try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, user.getName());
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		throw new SQLException("Failed to insert user: " + user.getName());
	}
	// endregion
}
