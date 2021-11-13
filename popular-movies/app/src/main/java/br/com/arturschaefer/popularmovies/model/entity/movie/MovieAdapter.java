package br.com.arturschaefer.popularmovies.model.entity.movie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.arturschaefer.popularmovies.R;
import br.com.arturschaefer.popularmovies.application.MoviesApplication;
import br.com.arturschaefer.popularmovies.main.MainActivity;
import br.com.arturschaefer.popularmovies.model.remote.Queries;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MovieModel> mMovies;
    private final CallbackInterface mCallback;

    public interface CallbackInterface {
        void selectMovie(MovieModel movie, int position);
    }

    public MovieAdapter(Context context, List<MovieModel> movies, CallbackInterface mCallback) {
        mMovies = movies;
        mContext = context;
        this.mCallback = mCallback;
    }

    public LayoutInflater getmLayoutInflater() {
        return mLayoutInflater;
    }

    public void setmLayoutInflater(LayoutInflater mLayoutInflater) {
        this.mLayoutInflater = mLayoutInflater;
    }

    public List<MovieModel> getmMovies() {
        return mMovies;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
    }

    private Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void newListMovies(List<MovieModel> movies) {
        this.mMovies.clear();
        this.mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.item_movie_list, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieHolder holder, final int position) {
        assert mMovies != null;
        final MovieModel movie = getmMovies().get(position);
        try {

            Picasso.get()
                    .load(Queries.makeRequestUrlForPoster(movie.getPosterPath()))
                    .into(holder.moviePoster);
            Picasso.get()
                    .load(R.drawable.tmdb)
                    .into(holder.imageLogoTmdb);
            holder.movieName.setText(movie.getTitle());
            holder.textRateTmdb.setText(movie.getVoteAverage().toString());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.selectMovie(movie, holder.getAdapterPosition());
                }
            });

            String string = "";
            if(movie.getGenreIds() == null)
                holder.textGenre.setText("Sem Gêneros Definidos");
            else{
                for(int i = 0; i < movie.getGenreIds().length; i++){
                    string += MoviesApplication.genres.get(movie.getGenreIds()[i]);
                    if(i < movie.getGenreIds().length - 1)
                        string += ", ";
                }
                holder.textGenre.setText(string);
            }
            SimpleDateFormat dateFormatEntrada = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatSaida = new SimpleDateFormat("yyyy");

            Date dataOriginal = null;
            String dataTrocada = null;
            //Transforma a String em Date
            dataOriginal = dateFormatEntrada.parse(movie.getReleaseDate());
            //Transforma a Date num String com o formato pretendido
            dataTrocada = dateFormatSaida.format(dataOriginal);
            holder.textYear.setText(dataTrocada);
        } catch (Exception e) {
            Log.e(LOG_TAG, "OnBindViewHolder " + e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.movie_name)
        TextView movieName;
        @BindView(R.id.text_year)
        TextView textYear;
        @BindView(R.id.text_genre)
        TextView textGenre;
        @BindView(R.id.image_logo_tmdb)
        ImageView imageLogoTmdb;
        @BindView(R.id.text_rate_tmdb)
        TextView textRateTmdb;
        public final View mView;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }

    public void uiThread(final int resource, final View view, final boolean value) {
        try {
            ((MainActivity) mContext).runOnUiThread(new Runnable() {
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(mContext, resource);
                    view.setAnimation(animation);
                    if (value)
                        view.setVisibility(View.VISIBLE);
                    else
                        view.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
