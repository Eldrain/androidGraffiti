package ru.artnnv.graffititask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import ru.artnnv.graffititask.adapters.GraffitiListAdapter;
import ru.artnnv.graffititask.data.ArtWork;
import ru.artnnv.graffititask.mvp.GraffitiListView;
import ru.artnnv.graffititask.mvp.GraffitiPresenter;

public class MainActivity extends AppCompatActivity implements GraffitiListView {
    public static final String KEY_GRAFFITI_DATA = "KEY_GRAFFITI_DATA";

    @BindView(R.id.graffitiListProgressContainer)
    RelativeLayout progressContainer;
    @BindView(R.id.graffitiListRecyclerView)
    RecyclerView recyclerViewArtWorksList;

    private GraffitiPresenter mPresenter;
    private List<ArtWork> mLoadedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GraffitiPresenter(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mLoadedData == null) {
            mPresenter.loadData();
        }
    }

    @Override
    public void showData(List<ArtWork> artWorkList) {
        mLoadedData = artWorkList;
        recyclerViewArtWorksList.setAdapter(
                new GraffitiListAdapter(artWorkList, this, mPresenter));
    }

    @Override
    public void openGraffitiScreen(Bundle bundle) {
        Intent intent = new Intent(this, GraffitiActivity.class);
        intent.putExtra(KEY_GRAFFITI_DATA, bundle);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        progressContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRequestError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribeOnDestroy();
    }
}
