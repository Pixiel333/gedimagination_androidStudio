package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bts.sio.gedimaginationandroid.models.PhotosItem;

public class ListePhotos extends AppCompatActivity {
    private PhotosDAO maBDD;
    private ArrayList<String> lesPhotosListe = null;
    private Boolean estClique = false;
    private ArrayList<HashMap<String,String>> lesPhotos;
    private ListView photosLV = null;
    private Button btnValider = null;
    private  List<PhotosItem> photosItemList = new ArrayList<PhotosItem>();
    private List<PhotosItem> photosChecked;
    private String idTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_photos);
        Intent intent = getIntent();
        idTicket = intent.getStringExtra("idTicket");
        //get list view
        photosLV = findViewById(R.id.LV_photos);
        maBDD = new PhotosDAO(ListePhotos.this);
        ArrayList<Photo> listPhotos = maBDD.selectionnerLesPhotos();
        btnValider = (Button) findViewById(R.id.voter);
        btnValider.setEnabled(false);
        //listes des photos
        for (Photo laPhoto : listPhotos)
        {
            photosItemList.add(new PhotosItem(laPhoto.getId(),laPhoto.getTitre()));
        }
        lesPhotosListe = new ArrayList<String>();
        for (PhotosItem photoItm: photosItemList) {
            lesPhotosListe.add(String.valueOf(photoItm.getNumero()) + " | " + photoItm.getTitre());
        }

        photosLV.setAdapter(new ArrayAdapter<String>(this, R.layout.adapter_lv_photos, lesPhotosListe ));
        photosLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray click = null;
                int cpt = 0;
                click = photosLV.getCheckedItemPositions();
                for (int i=0; i<click.size();i++) {
                    if (click.valueAt(i)) {
                        cpt++;
                    }
                    Log.i("test_valueAt", click.keyAt(i) + String.valueOf(click.valueAt(i)) + photosItemList.get(click.keyAt(i)).getTitre());
                }

                if (cpt > 3 || cpt == 0) {
                    btnValider.setEnabled(false);
                }
                else
                {
                    btnValider.setEnabled(true);
                }
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                photosChecked = new ArrayList<PhotosItem>();
                SparseBooleanArray lesChoix = photosLV.getCheckedItemPositions();
                for (int i=0; i<lesChoix.size();i++) {
                    if (lesChoix.valueAt(i)) {
                        photosChecked.add(photosItemList.get(lesChoix.keyAt(i)));
                    }
                }
                Log.i("tester", String.valueOf(photosChecked.size()));
                Intent notation = new Intent(ListePhotos.this, Notation.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ARRAYLIST",(Serializable)photosChecked);
                notation.putExtra("bundle", bundle);
                notation.putExtra("idTicket", idTicket);
                startActivity(notation);
            }
        });

    }
}