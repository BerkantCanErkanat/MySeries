package com.berkantcanerkanat.myseries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyList extends AppCompatActivity {
     ArrayList<Result> myMovies;
     SQLiteDatabase db;
     ArrayList<String> ids;
     RecyclerView recyclerView;
     Call<Result> responseCall = null;
     Retrofit retrofit;
     String API_KEY = "23ded31c24a4d9912afc40b57f8d2efb";
     MyListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        ids = new ArrayList<>();
        myMovies = new ArrayList<>();
        getTheIds();
        retrofit();
        call();
        putThemToList();
    }
    public void call(){
        MyApiCalls myApiCall = retrofit.create(MyApiCalls.class);
        for(String id: ids){
            responseCall = myApiCall.getMySeries(Integer.parseInt(id),API_KEY);
            responseCall.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if(response.isSuccessful()){
                        System.out.println("girdi");
                        Result movie = new Result(response.body().getId(),response.body().getName(),response.body().getVote_average(),response.body().getPoster_path());
                        System.out.println(movie);
                        myMovies.add(movie);
                        listAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });
        }
    }
    public void putThemToList(){
        recyclerView = findViewById(R.id.recyclerViewList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyList.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        listAdapter = new MyListAdapter(myMovies,MyList.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
    }
    public void retrofit(){
             retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public void getTheIds(){
        db = openOrCreateDatabase("MyMovies", Context.MODE_PRIVATE,null);
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM mymovies",null);
            int idIx = cursor.getColumnIndex("id");
            while(cursor.moveToNext()){
                ids.add(cursor.getString(idIx));
            }
            cursor.close();
        }catch (Exception ee){
            ee.printStackTrace();
        }
    }
}