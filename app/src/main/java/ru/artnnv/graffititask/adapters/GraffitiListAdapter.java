package ru.artnnv.graffititask.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.artnnv.graffititask.R;
import ru.artnnv.graffititask.data.ArtWork;
import ru.artnnv.graffititask.mvp.GraffitiPresenter;

/**
 * Author: Artem Novikov
 * Date: 21.12.2018
 * Module name: GraffitiListAdapter
 * Description: Graffiti List Adapter for recycler view
 */
public class GraffitiListAdapter extends RecyclerView.Adapter<GraffitiListAdapter.ViewHolder> {
    private static final String NOT_SPECIFIED = "не указано";
    private static String AUTHOR_PREFIX;

    private List<ArtWork> mList;
    private LayoutInflater mInflater;
    private GraffitiPresenter mGraffitiPresenter;

    public GraffitiListAdapter(List<ArtWork> artWorks, Context context,
                               GraffitiPresenter graffitiPresenter) {
        mList = artWorks;
        mInflater = LayoutInflater.from(context);
        mGraffitiPresenter = graffitiPresenter;
        AUTHOR_PREFIX = context.getResources().getString(R.string.author_prefix);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.graffiti_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ArtWork artWork = mList.get(i);
        String author = (artWork.getArtists().size() > 0)?
                artWork.getArtists().get(0).getName() : NOT_SPECIFIED;
        String imageUrl = null;
        if(artWork.getPhotos() != null) {
            imageUrl = artWork.getPhotos().get(0).getImageUrl();
        }

        viewHolder.label.setText(artWork.getName());
        viewHolder.author.setText(AUTHOR_PREFIX.concat(author));

        if(imageUrl != null) {
            viewHolder.noPhoto.setVisibility(View.GONE);
            viewHolder.photo.setVisibility(View.VISIBLE);
            Picasso.get().load(imageUrl).into(viewHolder.photo);
        } else {
            viewHolder.noPhoto.setVisibility(View.VISIBLE);
            viewHolder.photo.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mGraffitiPresenter != null) {
                    mGraffitiPresenter.itemSelected(artWork);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        TextView author;
        ImageView photo;
        TextView noPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.graffitiItemName);
            author = itemView.findViewById(R.id.graffitiItemAuthor);
            photo = itemView.findViewById(R.id.graffitiItemImage);
            noPhoto = itemView.findViewById(R.id.graffitiItemImageNoPhoto);
        }
    }
}
