package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bts.sio.gedimaginationandroid.models.PhotosItem;

public class Notation extends AppCompatActivity {
    private List<PhotosItem> photosChecked;
    private  ArrayList<HashMap<String,String>> lesPhotos;
    private ListView LVPhotosVote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notation);
        Intent i = getIntent();
        Bundle args = i.getBundleExtra("bundle");
        photosChecked = (ArrayList<PhotosItem>) args.getSerializable("ARRAYLIST");
        lesPhotos = new ArrayList<HashMap<String, String>>();

        LVPhotosVote = (ListView) findViewById(R.id.LV_photos_vote);

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
                new int[] {R.id.id_notation_image, R.id.nom_notation_image}));

    }
}