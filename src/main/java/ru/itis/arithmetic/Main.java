package ru.itis.arithmetic;

import javafx.util.Pair;
import ru.itis.arithmetic.algorithm.ArithmeticCoding;

import java.util.HashMap;

public class Main {

    private final static ArithmeticCoding arithmeticCoding = new ArithmeticCoding();
    private final static ArithmeticCoding.Prepare arithmeticCodingPrepare = new ArithmeticCoding.Prepare();
    private final static ArithmeticCoding.Decode arithmeticCodingDecode = new ArithmeticCoding.Decode();

    public static void main(String[] args) {

        StringBuilder result = new StringBuilder();

        String source = arithmeticCodingPrepare.readFile("D:\\Another\\Univercity\\Тесты\\tic-second\\src\\test.txt");
        HashMap<Character, Double> probabilities = arithmeticCodingPrepare.getProbabilities(source);

        String encode = arithmeticCoding.algorithm(source, probabilities);

        System.out.println(encode);

        String decode = arithmeticCodingDecode.decode(encode, probabilities);

        System.out.println(decode);
//        String str = String.valueOf(0.32d).substring(2);
//        System.out.println(Integer.toBinaryString(Integer.parseInt(str)));
//
//        String bin = "0100";
//        Double doub = Double.valueOf("0." + Integer.parseInt(bin, 2));
//
//        System.out.println(doub);
    }
}
