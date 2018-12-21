package ru.artnnv.graffititask.mvp;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import ru.artnnv.graffititask.api.GraffitiApi;
import ru.artnnv.graffititask.data.ArtWork;
import ru.artnnv.graffititask.data.Artist;
import ru.artnnv.graffititask.data.Photo;

import static ru.artnnv.graffititask.data.ArtWork.KEY_AUTHOR;

/**
 * Author: Artem Novikov
 * Date: 20.12.2018
 * Module name: GraffitiPresenter
 * Description: Graffiti Screen MVP Presenter
 */
public class GraffitiPresenter implements IPresenter {
    private static final String LOG = "GraffitiPresenter";
    private static final String REQUEST_ERROR_MESSAGE = "Ошибка запроса." +
            " Пожалуйста, проверьте интернет-соединение";

    private GraffitiListView mView;
    private CompositeDisposable mComposite;

    public GraffitiPresenter(GraffitiListView view) {
        mView = view;
        mComposite = new CompositeDisposable();
    }

    public void loadData() {
        mView.showLoading();
        mComposite.add(GraffitiApi.getInstance().getArtWorks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<List<ArtWork>>() {

                            @Override
                            public void onSuccess(List<ArtWork> artWorks) {
                                mView.hideLoading();
                                mView.showData(artWorks);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.hideLoading();
                                mView.showRequestError(REQUEST_ERROR_MESSAGE);
                                Log.e(LOG, "Message: " + e.getMessage(), e);
                            }
                        }));
    }

    public void itemSelected(ArtWork artWork) {
        Bundle bundle = new Bundle();

        bundle.putString(ArtWork.KEY_LABEL, artWork.getName());
        //bundle.putString(ArtWork.KEY_AUTHOR, artWork.getAuthor());
        //bundle.putString(ArtWork.KEY_IMAGE_URL, artWork.getImageUrl());
        bundle.putString(ArtWork.KEY_ADDRESS, artWork.getAddress());
        bundle.putString(ArtWork.KEY_DESCRIPTION, artWork.getDescription());

        ArrayList<String> authorsList = new ArrayList<>();

        if(artWork.getArtists() != null) {
            for(Artist item : artWork.getArtists()) {
                if(item.getName() != null
                        && !item.getName().equals("")) {
                    authorsList.add(item.getName());
                }

            }
        }

        ArrayList<String> imageUrls = new ArrayList<>();

        if(artWork.getPhotos() != null) {
            for(Photo item : artWork.getPhotos()) {
                if(item.getImageUrl() != null
                        && !item.getImageUrl().equals("")) {
                    imageUrls.add(item.getImageUrl());
                }
            }
        }

        bundle.putStringArrayList(KEY_AUTHOR, authorsList);
        bundle.putStringArrayList(ArtWork.KEY_IMAGE_URL, imageUrls);

        mView.openGraffitiScreen(bundle);
    }

    @Override
    public void unsubscribeOnDestroy() {
        mComposite.clear();
    }
}
