package techguy.webviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bugsnag.android.Bugsnag;

public class Splash extends AppCompatActivity {
    /**
     * get all the layouts components here
     */
    Network network = new Network();
    ImageView imageView;
    TextView slogan, companyName, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bugsnag.init(this);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.splash_image);
        slogan = findViewById(R.id.slogan);
        companyName = findViewById(R.id.companyName);
        url = findViewById(R.id.url); ProgressBar spinner = findViewById(R.id.progressBar);

        //set color to progressbar
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#f5e6e8"), android.graphics.PorterDuff.Mode.MULTIPLY);

        //create animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        //start animation with the variables
        imageView.startAnimation(animation);
        companyName.startAnimation(animation);
        slogan.startAnimation(animation);
        spinner.startAnimation(animation);

        //redirect to url
        companyName.setTextColor(getResources().getColor(R.color.white_two));
        companyName.setMovementMethod(LinkMovementMethod.getInstance());
        companyName.setText(R.string.powered);
        Linkify.addLinks(url, Linkify.WEB_URLS);

        //start a new activity after splash screen
        final Intent intentMain = new Intent(this, MainPage.class);
        final Intent intentError = new Intent(this, Error404.class);

        //start a thread to give a counter
        Thread timer = new Thread() {
            public void run() {
                try {
                    //give your delay timer here
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //check network connection
                    String networkStatus = network.networkChecker(Splash.this);
                    if (networkStatus != null) {
                        startActivity(intentMain);
                        finish();
                    } else {
                        startActivity(intentError);
                        finish();
                    }
                }
            }
        };
        //start the timer
        timer.start();

    }
}
