package sg.ruqqq.WHSFinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class FeedbackActivity extends Activity {
	// For storing the place data
	Place p;
	
	EditText etComment, etEmail;
	Button btnSubmit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		
		// Get data passed from Intent
        Intent i = getIntent();
        p = new Place(i.getStringExtra("name"), i.getStringExtra("description"), i.getStringExtra("imageUrl"), i.getStringExtra("url"), i.getStringExtra("address"), i.getDoubleExtra("latitude", 0), i.getDoubleExtra("longitude", 0));
        
        // Get btnCancel and set it's onClick listener to finish the app
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();
			}
        });
        
        // Get etComment and etEmail and set their onFocusChange to enable/disable Submit button accordingly
        // if the fields are filled/empty
        etComment = (EditText) findViewById(R.id.etComment);
        etComment.setOnFocusChangeListener(new OnFocusChangeListener(){
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus)
					verifyCommentEmail();
			}
        });
        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setOnFocusChangeListener(new OnFocusChangeListener(){
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus)
					verifyCommentEmail();
			}
        });
        
        // Get rbRating and etPhoneNumber to get their information later for sending email
        final RatingBar rbRating = (RatingBar) findViewById(R.id.rtRating);
        final EditText etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        
        // Get submit button and set it's onClick to start and intent to send email with the relevant information from the form
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setEnabled(false);
        btnSubmit.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				 final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                 emailIntent.setType("plain/text");
                 emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"faruq.91@gmail.com"});
                 emailIntent.putExtra(android.content.Intent.EXTRA_BCC, new String[]{etEmail.getText().toString()});
                 emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_for)+p.getName());
                 emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, etComment.getText().toString()+"\n\n"+getResources().getString(R.string.rating)+rbRating.getRating()+"\n"+getResources().getString(R.string.my_contact_number)+etPhoneNumber.getText().toString());
                 startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.chooser_send_feedback)));
                 finish();
			}
        });
	}
	
	// Method that's being called everytime a textfield loses focus
	// purpose: to only enable submission if comment and email is filled
	private void verifyCommentEmail() {
		if (etComment.getText().toString().length() > 0 && etEmail.getText().toString().length() > 0) {
			btnSubmit.setEnabled(true);
		} else {
			btnSubmit.setEnabled(false);
		}
	}
}
