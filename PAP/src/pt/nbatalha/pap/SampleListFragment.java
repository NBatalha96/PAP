package pt.nbatalha.pap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SampleListFragment extends ListFragment {

	SampleAdapter adapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new SampleAdapter(getActivity());
		
		adapter.add(new SampleItem("Home", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Scan", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Manual", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Analysis", android.R.drawable.ic_menu_search));

		setListAdapter(adapter);
	}

	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
		switch (position)
		{
		case 0:
			Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();			
			break;
		case 1:
			ScanFragment myFragment = (ScanFragment)getFragmentManager().findFragmentByTag("scan");
			
			if (myFragment==null) 
			{	
			ScanFragment newFragment = new ScanFragment();
			Bundle args = new Bundle();
			args.putInt(ScanFragment.ARG_POSITION, position);
			newFragment.setArguments(args);

			FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack so the user can navigate back
			transaction.replace(R.id.activity_frame, newFragment, "scan");
			transaction.addToBackStack(null);
			//transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			// Commit the transaction
			transaction.commit();
			}
			
			MainActivity.sm.toggle();
			break;
		case 2:
			Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
			break;
		}
		
        //super.onListItemClick(l, v, position, id);
    }
	
	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}
	}
}
