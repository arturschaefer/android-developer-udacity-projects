package br.com.arturschaefer.popularmovies.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import br.com.arturschaefer.popularmovies.R;
import br.com.arturschaefer.popularmovies.application.MoviesApplication;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieAdapter;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieModel;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieResult;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

@SuppressLint("ValidFragment")
public class MainFragment extends MvpFragment implements MainView, MovieAdapter.CallbackInterface {
    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    private static Context appContext;


    @InjectPresenter
    MainPresenter presenter;

    MovieResult results;
    ArrayList<MovieModel> moviesList;
    MovieAdapter movieAdapter;

    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;
    @BindView(R.id.fragment_movie_list)
    ConstraintLayout fragmentMovieList;
    @BindView(R.id.progress_view)
    LottieAnimationView progressView;

    @SuppressLint("ValidFragment")
    public MainFragment(PageListener pageListener) {
        this.pageListener = pageListener;
    }

    public PageListener pageListener;

    public interface PageListener {
        public void page(int page, int totalPages, MovieAdapter movieAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);

        appContext = getActivity();

        if (MoviesApplication.genres == null)
            presenter.callGenres();
        if (MoviesApplication.movieResults == null) {
            presenter.callResultMovies(1);
        } else {
            presenter.callResultMovies(MoviesApplication.getMovieResults().getPage());
        }

        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieModel>(), this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rvMovieList.setLayoutManager(layoutManager);
        ViewCompat.setNestedScrollingEnabled(rvMovieList, false);

        rvMovieList.setHasFixedSize(true);
        rvMovieList.setAdapter(movieAdapter);

        /*swipeMovieList.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.primaryLightColor);
        swipeMovieList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.callResultMovies();
            }
        });*/
        return rootView;
    }

    @Override
    public void callPage(MovieResult results) {
        this.results = results;
        moviesList = (ArrayList<MovieModel>) results.getResults();
        Timber.tag(LOG_TAG).d(String.valueOf(moviesList.size()));
        if (moviesList != null && moviesList.size() > 0) {
            pageListener.page(results.getPage(), results.getTotalPages(), movieAdapter);
            movieAdapter.newListMovies(moviesList);
            MoviesApplication.movieResults = results;
        } else {
            Snackbar snackbar = Snackbar
                    .make(fragmentMovieList, "Erro ao carregar filmes", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void showProgress() {
        uiThread(R.anim.fade_in, progressView, true);
        uiThread(R.anim.fade_out, rvMovieList, false);
    }

    @Override
    public void hideProgress() {
        uiThread(R.anim.fade_out, progressView, false);
        uiThread(R.anim.fade_in, rvMovieList, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void selectMovie(MovieModel movie, int position) {

    }

    public void uiThread(final int resource, final View view, final boolean value) {
        try {
            ((MainActivity) appContext).runOnUiThread(new Runnable() {
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(appContext, resource);
                    view.setAnimation(animation);
                    if (value)
                        view.setVisibility(View.VISIBLE);
                    else
                        view.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
