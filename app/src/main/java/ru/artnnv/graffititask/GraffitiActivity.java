package ru.artnnv.graffititask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artnnv.graffititask.data.ArtWork;

public class GraffitiActivity extends AppCompatActivity {

    private static String AUTHOR_PREFIX;
    private static String ADDRESS_PREFIX;
    private static String DESC_PREFIX;

    @BindView(R.id.graffitiInfoName)
    TextView textLabel;
    @BindView(R.id.graffitiInfoAuthor)
    TextView textAuthor;
    @BindView(R.id.graffitiInfoAddress)
    TextView textAddress;
    @BindView(R.id.graffitiInfoDescription)
    TextView textDescription;
    @BindView(R.id.graffitiInfoImage)
    ImageView imagePhoto;
    @BindView(R.id.graffitiInfoImageNoPhoto)
    TextView textNoPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graffiti);
        ButterKnife.bind(this);

        AUTHOR_PREFIX = getResources().getString(R.string.author_prefix);
        ADDRESS_PREFIX = getResources().getString(R.string.address_prefix);
        DESC_PREFIX = getResources().getString(R.string.description_prefix);

        Bundle graffitiData = getIntent().getExtras()
                .getBundle(MainActivity.KEY_GRAFFITI_DATA);

        String label = graffitiData.getString(ArtWork.KEY_LABEL);
        String author = graffitiData.getString(ArtWork.KEY_AUTHOR);
        String address = graffitiData.getString(ArtWork.KEY_ADDRESS);
        String description = graffitiData.getString(ArtWork.KEY_DESCRIPTION);
        String imageUrl = graffitiData.getString(ArtWork.KEY_IMAGE_URL);

        textLabel.setText(label);
        textAuthor.setText(AUTHOR_PREFIX.concat(author));
        textAddress.setText(ADDRESS_PREFIX.concat(address));
        textDescription.setText(DESC_PREFIX.concat(description));

        if(imageUrl == null) {
            imagePhoto.setVisibility(View.GONE);
            textNoPhoto.setVisibility(View.VISIBLE);
        } else {
            imagePhoto.setVisibility(View.VISIBLE);
            Picasso.get().load(imageUrl).into(imagePhoto);
            textNoPhoto.setVisibility(View.GONE);
        }
    }


}
