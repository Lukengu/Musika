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
import pro.novatechsolutions.app.musika.adapters.ArtistAdapter;
import pro.novatechsolutions.app.musika.deezer.api.DeezerApiException;
import pro.novatechsolutions.app.musika.deezer.api.ServiceResponseListerner;
import pro.novatechsolutions.app.musika.deezer.api.call.Chart;
import pro.novatechsolutions.app.musika.deezer.api.response.ArtistResponse;
import pro.novatechsolutions.app.musika.deezer.api.response.PlaylistResponse;

public class Artists extends Fragment implements  ServiceResponseListerner {
    private  View view;
  //


    private ArtistAdapter arstistAdapter;
    private ArrayList<ArtistResponse> artists = new ArrayList();
    private GridView gridview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.genres, container, false);

        gridview = view.findViewById(R.id.gridview);
        arstistAdapter = new ArtistAdapter(getActivity(), artists);
        new Chart(getActivity(), this, true).artistList();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                            .putString("action", "ARTISTS")
                            .putLong("id", arstistAdapter.getItem(position).getId())
                            .commit();


                    getActivity().startActivity(new Intent(getActivity(), Player.class).putExtra("title",  arstistAdapter.getItem(position).getName()));

                } catch(NullPointerException e){

                }
            }
        });



        return view;
    }







    @Override
    public void onSuccess(Object object) {

        List<ArtistResponse> data = (List<ArtistResponse>) object;
        Collections.sort(data,  new Comparator<ArtistResponse>() {
            public int compare(ArtistResponse response1, ArtistResponse response2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(response1.getName(), response2.getName());
                if (res == 0) {
                    res = response1.getName().compareTo(response2.getName());
                }
                return res;
            }
        });
        artists.addAll(data);
        gridview.setAdapter(arstistAdapter);
    }

    @Override
    public void onFailure(DeezerApiException e) {

    }
}
