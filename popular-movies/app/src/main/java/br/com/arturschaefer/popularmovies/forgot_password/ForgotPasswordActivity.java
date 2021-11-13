package br.com.arturschaefer.popularmovies.forgot_password;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import br.com.arturschaefer.popularmovies.R;
import br.com.arturschaefer.popularmovies.utilities.MovieUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends MvpActivity implements ForgotPasswordView {
    @InjectPresenter
    ForgotPasswordPresenter presenter;

    @BindView(R.id.toolbar_settings)
    Toolbar toolbarSettings;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.image_logo)
    ImageView imageLogo;
    @BindView(R.id.text_forgot_email)
    TextInputEditText textForgotEmail;
    @BindView(R.id.text_forgot_email_layout)
    TextInputLayout textForgotEmailLayout;
    @BindView(R.id.button_forgot_password)
    Button buttonForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);


        Picasso.get().load(R.drawable.movie_camera)
                .placeholder(R.drawable.movie_camera).into(imageLogo);

        buttonForgotPassword.setEnabled(false);
        buttonForgotPassword.setAlpha((float) 0.3);
        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textForgotEmail.getText() != null) {
                    presenter.forgotPassword(textForgotEmail.getText().toString());
                }
            }
        });

        textForgotEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains("@")) {
                    buttonForgotPassword.setEnabled(true);
                    buttonForgotPassword.setAlpha((float) 1.0);
                } else {
                    buttonForgotPassword.setEnabled(false);
                    buttonForgotPassword.setAlpha((float) 0.3);
                }
            }
        });

        toolbarSettings.setTitle("Forgot Password");
        try {
            toolbarSettings.setNavigationIcon(R.drawable.ic_backward);
            toolbarSettings.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            toolbarSettings.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    @Override
    public void showProgress() {
        try {
            MovieUtils.createProgress(getFragmentManager().beginTransaction());
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    @Override
    public void hideProgress() {
        MovieUtils.hideProgress();
    }

    @Override
    public void successSendEmail() {
        hideProgress();
        Toast.makeText(this, "Please, check your email.", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorSendEmail(String s) {
        hideProgress();
        Snackbar snackbar = Snackbar.make(findViewById(R.id.constraint_forgot_password), s,
                Snackbar.LENGTH_LONG);
        snackbar.setAction("Try again", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textForgotEmail.getText() != null)
                    presenter.forgotPassword(textForgotEmail.getText().toString());
            }
        });
        snackbar.show();
    }
}
