package com.example.invfinal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add_Item extends AppCompatActivity {

    private EditText item_number_input, item_name_input, item_qty_input;
    private String item_number, item_name, item_qty;
    private Button add_button;
    private InvDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        item_number_input = findViewById(R.id.item_number_input);
        item_name_input = findViewById(R.id.item_name_input);
        item_qty_input = findViewById(R.id.item_qty_input);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB = new InvDB(Add_Item.this);
                item_number = item_number_input.getText().toString();
                item_name = item_name_input.getText().toString();
                item_qty = item_qty_input.getText().toString();
                Item item = new Item(item_name, item_number, item_qty);
                Log.i("itemname", item_name);
                Log.i("item_number", item_number);
                Log.i("item_qty", item_qty);
                Boolean newItem = myDB.createItem(item);
                if(newItem) {
                    Toast.makeText(Add_Item.this, "Added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
