package org.example.DBManager;

import org.example.TableMapper;
import org.example.Statemants.GenerateInserts;
import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Exceptions.Unchecked.DatabaseQueryException;
import org.example.Users.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class DBManager<T extends TableMapper>{
    private final String path;
    private final String username;
    private final String password;

    public DBManager(ConnectionDB connectionDB) throws DatabaseConnectionException {
        this.path = connectionDB.path();
        this.username = connectionDB.user();
        this.password = connectionDB.password();
        try {
            testConnection(); // Verificăm dacă se poate conecta la baza de date
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to establish connection to the database");
        }
    }

    private void testConnection() throws SQLException {
        try (Connection connection = DriverManager.getConnection(path, username, password)) {
        }
    }

    //aceste tabele au primary key din cauza asta nu le generez cu functia generateCreateTableSQL
    public void createTables() throws DatabaseQueryException {
        try(Connection connection = DriverManager.getConnection(path,username,password);
            Statement statement = connection.createStatement()){
            String usersTable = """ 
                    CREATE TABLE IF NOT EXISTS users (
                                                id INT NOT NULL,
                                                username VARCHAR(255) NOT NULL,
                                                password VARCHAR(255) NOT NULL,
                                                type VARCHAR(255) NOT NULL,
                                                PRIMARY KEY (id),
                                                UNIQUE (username));
                    """;

            String allTablesUsers = """
                    CREATE TABLE IF NOT EXISTS all_table_users (
                                                nameTable VARCHAR(255) NOT NULL,
                                                id INT NOT NULL,
                                                nameUser VARCHAR(255) NULL,
                                                PRIMARY KEY (`id`));
                    """;
            String audit_service = """
                    CREATE TABLE IF NOT EXISTS audit_service (
                                                id INT NOT NULL,
                                                nameUser varchar(255) NOT NULL,
                                                command VARCHAR(255) NOT NULL,
                                                timestamp varchar(255) NOT NULL,
                                                PRIMARY KEY (id));
                    """;
            statement.execute(usersTable);
            statement.execute(allTablesUsers);
            statement.execute(audit_service);
        } catch (SQLException e){
            throw new DatabaseQueryException("Failed to create the tables", e);
        }
    }

    public void executeSQL(String sql){
        try(Connection connection = DriverManager.getConnection(path,username,password);
            Statement statement = connection.createStatement()){
            statement.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertIntoDB(T object) throws SQLException {
        Map<Object, Object> data = object.getDataUser();
        System.out.println(data);
        String tableName = object.getTableName();
        List<List<String>> dataObject = new ArrayList<>();
        List<String> set = new ArrayList<>();

        for (Object key : data.keySet()) {
            Object value = data.get(key);
            set.add(value != null ? value.toString() : null);
        }

        dataObject.add(set);
        GenerateInserts generateInserts = new GenerateInserts(object.getColumns());
        set = generateInserts.generateStatements(data, tableName, dataObject);
        String insert = set.get(0); // using get(0) instead of getFirst()

        executeSQL(insert);
    }


    public void saveCommand(User user, String command, Boolean flag) {
        String selectFromDB =
                """
                SELECT id FROM users WHERE username = ?;
                """;
        try (Connection connection = DriverManager.getConnection(path, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(selectFromDB)) {
            int id;
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("DBManager error\n" + e);
        }
    }

    public User getUserByUsername(String username) throws DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(path, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ?")) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");
                return new User(username, password, type);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while fetching user by username");
        }
    }

    public int getMaxId() throws DatabaseConnectionException {
        try (Connection connection = DriverManager.getConnection(path, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT MAX(id) FROM users")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int maxId = resultSet.getInt(1); // Obține valoarea primei coloane
                return maxId;
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while identifying max id.");
        }
    }
    public boolean isUserExists(String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(path, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }
    public void logCommand(String username, String command) {
        String sql = "INSERT INTO audit_service (id, nameUser, command, timestamp) VALUES (?, ?, ?, ?)";
        String sqlID = "SELECT MAX(id) FROM audit_service";
        int maxID = -1;
        try (Connection connection = DriverManager.getConnection(Credits.getJdbcUrl(), Credits.getUser(), Credits.getJPassword());
             PreparedStatement statement = connection.prepareStatement(sqlID)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxID = resultSet.getInt(1); // Obține valoarea primei coloane
            }
        } catch (SQLException e) {
            System.out.println("Error getting max id from audit: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(Credits.getJdbcUrl(), Credits.getUser(), Credits.getJPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, String.valueOf(maxID+1));
            statement.setString(2, username);
            statement.setString(3, command);
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error logging command: " + e.getMessage());
        }
    }
    }
