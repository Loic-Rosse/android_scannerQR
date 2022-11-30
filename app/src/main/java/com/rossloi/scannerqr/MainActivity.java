package com.rossloi.scannerqr;


import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    // création des variables
    private Button BT_Scan;
    public static Context context;
    public  static Double latitude;
    public static Double longitude;
    public static String nomBenne;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation
        BT_Scan = findViewById(R.id.boutonScan);
        context = getApplicationContext();




        /**
         * Vérifie si le bouton est cliqué
         */
        BT_Scan.setOnClickListener(new View.OnClickListener() {

            /**
             * Au clic,
             * @param view
             */
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        MainActivity.this
                );

                // Etablir le texte
                intentIntegrator.setPrompt("Pour le flash, utilisez le bouton augmenté volume");

                // Etablir le son
                intentIntegrator.setBeepEnabled(true);

                // Vérrouiller l'orientation
                intentIntegrator.setOrientationLocked(true);

                // Etablir l'activité capture
                intentIntegrator.setCaptureActivity(Capture.class);

                // Etablir le scan
                intentIntegrator.initiateScan();

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Initialise le résultat
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        // check les conditions
        if (intentResult.getContents() != null){

            String text = intentResult.getContents();
            // Sépare les éléments scannés dans le QRcode
            String[] value = text.split(";");

            if (value.length != 3){
                Toast.makeText(getApplicationContext(),"oups, ton QRCode n'est pas valide", Toast.LENGTH_SHORT).show();
            }else {

                latitude = Double.parseDouble(value[0]);
                longitude = Double.parseDouble(value[1]);
                nomBenne = value[2];


            }

            Intent sms = new Intent(MainActivity.this,smsActivity.class);
            MainActivity.this.startActivity(sms);



        } else {
            // quand le résultat est nul
            Toast.makeText(getApplicationContext(),"OUPS.. Tu n'as rien scanné", Toast.LENGTH_SHORT).show();
        }
    }



}