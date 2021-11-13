package br.com.arturschaefer.popularmovies.search;

import com.arellomobile.mvp.MvpView;

import br.com.arturschaefer.popularmovies.model.entity.movie.MovieResult;

interface SearchView extends MvpView {

    void showProgress();

    void hideProgress();

    void callPage(MovieResult movieResult);
}
