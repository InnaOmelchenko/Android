package com.example.lab1_omelchenko;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Helper helper;
    SimpleExpandableListAdapter adapter;

    final int RESULT_DIALOG = 1;

    RadioGroup first;
    RadioGroup second;
    RadioGroup third;

    int checked = -1;
    boolean isChecking = true;
    String author = "";

    ExpandableListView elv;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        helper = new Helper(this);
        adapter = helper.getAdapter();

        elv = (ExpandableListView) findViewById(R.id.elv);
        elv.setAdapter(adapter);

        first = findViewById(R.id.radioGroup1);
        second = findViewById(R.id.radioGroup2);
        third = findViewById(R.id.radioGroup3);

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

        // click on the element
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,   int childPosition, long id) {
                author = ((Map<String,String>)(adapter.getChild(groupPosition, childPosition))).get("authorName");
                textView.setText(author);
                return false;
            }
        });
    }

    public void onclick(View v)
    {
        switch (v.getId())
        {
            case R.id.button:
                if(author.equals("") || getYear(checked).equals(""))
                {
                    Toast.makeText(this, "Author and year must be chosen to show results", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    showDialog(RESULT_DIALOG);
                }
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
                adb.setNeutralButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
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

    private String getBooks(String author, String year)
    {
        Map<String, Map<String, ArrayList<String>>> library = helper.getLibrary();

        ArrayList<String> books_by_author_by_year = library.get(author).get(year);

        //перетворення списку книжок в строку, яку потім передаємо в діалог
        String books_by_author_by_year_string = "";
        if(books_by_author_by_year == null)
        {
            books_by_author_by_year_string += "\nNo books found";
        }
        else
        {
            for (String elem : books_by_author_by_year) {
                books_by_author_by_year_string += ("\n" + elem);
            }
        }
        return books_by_author_by_year_string;
    }
}
