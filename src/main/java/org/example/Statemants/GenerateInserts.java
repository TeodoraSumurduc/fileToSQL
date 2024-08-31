package org.example.Statemants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateInserts implements Generate{
    private final List<String> listInsert;
    private final Map<Object, Object> listColumns;

    /**
     * Constructs a GenerateInserts object with the specified column details.
     *
     * @param listColumns A map representing the column details.
     */
    public GenerateInserts(Map<Object, Object> listColumns) {
        this.listInsert = new ArrayList<>();
        this.listColumns = listColumns;
    }
    /**
     * Generates SQL INSERT statements based on the provided data and column details.
     *
     * @param list      A map representing the data to be included in the INSERT statements.
     * @param tableName The name of the table for which the INSERT statements are generated.
     * @param data      A list of lists containing the data to be included in the INSERT statements.
     * @return A list of strings representing the generated SQL INSERT statements.
     */
    public List<String> generateStatements(Map<Object,Object> list, String tableName, List<List<String>> data){
        ///am creat insert into tableName(columns)
        Map<Integer,String> indexs = new HashMap<Integer,String>();
        String insert = "insert into " + tableName+"(";
        int nr = 0;
        for(Object key:listColumns.keySet()){
            insert += (String) key + ", ";
            indexs.put(nr, (String) listColumns.get(key));
            nr++;
        }
        insert = insert.substring(0,insert.length() - 2);
        insert += ") values (";

        for(List<String> row: data){
            String rowInsert = insert;
            for(int i=0;i<row.size();i++){
                if(indexs.get(i).equals("varchar")){
                    rowInsert += StringInSQL(row.get(i)) + ", ";
                }
                else rowInsert += row.get(i) + ", ";
            }
            rowInsert = rowInsert.substring(0,rowInsert.length()-2);
            rowInsert += ");";
            listInsert.add(rowInsert);
        }
        return  listInsert;
    }
    /**
     * Converts a string to its SQL representation.
     *
     * @param text The text to be converted.
     * @return The SQL representation of the input text.
     */
    public String StringInSQL(String text){
        List<String> textSplit = List.of(text.split(""));
        List<String> errors = List.of(":", "&", ".", ",", "'","^","$","%","*");
        text = "'";
        for(int i=0;i<textSplit.size();i++){
            if(errors.contains(textSplit.get(i))){
                 text += "'" + textSplit.get(i) + "'";
            }
            else text += textSplit.get(i);
        }
        text+="'";
        return text;

    }
}
