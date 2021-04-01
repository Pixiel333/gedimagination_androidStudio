package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import bts.sio.gedimaginationandroid.models.PhotosItem;

public class Notation extends AppCompatActivity {
    private List<PhotosItem> photosChecked;
    private  ArrayList<HashMap<String,String>> lesPhotos;
    private ListView LVPhotosVote;
    private Button btnVoter;
    private PhotosDAO maBDD;
    private String idTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notation);
        Intent intent = getIntent();
        idTicket = intent.getStringExtra("idTicket");
        Bundle args = intent.getBundleExtra("bundle");
        photosChecked = (ArrayList<PhotosItem>) args.getSerializable("ARRAYLIST");
        lesPhotos = new ArrayList<HashMap<String, String>>();
        LVPhotosVote = (ListView) findViewById(R.id.LV_photos_vote);
        btnVoter = (Button) findViewById(R.id.voter);

        for (PhotosItem photoSelec : photosChecked) {
            HashMap<String,String> unePhoto = new HashMap<String, String>();
            unePhoto.put("ID", String.valueOf(photoSelec.getNumero()));
            unePhoto.put("TITRE", photoSelec.getTitre());
            lesPhotos.add(unePhoto);
        }

        LVPhotosVote.setAdapter(new SimpleAdapter(this,
                lesPhotos,
                R.layout.adapter_lv_notation,
                new String[] {"ID","TITRE"},
                new int[] {R.id.id_notation_image, R.id.nom_notation_image})
        );
        Log.i("tester_lv", String.valueOf(LVPhotosVote.getCount()));
        //for (int i=0; i<LVPhotosVote.getCount();i++) {
            //View v = LVPhotosVote.getChildAt(i);
            //RatingBar ratingBar = v.findViewById(R.id.rb_notation_image);
            //Log.i("test-rb", String.valueOf(ratingBar.getRating()));
        //}

        btnVoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                maBDD = new PhotosDAO(Notation.this);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String dateNow = dateFormat.format(date);

                for (int i=0; i<LVPhotosVote.getCount();i++) {
                    View view = LVPhotosVote.getChildAt(i);
                    RatingBar ratingBar = view.findViewById(R.id.rb_notation_image);
                    TextView idText = (TextView) view.findViewById(R.id.id_notation_image);
                    maBDD.ajouterVote(idTicket,Integer.parseInt(idText.getText().toString()), (int)ratingBar.getRating(), dateNow);
                    Intent remerciement = new Intent(Notation.this, remerciement.class);
                    startActivity(remerciement);
                }
            }
        });



    }
}