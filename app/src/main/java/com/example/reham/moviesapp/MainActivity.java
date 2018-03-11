package com.example.reham.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener, Values {

    private MovieAdapter movieAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView RC;
    @BindView(R.id.toprated)
    RadioButton topRated;
    @BindView(R.id.mostpopular)
    RadioButton mostPopular;
    @BindView(R.id.chooose)
    RadioGroup Group;
    MovieAdapter movieAdapter1;
    String topmovies = "http://api.themoviedb.org/3/movie/top_rated?api_key=[API_KEY]";
    String mostpopularmovies = "http://api.themoviedb.org/3/movie/popular?api_key=[API_KEY]";
    Boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RC.setLayoutManager(new GridLayoutManager(this, snapCount));
        RC.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this, numOfItems, mostpopularmovies, this);
        movieAdapter1 = new MovieAdapter(getBaseContext(), numOfItems, topmovies, this);
        RC.setAdapter(movieAdapter);
        Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mostPopular.getId() == checkedId) {
                    RC.setAdapter(movieAdapter);
                    check = true;
                } else if (topRated.getId() == checkedId) {
                    RC.setAdapter(movieAdapter1);
                    check = false;
                }
            }
        });
    }

    @Override
    public void onItemClick(final int position) {
        if (check) {
            Intent i = new Intent(MainActivity.this, MovieInformation.class);
            i.putExtra(moviePath, movieAdapter.PAth[position]);
            i.putExtra(movieName, movieAdapter.Name[position]);
            i.putExtra(Overview, movieAdapter.Overview[position]);
            i.putExtra(Rate, movieAdapter.Rate[position]);
            i.putExtra(Date, movieAdapter.date[position]);
            startActivity(i);
        } else {
            Intent i = new Intent(MainActivity.this, MovieInformation.class);
            i.putExtra(moviePath, movieAdapter1.PAth[position]);
            i.putExtra(movieName, movieAdapter1.Name[position]);
            i.putExtra(Overview, movieAdapter1.Overview[position]);
            i.putExtra(Rate, movieAdapter1.Rate[position]);
            i.putExtra(Date, movieAdapter1.date[position]);
            startActivity(i);
        }
    }

}

