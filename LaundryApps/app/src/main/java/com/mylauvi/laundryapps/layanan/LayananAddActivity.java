package com.mylauvi.laundryapps.layanan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mylauvi.laundryapps.R;
import com.mylauvi.laundryapps.database.SQLiteHelper2;
import com.mylauvi.laundryapps.model.ModelLayanan;

import java.util.UUID;

public class LayananAddActivity extends AppCompatActivity {
    EditText edTipe, edHarga;
    Button btnLaySimpan, btnLayBatal;
    SQLiteHelper2 db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layanan_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edTipe = (EditText) findViewById(R.id.edLayAddLayanan);
        edHarga = (EditText) findViewById(R.id.edLayAddHarga);
        btnLaySimpan = (Button) findViewById(R.id.btnLayAddSimpan);
        btnLayBatal = (Button) findViewById(R.id.btnLayAddBatal);

        db = new SQLiteHelper2(LayananAddActivity.this);
        btnLaySimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelLayanan ml = new ModelLayanan();
                String uniqueID = UUID.randomUUID().toString();
                ml.setId(""+uniqueID);
                ml.setTipe(edTipe.getText().toString());
                ml.setHarga(edHarga.getText().toString());

                Toast.makeText(LayananAddActivity.this, ""+ml.getId()+ml.getTipe()+ml.getHarga(), Toast.LENGTH_SHORT).show();
                boolean cek = db.insertLayanan(ml);
                if(cek == true){
                    Toast.makeText(LayananAddActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LayananAddActivity.this, LayananActivity.class));
                    finish();
                }else {
                    Toast.makeText(LayananAddActivity.this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLayBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}