package ru.itis.arithmetic.algorithm;

import javafx.util.Pair;
import ru.itis.arithmetic.model.FrequencySection;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ArithmeticCoding {

    public Pair<Double, Double> algorithm(String text, HashMap<Character, Double> probabilities) {
        FrequencySection frequencySection = new FrequencySection();
        for (Character character : probabilities.keySet()) {
            frequencySection.addSection(character, probabilities.get(character));
        }

        for (int i = 0; i < text.length(); i++) {
            frequencySection.setNewDiapason();
        }

        return new Pair<>(frequencySection.getStartDiapason(), frequencySection.getEndDiapason());
    }

    public static class Prepare {

        public String readFile(String path) {
            StringBuilder result = new StringBuilder();
            try (FileReader reader = new FileReader(path)) {
                int c;
                while ((c = reader.read()) != -1) {
                    result.append((char) c);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            result.append("_");
            return result.toString();
        }

        public HashMap<Character, Double> getProbabilities(String text) {
            HashMap<Character, Double> result = new HashMap<>();
            HashMap<Character, Integer> freq = getFrequency(text);

            for (Character character : freq.keySet()) {
                result.put(character, (double) freq.get(character) / text.length());
            }

            return result;
        }

        private HashMap<Character, Integer> getFrequency(String text) {
            HashMap<Character, Integer> freqMap = new HashMap<>();
            for (int i = 0; i < text.length(); i++) {
                Character character = text.charAt(i);
                Integer count = freqMap.get(character);
                freqMap.put(character, count != null ? count + 1 : 1);
            }
            return freqMap;
        }
    }

    public static class Decode {

        public String decode(Double coded, HashMap<Character, Double> probabilities) {
            StringBuilder stringBuilder = new StringBuilder();

            FrequencySection initFrequencySection = new FrequencySection();
            for (Character character : probabilities.keySet()) {
                initFrequencySection.addSection(character, probabilities.get(character));
            }

            label: while (true) {
                for (FrequencySection.Section section : initFrequencySection.getFreqSection()) {
                    if (section.getStartDiapason() <= coded && section.getEndDiapason() > coded) {
                        stringBuilder.append(section.getSymbol());
                        initFrequencySection.addSubSection(section);
                        if (section.getSymbol() == '_') break label;
                    }
                }
            }

            return stringBuilder.toString();
        }
    }
}
