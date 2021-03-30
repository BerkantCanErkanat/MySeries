package com.berkantcanerkanat.myseries;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebStorage;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.myViewHolder> {
    ArrayList<Result> seriesList;
    Context context;
    SQLiteDatabase db;
    HashSet<String> ids;
    public ListAdapter(ArrayList<Result> seriesList, Context context,HashSet<String> ids) {
        this.seriesList = seriesList;
        this.context = context;
        this.ids = ids;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.list_row,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
      holder.name.setText(seriesList.get(position).getName());
      holder.rate.setText(String.valueOf(seriesList.get(position).getVote_average()));

      //glide
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+seriesList.get(position).getPoster_path())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowSeriesActivity.class);
                intent.putExtra("id",seriesList.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ids.contains(String.valueOf(seriesList.get(position).getId()))){
                    addToDb(seriesList.get(position).getId());
                    ids.add(String.valueOf(seriesList.get(position).getId()));
                    Toast.makeText(context,"added to your list",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"already in your list",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
   public void addToDb(int id){
       try{
           db = context.openOrCreateDatabase("MyMovies",Context.MODE_PRIVATE,null);
           db.execSQL("CREATE TABLE IF NOT EXISTS mymovies (id VARCHAR)");
           String sqlStatement = "INSERT INTO mymovies (id) VALUES (?)";
           SQLiteStatement sqLiteStatement = db.compileStatement(sqlStatement);
           sqLiteStatement.bindString(1, String.valueOf(id));
           sqLiteStatement.execute();
       }catch(Exception e){
           e.printStackTrace();
       }
   }
    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        ImageView image,rightImage;
        TextView name,rate;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageRow);
            name = itemView.findViewById(R.id.nameRow);
            rate = itemView.findViewById(R.id.rateRow);
            rightImage = itemView.findViewById(R.id.rightImageView);
            rightImage.setImageResource(R.drawable.ic_baseline_add_circle_24);
        }
    }
}
