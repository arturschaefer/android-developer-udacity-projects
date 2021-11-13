package br.com.arturschaefer.popularmovies.model.remote;

import android.net.Uri;

import java.util.concurrent.TimeUnit;

import br.com.arturschaefer.popularmovies.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static final String BACKDROP_URL = "http://image.tmdb.org/t/p/original";

    public static final String REQUEST_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGE_REQUEST_URL = "https://image.tmdb.org/t/p/w185";
    private static final String LOG_TAG = API.class.getSimpleName();
    private static Retrofit retrofit = null;

    public static Retrofit getConnection() {
        if (retrofit == null) {
            OkHttpClient client;
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            if (BuildConfig.DEBUG) {
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            }

            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(0, TimeUnit.SECONDS)
                    .readTimeout(0, TimeUnit.SECONDS)
                    .writeTimeout(0, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(REQUEST_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static String makeRequestUrlForPoster(String posterPath) {
        Uri.Builder uriBuilder = Uri.parse(IMAGE_REQUEST_URL)
                .buildUpon()
                .appendEncodedPath(posterPath);
        return uriBuilder.toString();
    }

    public static String makeRequestUrlForBackdrop(String backdropPath) {
        return BACKDROP_URL + backdropPath;
    }

}
