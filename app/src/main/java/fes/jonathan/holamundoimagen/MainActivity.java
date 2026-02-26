package fes.jonathan.holamundoimagen;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    ImageButton imageButton;
    ImageButton imageButton2;
    ImageButton imageButton3;
    ImageButton imageButton4;
    ImageButton imageButton5;




    String url = "https://2d99lsm9-3000.usw3.devtunnels.ms/images/menus/breads.png";
    String url2 = "https://2d99lsm9-3000.usw3.devtunnels.ms/images/menus/pizza.png";
    String url3 = "https://2d99lsm9-3000.usw3.devtunnels.ms/images/menus/chicken.png";
    String url4 = "https://2d99lsm9-3000.usw3.devtunnels.ms/images/menus/dessert.png";
    String url5 = "https://2d99lsm9-3000.usw3.devtunnels.ms/images/menus/drinks.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageButton imageButton = findViewById(R.id.imageButton);
        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        ImageButton imageButton4 = findViewById(R.id.imageButton4);
        ImageButton imageButton5 = findViewById(R.id.imageButton5);



        Glide.with(this).load(url2).into(imageButton);
        Glide.with(this).load(url).into(imageButton2);
        Glide.with(this).load(url3).into(imageButton3);
        Glide.with(this).load(url4).into(imageButton4);
        Glide.with(this).load(url5).into(imageButton5);




    }

    public void pizza(View view) {

        Toast.makeText(this, "Pizza", Toast.LENGTH_SHORT).show();
    }
    public void Entradas(View view) {
        Toast.makeText(this, "Entradas", Toast.LENGTH_SHORT).show();
    }
    public void Pollo(View view) {
        Toast.makeText(this, "Pollo", Toast.LENGTH_SHORT).show();
    }
    public void Postres(View view) {
        Toast.makeText(this, "Postres", Toast.LENGTH_SHORT).show();
    }
    public void Bebidas(View view) {
        Toast.makeText(this, "Bebidas", Toast.LENGTH_SHORT).show();
    }



}