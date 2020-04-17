package com.chotuboy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chotuboy.R;
import com.chotuboy.modelClass.orderDetailsModel.OrderDetailsResponse;
import com.chotuboy.modelClass.orderDetailsModel.OrderProduct;

import java.util.List;

public class OrderProductDetailsAdapter extends RecyclerView.Adapter<OrderProductDetailsAdapter.ViewHolder> {
    Context context;
    OrderDetailsResponse orderDetailsResponse;
    List<OrderProduct> orderProductList;
    int order_product_details_item ;


    public OrderProductDetailsAdapter(List<OrderProduct> orderProductLis, Context context1, int order_product_details_item) {
        this.orderProductList = orderProductLis;
        this.context = context1;
        this.order_product_details_item = order_product_details_item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_details_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderProduct orderProduct = orderProductList.get(position);

        holder.OrderedIdTv.setText(""+orderProduct.getOrderId());
        holder.customerIdTv.setText(""+orderProduct.getCustomerId());
        holder.Tv1Product_title.setText(""+orderProduct.getProductTitle());
        holder.tv2ProductWeight.setText(""+orderProduct.getQuantity());
        holder.tv3ProductPrice.setText(""+orderProduct.getPrice());

    }

    @Override
    public int getItemCount() {
        /*if (orderDetailsResponse!=null
        && orderDetailsResponse.getOrderInfo()!=null
        && orderDetailsResponse.getOrderInfo().size()>0){
            return orderDetailsResponse.getOrderInfo().size();
        }else{return 0;}*/

        if (orderProductList != null && orderProductList.size() > 0) {
            return orderProductList.size();
        } else {
            return 0;
        }
    }

    public void setData(OrderDetailsResponse orderDetailsResponse) {
        this.orderDetailsResponse = orderDetailsResponse;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView OrderedIdTv,customerIdTv,Tv1Product_title, tv2ProductWeight, tv3ProductPrice, tv5TotalProductPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            OrderedIdTv = itemView.findViewById(R.id.OrderedId);
            customerIdTv = itemView.findViewById(R.id.customerId);
            Tv1Product_title = itemView.findViewById(R.id.Tv1Product_title);
            tv2ProductWeight = itemView.findViewById(R.id.Tv2ProductQuantity);
            tv3ProductPrice = itemView.findViewById(R.id.Tv3ProductPrice);
            tv5TotalProductPrice = itemView.findViewById(R.id.Tv5TotalProductPrice);

        }
    }
}
