package com.chotuboy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chotuboy.R;
import com.chotuboy.modelClass.orderDetailsModel.OrderDetailsResponse;
import com.chotuboy.modelClass.orderDetailsModel.OrderInfo;

import java.util.List;

public class GettingNewOrderAdapter extends RecyclerView.Adapter<GettingNewOrderAdapter.MyViewHolder> {
    Context context;
    GetNewOrderListInterface getNewOrderListInterface;
    List<OrderInfo> orderInfos;
    private OrderDetailsResponse orderDetailsResponse;
    int getting_ordered_recy_item;

    public GettingNewOrderAdapter(List<OrderInfo> orderInfos1, Context context1, int getting_ordered_recy_item) {
        this.orderInfos = orderInfos1;
        this.context = context1;
        this.getting_ordered_recy_item = getting_ordered_recy_item;
      //  this.orderDetailsResponse = orderDetailsResponse;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.getting_ordered_recy_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        OrderInfo orderInfo = orderInfos.get(position);
        holder.orderIdShowTV.setText("" + orderInfo.getOrderId());
        holder.outLetIdShowTV.setText("" + orderInfo.getOutletId());
        holder.dateShowTV.setText("" + orderInfo.getDateAdded());

        holder.gettingOrderLayOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getNewOrderListInterface != null) {
                    //getNewOrderListInterface.newOrderList(orderInfos.get(position).getOrderId());
                    getNewOrderListInterface.newOrderListI(orderInfo.getOrderId(),
                            orderInfo.getCustomerId(), orderInfo.getShippingId(),
                            orderInfo.getOutletId(), orderInfo.getDeliveryId(),
                            orderInfo.getRazorpayOrderId(), orderInfo.getAmount(),
                            orderInfo.getDiscount(), orderInfo.getPaymentStatus(),
                            orderInfo.getOrderStatus(), orderInfo.getFirstname(),
                            orderInfo.getLastname(), orderInfo.getEmail(),
                            orderInfo.getPhone(), orderInfo.getAddress(),
                            orderInfo.getCity(), orderInfo.getPostcode(),
                            orderInfo.getStateId(), orderInfo.getDeliveryCharge(),
                            orderInfo.getPaymentType(), orderInfo.getDateAdded(),
                            orderInfo.getOrderProductId(), orderInfo.getProductId(),
                            orderInfo.getPriceId(), orderInfo.getQuantity()
                    );
                }
            }
        });
    }

    public void setGetNewOrderListInterface(GetNewOrderListInterface getNewOrderListInterface1) {
        this.getNewOrderListInterface = getNewOrderListInterface1;
    }

    public interface GetNewOrderListInterface {       ///
        public void newOrderListI(Integer orderId, Integer customerId, Integer shippingId, Integer outletId, Integer deliveryId,
                                  Object razorpayOrderId, String amount, String discount, String paymentStatus, String orderStatus,
                                  String firstname, String lastname, String email, String phone, String address, String city,
                                  String postcode, Object stateId, Integer deliveryCharge, String paymentType, String dateAdded,
                                  Integer orderProductId, Integer productId, Integer priceId, Integer quantity);
    }

    public void setData(OrderDetailsResponse orderDetailsResponse1) {
        this.orderDetailsResponse= orderDetailsResponse1;
    }

    @Override
    public int getItemCount() {

        if (orderDetailsResponse!=null
        && orderDetailsResponse.getOrderInfo()!=null
        && orderDetailsResponse.getOrderInfo().size()>0){
        return orderDetailsResponse.getOrderInfo().size();
        }else {
            return 0;
        }
//         if (orderInfos != null && orderInfos.size() > 0) {
//            return orderInfos.size();
//        } else {
//            return 0;
//        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout gettingOrderLayOut;
        TextView orderTv, orderIdShowTV, outLetTV, outLetIdShowTV, dateTv, dateShowTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderTv = itemView.findViewById(R.id.OrderTv);
            orderIdShowTV = itemView.findViewById(R.id.OrderIdShowTV);
            outLetTV = itemView.findViewById(R.id.OutLetTV);
            outLetIdShowTV = itemView.findViewById(R.id.OutLetIdShowTV);
            dateTv = itemView.findViewById(R.id.DateTv);
            dateShowTV = itemView.findViewById(R.id.DateShowTV);
            gettingOrderLayOut = itemView.findViewById(R.id.GettingOrderLayOut);


        }
    }


}
