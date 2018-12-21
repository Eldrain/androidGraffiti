package ru.artnnv.graffititask.mvp;

import android.os.Bundle;

import java.util.List;

import ru.artnnv.graffititask.data.ArtWork;

/**
 * Author: Artem Novikov
 * Date: 20.12.2018
 * Module name: GraffitiListView
 * Description: GraffitiList MVP View
 */
public interface GraffitiListView extends LoadView {

    void showData(List<ArtWork> artWorkList);

    void openGraffitiScreen(Bundle bundle);

}
