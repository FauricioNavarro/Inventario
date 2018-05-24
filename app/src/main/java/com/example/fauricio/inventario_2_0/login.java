package com.example.fauricio.inventario_2_0;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    private GoogleApiClient myGoogleSignInClient;
    private GoogleSignInOptions googleSignInOptions;
    private SignInButton signInButton;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signInButton = findViewById(R.id.sign_in_button);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        myGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();

        signInButton.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(googleSignInResult);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:{
                login();
            }break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w("IsNotSuccess", "signInResult:failed code=" + connectionResult);
    }

    private void handleSignInResult(GoogleSignInResult completedTask) {
        if(completedTask.isSuccess()){
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Log.w("IsNotSuccess", "signInResult:failed code=" + -1);
        }
    }


    public void login(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(myGoogleSignInClient);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,RC_SIGN_IN);
    }
}
