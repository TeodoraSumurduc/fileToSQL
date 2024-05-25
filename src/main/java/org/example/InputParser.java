package org.example;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class InputParser {

    /**
     * Metodă pentru a obține argumentele dintr-un șir de caractere.
     *
     * @param input Șirul de caractere care conține comanda și argumentele.
     * @return O listă de argumente extrase din șirul de intrare.
     */
    public static ArrayList<String> getArgs(String input) {
        ArrayList<String> args = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(input);

        while (tokenizer.hasMoreTokens()) {
            args.add(tokenizer.nextToken());
        }

        return args;
    }
}
