package com.tharun26.tharun_gowrishankar.moviebox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_view =  inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        MovieModel movie = bundle.getParcelable("Movie_Details");
        String rel_date = movie.getRelease_date();
        double vote_avg = movie.getVote_average();
        String vote_average = Double.toString(vote_avg)+"/10";
        ImageView thumb_nail =(ImageView)root_view.findViewById(R.id.poster);
        TextView original_title =(TextView)root_view.findViewById(R.id.original_title);
        TextView over_view = (TextView)root_view.findViewById(R.id.overview);
        TextView rating = (TextView) root_view.findViewById(R.id.rating);
        TextView release_date = (TextView) root_view.findViewById(R.id.release_date);
        String url_poster_path = "http://image.tmdb.org/t/p/w185/"+ movie.getPoster_path();

        Picasso.with(getContext())
                .load(url_poster_path)
                .noFade()
                .into(thumb_nail);

        original_title.setText(movie.getOriginal_title());
        over_view.setText(movie.getOverview());
        rating.setText(vote_average);
        release_date.setText(rel_date);
        return root_view;
    }
}
