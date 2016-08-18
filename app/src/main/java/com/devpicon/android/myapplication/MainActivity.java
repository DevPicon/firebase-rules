package com.devpicon.android.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser != null){
            // Hay un usuario logueado
            setContentView(R.layout.activity_main);

            String nombreCompleto = currentUser.getDisplayName();
            String correoElectronico = currentUser.getEmail();

            TextView txtNombreCompleto = (TextView) findViewById(R.id.txtNombreCompleto);
            TextView txtCorreoElectronico = (TextView) findViewById(R.id.txtCorreoElectronico);

            txtNombreCompleto.setText(nombreCompleto);
            txtCorreoElectronico.setText(correoElectronico);

        } else {
            // No hay usuario logueado
            startActivityForResult(AuthUI
                    .getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(AuthUI.GOOGLE_PROVIDER)
                    .setLogo(R.mipmap.ic_launcher)
                    .build(), 101);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Se logueó correctamente!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
            }
            else{
                Toast.makeText(this, "Hubo un problema con el logueo", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
