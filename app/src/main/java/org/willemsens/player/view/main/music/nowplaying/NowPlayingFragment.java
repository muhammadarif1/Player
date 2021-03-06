package org.willemsens.player.view.main.music.nowplaying;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.willemsens.player.R;
import org.willemsens.player.playback.PlayBackIntentBuilder;

import static org.willemsens.player.playback.PlayerCommand.NEXT;
import static org.willemsens.player.playback.PlayerCommand.PREVIOUS;
import static org.willemsens.player.playback.PlayerCommand.STOP_PLAY_PAUSE;

public class NowPlayingFragment extends Fragment {
    @BindView(R.id.now_playing_bar_image)
    ImageView albumCover;

    @BindView(R.id.now_playing_bar_track)
    TextView trackNumber;

    @BindView(R.id.now_playing_bar_song)
    TextView songName;

    @BindView(R.id.now_playing_bar_album)
    TextView albumName;

    @BindView(R.id.button_previous)
    ImageView previousButton;

    @BindView(R.id.button_play_pause_stop)
    ImageView playPauseStopButton;

    @BindView(R.id.button_next)
    ImageView nextButton;

    private NowPlayingViewModel viewModel;

    public static NowPlayingFragment newInstance() {
        return new NowPlayingFragment();
    }

    @OnClick(R.id.button_previous)
    public void previousClicked() {
        new PlayBackIntentBuilder(getContext())
                .setPlayerCommand(PREVIOUS)
                .buildAndSubmit();
    }

    @OnClick(R.id.button_next)
    public void nextClicked() {
        new PlayBackIntentBuilder(getContext())
                .setPlayerCommand(NEXT)
                .buildAndSubmit();
    }

    @OnClick(R.id.button_play_pause_stop)
    public void playPauseStopClicked() {
        new PlayBackIntentBuilder(getContext())
                .setPlayerCommand(STOP_PLAY_PAUSE)
                .buildAndSubmit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);

        albumCover.setImageDrawable(null);

        this.viewModel = ViewModelProviders.of(this).get(NowPlayingViewModel.class);

        observeCurrentSong();
        observeCurrentPlayStatus();

        return view;
    }

    private void observeCurrentSong() {
        this.viewModel.songLiveData.observe(this, song -> {
            if (song != null && song.albumImageData != null) {
                final Bitmap bitmap = BitmapFactory.decodeByteArray(
                        song.albumImageData, 0, song.albumImageData.length);
                albumCover.setImageBitmap(bitmap);
            } else {
                albumCover.setImageDrawable(null);
            }

            if (song != null) {
                trackNumber.setText(String.valueOf(song.track));
                songName.setText(song.name);
                albumName.setText(song.albumName);
            } else {
                trackNumber.setText("--");
                songName.setText("");
                albumName.setText("");
            }
        });
    }

    private void observeCurrentPlayStatus() {
        this.viewModel.playStatusLiveData.observe(this, playStatus -> {
            switch (playStatus) {
                case PLAYING:
                    playPauseStopButton.setImageResource(R.drawable.ic_pause_white_48dp);
                    break;
                case STOPPED:
                    playPauseStopButton.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                    break;
                case PAUSED:
                    playPauseStopButton.setImageResource(R.drawable.ic_play_arrow_white_48dp);
            }
        });
    }
}
