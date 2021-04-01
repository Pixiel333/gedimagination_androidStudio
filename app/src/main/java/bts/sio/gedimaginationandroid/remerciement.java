package bts.sio.gedimaginationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class remerciement extends AppCompatActivity {

    private  Boolean dejaVoter;
    private TextView message;
    private Button btnRetour;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remerciement);


        btnRetour = (Button) findViewById(R.id.retour);
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

        //timer pour retour accueil auto
        timer = new CountDownTimer(15 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent accueil = new Intent(remerciement.this, MainActivity.class);
                startActivity(accueil);
            }
        };
        timer.start();

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent accueil = new Intent(remerciement.this, MainActivity.class);
                startActivity(accueil);
            }
        });
    }
}