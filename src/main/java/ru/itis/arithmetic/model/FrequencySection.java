package ru.itis.arithmetic.model;

import java.util.LinkedHashMap;

public class FrequencySection {

    private double startDiapason = 0d;
    private double endDiapason = 1d;
    private double tempDiapason = 0d;
    private final StringBuilder resultStartDiapason = new StringBuilder();
    private final StringBuilder resultEndDiapason = new StringBuilder();
    private final LinkedHashMap<Character, Section> freqSection = new LinkedHashMap<>();
    public int decodeOffset = 0;

    public FrequencySection() {
    }

    public double getStartDiapason() {
        return startDiapason;
    }

    public double getEndDiapason() {
        return endDiapason;
    }

    public void setEndDiapason(double endDiapason) {
        this.endDiapason = endDiapason;
    }

    public double getTempDiapason() {
        return tempDiapason;
    }

    public String getResultStartDiapason() {
        return resultStartDiapason.toString();
    }

    public String getResultEndDiapason() {
        return resultEndDiapason.toString();
    }

    public LinkedHashMap<Character, Section> getFreqSection() {
        return freqSection;
    }

    @Override
    public String toString() {
        return "FrequencySection{" +
                "startDiapason=" + startDiapason +
                ", endDiapason=" + endDiapason +
                "\n freqSection=" + freqSection +
                '}';
    }

    public void addSection(Character symbol, Double probability) {
        freqSection.put(
                symbol,
                new Section(tempDiapason, tempDiapason + probability, probability)
        );
        tempDiapason += probability;
    }

    public void setNewDiapason(Section section) {
        this.startDiapason = section.startDiapason;
        this.endDiapason = section.endDiapason;

        this.tempDiapason = this.startDiapason;
    }

    public void recalculateRange() {
        int countEqualsRank = getCountOfEqualsRank();
        if (countEqualsRank != 0)
            setNewScaledDiapason(countEqualsRank);

        Double diapasonLength = this.endDiapason - this.startDiapason;

        for (Character sectionName : freqSection.keySet()) {
            freqSection.get(sectionName).setStartDiapason(tempDiapason);
            freqSection.get(sectionName).setEndDiapason(
                    diapasonLength * freqSection.get(sectionName).probability + freqSection.get(sectionName).getStartDiapason()
            );

            this.tempDiapason += freqSection.get(sectionName).getEndDiapason() - freqSection.get(sectionName).getStartDiapason();
        }
    }

    public void addRemaining() {
        resultStartDiapason.append(String.valueOf(startDiapason).substring(2));
        resultEndDiapason.append(String.valueOf(endDiapason).substring(2));
    }

    public String getOptimalNum() {
        StringBuilder result = new StringBuilder();;

        String startResult = getResultStartDiapason();
        String endResult = getResultEndDiapason();

        for (int i = 0; i < Math.min(startResult.length(), endResult.length()); i++) {
            if (startResult.charAt(i) == endResult.charAt(i)) {
                result.append(startResult.charAt(i));
            } else {
                if (
                        Integer.parseInt(String.valueOf(endResult.charAt(i))) - Integer.parseInt(String.valueOf(startResult.charAt(i))) == 1
                ) {
                    boolean flag = false;
                    for (int j = i + 1; j < endResult.length(); j++) {
                        if (endResult.charAt(j) != '0') {
                            flag = true;
                            break;
                        }
                    }
                    if (flag)
                        result.append(endResult.charAt(i));
                    else {
                        if (i + 1 == startResult.length()) {
                            result.append(1);
                        } else {
                            result.append(startResult.charAt(i + 1));
                        }
                    }
                } else {
                    result.append(Integer.parseInt(String.valueOf(startResult.charAt(i))) + 1);
                }
                break;
            }
        }

        return result.toString();
    }

    public boolean compareDiapason(String encoded, Character symbol) {
        Section currentSection = freqSection.get(symbol);
        String curStartDiap = getCorrectStringFromDouble(String.valueOf(currentSection.startDiapason));
        String curEndDiap = getCorrectStringFromDouble(String.valueOf(currentSection.endDiapason));

        String currentEncodedStr = encoded.substring(decodeOffset);

        int minStartLength = Math.min(currentEncodedStr.length(), curStartDiap.length());
        int minEndLength = Math.min(currentEncodedStr.length(), curEndDiap.length());

        return recursiveStartCompareStrings(currentEncodedStr, curStartDiap, 0, minStartLength) &&
                recursiveEndCompareStrings(currentEncodedStr, curEndDiap, 0, minEndLength);
    }

