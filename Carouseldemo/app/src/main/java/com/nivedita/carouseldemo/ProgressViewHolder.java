package com.nivedita.carouseldemo;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by PUNEETU on 14-06-2017.
 */

public class ProgressViewHolder extends GridViewHolder {

    public ProgressBar progressBar;

    public ProgressViewHolder(View view) {
        super(view);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

    }
}
