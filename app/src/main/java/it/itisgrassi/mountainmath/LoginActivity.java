package it.itisgrassi.mountainmath;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity {

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USER = "user";
    private static final String TAG_PWD = "pwd";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SQUADRA = "squadra";
    private static final String TAG_CAPITANO = "capitano";
    private static final int STATUS_OK = 200;
    private Button invio;
    private TextView info;
    private String tmpUser ;
    private String tmpPass ;
    private static final String url_login_response = "http://mountainmath2.altervista.org/AMM/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        invio = (Button) findViewById(R.id.invio);
        invio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                tmpUser = getUser();
                tmpPass = getPass();
                new Submit(tmpUser,tmpPass).execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getUser(){
        EditText user = (EditText) findViewById(R.id.userText);
        return user.getText().toString();
    }

    public String getPass(){
        EditText pass = (EditText) findViewById(R.id.passText);
        return pass.getText().toString();
    }

    public void setInfo(String s){
        TextView info = (TextView)findViewById(R.id.info);
        info.setText(s);
    }

    public void clear(EditText e){
        e.setText("");
    }

    /*******************************************************************************************/

    public class Submit extends AsyncTask<String, String, String> {

        String utente=null;
        String pwd=null;
        int success = 0;
        int capitano = 0;
        int status = 0;
        String message = null;
        String squadra = null;
        JSONObject json = null;

        public Submit(String u,String p){
            utente = u;
            pwd = p;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login in corso...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            Log.d("INPUT", utente + " - " + pwd);
        }

        /**
         * Saving product
         */
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_USER, utente));
            params.add(new BasicNameValuePair(TAG_PWD, pwd));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            try {
                 json = jParser.makeHttpRequest(url_login_response,"POST", params);
                 //status = jsonParser.getStatusCode();
                 //Log.d("HTTP-STATUS",""+status);

                //if(status == STATUS_OK) {
                    // check json success tag
                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);

                    //capitano = json.getInt(TAG_CAPITANO);

                    if (success == 1) {  //debolezza strutturale: cambiare il success = 1 con un valore univoco o calcolabile
                        squadra = json.getString(TAG_SQUADRA);
                        Login.setSquadra(squadra);
                        Login.setUtente(tmpUser);
                        Login.setPassword(tmpPass);
                        Log.d("LOGIN","Prima di intent");

                        //apri nuovo intent
                        Context some = getApplicationContext();
                        Intent newintent = new Intent(some, ListActivity.class);
                        startActivity(newintent);
                        Log.d("LOGIN","Dopo req intent");
                    }else{
                        Log.e("ERR","no success");
                    }
                //}

            } catch (JSONException e) {
                Log.e("LOGIN", e.getMessage());

            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            setInfo(message);
            //Toast.makeText(getApplicationContext(), squadra, Toast.LENGTH_LONG).show();
        }
    }

}
