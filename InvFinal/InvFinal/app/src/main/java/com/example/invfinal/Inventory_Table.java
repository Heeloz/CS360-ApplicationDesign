package com.example.invfinal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Inventory_Table extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_buton;
    ImageView empty_imageview;
    TextView no_data;
    private InvDB myDB;
    private Item item;
    private ArrayList<Item> listOfItems;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reclyehelper);
        recyclerView = findViewById(R.id.recycleView);
        add_buton = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);

        add_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory_Table.this, Add_Item.class);
                startActivity(intent);
            }
        });

        myDB = new InvDB(Inventory_Table.this);
        listOfItems = new ArrayList<>();
        storeData();
        customAdapter = new CustomAdapter(Inventory_Table.this, this, listOfItems);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Inventory_Table.this));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeData() {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
//            Toast.makeText(this, "No data.", Toast.LENGTH_LONG).show();
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while(cursor.moveToNext()) {
                item = new Item(
                        cursor.getString(1), // name
                        cursor.getString(2), // item number
                        cursor.getString(3), // qty
                        Long.valueOf(cursor.getString(0)).longValue()
                        );
                listOfItems.add(item);
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            cursor.close();
        }
    }

}