package pro.novatechsolutions.app.musika.deezer.api.call;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pro.novatechsolutions.app.musika.R;
import pro.novatechsolutions.app.musika.deezer.api.DeezerApiConfiguration;
import pro.novatechsolutions.app.musika.deezer.api.DeezerApiException;
import pro.novatechsolutions.app.musika.deezer.api.DeezerApiUtils;
import pro.novatechsolutions.app.musika.deezer.api.ServiceResponseListerner;
import pro.novatechsolutions.app.musika.deezer.api.response.AlbumResponse;
import pro.novatechsolutions.app.musika.deezer.api.response.ArtistResponse;
import pro.novatechsolutions.app.musika.deezer.api.response.PlaylistResponse;


public class Chart  extends AsyncHttpClient {

    private ServiceResponseListerner<Object> mCallBack;
    private Context mContext;
    private ProgressDialog progressBar;
    private boolean  showProgressDialog;

    public Chart(Context context, ServiceResponseListerner callback, boolean showProgressDialog) {
        mCallBack = callback;
        mContext = context;
        this.showProgressDialog = showProgressDialog;
        addHeader("Accept", "application/json");
        if(this.showProgressDialog) {
            progressBar = new ProgressDialog(mContext, R.style.TransparentProgressDialog);
            //progressBar.setProgressStyle(R.style.TransparentProgressDialog);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBar.setProgressDrawable(context.getDrawable(R.drawable.ic_loading));
            } else {
                progressBar.setProgressDrawable( context.getResources().getDrawable(R.drawable.ic_loading));
            }


            progressBar.show();
        }
    }

    public void albumList() {

      //  Log.e("Link", DeezerApiConfiguration.API_URL+"/chart/albums");

       // this.get("test", new RequestParams(), new JsonHttpResponseHandler());
        Log.i("Link", DeezerApiConfiguration.API_URL+"/chart/albums");

        get(DeezerApiConfiguration.API_URL+"/chart/albums", new RequestParams(),
                new JsonHttpResponseHandler(){

                    //Log.("ERROR");


                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject chart) {
                        // Pull out the first event on the public timeline
                        Log.i("Link", DeezerApiConfiguration.API_URL+"/chart/albums");
                        Log.i("Results", chart.toString());
                        AlbumResponse album = new AlbumResponse(chart);

                        mCallBack.onSuccess(album.getAlbumList());

                        if(showProgressDialog)
                            progressBar.dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                      //   Log.e("ERROR", t.getMessage());
                        JSONArray array = null;
                        String error_message = res;

                        DeezerApiUtils.JSONTYPE type = DeezerApiUtils.isJSONValid(res);
                        if(type.equals( DeezerApiUtils.JSONTYPE.JSON_OBJECT)){

                            JSONObject object = null;
                            try {
                                object = new JSONObject(res);
                                error_message = object.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else if(type.equals( DeezerApiUtils.JSONTYPE.JSON_ARRAY)){
                            try {
                                array = new JSONArray(res);
                                JSONObject object = array.getJSONObject(0);
                                error_message = object.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        mCallBack.onFailure(new DeezerApiException(res != null ? error_message : t.getMessage()));

                        if(showProgressDialog)
                            progressBar.dismiss();
                        if(showProgressDialog)
                            progressBar.dismiss();
                    }
                });
    }

    public void playlistList() {
        get(DeezerApiConfiguration.API_URL+"/chart", new RequestParams(),
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject chart) {
                        // Pull out the first event on the public timeline
                        Log.i("Link", DeezerApiConfiguration.API_URL+"/chart");
                        Log.i("Results", chart.toString());
                        JSONArray playlists= chart.optJSONObject("playlists").optJSONArray("data");
                        PlaylistResponse playlist = new PlaylistResponse(playlists);
                        mCallBack.onSuccess(playlist.getPlaylist());

                        if(showProgressDialog)
                            progressBar.dismiss();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        //  Log.e("ERROR", t.getMessage());
                        JSONArray array = null;
                        String error_message = res;

                        DeezerApiUtils.JSONTYPE type = DeezerApiUtils.isJSONValid(res);
                        if(type.equals( DeezerApiUtils.JSONTYPE.JSON_OBJECT)){

                            JSONObject object = null;
                            try {
                                object = new JSONObject(res);
                                error_message = object.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else if(type.equals( DeezerApiUtils.JSONTYPE.JSON_ARRAY)){
                            try {
                                array = new JSONArray(res);
                                JSONObject object = array.getJSONObject(0);
                                error_message = object.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        mCallBack.onFailure(new DeezerApiException(res != null ? error_message : t.getMessage()));

                        if(showProgressDialog)
                            progressBar.dismiss();
                        if(showProgressDialog)
                            progressBar.dismiss();
                    }
                });
    }

    public void artistList() {
        get(DeezerApiConfiguration.API_URL+"/chart", new RequestParams(),
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject chart) {
                        // Pull out the first event on the public timeline

                        Log.i("Link", DeezerApiConfiguration.API_URL+"/chart");
                        Log.i("Results", chart.toString());
                        JSONArray artists= chart.optJSONObject("artists").optJSONArray("data");
                        ArtistResponse artist = new ArtistResponse(artists);
                        mCallBack.onSuccess(artist.getArtistList());

                        if(showProgressDialog)
                            progressBar.dismiss();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        //  Log.e("ERROR", t.getMessage());
                        JSONArray array = null;
                        String error_message = res;

                        DeezerApiUtils.JSONTYPE type = DeezerApiUtils.isJSONValid(res);
                        if(type.equals( DeezerApiUtils.JSONTYPE.JSON_OBJECT)){

                            JSONObject object = null;
                            try {
                                object = new JSONObject(res);
                                error_message = object.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else if(type.equals( DeezerApiUtils.JSONTYPE.JSON_ARRAY)){
                            try {
                                array = new JSONArray(res);
                                JSONObject object = array.getJSONObject(0);
                                error_message = object.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        mCallBack.onFailure(new DeezerApiException(res != null ? error_message : t.getMessage()));

                        if(showProgressDialog)
                            progressBar.dismiss();
                        if(showProgressDialog)
                            progressBar.dismiss();
                    }
                });
    }
}
