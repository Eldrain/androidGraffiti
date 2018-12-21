package ru.artnnv.graffititask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artnnv.graffititask.data.ArtWork;

public class GraffitiActivity extends AppCompatActivity {

    private static String NOT_SPECIFIED;
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
    //@BindView(R.id.graffitiInfoImage)
    //ImageView imagePhoto;
    @BindView(R.id.graffitiInfoImageNoPhoto)
    TextView textNoPhoto;
    @BindView(R.id.graffitiInfoImageContainer)
    RelativeLayout graffitiPhotoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graffiti);
        ButterKnife.bind(this);

        NOT_SPECIFIED = getResources().getString(R.string.not_specified);
        AUTHOR_PREFIX = getResources().getString(R.string.author_prefix);
        ADDRESS_PREFIX = getResources().getString(R.string.address_prefix);
        DESC_PREFIX = getResources().getString(R.string.description_prefix);

        Bundle graffitiData = getIntent().getExtras()
                .getBundle(MainActivity.KEY_GRAFFITI_DATA);

        String label = graffitiData.getString(ArtWork.KEY_LABEL);
        //String author = graffitiData.getString(ArtWork.KEY_AUTHOR);
        String address = graffitiData.getString(ArtWork.KEY_ADDRESS);
        String description = graffitiData.getString(ArtWork.KEY_DESCRIPTION);
        //String imageUrl = graffitiData.getString(ArtWork.KEY_IMAGE_URL);

        ArrayList<String> authorsList =
                graffitiData.getStringArrayList(ArtWork.KEY_AUTHOR);
        String authors = "";

        if(authorsList.size() == 0) {
            authors = NOT_SPECIFIED;
        } else {
            boolean first = true;
            for(String item : authorsList) {
                if(first) {
                    first = false;
                } else {
                    authors = authors.concat("\n");
                }
                authors = authors.concat(item);
            }
        }

        ArrayList<String> imageUrls =
                graffitiData.getStringArrayList(ArtWork.KEY_IMAGE_URL);

        textLabel.setText(label);
        textAuthor.setText(AUTHOR_PREFIX.concat(authors));
        textAddress.setText(ADDRESS_PREFIX.concat(address));
        textDescription.setText(DESC_PREFIX.concat(description));

        if(imageUrls.size() == 0) {
            //imagePhoto.setVisibility(View.GONE);
            textNoPhoto.setVisibility(View.VISIBLE);
        } else {
            //imagePhoto.setVisibility(View.VISIBLE);
            for(String url : imageUrls) {
                insertImage(url);
            }
            textNoPhoto.setVisibility(View.GONE);
        }
    }

    /**
     * Insert graffiti photo in container
     * @param url
     */
    private void insertImage(String url) {
        ImageView photo = new ImageView(this);
        Picasso.get().load(url).into(photo);
        graffitiPhotoContainer.addView(photo);
    }

}
