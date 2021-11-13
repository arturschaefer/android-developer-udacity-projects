package br.com.arturschaefer.popularmovies.your_movies;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.arturschaefer.popularmovies.R;

public class YourMoviesPageAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public YourMoviesPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FavoritesMoviesFragment();
        } else if (position == 1) {
            return new WatchedMoviesFragment();
        }
        return new FavoritesMoviesFragment();
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_favorites);
            case 1:
                return mContext.getString(R.string.title_watched);
            default:
                return null;
        }
    }
}
