package com.example.capstoneproject.ui.mall;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.model.Mall;
import com.example.capstoneproject.utils.ViewUtilsKt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MallAdapter extends RecyclerView.Adapter<MallAdapter.MallViewHolder> {
    private ArrayList<Mall> dataList;
    private List<Mall> mMallListFull;
    private OnItemClickListener mListener;

    /*public Filter getFilter() {
        return mallFilter;
    }*/

    /*private Filter mallFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MallModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mMallListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (MallModel item : mMallListFull) {
                    if (item.getmRating().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.values != null){
                dataList.clear();
                dataList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };*/


    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MallViewHolder extends RecyclerView.ViewHolder {
        public TextView mMallName;
        public TextView mRating;
        public TextView mCarAvailable;
        public TextView mMotorAvailable;
        public OnItemClickListener onItemClickListener;

//        public TextView mDistance;
//        public TextView mStatus;

        public MallViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mMallName = itemView.findViewById(R.id.list_name);
            mRating = itemView.findViewById(R.id.list_rating);
            mCarAvailable = itemView.findViewById(R.id.list_car_status);
            mMotorAvailable = itemView.findViewById(R.id.list_motor_status);
            onItemClickListener = listener;

//            mDistance = itemView.findViewById(R.id.distance);
//            mStatus = itemView.findViewById(R.id.open_close);
        }

        public void bind(Mall mall){
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(mall.getId());
                }
            });

            mMallName.setText(mall.getName());
            String rating = String.format(Locale.getDefault(), "%.1f", mall.getRating()) + "/5.0";
            mRating.setText(rating);
            SpannableStringBuilder carAvailable;
            if(mall.isCarFull()){
                carAvailable = ViewUtilsKt.getSpannableString("Full", itemView.getContext(), R.color.colorRed);
            }else{
                String value = mall.getCarCapacity() - mall.getCarOccupied() + " Available";
                carAvailable = ViewUtilsKt.getSpannableString(value, itemView.getContext(), R.color.colorGreen);
            }
            mCarAvailable.setText(carAvailable);
            SpannableStringBuilder motorcycleAvailable;
            if(mall.isMotorcycleFull()){
                motorcycleAvailable = ViewUtilsKt.getSpannableString("Full", itemView.getContext(), R.color.colorRed);
            }else{
                String value = mall.getMotorcycleCapacity() - mall.getMotorcycleOccupied() + " Available";
                motorcycleAvailable = ViewUtilsKt.getSpannableString(value, itemView.getContext(), R.color.colorGreen);
            }
            mMotorAvailable.setText(motorcycleAvailable);
        }
    }

    public MallAdapter(ArrayList<Mall> exampleList) {
        dataList = exampleList;
    }

    @Override
    public MallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mall, parent, false);
        MallViewHolder mvh = new MallViewHolder(v, mListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MallViewHolder holder, int position) {
        Mall currentItem = dataList.get(position);
        holder.bind(currentItem);

        /*holder.mDistance.setText(currentItem.getmDistance());
        holder.mStatus.setText(currentItem.getmStatus());*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setData(List<Mall> malls){
        dataList.clear();
        dataList.addAll(malls);
        notifyDataSetChanged();
    }
}