package br.com.arturschaefer.popularmovies.application;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.annotation.Nullable;

import br.com.arturschaefer.popularmovies.BuildConfig;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieModel;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieResult;
import br.com.arturschaefer.popularmovies.model.entity.user.UserDetails;
import br.com.arturschaefer.popularmovies.utilities.MovieUtils;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class MoviesApplication extends android.app.Application {
    private static Context appContext;
    private static String appDir;

    public static MovieResult movieResults;
    public static HashMap<Integer, String> genres;
    public static String currentListType;
    public static FirebaseUser firebaseUser;
    public static FirebaseAuth firebaseAuth;
    public static UserDetails userDetails;
    public static ArrayList<MovieModel> favoritesMovies;
    public static ArrayList<MovieModel> watchedMovies;

    public static Context getAppContext() {
        return appContext;
    }

    public static Activity mCurrentActivity = null;

    private static FirebaseAnalytics mFirebaseAnalytics;

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }

    public static FirebaseAnalytics getFirebaseAnalytisInstance() {
        return mFirebaseAnalytics;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        appContext = this;

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        appDir = getCacheDir().getAbsolutePath() + "/";
        File dir = new File(appDir);
        dir.mkdirs();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        currentListType = MovieUtils.TOP_RATED;

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore firestoreClient = FirebaseFirestore.getInstance();
        firestoreClient.setFirestoreSettings(settings);

        Fabric.with(this, new Crashlytics());
        getFavorites();
        getWatched();
    }

    public static MovieResult getMovieResults() {
        return movieResults;
    }

    public static HashMap<Integer, String> getGenres() {
        return genres;
    }

    public static void setGenres(HashMap<Integer, String> genres) {
        MoviesApplication.genres = genres;
    }

    public static String getAppDir() {
        return appDir;
    }

    public void getFavorites() {
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        try {
            firebaseFirestore
                    .collection("users")
                    .document(Objects.requireNonNull(user).getUid())
                    .collection("favorites")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            MoviesApplication.favoritesMovies = new ArrayList<>();
                            if (Objects.requireNonNull(queryDocumentSnapshots).getDocuments().size() > 0) {
                                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                                    MoviesApplication.favoritesMovies.add(snapshot.toObject(MovieModel.class));
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Logger.e(e.toString());
        }

    }

    public void getWatched() {
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        try {
            firebaseFirestore
                    .collection("users")
                    .document(Objects.requireNonNull(user).getUid())
                    .collection("watched")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            MoviesApplication.watchedMovies = new ArrayList<>();
                            if (Objects.requireNonNull(queryDocumentSnapshots).getDocuments().size() > 0) {
                                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                                    MoviesApplication.watchedMovies.add(snapshot.toObject(MovieModel.class));
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }
}

