package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

public class PracticalTest02 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
        Button clickButton = (Button) findViewById(R.id.connect_button);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String url = "https://api.coindesk.com/v1/bpi/currentprice/EUR.json";

                RequestPackage requestPackage = new RequestPackage();
                requestPackage.setMethod("GET");
                requestPackage.setUrl(url);

                Httpreq req = new Httpreq();
                req.execute(requestPackage);

            }
        });

    }

    private class Httpreq extends AsyncTask<RequestPackage, String, String> {
        @Override
        protected String doInBackground(RequestPackage... params) {
            return HtppManager.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                Log.i("resp", result);
                JSONObject jsonObject = new JSONObject(result);
                String usd = jsonObject.getJSONObject("bpi").getJSONObject("USD").getString("rate");
                String eur = jsonObject.getJSONObject("bpi").getJSONObject("EUR").getString("rate");
                Log.i("usd", usd);
                Log.i("eur", eur);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}