package in.bitbytetech.popularmoviesv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "#PopularMovies: ";

    private static String tmdb_end_point;

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private TextView mErrorMessage;

    private String apiKey;
    private String language;

    private Retrofit retrofit;

    private MovieApiService movieApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tmdb_end_point = getResources().getString(R.string.moviedb_endpoint);

        apiKey = getResources().getString(R.string.moviedb_api_key);

        language = getResources().getString(R.string.moviedb_language);

        mErrorMessage = (TextView) findViewById(R.id.error_message);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        movieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(movieAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(tmdb_end_point)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApiService = retrofit.create(MovieApiService.class);

        fetchPopularMovies();
    }

    private void fetchPopularMovies() {
        Call<MoviesInfo> popularMovies = movieApiService.getPopularMovies(apiKey, language);
        popularMovies.enqueue(new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {
                if ( response.isSuccessful() )
                    updateMovieAdapter(response.body().movieList);
                else
                    showErrorMessage();
            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchTopRatedMovies() {
        Call<MoviesInfo> popularMovies = movieApiService.getTopRatedMovies(apiKey, language);
        popularMovies.enqueue(new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {
                if ( response.isSuccessful() )
                    updateMovieAdapter(response.body().movieList);
                else
                    showErrorMessage();
            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void updateMovieAdapter(List<Movie> movies) {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

        movieAdapter.setMovieList(movies);
    }

    private void showErrorMessage() {
        mErrorMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_popular_movies) {
            return true;
        }*/

        switch (id) {
            case R.id.action_popular_movies: {
                fetchPopularMovies();
                break;
            }
            case R.id.action_toprated_movies: {
                fetchTopRatedMovies();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
