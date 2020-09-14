package com.mehmetaltindal.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tryText,usdText,cadText,chfText,jpyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tryText = findViewById(R.id.tryText);
        jpyText = findViewById(R.id.jpyText);
        cadText = findViewById(R.id.cadText);
        usdText = findViewById(R.id.usdText);
        chfText = findViewById(R.id.chfText);
    }


    public void getRates(View view){

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://data.fixer.io/api/latest?access_key=03cb371c1b7c160100f03d8162d8febd&format=1";

            downloadData.execute(url);

        }catch (Exception e){



        }

    }

    public class DownloadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data =inputStreamReader.read();

                while (data > 0){

                    char character = (char) data;
                    result += character;
                    data = inputStreamReader.read();
                }

                return result;

            }catch (Exception e){
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            //System.out.println("aldık mı?  " +  s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                System.out.println("base : " + base);
                String rates = jsonObject.getString("rates");
                System.out.println("rates : " + rates);

                JSONObject jsonObject2 = new JSONObject(rates);
                String turkishLira = jsonObject2.getString("TRY");
                String usd = jsonObject2.getString("USD");
                String cad = jsonObject2.getString("CAD");
                String chf = jsonObject2.getString("CHF");
                String jpy = jsonObject2.getString("JPY");
                //System.out.println("TRY : "+ turkishLira );

                tryText.setText("TRY : "+ turkishLira);
                usdText.setText("USD : "+usd);
                cadText.setText("CAD : "+ cad);
                chfText.setText("CHF : "+chf);
                jpyText.setText("JPY : "+ jpy);

            }catch (Exception e){

            }
        }
    }
}