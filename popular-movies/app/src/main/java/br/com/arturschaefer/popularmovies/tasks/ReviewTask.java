/*
package br.com.arturschaefer.popularmovies.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.arturschaefer.popularmovies.BuildConfig;
import br.com.arturschaefer.popularmovies.model.Review;
import br.com.arturschaefer.popularmovies.model.Reviews;
import br.com.arturschaefer.popularmovies.model.remote.Services;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ReviewTask extends AsyncTask<Long, Void, List<Review>> {

    public static String LOG_TAG = ReviewTask.class.getSimpleName();
    private final Listener mListener;

    public interface Listener {
        void onReviewsFetchFinished(List<Review> reviews);
    }

    public ReviewTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected List<Review> doInBackground(Long... longs) {
        if (longs.length == 0) {
            return null;
        }
        long movieId = longs[0];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);
        Call<Reviews> call = service.findReviewsById(movieId,
                BuildConfig.API_KEY);
        try {
            Response<Reviews> response = call.execute();
            Reviews reviews = response.body();
            return reviews.getReviews();
        } catch (IOException e) {
            Timber.e(LOG_TAG, "Error in review list ", e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
        if (reviews != null) {
            mListener.onReviewsFetchFinished(reviews);
        } else {
            mListener.onReviewsFetchFinished(new ArrayList<Review>());
        }
    }
}
*/
