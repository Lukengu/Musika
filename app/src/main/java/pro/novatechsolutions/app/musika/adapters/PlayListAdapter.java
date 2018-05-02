package pro.novatechsolutions.app.musika.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pro.novatechsolutions.app.musika.R;
import pro.novatechsolutions.app.musika.deezer.api.response.PlaylistResponse;

public class PlayListAdapter extends ArrayAdapter<PlaylistResponse> {
    private Context context;
    private Handler handler = new Handler();
    private LayoutInflater playlistInf;
    private ArrayList<PlaylistResponse> playlists;


    public PlayListAdapter(Context context, ArrayList<PlaylistResponse> playlists) {
        super(context, R.layout.artist_item, playlists);
        this.playlists = playlists;
        this.playlistInf = LayoutInflater.from(context);
        this.context = context;
    }

    public int getCount() {
        return this.playlists.size();
    }

    public PlaylistResponse getItem(int arg0) {
        return (PlaylistResponse) this.playlists.get(arg0);
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        android.support.v7.widget.CardView playlisttLayout = (android.support.v7.widget.CardView) this.playlistInf.inflate(R.layout.artist_item, parent, false);
        final ImageView cover = (ImageView) playlisttLayout.findViewById(R.id.thumbnail);
        final PlaylistResponse playlist = (PlaylistResponse) this.playlists.get(position);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ProximaNova-Regular.otf");

        Glide
                .with(context)
                .load(getItem(position).getPicture_big())
                .placeholder(R.drawable.ic_loading)
                .centerCrop()
                .into(cover);

        return playlisttLayout;
    }
}
