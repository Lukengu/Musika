package pro.novatechsolutions.app.musika.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import java.io.IOException;
import java.util.ArrayList;

import pro.novatechsolutions.app.musika.deezer.api.response.TrackResponse;

@SuppressLint({"NewApi"})
@TargetApi(23)
public class CustomMediaPlayer implements  OnCompletionListener {
    public static final MediaPlayer mediaPlayer = new MediaPlayer();
    private int current_position;
    private TrackResponse current_track;
    private ArrayList<TrackResponse> tracks;


    @Override
    public void onCompletion(MediaPlayer mp) {
        try {
           nextTrack();
        } catch (IllegalArgumentException e) {

        } catch (SecurityException e) {

        } catch (IllegalStateException e3) {

        } catch (IOException e4) {

        }

    }


    private static class CustomMediaPlayerHolder {
        private static final CustomMediaPlayer INSTANCE = new CustomMediaPlayer();

        private CustomMediaPlayerHolder() {
        }
    }

    private CustomMediaPlayer() {
    }

    public static CustomMediaPlayer getInstance() {
        return CustomMediaPlayerHolder.INSTANCE;
    }

    public void playTrack() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
        playTrack(current_track);
    }

    public void playTrack(TrackResponse track) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
        setCurrentTrack(track);
        setCurrentPosition();
        mediaPlayer.reset();
        mediaPlayer.setDataSource(current_track.getPreview());
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public void playTrack(ArrayList<TrackResponse> tracks) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
        setTracks(tracks);
        current_track = (TrackResponse) tracks.get(0);
        playTrack(current_track);
        mediaPlayer.setOnCompletionListener(this);
    }

    public void pauseTrack() {
        mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    public void setCurrentAudioTime(int msec) {
        mediaPlayer.seekTo(msec);
    }

    public long getCurrentAudioTime() {
        return mediaPlayer.getTimestamp().getAnchorMediaTimeUs();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public void setTracks(ArrayList<TrackResponse> tracks) {
        this.tracks = tracks;
    }

    public TrackResponse getCurrentTrack() {
        return this.current_track;
    }
    public int getCurrentTrackPosition() {
        return this.current_position;
    }

    public int setCurrentPosition() {
        current_position = tracks.indexOf(this.current_track);
        return current_position;
    }

    private void setCurrentTrack(TrackResponse track) {
        current_track = track;
    }

    public void nextTrack() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
        int i = this.current_position + 1;
        current_position = i;
        if (i >= tracks.size()) {
            current_position = 0;

        }
        current_track = (TrackResponse) tracks.get(current_position);
        playTrack(current_track);
    }

    public void previousTrack() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
       // int i = current_position - 1;
        int i = current_position - 1;
        current_position = i;

        if (i <= 0 ) {
            current_position = tracks.size();

        }
        current_track = (TrackResponse) tracks.get(current_position);
        playTrack(current_track);

        //current_position = i;
        //if (i <= this.tracks.size()) {
          //  current_position = tracks.size();
            //return;
        //}

    }
}
