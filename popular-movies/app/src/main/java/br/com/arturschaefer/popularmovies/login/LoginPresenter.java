package br.com.arturschaefer.popularmovies.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.orhanobut.logger.Logger;

import br.com.arturschaefer.popularmovies.application.MoviesApplication;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {
    private static final String LOG_TAG = LoginPresenter.class.getSimpleName();

    Activity activity;

    public LoginPresenter() {
    }

    public void signIn(String email, String password) {
        Logger.d("signIn:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        getViewState().showProgressDialog();

        // [START sign_in_with_email]
        MoviesApplication.firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Logger.d("signInWithEmail:success");
                            MoviesApplication.firebaseUser = MoviesApplication.firebaseAuth.getCurrentUser();
                            getViewState().updateUI(MoviesApplication.firebaseUser);
                        } else {
                            getViewState().updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            getViewState().error("Erro ao realizar o login");
                        }
                        getViewState().hideProgress();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;
        boolean emailBoolean = true, passwordBoolean = true;

        if (TextUtils.isEmpty(email)) {
            getViewState().error("email");
            valid = false;
            emailBoolean = false;
        }

        if (TextUtils.isEmpty(password)) {
            getViewState().error("password");
            valid = false;
            passwordBoolean = false;
        }

        getViewState().errorFields(emailBoolean, passwordBoolean);
        return valid;
    }
}
