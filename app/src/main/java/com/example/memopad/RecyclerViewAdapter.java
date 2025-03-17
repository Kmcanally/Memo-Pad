package com.example.memopad;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.memopad.databinding.MemoItemBinding;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Memo> data;
    private MainActivity main;
    public RecyclerViewAdapter(MainActivity activity, List<Memo> data) {
        this.data = data;
        this.main = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MemoItemBinding binding = MemoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(main.getItemClick());
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMemo(data.get(position));
        holder.bindData();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public Memo getMemo(int position){
        Memo memo = data.get(position);
        return memo;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Memo memo;
        private TextView memoHolderView;
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public Memo getMemo() {
            return memo;
        }
        public void setMemo(Memo memo) {
            this.memo = memo;
        }
        public void bindData() {
            if (memoHolderView == null) {
                memoHolderView = (TextView) itemView.findViewById(R.id.memoHolderView);
            }

            memoHolderView.setText(memo.getMemo());
        }
    }
}