package org.example.Utils;

public enum StatemantTypes {
    INSERT,
    UPDATE;
    public static StatemantTypes mapper (String statementType) throws ClassNotFoundException {
        switch (statementType) {
            case "INSERT" -> {
                return INSERT;
            }
            case "UPDATE" -> {
                return UPDATE;
            }
            default -> {
                try {
                    throw new ClassNotFoundException();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
