package com.example.miniaturemanagement.model;

public class NeededMiniature {

    Miniature miniature;
    int count;

    public NeededMiniature(Miniature miniature, int count) {
        this.miniature = miniature;
        this.count = count;
    }

    public Miniature getMiniature() {
        return miniature;
    }

    public void setMiniature(Miniature miniature) {
        this.miniature = miniature;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
