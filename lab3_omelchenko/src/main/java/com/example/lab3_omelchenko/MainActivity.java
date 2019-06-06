package com.example.lab3_omelchenko;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Helper helper;
    SimpleExpandableListAdapter adapter;

    final int RESULT_DIALOG = 1;

    int checked = -1;
    boolean isChecking = true;
    String author = "";

    ExpandableListView elv;

    TextView textView;
    MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this);

        textView = findViewById(R.id.textView);

        helper = new Helper(this);
        adapter = helper.getAdapter();

        elv = (ExpandableListView) findViewById(R.id.elv);
        elv.setAdapter(adapter);

        final RadioGroup first = findViewById(R.id.radioGroup1);
        final RadioGroup second = findViewById(R.id.radioGroup2);
        final RadioGroup third = findViewById(R.id.radioGroup3);

        first.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    second.clearCheck();
                    third.clearCheck();
                    checked = checkedId;
                    isChecking = true;
                }
            }
        });

        second.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    first.clearCheck();
                    third.clearCheck();
                    checked = checkedId;
                    isChecking = true;
                }
            }
        });

        third.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    first.clearCheck();
                    second.clearCheck();
                    checked = checkedId;
                    isChecking = true;
                }
            }
        });

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                author = ((Map<String,String>)(adapter.getChild(groupPosition, childPosition))).get("authorName");
                textView.setText(author);
                return false;
            }
        });
    }

    public void onclick(View v)
    {
        Intent intent;
        ContentValues cv = new ContentValues();
        db = dbHelper.getWritableDatabase();

        switch (v.getId())
        {
            case R.id.button:
                if(author.equals("") || getYear(checked).equals(""))
                {
                    Toast.makeText(this, "Author and year must be chosen to show results", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String str = "Author: " + author + "\nYear: " + getYear(checked) + getBooks(author, getYear(checked));
                    cv.put("result", str);

                    db.insert("results", null, cv);

                    showDialog(RESULT_DIALOG);
                }
                break;
            case R.id.show_library_button:
                intent = new Intent(this, Activity2.class);
                intent.putExtra("data_id", 1);
                startActivity(intent);
                break;
            case R.id.get_data_button:
                intent = new Intent(this, Activity2.class);
                intent.putExtra("data_id", 2);
                startActivity(intent);
                break;
            case R.id.erase_data_button:
                int num = db.delete("results", null, null);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "results" + "'");
                Toast.makeText(this, Integer.toString(num) + " rows deleted from the database", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    protected Dialog onCreateDialog(int id)
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch(id)
        {
            case 1:
                adb.setTitle("Results");
                adb.setMessage("");
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(MainActivity.this, "Log entered to the database", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return adb.create();
    }

    protected void onPrepareDialog(int id, Dialog dialog)
    {
        if(id == 1)
        {
            ((AlertDialog)dialog).setMessage("Author: " + author +
                    "\nYear: " + getYear(checked) + getBooks(author, getYear(checked)));
        }
    }

    private String getYear(int checkedId)
    {
        if(checkedId == -1)
            return "";
        RadioButton radioButton = findViewById(checkedId);
        return radioButton.getText().toString();
    }

    private String getBooks(String author_param, String year_param)
    {
        db = dbHelper.getWritableDatabase();
        Cursor c = null;
        String selection = "author = ? and year = ?";
        String[] selectionArgs = new String[] { author_param, year_param};

        c = db.query("library", null, selection, selectionArgs, null, null, null);

        String str = "";

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    str += ("\n" + c.getString(c.getColumnIndex("book")));
                } while (c.moveToNext());
            }
            c.close();
        }
        if(str.equals("")) {
            str += "\nNo books found";
        }

        return str;
    }
}
