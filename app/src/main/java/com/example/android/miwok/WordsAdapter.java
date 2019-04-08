package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordsAdapter extends ArrayAdapter<Words> {

    private int mColorResourceId;

    public WordsAdapter(Activity context, ArrayList<Words> words,int colorId){
        super(context,0,words);
        mColorResourceId = colorId;
    }


    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Words currentWord = getItem(position);
        View listItemView = convertView;
        //check if the listItem view provided is null, if yes inflate it from scratch
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }



        TextView defaultTranslation = (TextView) listItemView.findViewById(R.id.listItem2);
        defaultTranslation.setText(currentWord.getDefaultTranslation());

        TextView miwokTranslation = (TextView) listItemView.findViewById(R.id.listItem1);
        miwokTranslation.setText(currentWord.getMiwokTranslation());


        ImageView imageView = (ImageView)listItemView.findViewById(R.id.listImage);
        if(currentWord.hasImage()){
            //set image
            imageView.setImageResource(currentWord.getImageID());

            //Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);

        }else{

            //Hide the imageView
            imageView.setVisibility(View.GONE);
        }

        View textContainer =  listItemView.findViewById(R.id.linearLayout);
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);






        return listItemView;
    }


}
