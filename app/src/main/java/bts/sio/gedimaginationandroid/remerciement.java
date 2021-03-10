package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class remerciement extends AppCompatActivity {

    private  Boolean dejaVoter;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remerciement);

        Intent i = getIntent();
        dejaVoter = i.getBooleanExtra("dejaVoter",false);

        message = (TextView)findViewById(R.id.textRemerciement);
        if (dejaVoter)
        {
            message.setText("Vous avez déjà voté !\n Pour revoter vous pouvez faire un nouvel achat !");
        }
        else
        {
            message.setText("Merci d'avoir voté !\n Pour revoter vous pouvez faire un nouvel achat !");
        }
    }
}