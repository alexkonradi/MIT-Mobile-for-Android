package edu.mit.mitmobile2.maps;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.mit.mitmobile2.Global;
import edu.mit.mitmobile2.Module;
import edu.mit.mitmobile2.ModuleActivity;
import edu.mit.mitmobile2.R;
import edu.mit.mitmobile2.SimpleArrayAdapter;
import edu.mit.mitmobile2.TitleBar;
import edu.mit.mitmobile2.TwoLineActionRow;
import edu.mit.mitmobile2.objs.MapCatItem;

public class MITMapBrowseSubCatsActivity extends ModuleActivity {

	static final String TAG = "MITMapBrowseSubCatsActivity";
	static final int MENU_BOOKMARKS  = MENU_SEARCH + 1;
	
	static final String CATEGORY_NAME_KEY = "category_name";
	private String mCategoryName;

	ArrayList<MapCatItem> mSubCats;
	List<String> subcategory_names;
	
	
	/****************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG,"onCreate()");
	
	    super.onCreate(savedInstanceState);
	    
    	Bundle extras = getIntent().getExtras();
        if (extras!=null){  
        	mCategoryName = extras.getString(CATEGORY_NAME_KEY);
        } 
        
        mSubCats = Global.curSubCats;
        
        if (mSubCats == null) {
        	// categories flushed from memory
        	finish();
        	return;
        }
	    
        createView();
		
	}
		

	void createView() {
		
		setContentView(R.layout.boring_list_layout);
		TitleBar titleBar = (TitleBar) findViewById(R.id.boringListTitleBar);
		titleBar.setTitle("Browse by " + mCategoryName);
		
		// hide loader
		findViewById(R.id.boringListLoader).setVisibility(View.GONE);
		
		ListView listView = (ListView) findViewById(R.id.boringListLV);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View row, int position, long id) {
				Intent i = new Intent(MITMapBrowseSubCatsActivity.this, MITMapBrowseResultsActivity.class); 
				i.putExtra(MITMapBrowseResultsActivity.KEY_CAT, mSubCats.get(position).categoryId);
				i.putExtra(MITMapBrowseResultsActivity.CATEGORY_NAME_KEY, mSubCats.get(position).categoryName);

				startActivity(i);
			}
		});
		
		listView.setVisibility(View.VISIBLE);
		ArrayAdapter<MapCatItem> subCategoriesAdapter = new SubCategoriesAdapter(this, mSubCats);
		listView.setAdapter(subCategoriesAdapter);		
	}
	
	private static class SubCategoriesAdapter extends SimpleArrayAdapter<MapCatItem> {

		public SubCategoriesAdapter(Context context, List<MapCatItem> items) {
			super(context, items, R.layout.boring_action_row);
		}

		@Override
		public void updateView(MapCatItem catItem, View view) {
			TwoLineActionRow row = (TwoLineActionRow) view;
			row.setTitle(catItem.categoryName);
		}
	}
	
	/****************************************************/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case MENU_BOOKMARKS: 
			Intent i = new Intent(this,MITMapBrowseResultsActivity.class);  
			startActivity(i);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void prepareActivityOptionsMenu(Menu menu) {
		menu.add(0, MENU_BOOKMARKS, Menu.NONE, "Bookmarks")
		  .setIcon(R.drawable.menu_bookmarks);
	}
	
	@Override
	protected Module getModule() {
		return new MapsModule();
	}

	@Override
	public boolean isModuleHomeActivity() {
		return false;
	}
}






