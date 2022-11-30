package com.rossloi.scannerqr;

import static com.rossloi.scannerqr.MainActivity.latitude;
import static com.rossloi.scannerqr.MainActivity.longitude;
import static com.rossloi.scannerqr.MainActivity.nomBenne;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;


public class messageFragment extends Fragment {

    // création des variables
    private Button BT_Envoyer;
    private TextInputEditText ED_Message;
    private String numero;
    private String url = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        // Initialisation
        ED_Message = view.findViewById(R.id.EditText_Message);
        BT_Envoyer = view.findViewById(R.id.boutonEnvoyer);
        ED_Message.setText(nomBenne + " qui se trouve ici : " + url + " est remplie. Veuillez la récupérer");


        /**
         * Vérifie si le bouton est cliqué
         */
        BT_Envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // permission pour les SMS
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    sendMSG(ED_Message.getText().toString());

                } else {
                    // Si les permissions sont refusés
                    ActivityCompat.requestPermissions((Activity) getContext(),
                            new String[]{Manifest.permission.SEND_SMS}, 44);
                }
            }
        });

        return view;
    }


    /**
     * Fonction qui permet d'envoyer le sms
     *
     * @param msg Le message qui devra être envoyer
     */
    private void sendMSG(String msg) {

        // Initialisation du numéro de téléphone
        numero = "0793593384";

        //Si le message n'est pas vide on lance la procédure d'envoi
        if (msg.length() > 0) {
            //Grâce à l'objet de gestion de SMS (SmsManager) que l'on récupère via la méthode static getDefault()
            //On envoie le SMS à l'aide de la méthode sendTextMessage
            SmsManager.getDefault().sendTextMessage(numero, null, msg, null, null);
        }

        // relance l'activité main
        Intent main = new Intent(getContext(), MainActivity.class);
        messageFragment.this.startActivity(main);
    }


}





