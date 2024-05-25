package org.example;

/**
 * Abstract class representing a table mapper.
 * It implements the TableClassesMapper interface and provides functionality to work with table names.
 */
public abstract class TableMapper implements TableClassesMapper {
    private String tableName;

    /**
     * Retrieves the name of the table associated with this mapper.
     *
     * @return The name of the table
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the name of the table associated with this mapper.
     *
     * @param tableName_ The name of the table to set
     */
    public void setTableName(String tableName_) {
        this.tableName = tableName_;
    }
}