    private boolean recursiveStartCompareStrings(String encoded, String curStart, int offset, int minLength) {

        if (encoded.charAt(offset) == curStart.charAt(offset)) {
            offset++;
            if (offset == minLength)
                return true;
            return recursiveStartCompareStrings(encoded, curStart, offset, minLength);
        }

        return encoded.charAt(offset) >= curStart.charAt(offset);
    }

    private boolean recursiveEndCompareStrings(String encoded, String curEnd, int offset, int minLength) {

        if (encoded.charAt(offset) == curEnd.charAt(offset)) {
            offset++;
            if (offset == minLength) {
                return curEnd.length() > minLength;
            }
            return recursiveEndCompareStrings(encoded, curEnd, offset, minLength);
        }

        return encoded.charAt(offset) < curEnd.charAt(offset);
    }

    private int getCountOfEqualsRank() {
        int counter = 0;
        String startDiapason = getCorrectStringFromDouble(String.valueOf(this.startDiapason));
        String endDiapason = getCorrectStringFromDouble(String.valueOf(this.endDiapason));

        if (this.startDiapason == 0.0d) {
            while (endDiapason.charAt(counter) == '0') {
                resultStartDiapason.append("0");
                resultEndDiapason.append("0");
                System.out.println(resultStartDiapason);
                counter++;
            }
            return counter;
        }

        int minLength = Math.min(startDiapason.length(), endDiapason.length());
        for (int i = 0; i < minLength; i++) {
            if (startDiapason.charAt(i) == endDiapason.charAt(i))
                counter++;
            else {
                resultStartDiapason.append(startDiapason, 0, i);
                resultEndDiapason.append(endDiapason, 0, i);

                return counter;
            }
        }

        return counter;
    }

    private String getCorrectStringFromDouble(String num) {

        if (num.matches(".*E.*")) {
            StringBuilder result = new StringBuilder();
            String[] splittedData = num.split("E");

            int additionalForExp;
            try {
                additionalForExp = splittedData[0].split("\\.")[1].length();
            } catch (ArrayIndexOutOfBoundsException e) {
                additionalForExp = 0;
            }
            Integer exponen = Integer.parseInt(splittedData[1].substring(1)) + additionalForExp;

            splittedData[0] = splittedData[0].replaceAll("\\.", "");

            int tempJ = 0;
            for (int i = exponen; i >= 0; i--) {
                if (tempJ < splittedData[0].length()) {
                    if (i - 1 < 0) {
                        result.append(".");
                    }
                    result.append(splittedData[0].charAt(tempJ));
                    tempJ++;
                } else {
                    if (i - 1 < 0) {
                        result.insert(0, ".");
                    }
                    result.insert(0, "0");
                }
            }

            return result.substring(2);
        }

        return num.substring(2);
    }

    private void setNewScaledDiapason(int countOfRanks) {
        for (int i = 0; i < countOfRanks; i++) {
            startDiapason *= 10;
            endDiapason *= 10;
        }
        int tempStart = (int)startDiapason;
        int tempEnd = (int)endDiapason;

        startDiapason = startDiapason - tempStart;
        endDiapason = endDiapason - tempEnd;

        tempDiapason = startDiapason;
        decodeOffset += countOfRanks;
    }

    public class Section {

        private Double startDiapason;
        private Double endDiapason;
        private Double probability;

        public Section(Double sd, Double ed, Double prob) {
            startDiapason = sd;
            endDiapason = ed;
            probability = prob;
        }

        public Double getStartDiapason() {
            return startDiapason;
        }

        public void setStartDiapason(Double startDiapason) {
            this.startDiapason = startDiapason;
        }

        public Double getEndDiapason() {
            return endDiapason;
        }

        public void setEndDiapason(Double endDiapason) {
            this.endDiapason = endDiapason;
        }

        public Double getProbability() {
            return probability;
        }

        public void setProbability(Double probability) {
            this.probability = probability;
        }

        @Override
        public String toString() {
            return "Section{" +
                    "startDiapason=" + startDiapason +
                    ", endDiapason=" + endDiapason +
                    ", probability=" + probability +
                    '}';
        }
    }
}