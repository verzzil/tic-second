package ru.itis.arithmetic.model;

import java.util.LinkedHashMap;

public class FrequencySection {

    private Double startDiapason = 0d;
    private Double endDiapason = 1d;
    private Double tempDiapason = 0d;
    private int bitsOutstanding = 0;
    private final StringBuilder resultBits = new StringBuilder();
    private final LinkedHashMap<Character, Section> freqSection = new LinkedHashMap<>();

    public FrequencySection() {
    }

    public Double getStartDiapason() {
        return startDiapason;
    }

    public Double getEndDiapason() {
        return endDiapason;
    }

    public void setEndDiapason(Double endDiapason) {
        this.endDiapason = endDiapason;
    }

    public Double getTempDiapason() {
        return tempDiapason;
    }

    public StringBuilder getResultBits() {
        return resultBits;
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

    public void renormalization() {
        if (startDiapason >= 0d && endDiapason < 0.5d) {
            resultBits.append("0");
            flushAccumulatedBits("1");

            expansionToTheRight();
        } else if (startDiapason >= 0.25d && endDiapason < 0.75d) {
            bitsOutstanding++;

            expansionToAllDirections();
        } else if (startDiapason >= 0.5d && endDiapason < 1) {
            resultBits.append("1");
            flushAccumulatedBits("0");

            expansionToTheLeft();
        }

        this.tempDiapason = this.startDiapason;
    }

    public void recalculateRange() {
        Double diapasonLength = this.endDiapason - this.startDiapason;

        for (Character sectionName : freqSection.keySet()) {
            freqSection.get(sectionName).setStartDiapason(tempDiapason);
            freqSection.get(sectionName).setEndDiapason(
                    diapasonLength * freqSection.get(sectionName).probability + freqSection.get(sectionName).getStartDiapason()
            );

            this.tempDiapason += freqSection.get(sectionName).getEndDiapason() - freqSection.get(sectionName).getStartDiapason();
        }
    }

    public void setEndBits() {
        if (this.startDiapason < 0.25d)
            resultBits.append("01");
        else
            resultBits.append("10");
    }

    private void flushAccumulatedBits(String bit) {
        for (int i = 0; i < bitsOutstanding; i++) {
            resultBits.append(bit);
        }
        bitsOutstanding = 0;
    }

    private void expansionToTheRight() {
        startDiapason *= 2;
        endDiapason *= 2;
    }

    private void expansionToTheLeft() {
        startDiapason = startDiapason - (1 - startDiapason);
        endDiapason = endDiapason - (1 - endDiapason);
    }

    private void expansionToAllDirections() {
        startDiapason = startDiapason + (startDiapason - 0.5);
        endDiapason = endDiapason + (endDiapason - 0.5);
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
