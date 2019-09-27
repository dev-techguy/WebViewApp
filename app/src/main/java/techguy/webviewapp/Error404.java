package techguy.webviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Error404 extends AppCompatActivity {
    Button tryAgain;
    TextView companyName, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error404);

        url = findViewById(R.id.url);
        companyName = findViewById(R.id.companyName);
        tryAgain = findViewById(R.id.tryAgain);

        //try to refresh/reload again
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Error404.this, MainPage.class));
            }
        });

        //redirect to url
        companyName.setTextColor(getResources().getColor(R.color.white_two));
        companyName.setMovementMethod(LinkMovementMethod.getInstance());
        companyName.setText(R.string.powered);
        Linkify.addLinks(url, Linkify.WEB_URLS);
    }
}
