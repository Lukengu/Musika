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
import pro.novatechsolutions.app.musika.deezer.api.response.AlbumResponse;

public class AlbumAdapter extends ArrayAdapter<AlbumResponse> {
    private Context context;
    private Handler handler = new Handler();
    private LayoutInflater artistsInf;
    private ArrayList<AlbumResponse> albumResponse;


    public AlbumAdapter(Context context, ArrayList<AlbumResponse> albumResponse) {
        super(context, R.layout.artist_item, albumResponse);

        this.albumResponse = albumResponse;
        this.artistsInf = LayoutInflater.from(context);
        this.context = context;
    }

    public int getCount() {
        return this.albumResponse.size();
    }

    public AlbumResponse getItem(int arg0) {
        return albumResponse.get(arg0);
    }

    public long getItemId(long arg0) {
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CardView artistLayout = (CardView) artistsInf.inflate(R.layout.artist_item, parent, false);
        ImageView cover = (ImageView) artistLayout.findViewById(R.id.thumbnail);
        LinearLayout  linearLayout  = artistLayout.findViewById(R.id.title_container);
        TextView title = artistLayout.findViewById(R.id.title);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ProximaNova-Regular.otf");
        linearLayout.setVisibility(View.VISIBLE);
        title.setText(getItem(position).getTitle()+" - "+ getItem(position).getArtist());
        title.setTextColor(Color.WHITE);
        title.setTypeface(tf);
        title.setTextSize(11);
        title.setMaxLines(1);
        title.setMaxWidth(65);
        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);


        Glide
                .with(context)
                .load(getItem(position).getCover_big())
                .placeholder(R.drawable.ic_loading)
                .centerCrop()
                .into(cover);


        return artistLayout;
    }
}

