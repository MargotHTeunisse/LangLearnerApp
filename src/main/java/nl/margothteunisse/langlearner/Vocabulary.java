package nl.margothteunisse.langlearner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

class Vocabulary {
    private final String[][] words;

    Vocabulary(String filename) throws IOException {
        List<String> lines = readFile(filename);
        words = new String[lines.size()][2];
        int wordIndex = 0;
        for (String line: lines) {
            String[] wordPair = line.split("\\s*,\\s*");
            words[wordIndex] = wordPair;
            wordIndex++;
        }
    }

    private static List<String> readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(filename);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        return reader.readAllLines();
    }

    int wordCount() {
        return words.length;
    }

    Card getCard(int wordIndex) {
        return new Card(words[wordIndex][0], words[wordIndex][1]);
    }
}
