package com.nameless.newsportal;

import android.content.Context;
import android.widget.Toast;

import com.nameless.newsportal.models.NewsApiResponses;
import com.nameless.newsportal.models.NewsHeadlines;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    List<NewsHeadlines> newsArticel = new ArrayList<>();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(MainActivity context) {
        this.context = context;
    }

    public void getNewsHeadlines(OnFetchDataListener listener, String query, String catagory){
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponses> call = callNewsApi.callHeadlines("us",catagory, query, context.getString(R.string.api_key));

        try {
            call.enqueue(new Callback<NewsApiResponses>() {
                @Override
                public void onResponse(Call<NewsApiResponses> call, Response<NewsApiResponses> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Error!!!", Toast.LENGTH_SHORT).show();
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponses> call, Throwable t) {
                    listener.onError("Request Failed!!!");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface CallNewsApi{
        @GET("top-headlines")
        Call<NewsApiResponses> callHeadlines(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String api_key
        );
    }
}
