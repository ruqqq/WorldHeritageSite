package sg.ruqqq.WHSFinder;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class DetailViewActivity extends TabActivity {
	// For storing the place data
	Place p;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.detailview);

	    // Get data passed from Intent
        Intent i = getIntent();
        p = new Place(i.getStringExtra("name"), i.getStringExtra("description"), i.getStringExtra("imageUrl"), i.getStringExtra("url"), i.getStringExtra("address"), i.getDoubleExtra("latitude", 0), i.getDoubleExtra("longitude", 0));

        // Set the Title
        ((TextView) findViewById(R.id.pName)).setText(p.getName());
        
        // Set titlebar home button
        ((ImageButton) findViewById(R.id.btnHome)).setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// on click, go back our List activity
				Intent iH = new Intent(getBaseContext(), WHSListActivity.class);
				startActivity(iH);
			}
        });
        
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent iTab;  // Reusable Intent for each tab
	    TextView txtTabInfo; // Reusable tab indicator
	    
	    // Get TabWidget so we can set the Divider
	    TabWidget tabWidget = this.getTabWidget();
	    tabWidget.setDividerDrawable(R.drawable.minitab_divider);

	    // Create an Intent to launch an Activity for the tab
	    iTab = new Intent().setClass(this, PlaceDetailsActivity.class);
	    iTab.putExtra("name", p.getName());
	    iTab.putExtra("description", p.getDescription());
	    iTab.putExtra("imageUrl", p.getImageUrl());
	    iTab.putExtra("url", p.getUrl());
	    iTab.putExtra("address", p.getAddress());
	    iTab.putExtra("latitude", p.getLatitude());
	    iTab.putExtra("longitude", p.getLongitude());

	    // Create our tab indicator
	    txtTabInfo = new TextView(this);
	    txtTabInfo.setText("Info");
	    txtTabInfo.setTextSize(16);
	    txtTabInfo.setTypeface(Typeface.DEFAULT_BOLD);
	    txtTabInfo.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
	    txtTabInfo.setBackgroundResource(R.drawable.minitab);
	    txtTabInfo.setMinHeight(55);
	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("info").setIndicator(txtTabInfo)
	                  .setContent(iTab);
	    tabHost.addTab(spec);
	    
	    /*
	     * Do the same fo FeedbackActivity below
	     */
	    
	    iTab = new Intent().setClass(this, FeedbackActivity.class);
	    iTab.putExtra("name", p.getName());
	    iTab.putExtra("description", p.getDescription());
	    iTab.putExtra("imageUrl", p.getImageUrl());
	    iTab.putExtra("url", p.getUrl());
	    iTab.putExtra("address", p.getAddress());
	    iTab.putExtra("latitude", p.getLatitude());
	    iTab.putExtra("longitude", p.getLongitude());
	    
	    txtTabInfo = new TextView(this);
	    txtTabInfo.setText("Feedback");
	    txtTabInfo.setTextSize(16);
	    txtTabInfo.setTypeface(Typeface.DEFAULT_BOLD);
	    txtTabInfo.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
	    txtTabInfo.setBackgroundResource(R.drawable.minitab);
	    txtTabInfo.setMinHeight(55);

	    spec = tabHost.newTabSpec("feedback").setIndicator(txtTabInfo)
	                  .setContent(iTab);
	    tabHost.addTab(spec);
	    
	    
	    // get the intent extras and switch to the corresponding tab that's being requested
	    if (i.getStringExtra("setTab").contentEquals("feedback"))
	    	tabHost.setCurrentTab(1);
	    else
	    	tabHost.setCurrentTab(0);
	}
}
