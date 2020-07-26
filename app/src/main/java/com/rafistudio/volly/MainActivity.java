package com.rafistudio.volly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    private Button loginBtn;
    private Button showUsersBtn;
    private Button insertUserBtn;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        email = findViewById(R.id.activityMain_email);
        password = findViewById(R.id.activityMain_password);
        loginBtn = findViewById(R.id.activityMain_loginBtn);
        showUsersBtn = findViewById(R.id.activityMain_gotoShow);
        insertUserBtn = findViewById(R.id.activityMain_gotoRegistration);
        progressBar = findViewById(R.id.mainActivity_progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest();
            }
        });

        showUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoShowActivityIntent = new Intent(MainActivity.this, Show.class);
                startActivity(gotoShowActivityIntent);
                finish();
            }
        });

        insertUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegisterActivityIntent = new Intent(MainActivity.this, Insert.class);
                startActivity(gotoRegisterActivityIntent);
                finish();
            }
        });
    }

    private void loginRequest() {

        Drawable customErrorIcon = getResources().getDrawable(R.drawable.error_24);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());

        if (email.getText().toString().equals("") &&
                password.getText().toString().equals("")) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "please enter email & password", Toast.LENGTH_SHORT).show();
        } else {
            if (email.getText().toString().matches(emailPattern)) {
                if (password.length() > 7 && password.length() < 9) {

                    progressBar.setVisibility(View.VISIBLE);
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String response = null;
                    final String finalResponse = response;

                    StringRequest postRequest = new StringRequest(Request.Method.POST, Api.Login,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        progressBar.setVisibility(View.GONE);
                                        JSONObject jsonObject = new JSONObject(response);
                                        String Response = jsonObject.getString("response");

                                        if (Response.equals("success")) {
                                            Toast.makeText(MainActivity.this, "successful login", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(MainActivity.this, UserDetails.class);
                                            intent.putExtra("email", email.getText().toString());
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(MainActivity.this, "invalid email or password", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", email.getText().toString());
                            params.put("password", password.getText().toString());
                            return params;
                        }
                    };
                    postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(postRequest);

                } else {
                    password.setError("password must be 8 character", customErrorIcon);
                }
            } else {
                email.setError("invalid email", customErrorIcon);
            }
        }
    }
}
