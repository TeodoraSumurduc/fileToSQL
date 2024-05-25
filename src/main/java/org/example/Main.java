package org.example;

import org.example.Statemants.InsertSpecificColumns;
import org.example.Statemants.UpdateColumns;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            new Application().startApp();
        } catch (SQLException e) {
            System.err.println("Failed to initialize the application: " + e.getMessage());
        }
    }
}