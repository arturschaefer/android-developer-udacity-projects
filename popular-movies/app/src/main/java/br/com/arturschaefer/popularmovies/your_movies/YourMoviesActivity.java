package br.com.arturschaefer.popularmovies.your_movies;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.arturschaefer.popularmovies.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class YourMoviesActivity extends AppCompatActivity implements
        FavoritesMoviesFragment.OnFragmentInteractionListener,
        WatchedMoviesFragment.OnFragmentInteractionListener {

    @BindView(R.id.toolbar_your_movies)
    Toolbar toolbarSettings;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the  activity_main.xml layout file
        setContentView(R.layout.activity_your_movies);
        ButterKnife.bind(this);

        toolbarSettings.setTitle("Your Movies");
        toolbarSettings.setTitleTextColor(Color.WHITE);
        toolbarSettings.setNavigationIcon(R.drawable.ic_backward);
        toolbarSettings.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        setSupportActionBar(toolbarSettings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarSettings.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        YourMoviesPageAdapter moviesPageAdapter =
                new YourMoviesPageAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(moviesPageAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
