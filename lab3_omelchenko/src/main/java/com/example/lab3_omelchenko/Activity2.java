package com.example.lab3_omelchenko;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Intent intent = getIntent();
        int data_id = intent.getIntExtra("data_id", 1);

        ListView listView = findViewById(R.id.listView);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c;
        ArrayList<String> list = new ArrayList();

        switch (data_id)
        {
            case 1:
                c = db.query("library", null, null, null, null, null, null);

                if (c.moveToFirst()) {

                    int idColIndex = c.getColumnIndex("id");
                    int authorColIndex = c.getColumnIndex("author");
                    int yearColIndex = c.getColumnIndex("year");
                    int bookColIndex = c.getColumnIndex("book");

                    do {
                        list.add("ID: " + c.getInt(idColIndex) + " Author: " + c.getString(authorColIndex)
                                + " Year: " + c.getString(yearColIndex) + " Book: " + c.getString(bookColIndex));
                    } while (c.moveToNext());
                }
                else {
                    list.add("no books yet");
                }
                break;
            case 2:
                c = db.query("results", null, null, null, null, null, null);

                if (c.moveToFirst()) {

                    int idColIndex = c.getColumnIndex("id");
                    int resultColIndex = c.getColumnIndex("result");

                    do {
                        list.add("ID: " + c.getInt(idColIndex) + " " + c.getString(resultColIndex));
                    } while (c.moveToNext());
                }
                else {
                    list.add("no results yet");
                }
                break;
            default:
                c = null;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        c.close();
        dbHelper.close();
    }
}
