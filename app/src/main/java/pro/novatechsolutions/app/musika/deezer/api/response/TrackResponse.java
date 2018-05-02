package pro.novatechsolutions.app.musika.deezer.api.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrackResponse {

    private String album_cover;
    private String album_name;
    private String artist;
    private int duration;
    private long id;
    private String preview;
    private boolean readable;
    private String title;



    public enum TRACKS_OBJECT {
        ALBUM,
        ARTIST,
        PLAYLIST
    }

    private TRACKS_OBJECT obj;
    private JSONObject data;



    private TrackResponse( String album_cover, String album_name, String artist,
                           int duration, long id,  String preview, boolean readable, String title){

        this.album_cover = album_cover;
        this.album_name =album_name;
        this.artist = artist;
        this.duration = duration;
        this.id = id;
        this.preview = preview;
        this.readable = readable;
        this.title = title;

    }


    public TrackResponse(JSONObject data, TRACKS_OBJECT obj){
         this.data = data;
         this.obj = obj;
    }

    public List<TrackResponse> getTrackResponses() {

        JSONArray tracks = null;
        String title = null ;
        String artist =  null;
        String cover_big = null;


         switch(obj) {
             case ALBUM:
                 cover_big = data.optString("cover_big");
                 artist = data.optJSONObject("artist").optString("name");
                 title = data.optString("title");
                 tracks = data.optJSONObject("tracks").optJSONArray("data");
                 break;
             case ARTIST:
             case PLAYLIST:
                 tracks = data.optJSONArray("data");
                 break;
         }

        return  fillResponse(tracks,cover_big,artist,title);

    }

    private  List<TrackResponse> fillResponse(JSONArray tracks,  String cover_big ,  String artist,  String title) {
        List<TrackResponse> responses = new ArrayList<>();

        for (int i = 0; i < tracks.length(); i++) {
            JSONObject track = tracks.optJSONObject(i);
            if(cover_big != null && artist != null && title != null) {
                responses.add(new TrackResponse(
                        cover_big,
                        title,
                        artist,
                        track.optInt("duration"),
                        track.optLong("id"),
                        track.optString("preview"),
                        track.optBoolean("readable"),
                        track.optString("title")
                ));
            } else {
                responses.add(new TrackResponse(
                        track.optJSONObject("album").optString("cover_big"),
                        track.optJSONObject("album").optString("name"),
                        track.optJSONObject("artist").optString("name"),
                        track.optInt("duration"),
                        track.optLong("id"),
                        track.optString("preview"),
                        track.optBoolean("readable"),
                        track.optString("title")
                ));
            }

        }
        return responses;
    }

    public String getAlbum_cover() {
        return album_cover;
    }

    public void setAlbum_cover(String album_cover) {
        this.album_cover = album_cover;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
