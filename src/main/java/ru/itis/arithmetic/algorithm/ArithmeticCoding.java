package ru.itis.arithmetic.algorithm;

import ru.itis.arithmetic.model.FrequencySection;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ArithmeticCoding {

    public String algorithm(String text, HashMap<Character, Double> probabilities) {
        FrequencySection frequencySection = new FrequencySection();
        for (Character character : probabilities.keySet()) {
            frequencySection.addSection(character, probabilities.get(character));
        }

        for (int i = 0; i < text.length(); i++) {
            frequencySection.setNewDiapason(frequencySection.getFreqSection().get(text.charAt(i)));

            while (
                    (frequencySection.getStartDiapason() >= 0d && frequencySection.getEndDiapason() < 0.5d) ||
                            (frequencySection.getStartDiapason() >= 0.25d && frequencySection.getEndDiapason() < 0.75d) ||
                            (frequencySection.getStartDiapason() >= 0.5d && frequencySection.getEndDiapason() < 1d)
            ) {
                frequencySection.renormalization();
            }

            frequencySection.recalculateRange();
        }
        frequencySection.setEndBits();

        return frequencySection.getResultBits().toString();
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

        public String decode(String coded, HashMap<Character, Double> probabilities) {
            StringBuilder result = new StringBuilder();

            FrequencySection frequencySection = new FrequencySection();
            for (Character character : probabilities.keySet()) {
                frequencySection.addSection(character, probabilities.get(character));
            }

            Double ivlOffset;
            if (coded.length() > 2) {
                ivlOffset = Double.valueOf("0." + Integer.parseInt(coded.substring(0, 2)));
            }
            else
                return "";

            for (int i = 0; i < coded.length(); i++) {

                while (frequencySection.getEndDiapason() - frequencySection.getStartDiapason() < 0.25d) {
                    ivlOffset *= 2;
                    frequencySection.setEndDiapason(frequencySection.getEndDiapason() * 2);

                    ivlOffset = Double.valueOf("0." + (Integer.parseInt(String.valueOf(ivlOffset).substring(2) + 1)));
                }

                if (ivlOffset >= frequencySection.getFreqSection().get('0').getEndDiapason()) {
                    result.append("1");
                    ivlOffset -= (frequencySection.getEndDiapason() - frequencySection.getStartDiapason());
                    frequencySection.setNewDiapason(frequencySection.getFreqSection().get('1'));
                } else {
                    result.append("0");
                }

                System.out.println(frequencySection);
                frequencySection.recalculateRange();
            }

            return result.toString();
        }

        private String binaryDoubling(String binaryCode) {
            StringBuilder result = new StringBuilder();

            boolean haveExtraBit = false;
            for (int i = binaryCode.length() - 1; i >= 0; i--) {
                if (binaryCode.charAt(i) == '0') {
                    if (haveExtraBit)
                        result.insert(0,"1");
                    else
                        result.insert(0, "0");
                    haveExtraBit = false;
                } else {
                    if (haveExtraBit)
                        result.insert(0, "1");
                    else
                        result.insert(0, "0");
                    haveExtraBit = true;
                }
            }

            if (haveExtraBit)
                result.insert(0, "1");

            return result.toString();
        }
    }
}
