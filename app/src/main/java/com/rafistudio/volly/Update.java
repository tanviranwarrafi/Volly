package com.rafistudio.volly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Update extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private EditText password;
    private Button update;
    private ProgressBar progressBar;

    private String got_email, got_name, got_description, got_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        getSupportActionBar().setTitle("Update");

        name = findViewById(R.id.update_name);
        description = findViewById(R.id.update_description);
        password = findViewById(R.id.update_password);
        update = findViewById(R.id.update_update_btn);
        progressBar = findViewById(R.id.update_progressBar);

        Intent intent = getIntent();
        got_email = intent.getExtras().getString("email");
        got_name = intent.getExtras().getString("name");
        got_description = intent.getExtras().getString("description");
        got_password = intent.getExtras().getString("password");
        Log.d("success", "id: " + got_email);

        name.setText(got_name);
        description.setText(got_description);
        password.setText(got_password);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });
    }

    private void updateUser() {

        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(Update.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.Update,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressBar.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");

                            if (Response.equals("success")) {
                                Log.d("success", Response);
                                Toast.makeText(Update.this, "update successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Update.this, UserDetails.class);
                                intent.putExtra("email", got_email);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Update.this, "update failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Update.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name.getText().toString());
                params.put("description", description.getText().toString());
                params.put("email", got_email);
                params.put("password", password.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
