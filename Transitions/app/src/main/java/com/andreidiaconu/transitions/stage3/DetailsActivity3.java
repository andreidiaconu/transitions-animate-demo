package com.andreidiaconu.transitions.stage3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andreidiaconu.transitions.R;
import com.squareup.picasso.Picasso;

/**
 * Created by andrei on 4/14/2015.
 */
public class DetailsActivity3 extends Activity {

    public static void start(Activity from, String imageUrl, View initialView){
        Intent intent = new Intent(from, DetailsActivity3.class);
        intent.putExtra("imageUrl", imageUrl);

        //Send the initial position as an intent parameter. A Rect contains x, y, width, height.
        Rect initialPosition = new Rect();
        initialView.getGlobalVisibleRect(initialPosition);
        intent.putExtra("initialPosition", initialPosition);

        from.startActivity(intent);
        from.overridePendingTransition(0,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_red);
        ((Toolbar) findViewById(R.id.action_bar)).setTitle("Details - Stage 3");

        ImageView imageView = (ImageView) findViewById(R.id.image);
        String imageUrl = getIntent().getStringExtra("imageUrl");

        Picasso
                .with(this)
                .load(imageUrl)
                .into(imageView);

        if (savedInstanceState==null){
            runAnimations();
        }
    }

    public void runAnimations(){
        ImageView imageView = (ImageView) findViewById(R.id.image);

        //Use the position and size from previous screen and set it to the image on this screen.
        Rect initialPosition = getIntent().getParcelableExtra("initialPosition");

        //Don't know what the size of the image will be yet, so i'll just set it's size instead of scaling.
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = initialPosition.width();
        layoutParams.height = initialPosition.height();
        layoutParams.topMargin = initialPosition.top;
        layoutParams.leftMargin = initialPosition.left;
        imageView.setLayoutParams(layoutParams);


        //Not pretty, but good enough for making a point.
        findViewById(R.id.background).setBackgroundColor(Color.TRANSPARENT);
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.background).setBackgroundColor(getResources().getColor(R.color.details_background));
            }
        }, 3000);
    }
}
