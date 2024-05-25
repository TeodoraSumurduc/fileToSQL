package org.example.Commands;

import org.example.Exceptions.Checked.FileReadExceptions;
import org.example.Exceptions.Checked.FileWriteException;
import org.example.Statemants.GenerateInserts;
import org.example.Statemants.InsertAllColumns;
import org.example.Statemants.UpdateColumns;

import java.sql.SQLException;
import java.util.*;

public class LoadCommand extends Command {
    private String filePath;
    private String tableName;
    private String statementType;

    public LoadCommand(String[] args) {
        super(args);
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments for load command.");
        }
        this.filePath = "C:\\Users\\teodo\\Desktop\\Anul II Sem II\\PAO\\fileToSQL\\src\\"+args[0];
        this.tableName = args[1];
        this.statementType = args[2];
    }

    @Override
    public boolean execute() throws FileReadExceptions, SQLException, FileWriteException {
        if(Objects.equals(statementType, "INSERT"))
        {
            InsertAllColumns insertAllColumns = new InsertAllColumns(filePath,tableName);
            List<List<String>> fileData = insertAllColumns.readCSVorJSON();
            Map<Object,Object> columns = insertAllColumns.getColumnDetailsFromDatabase();
            String nameFile = insertAllColumns.writeStatement();
            System.out.println("The name of the result file is "+nameFile);
            return true;
        }
        else if(Objects.equals(statementType, "UPDATE"))
        {
            UpdateColumns updateColumns = new UpdateColumns(filePath,tableName);
            List<List<String>> fileData = updateColumns.readCSVorJSON();
            Map<Object,Object> columns = updateColumns.getColumnDetailsFromDatabase();
            System.out.print("Enter the number of columns you want to update: ");
            Scanner scanner = new Scanner(System.in);
            int numColumns = scanner.nextInt();
            scanner.nextLine();
            List<String> columnNames = new ArrayList<>();
            for (int i = 0; i < numColumns; i++) {
                System.out.print("Enter the column name " + (i + 1) + ": ");
                String columnName = scanner.nextLine();
                columnNames.add(columnName);
            }
            String nameFile = updateColumns.writeStatement(columnNames);
            System.out.println("The name of the result file is "+ nameFile);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getAllowedTypeUser() {
        List<String> users = new ArrayList<>();
        users.add("REGULAR");
        users.add("ADMIN");
        return users;
    }
}
