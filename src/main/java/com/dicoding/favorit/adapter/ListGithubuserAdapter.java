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
import com.dicoding.favorit.entity.Githubuser;

import java.util.ArrayList;

public class ListGithubuserAdapter extends RecyclerView.Adapter<ListGithubuserAdapter.ListViewHolder> {

    private ArrayList<Githubuser> listGithubuser;
    private OnItemClickCallback onItemClickCallback;


    public interface OnItemClickCallback {
        void onItemClicked(Githubuser data);
    }

    public ListGithubuserAdapter(ArrayList<Githubuser> list) {
        this.listGithubuser = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_githubuser, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Githubuser githubuser = listGithubuser.get(position);
        Glide.with(holder.itemView.getContext())
                .load(githubuser.getAvatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgPhoto);
        holder.tvuserid.setText(githubuser.getUserid());
        holder.tvname.setText(githubuser.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listGithubuser.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGithubuser.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvuserid, tvname;
        ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvuserid = itemView.findViewById(R.id.tv_item_userid);
            tvname = itemView.findViewById(R.id.tv_item_name);
        }
    }


    public void setFilter(ArrayList<Githubuser> filterList){
        listGithubuser = new ArrayList<>();
        listGithubuser.addAll(filterList);
        notifyDataSetChanged();
    }
}
