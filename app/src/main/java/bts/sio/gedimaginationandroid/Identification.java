package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cz.msebera.android.httpclient.util.TextUtils;

public class Identification extends AppCompatActivity {

    private PhotosDAO maBDD;
    private EditText id;
    private EditText nom;
    private EditText prenom;
    private EditText email;
    private Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        id = (EditText)findViewById(R.id.idText);
        nom = (EditText)findViewById(R.id.nomText);
        prenom = (EditText)findViewById(R.id.prenomText);
        email = (EditText)findViewById(R.id.emailText);
        valider = (Button)findViewById(R.id.valider);


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maBDD = new PhotosDAO(Identification.this);
                if (TextUtils.isEmpty(id.getText().toString()) || TextUtils.isEmpty(prenom.getText().toString()) || TextUtils.isEmpty(nom.getText().toString()) || TextUtils.isEmpty(email.getText().toString()))
                {

                }
                else
                {
                    valider.setClickable(true);
                    if (maBDD.idTicketExistant(id.getText().toString()))
                    {
                        Intent remerciement = new Intent(Identification.this, remerciement.class);
                        remerciement.putExtra("dejaVoter", true);
                        startActivity(remerciement);
                    }
                    else
                    {
                        String textId = id.getText().toString();
                        String textPrenom = prenom.getText().toString();
                        String textNom = nom.getText().toString();
                        String textEmail = email.getText().toString();

                        maBDD.ajouterAchat(textId, textPrenom, textNom, textEmail);
                    }
                }
            }
        });
    }
}