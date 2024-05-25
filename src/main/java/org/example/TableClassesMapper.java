package org.example;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface TableClassesMapper {
    HashMap<Object, Object> getDataUser() throws SQLException;

    Map<Object, Object> getColumns();
}
