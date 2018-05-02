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
import pro.novatechsolutions.app.musika.deezer.api.response.TrackResponse;


public class Track extends AsyncHttpClient {

    private ServiceResponseListerner<Object> mCallBack;
    private Context mContext;
    private ProgressDialog progressBar;
    private boolean showProgressDialog;

    public Track(Context context, ServiceResponseListerner callback, boolean showProgressDialog) {
        mCallBack = callback;
        mContext = context;
        this.showProgressDialog = showProgressDialog;

        addHeader("Accept", "application/json");

        if (this.showProgressDialog) {
            progressBar = new ProgressDialog(mContext, R.style.TransparentProgressDialog);
            //progressBar.setProgressStyle(R.style.TransparentProgressDialog);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBar.setProgressDrawable(context.getDrawable(R.drawable.ic_loading));
            } else {
                progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.ic_loading));
            }


            progressBar.show();
        }

    }


    private void _get(final String link, final TrackResponse.TRACKS_OBJECT object) {
        get(link, new RequestParams(),
                new JsonHttpResponseHandler(){
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                         // Pull out the first event on the public timeline

                        Log.e("Link", link);
                        TrackResponse trackResponse = new TrackResponse(response, object);
                        mCallBack.onSuccess(trackResponse.getTrackResponses());

                        if(showProgressDialog)
                            progressBar.dismiss();
                    }

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

    public void TrackByPlayList(long playlistID){
        _get(DeezerApiConfiguration.API_URL+"/playlist/"+playlistID+"/tracks", TrackResponse.TRACKS_OBJECT.PLAYLIST);
    }
    public void TrackByArtist(long artistID){
        _get(DeezerApiConfiguration.API_URL+"/artist/"+artistID+"/top?limit=50", TrackResponse.TRACKS_OBJECT.ARTIST);
    }
    public void TrackAlbum(long albumID){
        _get(DeezerApiConfiguration.API_URL+"/album/"+albumID, TrackResponse.TRACKS_OBJECT.ALBUM);
    }

}
