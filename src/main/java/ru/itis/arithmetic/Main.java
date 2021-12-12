package ru.itis.arithmetic;

import javafx.util.Pair;
import ru.itis.arithmetic.algorithm.ArithmeticCoding;
import ru.itis.arithmetic.model.Triple;

import java.util.HashMap;

public class Main {

    private final static ArithmeticCoding arithmeticCoding = new ArithmeticCoding();
    private final static ArithmeticCoding.Prepare arithmeticCodingPrepare = new ArithmeticCoding.Prepare();
    private final static ArithmeticCoding.Decode arithmeticCodingDecode = new ArithmeticCoding.Decode();

    public static void main(String[] args) {

        String source = arithmeticCodingPrepare.readFile("D:\\Another\\Univercity\\Тесты\\tic-second\\src\\test.txt");
        HashMap<Character, Double> probabilities = arithmeticCodingPrepare.getProbabilities(source);
        Triple encode = arithmeticCoding.algorithm(source, probabilities);

        System.out.println(encode);

        String decoded = arithmeticCodingDecode.decode(encode.getOptimalNum(), probabilities);

        System.out.println(decoded);

        System.out.println('0' < '9');
//        result.append(arithmeticCodingDecode.decode(encode.getKey(), probabilities));
//
//        for (int i = 0; i < result.length(); i++) {
//            if (result.charAt(i) == '_') {
//                result.deleteCharAt(i);
//            }
//        }
//        System.out.println(result);
    }
}
