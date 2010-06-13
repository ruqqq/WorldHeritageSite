package sg.ruqqq.WHSFinder;

import java.net.MalformedURLException;

import sg.ruqqq.WHSFinder.ImageThreadLoader.ImageLoadedListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceDetailsActivity extends Activity {
	// Declare constants for OptionMenu
	static final private int MENU_WIKI = Menu.FIRST;
	
	// For storing the place data
	Place p;
	
	// For lazy loading of thumbnails
    private ImageThreadLoader imageLoader = new ImageThreadLoader();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.placedetails);

	    // Get data passed from Intent
        Intent i = getIntent();
        p = new Place(i.getStringExtra("name"), i.getStringExtra("description"), i.getStringExtra("imageUrl"), i.getStringExtra("url"), i.getStringExtra("address"), i.getDoubleExtra("latitude", 0), i.getDoubleExtra("longitude", 0));
        
        ((TextView) findViewById(R.id.pDescription)).setText(p.getDescription());
        ((TextView) findViewById(R.id.pUrl)).setText(getResources().getString(R.string.source)+p.getUrl()); 
        
        // Get our ImageView so we can put the picture of the place
        final ImageView vImage = (ImageView) findViewById(R.id.pImage);
        
        // Try to get the picture from the URL stored in p
        // Then, set it to the vImage
        Bitmap cachedImage = null;
        try {
          cachedImage = imageLoader.loadImage(p.getImageUrl(), new ImageLoadedListener() {
              public void imageLoaded(Bitmap imageBitmap) {
            	  vImage.setImageBitmap(imageBitmap);
            	  Log.d("C345Assignment1", "Image successfully loaded: "+imageBitmap);
              }
          });
        } catch (MalformedURLException e) {
        	Log.e("C345Assignment1", "Bad remote image URL: " + p.getImageUrl(), e);
        }

        if( cachedImage != null ) {
        	vImage.setImageBitmap(cachedImage);
        }
	}
	
	// Create Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    // Create and add our menu items
	    MenuItem itemWiki = menu.add(0, MENU_WIKI, Menu.NONE, R.string.menu_go_to_wiki);
	    
	    // Set their icons
	    itemWiki.setIcon(R.drawable.ic_menu_goto);
	    
	    return true;
    }
    
    // Handles Option Menu Selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);
	    switch (item.getItemId()) {
	    	case (MENU_WIKI):
	    		// start the chooser to go to the wiki page
	    		Intent iW = new Intent(Intent.ACTION_VIEW, Uri.parse(p.getUrl()));
				startActivity(Intent.createChooser(iW, getResources().getString(R.string.chooser_go_to_wiki)));
	    		break;
	    }
	    
	    return true;
    }
}
