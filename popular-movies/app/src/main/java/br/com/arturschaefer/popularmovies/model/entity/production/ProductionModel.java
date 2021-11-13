package br.com.arturschaefer.popularmovies.model.entity.production;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("logo_path")
    @Expose
    private String logoPath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("origin_country")
    @Expose
    private String originCountry;

    protected ProductionModel(Parcel in) {
        this.id = in.readInt();
        this.logoPath = in.readString();
        this.name = in.readString();
        this.originCountry = in.readString();
    }

    public int getId() {
        return id;
    }

    public Object getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductionModel() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.logoPath);
        dest.writeString(this.name);
        dest.writeString(this.originCountry);
    }

    public static final Parcelable.Creator<ProductionModel> CREATOR = new Parcelable.Creator<ProductionModel>() {
        @Override
        public ProductionModel createFromParcel(Parcel source) {
            return new ProductionModel(source);
        }

        @Override
        public ProductionModel[] newArray(int size) {
            return new ProductionModel[size];
        }
    };
}