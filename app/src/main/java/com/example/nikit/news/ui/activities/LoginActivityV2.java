package com.example.nikit.news.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nikit.news.R;
import com.example.nikit.news.api.ApiClient;
import com.example.nikit.news.firebase.FirebaseDbHelper;
import com.example.nikit.news.ui.fragments.login.LoginMethodFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class LoginActivityV2 extends AppCompatActivity {
    private Button btLoginByFacebook;
    private Button btLoginByGoogle;

    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private static final String TAG = LoginMethodFragment.class.getSimpleName();
    private static String LOGIN_TAG = "LOGIN_FRAGMENT";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient googleApiClient;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_v2);

        firebaseAuth = FirebaseAuth.getInstance();

        //start initializing Firebase auth listener
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d(LOGIN_TAG, "firebase login ok");
                }else{
                    Log.d(LOGIN_TAG, "firebase login fail");
                }
            }
        };
        //end

        //start initializing facebook Login manager
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(LOGIN_TAG, "success login by facebook");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(LOGIN_TAG, "cancel login by facebook");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(LOGIN_TAG, "login by facebook error" + error.getMessage());
            }
        });
        //end

        //start initializing google login

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivityV2.this, null )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //end


        btLoginByFacebook = (Button) findViewById(R.id.login_by_facebook);
        btLoginByFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOGIN_TAG, "login start");
                LoginManager.getInstance().logInWithReadPermissions(LoginActivityV2.this,
                        Arrays.asList("public_profile", "user_friends"));

            }
        });

        btLoginByGoogle = (Button) findViewById(R.id.bt_login_by_google);
        btLoginByGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {

            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuthStateListener!=null){
            firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivityV2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FirebaseDbHelper helper = new FirebaseDbHelper();
    }

    private void handleFacebookAccessToken(AccessToken token){
        Log.d(LOGIN_TAG, "call handleFacebookAccessToken method");

        Log.d(LOGIN_TAG, "FacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(LOGIN_TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.w(LOGIN_TAG, "signInWithCredential", task.getException());
                    Toast.makeText(LoginActivityV2.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
