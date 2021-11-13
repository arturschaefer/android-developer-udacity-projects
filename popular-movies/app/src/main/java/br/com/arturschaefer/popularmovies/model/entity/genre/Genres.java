package br.com.arturschaefer.popularmovies.model.entity.genre;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Genres {
    public ArrayList<GenreModel> getGenreModels() {
        return genreModels;
    }

    public void setGenreModels(ArrayList<GenreModel> genreModels) {
        this.genreModels = genreModels;
    }

    @SerializedName("genres")
    @Expose
    ArrayList<GenreModel> genreModels;

    public Genres() {
    }
}
