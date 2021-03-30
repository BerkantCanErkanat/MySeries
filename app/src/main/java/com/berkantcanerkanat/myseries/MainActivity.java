package com.berkantcanerkanat.myseries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<Result> returnedSeries;
    String API_KEY = "23ded31c24a4d9912afc40b57f8d2efb";
    RecyclerView recyclerView;
    EditText searchText;
    LoadingDialog loadingDialog;
    FloatingActionButton fab;
    HashSet<String> ids;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingDialog = new LoadingDialog(MainActivity.this);
        ids = new HashSet<>();
        getTheIds();
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyList.class);
                startActivity(intent);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiCalls myApiCall = retrofit.create(MyApiCalls.class);
        Button search = findViewById(R.id.searchButton);
        searchText = findViewById(R.id.searchText);
        recyclerView = findViewById(R.id.recyclerView);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(searchText.getText().toString().trim())){
                    loadingDialog.startLoadingDialog();
                    Call<ReturnClass> response = myApiCall.searchMovie(API_KEY,searchText.getText().toString().trim());
                    response.enqueue(new Callback<ReturnClass>() {
                        @Override
                        public void onResponse(Call<ReturnClass> call, Response<ReturnClass> response) {
                            if(response.isSuccessful()){
                                returnedSeries = new ArrayList<>(response.body().getResults());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                                layoutManager.setOrientation(RecyclerView.VERTICAL);
                                recyclerView.setLayoutManager(layoutManager);
                                ListAdapter listAdapter = new ListAdapter(returnedSeries,MainActivity.this,ids);
                                recyclerView.setAdapter(listAdapter);
                                loadingDialog.dismissDialog();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReturnClass> call, Throwable t) {

                        }
                    });
                }

            }


        });

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