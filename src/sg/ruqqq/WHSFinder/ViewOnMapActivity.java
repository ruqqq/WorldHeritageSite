package sg.ruqqq.WHSFinder;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class ViewOnMapActivity extends MapActivity {
	// Declare constants for OptionMenu
	static final private int MENU_MYLOCATION = Menu.FIRST;
	static final private int MENU_MAPMODE = MENU_MYLOCATION + 1;
	
	// Our properties which stores the map and mapcontroller
	MapView vMap;
	MapController mMapController;
	// Class property to ease change of label
	MenuItem itemMapMode;
	
	// Stores the current selected place data (passed through intent extras)
	Place p;
	
	// The overlay layer for our Map to display the POI
	POIOverlay poiOverlay;
	
	// Our custom class for auto location update
	MyLocationOverlay myLocation;
	
	// Stores the user's current location
	GeoPoint myCurrentLocation;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        
        // Get data passed from Intent
        Intent i = getIntent();
        p = new Place(i.getStringExtra("name"), i.getStringExtra("description"), i.getStringExtra("imageUrl"), i.getStringExtra("url"), i.getStringExtra("address"), i.getDoubleExtra("latitude", 0), i.getDoubleExtra("longitude", 0));
        
        // Set TextViews values
        ((TextView) findViewById(R.id.pName)).setText(p.getName());
        
        // Set titlebar home button
        ((ImageButton) findViewById(R.id.btnHome)).setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// OnClick, go back our List activity
				Intent iH = new Intent(getBaseContext(), WHSListActivity.class);
				startActivity(iH);
			}
        });
        
        // Get titlebar WHS location button
        ((ImageButton) findViewById(R.id.btnGoWHSLocation)).setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// OnClick, go to our current location
				snapToWHSLocation();
			}
        });
        
        // Get Map and Map Controller
        vMap = (MapView) findViewById(R.id.mv);
        mMapController = vMap.getController();
        
        // Set map options (zoom level, controls, satellite view by default, street view on)
        mMapController.setZoom(14);
        vMap.setBuiltInZoomControls(true);
        vMap.setSatellite(true);
        vMap.setStreetView(true);
        
        // Set POI GeoPoint, Add Marker, Center to the point
        GeoPoint selGeoPoint = new GeoPoint((int)(p.getLatitude()*1E6), (int)(p.getLongitude()*1E6));
        poiOverlay = new POIOverlay(this.getResources().getDrawable(R.drawable.ic_location_pin), this);
        poiOverlay.addOverlay(new OverlayItem(selGeoPoint, p.getName(), p.getDescription()));
  	  	vMap.getOverlays().add(poiOverlay);
        mMapController.animateTo(selGeoPoint);
        
        // Overlay for current location
        // The Overlay class also handles auto updates of location
        // Note: MyLocationOverlay here is a custom class, look below for the class
        myLocation = new MyLocationOverlay(this, vMap);
        myLocation.enableMyLocation();
        vMap.getOverlays().add(myLocation);
        
        // Get initial location
        // Actually redundant, but just wanna show the Toast message ;)
        myLocation.scheduleLocationUpdate(0, 0);
    }
	
    @Override
    protected void onResume() {
    	super.onResume();
    	// OnResume, make sure the auto location update is re-enabled
    	if (myLocation != null)
    		myLocation.enableMyLocation();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	// OnPause, disable the auto location update so that battery life isn't used
    	myLocation.disableMyLocation();
    }
    
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void snapToMyLocation() {
		// if myCurrentLocation is already set, animate to the position, else, show a toast with friendly message
		if (myCurrentLocation != null) mMapController.animateTo(myCurrentLocation);
		else {
			Toast locationToast = Toast.makeText(this, R.string.finding_location, Toast.LENGTH_LONG);
	        locationToast.show();
		}
	}
	
	private void snapToWHSLocation() {
		// center map to our POI (WHS)
		mMapController.animateTo(new GeoPoint((int) (p.getLatitude()*1E6), (int) (p.getLongitude()*1E6)));
	}
	
    // Create Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    // Create and add our menu items
	    MenuItem itemMyLocation = menu.add(0, MENU_MYLOCATION, Menu.NONE, R.string.menu_my_location);
	    itemMapMode = menu.add(0, MENU_MAPMODE, Menu.NONE, R.string.menu_normal_mode);
	    
	    // Set their icons
	    itemMyLocation.setIcon(android.R.drawable.ic_menu_mylocation);
	    itemMapMode.setIcon(android.R.drawable.ic_menu_mapmode);
	    
	    return true;
    }
    
    // Handles Option Menu Selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);
	    switch (item.getItemId()) {
	    	case (MENU_MYLOCATION):
	    		// see the method for more info
	    		snapToMyLocation();
	    		break;
	    	case (MENU_MAPMODE):
	    		// Toggle Map Mode accordingly
	    		if (vMap.isSatellite()) {
	    			vMap.setSatellite(false);
	    			itemMapMode.setTitle(R.string.menu_satellite_mode);
	    		} else {
	    			vMap.setSatellite(true);
	    			itemMapMode.setTitle(R.string.menu_normal_mode);
	    		}
	            break;
	    }
	    
	    return true;
    }
    
    private void startDetailViewActivity(String tab) {
    	// More Info and Feedback are both same Activity as they're in a TabActivity
    	// this method is to abstract the starting of either of the activity to provide ease
    	Intent i = new Intent(getBaseContext(), DetailViewActivity.class);
		i.putExtra("name", p.getName());
		i.putExtra("description", p.getDescription());
		i.putExtra("imageUrl", p.getImageUrl());
		i.putExtra("url", p.getUrl());
		i.putExtra("address", p.getAddress());
		i.putExtra("latitude", p.getLatitude());
		i.putExtra("longitude", p.getLongitude());
		i.putExtra("setTab", tab);
    	startActivity(i);
    }
	
    // Extending Our own MyLocationOverlay class to allow auto updates of location
    // Reason for extending is because MyLocationOverlay by default gets our current location periodically
    // (it is also a LocationListener)
    // Hence, instead of wasting CPU cycles creating another LocationListener, we reuse MyLocationOverlay
    private class MyLocationOverlay extends com.google.android.maps.MyLocationOverlay {
    	// For storing our location manager
    	LocationManager myLocationManager;
    	// Store context in case we need to call Activity level methods
    	Context mContext;
    	
		public MyLocationOverlay(Context context, MapView mapView) {
			super(context, mapView);
			// Get the properties and store in our class
			mContext = context;
	        myLocationManager = (LocationManager) getSystemService(
	        	    Context.LOCATION_SERVICE);
		}
		
		@Override
		public void onLocationChanged(Location location) {
			super.onLocationChanged(location);
			
			// this method does the auto magic of updating our current location when it receives a reading
			// works whenever enableMyLocation is set
			// stops whenever disableMyLocation is set
			myCurrentLocation = new GeoPoint(
        		    (int)(location.getLatitude()*1E6),
        		    (int)(location.getLongitude()*1E6));
			
			Log.d("", "New Location: "+myCurrentLocation);
			
			// show toast whenever we get a location update
			Toast locationToast = Toast.makeText(mContext, R.string.location_updated, Toast.LENGTH_SHORT);
	        locationToast.show();
		}
		
		// method exist in case we need it (a different thread for updating location; not related to enableMyLocation)
		private void scheduleLocationUpdate(long minTime, float minDistance) {
			// Get Best Provider to use (GPS or Network)
			String bestProvider = myLocationManager.getBestProvider(new Criteria(), true);
	        myLocationManager.requestLocationUpdates(bestProvider, minTime, minDistance, this);
	        
	        // show toast whenever we're requesting location update
	        Toast locationToast = Toast.makeText(mContext, R.string.updating_location, Toast.LENGTH_LONG);
	        locationToast.show();
		}
		
		// to complement above method
		private void stopLocationUpdate() {
			myLocationManager.removeUpdates(this);
		}
    	
    }
    
	// POIOverlay class for displaying our POIs
	private class POIOverlay extends ItemizedOverlay<OverlayItem> {
		// for storing our POI items
		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		// just in case we need to call activity level methods
		Context mContext;
		// to store our pinpoint
		Drawable marker;
		
		public POIOverlay(Drawable defaultMarker, Context context) {
			  super(boundCenterBottom(defaultMarker));
			  // get the properties and store locally
			  mContext = context;
			  marker = defaultMarker;
		}
		
		public void addOverlay(OverlayItem overlay) {
			// add item to our overlay
		    mOverlays.add(overlay);
		    populate();
		}
		
		// needed methods
		@Override
		protected OverlayItem createItem(int i) {
		  return mOverlays.get(i);
		}
		@Override
		public int size() {
		  return mOverlays.size();
		}
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			boundCenterBottom(marker);
		}
			
		// Important method: Defines what happen when you tap on a POI
		@Override
		protected boolean onTap(int i) {
			// Get the X,Y coordinate of the selected item View
	    	Point xy = new Point();
	    	vMap.getProjection().toPixels(getItem(i).getPoint(), xy);
	    	Log.d("C345Assignment1", "x: "+xy.x+"; y: "+xy.y);
	    	Rect rect = new Rect(xy.x, xy.y+60, xy.x+vMap.getWidth(), xy.y+70);
			
	    	// Create QuickAction component (custom Popup for selection: QuickActionWindow.java)
	    	// The component require the position of the view calling it to know where to place the popup
	    	final QuickActionWindow qa = new QuickActionWindow(mContext, vMap, rect);
	    	qa.setTitle(p.getName()); // set QA title
	    	qa.setIcon(p.getImageUrl()); // set QA icon (loaded from internet)
	    	
	    	// if current location is already set, calculate distance and bearing, else, show friendly message instead
	    	if (myCurrentLocation != null) {
		    	float[] distance = new float[3];
		    	Location.distanceBetween(p.getLatitude(), p.getLongitude(), myCurrentLocation.getLatitudeE6()/1E6, myCurrentLocation.getLongitudeE6()/1E6, distance);
		    	qa.setText(((int)(distance[0]/1000))+getResources().getString(R.string.distance_bearing)+(((double)((int)(distance[1]*100)))/100)+"¡");
	    	} else {
	    		qa.setText(R.string.calculating_distance);
	    	}
	    	
	    	// Add "View" item and assign the listener on event it's being clicked
			qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_info_details), R.string.qa_more_info, new OnClickListener() {
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
			
			// Show the QuickAction popup after everything is initialized
			qa.show(xy.x);
			return true;
		}
	}
}
