package com.manokero.vividly.model;

import java.util.ArrayList;

public class SearchModel {
    private ArrayList<ImageModel> results;

    public SearchModel(ArrayList<ImageModel> results) {
        this.results = results;
    }

    public ArrayList<ImageModel> getResults() {
        return results;
    }
}
