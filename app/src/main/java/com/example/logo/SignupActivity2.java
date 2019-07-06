package com.example.logo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class SignupActivity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String doctorCode;
    String radioSelected;

    EditText name;
    EditText surname;
    EditText cf;
    CalendarView birthDateCalendar;
    private RadioGroup radioSexGroup;
    private Button next;
    private TextView dateText;
    private String caregiverEmail;
    private String sex = "";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        next = (Button) findViewById(R.id.nextSignup2);
        name = (EditText) findViewById(R.id.editTextName);
        surname = (EditText) findViewById(R.id.editTextSurname);
        cf = (EditText) findViewById(R.id.editTextCf);
        dateText = findViewById(R.id.date_text);
        requestQueue = Volley.newRequestQueue(this);


        findViewById(R.id.show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Intent signupIntentOne = getIntent();
        Bundle signupBundleOne = signupIntentOne.getExtras();
        if(!signupBundleOne.isEmpty()){
            doctorCode = signupBundleOne.getString("doctorcode");

        }
        patientData(doctorCode);


        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        radioSexGroup.clearCheck();

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    radioSelected = rb.getText().toString();
                    sex = " ";
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllaCampi();
            }
        });

    }

    private void controllaCampi() {
        String nomePaziente = name.getText().toString();
        String cognomePaziente = surname.getText().toString();
        String cfPaziente = cf.getText().toString();
        String dataNascitaPaziente = dateText.getText().toString();
        String sessoPaziente = sex;
        if(nomePaziente.equalsIgnoreCase("") || cognomePaziente.equalsIgnoreCase("") ||
                cfPaziente.equalsIgnoreCase("") || dataNascitaPaziente.equalsIgnoreCase("") ||
                sessoPaziente.equalsIgnoreCase("") ){
            Snackbar.make(findViewById(android.R.id.content), "Completare correttamente tutti i campi!", Snackbar.LENGTH_LONG).show();
        } else{
            procedi();
        }
    }

    private void patientData(String codiceMedico){
        String URL = "http://www.logopediapp.altervista.org/database/crud_paziente/read_condition.php?condition=codice='" +codiceMedico+"'";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Initialize a new JsonArrayRequest instance
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{

                            JSONArray jsonArray = response.getJSONArray("records");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String nome = object.getString("nome");
                                String cognome = object.getString("cognome");
                                caregiverEmail = object.getString("caregiver_email");
                                name.setText(nome);
                                surname.setText(cognome);

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred

                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }


    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month= month+1;
        String date = dayOfMonth + "/" + month + "/" + year;
        dateText.setText(date);
    }


    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        radioSexGroup.clearCheck();
    }

    public void onSubmit(View v) {
        RadioButton rb = (RadioButton) radioSexGroup.findViewById(radioSexGroup.getCheckedRadioButtonId());
        Toast.makeText(SignupActivity2.this, rb.getText(), Toast.LENGTH_SHORT).show();
    }

    private void procedi() {
        Intent intentProcedi = new Intent(getApplicationContext(), SignupActivity3.class);
        Bundle bundle = new Bundle();
        bundle.putString("codice", doctorCode);
        bundle.putString("nome", name.getText().toString());
        bundle.putString("cognome", surname.getText().toString());
        bundle.putString("cf", cf.getText().toString());
        bundle.putString("sesso", radioSelected);
        bundle.putString("data_nascita", dateText.getText().toString());
        bundle.putString("caregiver_email", caregiverEmail);
        intentProcedi.putExtras(bundle);
        startActivity(intentProcedi);

    }
}