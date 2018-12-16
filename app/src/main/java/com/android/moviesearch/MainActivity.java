package com.android.moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    EditText query;
    Button search;
    ArrayList<items> movieList;
    MyAdapter myAdapter;
    static final String baseURL = "https://openapi.naver.com/v1/search/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        query = (EditText) findViewById(R.id.searchWord);
        search = (Button) findViewById(R.id.search_button);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        movieList = new ArrayList<>();
        myAdapter = new MyAdapter(this, movieList);
        mRecyclerView.setAdapter(myAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                getMovieInfo();
            }
        });

    }

    public void getMovieInfo() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> qmap = new HashMap();
        qmap.put("query", query.getText().toString());
        qmap.put("display", String.valueOf(100));

        String nid = getString(R.string.naverClientId);
        String npwd = getString(R.string.naverClientPwd);
        Map<String, String> hmap = new HashMap();
        hmap.put("X-Naver-Client-Id", nid);
        hmap.put("X-Naver-Client-Secret", npwd);

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<movieRepo> call = retrofitService.getMovieItem(hmap, qmap);
        call.enqueue(new Callback<movieRepo>() {
            @Override
            public void onResponse(Call<movieRepo> call, Response<movieRepo> response) {
                    Log.v("movieList", movieList.toString());
                    movieRepo repo = response.body();

                    for (int i = 0; i < repo.getitemsList().size(); i++) {
                        items movieitem = new items(
                                repo.getitemsList().get(i).getTitle(),
                                repo.getitemsList().get(i).getLink(),
                                repo.getitemsList().get(i).getImage(),
                                repo.getitemsList().get(i).getSubtitle(),
                                repo.getitemsList().get(i).getPubDate(),
                                repo.getitemsList().get(i).getDirector(),
                                repo.getitemsList().get(i).getActor(),
                                repo.getitemsList().get(i).getUserRating()/2
                                );
                        movieList.add(movieitem);
                    }

                    //
                    myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<movieRepo> call, Throwable t) {
                Log.v("failmessage", t.getMessage());
            }
        });
    }

}
