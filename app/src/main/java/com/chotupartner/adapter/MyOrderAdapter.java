package com.chotupartner.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chotupartner.OrderDetailsActivity;
import com.chotupartner.R;
import com.chotupartner.modelClass.orderlist.OrderInfo;
import com.chotupartner.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private List<OrderInfo> moviesList;
    private LayoutInflater mInflater;
    private MyOrderAdapter.ItemClickListener mClickListener;
    boolean check = true;
    Context context;
    Context cartListActivity;
    int value = 0, count = 1;

    // data is passed into the constructor
    public MyOrderAdapter(Context cartListActivity, List<OrderInfo> moviesList) {

        this.moviesList = moviesList;
        this.cartListActivity = cartListActivity;
    }

    // inflates the cell layout from xml when needed
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.myorder_item, parent, false);


        return new MyOrderAdapter.ViewHolder(itemView);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, final int position) {


        //  Picasso.get().load(moviesList.get(position).getProductImg()).into(holder.ivItem);

       // holder.deliverBy.setText("Deliver By "+moviesList.get(position).getOutlet().getOutletName());
        holder.tvStoreName.setText("Order Amount");
        holder.tvPrice.setText("Rs " + moviesList.get(position).getAmount());

        holder.tvDeliveryChar.setText("Rs " + moviesList.get(position).getDeliveryCharge());
        holder.tvDeliverd.setVisibility(View.GONE);

        holder.tvOrderID1.setText(""+moviesList.get(position).getOrderCustomerId());

        int total = (int) Float.parseFloat(moviesList.get(position).getAmount());


        if (moviesList.get(position).getOrderStatus().equalsIgnoreCase("Pending")) {

            Picasso.get().load(R.drawable.circle).into(holder.iv1);

            Picasso.get().load(R.drawable.ring).into(holder.iv2);
            Picasso.get().load(R.drawable.ring).into(holder.iv3);
            Picasso.get().load(R.drawable.ring).into(holder.iv4);
        } else if (moviesList.get(position).getOrderStatus().equalsIgnoreCase("Processing")) {
            Picasso.get().load(R.drawable.circle).into(holder.iv1);
            holder.iv1Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            Picasso.get().load(R.drawable.circle).into(holder.iv2);
            Picasso.get().load(R.drawable.ring).into(holder.iv3);
            Picasso.get().load(R.drawable.ring).into(holder.iv4);
        } else if (moviesList.get(position).getOrderStatus().equalsIgnoreCase("Processed")) {

            Picasso.get().load(R.drawable.circle).into(holder.iv1);
            holder.iv1Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            holder.iv2Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));



            Picasso.get().load(R.drawable.circle).into(holder.iv2);
            Picasso.get().load(R.drawable.circle).into(holder.iv3);
            Picasso.get().load(R.drawable.ring).into(holder.iv4);
        } else if (moviesList.get(position).getOrderStatus().equalsIgnoreCase("Complete")) {
            holder.iv1Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            holder.iv2Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            holder.iv3Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));

            Picasso.get().load(R.drawable.circle).into(holder.iv1);
            Picasso.get().load(R.drawable.circle).into(holder.iv2);
            Picasso.get().load(R.drawable.circle).into(holder.iv3);
            Picasso.get().load(R.drawable.circle).into(holder.iv4);

        } else {
            holder.llOutLetText.setVisibility(View.GONE);

            holder.llImage.setVisibility(View.GONE);
            holder.tvDeliverd.setVisibility(View.VISIBLE);
        }


        holder.totalPaidAmount.setText("Rs " + total);


        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(Long.parseLong(moviesList.get(position).getSlotBook())*1000);  //here your time in miliseconds
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.YEAR);
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);

        holder.DeliveryDate.setText("Placed on " + Utils.startTimeFormat(Long.parseLong(moviesList.get(position).getSlotBook())*1000));

//        holder.DeliveryDate.setText("Scheduled for " + date + " " + time);

        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iii = new Intent(view.getContext(), OrderDetailsActivity.class);
                iii.putExtra("orderID", moviesList.get(position).getOrderId());
                view.getContext().startActivity(iii);

            }
        });


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivStore, iv1, iv2, iv3, iv4;
        public View iv1Line, iv2Line, iv3Line;

        RelativeLayout rl;
        TextView DeliveryDate, tvStoreName, tvPrice,deliverBy, tvDeliverd,tvOrderID1, tvDeliveryChar, totalPaidAmount;
        LinearLayout llImage, llOutLetText, llTop;
        Button btnViewDetails;

        ViewHolder(View itemView) {
            super(itemView);


            DeliveryDate = itemView.findViewById(R.id.DeliveryDate);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            deliverBy = itemView.findViewById(R.id.deliverBy);

            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDeliveryChar = itemView.findViewById(R.id.tvDeliveryChar);
            tvOrderID1 = itemView.findViewById(R.id.tvOrderID1);
            totalPaidAmount = itemView.findViewById(R.id.totalPaidAmount);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            ivStore = itemView.findViewById(R.id.ivStore);
            llImage = itemView.findViewById(R.id.llImage);
            llOutLetText = itemView.findViewById(R.id.llOutLetText);


            iv1 = itemView.findViewById(R.id.iv1);
            iv2 = itemView.findViewById(R.id.iv2);
            iv3 = itemView.findViewById(R.id.iv3);
            iv4 = itemView.findViewById(R.id.iv4);

            tvDeliverd = itemView.findViewById(R.id.tvDeliverd);


            iv1Line = itemView.findViewById(R.id.v1line);
            iv2Line = itemView.findViewById(R.id.v2line);
            iv3Line = itemView.findViewById(R.id.v3line);

        }


    }

    // convenience method for getting data at click position


    // allows clicks events to be caught
    void setClickListener(MyOrderAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
