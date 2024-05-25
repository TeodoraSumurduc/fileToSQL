package org.example.Statemants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateUpdates implements Generate{
    private List<String> listUpdate;
    private final Map<Object, Object> listColumns;

    public GenerateUpdates( Map<Object, Object> listColumns) {
        this.listColumns = listColumns;
        this.listUpdate = new ArrayList<String>();
    }
    /**
     * Generates SQL UPDATE statements based on the provided data and column details.
     *
     * @param list      A map representing the data to be included in the UPDATE statements.
     * @param tableName The name of the table for which the UPDATE statements are generated.
     * @param data      A list of lists containing the data to be included in the UPDATE statements.
     * @return A list of strings representing the generated SQL UPDATE statements.
     */
    public List<String> generateStatements(Map<Object,Object> list, String tableName, List<List<String>> data){
        Map<Integer,String> indexs = new HashMap<Integer,String>();
        String update = "update " + tableName+" set ";
        int nr = 0;
        for(Object key:list.keySet()){
            indexs.put(nr, (String) key);
            nr++;
        }
        for(List<String> row: data){
            String rowupdate = update;
            for(int i=0;i<row.size();i++){
                if(indexs.get(i).equals("varchar")){
                    rowupdate += indexs.get(i) +" = " + StringInSQL(row.get(i)) + ", ";
                }
                rowupdate += list.get(indexs.get(i))+" = " + row.get(i) + ", ";
            }
            rowupdate = rowupdate.substring(0,rowupdate.length()-2);
            rowupdate += ";";
            listUpdate.add(rowupdate);
        }
        return  listUpdate;
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
        text = "";
        for(int i=0;i<textSplit.size();i++){
            if(errors.contains(textSplit.get(i))){
                text += "'" + textSplit.get(i) + "'";
            }
            text += textSplit.get(i);
        }
        return text;

    }
}
