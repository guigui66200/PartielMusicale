package fr.partiel.partiel;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

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

        urlSwitch = new UrlSwitch();
        aq = new AQuery(this);
        aq.id(R.id.btn_login).clicked(this, "buttonConnectClicked");

    }

    public void buttonConnectClicked(View view){
        loader.setVisibility(View.VISIBLE);
        AQuery aq = new AQuery(this);

        EditText login = (EditText) findViewById(R.id.input_pseudo);
        String loginVal = login.getText().toString();

        EditText password = (EditText) findViewById(R.id.input_password);
        String passwordVal = password.getText().toString();

        JSONObject user = new JSONObject();

        try{
            user.put("mail", loginVal);
            user.put("mdp", passwordVal);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Log.e("LoginActivity", user.toString());

        AjaxCallback<JSONObject> loginCallback = new AjaxCallback<JSONObject>() {
            public void callback(String url, JSONObject json, AjaxStatus status) {
                boolean connect = false;
                if(json != null){
                    Log.e("LoginActivity", "enter json");
                    connect = true;
                }else {
                    //ajax error, show error code
                    Log.e("LoginActivity", status.getMessage());
                    toToast("Aucune réponse du serveur, veuillez activé vos datas ou votre wifi");
                    loader.setVisibility(View.INVISIBLE);
                }
                if(connect){
                    try {
                        JSONObject token = json.getJSONObject("keySession");

                        String tokenVal = token.getString("isValid");

                        Log.e("LoginFragment", tokenVal);
                        Log.e("LoginFragment", json.toString());

                        toToast("SUCCESS");
                        //go to channel
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e){
                        try {
                            JSONObject message = json.getJSONObject("message");
                            toToast("ERROR because :" + e + message);
                            loader.setVisibility(View.INVISIBLE);
                        }catch (JSONException je){
                            toToast("ERROR because :" + e );
                            loader.setVisibility(View.INVISIBLE);
                        }
                    }
                }else{
                    loader.setVisibility(View.INVISIBLE);
                }
            }
        };

        String url = urlSwitch.urlChoice("CONNECT");
        aq.post(url, user, JSONObject.class, loginCallback);
        System.out.println(url);
    }

    public void toToast(String string){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }
}
