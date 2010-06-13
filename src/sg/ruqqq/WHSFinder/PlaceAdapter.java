package sg.ruqqq.WHSFinder;

import java.net.MalformedURLException;
import java.util.ArrayList;

import sg.ruqqq.WHSFinder.ImageThreadLoader.ImageLoadedListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceAdapter extends ArrayAdapter<Place> {

    private ArrayList<Place> items;
    Context context;
    int textViewResourceId;
    
    // For lazy loading of thumbnails
    private ImageThreadLoader imageLoader = new ImageThreadLoader();
    
    public PlaceAdapter(Context context, int textViewResourceId, ArrayList<Place> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
            this.textViewResourceId = textViewResourceId;
    }

    // getView override which allows the use of custom XML for our list
    // this method associate the data to the view for the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(textViewResourceId, null);
            }
            Place p = items.get(position);
            if (p != null) {
                    TextView vText = (TextView) v.findViewById(R.id.heading);
                    TextView vText2 = (TextView) v.findViewById(R.id.subheading);
                    final ImageView vImage = (ImageView) v.findViewById(R.id.thumbnail);
                    if (vText != null) {
                    	vText.setText(p.getName());
                    }
                    if (vText2 != null) {
                    	vText2.setText(p.getDescription());
                    }
                    if (vImage != null) {
                        Bitmap cachedImage = null;
                        try {
                          cachedImage = imageLoader.loadImage(p.getImageUrl(), new ImageLoadedListener() {
	                          public void imageLoaded(Bitmap imageBitmap) {
	                        	  vImage.setImageBitmap(imageBitmap);
	                        	  notifyDataSetChanged();
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
            }
            return v;
    }
}