package pro.novatechsolutions.app.musika.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pro.novatechsolutions.app.musika.Player;
import pro.novatechsolutions.app.musika.R;
import pro.novatechsolutions.app.musika.adapters.PlayListAdapter;
import pro.novatechsolutions.app.musika.deezer.api.DeezerApiException;
import pro.novatechsolutions.app.musika.deezer.api.ServiceResponseListerner;
import pro.novatechsolutions.app.musika.deezer.api.call.Chart;
import pro.novatechsolutions.app.musika.deezer.api.response.GenreResponse;
import pro.novatechsolutions.app.musika.deezer.api.response.PlaylistResponse;

public class Playlists extends Fragment implements  ServiceResponseListerner {
    private  View view;
  //


    private PlayListAdapter playListAdapter;
    private ArrayList<PlaylistResponse> response = new ArrayList();
    private GridView gridview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.genres, container, false);

        gridview = view.findViewById(R.id.gridview);

        playListAdapter = new PlayListAdapter(getActivity(), response);

        new Chart(getActivity(), this, true).playlistList();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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







    @Override
    public void onSuccess(Object object) {


        List<PlaylistResponse> data = (List<PlaylistResponse>) object;
        Collections.sort(data,  new Comparator<PlaylistResponse>() {
            public int compare(PlaylistResponse response1, PlaylistResponse response2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(response1.getTitle(), response2.getTitle());
                if (res == 0) {
                    res = response1.getTitle().compareTo(response2.getTitle());
                }
                return res;
            }
        });
        response.addAll(data);
      //  Collections.shuffle(genres);
        gridview.setAdapter(playListAdapter);
    }

    @Override
    public void onFailure(DeezerApiException e) {

    }
}
