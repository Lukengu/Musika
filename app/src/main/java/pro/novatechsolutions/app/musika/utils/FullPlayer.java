package pro.novatechsolutions.app.musika.utils;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.io.IOException;

import pro.novatechsolutions.app.musika.R;
import pro.novatechsolutions.app.musika.adapters.TrackListAdapter;

public class FullPlayer extends LinearLayout implements OnCompletionListener, OnPreparedListener, OnClickListener, OnBufferingUpdateListener, OnSeekCompleteListener {

    private ImageView album;
    public CustomMediaPlayer customMediaPlayer;
    private Handler handler;
    private SeekBar mSeekBar;
    private ImageView play;
    private ImageView skip_back;
    private ImageView skip_forward;
    private TextView track_info;
    private Context context;
    //Hack
    private ListView trackList;






    public FullPlayer(Context context) {
        this(context, null, 0);

    }

    private void updateSeekBar() {
        if (CustomMediaPlayer.mediaPlayer != null) {
            mSeekBar.setProgress(CustomMediaPlayer.mediaPlayer.getCurrentPosition() / 1000);
        }
    }

    public FullPlayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullPlayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        customMediaPlayer = CustomMediaPlayer.getInstance();
        handler = new Handler();
      //  CustomMediaPlayer.mediaPlayer.setOnCompletionListener(this);
        CustomMediaPlayer.mediaPlayer.setOnPreparedListener(this);
        CustomMediaPlayer.mediaPlayer.setOnBufferingUpdateListener(this);
        CustomMediaPlayer.mediaPlayer.setOnSeekCompleteListener(this);
        init(context);
    }

    public void init(Context context) {
        View.inflate(context, R.layout.full_player, this);
        play = (ImageView) findViewById(R.id.play);
        skip_back = (ImageView) findViewById(R.id.skip_back);
        skip_forward = (ImageView) findViewById(R.id.skip_forward);
        album = (ImageView) findViewById(R.id.album);

        mSeekBar = (SeekBar) findViewById(R.id.mSeekBar);
        setClick(R.id.play);
        setClick(R.id.skip_back);
        setClick(R.id.skip_forward);
        this.context = context;
    }

    public View setClick(int id) {
        View v = findViewById(id);
        v.setOnClickListener(this);
        return v;
    }

    public void onPrepared(MediaPlayer mp) {
        System.out.println("preparing");
        if (mp.isPlaying()) {
            play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        } else {
            play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
        }
        updateSeekBar();

        Glide
                .with(context)
                .load( customMediaPlayer.getCurrentTrack().getAlbum_cover())
                .placeholder(R.drawable.ic_loading)
                .centerCrop()
                .into(album);






    }

    public void setListView(ListView trackList){
        this.trackList = trackList;



    }

    public void onCompletion(MediaPlayer mp) {
        if (mp.isPlaying()) {
            play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        } else {
            play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
        }
        updateSeekBar();
        try {

            customMediaPlayer.nextTrack();
            int position = customMediaPlayer.getCurrentTrackPosition();
            ((TrackListAdapter) trackList.getAdapter()).setTrackPosition(position);
            //trackList.setSelection(customMediaPlayer.getCurrentTrackPosition());
          //  trackList.setSelection(customMediaPlayer.getCurrentTrackPosition());

            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip_back:
                try {
                    customMediaPlayer.previousTrack();
                    int position = customMediaPlayer.getCurrentTrackPosition();
                    ((TrackListAdapter) trackList.getAdapter()).setTrackPosition(position);
                   // trackList.setSelection(customMediaPlayer.getCurrentTrackPosition());

                   // Application.setTitle(customMediaPlayer.getCurrentTrack().getTitle());
                    return;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return;
                } catch (SecurityException e2) {
                    e2.printStackTrace();
                    return;
                } catch (IllegalStateException e3) {
                    e3.printStackTrace();
                    return;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return;
                }
            case R.id.play:
                if (customMediaPlayer.isPlaying()) {
                    customMediaPlayer.pauseTrack();
                   // Application.setTitle(customMediaPlayer.getCurrentTrack().getTitle());
                    return;
                }
                try {
                    customMediaPlayer.playTrack();
                    play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    trackList.setSelection(customMediaPlayer.getCurrentTrackPosition());
                   // Application.setTitle(customMediaPlayer.getCurrentTrack().getTitle());
                    return;
                } catch (IllegalArgumentException e5) {
                    e5.printStackTrace();
                    return;
                } catch (SecurityException e22) {
                    e22.printStackTrace();
                    return;
                } catch (IllegalStateException e32) {
                    e32.printStackTrace();
                    return;
                } catch (IOException e42) {
                    e42.printStackTrace();
                    return;
                }
            case R.id.skip_forward:
                try {
                    customMediaPlayer.nextTrack();
                    int position = customMediaPlayer.getCurrentTrackPosition();
                    ((TrackListAdapter) trackList.getAdapter()).setTrackPosition(position);

                  //  trackList.setSelection(customMediaPlayer.getCurrentTrackPosition());
                   // trackList.setSelection(customMediaPlayer.getCurrentTrackPosition());
                  //  Application.setTitle(customMediaPlayer.getCurrentTrack().getTitle());
                    return;
                } catch (IllegalArgumentException e52) {
                    e52.printStackTrace();
                    return;
                } catch (SecurityException e222) {
                    e222.printStackTrace();
                    return;
                } catch (IllegalStateException e322) {
                    e322.printStackTrace();
                    return;
                } catch (IOException e422) {
                    e422.printStackTrace();
                    return;
                }
            default:
                return;
        }

    }

    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (mp.isPlaying()) {
            play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        } else {
            play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
        }
        updateSeekBar();
      //  updateSeekBar();
       /* Glide
                .with(context)
                .load( customMediaPlayer.getCurrentTrack().getAlbum_cover())
                .placeholder(R.drawable.ic_loading)
                .centerCrop()
                .into(album);*/
    }

    public void onSeekComplete(MediaPlayer mp) {
        mp.seekTo(mSeekBar.getProgress());

    }
}

