package br.liveo.ndrawer.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.liveo.ndrawer.HelperMethods;
import br.liveo.ndrawer.MongoLabUser.GetUserAsyncTask;
import br.liveo.ndrawer.MongoLabUser.User;
import br.liveo.ndrawer.MongoLabUser.UserStatus;
import br.liveo.ndrawer.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//import butterknife.ButterKnife;
//import butterknife.InjectView;

public class
        LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    protected EditText _emailText;
    protected EditText _passwordText;
    protected Button _loginButton;
    protected TextView _signupLink;

    protected User loggedInUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ButterKnife.inject(this);
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

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        ArrayList<User> returnValues = new ArrayList<User>();

        GetUserAsyncTask task = new GetUserAsyncTask();
        try {
            returnValues = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(User x: returnValues){

            if(x.email.equals(email) && x.password.equals(password)) {

                Toast.makeText(getApplicationContext(), "Welcome" + x.name, Toast.LENGTH_LONG);

                //Toast.makeText(getApplicationContext(), "Welcome" + x.name, Toast.LENGTH_LONG);

                loggedInUser = x;
                UserStatus userStatus = new UserStatus();
                userStatus.SetStatus(true);
                userStatus.SetUser_Id(x.user_id);
                userStatus.SetName(x.name);
                userStatus.SetEmail(x.email);
                userStatus.SetPassword(x.password);
                Intent moreDetailsIntent = new Intent(LoginActivity.this, HomePage.class);
                Toast.makeText(getApplicationContext(),"You done Successfully",Toast.LENGTH_SHORT).show();
                startActivity(moreDetailsIntent);
                break;
            }
          //  Toast.makeText(getApplicationContext(),"Incorrent Username or Password",Toast.LENGTH_SHORT).show();
        }

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        progressDialog.dismiss();
                        onLoginFailed();
                    }
                }, 3000);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
              //  Toast.makeText(getApplicationContext(), "Welcome" + loggedInUser.name, Toast.LENGTH_LONG).show();
                // By default we just finish the Activity and log them in automatically
                //this.finish();
            }
        }
    }



    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

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



        if(!HelperMethods.isInternetAvailable(this))
            valid = false;
        return valid;
    }


}
