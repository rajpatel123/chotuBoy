package com.chotuboy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chotuboy.R;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {
    Context context;

    public OrderDetailsAdapter(Context context1) {
        this.context = context1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_details_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;

        /*if (upcomingTrips != null && upcomingTrips.size() > 0) {
            return upcomingTrips.size();
        } else {
            return 0;
        }*/
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv1Product, tv2ProductWeight, tv3ProductPrice, tv5TotalProductPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1Product = itemView.findViewById(R.id.Tv1Product);
            tv2ProductWeight = itemView.findViewById(R.id.Tv2ProductWeight);
            tv3ProductPrice = itemView.findViewById(R.id.Tv3ProductPrice);
            tv5TotalProductPrice = itemView.findViewById(R.id.Tv5TotalProductPrice);

        }
    }
}
