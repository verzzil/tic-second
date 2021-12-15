package ru.itis.arithmetic;

import javafx.util.Pair;
import ru.itis.arithmetic.algorithm.ArithmeticCoding;
import ru.itis.arithmetic.model.Triple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private final static ArithmeticCoding arithmeticCoding = new ArithmeticCoding();
    private final static ArithmeticCoding.Prepare arithmeticCodingPrepare = new ArithmeticCoding.Prepare();
    private final static ArithmeticCoding.Decode arithmeticCodingDecode = new ArithmeticCoding.Decode();

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("========== Режим работы ===========");
        System.out.println("1: Кодирование \t ----- \t  2: Декодирование");
        int mode = scan.nextInt();

        if (mode == 1) {
            System.out.println("Введите путь до файла с данными: ");
            String path = scan.next();

            String source = arithmeticCodingPrepare.readFile(path);
            HashMap<Character, Double> probabilities = arithmeticCodingPrepare.getProbabilities(source);

            Triple encode = arithmeticCoding.algorithm(source, probabilities);

            String resultData = probabilities.toString() + "\n-----\n" +
                    encode.getOptimalNum();
            arithmeticCoding.writeToFile("./coderResult.txt", resultData);

            System.out.println(probabilities);
        } else if (mode == 2) {
            Pair<HashMap<Character, Double>, String> data = arithmeticCodingDecode.readFile("./coderResult.txt");

            String decoded = arithmeticCodingDecode.decode(data.getValue(), data.getKey());

            arithmeticCodingDecode.writeToFile("./decoderResult.txt", decoded);

            System.out.println("Декодировано");
        }
    }
}
