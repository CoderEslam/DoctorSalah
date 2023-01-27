package com.doubleclick.doctor.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.doctor.R;
import com.doubleclick.doctor.model.Sub;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.SecondViewHolder> {

    private ArrayList<Sub> subs = new ArrayList<>();

    public SecondAdapter(ArrayList<Sub> subs) {
        this.subs = subs;
    }


    @NonNull
    @Override
    public SecondAdapter.SecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SecondViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sub_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SecondAdapter.SecondViewHolder holder, int position) {

        holder.tv_sub_title.setText(subs.get(position).getName());
        Glide.with(holder.itemView.getContext()).load(subs.get(position).getImage()).into(holder.profile_image);
    }

    @Override
    public int getItemCount() {
        return subs.size();
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_sub_title;
        private CircleImageView profile_image;

        public SecondViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sub_title = itemView.findViewById(R.id.tv_sub_title);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}
