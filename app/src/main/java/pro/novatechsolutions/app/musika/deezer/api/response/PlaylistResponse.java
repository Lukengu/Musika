package pro.novatechsolutions.app.musika.deezer.api.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PlaylistResponse implements Serializable {

    private long id;
    private String picture_big;
    private String picture_medium;
    private String picture_small;
    private String title;
    private JSONArray playlists;

    private PlaylistResponse(){}

    public PlaylistResponse(JSONObject response){

        for (Field f : PlaylistResponse.class.getDeclaredFields()){
            if("playlists".equals(f.getName()))
                continue;

            try{
                if(f.getType() == String.class){
                    f.set(this, response.optString(f.getName()));
                }
                if(f.getType() == int.class){
                    f.set(this, response.optInt(f.getName()));
                }
                if(f.getType() == long.class){
                    f.set(this, response.optLong(f.getName()));
                }
            } catch (IllegalAccessException e) {

            }

        }
    }

    public List<PlaylistResponse> getPlaylist(){

        List<PlaylistResponse>  playlistList  = new ArrayList<PlaylistResponse>();
        for(int i =0; i < playlists.length(); i++){
            JSONObject response = null;
            try {
                response = playlists.getJSONObject(i);
                playlistList.add( new PlaylistResponse(response));
            } catch (JSONException e) {

            }
        }

        return playlistList;
    }


    public PlaylistResponse(JSONArray playlists){
        this.playlists = playlists;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }

    public String getPicture_medium() {
        return picture_medium;
    }

    public void setPicture_medium(String picture_medium) {
        this.picture_medium = picture_medium;
    }

    public String getPicture_small() {
        return picture_small;
    }

    public void setPicture_small(String picture_small) {
        this.picture_small = picture_small;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
