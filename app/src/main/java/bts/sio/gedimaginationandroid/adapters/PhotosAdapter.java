package bts.sio.gedimaginationandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bts.sio.gedimaginationandroid.ListePhotos;
import bts.sio.gedimaginationandroid.R;
import bts.sio.gedimaginationandroid.models.PhotosItem;

public class PhotosAdapter extends BaseAdapter {

    private Context context;
    private List<PhotosItem> photosList;
    private LayoutInflater inflater;

    public PhotosAdapter(Context context, List<PhotosItem> photosList) {
        this.context = context;
        this.photosList = photosList;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return this.photosList.size();
    }

    @Override
    public PhotosItem getItem(int position) {
        return this.photosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adapter_lv_photos,null);

        //information sur l'item
        PhotosItem currentItem = getItem(position);
        String itemTitre = currentItem.getTitre();
        int itemId = currentItem.getNumero();

        //get item titre view
        TextView itemTitreView = convertView.findViewById(R.id.titre_photo_txt);
        itemTitreView.setText(itemTitre);

        //get item id view
        //TextView itemIdView = convertView.findViewById(R.id.id_photo_txt);
        //itemIdView.setText(String.valueOf(itemId));
        return convertView;
    }
}
