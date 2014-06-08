//very important!!
/*
 * 
 */

package pt.nbatalha.pap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.actionbar.ActionBarSlideIcon;

public class MainActivity extends FragmentActivity {

	public static SlidingMenu sm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_frame);
		
		// Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.activity_frame) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            
            // Create a new Fragment to be placed in the activity layout
            HomeFragment firstFragment = new HomeFragment();
            
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());
            
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.activity_frame, firstFragment, "main").commit();
        }

		sm = new SlidingMenu(this);
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setMenu(R.layout.menu_frame);
		
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new SampleListFragment()).commit();
		
		sm.setActionBarSlideIcon(new ActionBarSlideIcon(this, R.drawable.ic_drawer, R.string.hello_world, R.string.hello_world));
		
		sm.setOnClosedListener(new OnClosedListener() {
			
			@Override
			public void onClosed() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "closing...", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    case android.R.id.home:
	    	sm.toggle();
	    }
		return super.onOptionsItemSelected(item);
	}

	//http://stackoverflow.com/questions/12795260/android-how-to-hide-the-sliding-menu-when-back-button-clicked - PSchuette
	public boolean onKeyUp(int keycode, KeyEvent e) 
	{
		switch(keycode) 
		{
		case KeyEvent.KEYCODE_MENU:
            sm.toggle();
            
    		sm.setOnOpenListener(new OnOpenListener() {
    	        @Override
    	        public void onOpen() 
    	        {
    	            if (ScanFragment.mCamera != null)
    	            {
    	            ScanFragment.mCamera.stopPreview();
    	            Toast.makeText(getApplicationContext(), "camera off", Toast.LENGTH_LONG).show();
    	            }
    	        }
    	    });
            return true; //showing problems on hardware buttons
        case KeyEvent.KEYCODE_BACK:
        	if (sm.isMenuShowing())
   	         sm.showContent(true);
   	     else
   	    	 super.onBackPressed();
        	return true;
        }
		
		return super.onKeyDown(keycode, e);
	}
}