package com.horn.blue.previewsanta3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText etxtName, etxtLastName, etxtUserName, etxtPass, etxtDate;
    private Button btnSubmitReg;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private ProgressDialog progress;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private String dateBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etxtName = findViewById(R.id.etxtName);
        etxtLastName = findViewById(R.id.etxtLastName);
        etxtUserName = findViewById(R.id.etxtUserName);
        etxtPass = findViewById(R.id.etxtPassword);
        etxtDate = findViewById(R.id.etxtDate);
        btnSubmitReg = findViewById(R.id.btnSubmitRegister);

        request = Volley.newRequestQueue(getApplicationContext());

        etxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etxtDate.setText(dayOfMonth + "-" + month + "-" + year);
                dateBD = year + "-" + month + "-" + dayOfMonth;
            }
        };

        btnSubmitReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etxtName.getText().toString();
                String lastName = etxtLastName.getText().toString();
                String userName = etxtUserName.getText().toString();
                String pass = etxtPass.getText().toString();
                String date = etxtDate.getText().toString();
                if (!name.isEmpty() && !lastName.isEmpty() && !userName.isEmpty() && !pass.isEmpty() && !date.isEmpty()) {
                    saveUserData(name, lastName, userName, pass, dateBD);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void saveUserData(String name, String lastName, String userName, String pass, String date) {
        progress = new ProgressDialog(RegisterActivity.this);
        progress.setMessage("Cargando...");
        progress.show();

        String url = "http://177.238.144.234:8081/proyectoSanta/registrar_usuario.php?nom_usuario=" + userName + "&nombre=" + name + "&apellido=" + lastName + "&fecha_nac=" + date + "&contra=" + pass + "&es_admin=No";
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(RegisterActivity.this, "Registro Correcto", Toast.LENGTH_SHORT).show();
        progress.hide();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        etxtName.setText("");
        etxtLastName.setText("");
        etxtUserName.setText("");
        etxtPass.setText("");
        etxtDate.setText("");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progress.hide();
        Toast.makeText(RegisterActivity.this, "No se pudo Registrar.Error: " + error, Toast.LENGTH_SHORT).show();
    }

}
