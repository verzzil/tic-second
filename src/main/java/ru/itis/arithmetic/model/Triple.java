package ru.itis.arithmetic.model;

public class Triple {

    private String startDiapason;
    private String endDiapason;
    private String optimalNum;

    public Triple(String sd, String ed, String on) {
        startDiapason = sd;
        endDiapason = ed;
        optimalNum = on;
    }

    @Override
    public String toString() {
        return "Triple{" +
                "\nstartDiapason='\t" + startDiapason + '\'' +
                "\nendDiapason='\t" + endDiapason + '\'' +
                "\noptimalNum='\t" + optimalNum + '\'' +
                "\n}";
    }

    public String getOptimalNum() {
        return optimalNum;
    }
}
