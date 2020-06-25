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
import com.dicoding.favorit.entity.Follower;

import java.util.ArrayList;

public class ListFollowerAdapter extends RecyclerView.Adapter<ListFollowerAdapter.ListViewHolder> {

    private ArrayList<Follower> listFollower;

    public ListFollowerAdapter(ArrayList<Follower> list) {
        this.listFollower = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_follower, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFollowerAdapter.ListViewHolder holder, int position) {
        Follower Follower = listFollower.get(position);
        Glide.with(holder.itemView.getContext())
                .load(Follower.getAvatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgPhoto);
        holder.tvuserid.setText(Follower.getUserid());
        holder.tvname.setText(Follower.getName());
    }

    @Override
    public int getItemCount() {
        return listFollower.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvuserid, tvname;
        ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_follower);
            tvuserid = itemView.findViewById(R.id.tv_item_follower_userid);
            tvname = itemView.findViewById(R.id.tv_item_follower_name);
        }
    }


}