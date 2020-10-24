package com.example.test.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.NewsItems;
import com.example.test.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    ArrayList<NewsItems> NewsData;
    Context mContext;
public  NewsAdapter(ArrayList<NewsItems> NewsData, Context context){
    this.NewsData=NewsData;
    this.mContext=context;
}

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final  NewsItems items=NewsData.get(position);
        holder.UName.setText(String.valueOf(items.getUserName()));
        holder.UPLoadDate.setText(String.valueOf(items.getNewsDate()));
        holder.NewsTitle.setText(String.valueOf(items.getNewsTitle()));
        holder.NewsDescription.setText(String.valueOf(items.getNewsDescription()));
        Picasso.get().load(items.getImage()).into(holder.Image);
        Picasso.get().load(items.getImage()).into(holder.UImage);
    }
    @Override
    public int getItemCount() {
        if(!NewsData.isEmpty()){
            return NewsData.size();
        }else{
            return 0;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView UName,UPLoadDate,NewsTitle,NewsDescription,ReadMore;
    ImageView Image,UImage;
        public ViewHolder( View itemView) {

            super(itemView);
            UName=itemView.findViewById(R.id.TvUserName);
            UPLoadDate=itemView.findViewById(R.id.TvUpLoadDate);
            NewsTitle=itemView.findViewById(R.id.TvNewsTitle);
            NewsDescription=itemView.findViewById(R.id.TvNewsDescription);
            ReadMore=itemView.findViewById(R.id.TvReadMore);
            Image=itemView.findViewById(R.id.NewsImage);
            UImage=itemView.findViewById(R.id.UserImag);

        }
    }
}
