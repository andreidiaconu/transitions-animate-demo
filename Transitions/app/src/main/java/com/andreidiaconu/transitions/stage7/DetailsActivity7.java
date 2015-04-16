package com.andreidiaconu.transitions.stage7;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.andreidiaconu.transitions.R;
import com.squareup.picasso.Picasso;

/**
 * Created by andrei on 4/14/2015.
 */
public class DetailsActivity7 extends Activity {

    public static void start(Activity from, String imageUrl, View initialView){
        Intent intent = new Intent(from, DetailsActivity7.class);
        intent.putExtra("imageUrl", imageUrl);

        Bundle animationBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(from, initialView, "shared_image").toBundle();
        ActivityCompat.startActivity(from,intent, animationBundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_shared);
        ((Toolbar) findViewById(R.id.action_bar)).setTitle("Details - Stage 7");

        ImageView imageView = (ImageView) findViewById(R.id.image);
        String imageUrl = getIntent().getStringExtra("imageUrl");

        Picasso
                .with(this)
                .load(imageUrl)
                .into(imageView);
    }

}
