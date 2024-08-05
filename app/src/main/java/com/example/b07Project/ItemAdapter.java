package com.example.b07project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;



public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private List<Item> itemList;
    private HashSet<Integer> expandedPositions;

    public ItemAdapter(List<Item> dataList) {
        this.itemList = dataList;
        this.expandedPositions = new HashSet<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lotNum, name, category, period, description;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lotNum = itemView.findViewById(R.id.lotNum);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            period = itemView.findViewById(R.id.period);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView2);
        }
    }


    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item data = itemList.get(position);
        holder.lotNum.setText("" + data.getLotNumber());
        holder.name.setText(data.getName());
        holder.category.setText(data.getCategory());
        holder.period.setText(data.getPeriod());
        holder.description.setText(data.getDescription());
        Picasso.get().load(data.getUri()).fit().centerCrop().into(holder.imageView);
        boolean isExpanded = expandedPositions.contains(position);
        if(isExpanded){
            holder.description.setMaxLines(Integer.MAX_VALUE);
        }else{
            holder.description.setMaxLines(6);
        }
        holder.itemView.setOnClickListener(v -> {
            if (isExpanded) {
                expandedPositions.remove(position);
            } else {
                expandedPositions.add(position);
            }
            notifyItemChanged(position);
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
