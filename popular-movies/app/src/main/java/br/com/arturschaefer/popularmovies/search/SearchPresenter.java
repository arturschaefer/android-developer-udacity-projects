package br.com.arturschaefer.popularmovies.search;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import br.com.arturschaefer.popularmovies.BuildConfig;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieResult;
import br.com.arturschaefer.popularmovies.model.remote.API;
import br.com.arturschaefer.popularmovies.model.remote.Services;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

@InjectViewState
public class SearchPresenter extends MvpPresenter<SearchView> {

    private static final String LOG_TAG = SearchPresenter.class.getSimpleName();

    public SearchPresenter() {
    }

    public void callResultMovies(final int page, final String query) {
        final ArrayList<MovieResult> listResult = new ArrayList<>();
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> voidVoidVoidAsyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Call<MovieResult> call = null;
                Services service = API.getConnection().create(Services.class);
                call = service.searchMovies(BuildConfig.API_KEY, query, page);
                try {
                    Response<MovieResult> response = call.execute();
                    listResult.add(response.body());
                } catch (Exception e) {
                    Timber.e(LOG_TAG, e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    super.onPostExecute(aVoid);
                    getViewState().callPage(listResult.get(0));
                } catch (Exception e) {
                    Logger.e(e.toString());
                }
            }
        };
        voidVoidVoidAsyncTask.execute();
    }

}
