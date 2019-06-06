package com.example.lab2_omelchenko;

import android.content.Context;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Helper
{
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

    Map<String, Map<String, ArrayList<String>>> library;
    Map<String, ArrayList<String>> years;
    ArrayList<String> books;

    Map<String, Map<String, ArrayList<String>>> getLibrary()
    {
        library = new HashMap();

        //J.K.Rowling
        years = new HashMap();
        books = new ArrayList();
        books.add("Harry Potter and the Chamber of Secrets");
        years.put("1998", books);

        books = new ArrayList();
        books.add("Harry Potter and the Half-Blood Prince");
        years.put("2005", books);

        books = new ArrayList();
        books.add("Harry Potter and the Deathly Hallows");
        years.put("2007", books);

        library.put("J. K. Rowling", years);

        //J. R. R. Tolkien
        years = new HashMap();
        books = new ArrayList();
        books.add("The Lord of the Rings");
        years.put("1954", books);

        library.put("J. R. R. Tolkien", years);

        //George R. R. Martin
        years = new HashMap();
        books = new ArrayList();
        books.add("A Clash of Kings");
        books.add("The Hedge Knight");
        years.put("1998", books);

        books = new ArrayList();
        books.add("A Feast for Crows");
        years.put("2005", books);

        books = new ArrayList();
        books.add("Hunter's Run");
        years.put("2007", books);

        library.put("George R. R. Martin", years);

        //Stephen King
        years = new HashMap();
        books = new ArrayList();
        books.add("It");
        years.put("1986", books);

        library.put("Stephen King", years);

        //Charles Dickens
        years = new HashMap();
        books = new ArrayList();
        books.add("A Christmas Carol");
        years.put("1843", books);

        books = new ArrayList();
        books.add("Oliver Twist");
        years.put("1839", books);

        library.put("Charles Dickens", years);

        //William Shakespeare
        years = new HashMap();
        books = new ArrayList();
        books.add("Hamlet");
        years.put("1609", books);

        books = new ArrayList();
        books.add("Romeo and Juliet");
        years.put("1597", books);

        library.put("William Shakespeare", years);

        return library;
    }
}
