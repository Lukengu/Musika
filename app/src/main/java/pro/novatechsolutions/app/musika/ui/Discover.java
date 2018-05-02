package pro.novatechsolutions.app.musika.ui;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pro.novatechsolutions.app.musika.Player;
import pro.novatechsolutions.app.musika.R;
import pro.novatechsolutions.app.musika.adapters.ArtistAdapter;
import pro.novatechsolutions.app.musika.adapters.FancyCoverFlowSampleAdapter;
import pro.novatechsolutions.app.musika.adapters.PlayListAdapter;
import pro.novatechsolutions.app.musika.coverflow.FancyCoverFlow;
import pro.novatechsolutions.app.musika.deezer.api.DeezerApiException;
import pro.novatechsolutions.app.musika.deezer.api.ServiceResponseListerner;
import pro.novatechsolutions.app.musika.deezer.api.call.Chart;
import pro.novatechsolutions.app.musika.deezer.api.response.AlbumResponse;
import pro.novatechsolutions.app.musika.deezer.api.response.ArtistResponse;
import pro.novatechsolutions.app.musika.deezer.api.response.PlaylistResponse;

public class Discover extends Fragment implements  ServiceResponseListerner {
    private  View view;
  //  private FeatureCoverFlow mCoverFlow;
    //private CoverFlowAdapter mAdapter;

    private FancyCoverFlow mCoverFlow;
    private FancyCoverFlowSampleAdapter mAdapter;

    private ArrayList<AlbumResponse> mData = new ArrayList<>(0);
    private TextView artist_title_text, playlist_title_text;
    private GridView top_artists, top_playlists;
    private Handler handler  = new Handler();
    private List<ArtistResponse> albums = new ArrayList();
    private ArtistAdapter artistAdapter;
    private ArrayList<PlaylistResponse> playlists = new ArrayList();
    private ArrayList<ArtistResponse> artists = new ArrayList();
    private PlayListAdapter playListAdapter;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.discover, container, false);
        mData.add(new AlbumResponse());
        mAdapter = new FancyCoverFlowSampleAdapter(getActivity());
        mAdapter.updateData(mData);

        mCoverFlow = view.findViewById(R.id.fancyCoverFlow);

        mCoverFlow.setAdapter(mAdapter);
        mCoverFlow.setUnselectedAlpha(1.0f);
        mCoverFlow.setUnselectedSaturation(0.0f);
        mCoverFlow.setUnselectedScale(1);
        mCoverFlow.setSpacing(0);
        mCoverFlow.setMaxRotation(0);
        mCoverFlow.setScaleDownGravity(0);
        mCoverFlow.setActionDistance(2);

        top_artists = view.findViewById(R.id.top_artists);
        top_playlists = view.findViewById(R.id.top_playlists);

        artist_title_text  = view.findViewById(R.id.artist_title_text);
        playlist_title_text  = view.findViewById(R.id.playlist_title_text);

        artist_title_text.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/ProximaNova-Black.otf"));
        playlist_title_text.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/ProximaNova-Black.otf"));
        artistAdapter = new ArtistAdapter(getActivity(), artists);
        playListAdapter = new PlayListAdapter(getActivity(),playlists );
        mCoverFlow.setOnItemClickListener( new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                            .putString("action", "ALBUMS")
                            .putLong("id", mAdapter.getItem(position).getId())
                            .commit();

                    getActivity().startActivity(new Intent(getActivity(), Player.class).putExtra("title",  mAdapter.getItem(position).getTitle()));


                } catch(NullPointerException e){

                }
            }
        });

       /* mCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try{
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                            .putString("action", "ALBUMS")
                            .putLong("id", mAdapter.getItem(i).getId())
                            .commit();


                    getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new Player()).commit();

                } catch(NullPointerException e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        new Chart(getActivity(), this, true).albumList();

        new Chart(getActivity(), new ServiceResponseListerner() {
            @Override
            public void onSuccess(Object object) {
                List a = (List<ArtistResponse>) object;
                //Collections.shuffle(p, new Random(p.size() - 1));


                Collections.shuffle(a);
                 a = a.subList(0,2);
                artistAdapter.addAll(a);
                top_artists.setAdapter(artistAdapter);
            }

            @Override
            public void onFailure(DeezerApiException e) {

            }
        }, false).artistList();


        new Chart(getActivity(), new ServiceResponseListerner() {


            @java.lang.Override
            public void onSuccess(java.lang.Object object) {
                List p = (List<PlaylistResponse>) object;

                // Collections.shuffle(p, new Random(p.size() - 1));
                Collections.shuffle(p);
                p = p.subList(0,2);
                playListAdapter.addAll(p);
                top_playlists.setAdapter(playListAdapter);

            }

            @Override
            public void onFailure(DeezerApiException e) {

            }
        }, false).playlistList();

        top_artists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try{
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                            .putString("action", "ARTISTS")
                            .putLong("id", artistAdapter.getItem(position).getId())
                            .commit();


                    getActivity().startActivity(new Intent(getActivity(), Player.class).putExtra("title",  artistAdapter.getItem(position).getName()));


                } catch(NullPointerException e){

                }
            }
        });
        top_playlists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try{
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                            .putString("action", "PLAYLISTS")
                            .putLong("id", playListAdapter.getItem(position).getId())
                            .commit();


                    getActivity().startActivity(new Intent(getActivity(), Player.class).putExtra("title",  playListAdapter.getItem(position).getTitle()));


                } catch(NullPointerException e){

                }
            }
        });



        return view;
    }


    private void postDelayedScrollNext() {
        handler.postDelayed(new Runnable() {
            public void run() {
                // check to see if we are at the image is at the last index, if so set the
                // selection back to 1st image.

                if (mCoverFlow.getSelectedItemPosition() == mAdapter.getCount() - 1) {
                    mCoverFlow.setSelection(1);
                    postDelayedScrollNext();
                    return;
                }
                postDelayedScrollNext();
                mCoverFlow.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
            }
        }, 5000);

    }





    @Override
    public void onSuccess(Object object) {
        mData.clear();

        List<AlbumResponse> albumResponseList = (List<AlbumResponse>) object;
        mData.addAll(albumResponseList);
        mAdapter.updateData(mData);
        postDelayedScrollNext();

        Log.i("title", mData.get(0).getTitle());
        Log.i("Artist", mData.get(0).getArtist());
        Log.i("Artist", mData.get(0).getCover_big());
    }

    @Override
    public void onFailure(DeezerApiException e) {

    }
}
