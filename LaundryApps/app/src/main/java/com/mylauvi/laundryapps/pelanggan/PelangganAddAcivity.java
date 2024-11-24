package com.mylauvi.laundryapps.pelanggan;

import android.content.Intent;
import android.content.res.ColorStateList;
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
import com.mylauvi.laundryapps.database.SQLiteHelper;
import com.mylauvi.laundryapps.model.ModelPelanggan;

import java.util.UUID;

public class PelangganAddAcivity extends AppCompatActivity {

    EditText nama, email, hp;
    Button btnSimpan, btnBatal;
    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pelanggan_add_acivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nama = (EditText) findViewById(R.id.edPelAddNama);
        email = (EditText) findViewById(R.id.edPelAddEmail);
        hp = (EditText) findViewById(R.id.edPelAddHp);
        btnSimpan = (Button) findViewById(R.id.btnPelAddSimpan);
        btnBatal = (Button) findViewById(R.id.btnPelAddBatal);

        db = new SQLiteHelper(PelangganAddAcivity.this);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelPelanggan mp = new ModelPelanggan();
                String uniqueID = UUID.randomUUID().toString();
                mp.setId(""+uniqueID);
                mp.setNama(nama.getText().toString());
                mp.setEmail(email.getText().toString());
                mp.setHp(hp.getText().toString());

                Toast.makeText(PelangganAddAcivity.this, ""+mp.getId()+mp.getNama()+mp.getEmail()+mp.getHp(), Toast.LENGTH_SHORT).show();
                boolean cek = db.insertPelanggan(mp);
                if(cek == true){
                    Toast.makeText(PelangganAddAcivity.this, "Data berhenti disimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PelangganAddAcivity.this, PelangganActivity.class));
                    finish();
                }else {
                    Toast.makeText(PelangganAddAcivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
