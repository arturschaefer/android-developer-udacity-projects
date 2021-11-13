package br.com.arturschaefer.popularmovies.your_movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import br.com.arturschaefer.popularmovies.R;
import br.com.arturschaefer.popularmovies.application.MoviesApplication;
import br.com.arturschaefer.popularmovies.main.MovieAdapter;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieModel;
import br.com.arturschaefer.popularmovies.movie_details.MovieDetailsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static br.com.arturschaefer.popularmovies.utilities.MovieUtils.EXTRA_MOVIE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WatchedMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchedMoviesFragment extends Fragment implements MovieAdapter.CallbackInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MovieAdapter movieAdapter;

    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;
    @BindView(R.id.empty_recycler)
    LottieAnimationView emptyRecycler;
    @BindView(R.id.text_empty)
    TextView textEmpty;
    @BindView(R.id.fragment_movie_list)
    ConstraintLayout fragmentMovieList;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WatchedMoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesMoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WatchedMoviesFragment newInstance(String param1, String param2) {
        WatchedMoviesFragment fragment = new WatchedMoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        adView.setVisibility(View.GONE);
        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieModel>(), this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rvMovieList.setLayoutManager(layoutManager);
        ViewCompat.setNestedScrollingEnabled(rvMovieList, false);

        rvMovieList.setHasFixedSize(true);
        rvMovieList.setAdapter(movieAdapter);

        getWatched();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void selectMovie(MovieModel movie, int position) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    public void getWatched() {
        if (MoviesApplication.watchedMovies == null ||
                MoviesApplication.watchedMovies.size() == 0) {
            showHideList(true);
            textEmpty.setText("You don't register any movie watched");
        } else {
            showHideList(false);
            movieAdapter.newListMovies(MoviesApplication.watchedMovies);
        }
    }

    public void showHideList(boolean isEmpty) {
        if (isEmpty) {
            textEmpty.setVisibility(View.VISIBLE);
            emptyRecycler.setVisibility(View.VISIBLE);
            rvMovieList.setVisibility(View.GONE);
        } else {
            textEmpty.setVisibility(View.GONE);
            emptyRecycler.setVisibility(View.GONE);
            rvMovieList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getWatched();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
