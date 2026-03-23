package fes.jonathan.holamundoimagen;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fes.jonathan.holamundoimagen.models.ClienteModel;
import fes.jonathan.holamundoimagen.models.SesionResponse;
import fes.jonathan.holamundoimagen.services.PizzasService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etApellidos, etCorreo, etContrasenia;
    private Button btnRegistrar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre      = findViewById(R.id.etNombre);
        etApellidos   = findViewById(R.id.etApellidos);
        etCorreo      = findViewById(R.id.etCorreoReg);
        etContrasenia = findViewById(R.id.etContraseniaReg);
        btnRegistrar  = findViewById(R.id.btnRegistrar);
        progressBar   = findViewById(R.id.progressBar);
        TextView tvIrLogin = findViewById(R.id.tvIrLogin);

        btnRegistrar.setOnClickListener(v -> {
            if (validarCampos()) {
                registrar();
            }
        });

        tvIrLogin.setOnClickListener(v -> finish());
    }

    private boolean validarCampos() {
        String nombre      = etNombre.getText().toString().trim();
        String apellidos   = etApellidos.getText().toString().trim();
        String correo      = etCorreo.getText().toString().trim();
        String contrasenia = etContrasenia.getText().toString().trim();
        boolean valido = true;

        // Nombre
        if (nombre.isEmpty()) {
            etNombre.setError("El nombre es obligatorio");
            valido = false;
        } else if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
            etNombre.setError("Solo letras, sin números ni caracteres especiales");
            valido = false;
        } else if (nombre.length() < 2 || nombre.length() > 50) {
            etNombre.setError("Entre 2 y 50 caracteres");
            valido = false;
        } else {
            etNombre.setError(null);
        }

        // Apellidos
        if (apellidos.isEmpty()) {
            etApellidos.setError("Los apellidos son obligatorios");
            valido = false;
        } else if (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
            etApellidos.setError("Solo letras, sin números ni caracteres especiales");
            valido = false;
        } else if (apellidos.length() < 2 || apellidos.length() > 50) {
            etApellidos.setError("Entre 2 y 50 caracteres");
            valido = false;
        } else {
            etApellidos.setError(null);
        }

        // Correo
        if (correo.isEmpty()) {
            etCorreo.setError("El correo es obligatorio");
            valido = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.setError("Correo no válido");
            valido = false;
        } else {
            etCorreo.setError(null);
        }

        // Contraseña
        if (contrasenia.isEmpty()) {
            etContrasenia.setError("La contraseña es obligatoria");
            valido = false;
        } else if (contrasenia.length() < 8) {
            etContrasenia.setError("Mínimo 8 caracteres");
            valido = false;
        } else if (contrasenia.length() > 100) {
            etContrasenia.setError("Máximo 100 caracteres");
            valido = false;
        } else {
            etContrasenia.setError(null);
        }

        return valido;
    }

    private void registrar() {
        String nombre      = etNombre.getText().toString().trim();
        String apellidos   = etApellidos.getText().toString().trim();
        String correo      = etCorreo.getText().toString().trim();
        String contrasenia = etContrasenia.getText().toString().trim();

        String encodedKey = Base64.encodeToString(
                correo.getBytes(), Base64.NO_WRAP
        );

        ClienteModel cliente = new ClienteModel(
                encodedKey, nombre, apellidos, correo, contrasenia
        );

        // Bloquea formulario y muestra ProgressBar
        setFormularioHabilitado(false);

        PizzasService service = ApiClient.getService();
        service.registrarCliente(cliente).enqueue(new Callback<SesionResponse>() {
            @Override
            public void onResponse(Call<SesionResponse> call,
                                   Response<SesionResponse> response) {
                // Desbloquea formulario y oculta ProgressBar
                setFormularioHabilitado(true);

                if (response.code() == 201) {
                    Toast.makeText(RegistroActivity.this,
                            "¡Cuenta creada! Ya puedes iniciar sesión",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else if (response.code() == 200) {
                    etCorreo.setError("Este correo ya está registrado");
                    etCorreo.requestFocus();
                } else {
                    Toast.makeText(RegistroActivity.this,
                            "Error " + response.code() + ", intenta de nuevo",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SesionResponse> call, Throwable t) {
                setFormularioHabilitado(true);
                Toast.makeText(RegistroActivity.this,
                        "Sin conexión, verifica tu internet",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setFormularioHabilitado(boolean habilitado) {
        etNombre.setEnabled(habilitado);
        etApellidos.setEnabled(habilitado);
        etCorreo.setEnabled(habilitado);
        etContrasenia.setEnabled(habilitado);
        btnRegistrar.setEnabled(habilitado);
        progressBar.setVisibility(habilitado ? View.GONE : View.VISIBLE);
    }
}