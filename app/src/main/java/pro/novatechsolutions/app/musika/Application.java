package pro.novatechsolutions.app.musika;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pro.novatechsolutions.app.musika.ui.Albums;
import pro.novatechsolutions.app.musika.ui.Artists;
import pro.novatechsolutions.app.musika.ui.Discover;
import pro.novatechsolutions.app.musika.ui.Genres;
import pro.novatechsolutions.app.musika.ui.Playlists;

public class Application extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int menuId = sharedPreferences.contains("menuId") ?  sharedPreferences.getInt("menuId", R.id.nav_discover) :
                R.id.nav_discover;
        MenuItem menu = navigationView.getMenu().findItem(  R.id.nav_discover);
        menu.setChecked(true);
        onNavigationItemSelected(menu);

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.application, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        FragmentManager fragmentManager = getFragmentManager();

        PreferenceManager.getDefaultSharedPreferences(Application.this).edit().putInt("menuId", id)
                .putInt("menuId", id).commit();

        Fragment fragment = null;

        if (id == R.id.nav_discover) {
            // Handle the camera action
            fragment = new Discover();

        } else if (id == R.id.nav_genre) {

            fragment = new Genres();

        }else if (id == R.id.nav_artists) {
            fragment = new Artists();

        } else if (id == R.id.nav_album) {
            fragment = new Albums();
        } else if (id == R.id.nav_playlist) {
            fragment = new Playlists();

        } else if (id == R.id.nav_playlist) {
            fragment = new Playlists();

        }

        getSupportActionBar().setTitle( id == R.id.nav_discover ? "Discover" : item.getTitle());

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
