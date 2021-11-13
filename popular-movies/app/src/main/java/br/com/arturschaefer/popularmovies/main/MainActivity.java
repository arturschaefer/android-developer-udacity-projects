package br.com.arturschaefer.popularmovies.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpActivity;

import br.com.arturschaefer.popularmovies.R;
import br.com.arturschaefer.popularmovies.application.MoviesApplication;
import br.com.arturschaefer.popularmovies.botton_navigation.BottomNavigationBehavior;
import br.com.arturschaefer.popularmovies.botton_navigation.BottomNavigationViewHelper;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieAdapter;
import br.com.arturschaefer.popularmovies.utilities.MovieUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.arturschaefer.popularmovies.utilities.MovieUtils.POPULAR;
import static br.com.arturschaefer.popularmovies.utilities.MovieUtils.TOP_RATED;
import static br.com.arturschaefer.popularmovies.utilities.MovieUtils.isDeviceConnected;

public class MainActivity extends MvpActivity implements
        NavigationView.OnNavigationItemSelectedListener,
MainFragment.PageListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private int pageSelected;
    private int totalPages;
    MovieAdapter movieAdapter;
    MainFragment mainFragment;
    FragmentManager fragmentManager;
    FragmentTransaction myfragmentTransaction;
    Context mContext;

    String teste = null;
    NavigationView navView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bottom_navigation_main)
    BottomNavigationView bottomNavigationMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar_movie_list);
        toolbar.setTitle(R.string.app_name);

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.inflateMenu(R.menu.menu_search);
        navView.setNavigationItemSelectedListener(this);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationMain);
        bottomNavigation();
        // attaching bottom sheet behaviour - hide / show on scroll
        // Author: Android Hive, Source: https://www.androidhive.info/2017/12/android-working-with-bottom-navigation/
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationMain.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        mainFragment = new MainFragment(this);
        fragmentManager = getFragmentManager();
        myfragmentTransaction = fragmentManager.beginTransaction();
        if(isDeviceConnected(this)) {
            myfragmentTransaction.replace(
                    R.id.fl_content,
                    mainFragment,
                    LOG_TAG)
                    .commit();
        } else {
            //TODO 1 - test connection
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searcItem = menu.findItem(R.id.action_search);

        searcItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //TODO open new fragment
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(MovieUtils.isDeviceConnected(this)) {
            fragmentManager = getFragmentManager();
            myfragmentTransaction = fragmentManager.beginTransaction();
            myfragmentTransaction.replace(
                    R.id.fl_content,
                    mainFragment,
                    LOG_TAG)
                    .commit();
            switch (id) {
                case R.id.nav_populares:
                    MoviesApplication.currentListType = POPULAR;
                    mainFragment.presenter.callResultMovies(1);
                    break;
                case R.id.nav_most_rated:
                    MoviesApplication.currentListType = TOP_RATED;
                    mainFragment.presenter.callResultMovies(1);
                    break;
                case R.id.nav_fav:

                    break;
                case R.id.nav_actors:

                    break;
                case R.id.nav_share:

                    break;
                case R.id.nav_exit:

                    break;
            }
        } else {
            //TODO 1 - test connection
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void bottomNavigation(){
        bottomNavigationMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (MovieUtils.isDeviceConnected(mContext)) {
                    switch (item.getItemId()) {
                        case R.id.next_page:
                            if (pageSelected < totalPages) {
                                mainFragment.presenter.callResultMovies(pageSelected + 1);
                            } else {
                                Snackbar.make(findViewById(R.id.drawer_layout),
                                        "Você está na última página!",
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                            break;
                        case R.id.previous_page:
                            if (pageSelected > 1) {
                                mainFragment.presenter.callResultMovies(pageSelected - 1);
                            } else {
                                Snackbar.make(findViewById(R.id.drawer_layout),
                                        "Você está primeira página!",
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                            break;
                    }
                }else {
                    //TODO 1 - test connection
                }
                return true;
            }
        });
    }

    @Override
    public void page(int page, int totalPages, MovieAdapter movieAdapter) {
        this.pageSelected = page;
        this.totalPages = totalPages;
        bottomNavigationMain.setVisibility(View.VISIBLE);
        this.movieAdapter = movieAdapter;
    }
}
