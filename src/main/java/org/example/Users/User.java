package org.example.Users;

import org.example.TableMapper;
import org.example.DBManager.Credits;
import org.example.DBManager.DBManager;
import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Utils.UserTypes;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class User extends TableMapper {
    private int id;
    private String username,password;
    private UserTypes type;
    /**
     * Constructs a user with the given username and password.
     *
     * @param username The username of the user
     * @param password The password of the user
     * @throws DatabaseConnectionException If a database connection error occurs
     */
    public User(String username, String password) throws DatabaseConnectionException {
        DBManager dbManager = new DBManager<>(Credits.getConnectionDB());
        this.id = dbManager.getMaxId()+1;
        this.username = username;
        this.password = password;
        setTableName("users");
    }
    /**
     * Constructs an empty user.
     */
    public User() {
        setTableName("users");
    }

    /**
     * Constructs a user with the given username, password, and type.
     *
     * @param username The username of the user
     * @param password The password of the user
     * @param type     The type of the user
     * @throws DatabaseConnectionException If a database connection error occurs
     */
    public User(String username, String password, String type) throws DatabaseConnectionException {
        DBManager dbManager = new DBManager<>(Credits.getConnectionDB());
        this.id = dbManager.getMaxId()+1;
        this.username = username;
        this.password = password;
        this.type = UserTypes.valueOf(type);
        setTableName("users");

    }
    /**
     * Retrieves the type of the user.
     *
     * @return The type of the user
     */
    public CharSequence getType() {
        if (type != null) {
            switch (type) {
                case ANONYMOUS -> {
                    return "ANONYMOUS";
                }
                case REGULAR -> {
                    return "REGULAR";
                }
                case ADMIN -> {
                    return "ADMIN";
                }
            }
        }
        return null;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the type of the user.
     *
     * @param type The type of the user
     */
    public void setType(UserTypes type) {
        this.type = type;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }
    /**
     * Retrieves the columns for the user table.
     *
     * @return The columns for the user table
     */
    public Map<Object,Object> getColumns(){
        Map<Object,Object> columns = new HashMap<Object,Object>();
        columns.put("id","int");
        columns.put("username","varchar");
        columns.put("password","varchar");
        columns.put("type","varchar");
        return columns;
    }
    /**
     * Retrieves the data for the user.
     *
     * @return The data for the user
     * @throws SQLException If a SQL exception occurs
     */
    @Override
    public HashMap<Object, Object> getDataUser() throws SQLException{
        HashMap<Object, Object> db = new HashMap<Object, Object>();
        db.put("id", id);
        db.put("username",username);
        db.put("password",password);
        db.put("type", type);
        return db;
    }
}
