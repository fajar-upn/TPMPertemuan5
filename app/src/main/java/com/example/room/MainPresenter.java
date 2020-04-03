package com.example.room;

import android.os.AsyncTask;
import android.util.Log;

import com.example.room.Model.AppDatabase;
import com.example.room.Model.DataDiri;

import java.util.List;

public class MainPresenter implements MainContract.presenter {

    private MainContract.view view;

    public MainPresenter(MainContract.view view) {
        this.view = view;
    }

    class InsertData extends AsyncTask<Void,Void,Long>{

        private AppDatabase database;
        private DataDiri dataDiri;

        public InsertData(AppDatabase database, DataDiri dataDiri){
            this.database = database;
            this.dataDiri = dataDiri;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return database.dao().insertData(dataDiri);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            view.successAdd();
        }
    }

    @Override
    public void insertData(String name, String alamat, char gender, final AppDatabase database) {
        final DataDiri dataDiri = new DataDiri();
        dataDiri.setName(name);
        dataDiri.setAddress(alamat);
        dataDiri.setGender(gender);
        new InsertData(database,dataDiri).execute();

    }

    @Override
    public void readData(AppDatabase database) {
        List list;
        list = database.dao().getData();
        view.getData(list);
    }

    class EditData extends AsyncTask<Void,Void, Integer>{

        private AppDatabase database;
        private DataDiri dataDiri;

        public EditData(AppDatabase database, DataDiri dataDiri) {
            this.database = database;
            this.dataDiri = dataDiri;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return database.dao().updateData(dataDiri);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d("integer db","onPostExecute: "+integer);
            view.successAdd();
        }
    }

    @Override
    public void editData(String nama, String alamat, char gender, int id, AppDatabase database) {
        final DataDiri dataDiri = new DataDiri();
        dataDiri.setName(nama);
        dataDiri.setAddress(alamat);
        dataDiri.setGender(gender);

        new EditData(database, dataDiri).execute();
    }

    class DeleteData extends AsyncTask<Void, Void, Void>{

        private AppDatabase database;
        private DataDiri dataDiri;

        DeleteData(AppDatabase database, DataDiri dataDiri) {
            this.database = database;
            this.dataDiri = dataDiri;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.dao().deleteData(dataDiri);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            view.successDelete();
        }
    }

    @Override
    public void deleteData(DataDiri dataDiri, AppDatabase database) {
        new DeleteData(database,dataDiri).execute();
    }
}
