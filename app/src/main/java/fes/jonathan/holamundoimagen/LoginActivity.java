package fes.jonathan.holamundoimagen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fes.jonathan.holamundoimagen.models.SesionResponse;
import fes.jonathan.holamundoimagen.services.PizzasService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, etContrasenia;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Si ya hay sesión activa, salta directo al inicio
        if (new SesionManager(this).haySesion()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        etCorreo      = findViewById(R.id.etCorreo);
        etContrasenia = findViewById(R.id.etContrasenia);
        btnLogin      = findViewById(R.id.btnLogin);
        progressBar   = findViewById(R.id.progressBar);
        TextView tvIrRegistro = findViewById(R.id.tvIrRegistro);

        btnLogin.setOnClickListener(v -> {
            if (validarCampos()) {
                iniciarSesion();
            }
        });

        tvIrRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class))
        );
    }

    private boolean validarCampos() {
        String correo      = etCorreo.getText().toString().trim();
        String contrasenia = etContrasenia.getText().toString().trim();
        boolean valido = true;

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
        } else if (contrasenia.length() < 2) {
            etContrasenia.setError("Mínimo 8 caracteres");
            valido = false;
        } else {
            etContrasenia.setError(null);
        }

        return valido;
    }

    private void iniciarSesion() {
        String correo      = etCorreo.getText().toString().trim();
        String contrasenia = etContrasenia.getText().toString().trim();

        // Basic Auth: Base64(correo:contrasenia)
        String credenciales = correo + ":" + contrasenia;
        String basicAuth = "Basic " + Base64.encodeToString(
                credenciales.getBytes(), Base64.NO_WRAP
        );

        // Bloquea formulario y muestra ProgressBar
        setFormularioHabilitado(false);

        PizzasService service = ApiClient.getService();
        service.iniciarSesion(basicAuth).enqueue(new Callback<SesionResponse>() {
            @Override
            public void onResponse(Call<SesionResponse> call,
                                   Response<SesionResponse> response) {
                setFormularioHabilitado(true);

                if (response.isSuccessful() && response.body() != null) {
                    SesionResponse body = response.body();

                    // Guarda token y datos de sesión
                    new SesionManager(LoginActivity.this).guardarSesion(
                            body.getToken(),
                            body.getExpiracion(),
                            correo
                    );

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                } else if (response.code() == 401) {
                    etContrasenia.setError("Contraseña incorrecta");
                    etContrasenia.requestFocus();
                } else if (response.code() == 404) {
                    etCorreo.setError("Correo no registrado");
                    etCorreo.requestFocus();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Error " + response.code() + ", intenta de nuevo",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SesionResponse> call, Throwable t) {
                setFormularioHabilitado(true);
                Toast.makeText(LoginActivity.this,
                        "Sin conexión, verifica tu internet",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setFormularioHabilitado(boolean habilitado) {
        etCorreo.setEnabled(habilitado);
        etContrasenia.setEnabled(habilitado);
        btnLogin.setEnabled(habilitado);
        progressBar.setVisibility(habilitado ? View.GONE : View.VISIBLE);
    }
}