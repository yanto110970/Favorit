package com.dicoding.favorit.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.favorit.CustomOnItemClickListener;
import com.dicoding.favorit.R;
import com.dicoding.favorit.UpdDelFavoritActivity;
import com.dicoding.favorit.entity.Favorit;

import java.util.ArrayList;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.NoteViewHolder> {
    private final ArrayList<Favorit> listFavorits = new ArrayList<>();
    private final Activity activity;

    public FavoritAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Favorit> getListFavorits() {
        return listFavorits;
    }

    public void setListFavorits(ArrayList<Favorit> listFavorits) {
        this.listFavorits.clear();
        this.listFavorits.addAll(listFavorits);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorit, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.tvUserid.setText(listFavorits.get(position).getUserid());
        holder.tvUsername.setText(listFavorits.get(position).getUsername());
        Glide.with(holder.itemView.getContext())
                .load(listFavorits.get(position).getAvatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, UpdDelFavoritActivity.class);
                intent.putExtra(UpdDelFavoritActivity.EXTRA_POSITION, position);
                intent.putExtra(UpdDelFavoritActivity.EXTRA_NOTE, listFavorits.get(position));
                activity.startActivityForResult(intent, UpdDelFavoritActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listFavorits.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvUserid, tvUsername;
        ImageView imgPhoto;

        NoteViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvUserid = itemView.findViewById(R.id.tv_item_userid);
            tvUsername = itemView.findViewById(R.id.tv_item_username);
        }
    }
}

