package in.bitbytetech.popularmoviesv2;

import android.content.res.Resources;
import android.util.Log;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Home on 01/02/2017.
 */

public interface MovieApiService {

    @GET ("discover/movie")
    //void getPopularMovies(Callback<Movie.MovieResults> cb);
    Call<MoviesInfo> discoverMovies(
            @QueryMap(encoded=true) Map<String, String> options
    );
}
