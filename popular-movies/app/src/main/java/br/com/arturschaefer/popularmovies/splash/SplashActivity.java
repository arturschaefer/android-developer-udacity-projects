package br.com.arturschaefer.popularmovies.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.arturschaefer.popularmovies.R;
import br.com.arturschaefer.popularmovies.application.MoviesApplication;
import br.com.arturschaefer.popularmovies.login.LoginActivity;
import br.com.arturschaefer.popularmovies.main.MainActivity;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        final Intent intentLogin = new Intent(this, LoginActivity.class);
        final Intent intentMain = new Intent(this, MainActivity.class);

        int SPLASH_DISPLAY_LENGTH = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                MoviesApplication.firebaseAuth = FirebaseAuth.getInstance();
                if (MoviesApplication.firebaseAuth.getCurrentUser() == null) {
                    startActivity(intentLogin);
                    finish();
                } else {
                    MoviesApplication.firebaseUser = MoviesApplication.firebaseAuth.getCurrentUser();
                    startActivity(intentMain);
                    finish();
                }
            }

        }, SPLASH_DISPLAY_LENGTH);
    }
}
