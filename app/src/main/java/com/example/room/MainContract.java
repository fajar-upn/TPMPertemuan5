package com.example.room;

import android.view.View;

import com.example.room.Model.AppDatabase;
import com.example.room.Model.DataDiri;

import java.util.List;

public interface MainContract {
    interface view extends View.OnClickListener{
        void successAdd();
        void successDelete();
        void resetForm();
        void getData(List<DataDiri> list);
        void editData(DataDiri item);
        void deleteData(DataDiri item);
    }

    interface presenter{
        void insertData(String name, String alamat, char gender, AppDatabase database);
        void readData(AppDatabase database);
        void editData(String nama, String alamat, char gender, int id, AppDatabase database);
        void deleteData(DataDiri dataDiri, AppDatabase database);
    }
}
