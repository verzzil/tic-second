package ru.itis.arithmetic.algorithm;

import javafx.util.Pair;
import ru.itis.arithmetic.model.FrequencySection;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ArithmeticCoding {

    public Pair<Double, Double> algorithm(String text, HashMap<Character, Double> probabilities) {
        FrequencySection initFrequencySection = new FrequencySection();
        for (Character character : probabilities.keySet()) {
            initFrequencySection.addSection(character, probabilities.get(character));
        }

        for (int i = 0; i < text.length(); i++) {
            for (FrequencySection.Section section : initFrequencySection.getFreqSection()) {
                if (section.compareTo(text.charAt(i)) == 0) {
                    initFrequencySection.addSubSection(section);
                }
            }
        }

        return new Pair<>(initFrequencySection.getStartDiapason(), initFrequencySection.getEndDiapason());
    }

    public static class Prepare {

        public ArrayList<String> readFile(String path) {
            ArrayList<String> chanks = new ArrayList<>();
            try (FileReader reader = new FileReader(path)) {
                int c;
                int counter = 0;
                StringBuilder tempString = new StringBuilder();
                while ((c = reader.read()) != -1) {
                    tempString.append((char) c);
                    if (counter > 8) {
                        tempString.append("_");
                        chanks.add(tempString.toString());
                        tempString.setLength(0);
                        counter = 0;
                    } else {
                        counter++;
                    }
                }
                if (counter <= 9) {
                    tempString.append("_");
                    chanks.add(tempString.toString());
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            return chanks;
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

            int i = 0;
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
