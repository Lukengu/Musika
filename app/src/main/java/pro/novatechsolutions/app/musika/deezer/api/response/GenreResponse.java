package pro.novatechsolutions.app.musika.deezer.api.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GenreResponse implements Serializable {

    private int id;
    private String name;
    private String picture_big;
    private String picture_medium;
    private String picture_small;

    private JSONArray genres;


    private GenreResponse(){}

    public GenreResponse(JSONObject response){

        for (Field f : GenreResponse.class.getDeclaredFields()){
            if("genres".equals(f.getName()))
                continue;

            try{
                if(f.getType() == String.class){
                    f.set(this, response.optString(f.getName()));
                }
                if(f.getType() == int.class){
                    f.set(this, response.optInt(f.getName()));
                }

            } catch (IllegalAccessException e) {

            }

        }
    }

    public List<GenreResponse> getGenresList(){
        List<GenreResponse>  genresList  = new ArrayList<GenreResponse>();


        for(int i =0; i < genres.length(); i++){
            JSONObject response = null;
            try {
                response = genres.getJSONObject(i);
                genresList.add( new GenreResponse(response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return genresList;
    }
    public GenreResponse(JSONArray genres){
        this.genres =genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
