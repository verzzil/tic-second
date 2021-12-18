package ru.itis.arithmetic;

import javafx.util.Pair;
import ru.itis.arithmetic.algorithm.ArithmeticCoding;
import ru.itis.arithmetic.model.Triple;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private final static ArithmeticCoding arithmeticCoding = new ArithmeticCoding();
    private final static ArithmeticCoding.Prepare arithmeticCodingPrepare = new ArithmeticCoding.Prepare();
    private final static ArithmeticCoding.Decode arithmeticCodingDecode = new ArithmeticCoding.Decode();

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

//        System.out.println("========== Режим работы ===========");
//        System.out.println("1: Кодирование \t ----- \t  2: Декодирование");
//        int mode = scan.nextInt();
//
//        if (mode == 1) {
//            System.out.println("Введите путь до файла с данными: ");
//            String path = scan.next();
//
//            System.out.println("Введите символ, которого нет в текте: ");
//            String borderlineSymbol = scan.next();
//
//            String source = arithmeticCodingPrepare.readFile(path);
//            HashMap<Character, Double> probabilities = arithmeticCodingPrepare.getProbabilities(source);
//
//            Triple encode = arithmeticCoding.algorithm(source, probabilities);
//
//            String resultData = probabilities.toString() + " ----- " + borderlineSymbol + " ----- " +
//                    encode.getOptimalNum();
//            arithmeticCoding.writeToFile("./coderResult.txt", resultData);
//        } else if (mode == 2) {
//            Pair<Pair<HashMap<Character, Double>, String>, String> data = arithmeticCodingDecode.readFile("./coderResult.txt");
//
//            HashMap<Character, Double> probabilities = data.getKey().getKey();
//            String borderlineSymbol = data.getKey().getValue();
//            String encoded = data.getValue();
//
//            String decoded = arithmeticCodingDecode.decode(encoded, probabilities, borderlineSymbol);
//
//            arithmeticCodingDecode.writeToFile("./decoderResult.txt", decoded);
//        }

//        String doub = "2.9E-4";
//
//        if (doub.matches(".*E.*")) {
//            String[] splittedData = doub.split("E");
//            StringBuilder result = new StringBuilder();
//
//            int additionalForExp;
//            try {
//                additionalForExp = splittedData[0].split("\\.")[1].length();
//            } catch (ArrayIndexOutOfBoundsException e) {
//                additionalForExp = 0;
//            }
//            Integer exponen = Integer.parseInt(splittedData[1].substring(1)) + additionalForExp;
//
//            splittedData[0] = splittedData[0].replaceAll("\\.", "");
//
//            int tempJ = 0;
//            for (int i = exponen; i >= 0; i--) {
//                if (tempJ < splittedData[0].length()) {
//                    if (i - 1 < 0) {
//                        result.append(".");
//                    }
//                    result.append(splittedData[0].charAt(tempJ));
//                    tempJ++;
//                } else {
//                    if (i - 1 < 0) {
//                        result.insert(0, ".");
//                    }
//                    result.insert(0, "0");
//                }
//            }
//
//            System.out.println(result.toString());
//        }


        String source = arithmeticCodingPrepare.readFile("D:\\Another\\Univercity\\Тесты\\tic-second\\src\\test.txt");
        HashMap<Character, Double> probabilities = arithmeticCodingPrepare.getProbabilities(source);

        System.out.println(probabilities);
        Triple encode = arithmeticCoding.algorithm(source, probabilities);

        System.out.println(encode);
        String decoded = arithmeticCodingDecode.decode(encode.getOptimalNum(), probabilities, "_");

        System.out.println(decoded);
    }
}
