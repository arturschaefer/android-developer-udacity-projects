package br.com.arturschaefer.popularmovies.model.remote;

import br.com.arturschaefer.popularmovies.model.entity.genre.Genres;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieModel;
import br.com.arturschaefer.popularmovies.model.entity.movie.MovieResult;
import br.com.arturschaefer.popularmovies.model.entity.review.ReviewResult;
import br.com.arturschaefer.popularmovies.model.entity.trailer.TrailerResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Services {
    @GET("movie/top_rated")
    Call<MovieResult> findTopRated(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Call<MovieResult> findPopular(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/now_playing")
    Call<MovieResult> findOnTheater(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieResult> findUpcoming(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("genre/movie/list")
    Call<Genres> findGenre(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieModel> getDetailsMovie(@Path("id") int movieId,
                                     @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailerResult> getTrailers(@Path("id") int movieId,
                                    @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewResult> getReviews(@Path("id") int movieId,
                                  @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResult> searchMovies(@Query("api_key") String apiKey,
                                   @Query("query") String query,
                                   @Query("page") int page);

}
