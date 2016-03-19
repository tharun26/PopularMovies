package com.tharun26.tharun_gowrishankar.moviebox;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Tharun_Gowrishankar on 3/19/2016.
 */
public class HomeScreenAdapter extends ArrayAdapter<MovieModel> {
    public HomeScreenAdapter(Context context,ArrayList<MovieModel> movieModels) {
        super(context, 0, movieModels);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public MovieModel getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieModel each_movie=getItem(position);
        ImageView each_movie_poster;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.each_movie_item, parent, false);
        }
        /*if (convertView == null)
        {
            each_movie_poster = new ImageView(getContext());
        }
        else {
            // Recycling the image view - Based on convertView parameter
            each_movie_poster = (ImageView) convertView;
        }
        */
        ProgressBar progressBar = null;
        if(convertView != null){
            progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            }
        each_movie_poster = (ImageView)convertView.findViewById(R.id.each_movie);

        Uri.Builder builder =  new Uri.Builder();
        builder.scheme("http")
                .appendPath("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendEncodedPath(each_movie.getPoster_path());

        String url = "http://image.tmdb.org/t/p/w185/"+each_movie.getPoster_path();
        Log.d("MovieTitle", each_movie.getOriginal_title());
        each_movie_poster.setPadding(0, 0, 0, 0);
        each_movie_poster.setAdjustViewBounds(true);
        //each_movie_poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(getContext())
                .load(url)
                .noFade()
                .into(each_movie_poster,new ImageLoadedCallback(progressBar) {
            @Override
            public void onSuccess() {
                if (this.progressBar != null) {
                    this.progressBar.setVisibility(View.GONE);
                }
            }
        });
        return convertView;
    }
    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public  ImageLoadedCallback(ProgressBar progBar){
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
