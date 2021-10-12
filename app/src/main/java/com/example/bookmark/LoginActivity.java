package com.example.bookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void onLogin(View view) {
        username = findViewById(R.id.username);
        String txt_username = username.getText().toString();
        password = findViewById(R.id.password);
        String txt_password = password.getText().toString();
        if (txt_username.isEmpty() || txt_password.isEmpty()) {
            Toast.makeText(this, "Please enter credentials!", Toast.LENGTH_LONG).show();
        } else {
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("loginDetails.csv"), StandardCharsets.UTF_8));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (!isLineCorrect(tokens)) {
                        Toast.makeText(this, "Wrong input format in target file", Toast.LENGTH_LONG).show();
                        break;
                    }
                    User preDefinedUser = new User(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
                    User inputUser = new User(txt_username, txt_password);
                    if (inputUser.isSame(preDefinedUser)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("USER", preDefinedUser);
                        startActivity(intent);
                        break;
                    }
                }
                bufferedReader.close();
            } catch (IOException e) {
                Toast.makeText(this, "Failed to find the target file.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public boolean isLineCorrect(String[] line) {
        if (line.length != 3) {
            return false;
        }
        char[] charArray = line[0].toCharArray();
        for (Character c : charArray) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}