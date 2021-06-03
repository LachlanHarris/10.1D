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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Food> foodList;
    private Context context;
    private OnRowClickListener listener;

    public RecyclerViewAdapter(List<Food> foodList, Context context, OnRowClickListener listener) {
        this.foodList = foodList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.food_row, parent, false);

        return new ViewHolder(itemView, listener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.foodImage.setImageURI(foodList.get(position).getImage());
        holder.foodTitle.setText(foodList.get(position).getTitle());
        holder.foodDescription.setText(foodList.get(position).getDescription());
        holder.foodLocation.setText(foodList.get(position).getLocation());
        holder.foodQuantity.setText(String.valueOf(foodList.get(position).getQuantity()));
        //changing the date type to a string, this message is mainly a reminder in case this starts throwing errors
        // maybe also use this date field to combine the date and time once the user enters it, im unsure as of yet come back to this
        holder.foodDate.setText(foodList.get(position).getDate().toString());

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView foodImage;
        TextView foodTitle;
        TextView foodDescription;
        TextView foodLocation;
        TextView foodQuantity;
        TextView foodDate;

        public OnRowClickListener onRowClickListener;

        public ViewHolder(@NonNull View itemView, OnRowClickListener onRowClickListener) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodTitle = itemView.findViewById(R.id.foodTitle);
            foodDescription = itemView.findViewById(R.id.foodDescription);
            foodLocation = itemView.findViewById(R.id.foodLocation);
            foodQuantity = itemView.findViewById(R.id.foodQuantity);
            foodDate = itemView.findViewById(R.id.foodDate);

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
