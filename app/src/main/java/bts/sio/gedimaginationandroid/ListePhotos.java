package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import bts.sio.gedimaginationandroid.adapters.PhotosAdapter;
import bts.sio.gedimaginationandroid.models.PhotosItem;

public class ListePhotos extends AppCompatActivity {
    private String[] lesCouleurs = {"Blanc","Bleu","Jaune","Noir","Rouge","Vert"} ;
    private Boolean estClique = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_photos);

        //listes des photos
        List<PhotosItem> photosItemList = new ArrayList<>();
        photosItemList.add(new PhotosItem(1,"un perry"));
        photosItemList.add(new PhotosItem(2,"une table carré"));
        photosItemList.add(new PhotosItem(3,"du carrelage"));
        photosItemList.add(new PhotosItem(4,"mon salon tout neuf"));
        lesLivres = new


        //get list view
        final ListView photosLV = findViewById(R.id.LV_photos);

        photosLV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,lesCouleurs));
        //photosLV.setAdapter(new PhotosAdapter(this, photosItemList));

        /*photosLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout lnl = (LinearLayout) photosLV.getChildAt(position);
                CardView cv = (CardView) lnl.findViewById(R.id.card_view);
                cv.setSelected(true);
                cv.setPressed(true);
                cv.setBackground(Drawable.createFromPath("/drawable/txt_view_bg.xml"));

            }
        });*/
    }
}