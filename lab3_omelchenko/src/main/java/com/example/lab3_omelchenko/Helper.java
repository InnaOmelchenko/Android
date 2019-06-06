package com.example.lab3_omelchenko;

import android.content.Context;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Helper {
    Context context;

    Helper(Context ctx)
    {
        context = ctx;
    }

    SimpleExpandableListAdapter adapter;

    String[] groups = new String[] {"Authors"};
    String[] authors = new String[] {"J. K. Rowling", "J. R. R. Tolkien", "George R. R. Martin", "Stephen King", "Charles Dickens", "William Shakespeare"};

    ArrayList<Map<String, String>> groupData;
    ArrayList<ArrayList<Map<String, String>>> childData;
    ArrayList<Map<String, String>> childDataItem;

    Map<String, String> map;

    SimpleExpandableListAdapter getAdapter()
    {
        //формування першого рівня expandable list view
        groupData = new ArrayList<Map<String, String>>();
        for (String group : groups) {
            map = new HashMap<String, String>();
            map.put("groupName", group);
            groupData.add(map);
        }

        String groupFrom[] = new String[] {"groupName"};
        int groupTo[] = new int[] {android.R.id.text1};

        //формування другого рівня expandable list view
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        childDataItem = new ArrayList<Map<String, String>>();
        for (String author : authors) {
            map = new HashMap<String, String>();
            map.put("authorName", author);
            childDataItem.add(map);
        }

        childData.add(childDataItem);

        String childFrom[] = new String[] {"authorName"};
        int childTo[] = new int[] {android.R.id.text1};

        adapter = new SimpleExpandableListAdapter(
                context,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        return adapter;
    }
}
