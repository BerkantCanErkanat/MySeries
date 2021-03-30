package com.berkantcanerkanat.myseries;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReturnClass {
    @SerializedName("results")
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }
}
