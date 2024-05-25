package org.example;

public abstract class TableMapper implements TableClassesMapper{
    private String tableName;

    public String getTableName(){
        return tableName;
    }
    public void setTableName(String tableName_){
        this.tableName = tableName_;
    }
}
