package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Identification extends AppCompatActivity {

    private EditText id;
    private EditText nom;
    private EditText prenom;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

    }
}