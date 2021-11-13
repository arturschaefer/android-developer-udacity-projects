package br.com.arturschaefer.popularmovies.main;

import com.arellomobile.mvp.MvpView;

import br.com.arturschaefer.popularmovies.model.entity.movie.MovieResult;

public interface MainView extends MvpView {
    public void callPage(MovieResult results);

    public void showProgress();

    public void hideProgress();
}
