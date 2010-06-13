/**
 * FARUQ: NOT RELEVANT TO PROBLEM
 * Hence, code is left not properly commented
 */

/**
 * QuickActionItem
 * @author ruqqq (ruqqq.sg)
 * @desc Part of QuickActionWindow popup code. See QuickActionWindow.java for more information.
 * 
 * Released under GPL License: http://www.gnu.org/licenses/gpl.html
 * Credits greatly appreciated ;)
 * 
 */

package sg.ruqqq.WHSFinder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuickActionItem extends LinearLayout implements Checkable {
    private boolean mChecked;

    private static final int[] CHECKED_STATE_SET = {
        android.R.attr.state_checked
    };

    public QuickActionItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void toggle() {
        setChecked(!mChecked);
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }
    
    // Set the Icon
    public void setImageDrawable(Drawable drawable) {
    	((ImageView)findViewById(R.id.quickaction_icon)).setImageDrawable(drawable);
    }
    
    // Set the Label
    public void setText(String text) {
    	((TextView)findViewById(R.id.quickaction_text)).setText(text);
    }
}