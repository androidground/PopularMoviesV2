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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tmdb_end_point = getResources().getString(R.string.moviedb_endpoint);

        mErrorMessage = (TextView) findViewById(R.id.error_message);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        movieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(movieAdapter);

        fetchData(R.id.action_popular_movies);
    }

    private void fetchData(int sortType) {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(tmdb_end_point)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Map<String, String> queryData = new HashMap<String, String>();
        queryData.put("api_key", getResources().getString(R.string.moviedb_api_key));
        queryData.put("language", getResources().getString(R.string.moviedb_language));

        if ( sortType == R.id.action_popular_movies ) {
            queryData.put("sort_by", getResources().getString(R.string.sort_popular_movies));
        } else if ( sortType == R.id.action_toprated_movies) {
            queryData.put("sort_by", getResources().getString(R.string.sort_top_rated));
        }


        Call<MoviesInfo> discoverMovies = movieApiService.discoverMovies(queryData);

        discoverMovies.enqueue(new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {
                if ( response.isSuccessful() )
                    //movieAdapter.setMovieList(response.body().movieList);
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
                fetchData(R.id.action_popular_movies);
                break;
            }
            case R.id.action_toprated_movies: {
                fetchData(R.id.action_toprated_movies);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
