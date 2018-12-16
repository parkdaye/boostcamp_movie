package com.android.moviesearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        RatingBar rating;
        TextView pdate;
        TextView director;
        TextView actor;
        CardView cardView;
 
        MyViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.movie_img);
            title = view.findViewById(R.id.movie_title);
            rating = view.findViewById(R.id.movie_rating);
            pdate = view.findViewById(R.id.movie_year);
            director = view.findViewById(R.id.movie_director);
            actor = view.findViewById(R.id.movie_actor);
            cardView = view.findViewById(R.id.card_view);
        }
    }
 
    private ArrayList<items> movieList;
    MyAdapter(Context context, ArrayList<items> movieList){
        this.context = context;
        this.movieList = movieList;
    }
 
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
 
        return new MyViewHolder(v);
    }
 
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
 
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        Glide.with(context)
                .load(movieList.get(position).getImage())
                .placeholder(R.drawable.nophoto)
                .into(myViewHolder.img);
        myViewHolder.title.setText(movieList.get(position).getTitle());
        myViewHolder.rating.setRating(movieList.get(position).getUserRating());
        myViewHolder.pdate.setText(movieList.get(position).getPubDate());
        myViewHolder.director.setText(movieList.get(position).getDirector());
        myViewHolder.actor.setText(movieList.get(position).getActor());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieList.get(position).getLink()));
                context.startActivity(intent);
            }
        });

    }
 
    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
