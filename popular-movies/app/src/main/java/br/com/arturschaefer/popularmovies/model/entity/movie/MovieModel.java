package br.com.arturschaefer.popularmovies.model.entity.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.arturschaefer.popularmovies.model.entity.genre.GenreModel;
import br.com.arturschaefer.popularmovies.model.entity.language.SpokenLanguageModel;
import br.com.arturschaefer.popularmovies.model.entity.production.ProductionCountryModel;
import br.com.arturschaefer.popularmovies.model.entity.production.ProductionModel;

@IgnoreExtraProperties
public class MovieModel implements Parcelable {

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("adult")
    @Expose
    private boolean adult;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("genres")
    @Expose
    private List<GenreModel> genres;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("production_companies")
    @Expose
    private List<ProductionModel> productionCompanies;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountryModel> productionCountries;
    @SerializedName("revenue")
    @Expose
    private double revenue;
    @SerializedName("spoken_languages")
    @Expose
    private List<SpokenLanguageModel> spokenLanguages = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("runtime")
    @Expose
    private double runtime;
    @SerializedName("video")
    @Expose
    private boolean video;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds;

    public MovieModel() {
    }

    protected MovieModel(Parcel in) {
        this.adult = in.readByte() != 0;
        this.backdropPath = in.readString();
        this.budget = in.readString();
        this.genres = in.createTypedArrayList(GenreModel.CREATOR);
        this.homepage = in.readString();
        this.id = in.readInt();
        this.imdbId = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
        this.productionCompanies = in.createTypedArrayList(ProductionModel.CREATOR);
        this.productionCountries = in.createTypedArrayList(ProductionCountryModel.CREATOR);
        this.releaseDate = in.readString();
        this.revenue = in.readDouble();
        this.runtime = in.readDouble();
        this.spokenLanguages = in.createTypedArrayList(SpokenLanguageModel.CREATOR);
        this.status = in.readString();
        this.tagline = in.readString();
        this.title = in.readString();
        this.video = in.readByte() != 0;
        this.voteAverage = in.readDouble();
        this.voteCount = in.readInt();
        in.readList(this.genreIds, List.class.getClassLoader());
    }

    public static Creator<MovieModel> getCREATOR() {
        return CREATOR;
    }

    public String genreToString() {
        String string = null;
        for (int i = 0; i < this.genres.size(); i++) {
            string += this.genres.get(i).getName();
            if (i != this.genres.size() - 1) {
                string += ", ";
            }
        }
        return string;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdropPath);
        dest.writeString(this.budget);
        dest.writeTypedList(this.genres);
        dest.writeString(this.homepage);
        dest.writeInt(this.id);
        dest.writeString(this.imdbId);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeValue(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeTypedList(this.productionCompanies);
        dest.writeTypedList(this.productionCountries);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.revenue);
        dest.writeDouble(this.runtime);
        dest.writeTypedList(this.spokenLanguages);
        dest.writeString(this.status);
        dest.writeString(this.tagline);
        dest.writeString(this.title);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.voteAverage);
        dest.writeInt(this.voteCount);
        dest.writeList(this.genreIds);
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBudget() {
        return budget;
    }

    public List<GenreModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreModel> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProductionModel> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionModel> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountryModel> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountryModel> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getRuntime() {
        return runtime;
    }

    public List<SpokenLanguageModel> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguageModel> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRuntime(double runtime) {
        this.runtime = runtime;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }
}