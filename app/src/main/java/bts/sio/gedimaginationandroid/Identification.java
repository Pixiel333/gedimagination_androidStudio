package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        valider.setEnabled(false);
        if (id.getText() != null && nom.getText() != null && prenom.getText() != null && email.getText() != null)
        {
            valider.setEnabled(true);
        }

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maBDD = new PhotosDAO(Identification.this);
                if (maBDD.idTicketExistant(id.getText().toString()))
                {

                }
                else
                {

                }
            }
        });
    }
}