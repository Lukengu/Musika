package pro.novatechsolutions.app.musika.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pro.novatechsolutions.app.musika.R;
import pro.novatechsolutions.app.musika.deezer.api.response.GenreResponse;

public class GenreAdapter extends ArrayAdapter<GenreResponse> {
    private Context context;
    private Handler handler = new Handler();
    private LayoutInflater  layoutInflater;
    private ArrayList<GenreResponse> genres;


    public GenreAdapter(Context context, ArrayList<GenreResponse> genres) {
        super(context, R.layout.artist_item, genres);

        this.genres = genres;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getCount() {
        return this.genres.size();
    }

    public GenreResponse getItem(int arg0) {
        return genres.get(arg0);
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CardView artistLayout = (CardView) layoutInflater.inflate(R.layout.artist_item, parent, false);
        ImageView cover = (ImageView) artistLayout.findViewById(R.id.thumbnail);
        LinearLayout  linearLayout  = artistLayout.findViewById(R.id.title_container);
        TextView title = artistLayout.findViewById(R.id.title);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ProximaNova-Regular.otf");
        linearLayout.setVisibility(View.VISIBLE);
        title.setText(getItem(position).getName());
        title.setTextColor(Color.WHITE);
        title.setTypeface(tf);
        title.setTextSize(11);
        //title.setMaxLines(1);

        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);


        Glide
                .with(context)
                .load(getItem(position).getPicture_big())
                .placeholder(R.drawable.ic_loading)
                .centerCrop()
                .into(cover);


        return artistLayout;
    }
}

