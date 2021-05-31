package com.example.capstoneproject.ui.mall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;

import java.util.ArrayList;

public class MallAdapter extends RecyclerView.Adapter<MallAdapter.MallViewHolder> {
    private ArrayList<MallModel> dataList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MallViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;

        public MallViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);
            mTextView4 = itemView.findViewById(R.id.textView4);

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

        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        holder.mTextView3.setText(currentItem.getText3());
        holder.mTextView4.setText(currentItem.getText4());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}