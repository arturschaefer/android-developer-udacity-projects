package br.com.arturschaefer.popularmovies.forgot_password;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

@InjectViewState
public class ForgotPasswordPresenter extends MvpPresenter<ForgotPasswordView> {
    private static final String LOG_TAG = ForgotPasswordPresenter.class.getSimpleName();

    public ForgotPasswordPresenter() {
    }

    public void forgotPassword(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        getViewState().showProgress();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getViewState().successSendEmail();
                            Log.d(LOG_TAG, "Email sent.");
                        } else {
                            getViewState().errorSendEmail(task.getException().getMessage());
                        }
                    }
                });
    }
}
