package org.example.Statemants;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface Generate {
    public List<String> generateStatements(Map<Object,Object> list, String tableName, List<List<String>> data);
    public String StringInSQL(String text);
}
