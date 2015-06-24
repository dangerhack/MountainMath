package it.itisgrassi.mountainmath;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Claudiu on 08/02/2015.
 */
public class Risultati extends Activity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USER = "user";
    private static final String TAG_PWD = "pwd";
    private static final String TAG_MESSAGE = "message";
    private Button invio;
    private TextView info;
    private static final String url_login_response = "http://hostingphpscuola.altervista.org/AMM/login.php";


    /************************************************************************************************
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
     /*******************************************************************************************/

    public void setInfo(String s){
        TextView info = (TextView)findViewById(R.id.classifica);
        info.setText(s);
    }

    /*******************************************************************************************/

    public class Submit extends AsyncTask<String, String, String> {

        String utente;
        String pwd;
        int success = 0;
        String message = null;
        JSONObject json = null;

        public Submit(String u,String p){
            utente = u;
            pwd = p;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Risultati.this);
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
                try {
                    json = jsonParser.makeHttpRequest(url_login_response, "GET", params);
                }
                catch(Exception internet){Log.e("HTTP_REQ","Errore http request");}

                // check json success tag
                success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                   // qui predere tutte le squadre e classificarle
                } else {

                }

            } catch (JSONException e) {
                Log.e("UPLOAD", "Errore login!");
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }
}
