package com.emoshiapps.baccus.controller.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.emoshiapps.baccus.R;
import com.emoshiapps.baccus.controller.activity.WineryActivity;
import com.emoshiapps.baccus.model.Wine;
import com.emoshiapps.baccus.model.Winery;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WineListFragment extends Fragment {

    private OnWineSelectedListener mOnWineSelectedListener = null;
    private ProgressDialog mProgressDialog = null;

    public WineListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_wine_list, container, false);



        AsyncTask<Void,Void,Winery> wineryDownloader = new AsyncTask<Void, Void, Winery>() {
            @Override
            protected Winery doInBackground(Void... params) {
                return Winery.getInstance();
            }

            @Override
            protected void onPostExecute(final Winery winery) {
                WineListAdapter adapter = new WineListAdapter(getActivity(),winery);

                ExpandableListView listView = (ExpandableListView) getView().findViewById(android.R.id.list);
                listView.setAdapter(adapter);

                listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        int index = winery.getAbsolutePosition(Winery.WineType.values()[groupPosition],childPosition);
                        if (mOnWineSelectedListener != null) {
                            mOnWineSelectedListener.onWineSelected(index);
                        }
                        return true;
                    }
                });

                mProgressDialog.dismiss();
            }
        };

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));

        if(!Winery.isInstanceAvailable()){
            mProgressDialog.show();
        }

        wineryDownloader.execute();

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnWineSelectedListener = (OnWineSelectedListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnWineSelectedListener = null;
    }

    public interface OnWineSelectedListener {
        void onWineSelected(int wineIndex);
    }

    class WineListAdapter extends BaseExpandableListAdapter {

        private Typeface mTextFace = null;

        private Context mContext = null;
        private Winery mWinery = null;

        public WineListAdapter(Context context, Winery winery){
            mContext =context;
            mWinery = winery;
            mTextFace = Typeface.createFromAsset(mContext.getAssets(),"Valentina-Regular.otf");
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View wineRow = inflater.inflate(R.layout.list_item_wine,parent,false);

            final ImageView wineImage = (ImageView)wineRow.findViewById(R.id.wine_image);
            TextView wineName = (TextView)wineRow.findViewById(R.id.wine_name);
            TextView wineCompany = (TextView)wineRow.findViewById(R.id.wine_company);

            final Wine currentWine = getChild(groupPosition,childPosition);

            AsyncTask<Void,Void,Bitmap> wineImageDowloader = new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... params) {
                    return currentWine.getPhoto(getActivity());
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    wineImage.setImageBitmap(bitmap);
                }
            };

            wineImageDowloader.execute();


//            wineImage.setImageBitmap(currentWine.getPhoto(getActivity()));
            wineName.setText(currentWine.getName());
            wineCompany.setText(currentWine.getCompanyName());


            wineName.setTypeface(mTextFace);
            wineCompany.setTypeface(mTextFace);

            return wineRow;
        }

        @Override
        public int getGroupCount() {
            return Winery.WineType.values().length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mWinery.getWineCount(getGroup(groupPosition));
        }

        @Override
        public Winery.WineType getGroup(int groupPosition) {
            return Winery.WineType.values()[groupPosition];
        }

        @Override
        public Wine getChild(int groupPosition, int childPosition) {
            return mWinery.getWine(getGroup(groupPosition),childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View wineHeader = inflater.inflate(R.layout.list_item_wine_header, parent,false);
            TextView headerText = (TextView) wineHeader.findViewById(R.id.wine_type);
            headerText.setTypeface(mTextFace);

            if (getGroup(groupPosition) == Winery.WineType.RED) {
                headerText.setText(R.string.red);
            } else if(getGroup(groupPosition)== Winery.WineType.WHITE){
                headerText.setText(R.string.white);
            } else if (getGroup(groupPosition)== Winery.WineType.ROSE){
                headerText.setText(R.string.rose);
            } else { headerText.setText(R.string.other);}

            return wineHeader;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
