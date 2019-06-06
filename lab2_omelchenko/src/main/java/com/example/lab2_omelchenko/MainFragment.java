package com.example.lab2_omelchenko;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainFragment extends Fragment {

    Helper helper;
    SimpleExpandableListAdapter adapter;

    int checked = -1;
    boolean isChecking = true;
    String author = "";

    TextView textView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, null);

        textView = v.findViewById(R.id.textView);

        helper = new Helper(getActivity());
        adapter = helper.getAdapter();

        ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.elv);
        elv.setAdapter(adapter);

        final RadioGroup first = v.findViewById(R.id.radioGroup1);
        final RadioGroup second = v.findViewById(R.id.radioGroup2);
        final RadioGroup third = v.findViewById(R.id.radioGroup3);

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


        Button button = v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(author.equals("") || getYear(checked).equals(""))
                {
                    Toast.makeText(getActivity(), "Author and year must be chosen to show results", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String str = "Author: " + author +
                            "\nYear: " + getYear(checked) + getBooks(author, getYear(checked));

                    ResultFragment resultFragment = new ResultFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("list", str);
                    resultFragment.setArguments(bundle);

                    resultFragment.show(getFragmentManager(), "resultFragment");
                }
            }
        });

        return v;
    }

    private String getYear(int checkedId)
    {
        if(checkedId == -1)
            return "";
        RadioButton radioButton = getActivity().findViewById(checkedId);
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
