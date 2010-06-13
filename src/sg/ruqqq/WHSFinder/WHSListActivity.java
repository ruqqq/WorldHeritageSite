package sg.ruqqq.WHSFinder;

import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class WHSListActivity extends ListActivity {
	// Declare constants for OptionMenu
	static final private int MENU_SORT = Menu.FIRST;
	static final private int MENU_ABOUT = MENU_SORT+1;
	static final private int MENU_VIEW = MENU_ABOUT+1;
	static final private int MENU_FEEDBACK = MENU_VIEW+1;
	
	// ArrayList to store our Place of Interest details
	ArrayList<Place> placesList = new ArrayList<Place>();
	// The ArrayAdapter associated with the list;
	PlaceAdapter aa;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whslist);
        
        // Get ListView widget
        ListView lv = getListView();
        
        // Add Places to arrayList
        // ...Data retrieved from Wikipedia
        // Hardcoded here instead of putting in arrays.xml
        placesList.add(new Place(
        		"Kinabalu National Park",
        		"Kinabalu National Park or Taman Negara Kinabalu in Malay, established as one of the first national parks of Malaysia in 1964, is Malaysia's first World Heritage Site designated by UNESCO in December 2000 for its “outstanding universal values” and the role as one of the most important biological sites in the world.",
        		"http://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Mount_kinabalu_01.png/300px-Mount_kinabalu_01.png",
        		"http://en.wikipedia.org/wiki/Kinabalu_Park",
        		"", 6.15, 116.65));
        placesList.add(new Place(
        		"Gunung Mulu National Park",
        		"Gunung Mulu National Park is famous for its limestone karst formations. Features include enormous caves, vast cave networks, rock pinnacles, cliffs and gorges.",
        		"http://upload.wikimedia.org/wikipedia/commons/d/d0/Api_Chamber.png",
        		"http://en.wikipedia.org/wiki/Gunung_Mulu_National_Park",
        		"", 4.05, 114.933333));
        placesList.add(new Place(
        		"Mada'in Saleh",
        		"In 2008, for its well-preserved remains from late antiquity, especially the 131 rock-cut monumental tombs, with their elaborately ornamented façades, of the Nabatean kingdom, UNESCO proclaimed Mada'in Saleh as a site of patrimony, becoming Saudi Arabia's first World Heritage Site.",
        		"http://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Thamudi.jpg/250px-Thamudi.jpg",
        		"http://en.wikipedia.org/wiki/Meda'in_Saleh",
        		"", 26.814167, 37.9475));
        placesList.add(new Place(
        		"Samarra",
        		"Medieval Islamic writers believed that the name “Samarra” is derived from the Arabic phrase “Sarr man ra’a”, which translates to “A joy for all who see”",
        		"http://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Great_Mosque_of_Samarra.jpg/210px-Great_Mosque_of_Samarra.jpg",
        		"http://en.wikipedia.org/wiki/Samarra",
        		"", 34.198333, 43.874167));
        placesList.add(new Place(
        		"Kadisha Valley",
        		"Kadisha means “Holy” in Aramaic, and the valley, sometimes called the Holy Valley, has sheltered Christian monastic communities for many centuries.",
        		"http://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Qadisha.jpg/300px-Qadisha.jpg",
        		"http://en.wikipedia.org/wiki/Kadisha_Valley",
        		"", 34.282944, 35.951622));
        placesList.add(new Place(
        		"Komodo National Park",
        		"The national park was founded in 1980 in order to protect the Komodo dragon, the world's largest lizard. Later it was dedicated to protecting other species, including marine species. In 1991 the national park was declared a UNESCO World Heritage Site.",
        		"http://upload.wikimedia.org/wikipedia/commons/thumb/0/00/Komodo-dragon-1.jpg/283px-Komodo-dragon-1.jpg",
        		"http://en.wikipedia.org/wiki/Komodo_National_Park",
        		"", -8.543333, 119.489444));
        placesList.add(new Place(
        		"Mount Tai Shan",
        		"Mount Tai is one of the “Five Sacred Mountains”. It is associated with sunrise, birth, and renewal, and is often regarded the foremost of the five. The temples on its slopes have been a destination for pilgrims for 3,000 years.",
        		"http://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Mount_tai_azure_clouds_temple.jpg/280px-Mount_tai_azure_clouds_temple.jpg",
        		"http://en.wikipedia.org/wiki/Mount_Taishan",
        		"", 36.25, 117.1));
        placesList.add(new Place(
        		"Sydney Opera House",
        		"Sydney Opera House is a multi-venue performing arts centre on Bennelong Point in Sydney, New South Wales, Australia. It was conceived and largely built by Danish architect Jørn Utzon, who, in 2003, received the Pritzker Prize, architecture's highest honour.",
        		"http://upload.wikimedia.org/wikipedia/commons/thumb/3/38/Sydney_opera_house_side_view.jpg/248px-Sydney_opera_house_side_view.jpg",
        		"http://en.wikipedia.org/wiki/Sydney_Opera_House",
        		"", -33.856944, 151.215278));
        placesList.add(new Place(
        		"Great Barrier Reef",
        		"The Great Barrier Reef is the world's largest reef system composed of over 2,900 individual reefs and 900 islands stretching for over 2,600 kilometres (1,600 mi) over an area of approximately 344,400 square kilometres (133,000 sq mi).",
        		"http://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/GreatBarrierReef-EO.JPG/230px-GreatBarrierReef-EO.JPG",
        		"http://en.wikipedia.org/wiki/Great_Barrier_Reef",
        		"", -18.286111, 147.7));
        placesList.add(new Place(
        		"Angkor",
        		"The temples of the Angkor area number over one thousand, ranging in scale from nondescript piles of brick rubble scattered through rice fields to the magnificent Angkor Wat, said to be the world's largest single religious monument.",
        		"http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Angkor_Wat.jpg/250px-Angkor_Wat.jpg",
        		"http://en.wikipedia.org/wiki/Angkor",
        		"", 13.433333, 103.833333));
        
        // Instantiate ArrayAdapter
        aa = new PlaceAdapter(this, R.layout.place_list_item, placesList);
        
        // Assign Adapter to ListView
        lv.setAdapter(aa);
        
        // Deprecated: Using QuickAction popup now
        // Enabled context menu for ListView
        // registerForContextMenu(lv);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	// Get the selected place data by retrieving based on the position clicked
    	final Place p = placesList.get(position);
    	Log.d("C345Assignment1", "Selected Place: "+p);
    	Log.d("C345Assignment1", "View: "+v);
    	
    	// Get the X,Y coordinate of the selected item View (for QuickAction to know where to popup)
    	int[] xy = new int[2];
    	v.getLocationInWindow(xy);
    	Log.d("C345Assignment1", "x: "+xy[0]+"; y: "+xy[1]);
    	Rect rect = new Rect(xy[0], xy[1], xy[0]+v.getWidth(), xy[1]+v.getHeight());
		
    	// Create QuickAction component (custom Popup for selection: QuickActionWindow.java)
    	// The component require the position of the view calling it to know where to place the popup
    	final QuickActionWindow qa = new QuickActionWindow(this, v, rect);
    	
    	// Add "View" item and assign the listener on event it's being clicked
		qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_mapmode), R.string.qa_map, new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), ViewOnMapActivity.class);
		    	i.putExtra("name", p.getName());
		    	i.putExtra("description", p.getDescription());
		    	i.putExtra("imageUrl", p.getImageUrl());
		    	i.putExtra("url", p.getUrl());
		    	i.putExtra("address", p.getAddress());
		    	i.putExtra("latitude", p.getLatitude());
		    	i.putExtra("longitude", p.getLongitude());
		    	startActivity(i);
		    	qa.dismiss();
			}
	    });
		
		// Add "View" item and assign the listener on event it's being clicked
		qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_info_details), R.string.qa_info, new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), DetailViewActivity.class);
		    	i.putExtra("name", p.getName());
		    	i.putExtra("description", p.getDescription());
		    	i.putExtra("imageUrl", p.getImageUrl());
		    	i.putExtra("url", p.getUrl());
		    	i.putExtra("address", p.getAddress());
		    	i.putExtra("latitude", p.getLatitude());
		    	i.putExtra("longitude", p.getLongitude());
		    	i.putExtra("setTab", "details");
		    	startActivity(i);
		    	qa.dismiss();
			}
	    });
		
		// Add "Feedback" item and assign the listener on event it's being clicked
		qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_agenda), R.string.qa_feedback, new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), DetailViewActivity.class);
		    	i.putExtra("name", p.getName());
		    	i.putExtra("description", p.getDescription());
		    	i.putExtra("imageUrl", p.getImageUrl());
		    	i.putExtra("url", p.getUrl());
		    	i.putExtra("address", p.getAddress());
		    	i.putExtra("latitude", p.getLatitude());
		    	i.putExtra("longitude", p.getLongitude());
		    	i.putExtra("setTab", "feedback");
		    	startActivity(i);
		    	qa.dismiss();
			}
	    });
		
		// Add "Wiki" item and assign the listener on event it's being clicked
		qa.addItem(getResources().getDrawable(R.drawable.ic_menu_goto), R.string.qa_wiki, new OnClickListener() {
			public void onClick(View v) {
				// Call up intent chooser in case user have more than one browser installed
				Intent iW = new Intent(Intent.ACTION_VIEW, Uri.parse(p.getUrl()));
				startActivity(Intent.createChooser(iW, getResources().getString(R.string.chooser_go_to_wiki)));
				qa.dismiss();
			}
	    });
		
		final int index = position;
		
		// Add "Delete" item and assign the listener on event it's being clicked
		qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_delete), R.string.qa_delete, new OnClickListener() {
			public void onClick(View v) {
				placesList.remove(index);
				aa.notifyDataSetChanged();
				qa.dismiss();
			}
	    });
		
		// Show the QuickAction popup after everything is initialized
		qa.show();
    }
    
    // Create Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    // Create and add our menu items
	    MenuItem itemSort = menu.add(0, MENU_SORT, Menu.NONE, R.string.menu_sort_az);
	    MenuItem itemAbout = menu.add(0, MENU_ABOUT, Menu.NONE, R.string.menu_about);
	    
	    // Set their icons
	    itemSort.setIcon(android.R.drawable.ic_menu_sort_alphabetically);
	    itemAbout.setIcon(android.R.drawable.ic_menu_info_details);
	    
	    return true;
    }
    
    // Handles Option Menu Selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);
	    switch (item.getItemId()) {
	    	case (MENU_SORT):
	    		// Perform the necessary task to sort the items in the ArrayList
	    		Collections.sort(placesList);
	    		aa.notifyDataSetChanged();
	    		break;
	    	case (MENU_ABOUT):
	    		// Show Popup about dialog
	    		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle(R.string.about);
				dialog.setMessage(R.string.about_text);
				dialog.show();
	            break;
	    }
	    
	    return true;
    }
    
    // Deprecated: Using custom QuickAction popup now
    /*
    // Create context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       super.onCreateContextMenu(menu, v, menuInfo);
       
       // Set the title for the context menu
       menu.setHeaderTitle("Actions");
       
       // Create the items and set their icons
       MenuItem view = menu.add(0, MENU_VIEW, Menu.NONE, "View");
       view.setIcon(android.R.drawable.ic_menu_view);
       MenuItem feedback = menu.add(0, MENU_FEEDBACK, Menu.NONE, "Write Feedback");
       feedback.setIcon(android.R.drawable.ic_menu_agenda);
    }
    
    // Handle context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
	    super.onContextItemSelected(item);
	    
	    // Get selected index
	    AdapterView.AdapterContextMenuInfo menuInfo;
		menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int index = menuInfo.position;
		
		Place p = placesList.get(index);
    	Log.d("C345Assignment1", "Selected Place: "+p);
		
	    switch (item.getItemId()) {
	    	case (MENU_VIEW):
		    	Intent iD = new Intent(getBaseContext(), ViewOnMapActivity.class);
		    	iD.putExtra("name", p.getName());
		    	iD.putExtra("description", p.getDescription());
		    	iD.putExtra("url", p.getUrl());
		    	iD.putExtra("address", p.getAddress());
		    	iD.putExtra("latitude", p.getLatitude());
		    	iD.putExtra("longitude", p.getLongitude());
		    	startActivity(iD);
	    		return true;
	    		
	    	case (MENU_FEEDBACK):
	    		Intent iF = new Intent(getBaseContext(), FeedbackActivity.class);
		    	iF.putExtra("name", p.getName());
		    	iF.putExtra("description", p.getDescription());
		    	iF.putExtra("url", p.getUrl());
		    	iF.putExtra("address", p.getAddress());
		    	iF.putExtra("latitude", p.getLatitude());
    			iF.putExtra("longitude", p.getLongitude());
		    	startActivity(iF);
	    		return true;
	    }
	    
	    return false;
    }*/
}