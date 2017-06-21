package com.nivedita.carouseldemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nivedita.carouseldemo.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by PUNEETU on 05-06-2017.
 */

public class GridviewAdapter extends RecyclerView.Adapter<GridViewHolder> {

    private ArrayList<Image> images;
    private Context context;
    private int visibleThreshold = 6;
    private int totalItemCount;
    private int lastVisibleItem;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public GridviewAdapter(Context context) {
        this.context = context;
        //addInfiniteScroll(recyclerView);
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int id = R.layout.grid_item;
        boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(context).inflate(id, parent, shouldAttachToParentImmediately);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {

        holder.title.setText(images.get(position).getTitle());
        Picasso.with(this.context).load(images.get(position).getThumbNail()).into(holder.randomImage);
    }

    @Override
    public int getItemViewType(int position) {
        return images.get(position) != null ? 1 : 0;
    }

    @Override
    public int getItemCount() {

        if (null == images) {
            return 0;
        } else {
            return images.size();

        }
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
        // notifyDataSetChanged();
    }

    private void addInfiniteScroll(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {

            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = staggeredGridLayoutManager.getItemCount();
                    int[] lastVisibleItems = new int[staggeredGridLayoutManager.getSpanCount()];
                    lastVisibleItem = getLastVisibleItem(staggeredGridLayoutManager, lastVisibleItems);
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }

                        loading = true;
                    }
                }
            });

        }
    }


    private int getLastVisibleItem(StaggeredGridLayoutManager staggeredGridLayoutManager, int[] lastVisibleItem) {

        switch (staggeredGridLayoutManager.getSpanCount()) {
            case 1:
                return lastVisibleItem[0];
            case 2:
                return Math.max(lastVisibleItem[0], lastVisibleItem[1]);
            case 3:
                return Math.max(Math.max(lastVisibleItem[0], lastVisibleItem[1]), lastVisibleItem[2]);
            default:
                return 2;
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

}
