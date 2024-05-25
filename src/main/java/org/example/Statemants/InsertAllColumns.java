package org.example.Statemants;

import java.io.*;
import java.sql.*;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Exceptions.Checked.FileReadExceptions;
import org.example.Exceptions.Checked.FileWriteException;

public class InsertAllColumns implements Statemants{
    private String pathName;
    private String tableName;

    public InsertAllColumns(String pathName, String tableName) {
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
    /**
     * Writes SQL insert statements to a file based on the data read from a CSV or JSON file.
     *
     * @return The name of the created file.
     * @throws SQLException        If an SQL error occurs while retrieving column details.
     * @throws FileWriteException  If an error occurs while writing to the file.
     * @throws FileReadExceptions If an error occurs while reading the CSV or JSON file.
     */
    public String writeStatement() throws SQLException, FileWriteException, FileReadExceptions {
        String nameFile = "results_" + tableName + ".txt";
        Map<Object, Object> columns =  this.getColumnDetailsFromDatabase();
        List<List<String>> data = this.readCSVorJSON();
        GenerateInserts generateInserts = new GenerateInserts(columns);
        List<String> fileContent = generateInserts.generateStatements(columns, tableName, data);

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
