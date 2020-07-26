package com.rafistudio.volly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class Insert extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText description;
    private Button insert;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        getSupportActionBar().setTitle("Insert");
        getSupportActionBar().hide();

        name = findViewById(R.id.insert_name);
        email = findViewById(R.id.insert_email);
        password = findViewById(R.id.insert_password);
        description = findViewById(R.id.insert_description);
        insert = findViewById(R.id.insert_insert_btn);
        progressBar = findViewById(R.id.insert_progressBar);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();
            }
        });
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(name.getText())) {
            if (!TextUtils.isEmpty(email.getText())) {
                if (!TextUtils.isEmpty(password.getText())) {
                    if (!TextUtils.isEmpty(description.getText())) {
                        insert.setEnabled(true);
                    } else {
                        insert.setEnabled(false);
                    }
                } else {
                    insert.setEnabled(false);
                }
            } else {
                insert.setEnabled(false);
            }
        }
    }

    private void insertUser() {

        Drawable customErrorIcon = getResources().getDrawable(R.drawable.error_24);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());

        if (email.getText().toString().matches(emailPattern)) {
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(Insert.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.Insert,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressBar.setVisibility(View.GONE);
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");

                                if (Response.equals("registered")) {
                                    Toast.makeText(Insert.this, "this email already inserted", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (Response.equals("success")) {
                                        Toast.makeText(Insert.this, "insert successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Insert.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Insert.this, "insert failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Insert.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("name", name.getText().toString());
                    params.put("description", description.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            email.setError("invalid email", customErrorIcon);
        }
    }
}
