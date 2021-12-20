package com.example.invfinal;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class newUserActivity extends AppCompatActivity {
    private Button returnButton;
    private Button createNewUser;
    private EditText createLoginName;
    private EditText createPassword;
    private EditText createConfirmPassword;
    private LogDB mDb;
    private String createPassString;
    private String createLoginNameString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_creation);
        createConfirmPassword = (EditText) findViewById(R.id.newUserLoginPasswordConfirm);
        createLoginName = (EditText) findViewById(R.id.newUserLoginName);
        returnButton = (Button) findViewById(R.id.returnToHomeScreen);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("buttonClick","return");
                returnToMain();
            }
        });
        createNewUser = (Button) findViewById(R.id.newUserCreate);
        createNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mDb = new LogDB(newUserActivity.this);
               Boolean userNameVerify =  verifyUserName();
               Boolean passWordVerify = verifyPasswords();
               if(userNameVerify && passWordVerify) {
                   Boolean createUserBool = mDb.createUser(createLoginNameString, createPassString);
                   if(createUserBool){
                       Toast.makeText(newUserActivity.this,"Successfully created user", Toast.LENGTH_LONG).show();
                   } else {
                       Toast.makeText(newUserActivity.this,"Failed to created user", Toast.LENGTH_LONG).show();
                   }
               } else {
                   Log.i("failedpass", "Pass and user not desirable");
               }
            }
        });
    }

    public void returnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }
    public Boolean verifyPasswords() {
        createPassword = (EditText) findViewById(R.id.newUserLoginPassword);
        createPassString = createPassword.getText().toString();
        String createConfirmPasswordString = createConfirmPassword.getText().toString();
        Log.i("pass", createPassString);
        Log.i("pass", createConfirmPasswordString);
        Log.i("compPass", String.valueOf(createPassString.equals(createConfirmPasswordString)));
        if(Boolean.valueOf(String.valueOf(createPassString.equals(createConfirmPasswordString)))) {
            return true;
        }
        Toast.makeText(newUserActivity.this, "Ensure passwords match", Toast.LENGTH_LONG).show();
        return false;
    }

    public Boolean verifyUserName() {
        createLoginNameString = createLoginName.getText().toString();
        Log.i("name", createLoginNameString);
        Boolean verifyUser = mDb.getUser(createLoginNameString);

        if(createLoginNameString != "" && verifyUser) {
            return true;
        } else if(!verifyUser) {
            Toast.makeText(newUserActivity.this,"User name already in use", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(newUserActivity.this, "User name can't be blank", Toast.LENGTH_LONG).show();
        return false;
    }
}