package com.doubleclick.doctor.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.doctor.Interface.FirstInterface;
import com.doubleclick.doctor.R;

import java.util.ArrayList;

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.FirstViewHolder> {

    private ArrayList<String> arrayList ;
    private FirstInterface firstInterface;


    public FirstAdapter(ArrayList<String> arrayList, FirstInterface firstInterface) {
        this.arrayList = arrayList;
        this.firstInterface = firstInterface;
    }

    @NonNull
    @Override
    public FirstAdapter.FirstViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FirstViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FirstAdapter.FirstViewHolder holder, int position) {
        if (position % 2 == 1) {
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 200, 0, 0);
            holder.constrain_parent.setLayoutParams(layoutParams);
        }
        holder.tv_title.setText(arrayList.get(position));
        holder.itemView.setOnClickListener(view -> {
            firstInterface.onItemFirst(arrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FirstViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private ConstraintLayout constrain_parent;

        public FirstViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            constrain_parent = itemView.findViewById(R.id.constrain_parent);
        }
    }
}
