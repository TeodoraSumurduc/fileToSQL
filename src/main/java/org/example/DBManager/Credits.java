package org.example.DBManager;

public class Credits {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/proiectpao";
    private static final String USER = "root";
    private static final String PASSWORD = "Teo.2510";
    private Credits() {
    }
    public static ConnectionDB getConnectionDB(){
        return new ConnectionDB("jdbc:mysql://localhost:3306/proiectpao",
                "root",
                "Teo.2510");
    }

    public static String getJdbcUrl() {
        return JDBC_URL;
    }
    public static String getUser() {
        return USER;
    }
    public static String getJPassword() {
        return PASSWORD;
    }
}
