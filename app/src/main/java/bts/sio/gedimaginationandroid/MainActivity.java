package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bts.sio.gedimaginationandroid.models.Dates;
import bts.sio.gedimaginationandroid.models.Photo;
import bts.sio.gedimaginationandroid.models.Vote;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {

    private Button btnImporter = null;
    private Button btnVoter = null;
    private Button btnExporter = null;
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
        btnExporter = (Button)findViewById(R.id.exporter);
        btnVoter.setVisibility(View.INVISIBLE);
        btnImporter.setVisibility(View.INVISIBLE);
        btnExporter.setVisibility(View.INVISIBLE);
        importDates();

        btnImporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maBDD = new PhotosDAO(MainActivity.this);
                if (!maBDD.donneesImportees())
                {
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
                                    Toast.makeText(getApplicationContext(), "Les donn??es ont bien ??t?? import??es !", Toast.LENGTH_LONG).show();
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
                else
                {
                    Toast.makeText(getApplicationContext(), "Les donn??es ont d??j?? ??t?? import??es !", Toast.LENGTH_LONG).show();
                }
                maBDD.close();
            }
        });

        btnExporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONArray data = new JSONArray();
                    ArrayList<Vote> lesVotes = new ArrayList<Vote>();
                    maBDD = new PhotosDAO(MainActivity.this);
                    lesVotes = maBDD.getDataVote();
                    maBDD.close();
                    if (!lesVotes.isEmpty())
                    {
                        for (Vote vote : lesVotes) {
                            JSONObject jsonParams = new JSONObject();
                            jsonParams.put("idTicket", vote.getIdTicket());
                            jsonParams.put("idPhoto", vote.getIdPhoto());
                            jsonParams.put("rating", vote.getRating());
                            jsonParams.put("dateVote", vote.getDateVote());
                            data.put(jsonParams);
                        }

                        String url2 = "http://10.0.2.2:8080/gedimagination/WebService/concours/photos";
                        AsyncHttpClient client = new AsyncHttpClient();
                        StringEntity entity = new StringEntity(data.toString());
                        Log.i("testExport", data.toString());
                        client.post(MainActivity.this, url2, entity, "application/json", new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                Toast.makeText(getApplicationContext(), "Donn??es envoy??es, Status code = " + Integer.toString(statusCode), Toast.LENGTH_LONG).show();
                                btnExporter.setEnabled(false);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                if (statusCode == 401) {
                                    Toast.makeText(getApplicationContext(), "Erreur d'authentification, Status code = " + Integer.toString(statusCode), Toast.LENGTH_LONG).show();
                                } else if (statusCode != 201) {
                                    Toast.makeText(getApplicationContext(), "Erreur impossible d'envoyer les donn??es, Status code = " + Integer.toString(statusCode), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                } catch (JSONException | UnsupportedEncodingException e) {
                }

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
                    //Log.i("test", String.valueOf(response));
                    //Log.i("test1", response.getString("date_fin_vote"));
                    //Log.i("test_verif", verifDate().toString());
                    finiImportDates = true;
                    verifDate();
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

            @Override
            public void onFinish() {
                Log.i("testOnFinish", String.valueOf(finiImportDates));
                maBDD = new PhotosDAO(MainActivity.this);
                if (!maBDD.datesImportees() && !maBDD.donneesImportees())
                {
                    Toast.makeText(getApplicationContext(), "ERREUR : Vous n'??tes pas connect?? !", Toast.LENGTH_LONG).show();
                }
                maBDD.close();
                verifDate();
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
        String dateFinVote = "";
        for(curseurTous.moveToFirst(); !curseurTous.isAfterLast(); curseurTous.moveToNext()) {
            Log.i("Passage for", "okay");
            dateFinInsc= curseurTous.getString(curseurTous.getColumnIndex("dateFinInsc"));
            dateDebutVote = curseurTous.getString(curseurTous.getColumnIndex("dateDebutVote"));
            dateFinVote = curseurTous.getString(curseurTous.getColumnIndex("dateFinVote"));
        }
        curseurTous.close();
        maBDD.close();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNow = new Date();
            String date = dateFormat.format(dateNow);
            Date dateFinIns = dateFormat.parse(dateFinInsc);
            Date dateDebut = dateFormat.parse(dateDebutVote);
            Date dateFinV = dateFormat.parse(dateFinVote);
            Log.i("test", String.valueOf(dateFinV));
            if (dateNow.after(dateFinIns) && dateNow.before(dateDebut))
            {
                btnImporter.setVisibility(View.VISIBLE);
                dateValide = true;
            }
            else if (dateNow.after(dateDebut) && dateNow.before(dateFinV))
            {
                btnImporter.setVisibility(View.INVISIBLE);
                btnVoter.setVisibility(View.VISIBLE);
            }
            else if (dateNow.after(dateFinV))
            {
                btnExporter.setVisibility(View.VISIBLE);
                btnVoter.setVisibility(View.INVISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("test_date",dateValide.toString());
        return  dateValide;
    }
}