package br.com.arturschaefer.popularmovies.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import br.com.arturschaefer.popularmovies.R;
import br.com.arturschaefer.popularmovies.application.MoviesApplication;
import br.com.arturschaefer.popularmovies.forgot_password.ForgotPasswordActivity;
import br.com.arturschaefer.popularmovies.main.MainActivity;
import br.com.arturschaefer.popularmovies.signup.SignUpActivity;
import br.com.arturschaefer.popularmovies.utilities.MovieUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;


public class LoginActivity extends MvpActivity implements LoginView {
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    @BindView(R.id.text_login_email)
    TextInputEditText textLoginEmail;
    @BindView(R.id.text_layout_email)
    TextInputLayout textLayoutEmail;
    @BindView(R.id.text_login_pass)
    TextInputEditText textLoginPass;
    @BindView(R.id.text_layout_pass)
    TextInputLayout textLayoutPass;
    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.text_forgot_pass)
    TextView textForgotPass;
    @BindView(R.id.button_login_google)
    Button buttonLoginGoogle;
    @BindView(R.id.button_create_user)
    Button buttonCreateUser;
    @BindView(R.id.image_logo)
    ImageView imageLogo;

    @InjectPresenter
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Picasso.get().load(R.drawable.movie_camera).into(imageLogo);
        MoviesApplication.firebaseAuth = FirebaseAuth.getInstance();
        presenter.activity = this;

        MoviesApplication.firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (MoviesApplication.firebaseAuth.getCurrentUser() != null) {
                    MoviesApplication.firebaseUser = MoviesApplication.firebaseAuth.getCurrentUser();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
        configClicks();
    }

    private void configClicks() {
        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.signIn(textLoginEmail.getText().toString(),
                        textLoginPass.getText().toString());
            }
        });

        buttonLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        ForgotPasswordActivity.class));
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (MoviesApplication.firebaseAuth == null) {
            MoviesApplication.firebaseAuth = FirebaseAuth.getInstance();
        }
        MoviesApplication.firebaseUser = MoviesApplication.firebaseAuth.getCurrentUser();
        updateUI(MoviesApplication.firebaseUser);
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Carregando");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    @Override
    public void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            textLoginEmail.setText("");
            textLoginPass.setText("");
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void error(String message) {
        Logger.e(message);
        Toast.makeText(LoginActivity.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorFields(boolean email, boolean password) {
        if (email) {
            textLoginEmail.setError("Erro de email");
        }
        if (password) {
            textLoginPass.setError("Error de senha");
        }
    }

    @Override
    public void hideProgress() {
        MovieUtils.hideProgress();
    }

    @Override
    public void showProgress() {
        try {
            MovieUtils.createProgress(getFragmentManager().beginTransaction());
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }
}
