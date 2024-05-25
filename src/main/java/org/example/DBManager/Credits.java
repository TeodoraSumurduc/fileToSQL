package org.example.DBManager;

/**
 * Clasa Credits oferă informații de conectare la baza de date și o metodă pentru a obține o conexiune.
 * Această clasă conține metode statice pentru a furniza URL-ul JDBC, utilizatorul și parola necesare pentru conectarea la baza de date.
 */
public class Credits {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/proiectpao";
    private static final String USER = "root";
    private static final String PASSWORD = "Teo.2510";

    /**
     * Constructor privat pentru a preveni crearea unei instanțe a clasei.
     */
    private Credits() {}

    /**
     * Returnează o instanță a clasei ConnectionDB care conține informațiile necesare pentru conectarea la baza de date.
     *
     * @return O instanță a clasei ConnectionDB care conține informațiile de conectare.
     */
    public static ConnectionDB getConnectionDB(){
        return new ConnectionDB("jdbc:mysql://localhost:3306/proiectpao",
                "root",
                "Teo.2510");
    }

    /**
     * Returnează URL-ul JDBC pentru conexiunea la baza de date.
     *
     * @return URL-ul JDBC pentru conexiunea la baza de date.
     */
    public static String getJdbcUrl() {
        return JDBC_URL;
    }

    /**
     * Returnează numele utilizatorului pentru conexiunea la baza de date.
     *
     * @return Numele utilizatorului pentru conexiunea la baza de date.
     */
    public static String getUser() {
        return USER;
    }

    /**
     * Returnează parola pentru conexiunea la baza de date.
     *
     * @return Parola pentru conexiunea la baza de date.
     */
    public static String getJPassword() {
        return PASSWORD;
    }
}
