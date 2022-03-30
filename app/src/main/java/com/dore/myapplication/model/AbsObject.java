package com.dore.myapplication.model;

public abstract class AbsObject {
    public int rate = 0;
    public boolean isFavor = false;
    public boolean isPlaying = false;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isFavor() {
        return isFavor;
    }

    public void setFavor(boolean favor) {
        isFavor = favor;
    }

}
