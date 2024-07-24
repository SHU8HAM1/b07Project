package com.example.b07Project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;



public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private List<Item> itemList;
    private HashSet<Integer> selectedItems;

    public ItemAdapter(List<Item> dataList) {
        this.itemList = dataList;
        this.selectedItems = new HashSet<Integer>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView lotNum, name, category, period, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            lotNum = itemView.findViewById(R.id.lotNum);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            period = itemView.findViewById(R.id.period);
            description = itemView.findViewById(R.id.description);
        }
    }


    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item data = itemList.get(position);
        holder.lotNum.setText(Integer.toString(data.getLotNum()));
        holder.name.setText(data.getName());
        holder.category.setText(data.getCategory());
        holder.period.setText(data.getPeriod());
        holder.description.setText(data.getDescription());
        holder.checkBox.setChecked(selectedItems.contains(position));
        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                selectedItems.add(position);
            } else {
                selectedItems.remove(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<Item> getSelectedItems() {
        List<Item> selectedItemsList = new ArrayList<>();
        for (Integer index : selectedItems) {
            selectedItemsList.add(itemList.get(index));
        }
        return selectedItemsList;
    }
}
