package com.berkantcanerkanat.myseries;

import com.google.gson.annotations.SerializedName;

public class Result {
    public Result(int id, String name, double vote_average, String poster_path) {
        this.id = id;
        this.name = name;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("poster_path")
    private String poster_path;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
