package com.quizapp.quizbox.Login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.quizapp.quizbox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText r_name, r_mobileno, r_password, r_conpassword;
    ImageButton r_register;
    String name, mobileno, password, conpassword;
    AlertDialog.Builder builder;
    ProgressDialog pDialog;
    String url = "http://apptesting.parivesa.com/quiz/register.php";
    //String url="https://quizapp1234.000webhostapp.com/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        pDialog = new ProgressDialog(Register.this);
        // Showing  dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Connecting To Server");
        pDialog.setCancelable(false);


        r_name = (EditText) findViewById(R.id.name);

        r_mobileno = (EditText) findViewById(R.id.mobileno);
        r_password = (EditText) findViewById(R.id.password);
        r_conpassword = (EditText) findViewById(R.id.conpassword);
        r_register = (ImageButton) findViewById(R.id.button);
        builder = new AlertDialog.Builder(Register.this);


        r_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

                if (name.equals("") || mobileno.equals("") || password.equals("") || conpassword.equals("")) {
                    builder.setTitle("Something went wrong...");
                    builder.setMessage("please fill all the fields...");
                    displayAlert("input error");
                } else {
                    if (!(password.equals(conpassword))) {
                        builder.setTitle("Something went wrong...");
                        builder.setMessage("Your password are not matching...");
                        displayAlert("input error");

                    } else {

                        pDialog.show();
                        checkInternetConenction();
                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    pDialog.dismiss();
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");
                                    String message = jsonObject.getString("message");
                                    builder.setTitle("Server response..");
                                    builder.setMessage(message);
                                    displayAlert(code);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();

                                params.put("name", name);
                                params.put("mobileno", mobileno);
                                params.put("password", password);
                                return params;
                            }
                        };

                        Singleton.getmInstance(Register.this).addToRequestque(stringRequest);

                    }


                }


            }
        });

    }

    public void displayAlert(final String code) {
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("input error")) {
                    r_password.setText("");
                    r_conpassword.setText("");
                } else if (code.equals("registration done")) {
                    finish();
                } else if (code.equals("registration failed")) {
                    r_name.setText("");
                    r_mobileno.setText("");
                    r_password.setText("");
                    r_conpassword.setText("");

                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void getData() {
        name = r_name.getText().toString().trim();

        mobileno = r_mobileno.getText().toString().trim();
        if (!isValidMobile(mobileno)) {
            r_mobileno.setError("Invalid Mobile Number");
            mobileno = "";
        }
        password = r_password.getText().toString().trim();
        if (!isValidPassword(password)) {
            r_password.setError("Invalid Password");
            password = "";
        }
        conpassword = r_conpassword.getText().toString().trim();

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