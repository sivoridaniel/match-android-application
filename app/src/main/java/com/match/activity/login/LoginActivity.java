package com.match.activity.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.match.R;
import com.match.activity.HomeActivity;
import com.match.activity.register.RegistarAccountActivity;
import com.match.activity.register.RegisterUserActivity;
import com.match.utils.Validator;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.btn_signup)
    TextView _signUpButton;
    @InjectView(R.id.link_forget_password)
    TextView _forgetPassword;

    private ProgressDialog progressDialog;
    private LoginController controller;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        controller = new LoginControllerImpl(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        _signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        _forgetPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });
    }

    private void login() {
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        controller.loginUser(email, password);
    }

    private void signUp() {
        Intent intent = new Intent(LoginActivity.this, RegistarAccountActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    private void forgetPassword() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void setEmailError() {
        this._emailText.setError(getBaseContext().getResources().getString(R.string.error_mail));
    }

    @Override
    public void setPasswordError() {
        this._passwordText.setError(getBaseContext().getResources().getString(R.string.error_password, Validator.MIN_LENGTH_PASSWORD, Validator.MAX_LENGTH_PASSWORD));
    }


    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getBaseContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(LoginActivity.this, "", "Iniciando Sesión...", true, false);
        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void goToNext() {
        Intent intent = null;
        if (controller.userHasSavedInformation()) {
            intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
        }
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
