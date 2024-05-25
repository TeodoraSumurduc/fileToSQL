package org.example.Statemants;

import org.example.Exceptions.Checked.FileReadExceptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Statemants {
    public List<List<String>> readCSVorJSON() throws FileReadExceptions;
    public Map<Object, Object> getColumnDetailsFromDatabase() throws SQLException;
}
