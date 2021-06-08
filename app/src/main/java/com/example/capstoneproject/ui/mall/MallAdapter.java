package com.example.capstoneproject.ui.mall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MallAdapter extends RecyclerView.Adapter<MallAdapter.MallViewHolder> {
    private ArrayList<MallModel> dataList;
    private List<MallModel> mMallListFull;
    private OnItemClickListener mListener;

    public Filter getFilter() {
        return mallFilter;
    }

    private Filter mallFilter = new Filter() {
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
            dataList.clear();
            dataList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MallViewHolder extends RecyclerView.ViewHolder {
        public TextView mMallName;
        public TextView mRating;
        public TextView mCarCapacity;
        public TextView mMotorCapacity;

//        public TextView mDistance;
//        public TextView mStatus;

        public MallViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mMallName = itemView.findViewById(R.id.list_name);
            mRating = itemView.findViewById(R.id.list_rating);
            mCarCapacity = itemView.findViewById(R.id.list_car_status);
            mMotorCapacity = itemView.findViewById(R.id.list_motor_status);

//            mDistance = itemView.findViewById(R.id.distance);
//            mStatus = itemView.findViewById(R.id.open_close);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public MallAdapter(ArrayList<MallModel> exampleList) {
        dataList = exampleList;
    }

    @Override
    public MallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MallViewHolder mvh = new MallViewHolder(v, mListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MallViewHolder holder, int position) {
        MallModel currentItem = dataList.get(position);

        holder.mMallName.setText(currentItem.getmName());
        holder.mRating.setText(currentItem.getmRating());
        holder.mCarCapacity.setText(currentItem.getmCarCapacity());
        holder.mMotorCapacity.setText(currentItem.getmMotorCapacity());

//        holder.mDistance.setText(currentItem.getmDistance());
//        holder.mStatus.setText(currentItem.getmStatus());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}