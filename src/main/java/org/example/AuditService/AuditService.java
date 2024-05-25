package org.example.AuditService;

import org.example.TableMapper;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AuditService extends TableMapper {
    private static int lastId = 0;
    private int id;
    private String command;
    private Timestamp timestamp;
    private String username;
    public AuditService(String command, Timestamp timestamp, String username) {
        this.id = ++lastId;
        this.command = command;
        this.timestamp = timestamp;
        this.username = username;
        setTableName("audit_service");
    }

    @Override
    public HashMap<Object, Object> getDataUser() throws SQLException {
        HashMap<Object, Object> column = new HashMap<>();
        column.put("id",id);
        column.put("nameUser", username);
        column.put("command",command);
        column.put("timestamps",timestamp);
        return column;
    }

    @Override
    public Map<Object, Object> getColumns() {
        Map<Object,Object> columnsTable = new HashMap<>();
        columnsTable.put("id","varchar");
        columnsTable.put("nameUser","varchar");
        columnsTable.put("command","varchar");
        columnsTable.put("timestamps","varchar");
        return columnsTable;
    }
}
