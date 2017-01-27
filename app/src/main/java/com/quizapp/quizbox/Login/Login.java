package com.quizapp.quizbox.Login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.quizapp.quizbox.MainActivity;
import com.quizapp.quizbox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    ImageButton login, register;
    EditText login_mobileno, login_password;
    String mobilenumber;
    String mobileno, password;
    AlertDialog.Builder builder;
    //String login_url="https://quizapp1234.000webhostapp.com/login.php";
    String login_url = "http://apptesting.parivesa.com/quiz/login.php";
    SessionManager sessionManager;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        pDialog = new ProgressDialog(Login.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Connecting To Server");
        pDialog.setCancelable(false);


        register = (ImageButton) findViewById(R.id.registerbutton);
        login = (ImageButton) findViewById(R.id.loginbutton);
        login_mobileno = (EditText) findViewById(R.id.mobileno);

        login_password = (EditText) findViewById(R.id.password);

        builder = new AlertDialog.Builder(Login.this);
        sessionManager = new SessionManager(Login.this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInternetConenction();
                startActivity(new Intent(Login.this, Register.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getData();

                mobilenumber = mobileno;


                if (mobileno.equals("") || password.equals("")) {
                    builder.setTitle("Something went wrong...");
                    displayAlert("Enter a valid username and password...");

                } else {

                    pDialog.show();
                    checkInternetConenction();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                pDialog.dismiss();
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                if (code.equals("login failed")) {

                                    builder.setTitle("Login Error..");
                                    displayAlert(jsonObject.getString("message"));
                                } else {
                                    //Intent intent=new Intent(MainActivity_Login.this,Home.class);
                                    String name = jsonObject.getString("name");
                                    //String mobileno=jsonObject.getString("mobileno");
                                    //String mobilenumber=jsonObject.getString("user_name");
                                    //Toast.makeText(getApplicationContext(),mobilenumber,Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                                    sessionManager.createLoginSession(name, mobilenumber);
                                    //Bundle bundle=new Bundle();
                                    //bundle.putString("name",jsonObject.getString("name"));
                                    //bundle.putString("email",jsonObject.getString("email"));
                                    //intent.putExtras(bundle);

                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(MainActivity_Login.this,"Error",Toast.LENGTH_SHORT).show();
                            error.printStackTrace();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("mobileno", mobileno);
                            params.put("password", password);

                            return params;
                        }
                    };
                    Singleton.getmInstance(Login.this).addToRequestque(stringRequest);
                }

            }
        });

    }

    public void displayAlert(String message) {

        builder.setMessage(message);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                login_mobileno.setText("");
                login_password.setText("");

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void getData() {
        mobileno = login_mobileno.getText().toString();

        if (!isValidMobile(mobileno)) {
            login_mobileno.setError("Invalid Mobile Number");
            mobileno = "";
        }
        password = login_password.getText().toString();

        if (!isValidPassword(password)) {
            login_password.setError("Invalid Password");
            password = "";
        }
        Toast.makeText(getApplicationContext(), mobileno, Toast.LENGTH_LONG);
        Toast.makeText(getApplicationContext(), password, Toast.LENGTH_LONG);

    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 5) {
            return true;
        }
        return false;
    }

    // validating mobile number
    private boolean isValidMobile(String mobilenumber) {
        if (mobilenumber != null && mobilenumber.length() > 9) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(this, " NO INTERNET CONNECTION, RECONNECT AND TRY AGAIN ", Toast.LENGTH_LONG).show();
            pDialog.dismiss();

            return false;
        }
        return false;
    }
}
