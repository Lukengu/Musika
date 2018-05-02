package pro.novatechsolutions.app.musika.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pro.novatechsolutions.app.musika.R;
import pro.novatechsolutions.app.musika.deezer.api.response.AlbumResponse;
import pro.novatechsolutions.app.musika.deezer.api.response.TrackResponse;
import pro.novatechsolutions.app.musika.utils.CustomMediaPlayer;

public class TrackListAdapter extends ArrayAdapter<TrackResponse> {
    private Context context;
   
    private LayoutInflater layoutInflater;
    private ArrayList<TrackResponse> trackResponse;
    private int  trackPosition;


    public  void setTrackPosition( int trackPosition){
        this.trackPosition = trackPosition;
        notifyDataSetChanged();

    }


    public TrackListAdapter(Context context, ArrayList<TrackResponse> trackResponse) {
        super(context, R.layout.tracklist_view, trackResponse);

        this.trackResponse = trackResponse;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;

    }

    public int getCount() {
        return this.trackResponse.size();
    }

    public TrackResponse getItem(int arg0) {
        return trackResponse.get(arg0);
    }

    public long getItemId(long arg0) {
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.tracklist_view, parent, false);
       
        TextView mTitle = relativeLayout.findViewById(R.id.title); ;
        TextView mDuration=  relativeLayout.findViewById(R.id.duration);
        TextView mMetadata= relativeLayout.findViewById(R.id.metadata);
        ImageView current= relativeLayout.findViewById(R.id.current);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ProximaNova-Regular.otf");
     


        if(position %2 == 1)
        {
            relativeLayout.setBackgroundColor(R.drawable.label_background);
            // imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            relativeLayout.setBackgroundColor(R.drawable.cover_selector);
            // imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }


        if(trackPosition == position){
            current.setImageResource(R.drawable.ic_adjust_black_24dp);
        } else {
            current.setImageResource(R.drawable.ic_adjust_black_disable_24dp);
        }


        mTitle.setText(getItem(position).getTitle());
       mMetadata.setText(getItem(position).getAlbum_name()+" "+getItem(position).getArtist());
       

       mTitle.setTextColor(Color.WHITE);
       mTitle.setTypeface(tf);
       mTitle.setTextSize(14);
       mTitle.setMaxLines(1);
       mTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);


       mDuration.setTextColor(Color.WHITE);
       mDuration.setTypeface(tf);
       mDuration.setTextSize(14);
       mDuration.setMaxLines(1);
       mDuration.setEllipsize(TextUtils.TruncateAt.MARQUEE);

       mMetadata.setTextColor(Color.WHITE);
       mMetadata.setTypeface(tf);
       mMetadata.setTextSize(14);
       mMetadata.setMaxLines(1);
       mMetadata.setEllipsize(TextUtils.TruncateAt.MARQUEE);



        return relativeLayout;
    }
}

