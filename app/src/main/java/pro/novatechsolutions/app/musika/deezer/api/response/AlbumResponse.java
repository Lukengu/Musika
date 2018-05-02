package pro.novatechsolutions.app.musika.deezer.api.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlbumResponse implements Serializable {

    private String artist;
    private String cover_big;
    private String cover_medium;
    private String cover_small;
    private long id;
    private String title;

    private JSONArray albums;


    public AlbumResponse(){}
    public AlbumResponse(JSONObject chart){

        /*setId(response.optLong("id"));
        setCover_small(response.optString("cover_small"));
        setCover_big(response.optString("cover_big"));
        setCover_medium(response.optString("cover_medium"));
        setTitle(response.optString("title"));
        setArtist(response.optJSONObject("artist").optString("name"));
        */
        albums = chart.optJSONObject("albums").optJSONArray("data");

    }

    public List<AlbumResponse> getAlbumList(){
        List<AlbumResponse>  albumList  = new ArrayList<AlbumResponse>();


        for(int i =0; i < albums.length(); i++){
            JSONObject response = null;
            try {
                response = albums.getJSONObject(i);
                AlbumResponse ar = new AlbumResponse();
                ar.setCover_small(response.optString("cover_small"));
                ar.setCover_big(response.optString("cover_big"));
                ar.setCover_medium(response.optString("cover_medium"));
                ar.setTitle(response.optString("title"));
                ar.setId(response.optLong("id"));
                ar.setArtist(response.optJSONObject("artist").optString("name"));
                albumList.add(ar);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        return albumList;
    }


    public AlbumResponse(JSONArray albums){
        this.albums = albums;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCover_big() {
        return cover_big;
    }

    public void setCover_big(String cover_big) {
        this.cover_big = cover_big;
    }

    public String getCover_medium() {
        return cover_medium;
    }

    public void setCover_medium(String cover_medium) {
        this.cover_medium = cover_medium;
    }

    public String getCover_small() {
        return cover_small;
    }

    public void setCover_small(String cover_small) {
        this.cover_small = cover_small;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
