package com.example.logo.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.logo.R;
import com.example.logo.SignupActivity3;
import com.example.logo.util.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static android.app.Activity.RESULT_OK;


public class ImpostazioniFragment extends AbFrg {
    private ImageView viewImage;
    private android.graphics.Bitmap bitmap;
    EditText name;
    EditText surname;
    EditText cf;
    EditText email;
    EditText password;
    CalendarView birthDateCalendar;
    private RadioGroup radioSexGroup;
    private Button save;
    private TextView dateText;
    private String caregiverEmail;
    private String sex = "";
    RequestQueue requestQueue;

    @Override
    protected View viewOfFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_impostazioni,container,false);

        save = (Button) root.findViewById(R.id.nextSignup2);
        name = (EditText) root.findViewById(R.id.editTextName);
        surname = (EditText) root.findViewById(R.id.editTextSurname);
        cf = (EditText) root.findViewById(R.id.editTextCf);
        email = (EditText) root.findViewById(R.id.editTextEmail);
        password = (EditText) root.findViewById(R.id.editTextPassword);
        viewImage = (ImageView) root.findViewById(R.id.viewImage);
        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        radioSexGroup = (RadioGroup) root.findViewById(R.id.radioSex);
        dateText = root.findViewById(R.id.date_text);

        requestQueue = Volley.newRequestQueue(getContext());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //request =  Volley.newRequestQueue(getActivity().getApplicationContext());
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        // email = preferences.getString("USERNAME", null);
        //apikey = preferences.getString("APIKEY", null);

        setHeader("1234");

    }

    private void setHeader(String codice){

        String URL = "http://www.logopediapp.altervista.org/database/crud_paziente/read_condition.php?condition=codice='" + codice + "'";

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
                                String emailCaregiver = object.getString("caregiver_email");
                                String codiceFiscale = object.getString("cf");
                                String sesso = object.getString("sesso");
                                String data_nascita = object.getString("data_nascita");

                                String immagineProfilo = object.getString("immagine_profilo");
                                String imgDecoded = immagineProfilo.replace("data:image/jpeg;base64,","");
                                viewImage.setImageBitmap(Bitmap.getBitmapFromString(imgDecoded));

                                name.setText(nome);
                                surname.setText(cognome);
                                cf.setText(codiceFiscale);
                                dateText.setText(data_nascita);
                                if(sesso.equalsIgnoreCase("u")){
                                    radioSexGroup.check(R.id.radioButton1);
                                }else if(sesso.equalsIgnoreCase("d")){
                                    radioSexGroup.check(R.id.radioButton2);
                                }

                                email.setText(emailCaregiver);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    private void selectImage() {
        final CharSequence[] options = {"Scatta una Foto", "Scegli dalla Galleria", "Annulla"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Scegli una foto!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Scatta una Foto")) {
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                                1);
                    } else {
                        // Permission has already been granted
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    }

                } else if (options[item].equals("Scegli dalla Galleria")) {
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Permission is not granted
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No explanation needed; request the permission
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    2);

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        // Permission has already been granted
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }

                } else if (options[item].equals("Annulla")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }try {
                    android.graphics.Bitmap bitmapImageCaptured;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmapImageCaptured = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viewImage.setImageBitmap(bitmapImageCaptured);
                    bitmap = bitmapImageCaptured;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContext().getContentResolver().query(selectedImage, filePath, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    android.graphics.Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    viewImage.setImageBitmap(thumbnail);
                    bitmap = thumbnail;
                }
            }
        }
    }

    public android.graphics.Bitmap getResizedBitmap(android.graphics.Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return android.graphics.Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    protected void onResumeReload(View v) {

    }
}
