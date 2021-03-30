package com.berkantcanerkanat.myseries;

import com.google.gson.annotations.SerializedName;

public class ReturnClassWId {
    @SerializedName("name")
    private String name;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("original_language")
    private String original_lang;

    @SerializedName("overview")
    private String overview;

    @SerializedName("first_air_date")
    private String first_air_date;

    @SerializedName("last_air_date")
    private String last_air_date;

    @SerializedName("number_of_episodes")
    private int num_of_episodes;

    @SerializedName("number_of_seasons")
    private int num_of_seasons;

    @SerializedName("status")
    private String status;

    @SerializedName("tagline")
    private String tagline;

    public String getName() {
        return name;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_lang() {
        return original_lang;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public int getNum_of_episodes() {
        return num_of_episodes;
    }

    public int getNum_of_seasons() {
        return num_of_seasons;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    @Override
    public String toString() {
        return "ReturnClassWId{" +
                "name='" + name + '\'' +
                ", vote_average=" + vote_average +
                ", poster_path='" + poster_path + '\'' +
                ", original_lang='" + original_lang + '\'' +
                ", overview='" + overview + '\'' +
                ", first_air_date='" + first_air_date + '\'' +
                ", last_air_date='" + last_air_date + '\'' +
                ", num_of_episodes=" + num_of_episodes +
                ", num_of_seasons=" + num_of_seasons +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                '}';
    }
}
