package com.dicoding.favorit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.favorit.R;
import com.dicoding.favorit.entity.Following;

import java.util.ArrayList;

public class ListFollowingAdapter extends RecyclerView.Adapter<ListFollowingAdapter.ListViewHolder>  {
    private ArrayList<Following> listFollowing;

    public ListFollowingAdapter(ArrayList<Following> list) {
        this.listFollowing = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_following, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFollowingAdapter.ListViewHolder holder, int position) {
        Following Following = listFollowing.get(position);
        Glide.with(holder.itemView.getContext())
                .load(Following.getAvatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgPhoto);
        holder.tvuserid.setText(Following.getUserid());
        holder.tvname.setText(Following.getName());
    }

    @Override
    public int getItemCount() {
        return listFollowing.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvuserid, tvname;
        ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_following);
            tvuserid = itemView.findViewById(R.id.tv_item_following_userid);
            tvname = itemView.findViewById(R.id.tv_item_following_name);
        }
    }
}

