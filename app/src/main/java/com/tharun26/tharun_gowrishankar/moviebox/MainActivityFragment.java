package com.tharun26.tharun_gowrishankar.moviebox;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public String sort_method="";
    public String prev_sort_method=null;
    ArrayList<MovieModel> movie_models=null;
    HomeScreenAdapter homeScreenAdapter=null;
    Integer page_number = new Integer(2);
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.fragment_main, container, false);
        GridView grid_movie_home_screen = (GridView)root_view.findViewById(R.id.id_movie_box);
        movie_models = new ArrayList<MovieModel>();
        homeScreenAdapter = new HomeScreenAdapter(getActivity(),movie_models);
        grid_movie_home_screen.setAdapter(homeScreenAdapter);
        String sort_option = null;

        grid_movie_home_screen.setOnScrollListener(new AbsListView.OnScrollListener() {
            int currentFirstVisibleItem ;
            int currentVisibleItemCount;
            int currentScrollState;
            //Integer page_number = new Integer(2);
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
            }
            public void isScrollCompleted()
            {
                if(this.currentVisibleItemCount>0 && this.currentScrollState == SCROLL_STATE_IDLE)
                {
                    UpdateHomePage("popularity.desc", Integer.toString(page_number));
                    if(prev_sort_method != sort_method)
                    {
                        page_number=2;
                    }
                    page_number++;
                }
            }

        });
        return root_view;
    }

    @Override
    public void onStart() {
        super.onStart();

        UpdateHomePage("popularity.desc","1");
    }
    public void UpdateHomePage(String sort_by,String page_num)
    {
        final String SORT_KEY= getResources().getString(R.string.pref_sort_key);

        SharedPreferences location_preference = PreferenceManager.getDefaultSharedPreferences(getContext());
        sort_method = location_preference.getString(SORT_KEY, "");
        if (prev_sort_method == null)
            prev_sort_method = sort_method;
        if(prev_sort_method != sort_method)
        {
            homeScreenAdapter.clear();
            movie_models.clear();
            page_number=2;
            homeScreenAdapter.notifyDataSetChanged();
            prev_sort_method = sort_method;
        }
        MovieFragment fetch_movie_info = new MovieFragment();
        fetch_movie_info.execute(sort_method, page_num);
    }

    private class MovieFragment extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            // Link to db to get details
            // http://api.themoviedb.org/3/movie/popular?api_key=f4f0c670c84d3517be039f9952e28d33
            //http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=f4f0c670c84d3517be039f9952e28d33&page=2
            String LOG_TAG = "Json Request";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;

            try
            {
                Uri.Builder url_builder = new Uri.Builder();
                url_builder.scheme("http")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("discover")
                        .appendPath("movie")
                        .appendQueryParameter("sort_by",params[0])
                        .appendQueryParameter("page",params[1])
                        .appendQueryParameter("api_key", "f4f0c670c84d3517be039f9952e28d33");
                Log.v(LOG_TAG, url_builder.build().toString());
                URL url = new URL(url_builder.build().toString());

                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                {
                    Log.v(LOG_TAG,"Input stream is null");
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine())!=null)
                {
                    buffer.append(line+"\n");
                    Log.i(LOG_TAG, line);
                }
                if (buffer.length() == 0)
                {
                    return null;
                }
                movieJsonStr = buffer.toString();
                try
                {
                    JSONObject movieJson = new JSONObject(movieJsonStr);
                    JSONArray movie_details = movieJson.getJSONArray("results");
                    movie_models = MovieModel.fromJsonArray(movie_details);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }catch(IOException e)
            {
                e.printStackTrace();
                return null;
            }
            finally {
                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }
                if(reader != null)
                {
                    try{
                        reader.close();
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            /*(int i =0;i<movie_models.size();i++) {
                MovieModel test;
                test = movie_models.get(i);
                Log.i("Poster Path", test.getPoster_path());
            }*/

            homeScreenAdapter.addAll(movie_models);
            homeScreenAdapter.notifyDataSetChanged();

            super.onPostExecute(aVoid);
        }
    }
}
