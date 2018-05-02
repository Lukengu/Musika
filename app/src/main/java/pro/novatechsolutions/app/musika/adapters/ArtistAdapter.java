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
import pro.novatechsolutions.app.musika.deezer.api.response.ArtistResponse;

public class ArtistAdapter  extends ArrayAdapter<ArtistResponse> {
    private Context context;
    private Handler handler = new Handler();
    private LayoutInflater artistsInf;
    private ArrayList<ArtistResponse> artists;


    public ArtistAdapter(Context context, ArrayList<ArtistResponse> artists) {
        super(context, R.layout.artist_item, artists);

        this.artists = artists;
        this.artistsInf = LayoutInflater.from(context);
        this.context = context;
    }

    public int getCount() {
        return this.artists.size();
    }

    public ArtistResponse getItem(int arg0) {
        return artists.get(arg0);
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CardView artistLayout = (CardView) artistsInf.inflate(R.layout.artist_item, parent, false);
        ImageView cover = (ImageView) artistLayout.findViewById(R.id.thumbnail);
        LinearLayout  linearLayout  = artistLayout.findViewById(R.id.title_container);
        TextView title = artistLayout.findViewById(R.id.title);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ProximaNova-Regular.otf");
        linearLayout.setVisibility(View.VISIBLE);
        title.setText(getItem(position).getName());
        title.setTextColor(Color.WHITE);
        title.setTypeface(tf);
        title.setTextSize(11);
        title.setMaxLines(1);
        title.setMaxWidth(65);
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

