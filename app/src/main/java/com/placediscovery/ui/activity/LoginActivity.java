package com.placediscovery.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.placediscovery.HelperClasses.HelperMethods;
import com.placediscovery.HelperClasses.SessionManager;
import com.placediscovery.MongoLabUser.User;
import com.placediscovery.MongoLabUser.UserQueryBuilder;
import com.placediscovery.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    SessionManager session;
    User user;
    private String result;

    protected EditText _emailText;
    protected EditText _passwordText;
    protected Button _loginButton;
    protected TextView _signupLink;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Session Manager
        session = new SessionManager(getApplicationContext());

        _signupLink = (TextView) findViewById(R.id.link_signup);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        user = new User();
        user.email = _emailText.getText().toString();
        user.password = _passwordText.getText().toString();

        GetUserAsyncTask task = new GetUserAsyncTask();
        task.execute(user);
    }

    public void onLoginSuccess() {
        session.createLoginSession(user.name, user.email, result);  //result is token to be saved
        Intent intent = new Intent(this, HomePageActivity.class);
        Toast.makeText(getApplicationContext(), "Welcome user, You are now logged in.",
                Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed, Please try again!!", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (!HelperMethods.isInternetAvailable(this))
            valid = false;
        return valid;
    }

    private class GetUserAsyncTask extends AsyncTask<User, Void, JSONObject> {
        String server_output = null;
        String temp_output = null;

        @Override
        protected void onPreExecute() {
            // update the UI immediately after the task is executed
            super.onPreExecute();

            progressDialog = ProgressDialog.show(LoginActivity.this, "",
                    "signing in...", false);
        }

        @Override
        protected JSONObject doInBackground(User... arg0) {

            JSONObject jObj = null;
            String json = "";

            try {
                User user = arg0[0];

                UserQueryBuilder qb = new UserQueryBuilder();

                URL url = new URL("http://52.192.70.192:80/login");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setDoInput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Content-Type", "application/json");

                OutputStream os = conn.getOutputStream();
                os.write(qb.createUser(user).getBytes());
                os.flush();
                os.close();

                InputStream is = conn.getInputStream();

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    json = sb.toString();

                } catch (Exception e) {
                    return null;
                }


                try {
                    jObj = new JSONObject(json);
                } catch (JSONException e) {
                    return null;
                }

                return jObj;

            } catch (IOException e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (jsonObject == null) {
                onLoginFailed();
            } else
                try {
                    if (jsonObject.getString("response").equals("SUCCESS")) {
                        result = jsonObject.getString("result");    //this result is to be used as signin token
                        user.setName(jsonObject.getString("name"));
                        onLoginSuccess(); //TODO: have to edit signupsuccess
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

}
