package br.com.arturschaefer.popularmovies.signup;

import com.arellomobile.mvp.MvpView;
import com.google.firebase.auth.FirebaseUser;

public interface SignUpView extends MvpView {
    void showProgressDialog();

    void updateUI(FirebaseUser user);

    void hideProgressDialog();

    void error(String message);

    void errorFields(boolean email, boolean password);

    void hideProgress();

    void showProgress();
}
