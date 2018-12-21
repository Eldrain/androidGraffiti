package ru.artnnv.graffititask.api;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import ru.artnnv.graffititask.data.ArtWork;

/**
 * Author: Artem Novikov
 * Date: 20.12.2018
 * Module name: GraffitiApi
 * Description: Graffiti Api singleton
 */
public class GraffitiApi {
    public static final String BASE_URL = "https://street-art-server.herokuapp.com";

    private static final GraffitiApi ourInstance = new GraffitiApi();

    public static GraffitiApi getInstance() {
        return ourInstance;
    }

    private GraffitiApiInterface mApi;

    private GraffitiApi() {
        mApi = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GraffitiApiInterface.class);
    }

    public Single<List<ArtWork>> getArtWorks() {
        return mApi.getImages().subscribeOn(Schedulers.io());
    }


    interface GraffitiApiInterface {
        @GET("/artworks")
        Single<List<ArtWork>> getImages();
    }
}
