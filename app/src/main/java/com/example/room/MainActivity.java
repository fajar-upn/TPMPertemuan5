package com.example.room;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.room.Adapter.MainAdapter;
import com.example.room.Model.AppDatabase;
import com.example.room.Model.DataDiri;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.view{

    private AppDatabase appDatabase;
    private MainPresenter presenter;
    private MainAdapter adapter;

    private Button btnOK;
    private RecyclerView recyclerView;
    private EditText etNama, etAlamat;
    private RadioButton rbLaki, rbPerempuan;
    private RadioGroup radioGroup;

    private char gender;
    private boolean edit = false;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.iniDb(getApplicationContext());

        btnOK = findViewById(R.id.btn_submit);
        btnOK.setOnClickListener(this);
        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        rbLaki = findViewById(R.id.rb_laki);
        rbPerempuan = findViewById(R.id.rb_perempuan);
        recyclerView = findViewById(R.id.rc_main);
        radioGroup = findViewById(R.id.rg_main);


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        presenter = new MainPresenter(this);

        presenter.readData(appDatabase);
    }

    @Override
    public void successAdd() {
        Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
        presenter.readData(appDatabase);
    }

    @Override
    public void successDelete() {
        Toast.makeText(this, "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
        presenter.readData(appDatabase);
    }

    @Override
    public void resetForm() {
        etNama.setText("");
        etAlamat.setText("");
        radioGroup.clearCheck();
        btnOK.setText("submit");
    }

    @Override
    public void getData(List<DataDiri> list) {
        adapter = new MainAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void editData(DataDiri item) {
        etNama.setText(item.getName());
        etAlamat.setText(item.getAddress());
        id = item.getId();
        if(String.valueOf(item.getGender()).equals("L")){
            rbLaki.setChecked(true);
        } else rbPerempuan.setChecked(true);
        edit = true;
        btnOK.setText("Update");
    }

    @Override
    public void deleteData(final DataDiri item) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Menghapus Data")
                .setMessage("Anda yakin ingin menghapus data ini?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resetForm();
                        presenter.deleteData(item, appDatabase);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onClick(View view) {
        if(view == btnOK){
            if(etNama.getText().toString().equals("") || etAlamat.getText().toString().equals("") || radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            } else {

                if (rbLaki.isChecked()) {
                    gender = 'L';
                } else if (rbPerempuan.isChecked()) {
                    gender = 'P';
                }

                if(!edit) presenter.insertData(etNama.getText().toString(), etAlamat.getText().toString(), gender, appDatabase);
                else{
                    presenter.editData(etNama.getText().toString(), etAlamat.getText().toString(), gender, id, appDatabase);
                    edit = false;
                }
                resetForm();
            }
        }
    }
}
