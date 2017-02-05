package in.bitbytetech.popularmoviesv2;

import android.content.res.Resources;
import android.util.Log;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Home on 01/02/2017.
 */

public interface MovieApiService {

    @GET ("discover/movie")
    Call<MoviesInfo> discoverMovies(
            @QueryMap(encoded=true) Map<String, String> options
    );

    @GET ("movie/popular")
    Call<MoviesInfo> getPopularMovies(
            @Query(value="api_key", encoded = true) String apiKey,
            @Query(value="language", encoded = true) String language
    );

    //movie/top_rated
    @GET ("movie/top_rated")
    Call<MoviesInfo> getTopRatedMovies(
            @Query(value="api_key", encoded = true) String apiKey,
            @Query(value="language", encoded = true) String language
    );

}
