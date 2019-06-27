package com.example.logo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.example.logo.util.Bitmap.getStringFromBitmap;
import static com.example.logo.util.TrimMessage.trimMessage;

public class SignupActivity3 extends AppCompatActivity {
    private ImageView viewImage;
    private Bitmap bitmap;
    private Button send;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private RelativeLayout layout;

    RequestQueue requestQueue;

    private String codice;
    private String nome;
    private String cognome;
    private String sesso;
    private String data;
    private String cf;
    private String immagine;
    private String caregiverEmail;
    private String formattedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);
        layout = (RelativeLayout) findViewById(R.id.relativelayout);
        send = (Button) findViewById(R.id.nextSignup3);
        viewImage = (ImageView) findViewById(R.id.viewImage);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        requestQueue = Volley.newRequestQueue(this);

        Intent signupIntentTwo = getIntent();
        Bundle signupBundleTwo = signupIntentTwo.getExtras();
        if(!signupBundleTwo.isEmpty()){
            nome = signupBundleTwo.getString("nome");
            cognome = signupBundleTwo.getString("cognome");
            cf = signupBundleTwo.getString("cf");
            data = signupBundleTwo.getString("data_nascita");
            codice = signupBundleTwo.getString("codice");
            String currentString = data;
            String[] separated = currentString.split("/");
            String day = separated[0];
            String month = separated[1];
            String year = separated[2];

            if(day.length()==1){
                day = "0"+day;
            }
            if(month.length()==1){
                month = "0"+month;
            }

            formattedData = year+"-"+month+"-"+day;

            sesso = signupBundleTwo.getString("sesso");
            if(sesso.equalsIgnoreCase("M")){
                sesso = "u";
            }else{
                sesso = "d";
            }
            codice = signupBundleTwo.getString("codice");
            caregiverEmail = signupBundleTwo.getString("caregiver_email");
        }

        editTextEmail.setText(caregiverEmail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextEmail.getText().toString().isEmpty()||editTextPassword.getText().toString().isEmpty()) {
                    String text = "Completare i campi e selezionare una foto";
                    Snackbar.make(layout, text, Snackbar.LENGTH_LONG).show();
                } else {

                    Bitmap resizedBitmap = getResizedBitmap(bitmap, 100);
                    immagine = getStringFromBitmap(resizedBitmap);
                    registrazione_paziente();
                    registrazione_caregiver();


                }
            }

        });
    }


    public void registrazione_caregiver() {
        String urlCaregiver = "http://logopediapp.altervista.org/database/crud_caregiver/update.php";

        StringRequest caregiver = new StringRequest(Request.Method.POST, urlCaregiver,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("non entrato");
                        NetworkResponse errore = error.networkResponse;
                        String json = new String(errore.data);
                        String message = trimMessage(json, "message");
                        //Snackbar.make(rewards,message,Snackbar.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", editTextEmail.getText().toString());
                params.put("password", editTextPassword.getText().toString());
                return params;
            }
        };

        requestQueue.add(caregiver);
    }

    public void registrazione_paziente() {
        String urlPaziente = "http://logopediapp.altervista.org/database/crud_paziente/update.php";

        StringRequest paziente = new StringRequest(Request.Method.POST, urlPaziente,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse errore = error.networkResponse;
                        String json = new String(errore.data);
                        String message = trimMessage(json, "message");
                        //Snackbar.make(rewards,message,Snackbar.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("codice", codice);
                params.put("cf", cf);
                params.put("nome", nome);
                params.put("cognome", cognome);
                params.put("sesso", sesso); // u d
                params.put("data_nascita", formattedData); //1981-01-16
                params.put("immagine_profilo", immagine);

                return params;
            }
        };

        requestQueue.add(paziente);
    }

    private void selectImage() {
        final CharSequence[] options = {"Scatta una Foto", "Scegli dalla Galleria", "Annulla"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity3.this);
        builder.setTitle("Scegli una foto!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Scatta una Foto")) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(SignupActivity3.this,
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
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Permission is not granted
                        if (ActivityCompat.shouldShowRequestPermissionRationale(SignupActivity3.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No explanation needed; request the permission
                            ActivityCompat.requestPermissions(SignupActivity3.this,
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Bitmap bitmapImageCaptured;
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
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    viewImage.setImageBitmap(thumbnail);
                    bitmap = thumbnail;
                }
            }



        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
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
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onBackPressed() {

    }


}
