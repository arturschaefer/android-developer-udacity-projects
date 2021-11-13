package br.com.arturschaefer.popularmovies.forgot_password;

import com.arellomobile.mvp.MvpView;

interface ForgotPasswordView extends MvpView {
    void showProgress();

    void hideProgress();

    void successSendEmail();

    void errorSendEmail(String s);
}
