package com.example.reham.moviesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by reham on 2/25/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> implements Values {
    private int numberOfItems;
    private ItemClickListener mClickListener;
    private Context mContext;
    String Name[] = new String[20];
    String PAth[] = new String[20];
    String Overview[] = new String[20];
    String Rate[] = new String[20];
    String date[] = new String[20];
    URL movieUrl;

    String theUrl;

    public MovieAdapter(@NonNull Context context, int itemsNum, String url, ItemClickListener itemClickListener) {
        this.numberOfItems = itemsNum;
        this.mClickListener = itemClickListener;
        theUrl = url;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater;
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberOfItems;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public class getData extends AsyncTask<URL, Void, String> {
            PostExecute delegate = null;

            private getData(PostExecute delegate) {
                this.delegate = delegate;
            }

            @Override
            protected String doInBackground(URL... urls) {

                try {
                    String json = getResponseFromHttpUrl(urls[0]);
                    return json;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s != null) {
                    try {
                        JSONObject movies = new JSONObject(s);
                        JSONArray Results = movies.getJSONArray(results);
                        delegate.processFinish(Results);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        @BindView(R.id.imageview)
        ImageView image;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void loadMovies(URL url, final int position) {
            try {
                new ViewHolder.getData(new PostExecute() {
                    @Override
                    public void processFinish(JSONArray output) {
                        try {
                            JSONObject movie = output.getJSONObject(position);
                            String movieName1 = movie.getString(movieName0);
                            String imagePath0 = movie.getString(imagePath);
                            String overView = movie.getString(overview);
                            String Vote = movie.getString(vote);
                            String releaseDate = movie.getString(Date1);
                            String imageUrl = imagesUrl + imageWidth + imagePath0;
                            Picasso.with(mContext).load(imageUrl).into(image);
                            Log.i(movieName, movieName1 + imagePath0);
                            PAth[position] = imagePath0;
                            Name[position] = movieName1;
                            Overview[position] = overView;
                            Rate[position] = Vote;
                            date[position] = releaseDate;
                        } catch (Exception e) {
                            Log.i(Error, e.toString());
                        }
                    }
                }).execute(url);
            } catch (Exception e) {
                Log.i(Error, e.toString());
            }
        }

        private void bind(final int position) {
            try {
                movieUrl = new URL(theUrl);
                loadMovies(movieUrl, position);
            } catch (Exception e) {
                Log.i("error", e.toString());
            }

        }


        private String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                boolean hasInput = scanner.hasNext();
                String response = null;
                if (hasInput) {
                    response = scanner.next();
                }
                scanner.close();
                return response;
            } finally {
                urlConnection.disconnect();
            }
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
