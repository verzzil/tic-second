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
            System.out.println(source.getBytes().length * 8);
            System.out.println(10*16);
            HashMap<Character, Double> probabilities = arithmeticCodingPrepare.getProbabilities(source);

            Pair<Double, Double> encode = arithmeticCoding.algorithm(source, probabilities);

            System.out.println(encode);

            result.append(arithmeticCodingDecode.decode(encode.getKey(), probabilities));
//            0.0350257222
        }

        System.out.println(result);
    }
}
