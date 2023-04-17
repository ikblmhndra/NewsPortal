package com.nameless.newsportal;

import com.nameless.newsportal.models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponses> {
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
