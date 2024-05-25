package org.example.Statemants;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Exceptions.Checked.FileReadExceptions;
import org.example.Exceptions.Checked.FileWriteException;

import java.io.*;
import java.sql.*;
import java.util.*;

public class UpdateColumns implements Statemants{
    private String pathName;
    private String tableName;

    public UpdateColumns(String pathName, String tableName) {
        this.pathName = pathName;
        this.tableName = tableName;
    }

    public List<List<String>> readCSVorJSON() throws FileReadExceptions {
        List<List<String>> data = new ArrayList<>();
        if (this.pathName.endsWith(".csv")) {
            // Citirea din fișierul CSV
            String line = "";
            String splitBy = ";";
            try {
                BufferedReader br = new BufferedReader(new FileReader(pathName));
                while ((line = br.readLine()) != null) {
                    List<String> employee = List.of(line.split(splitBy));
                    data.add(employee);
                }
            } catch (IOException e) {
                throw new FileReadExceptions("Eroare la citirea fișierului CSV", e);
            }
        } else if (this.pathName.endsWith(".json")) {
            // Citirea din fișierul JSON
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(new File(pathName));
                for (JsonNode node : rootNode) {
                    List<String> row = new ArrayList<>();
                    Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> field = fields.next();
                        row.add(field.getValue().asText());
                    }
                    data.add(row);
                }
            } catch (IOException e) {
                throw new FileReadExceptions("Eroare la citirea fișierului JSON", e);
            }
        } else {
            System.err.println("Formatul fișierului nu este suportat.");
        }
        return data;
    }
    public Map<Object, Object> getColumnDetailsFromDatabase() throws SQLException {
        // Conectare la baza de date și interogare pentru a obține detalii despre coloanele tabelului
        // Returnează un map în care cheile sunt numele coloanelor, iar valorile sunt tipurile lor
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiectpao",
                "root",
                "Teo.2510"
        );
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM "+tableName);
        Map<Object,Object> list = new HashMap<Object,Object>();
        while(resultSet.next()){
            String numeColoana = resultSet.getString("numeColoana");
            String tipColaona = resultSet.getString("tipColoana");
            list.put(numeColoana,tipColaona);
        }
        return list;
    }
    private Map<Object,Object> generateColumnsToUpdate(List<String> columnsToUpdate) throws SQLException {
        Map<Object, Object> columns =  this.getColumnDetailsFromDatabase();
        Map<Object,Object> mapColumns = new HashMap<Object,Object>();
        for(Object column : columns.keySet()){
            if(columnsToUpdate.contains(column)){
                mapColumns.put(column,columns.get(column));
            }
        }
        return mapColumns;
    }
    /**
     * Writes SQL update statements to a file based on the specified columns to update and the data
     *
     * @param columnsToUpdate The list of column names to be updated
     * @return The name of the file where the SQL update statements are written
     * @throws SQLException         If a database access error occurs
     * @throws FileWriteException   If an error occurs while writing to the file
     * @throws FileReadExceptions  If an error occurs while reading from the file
     */
    public String writeStatement(List<String> columnsToUpdate) throws SQLException, FileWriteException, FileReadExceptions {
        String nameFile = "results_" + tableName + ".txt";
        Map<Object, Object> columns =  this.getColumnDetailsFromDatabase();
        List<List<String>> data = this.readCSVorJSON();
        Map<Object,Object> colToUp = this.generateColumnsToUpdate(columnsToUpdate);
        GenerateUpdates generateUpdates = new GenerateUpdates(colToUp);
        List<String> fileContent = generateUpdates.generateStatements(columns,tableName,data);

        try {
            File file = new File(nameFile);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(String s : fileContent) {
                bufferedWriter.write(s + "\n");
            }

            bufferedWriter.close();
            System.out.println("Fișierul a fost creat și scris cu succes!");
        } catch (IOException e) {
            throw new FileWriteException("A apărut o eroare în timpul creării sau scrierii în fișier", e);
        }
        return nameFile;
    }

}
