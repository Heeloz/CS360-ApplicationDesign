package com.example.invfinal;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button newUserButton;
    private Button logInButton;
    private LogDB mDb;
    private EditText userName;
    private EditText passWord;
    private String userNameString, passWordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.userName);

        passWord = (EditText) findViewById(R.id.passwordInput);
        mDb = LogDB.getInstance(getApplicationContext());
        newUserButton = (Button) findViewById(R.id.createNewUser);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), newUserActivity.class);
                startActivity(intent);
            }
        });

        logInButton = (Button) findViewById(R.id.loginButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                userNameString = userName.getText().toString();
                passWordString = passWord.getText().toString();
                Log.i("name", userNameString);
                Boolean validation = mDb.getUser(userNameString);
                // if made be false
                if(!validation) {
                    Boolean validLogin = mDb.validateLogin(userNameString, passWordString);
                    if(validLogin) {
                        Intent intent = new Intent(view.getContext(), Inventory_Table.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,"User name doesn't exist",Toast.LENGTH_LONG).show();
                }
                Log.i("varCheck", String.valueOf(validation));
            }
        });

    }

}