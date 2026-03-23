package fes.jonathan.holamundoimagen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fes.jonathan.holamundoimagen.models.SesionResponse;
import fes.jonathan.holamundoimagen.services.PizzasService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etCorreo      = findViewById(R.id.etCorreo);
        EditText etContrasenia = findViewById(R.id.etContrasenia);
        Button   btnLogin      = findViewById(R.id.btnLogin);
        TextView tvIrRegistro  = findViewById(R.id.tvIrRegistro);

        btnLogin.setOnClickListener(v -> {
            String correo      = etCorreo.getText().toString().trim();
            String contrasenia = etContrasenia.getText().toString().trim();

            if (correo.isEmpty() || contrasenia.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Basic Auth
            String credenciales = correo + ":" + contrasenia;
            String basicAuth = "Basic " + Base64.encodeToString(
                    credenciales.getBytes(), Base64.NO_WRAP
            );

            PizzasService service = ApiClient.getService();
            service.iniciarSesion(basicAuth).enqueue(new Callback<SesionResponse>() {
                @Override
                public void onResponse(Call<SesionResponse> call,
                                       Response<SesionResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        SesionResponse body = response.body();

                        SesionManager session = new SesionManager(LoginActivity.this);
                        session.guardarSesion(
                                body.getToken(),
                                body.getExpiracion(),
                                correo
                        );

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<SesionResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,
                            "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        tvIrRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class))
        );
    }
}