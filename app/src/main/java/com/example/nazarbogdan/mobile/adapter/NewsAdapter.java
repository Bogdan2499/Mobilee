package com.example.nazarbogdan.mobile.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nazarbogdan.mobile.MainActivity;
import com.example.nazarbogdan.mobile.Models.Article;
import com.example.nazarbogdan.mobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Context context;
    private List<Article> news = new ArrayList<>();
    private final Callback callback;

    public NewsAdapter(Callback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item, parent, false);
        final ViewHolder holder = new ViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article article = news.get(holder.getAdapterPosition());
                callback.onGameClick(article);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Article article = news.get(position);
        Picasso.get().load(article.getUrlToImage()).into(holder.ivPicture);
        holder.tvName.setText(article.getTitle());
        holder.tvDeck.setText(article.getDescription());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void replaceAll(List<Article> gamesToReplace) {
        news.clear();
        news.addAll(gamesToReplace);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPicture)
        ImageView ivPicture;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDeck)
        TextView tvDeck;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callback {
        void onGameClick(Article game);
    }
}