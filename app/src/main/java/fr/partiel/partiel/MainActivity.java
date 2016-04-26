package fr.partiel.partiel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.androidquery.AQuery;

import io.realm.Realm;

/**
 * Created by guillaumeboutin on 26/04/2016.
 */
public class MainActivity extends AppCompatActivity {

    // UI references.
    ProgressBar loader;
    Realm realm;
    UrlSwitch urlSwitch;
    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loader = (ProgressBar) findViewById(R.id.progressBar1);
        loader.setVisibility(View.INVISIBLE);

        aq = new AQuery(this);
        aq.id(R.id.btn_login).clicked(this, "buttonConnectClicked");

    }
}
