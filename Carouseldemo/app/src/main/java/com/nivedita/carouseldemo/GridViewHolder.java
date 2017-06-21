package com.nivedita.carouseldemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by PUNEETU on 14-06-2017.
 */

public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView randomImage;
    public TextView title;

    public GridViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);
        randomImage = (ImageView)itemView.findViewById(R.id.thumbNail);
        title = (TextView)itemView.findViewById(R.id.title);
    }

    @Override
    public void onClick(View view) {

    }
}
