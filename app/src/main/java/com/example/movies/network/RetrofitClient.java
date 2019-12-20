package com.example.movies.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

	private static Retrofit INSTANCE;
	private static final String BASE_URL = "https://api.themoviedb.org/3/";

	public static Retrofit getInstance() {
		if (INSTANCE == null) {

			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
			interceptor.level(HttpLoggingInterceptor.Level.BODY);
			OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

			INSTANCE = new retrofit2.Retrofit.Builder()
					.baseUrl(BASE_URL)
					.client(client)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}
		return INSTANCE;
	}
}