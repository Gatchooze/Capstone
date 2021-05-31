package com.example.capstoneproject.ui.mall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.ui.dashboard.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MallAdapter extends RecyclerView.Adapter<MallAdapter.CustomViewHolder> {
//    public static final String DATA_MALL = "datamall";

    private static List<MallModel> dataList;
    private Context context;

//    public MallAdapter(Context context, List<MallModel> dataList){
//        this.context = context;
//        this.dataList = dataList;
//    }

    public MallAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;

        private ImageView mImage;

        final TextView mMallName,mRating, mCarStatus, mMotorStatus;
        final ImageView mIvRating, mIvCar,mIvMotor;
        final Button mButton;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            mImage = itemView.findViewById(R.id.image);
            mMallName = itemView.findViewById(R.id.mall_name_item);
            mIvRating = itemView.findViewById(R.id.iv_rating);
            mRating = itemView.findViewById(R.id.rating_item);
            mIvCar = itemView.findViewById(R.id.iv_car);
            mCarStatus = itemView.findViewById(R.id.car_status_item);
            mIvMotor = itemView.findViewById(R.id.iv_motor);
            mMotorStatus = itemView.findViewById(R.id.motor_status_item);
            mButton = itemView.findViewById(R.id.btn_activity);
        }
    }
}
