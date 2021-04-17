package com.chotupartner.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chotupartner.OrderDetailsActivity;
import com.chotupartner.R;
import com.chotupartner.activity.ui.store.ProductOnOutlet;
import com.chotupartner.modelClass.orderlist.OrderInfo;
import com.chotupartner.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductOnOutlet> productOnOutlet;
    private LayoutInflater mInflater;
    private ProductAdapter.ItemClickListener mClickListener;
    boolean check = true;
    Context context;
    Context cartListActivity;
    ItemClickListener itemClickListener;
    int value = 0, count = 1;

    // data is passed into the constructor
    public ProductAdapter(Context cartListActivity, List<ProductOnOutlet> productOnOutlet, ItemClickListener itemClickListener) {
        this.productOnOutlet = productOnOutlet;
        this.cartListActivity = cartListActivity;
        this.itemClickListener = itemClickListener;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.product_info, parent, false);


        return new ProductAdapter.ViewHolder(itemView);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, final int position) {


        //  Picasso.get().load(moviesList.get(position).getProductImg()).into(holder.ivItem);

       // holder.deliverBy.setText("Deliver By "+moviesList.get(position).getOutlet().getOutletName());


        holder.productName.setText("" + productOnOutlet.get(position).getProductName());
        if (TextUtils.isEmpty(productOnOutlet.get(position).getMrp())){
            holder.mrp.setText("Rs " + productOnOutlet.get(position).getPrice());
            holder.finalPrice.setText("Rs " + (!TextUtils.isEmpty(productOnOutlet.get(position).getDiscountPrice())?productOnOutlet.get(position).getDiscountPrice():""));
            holder.discount.setText(""+0);
            holder.checkbox.setImageResource(R.drawable.ic_check_normal);
        }else{
            holder.mrp.setText("Rs " + productOnOutlet.get(position).getMrp());
            holder.finalPrice.setText("Rs " + productOnOutlet.get(position).getDiscountPrice());
            holder.discount.setText(""+productOnOutlet.get(position).getDiscount());
            holder.checkbox.setImageResource(R.drawable.ic_check_selected);

        }

        if (!TextUtils.isEmpty(productOnOutlet.get(position).getProductImages())){
            String imagePath = productOnOutlet.get(position).getProductImages();
//            Glide.with(context)
//                    .load(user.getImage())
//                    .addListener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            return false;
//                        }
//                    }).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)
//                    .error(R.drawable.place_holder_img)
//
//                    .fitCenter() // scale to fit entire image within ImageView
//                    .into(holder.productImage);
        }




        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (TextUtils.isEmpty(productOnOutlet.get(position).getAvailability())){
                     itemClickListener.updateAvailable(productOnOutlet.get(position),"1");
                 }else{
                     itemClickListener.updateAvailable(productOnOutlet.get(position),"0");
                 }
            }
        });

        holder.editproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onEdit(productOnOutlet.get(position));


            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return productOnOutlet.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImage,checkbox,editproduct;

        TextView  mrp,productName, discount,finalPrice;

        ViewHolder(View itemView) {
            super(itemView);


            productImage = itemView.findViewById(R.id.imagePlaceHolder);
            checkbox = itemView.findViewById(R.id.checkbox);
            mrp = itemView.findViewById(R.id.mrp);
            productName = itemView.findViewById(R.id.productName);
            discount = itemView.findViewById(R.id.discount);
            finalPrice = itemView.findViewById(R.id.finalPrice);
            editproduct = itemView.findViewById(R.id.editProduct);

        }
    }

    // allows clicks events to be caught
    void setClickListener(ProductAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void updateAvailable(ProductOnOutlet productOnOutlet, String available);
        void onEdit(ProductOnOutlet productOnOutlet);
    }
}
