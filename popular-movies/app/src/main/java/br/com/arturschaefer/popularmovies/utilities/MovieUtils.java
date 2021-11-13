package br.com.arturschaefer.popularmovies.utilities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

public class MovieUtils {
    //Constants
    public static final String TOP_RATED = "top_rated";
    public static final String POPULAR = "popular";

    static ProgressFragment progressFragment;

    public MovieUtils(){
    }

    public static void createProgress(FragmentTransaction fm) {
        if (progressFragment == null) {
            progressFragment = ProgressFragment.newInstance();
            progressFragment.show(fm, "progress_fragment");
        } else {
            progressFragment.show(fm, "progress_fragment");
        }
    }

    public static void hideProgress() {
        if (progressFragment != null) {
            progressFragment.dismiss();
            progressFragment = null;
        }
    }

    public static boolean isDeviceConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
