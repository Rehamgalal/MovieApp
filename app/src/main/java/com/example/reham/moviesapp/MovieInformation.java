package com.example.reham.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInformation extends AppCompatActivity implements Values {
    @BindView(R.id.Poster)
    ImageView I;
    @BindView(R.id.moviename)
    TextView N;
    @BindView(R.id.movierate)
    RatingBar rate;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.moviedate)
    TextView date;
    @BindView(R.id.ratelabel)
    TextView labelR;
    @BindView(R.id.datelabel)
    TextView labelD;
    @BindView(R.id.overviewlabel)
    TextView labelO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);
        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            String Name = data.getString(movieName);
            String Path = data.getString(moviePath);
            String Overview0 = data.getString(Overview);
            String Rate0 = data.getString(Rate);

            float rate0 = Float.parseFloat(Rate0) / 2;
            String Date0 = data.getString(Date);
            String imagePath = imagesUrl + imageWidth + Path;
            Picasso.with(this).load(imagePath).into(I);
            if (Rate0.isEmpty() || Rate0 == null) {
                rate0 = 0f;
            }
            rate.setRating(rate0);
            N.setText(Name);
            rate.setRating(rate0);
            overview.setText(Overview0);
            date.setText(Date0);
        }


    }
}
