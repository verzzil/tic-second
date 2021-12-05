package ru.itis.arithmetic.model;

import java.util.ArrayList;

public class FrequencySection {

    private Double tempDiapason = 0d;
    private Double startDiapason = 0d;
    private Double endDiapason = 1d;
    private final ArrayList<Section> freqSection = new ArrayList<>();

    public FrequencySection() { }

    public Double getStartDiapason() {
        return startDiapason;
    }

    public Double getEndDiapason() {
        return endDiapason;
    }

    public ArrayList<Section> getFreqSection() {
        return freqSection;
    }

    public void addSection(Character symbol, Double probability) {
        freqSection.add(new Section(symbol, probability));
    }

    public void addSubSection(Section section) {
        startDiapason = section.startDiapason;
        endDiapason = section.endDiapason;

        Section prevSection = null;
        for (Section sec : freqSection) {
            if (prevSection != null)
                sec.setStartDiapason(prevSection.endDiapason);
            else
                sec.setStartDiapason(startDiapason);
            sec.setEndDiapason(startDiapason + (endDiapason - startDiapason) * sec.getProbability());
            prevSection = sec;
        }
    }

    public class Section implements Comparable<Character> {

        public Character symbol;
        private Double startDiapason;
        private Double endDiapason;
        private Double probability;

        public Section(Character s, Double probability) {
            symbol = s;
            startDiapason = FrequencySection.this.tempDiapason;
            endDiapason = FrequencySection.this.tempDiapason + probability;
            FrequencySection.this.tempDiapason += probability;
            this.probability = endDiapason;
        }

        public Character getSymbol() {
            return symbol;
        }

        public void setSymbol(Character symbol) {
            this.symbol = symbol;
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
        public int compareTo(Character symbol) {
            return this.symbol == symbol ? 0 : -1;
        }

        @Override
        public String toString() {
            return "Section{" +
                    "symbol=" + symbol +
                    ", startDiapason=" + startDiapason +
                    ", endDiapason=" + endDiapason +
                    '}';
        }
    }
}
