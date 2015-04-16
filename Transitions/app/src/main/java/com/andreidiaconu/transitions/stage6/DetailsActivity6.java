package com.andreidiaconu.transitions.stage6;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.andreidiaconu.transitions.R;
import com.squareup.picasso.Picasso;

/**
 * Created by andrei on 4/14/2015.
 */
public class DetailsActivity6 extends Activity {

    public static void start(Activity from, String imageUrl, View initialView){
        Intent intent = new Intent(from, DetailsActivity6.class);
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
        setContentView(R.layout.activity_details_wrap);
        ((Toolbar) findViewById(R.id.action_bar)).setTitle("Details - Stage 6");

        ImageView imageView = (ImageView) findViewById(R.id.image);
        String imageUrl = getIntent().getStringExtra("imageUrl");

        Picasso
                .with(this)
                .load(imageUrl)
                .into(imageView);

        if (savedInstanceState==null){
            runAnimations();
        }else{
            canAnimateBack = false;
        }
    }

    public void runAnimations(){
        final ImageView imageView = (ImageView) findViewById(R.id.image);

        //We want the view to be measured before we go on with calculating the animation
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                actuallyRunAnimations();
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    public void actuallyRunAnimations(){ // :)
        ImageView imageView = (ImageView) findViewById(R.id.image);
        View background = findViewById(R.id.background);
        View actionbar = findViewById(R.id.action_bar);
        View tools = findViewById(R.id.tools);

        //Use the position and size from previous screen and set it to the image on this screen.
        Rect initialPosition = getIntent().getParcelableExtra("initialPosition");
        Rect endPosition = new Rect();

        imageView.getGlobalVisibleRect(endPosition);

        //Set initial size and position using scale and translate
        imageView.setScaleY((float) initialPosition.height() / endPosition.height());
        imageView.setScaleX((float) initialPosition.width() / endPosition.width());
        imageView.setTranslationY(initialPosition.centerY() - endPosition.centerY());
        imageView.setTranslationX(initialPosition.centerX() - endPosition.centerX());

//        background.setScaleY(0);
//        background.setScaleX(0);
        background.setAlpha(0);

        tools.setTranslationY(tools.getMeasuredHeight());
        actionbar.setTranslationY(-actionbar.getMeasuredHeight());

        reallyActuallyRunAnimations();
    }

    public void reallyActuallyRunAnimations(){ // :)
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        final View background = findViewById(R.id.background);
        final View actionbar = findViewById(R.id.action_bar);
        final View tools = findViewById(R.id.tools);

        imageView.animate()
                .scaleX(1)
                .scaleY(1)
                .translationX(0)
                .translationY(0)
                .start();

        background.animate()
                .alpha(1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        actionbar.animate()
                                .translationY(0)
                                .start();

                        tools.animate()
                                .translationY(0)
                                .start();
                    }
                })
                .start();
    }

    boolean canAnimateBack = true;
    @Override
    public void finish() {
        if (canAnimateBack)
            runAnimationsBackwards();
        else
            super.finish();
    }

    public void runAnimationsBackwards(){
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        final View background = findViewById(R.id.background);
        final View actionbar = findViewById(R.id.action_bar);
        final View tools = findViewById(R.id.tools);

        //Use the position and size from previous screen and set it to the image on this screen.
        final Rect initialPosition = getIntent().getParcelableExtra("initialPosition");
        final Rect endPosition = new Rect();

        imageView.getGlobalVisibleRect(endPosition);



        tools.animate()
                .translationY(tools.getMeasuredHeight())
                .start();
        actionbar.animate()
                .translationY(-actionbar.getMeasuredHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //Set initial size and position using scale and translate
                        imageView.animate()
                                .scaleY((float) initialPosition.height() / endPosition.height())
                                .scaleX((float) initialPosition.width() / endPosition.width())
                                .translationY(initialPosition.centerY() - endPosition.centerY())
                                .translationX(initialPosition.centerX() - endPosition.centerX())
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        DetailsActivity6.super.finish();
                                        overridePendingTransition(0, 0);
                                    }
                                })
                                .start();

                        background.animate()
                                .alpha(0)
                                .start();
                    }
                })
                .start();
    }

}
