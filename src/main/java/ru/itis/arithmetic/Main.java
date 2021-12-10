package ru.itis.arithmetic;

import javafx.util.Pair;
import ru.itis.arithmetic.algorithm.ArithmeticCoding;
import ru.itis.arithmetic.model.FrequencySection;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    private final static ArithmeticCoding arithmeticCoding = new ArithmeticCoding();
    private final static ArithmeticCoding.Prepare arithmeticCodingPrepare = new ArithmeticCoding.Prepare();
    private final static ArithmeticCoding.Decode arithmeticCodingDecode = new ArithmeticCoding.Decode();

    public static void main(String[] args) {

        StringBuilder result = new StringBuilder();
        ArrayList<String> sourceArray = arithmeticCodingPrepare.readFile("D:\\Another\\Univercity\\Тесты\\tic-second\\src\\test.txt");
        for (String source : sourceArray) {
            HashMap<Character, Double> probabilities = arithmeticCodingPrepare.getProbabilities(source);

            Pair<Double, Double> encode = arithmeticCoding.algorithm(source, probabilities);

            result.append(arithmeticCodingDecode.decode(encode.getKey(), probabilities));
        }

        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '_') {
                result.deleteCharAt(i);
            }
        }
        System.out.println(result);
    }
}
