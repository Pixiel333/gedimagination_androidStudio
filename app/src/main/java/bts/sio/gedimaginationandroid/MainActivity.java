package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Button btnImporter = null;
    private Button btnVoter = null;
    private PhotosDAO maBDD;
    private Photo P;
    private Boolean donneeImporte = false;
    private Boolean finiImportDates = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnImporter = (Button)findViewById(R.id.importer);
        btnVoter = (Button)findViewById(R.id.voter);
        btnVoter.setVisibility(View.INVISIBLE);
        btnImporter.setVisibility(View.INVISIBLE);
        importDates();

        btnImporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url1 = "http://10.0.2.2:8080/gedimagination/WebService/concours/photos";
                AsyncHttpClient request = new AsyncHttpClient();
                request.get(url1, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        maBDD = new PhotosDAO(MainActivity.this);
                        maBDD.supprimerTous();
                        for(int i = 0 ; i < response.length() ; i++) {
                            try {
                                int id = response.getJSONObject(i).getInt("id");
                                String titre = response.getJSONObject(i).getString("titre");
                                String description = response.getJSONObject(i).getString("description");
                                String date = response.getJSONObject(i).getString("date");
                                String cheminPhoto = response.getJSONObject(i).getString("chemin_photo");
                                Photo P = new Photo(id, titre, description, date, cheminPhoto);
                                maBDD.ajouterPhoto(P);
                                donneeImporte = true;
                                Toast.makeText(getApplicationContext(), "Les données ont bien été importées !", Toast.LENGTH_LONG).show();
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        maBDD.close();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable err) {
                        Toast.makeText(getApplicationContext(), "StatusCode = " + Integer.toString(statusCode) + "\n Erreur = " + err.toString() , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnVoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent identification = new Intent(MainActivity.this, Identification.class);
                startActivity(identification);
            }
        });
    }

    public Boolean importDates(){
        String url = "http://10.0.2.2:8080/gedimagination/WebService/concours/dates";
        AsyncHttpClient request = new AsyncHttpClient();
        request.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                maBDD = new PhotosDAO(MainActivity.this);
                try {
                    String dateDebutInsc = response.getString("date_debut_insc");
                    String dateFinInsc = response.getString("date_fin_insc");
                    String dateDebutVote = response.getString("date_debut_vote");
                    String dateFinVote = response.getString("date_fin_vote");
                    Dates D = new Dates(dateDebutInsc, dateFinInsc, dateDebutVote, dateFinVote);
                    maBDD.ajouterDates(D);
                    Log.i("test", String.valueOf(response));
                    Log.i("test1", response.getString("date_fin_vote"));
                    Log.i("test_verif", verifDate().toString());
                    if (verifDate() == true) {
                        btnImporter.setVisibility(View.VISIBLE);
                    }
                    else{
                        btnImporter.setVisibility(View.INVISIBLE);
                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }

                maBDD.close();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable err) {
                Toast.makeText(getApplicationContext(), "StatusCode = " + Integer.toString(statusCode) + "\n Erreur = " + err.toString() , Toast.LENGTH_LONG).show();
            }
        });
        Log.i("test_finish", String.valueOf(finiImportDates));
        return finiImportDates;
    }

    public Boolean verifDate() {
        Log.i("verif date", "okay");
        Boolean dateValide = false;
        maBDD = new PhotosDAO(this);
        Cursor curseurTous = maBDD.selectionnerLesDates();
        Log.i("verif date", String.valueOf(curseurTous.getCount()));
        String dateFinInsc = "";
        String dateDebutVote = "";
        for(curseurTous.moveToFirst(); !curseurTous.isAfterLast(); curseurTous.moveToNext()) {
            Log.i("Passage for", "okay");
            dateFinInsc= curseurTous.getString(curseurTous.getColumnIndex("dateFinInsc"));
            dateDebutVote = curseurTous.getString(curseurTous.getColumnIndex("dateDebutVote"));
        }
        curseurTous.close();
        maBDD.close();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNow = new Date();
            String date = dateFormat.format(dateNow);
            Date dateFin = dateFormat.parse(dateFinInsc);
            Date dateDebut = dateFormat.parse(dateDebutVote);
            Toast.makeText(getApplicationContext(), "ajd :" + date +"\n dateFIn : " + dateFinInsc +"\n dateDebut : " + dateDebutVote, Toast.LENGTH_LONG).show();
            Log.i("test_dates","ajd :" + dateNow +"\n dateFIn : " + dateFin +"\n dateDebut : " + dateDebut);
            Log.i("test_dateafter", String.valueOf(dateNow.after(dateFin)));
            Log.i("test_dateBefor", String.valueOf(dateNow.before(dateDebut)));
            if (dateNow.after(dateFin) && dateNow.before(dateDebut))
            {
                dateValide = true;
            }
            else if (dateNow.after(dateDebut))
            {
                btnVoter.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("test_date",dateValide.toString());
        return  dateValide;
    }
}