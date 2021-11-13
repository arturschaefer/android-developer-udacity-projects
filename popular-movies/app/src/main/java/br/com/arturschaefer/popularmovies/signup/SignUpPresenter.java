package br.com.arturschaefer.popularmovies.signup;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.Executor;

import br.com.arturschaefer.popularmovies.application.MoviesApplication;

import static android.support.constraint.Constraints.TAG;

@InjectViewState
public class SignUpPresenter extends MvpPresenter<SignUpView> {
    private static final String LOG_TAG = SignUpPresenter.class.getSimpleName();

    Activity activity;

    public SignUpPresenter() {
    }

    public void signUp(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        getViewState().showProgress();

        // [START create_user_with_email]
        MoviesApplication.firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new Executor() {
                    @Override
                    public void execute(@NonNull Runnable command) {

                    }
                }, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(LOG_TAG, "createUserWithEmail:success");
                            MoviesApplication.firebaseUser = MoviesApplication.firebaseAuth.getCurrentUser();
                            getViewState().updateUI(MoviesApplication.firebaseUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                            getViewState().updateUI(null);
                        }

                        // [START_EXCLUDE]
                        getViewState().hideProgress();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
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
