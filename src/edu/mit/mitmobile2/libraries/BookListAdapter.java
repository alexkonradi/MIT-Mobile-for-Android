package edu.mit.mitmobile2.libraries;

import java.util.List;

import android.content.Context;
import android.view.View;
import edu.mit.mitmobile2.SimpleArrayAdapter;
import edu.mit.mitmobile2.TwoLineActionRow;

public class BookListAdapter extends SimpleArrayAdapter<BookItem> {
    
    public BookListAdapter(Context context, List<BookItem> items, int rowResourceId) {
        super(context, items, rowResourceId);
    }
    
    public void updateView(BookItem book, View view) {          
        TwoLineActionRow twoLineActionRow = (TwoLineActionRow) view;
        twoLineActionRow.setTitle(book.title);
        twoLineActionRow.setSubtitle(book.getAuthorsDisplayString());
    }


}