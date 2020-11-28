package com.example.panderinath_techbulls_assigment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.moviesListHolder> {

    ArrayList<MoviesModel> arrayList;
    private Context mContext;

    public MoviesListAdapter(ArrayList<MoviesModel> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MoviesListAdapter.moviesListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_movies_details,viewGroup,false);
        MoviesListAdapter.moviesListHolder boyListHolder = new MoviesListAdapter.moviesListHolder(view);
        return boyListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesListAdapter.moviesListHolder ListHolder, int position) {
         TextView tv_name = ListHolder.tv_Movie_Name;
         TextView tv_type = ListHolder.tv_Movie_type;
         TextView tv_year = ListHolder.tv_Movie_year;
         ImageView iv_movie_Img = ListHolder.iv_movie_Img;

        tv_name.setText(arrayList.get(position).getName());
        tv_type.setText(arrayList.get(position).getType());
        tv_year.setText(arrayList.get(position).getDate());
        if(arrayList.get(position).getPoster().isEmpty()) {
            //iv_movie_Img.setImageResource(R.drawable.ic_account_circle_white_24dp);
        }else {//"https://www.picodel.com/seller/assets/"+
            String imgUrl = arrayList.get(position).getPoster();
            Picasso.with(mContext)
                    .load(imgUrl)
                    //.error(R.drawable.ic_account_circle_white_24dp)
                    .into(iv_movie_Img);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class moviesListHolder extends RecyclerView.ViewHolder{

          TextView tv_Movie_type,tv_Movie_year,tv_Movie_Name;
          ImageView iv_movie_Img;

          public moviesListHolder(@NonNull View itemView) {
            super(itemView);
              tv_Movie_type = itemView.findViewById(R.id.tv_Movie_type);
              tv_Movie_year = itemView.findViewById(R.id.tv_Movie_year);
              tv_Movie_Name = itemView.findViewById(R.id.tv_Movie_Name);
              iv_movie_Img = itemView.findViewById(R.id.iv_movie_Img);

        }
    }
}
