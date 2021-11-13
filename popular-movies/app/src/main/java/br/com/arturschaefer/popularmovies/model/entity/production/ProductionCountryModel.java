package br.com.arturschaefer.popularmovies.model.entity.production;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCountryModel implements Parcelable {

    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("name")
    @Expose
    private String name;

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
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
        dest.writeString(this.iso31661);
        dest.writeString(this.name);
    }

    public ProductionCountryModel() {
    }

    protected ProductionCountryModel(Parcel in) {
        this.iso31661 = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ProductionCountryModel> CREATOR = new Parcelable.Creator<ProductionCountryModel>() {
        @Override
        public ProductionCountryModel createFromParcel(Parcel source) {
            return new ProductionCountryModel(source);
        }

        @Override
        public ProductionCountryModel[] newArray(int size) {
            return new ProductionCountryModel[size];
        }
    };
}