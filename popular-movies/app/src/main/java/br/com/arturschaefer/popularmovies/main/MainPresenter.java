package br.com.arturschaefer.popularmovies.main;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.arturschaefer.popularmovies.BuildConfig;
import br.com.arturschaefer.popularmovies.application.MoviesApplication;
import br.com.arturschaefer.popularmovies.model.entity.genre.GenreModel;
import br.com.arturschaefer.popularmovies.model.entity.genre.Genres;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieResult;
import br.com.arturschaefer.popularmovies.model.remote.Services;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static br.com.arturschaefer.popularmovies.model.remote.Queries.REQUEST_URL;
import static br.com.arturschaefer.popularmovies.utilities.MovieUtils.POPULAR;
import static br.com.arturschaefer.popularmovies.utilities.MovieUtils.TOP_RATED;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String LOG_TAG = MainPresenter.class.getSimpleName();

    public MainPresenter() {
    }

    public void callResultMovies(final int page){
        final ArrayList<MovieResult> listResult = new ArrayList<>();
        getViewState().showProgress();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        AsyncTask<Void, Void, Void> voidVoidVoidAsyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                getViewState().showProgress();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(REQUEST_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Call<MovieResult> call = null;
                Services service = retrofit.create(Services.class);
                switch (MoviesApplication.currentListType){
                    case TOP_RATED:
                        call = service.findTopRated(BuildConfig.API_KEY, page);
                        break;
                    case POPULAR:
                        call = service.findPopular(BuildConfig.API_KEY, page);
                        break;
                }
                try {
                    Response<MovieResult> response = call.execute();
                    listResult.add(response.body());
                } catch (Exception e){
                    Timber.e(LOG_TAG, e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getViewState().callPage(listResult.get(0));
                getViewState().hideProgress();
            }
        };
        voidVoidVoidAsyncTask.execute();
    }

    public void callGenres(){
        final Genres[] genres = {new Genres()};
        final List<GenreModel> genreModels = new ArrayList<>();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        AsyncTask<Void, Void, Void> voidVoidVoidAsyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(REQUEST_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Services service = retrofit.create(Services.class);
                Call<Genres> call = service.findGenre(BuildConfig.API_KEY);
                try {
                    Response<Genres> response = call.execute();
                    genres[0] = response.body();
                } catch (Exception e){
                    Timber.e(LOG_TAG, e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(MoviesApplication.genres == null)
                    MoviesApplication.setGenres(new HashMap<Integer, String>());
                for(GenreModel genreModel : genres[0].getGenreModels()){
                    MoviesApplication.genres.put(genreModel.getId(), genreModel.getName());
                }
            }
        };
        voidVoidVoidAsyncTask.execute();
    }
}
