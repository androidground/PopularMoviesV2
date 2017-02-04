package in.bitbytetech.popularmoviesv2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 01/02/2017.
 */

public class Movie implements Parcelable {

    //private String poster_path;
    //private String overview;
    //private int id;
    //private String title;
    //private String backdrop_path;
    //private String popularity;
    //private String vote_count;
    //private String vote_average;

    @SerializedName("id")
    public int id;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("popularity")
    public float popularity;

    @SerializedName("title")
    public String title;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public int voteCount;

    /*"poster_path": "/WLQN5aiQG8wc9SeKwixW7pAR8K.jpg",
    "overview": "The quiet life of a terrier named Max is upended when his owner takes in Duke, a stray whom Max instantly dislikes.",
    "id": 328111,
    "title": "The Secret Life of Pets",
    "backdrop_path": "/lubzBMQLLmG88CLQ4F3TxZr2Q7N.jpg",
    "popularity": 181.40313,
    "vote_count": 1960,
    "vote_average": 5.8*/

    public Movie(int id, String overview, String releaseDate, String posterPath, String backdropPath, long popularity, String title, long voteAverage, int voteCount) {

        this.id = id;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = "http://image.tmdb.org/t/p/w500" + posterPath;
        this.backdropPath = "http://image.tmdb.org/t/p/w500" + backdropPath;
        this.popularity = popularity;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString("http://image.tmdb.org/t/p/w500" + posterPath);
        dest.writeString("http://image.tmdb.org/t/p/w500" + backdropPath);
        dest.writeFloat(popularity);
        dest.writeString(title);
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
    }

    public Movie(Parcel parcel) {
        this.id = parcel.readInt();
        this.overview = parcel.readString();
        this.releaseDate = parcel.readString();
        this.posterPath = parcel.readString();
        this.backdropPath = parcel.readString();
        this.popularity = parcel.readFloat();
        this.title = parcel.readString();
        this.voteAverage = parcel.readFloat();
        this.voteCount = parcel.readInt();

    }


    public static Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
