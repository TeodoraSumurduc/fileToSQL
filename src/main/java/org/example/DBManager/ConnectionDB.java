package org.example.DBManager;
/**
 * Clasa ConnectionDB reprezintă o înregistrare care conține informațiile necesare pentru conectarea la o bază de date.
 * Această clasă este utilizată pentru a transmite informațiile de conexiune către alte componente ale aplicației.
 */
public record ConnectionDB(String path, String user, String password){}
