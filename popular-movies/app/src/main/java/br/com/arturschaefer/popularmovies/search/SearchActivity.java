package br.com.arturschaefer.popularmovies.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import br.com.arturschaefer.popularmovies.R;
import br.com.arturschaefer.popularmovies.main.MovieAdapter;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieModel;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieResult;
import br.com.arturschaefer.popularmovies.movie_details.MovieDetailsActivity;
import br.com.arturschaefer.popularmovies.utilities.MovieUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static br.com.arturschaefer.popularmovies.utilities.MovieUtils.EXTRA_MOVIE;

public class SearchActivity extends MvpActivity
        implements br.com.arturschaefer.popularmovies.search.SearchView,
        MovieAdapter.CallbackInterface {
    private static final String LOG_TAG = SearchActivity.class.getSimpleName();
    @InjectPresenter
    SearchPresenter presenter;
    MovieResult results;
    ArrayList<MovieModel> moviesList;
    MovieAdapter movieAdapter;
    @BindView(R.id.empty_recycler)
    LottieAnimationView emptyRecycler;
    @BindView(R.id.text_empty)
    TextView textEmpty;
    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;
    @BindView(R.id.adView2)
    AdView adView;
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.movie_search)
    ConstraintLayout movieSearch;
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.textView5)
    TextView textView5;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mContext = this;
        showHideList(true);
        toolbarSearch.setTitle("Search Movies");
        toolbarSearch.setNavigationIcon(R.drawable.ic_backward);
        toolbarSearch.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        toolbarSearch.inflateMenu(R.menu.menu_search);
        toolbarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbarSearch.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                final SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // Toast like print
//                UserFeedback.show( "SearchOnQueryTextSubmit: " + query);
                        if (!searchView.isIconified()) {
                            searchView.setIconified(true);
                        }
                        item.collapseActionView();
                        presenter.callResultMovies(1, query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                        return false;
                    }
                });
                return false;
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("09915F32E519000339D4119F3DCF4B68").build();
//        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        movieAdapter = new MovieAdapter(mContext, new ArrayList<MovieModel>(), this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false);
        rvMovieList.setLayoutManager(layoutManager);
        ViewCompat.setNestedScrollingEnabled(rvMovieList, false);

        rvMovieList.setHasFixedSize(true);
        rvMovieList.setAdapter(movieAdapter);
    }

    @Override
    public void selectMovie(MovieModel movie, int position) {
        Intent intent = new Intent(SearchActivity.this, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        try {
            MovieUtils.createProgress(getFragmentManager().beginTransaction());
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    @Override
    public void hideProgress() {
        MovieUtils.hideProgress();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
//                UserFeedback.show( "SearchOnQueryTextSubmit: " + query);
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                presenter.callResultMovies(1, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
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

    @Override
    public void callPage(MovieResult results) {
        textEmpty.setVisibility(View.GONE);
        emptyRecycler.setVisibility(View.GONE);
        rvMovieList.setVisibility(View.GONE);
        this.results = results;
        moviesList = (ArrayList<MovieModel>) results.getResults();
        Timber.tag(LOG_TAG).d(String.valueOf(moviesList.size()));
        if (moviesList != null && moviesList.size() > 0) {
            movieAdapter.newListMovies(moviesList);
            showHideList(false);
        } else {
            showHideList(true);
            Snackbar snackbar = Snackbar
                    .make(movieSearch, "Error while load the movies", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void showHideList(boolean isEmpty) {
        if (isEmpty) {
            lottieAnimationView.setVisibility(View.VISIBLE);
            textView5.setVisibility(View.VISIBLE);
            rvMovieList.setVisibility(View.GONE);
        } else {
            lottieAnimationView.setVisibility(View.GONE);
            textView5.setVisibility(View.GONE);
            rvMovieList.setVisibility(View.VISIBLE);
        }
    }
}
