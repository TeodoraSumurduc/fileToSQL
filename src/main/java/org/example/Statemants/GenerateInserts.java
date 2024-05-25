package org.example.Statemants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateInserts implements Generate{
    private final List<String> listInsert;
    private final Map<Object, Object> listColumns;

    public GenerateInserts(Map<Object, Object> listColumns) {
        this.listInsert = new ArrayList<>();
        this.listColumns = listColumns;
    }

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
    public String StringInSQL(String text){
        List<String> textSplit = List.of(text.split(""));
        List<String> errors = List.of(":", "&", ".", ",", "'","^","$","%","*");
        text = "'";
        for(int i=0;i<textSplit.size();i++){
            if(errors.contains(textSplit.get(i))){
                 text += "'" + textSplit.get(i) + "'";
            }
            text += textSplit.get(i);
        }
        text+="'";
        return text;

    }
}
