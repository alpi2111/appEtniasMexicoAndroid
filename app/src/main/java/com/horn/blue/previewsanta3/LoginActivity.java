package com.horn.blue.previewsanta3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.horn.blue.previewsanta3.entities.userLoginData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private ProgressDialog progress;
    private EditText etxtUserName, etxtPass;
    private Button btnSubmitLogin;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etxtUserName = findViewById(R.id.etxtUserNameLogin);
        etxtPass = findViewById(R.id.etxtPasswordLogin);
        btnSubmitLogin = findViewById(R.id.btnSubmitLogin);

        request = Volley.newRequestQueue(getApplicationContext());

        btnSubmitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etxtUserName.getText().toString();
                String pass = etxtPass.getText().toString();
                if (!userName.isEmpty() && !pass.isEmpty()) {
                    try {
                        loginWithData(userName, pass);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Error de Inicio de Sesión", Toast.LENGTH_SHORT).show();
                        progress.hide();
                    }
                } else {
                    Snackbar.make(v, "Por favor, inserte datos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loginWithData(String userName, String pass) {
        progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("Cargando...");
        progress.show();

        String url = "http://177.238.144.234:8081/proyectoSanta/login.php?nom_usuario=" + userName + "&contra=" + pass;
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    private void guardarIsLogin(String x) {
        SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("isLogin", x);
        editor.commit();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progress.hide();
        Toast.makeText(LoginActivity.this, "No se pudo Iniciar Sesión.Error: " + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progress.hide();

        userLoginData data = new userLoginData();

        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            data.setUser(jsonObject.optString("nom_usuario"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!data.getUser().equals("0")) {
            Toast.makeText(LoginActivity.this, "Inicio de Sesión Correcto", Toast.LENGTH_SHORT).show();
            guardarIsLogin("Yes");
            Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
            startActivity(intent);
        } else {
            progress.hide();
            Toast.makeText(LoginActivity.this, "No se pudo Iniciar Sesión", Toast.LENGTH_SHORT).show();
        }
    }
}
