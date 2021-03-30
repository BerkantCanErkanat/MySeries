package com.berkantcanerkanat.myseries;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {
    ArrayList<Result> myMovies;
    Context context;
    SQLiteDatabase db;
    public MyListAdapter(ArrayList<Result> myMovies, Context context) {
        this.myMovies = myMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.list_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(myMovies.get(position).getName());
        holder.rate.setText(String.valueOf(myMovies.get(position).getVote_average()));

        //glide
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+myMovies.get(position).getPoster_path())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowSeriesActivity.class);
                intent.putExtra("id",myMovies.get(position).getId());
                context.startActivity(intent);
            }
        });

        holder.rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db = context.openOrCreateDatabase("MyMovies",Context.MODE_PRIVATE,null);
                    db.delete("mymovies","id = ?",new String[] {String.valueOf(myMovies.get(position).getId())});
                    myMovies.remove(position);
                    notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myMovies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image,rightImage;
        TextView name,rate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageRow);
            name = itemView.findViewById(R.id.nameRow);
            rate = itemView.findViewById(R.id.rateRow);
            rightImage = itemView.findViewById(R.id.rightImageView);
            rightImage.setImageResource(R.drawable.ic_baseline_delete_24);
        }
    }


}
