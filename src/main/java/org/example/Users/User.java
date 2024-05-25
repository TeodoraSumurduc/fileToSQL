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

    public User(String username, String password) throws DatabaseConnectionException {
        DBManager dbManager = new DBManager<>(Credits.getConnectionDB());
        this.id = dbManager.getMaxId()+1;
        this.username = username;
        this.password = password;
        setTableName("users");
    }

    public User() {
        setTableName("users");
    }

    public User(String username, String password, String type) throws DatabaseConnectionException {
        DBManager dbManager = new DBManager<>(Credits.getConnectionDB());
        this.id = dbManager.getMaxId()+1;
        this.username = username;
        this.password = password;
        this.type = UserTypes.valueOf(type);
        setTableName("users");

    }

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


    public String getPassword() {
        return password;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public Map<Object,Object> getColumns(){
        Map<Object,Object> columns = new HashMap<Object,Object>();
        columns.put("id","int");
        columns.put("username","varchar");
        columns.put("password","varchar");
        columns.put("type","varchar");
        return columns;
    }

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
