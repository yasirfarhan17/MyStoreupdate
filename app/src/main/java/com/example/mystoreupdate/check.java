package com.example.mystoreupdate;

public class check {
    long accNum;
    double blanceAmt;

    public check(long accNum, double blanceAmt) {
        this.accNum = accNum;
        this.blanceAmt = blanceAmt;
    }

    public long getAccNum() {
        return accNum;
    }

    public void setAccNum(long accNum) {
        this.accNum = accNum;
    }

    public double getBlanceAmt() {
        return blanceAmt;
    }

    public void setBlanceAmt(double blanceAmt) {
        this.blanceAmt = blanceAmt;
    }
}
