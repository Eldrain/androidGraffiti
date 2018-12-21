package ru.artnnv.graffititask.mvp;

/**
 * Author: Artem Novikov
 * Date: 20.12.2018
 * Module name: LoadView
 * Description: Load MVP View
 */
public interface LoadView extends IView {

    void showLoading();

    void hideLoading();

    void showRequestError(String errorMessage);
}
