package com.example.a101d.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a101d.R;
import com.example.a101d.model.Food;

import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder>{

    private List<Food> foodList;
    private Context context;
    private OnRowClickListener listener;

    public CartRecyclerViewAdapter(List<Food> foodList, Context context, OnRowClickListener listener) {
        this.foodList = foodList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_row, parent, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.cart_rowTitle.setText(foodList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cart_rowTitle;


        public OnRowClickListener onRowClickListener;

        public ViewHolder(@NonNull View itemView, OnRowClickListener onRowClickListener) {
            super(itemView);
            cart_rowTitle = itemView.findViewById(R.id.cart_rowTitle);
            this.onRowClickListener = onRowClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRowClickListener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnRowClickListener {
        void onItemClick(int position);
    }
}
