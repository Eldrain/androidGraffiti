package ru.artnnv.graffititask.mvp;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import ru.artnnv.graffititask.api.GraffitiApi;
import ru.artnnv.graffititask.data.ArtWork;

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
        bundle.putString(ArtWork.KEY_AUTHOR, artWork.getAuthor());
        bundle.putString(ArtWork.KEY_IMAGE_URL, artWork.getImageUrl());
        bundle.putString(ArtWork.KEY_ADDRESS, artWork.getAddress());
        bundle.putString(ArtWork.KEY_DESCRIPTION, artWork.getDescription());

        mView.openGraffitiScreen(bundle);
    }

    @Override
    public void unsubscribeOnDestroy() {
        mComposite.clear();
    }
}
