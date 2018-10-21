package com.nabil.nahla.moviesmvp.ui.listMovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nabil.nahla.moviesmvp.R;
import com.nabil.nahla.moviesmvp.data.models.Movie;
import com.nabil.nahla.moviesmvp.utils.GlideApp;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nabil.nahla.moviesmvp.data.network.ApiEndPoints.IMAGE_BASE_URL;

class MovieVH extends RecyclerView.ViewHolder {

    @BindView(R.id.posterIV)
    ImageView posterIV;

    @BindView(R.id.titleTV)
    TextView titleTV;

    @BindView(R.id.yearTV)
    TextView yearTV;

    @BindView(R.id.ratingTV)
    TextView ratingTV;

    @BindView(R.id.overviewTV)
    TextView overviewTV;

    public MovieVH(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void clear() {
        GlideApp.with(itemView.getContext())
                .load(R.color.colorAccent)
                .fitCenter()
                .into(posterIV);

        titleTV.setText("");
        yearTV.setText("");
        ratingTV.setText("");
        overviewTV.setText("");
    }

    public void onBind(ArrayList<Movie> movies) {
        GlideApp.with(itemView.getContext())
                .load(IMAGE_BASE_URL + movies.get(getAdapterPosition()).getPosterPath())
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                .fitCenter()
                .into(posterIV);

        titleTV.setText(movies.get(getAdapterPosition()).getTitle());
        yearTV.setText(movies.get(getAdapterPosition()).getReleaseDate());
        ratingTV.setText(itemView.getContext().getResources()
                .getString(R.string.rating, movies.get(getAdapterPosition()).getVoteAverage()));
        overviewTV.setText(movies.get(getAdapterPosition()).getOverview());
    }
}
