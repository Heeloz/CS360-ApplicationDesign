package com.example.invfinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Item> itemList;

    CustomAdapter(Activity activity, Context context, ArrayList itemList) {
        this.activity = activity;
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inventory_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(itemList.get(position), position);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, update_item.class);
                intent.putExtra("id", String.valueOf(itemList.get(position).getmItemId()));
                intent.putExtra("item_name", String.valueOf(itemList.get(position).getmItem_name()));
                intent.putExtra("item_number", String.valueOf(itemList.get(position).getmItem_number()));
                intent.putExtra("item_qty", String.valueOf(itemList.get(position).getmItem_qty()));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Item mItem;
        TextView item_id, item_name, item_number, item_qty;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_id = itemView.findViewById(R.id.item_id);
            item_name = itemView.findViewById(R.id.item_name);
            item_number = itemView.findViewById(R.id.item_number);
            item_qty = itemView.findViewById(R.id.item_qty);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }

        public void bind(Item item, int position) {
            mItem = item;
            item_id.setText(String.valueOf(item.getmItemId()));
            item_name.setText(item.getmItem_name());
            item_number.setText(item.getmItem_number());
            item_qty.setText(item.getmItem_qty());
        }
    }
}
