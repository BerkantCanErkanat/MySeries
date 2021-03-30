package com.berkantcanerkanat.myseries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowSeriesActivity extends AppCompatActivity {
    private int series_id;
    String API_KEY = "23ded31c24a4d9912afc40b57f8d2efb";
    ReturnClassWId returnedSeries;
    ImageView poster;
    TextView name,overview,status,season,episode,vote,language,tagline,firstair,lastair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_series);
        Intent intent = getIntent();
        series_id = intent.getIntExtra("id",0);
        poster = findViewById(R.id.posterView);
        name = findViewById(R.id.nameSText);
        overview = findViewById(R.id.overviewSText);
        status = findViewById(R.id.statusSText);
        season = findViewById(R.id.seasonSText);
        episode = findViewById(R.id.epiosdeText);
        vote = findViewById(R.id.voteSText);
        language = findViewById(R.id.languageSText);
        tagline = findViewById(R.id.taglineSText);
        firstair = findViewById(R.id.firstAirSText);
        lastair = findViewById(R.id.lastAirSText);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiCalls myApiCall = retrofit.create(MyApiCalls.class);

        if(series_id != 0){
            Call<ReturnClassWId> response = myApiCall.getTheSeris(series_id,API_KEY);
            response.enqueue(new Callback<ReturnClassWId>() {
                @Override
                public void onResponse(Call<ReturnClassWId> call, Response<ReturnClassWId> response) {
                    if(response.isSuccessful()){
                        returnedSeries = response.body();
                        showTheDatas();
                    }

                }

                @Override
                public void onFailure(Call<ReturnClassWId> call, Throwable t) {

                }
            });
        }
    }
    public void showTheDatas(){
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+returnedSeries.getPoster_path())
                .into(poster);
        name.setText("Name : "+returnedSeries.getName());
        overview.setText("OverView: "+returnedSeries.getOverview());
        status.setText("Status: "+returnedSeries.getStatus());
        season.setText(String.valueOf("Season Number: "+returnedSeries.getNum_of_seasons()));
        episode.setText(String.valueOf("Episode Number: "+returnedSeries.getNum_of_episodes()));
        vote.setText(String.valueOf("Average Vote: "+returnedSeries.getVote_average()));
        language.setText("Original Language: "+returnedSeries.getOriginal_lang());
        tagline.setText("Tagline: "+ returnedSeries.getTagline());
        firstair.setText("First Air Date: "+returnedSeries.getFirst_air_date());
        lastair.setText("Last Air Date: "+returnedSeries.getLast_air_date());
    }
}