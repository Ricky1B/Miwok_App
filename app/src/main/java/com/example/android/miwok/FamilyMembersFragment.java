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
public class FamilyMembersFragment extends Fragment{
        private ListView list;
        private WordsAdapter wordsAdapter;
        private MediaPlayer mediaPlayer;
        private AudioManager audioManager;
        AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
        ArrayList<Words> wordsList = new ArrayList<Words>();


    public FamilyMembersFragment() {
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
                        //Setting the mediaplayer at beginning as it doesn't make sense to hear translation in between

                        break;

                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        //Pausing as lowering volume doesn't make sense. We only have a very small audio clip
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                        break;
                }
            }
        };


        wordsList.add(new Words("father","әpә",R.drawable.family_father,R.raw.family_father));
        wordsList.add(new Words("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        wordsList.add(new Words("son","angsi",R.drawable.family_son,R.raw.family_son));
        wordsList.add(new Words("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        wordsList.add(new Words("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        wordsList.add(new Words("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        wordsList.add(new Words("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        wordsList.add(new Words("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        wordsList.add(new Words("grandmother","ama",R.drawable.family_grandfather,R.raw.family_grandmother));
        wordsList.add(new Words("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));


        list = rootView.findViewById(R.id.list);
        wordsAdapter = new WordsAdapter(getActivity(),wordsList,R.color.category_family);
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

        @Override
        public void onStop() {
            super.onStop();
            releaseMediaPlayer();
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




}
