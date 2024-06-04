package com.example.salus;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PerfilActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final String BASE_URL = "http://192.168.1.123:8000/api/v1/";
    private ShapeableImageView imagenFoto;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextDireccion;
    private EditText editTextCiudad;
    private EditText editTextTelefono;
    private EditText editTextCorreo;
    private EditText editTextUsuario;
    private EditText editTextClave;
    private EditText editTextDescripcion;
    private EditText editTextEstado;
    private Spinner spCondicionPerfil;
    private Switch swEditarPerfil;
    private ScrollView scrollView2;
    private Button btnActualizarPerfil;
    private Button btnCargarImagen;
    private ImageView imageView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar los componentes
        imagenFoto = findViewById(R.id.imagenFoto);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        editTextCiudad = findViewById(R.id.editTextCiudad);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextUsuario = findViewById(R.id.editTextTextUsuario);
        editTextClave = findViewById(R.id.editTextTextClave);
        editTextDescripcion = findViewById(R.id.editTextTextDescripcion);
        editTextEstado = findViewById(R.id.editTextEstado);
        spCondicionPerfil = findViewById(R.id.spCondicion_perfil);
        swEditarPerfil = findViewById(R.id.swEditar_perfil);
        scrollView2 = findViewById(R.id.scrollView2);
        btnActualizarPerfil = findViewById(R.id.btnActualizarPerfil);
        btnCargarImagen = findViewById(R.id.btnCargarImagen);
        imageView9 = findViewById(R.id.imageView9);

        // Set listeners
        btnCargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermissionAndTakePhoto();
            }
        });

        btnActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPerfil();
            }
        });

        swEditarPerfil.setOnCheckedChangeListener((buttonView, isChecked) -> {
            habilitarEdicion(isChecked);
        });

        habilitarEdicion(swEditarPerfil.isChecked());
    }

    private void checkCameraPermissionAndTakePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            tomarFoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tomarFoto();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagenFoto.setImageBitmap(imageBitmap);
        }
    }

    private void actualizarPerfil() {
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String direccion = editTextDireccion.getText().toString();
        String ciudad = editTextCiudad.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String correo = editTextCorreo.getText().toString();
        String usuario = editTextUsuario.getText().toString();
        String clave = editTextClave.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String estado = editTextEstado.getText().toString();

        JSONObject perfil = new JSONObject();
        try {
            perfil.put("nombre", nombre);
            perfil.put("apellido", apellido);
            perfil.put("direccion", direccion);
            perfil.put("ciudad", ciudad);
            perfil.put("telefono", telefono);
            perfil.put("correo", correo);
            perfil.put("usuario", usuario);
            perfil.put("clave", clave);
            perfil.put("descripcion", descripcion);
            perfil.put("estado", estado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new ActualizarPerfilTask().execute(perfil.toString());
    }

    private void habilitarEdicion(boolean habilitar) {
        editTextNombre.setEnabled(habilitar);
        editTextApellido.setEnabled(habilitar);
        editTextDireccion.setEnabled(habilitar);
        editTextCiudad.setEnabled(habilitar);
        editTextTelefono.setEnabled(habilitar);
        editTextCorreo.setEnabled(habilitar);
        editTextUsuario.setEnabled(habilitar);
        editTextClave.setEnabled(habilitar);
        editTextDescripcion.setEnabled(habilitar);
        editTextEstado.setEnabled(habilitar);
        spCondicionPerfil.setEnabled(habilitar);
        btnActualizarPerfil.setEnabled(habilitar);
    }

    private class ActualizarPerfilTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String perfilJson = params[0];
            try {
                URL url = new URL(BASE_URL + "updateProfile");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = perfilJson.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(PerfilActivity.this, "Perfil actualizado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PerfilActivity.this, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
            }
        }}};