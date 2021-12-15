package ru.itis.arithmetic.algorithm;

import javafx.util.Pair;
import ru.itis.arithmetic.model.FrequencySection;
import ru.itis.arithmetic.model.Triple;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class ArithmeticCoding {

    public Triple algorithm(String text, HashMap<Character, Double> probabilities) {
        FrequencySection frequencySection = new FrequencySection();
        for (Character character : probabilities.keySet()) {
            frequencySection.addSection(character, probabilities.get(character));
        }

        for (int i = 0; i < text.length(); i++) {
            frequencySection.setNewDiapason(frequencySection.getFreqSection().get(text.charAt(i)));

            frequencySection.recalculateRange();
        }
        frequencySection.addRemaining();

        return new Triple(frequencySection.getResultStartDiapason(), frequencySection.getResultEndDiapason(), frequencySection.getOptimalNum());
    }

    public void writeToFile(String path, String data) {
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(data);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
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
                result.put(
                        character,
                        (double) freq.get(character) / text.length()
                );
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

        public String decode(String encoded, HashMap<Character, Double> probabilities) {
            StringBuilder result = new StringBuilder();

            FrequencySection frequencySection = new FrequencySection();
            for (Character character : probabilities.keySet()) {
                frequencySection.addSection(character, probabilities.get(character));
            }

            label: while (true) {
                for (Character symbol : frequencySection.getFreqSection().keySet()) {

                    if (frequencySection.compareDiapason(encoded, symbol)) {

                        if (symbol == '_') {
                            break label;
                        }

                        frequencySection.setNewDiapason(frequencySection.getFreqSection().get(symbol));

                        frequencySection.recalculateRange();
                        result.append(symbol);
                    }
                }

            }

            return result.toString();
        }

        public void writeToFile(String path, String data) {
            try(FileWriter writer = new FileWriter(path, false)) {
                writer.write(data);
                writer.flush();
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }
        }

        public Pair<HashMap<Character, Double>, String> readFile(String path) {
            StringBuilder result = new StringBuilder();
            try (FileReader reader = new FileReader(path)) {
                int c;
                while ((c = reader.read()) != -1) {
                    result.append((char) c);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            return getDataFromRawData(result.toString());
        }

        private Pair<HashMap<Character, Double>, String> getDataFromRawData(String rawData) {
            HashMap<Character, Double> resultProbabilities = new HashMap<>();
            String encoded;

            String[] splitProbAndEncoded = rawData.split("-----");
            String[] rawProbabilities = splitProbAndEncoded[0].trim()
                    .replaceAll("[}{](?!=)", "")
                    .split("(?<! ) ");

            for (int i = 0; i < rawProbabilities.length; i++) {
                String[] temp = rawProbabilities[i].split("=(?=\\d)");
                resultProbabilities.put(
                        temp[0].replaceAll("\r\n", "\n").charAt(0),
                        Double.parseDouble(temp[1].replaceAll(",", ""))
                );
            }

            encoded = splitProbAndEncoded[1].trim();

            return new Pair<>(resultProbabilities, encoded);
        }
    }
}
