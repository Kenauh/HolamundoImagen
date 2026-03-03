package fes.jonathan.holamundoimagen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class pizzas extends AppCompatActivity {
    String baseUrl = "https://2d99lsm9-3000.usw3.devtunnels.ms";

    String url7 = baseUrl + "/images/pizzas/0ad30a1e-d7ed-4384-9b1e-b8a5c9587174.jpg";
    String url8 = baseUrl + "/images/pizzas/3f362b55-3f56-47f9-b67a-ff49eb10f66a.jpg";
    String url9 = baseUrl + "/images/pizzas/7f339d0c-bf13-4839-a9dc-de6721210a5b.jpg";
    String url10 = baseUrl + "/images/pizzas/8be27c50-bb3c-4774-ac8f-a41f42029617.jpg";
    String url11 = baseUrl + "/images/pizzas/75b89c2b-2c59-4225-8478-4e9ab4432ec9.jpg";
    String url12 = baseUrl + "/images/pizzas/9614b6f3-0def-4ace-8c5d-e0954d899a5b.jpg";
    String url13 = baseUrl + "/images/pizzas/022238e3-ac62-45af-b765-379a077a9b26.jpg";
    String url14 = baseUrl + "/images/pizzas/67734d82-5c38-40f8-a311-255c0d157bd5.jpg";
    String url15 = baseUrl + "/images/pizzas/00455854-40f9-45e2-a241-b03e70dfe6cc.jpg";
    String url16 = baseUrl + "/images/pizzas/49249843-d9af-4356-bcac-ae94e3357742.jpg";
    String url17 = baseUrl + "/images/pizzas/be242cad-08db-4b00-becd-be48a9fb86a4.jpg";
    String url18 = baseUrl + "/images/pizzas/cdcef20a-407a-488e-9751-6f481a871048.jpg";
    String url19 = baseUrl + "/images/pizzas/db99ef86-f2b8-4a8e-8afb-5ad4409e4233.jpg";
    String url20 = baseUrl + "/images/pizzas/f8748c06-e178-4d38-b99c-a522ad450a86.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pizzas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton imageButton6 = findViewById(R.id.imageButton6);
        Glide.with(this).load(url7).into(imageButton6);

        ImageButton imageButton7 = findViewById(R.id.imageButton7);
        Glide.with(this).load(url8).into(imageButton7);

        ImageButton imageButton8 = findViewById(R.id.imageButton8);
        Glide.with(this).load(url9).into(imageButton8);

        ImageButton imageButton9 = findViewById(R.id.imageButton9);
        Glide.with(this).load(url10).into(imageButton9);

        ImageButton imageButton10 = findViewById(R.id.imageButton10);
        Glide.with(this).load(url11).into(imageButton10);

        ImageButton imageButton11 = findViewById(R.id.imageButton11);
        Glide.with(this).load(url12).into(imageButton11);

        ImageButton imageButton12 = findViewById(R.id.imageButton12);
        Glide.with(this).load(url13).into(imageButton12);

        ImageButton imageButton13 = findViewById(R.id.imageButton13);
        Glide.with(this).load(url14).into(imageButton13);

        ImageButton imageButton14 = findViewById(R.id.imageButton14);
        Glide.with(this).load(url15).into(imageButton14);

        ImageButton imageButton15 = findViewById(R.id.imageButton15);
        Glide.with(this).load(url16).into(imageButton15);

        ImageButton imageButton16 = findViewById(R.id.imageButton16);
        Glide.with(this).load(url17).into(imageButton16);

        ImageButton imageButton17 = findViewById(R.id.imageButton17);
        Glide.with(this).load(url18).into(imageButton17);

        ImageButton imageButton18 = findViewById(R.id.imageButton18);
        Glide.with(this).load(url19).into(imageButton18);

        ImageButton imageButton19 = findViewById(R.id.imageButton19);
        Glide.with(this).load(url20).into(imageButton19);
    }

    public void irAInicio(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}