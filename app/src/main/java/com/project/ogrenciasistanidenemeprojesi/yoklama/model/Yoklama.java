package com.project.ogrenciasistanidenemeprojesi.yoklama.model;

import java.util.List;

public class Yoklama {

    private List<String> yoklama;

    public Yoklama(){}

    public Yoklama(List<String> yoklama) {
        this.yoklama = yoklama;
    }

    public List<String> getYoklama() {
        return yoklama;
    }

    public void setYoklama(List<String> yoklama) {
        this.yoklama = yoklama;
    }
}
