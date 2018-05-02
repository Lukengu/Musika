package pro.novatechsolutions.app.musika.deezer.api.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ArtistResponse implements Serializable {

    private String name;
    private String picture_big;
    private String picture_medium;
    private String picture_small;
    private long id;

    private JSONArray artists;


    private ArtistResponse(){}

    public ArtistResponse(JSONObject response){

        for (Field f : ArtistResponse.class.getDeclaredFields()){
            if("artists".equals(f.getName()))
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

    public List<ArtistResponse> getArtistList(){
        List<ArtistResponse>  artistList  = new ArrayList<ArtistResponse>();


        for(int i =0; i < artists.length(); i++){
            JSONObject response = null;
            try {
                response = artists.getJSONObject(i);
                artistList.add( new ArtistResponse(response));
            } catch (JSONException e) {

            }


        }

        return artistList;
    }


    public ArtistResponse(JSONArray artists){
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
