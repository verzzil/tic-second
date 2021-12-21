package ru.itis.arithmetic;

import javafx.util.Pair;
import ru.itis.arithmetic.algorithm.ArithmeticCoding;
import ru.itis.arithmetic.model.FrequencySection;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private final static ArithmeticCoding arithmeticCoding = new ArithmeticCoding();
    private final static ArithmeticCoding.Prepare arithmeticCodingPrepare = new ArithmeticCoding.Prepare();
    private final static ArithmeticCoding.Decode arithmeticCodingDecode = new ArithmeticCoding.Decode();

    public static void main(String[] args) {
        mainLogic();
    }

    private static void mainLogic() {
        Scanner scan = new Scanner(System.in);

        System.out.println("========== Режим работы ===========");
        System.out.println("1: Кодирование \t ----- \t  2: Декодирование");
        int mode = scan.nextInt();

        if (mode == 1) {
            System.out.println("Введите путь до файла с данными: ");
            String path = scan.next();

            System.out.println("Введите символ, которого нет в текте: ");
            String borderlineSymbol = scan.next();

            String source = arithmeticCodingPrepare.readFile(path, borderlineSymbol);
            HashMap<String, Double> probabilities = arithmeticCodingPrepare.getProbabilities(source);

            String encode = arithmeticCoding.algorithm(source, probabilities);

            String resultData = probabilities.toString() + " ----- " + borderlineSymbol + " ----- " +
                    encode;
            arithmeticCoding.writeToFile("./coderResult.txt", resultData);
        } else if (mode == 2) {
            Pair<Pair<HashMap<String, Double>, String>, String> data = arithmeticCodingDecode.readFile("./coderResult.txt");

            HashMap<String, Double> probabilities = data.getKey().getKey();
            String borderlineSymbol = data.getKey().getValue();
            String encoded = data.getValue();

            String decoded = arithmeticCodingDecode.decode(encoded, probabilities, borderlineSymbol);

            arithmeticCodingDecode.writeToFile("./decoderResult.txt", decoded);
        }
    }
}
