package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class NumbersFragment extends Fragment {
    private ListView list;
    private WordsAdapter wordsAdapter;
    ArrayList<Words> wordsList = new ArrayList<Words>();
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;



    public NumbersFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);
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

        wordsList.add(new Words("one","lutti", R.drawable.number_one,R.raw.number_one));
        wordsList.add(new Words("two","otiiko",R.drawable.number_two,R.raw.number_two));
        wordsList.add(new Words("three","tolookosu",R.drawable.number_three,R.raw.number_three));
        wordsList.add(new Words("four","oyyisa",R.drawable.number_four,R.raw.number_four));
        wordsList.add(new Words("five","massokka", R.drawable.number_five,R.raw.number_five));
        wordsList.add(new Words("six","temmokka",R.drawable.number_six,R.raw.number_six));
        wordsList.add(new Words("seven","kenekaku", R.drawable.number_seven,R.raw.number_seven));
        wordsList.add(new Words("eight","kawinta", R.drawable.number_eight,R.raw.number_eight));
        wordsList.add(new Words("nine","wo’e",R.drawable.number_nine,R.raw.number_nine));
        wordsList.add(new Words("ten","na’aacha",R.drawable.number_ten,R.raw.number_ten));


        list = rootView.findViewById(R.id.list);
        wordsAdapter = new WordsAdapter(getActivity(),wordsList, R.color.category_numbers);
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
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }else{
            try{ mediaPlayer.pause();
            }catch (Exception e){
                e.printStackTrace();
            }        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
