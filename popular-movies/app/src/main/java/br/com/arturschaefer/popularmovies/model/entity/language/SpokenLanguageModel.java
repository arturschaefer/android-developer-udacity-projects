package br.com.arturschaefer.popularmovies.model.entity.language;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpokenLanguageModel implements Parcelable {

        @SerializedName("iso_639_1")
        @Expose
        private String iso6391;
        @SerializedName("name")
        @Expose
        private String name;

        public String getIso6391() {
            return iso6391;
        }

        public void setIso6391(String iso6391) {
            this.iso6391 = iso6391;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iso6391);
        dest.writeString(this.name);
    }

    public SpokenLanguageModel() {
    }

    protected SpokenLanguageModel(Parcel in) {
        this.iso6391 = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<SpokenLanguageModel> CREATOR = new Parcelable.Creator<SpokenLanguageModel>() {
        @Override
        public SpokenLanguageModel createFromParcel(Parcel source) {
            return new SpokenLanguageModel(source);
        }

        @Override
        public SpokenLanguageModel[] newArray(int size) {
            return new SpokenLanguageModel[size];
        }
    };
}
