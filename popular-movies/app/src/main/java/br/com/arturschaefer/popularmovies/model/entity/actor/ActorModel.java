
package br.com.arturschaefer.popularmovies.model.entity.actor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class ActorModel {

    @SerializedName("adult")
    @Expose
    private Boolean mAdult;
    @SerializedName("also_known_as")
    @Expose
    private List<String> mAlsoKnownAs;
    @SerializedName("biography")
    @Expose
    private String mBiography;
    @SerializedName("birthday")
    @Expose
    private String mBirthday;
    @SerializedName("deathday")
    @Expose
    private Object mDeathday;
    @SerializedName("gender")
    @Expose
    private Long mGender;
    @SerializedName("homepage")
    @Expose
    private String mHomepage;
    @SerializedName("id")
    @Expose
    private Long mId;
    @SerializedName("imdb_id")
    @Expose
    private String mImdbId;
    @SerializedName("known_for_department")
    @Expose
    private String mKnownForDepartment;
    @Expose
    @SerializedName("name")
    private String mName;
    @SerializedName("place_of_birth")
    @Expose
    private String mPlaceOfBirth;
    @SerializedName("popularity")
    @Expose
    private Double mPopularity;
    @SerializedName("profile_path")
    @Expose
    private String mProfilePath;

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public List<String> getAlsoKnownAs() {
        return mAlsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        mAlsoKnownAs = alsoKnownAs;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public Object getDeathday() {
        return mDeathday;
    }

    public void setDeathday(Object deathday) {
        mDeathday = deathday;
    }

    public Long getGender() {
        return mGender;
    }

    public void setGender(Long gender) {
        mGender = gender;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public void setHomepage(String homepage) {
        mHomepage = homepage;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public void setImdbId(String imdbId) {
        mImdbId = imdbId;
    }

    public String getKnownForDepartment() {
        return mKnownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        mKnownForDepartment = knownForDepartment;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

}
