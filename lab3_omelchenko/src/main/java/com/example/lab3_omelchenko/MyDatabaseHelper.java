package com.example.lab3_omelchenko;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public MyDatabaseHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table results ("
                + "id integer primary key autoincrement,"
                + "result text" + ");");

        db.execSQL("create table library ("
                + "id integer primary key autoincrement,"
                + "author text,"
                + "year text,"
                + "book text" + ");");

        db.execSQL("insert into library(author, year, book) values" +
                "('J. K. Rowling', '1998', 'Harry Potter and the Chamber of Secrets'), " +
                "('J. K. Rowling', '2005', 'Harry Potter and the Half-Blood Prince')," +
                "('J. K. Rowling', '2007', 'Harry Potter and the Deathly Hallows')," +
                "('J. R. R. Tolkien', '1954', 'The Lord of the Rings')," +
                "('George R. R. Martin', '1998', 'A Clash of Kings')," +
                "('George R. R. Martin', '1998', 'The Hedge Knight')," +
                "('George R. R. Martin', '2005', 'A Feast for Crows')," +
                "('George R. R. Martin', '2007', 'Hunters Run')," +
                "('Stephen King', '1986', 'It')," +
                "('Charles Dickens', '1843', 'A Christmas Carol')," +
                "('Charles Dickens', '1839', 'Oliver Twist')," +
                "('William Shakespeare', '1597', 'Romeo and Juliet')," +
                "('William Shakespeare', '1609', 'Hamlet')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
