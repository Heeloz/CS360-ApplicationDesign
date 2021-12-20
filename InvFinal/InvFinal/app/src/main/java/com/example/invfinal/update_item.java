package com.example.invfinal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class update_item extends AppCompatActivity {

    EditText item_name_input, item_number_input, item_qty_input;
    String id, item_name, item_number, item_qty;
    Button update_button, delete_button;
    private InvDB myDB;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_update);

        item_name_input = findViewById(R.id.item_name_input2);
        item_number_input = findViewById(R.id.item_number_input2);
        item_qty_input = findViewById(R.id.item_qty_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(item_name);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                myDB = new InvDB(update_item.this);
                item_name = item_name_input.getText().toString();
                item_number = item_number_input.getText().toString();
                item_qty = item_qty_input.getText().toString();
                myDB.updateItem(item_name, item_number, item_qty, id);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB = new InvDB(update_item.this);
                myDB.deleteOneRow(id);
            }
        });
    }







    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("item_name") &&
                getIntent().hasExtra("item_number") && getIntent().hasExtra("item_qty")){

            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            item_name = getIntent().getStringExtra("item_name");
            item_number = getIntent().getStringExtra("item_number");
            item_qty = getIntent().getStringExtra("item_qty");

            //Setting Intent Data
            item_name_input.setText(item_name);
            item_number_input.setText(item_number);
            item_qty_input.setText(item_qty);
            Log.d("stev", item_name+" "+item_number+" "+item_qty);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}
