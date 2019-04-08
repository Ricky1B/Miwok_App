package com.example.android.miwok;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {
    private ListView list;
    private WordsAdapter wordsAdapter;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);
        audioManager = (AudioManager)getActivity().getSystemService(getActivity().AUDIO_SERVICE);
        audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange){
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releaseMediaPlayer();
                        break;

                    case AudioManager.AUDIOFOCUS_GAIN:
                        mediaPlayer.start();
                        break;

                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        //Pausing the mediaplayer
                        if(mediaPlayer !=null) {
                            mediaPlayer.pause();
                            mediaPlayer.seekTo(0);
                        }
                        break;

                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        //Pausing as lowering volume doesn't make sense. We only have a very small audio clip
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                        break;
                }
            }
        };

        final ArrayList<Words> wordsList = new ArrayList<Words>();

        wordsList.add(new Words("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        wordsList.add(new Words("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        wordsList.add(new Words("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        wordsList.add(new Words("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        wordsList.add(new Words("I’m feeling good.","kucchi acchit",R.raw.phrase_im_feeling_good));
        wordsList.add(new Words("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        wordsList.add(new Words("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        wordsList.add(new Words("’m coming.","әәnәm",R.raw.phrase_im_coming));
        wordsList.add(new Words("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        wordsList.add(new Words("Come here","әnni'nem",R.raw.phrase_come_here));


        list = rootView.findViewById(R.id.list);
        wordsAdapter = new WordsAdapter(getActivity(),wordsList, R.color.category_phrases);
        wordsAdapter.notifyDataSetChanged();
        list.setAdapter(wordsAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(getActivity(),wordsList.get(position).getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
        return rootView;

    }
    public void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer =null;
        }else{
            try{ mediaPlayer.pause();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
