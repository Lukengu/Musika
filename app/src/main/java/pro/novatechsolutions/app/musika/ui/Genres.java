package pro.novatechsolutions.app.musika.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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

import pro.novatechsolutions.app.musika.GenreArtists;
import pro.novatechsolutions.app.musika.R;
import pro.novatechsolutions.app.musika.adapters.GenreAdapter;
import pro.novatechsolutions.app.musika.deezer.api.DeezerApiException;
import pro.novatechsolutions.app.musika.deezer.api.ServiceResponseListerner;
import pro.novatechsolutions.app.musika.deezer.api.call.Genre;
import pro.novatechsolutions.app.musika.deezer.api.response.GenreResponse;


public class Genres extends Fragment implements  ServiceResponseListerner {
    private  View view;
  //


    private GenreAdapter genreAdapter;
    private ArrayList<GenreResponse> genres = new ArrayList();
    private GridView gridview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.genres, container, false);

        gridview = view.findViewById(R.id.gridview);

        genreAdapter = new GenreAdapter(getActivity(), genres);

        new Genre(getActivity(), this, true).genreList();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GenreResponse item =  genreAdapter.getItem(position);
                getActivity().startActivity(new Intent(getActivity(), GenreArtists.class).putExtra("title",
                        item.getName()).putExtra("id", item.getId()));
            }
        });


        return view;
    }







    @Override
    public void onSuccess(Object object) {

        List<GenreResponse> genre_data = (List<GenreResponse>) object;
        Collections.sort(genre_data,  new Comparator<GenreResponse>() {
            public int compare(GenreResponse response1, GenreResponse response2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(response1.getName(), response2.getName());
                if (res == 0) {
                    res = response1.getName().compareTo(response2.getName());
                }
                return res;
            }
        });

        genres.addAll(genre_data);
      //  Collections.shuffle(genres);
        gridview.setAdapter(genreAdapter);
    }

    @Override
    public void onFailure(DeezerApiException e) {

    }
}
