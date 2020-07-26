package com.rafistudio.volly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserDetails extends AppCompatActivity {

    private TextView name;
    private TextView description;
    private TextView email;
    private TextView password;
    private Button update;
    private Button delete;
    private ProgressBar progressBar;

    private String got_email;
    private JSONArray jsonArray = null;
    public static final String JSON_ARRAY = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        getSupportActionBar().setTitle("User Details");

        name = findViewById(R.id.userDetails_name);
        description = findViewById(R.id.userDetails_description);
        email = findViewById(R.id.userDetails_email);
        password = findViewById(R.id.userDetails_password);
        update = findViewById(R.id.userDetails_updateBtn);
        delete = findViewById(R.id.userDetails_deleteBtn);
        progressBar = findViewById(R.id.userDetails_progressBar);

        Intent intent = getIntent();
        got_email = intent.getExtras().getString("email");

        loadUserDetails();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });
    }

    private void loadUserDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.Show,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray(JSON_ARRAY);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(UserDetails.this, "no data recorded", Toast.LENGTH_SHORT).show();
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    UserModel userModel = new UserModel();
                                    userModel.setName(object.getString("name"));
                                    userModel.setDescription(object.getString("description"));
                                    userModel.setEmail(object.getString("email"));
                                    userModel.setPassword(object.getString("password"));

                                    name.setText(userModel.getName());
                                    description.setText(userModel.getDescription());
                                    email.setText(userModel.getEmail());
                                    password.setText(userModel.getPassword());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserDetails.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", got_email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UserDetails.this);
        requestQueue.add(stringRequest);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent(UserDetails.this, Update.class);
                updateIntent.putExtra("name", name.getText().toString());
                updateIntent.putExtra("description", description.getText().toString());
                updateIntent.putExtra("email", got_email);
                updateIntent.putExtra("password", password.getText().toString());
                startActivity(updateIntent);
            }
        });
    }

    private void deleteUser() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(UserDetails.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.Delete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressBar.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");

                            if (Response.equals("success")) {
                                Log.d("success", Response);
                                Toast.makeText(UserDetails.this, "delete successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserDetails.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(UserDetails.this, "delete failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UserDetails.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", got_email);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
