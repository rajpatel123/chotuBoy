package com.chotuboy.activity.ui.notifications;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chotuboy.R;
import com.chotuboy.modelClass.forOutLet.User;
import com.chotuboy.utils.ChotuBoyPrefs;
import com.google.gson.Gson;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    TextView nameTv,phonetv,addressTv,cityTv;
    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        nameTv = root.findViewById(R.id.outletName);
        phonetv = root.findViewById(R.id.phoneTv);
        addressTv = root.findViewById(R.id.addressTv);
        cityTv = root.findViewById(R.id.cityTv);
        imageView = root.findViewById(R.id.imageView);
        updateDetails();
        return root;
    }

    private void updateDetails() {
        if (!TextUtils.isEmpty(ChotuBoyPrefs.getString(getActivity(), "outletData"))) {
            User user = new Gson().fromJson(ChotuBoyPrefs.getString(getActivity(), "outletData"),User.class);
            nameTv.setText(user.getOutletName());
            phonetv.setText(user.getPhone());
            addressTv.setText(user.getAddress());
            cityTv.setText(user.getAssignedCity());

            if (!TextUtils.isEmpty(user.getImage())){
                Glide.with(getActivity())
                        .load(user.getImage())
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)
                        .error(R.drawable.place_holder_img)

                        .fitCenter() // scale to fit entire image within ImageView
                        .into(imageView);
            }

        }else{
            User user = new Gson().fromJson(ChotuBoyPrefs.getString(getActivity(), "deliveryData"),User.class);

        }
    }
}
