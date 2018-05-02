package pro.novatechsolutions.app.musika;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pro.novatechsolutions.app.musika.adapters.TrackListAdapter;

import pro.novatechsolutions.app.musika.deezer.api.DeezerApiException;
import pro.novatechsolutions.app.musika.deezer.api.ServiceResponseListerner;
import pro.novatechsolutions.app.musika.deezer.api.call.Track;
import pro.novatechsolutions.app.musika.deezer.api.response.TrackResponse;
import pro.novatechsolutions.app.musika.utils.FullPlayer;

public class Player extends AppCompatActivity implements ServiceResponseListerner, MediaPlayer.OnCompletionListener {

    private FullPlayer full_player;
    //private RecyclerView trackList;
    private ListView trackList ;
    private TrackListAdapter trackListAdapter;
    ArrayList<TrackResponse> trackResponses = new  ArrayList<TrackResponse> ();
    ArrayList<TrackResponse> trackResponseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        //trackList =  (RecyclerView) findViewById(R.id.trackList);
        trackList =  findViewById(R.id.trackList);
        full_player = findViewById(R.id.full_player);

        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(Player.this);
        String action = spref.getString("action","ALBUMS");
        long id = spref.getLong("id",0);

        Track track = new Track(Player.this, this, true);
        if(action.equals("ALBUMS")){
            track.TrackAlbum(id);
        } else if(action.equals("ARTISTS")) {
            track.TrackByArtist(id);
        } else if(action.equals("PLAYLISTS")) {
            track.TrackByPlayList(id);
        }

        Bundle bundle = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(bundle.getString("title", ""));


        trackListAdapter = new TrackListAdapter(Player.this, trackResponses);
        trackList.setAdapter(trackListAdapter);

        trackList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        trackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    full_player.customMediaPlayer.playTrack(trackResponseArrayList.get(position));
                    ((TrackListAdapter) trackList.getAdapter()).setTrackPosition(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });





        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
      //  trackList.setHasFixedSize(true);

        // use a linear layout manager
       // LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
       // trackList.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)


        TextView text_footer =  findViewById(R.id.text_footer);
        String footer_html = "&copy; Novatech  Consolidated Solutions";
        Calendar c = Calendar.getInstance();

        text_footer.setText( c.get(Calendar.YEAR)+" " + Html.fromHtml(footer_html) );

        text_footer.setTextSize(11);
        text_footer.setMaxLines(1);
        text_footer.setMaxWidth(65);
        text_footer.setEllipsize(TextUtils.TruncateAt.MARQUEE);


        text_footer.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ProximaNova-Bold.otf"));



    }

    @Override
    public void onSuccess(Object object) {

        trackResponseArrayList.addAll((List<TrackResponse>) object);
        full_player.customMediaPlayer.setTracks(trackResponseArrayList);





        try {
            //Application.setTitle(trackResponseArrayList.get(0).getTitle());
            trackListAdapter.addAll(trackResponseArrayList);
            full_player.customMediaPlayer.playTrack(trackResponseArrayList.get(0));
            full_player.setListView(trackList);
            ((TrackListAdapter) trackList.getAdapter()).setTrackPosition(0);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(DeezerApiException e) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
