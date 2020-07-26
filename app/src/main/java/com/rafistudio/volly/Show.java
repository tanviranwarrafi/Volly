package com.rafistudio.volly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<UserModel> userModelList = new ArrayList<>();

    private UserAdapter userAdapter;
    private RequestQueue mRequestQueue;
    private JSONArray jsonArray = null;
    public static final String JSON_ARRAY = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        getSupportActionBar().setTitle("Showing Users");
        getSupportActionBar().setElevation(0);

        recyclerView = findViewById(R.id.show_recyclerview);
        progressBar = findViewById(R.id.show_progressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        retriveUsers();

        UserAdapter userAdapter = new UserAdapter(userModelList, true);
        recyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        retriveUsers();
    }

    private void retriveUsers() {

        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.Show,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        JSONObject jsonObject = null;
                        userModelList.clear();
                        try {
                            jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray(JSON_ARRAY);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(Show.this, "no data recorded", Toast.LENGTH_SHORT).show();
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    UserModel userModel = new UserModel();
                                    userModel.setName(object.getString("name"));
                                    userModel.setDescription(object.getString("description"));
                                    userModel.setEmail(object.getString("email"));
                                    userModel.setPassword(object.getString("password"));

                                    userModelList.add(userModel);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        UserAdapter userAdapter = new UserAdapter(userModelList, true);
                        recyclerView.setAdapter(userAdapter);
                        userAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        mRequestQueue = Volley.newRequestQueue(Show.this);
        mRequestQueue.add(stringRequest);
    }
}
