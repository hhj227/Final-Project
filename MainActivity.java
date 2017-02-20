package com.example.hanhyejung.test9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.Button;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    EditText editText;
    EditText editText2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewsByID();

        editText = (EditText) findViewById(R.id.Name);
        editText2 = (EditText) findViewById(R.id.part_nr);


        Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String String_name=editText.getText().toString();
                String Int_Part=editText2.getText().toString();
                new Create_Part().execute(String_name, Int_Part);
            }
        });


    }
/*
        public void findViewsByID(){
            String_name = (EditText) findViewById(R.id.Name);
            password = (EditText) findViewById(R.id.txtPass);
        }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Send(View view) {
        new Create_Part().execute();
    }

    class Create_Part extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Sending part to the database...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String String_name = editText.getText().toString();
            //String String_name = editText.
            String Int_Part = editText2.getText().toString();



            //new Create_Part().execute(editText.getText().toString(), editText2.getText().toString());


            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Name", String_name));
            params.add(new BasicNameValuePair("part_nr", Int_Part));

            JSONObject json = jsonParser.makeHttpRequest("RaspberryPi_IP/db_create.php", "POST", params);

            try {
                int success = json.getInt("success");

                if(success == 1){
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url){
            pDialog.dismiss();
        }
    }

}
