package br.com.arturschaefer.popularmovies.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.arturschaefer.popularmovies.R;
import butterknife.ButterKnife;

public class NoConnectionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_no_connection, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }
}
