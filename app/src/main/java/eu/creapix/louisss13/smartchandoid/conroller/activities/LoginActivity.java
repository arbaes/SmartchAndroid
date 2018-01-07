package eu.creapix.louisss13.smartchandoid.conroller.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.UsersDao;
import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;
import eu.creapix.louisss13.smartchandoid.utils.Utils;


public class LoginActivity extends AppCompatActivity implements WebserviceListener {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.account_incorrect).setVisibility(View.GONE);
                if (Utils.hasConnexion(getApplicationContext())) {
                    attemptLogin();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegistration();
            }


        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        checkIntent(getIntent());
    }

    @Override
    public void onResume(){
        super.onResume();
        findViewById(R.id.account_incorrect).setVisibility(View.GONE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        findViewById(R.id.account_incorrect).setVisibility(View.GONE);
        checkIntent(intent);
    }

    private void checkIntent(Intent intent) {
        if (intent.hasExtra(Constants.LOGIN))
            mEmailView.setText(intent.getStringExtra(Constants.LOGIN));

        if (intent.hasExtra(Constants.PWD))
            mPasswordView.setText(intent.getStringExtra(Constants.PWD));
    }

    public void goToRegistration() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToMonitoredMatches() {
        Intent intent = new Intent(this, MonitoredMatchesActivity.class);
        startActivity(intent);
        finish();
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password_too_short_part1));
            focusView = mPasswordView;
            cancel = true;
        } else {
            boolean[] pwdValidity = Utils.PasswordValidity(password);
            if (!(pwdValidity[Constants.HAS_UPPER_CASE])){
                mPasswordView.setError(getString(R.string.error_invalid_password_no_uppercase));
                focusView = mPasswordView;
                cancel = true;
            } else if (!(pwdValidity[Constants.HAS_LOWER_CASE])) {
                mPasswordView.setError(getString(R.string.error_invalid_password_no_lowercase));
                focusView = mPasswordView;
                cancel = true;
            } else if (!(pwdValidity[Constants.HAS_DIGIT])) {
                mPasswordView.setError(getString(R.string.error_invalid_password_no_digit));
                focusView = mPasswordView;
                cancel = true;
            } else if (!(pwdValidity[Constants.HAS_SPECIAL_CHAR])) {
                    mPasswordView.setError(getString(R.string.error_invalid_password_no_special));
                    focusView = mPasswordView;
                    cancel = true;
            } else if (!(pwdValidity[Constants.HAS_ENOUGH_UNIQUE_CHAR])) {
                mPasswordView.setError(getString(R.string.error_invalid_password_not_enough_unique_part1) +
                        " " + String.valueOf(Constants.MIN_UNIQUE_CHAR_REQUIRED + " " +
                        getString(R.string.error_invalid_password_not_enough_unique_part2)));
                focusView = mPasswordView;
                cancel = true;
            }
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);

        }
    }

    private boolean isEmailValid(String email) {

        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > Constants.MIN_CHAR_REQUIRED;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onWebserviceFinishWithSuccess(String method, Integer id, ArrayList<Object> datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PreferencesUtils.saveEmail(getApplicationContext(), mEmailView.getText().toString());
                goToMonitoredMatches();
                finish();
            }
        });
    }

    @Override
    public void onWebserviceFinishWithError(String error, final int errorCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView errorField = findViewById(R.id.account_incorrect);

                switch (errorCode) {
                    case 400:
                        mEmailView.setError(getString(R.string.registered_already));
                        mEmailView.requestFocus();
                        break;
                    default:
                        errorField.setVisibility(View.VISIBLE);
                        errorField.setText(R.string.server_error_content);
                }
            }
        });
    }


    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private UsersDao userDao;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            userDao = new UsersDao();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                return userDao.login(LoginActivity.this, getApplicationContext(), mEmail, mPassword);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();

            } else {
                findViewById(R.id.account_incorrect).setVisibility(View.VISIBLE);
                mPasswordView.requestFocus();
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

