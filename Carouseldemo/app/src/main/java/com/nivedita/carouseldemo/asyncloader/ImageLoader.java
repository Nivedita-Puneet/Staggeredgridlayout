package com.nivedita.carouseldemo.asyncloader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.nivedita.carouseldemo.model.Image;
import com.nivedita.carouseldemo.utilities.NetworkUtils;
import com.nivedita.carouseldemo.utilities.Utilconstants;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by PUNEETU on 03-06-2017.
 */

public class ImageLoader extends AsyncTaskLoader<ArrayList<Image>> {

    ArrayList<Image> randomImages = new ArrayList<>();

    private static final String LOG_TAG = ImageLoader.class.getSimpleName();

    public ImageLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (randomImages != null && randomImages.size()>3) {
            deliverResult(randomImages);
        } else {
            forceLoad();

        }

    }

    @Override
    public ArrayList<Image> loadInBackground() {

        URL url = NetworkUtils.buildURL(Utilconstants.BASE_URL);
        String serverResponse = null;

        try {
            for (int i = 0; i < 3; i++) {
                serverResponse = NetworkUtils.makeHttpRequest(url);
                randomImages.add(NetworkUtils.getImageUrlFromXMLResponse(serverResponse));
            }

            Log.i(LOG_TAG, "Server Response Obtained is:" + " " + serverResponse);
            if (null != randomImages)
                return randomImages;
        } catch (IOException exception) {
            Log.e(LOG_TAG, exception.getLocalizedMessage());
        }

        /*String serverResponse = null;

        try{
            serverResponse = NetworkUtils.makeHttpRequest(url);
            randomImages = NetworkUtils.getImageUrlFromXMLResponse(serverResponse);

            Log.i(LOG_TAG, "Server Response Obtained is:" + " "+ serverResponse);
            if(null != randomImages)
                return randomImages;
        }catch (IOException exception){
            Log.e(LOG_TAG, exception.getLocalizedMessage());
        }*/
        return null;
    }

    public void deliverResult(ArrayList<Image> images) {
        super.deliverResult(images);
        this.randomImages = images;
    }

}
