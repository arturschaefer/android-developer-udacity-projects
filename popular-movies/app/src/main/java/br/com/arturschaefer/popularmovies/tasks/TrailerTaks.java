/*
package br.com.arturschaefer.popularmovies.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.arturschaefer.popularmovies.BuildConfig;
import br.com.arturschaefer.popularmovies.model.Trailer;
import br.com.arturschaefer.popularmovies.model.Trailers;
import br.com.arturschaefer.popularmovies.model.remote.Services;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrailerTaks extends AsyncTask<Long, Void, List<Trailer>>{

    public static String LOG_TAG = TrailerTaks.class.getSimpleName();
    private final Listener mListener;

    public TrailerTaks(Listener mListener) {
        this.mListener = mListener;
    }

    public interface Listener {
        void onFetchFinished(List<Trailer> trailers);
    }

    @Override
    protected void onPostExecute(List<Trailer> trailers) {
        if (trailers != null) {
            mListener.onFetchFinished(trailers);
        } else {
            mListener.onFetchFinished(new ArrayList<Trailer>());
        }
    }

    @Override
    protected List<Trailer> doInBackground(Long... longs) {
        if (longs.length == 0) {
            return null;
        }
        long movieId = longs[0];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);
        Call<Trailers> call = service.findTrailersById(movieId,
                BuildConfig.API_KEY);
        try {
            Response<Trailers> response = call.execute();
            Trailers trailers = response.body();
            return trailers.getTrailers();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in trailer list ", e);
        }
        return null;
    }
}
*/
