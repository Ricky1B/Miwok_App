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
public class ColorsFragment extends Fragment {
    private ListView list;
    private WordsAdapter wordsAdapter;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    ArrayList<Words> wordsList = new ArrayList<Words>();



    public ColorsFragment() {
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


        wordsList.add(new Words("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        wordsList.add(new Words("green","chokokki",R.drawable.color_green,R.raw.color_green));
        wordsList.add(new Words("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        wordsList.add(new Words("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_black));
        wordsList.add(new Words("black","kululli",R.drawable.color_black,R.raw.color_black));
        wordsList.add(new Words("white","kelelli",R.drawable.color_white,R.raw.color_white));
        wordsList.add(new Words("dusty Yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        wordsList.add(new Words("mustard Yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));



        list = rootView.findViewById(R.id.list);
        wordsAdapter = new WordsAdapter(getActivity(),wordsList,R.color.category_colors);
        wordsAdapter.notifyDataSetChanged();
        list.setAdapter(wordsAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Creating Mediaplayer class object
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
