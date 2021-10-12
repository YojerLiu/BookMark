package com.example.bookmark;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> links;
    private ArrayAdapter<String> adapter;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        User user = (User) getIntent().getExtras().getSerializable("USER");

        links = new ArrayList<>();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.data), StandardCharsets.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split("<");
                if (tokens[0].equals(user.getUsername())) {
                    links.clear();
                    links.addAll(Arrays.asList(tokens).subList(1, tokens.length));
                    break;
                }
            }
            bufferedReader.close();
        } catch (Resources.NotFoundException | IOException e) {
            Toast.makeText(this, "Failed to find the target file.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        //links.add("http://www.anu.edu.au/");
        //links.add("http://www.google.com/");
        Button button = findViewById(R.id.button);
        editText = findViewById(R.id.edit_view);
        ListView listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, links);
        listView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = editText.getText().toString();
                if (link.equals("")) {
                    Toast.makeText(MainActivity.this, "URL is invalid!", Toast.LENGTH_LONG).show();
                } else if (links.contains(link)) {
                    Toast.makeText(MainActivity.this, "Repeated URL!", Toast.LENGTH_LONG).show();
                } else {
                    links.add(link);
                    editText.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ActivityWeb.class);
                intent.putExtra("URL", links.get(position));
                startActivity(intent);
            }
        });
    }
}