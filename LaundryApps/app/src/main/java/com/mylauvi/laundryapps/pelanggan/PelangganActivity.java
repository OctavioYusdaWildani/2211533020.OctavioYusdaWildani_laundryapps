package com.mylauvi.laundryapps.pelanggan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mylauvi.laundryapps.R;
import com.mylauvi.laundryapps.adapter.AdapterPelanggan;
import com.mylauvi.laundryapps.database.SQLiteHelper;
import com.mylauvi.laundryapps.helper.Constant;
import com.mylauvi.laundryapps.model.ModelPelanggan;

import java.util.ArrayList;
import java.util.List;

public class PelangganActivity extends AppCompatActivity {
    SQLiteHelper db;
    TextView btnPelAdd;
    RecyclerView rvPelanggan;
    AdapterPelanggan adapterPelanggan;
    ArrayList<ModelPelanggan> list;
    ProgressDialog progressDialog;
    AlphaAnimation btnAnimasi = new AlphaAnimation(1F, 0.5F);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan);

        // Handling insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        setView();
        eventHandling();
        getData();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(btnAnimasi);
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            ModelPelanggan mp = list.get(position);
            Intent intent= new Intent(PelangganActivity.this, PelangganEditActivity.class);
            intent.putExtra(Constant.ID, mp.getId());
            intent.putExtra(Constant.NAMA, mp.getNama());
            intent.putExtra(Constant.EMAIL, mp.getEmail());
            intent.putExtra(Constant.HP, mp.getHp());
            startActivity(intent);

        }
    };

    private void getData() {
        list.clear();
        showMsg();
        try {
            List<ModelPelanggan> pelangganList = db.getPelanggan();
            if (pelangganList.size() > 0) {
                list.addAll(pelangganList);  // Simplified adding all data

                adapterPelanggan = new AdapterPelanggan(this, list);
                rvPelanggan.setAdapter(adapterPelanggan);
                adapterPelanggan.setOnItemClickListener(onClickListener);  // Handle click event
                adapterPelanggan.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Data pelanggan tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
        } finally {
            progressDialog.dismiss();
        }
    }

    private void eventHandling() {
        btnPelAdd.setOnClickListener(v -> {
            v.startAnimation(btnAnimasi);
            startActivity(new Intent(PelangganActivity.this, PelangganAddAcivity.class));
        });
    }

    private void setView() {
        db = new SQLiteHelper(this);
        progressDialog = new ProgressDialog(this);
        btnPelAdd = findViewById(R.id.btnPelAdd);
        rvPelanggan = findViewById(R.id.rvPelanggan);
        list = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvPelanggan.setLayoutManager(llm);
        rvPelanggan.setHasFixedSize(true);
    }

    private void showMsg() {
        progressDialog.setTitle("Informasi");
        progressDialog.setMessage("Loading Data..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getData(); // Memuat ulang data setelah penghapusan atau pembaruan
    }

}

