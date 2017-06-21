package com.nivedita.carouseldemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nivedita.carouseldemo.asyncloader.ImageLoader;
import com.nivedita.carouseldemo.model.Image;
import com.nivedita.carouseldemo.viewdecorator.SpacesItemDecoration;

import java.util.ArrayList;

/**
 * Created by PUNEETU on 03-06-2017.
 */

public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Image>> {

    private static final String ARG_SECTION_NUMBER = "section_number";
    int argsSectionNumber;
    private static final int RANDOM_IMAGE_CODE = 101;
    RecyclerView recyclerView;
    GridviewAdapter gridviewAdapter;

    public static HomeFragment newInstance(int sectionNumber) {

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        argsSectionNumber = getArguments() != null ? getArguments().getInt(ARG_SECTION_NUMBER) : 1;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View rootView = layoutInflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
       // recyclerView.addItemDecoration(new SpacesItemDecoration(15));
        recyclerView.setHasFixedSize(true);

        gridviewAdapter = new GridviewAdapter(getActivity());
        getLoaderManager().initLoader(RANDOM_IMAGE_CODE, null, HomeFragment.this);
        recyclerView.setAdapter(gridviewAdapter);

        return rootView;
    }

    @Override
    public Loader<ArrayList<Image>> onCreateLoader(int id, Bundle args) {
        return new ImageLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Image>> loader, ArrayList<Image> data) {

            /* check data length and restart the loader once length s greater than 10 set it to the adapter and start loading again.
            *  write an adapter to bind the data.
            * */
            //getLoaderManager().restartLoader(RANDOM_IMAGE_CODE,null, this);
            Log.i(HomeFragment.class.getSimpleName(), "Data is" +":" + data);
            gridviewAdapter.setImages(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Image>> loader) {

    }
}

