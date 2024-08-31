package org.example;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTableClassMapper implements TableClassesMapper{
    private String tableName;

    public String getTableName(){
        return tableName;
    }
    public void setTableName(String tableName_){
        this.tableName = tableName_;
    }
}
