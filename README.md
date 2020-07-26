# volly

This project is for learning how to use volly library in android app.

### Features:
  - Insert
  - Read
  - Update
  - Delete
  
### Library

```java
dependencies:
  implementation 'com.android.volley:volley:1.1.1'
```

### User

```java
RequestQueue requestQueue = Volley.newRequestQueue(Insert.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Api,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            
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
                    Map<String, String> params = new HashMap<>();

                    return params;
                }
            };
            requestQueue.add(stringRequest);
```

### Screenshots
<img src="screenshots/one.jpg" width="200"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="screenshots/two.jpg" width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="screenshots/three.jpg" width="200">
<img src="screenshots/four.jpg" width="200"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="screenshots/five.jpg" width="200">
