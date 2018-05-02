package pro.novatechsolutions.app.musika;

import android.content.Intent;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pro.novatechsolutions.app.musika.adapters.ArtistAdapter;
import pro.novatechsolutions.app.musika.adapters.PlayListAdapter;
import pro.novatechsolutions.app.musika.deezer.api.DeezerApiException;
import pro.novatechsolutions.app.musika.deezer.api.ServiceResponseListerner;
import pro.novatechsolutions.app.musika.deezer.api.call.Genre;
import pro.novatechsolutions.app.musika.deezer.api.response.ArtistResponse;
import pro.novatechsolutions.app.musika.deezer.api.response.PlaylistResponse;

public class GenreArtists extends AppCompatActivity implements ServiceResponseListerner {

    private GridView gridView;
    private ArtistAdapter arstistAdapter;
    private ArrayList<ArtistResponse> artists = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_artists);
        gridView = findViewById(R.id.gridview);

        Bundle bundle = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(bundle.getString("title", "")+" Artists");

        arstistAdapter = new ArtistAdapter(GenreArtists.this, artists);
        new Genre(GenreArtists.this, this, true).genreArtists(bundle.getInt("id", 0));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    PreferenceManager.getDefaultSharedPreferences(GenreArtists.this).edit()
                            .putString("action", "ARTISTS")
                            .putLong("id", arstistAdapter.getItem(position).getId())
                            .commit();

                    startActivity(new Intent(GenreArtists.this, Player.class).putExtra("title",  arstistAdapter.getItem(position).getName()));

                } catch(NullPointerException e){

                }
            }
        });
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
        gridView.setAdapter(arstistAdapter);
    }

    @Override
    public void onFailure(DeezerApiException e) {

    }
}
