package com.example.laba3;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends AppCompatActivity {

    private DateFormat format = new SimpleDateFormat("HH:mm:ss "); //"yyyy.MM.dd 'at'
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private ArrayList<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM students");
        insertStartInfo();

        dbHelper.close();

        Button btn_openDB = findViewById(R.id.btn_openDB);
        Button btn_addItemDB = findViewById(R.id.btn_addItemDB);
        Button btn_replace = findViewById(R.id.btn_replace);


        btn_openDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ShowDatabase.class);
                startActivity(intent);
            }
        });

        btn_addItemDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();

                Random random = new Random();
                int number;
                number = random.nextInt(name.size());
                Calendar thisDate = Calendar.getInstance();
                String data = format.format(thisDate.getTime());
                database.execSQL("INSERT INTO students(name, time) VALUES (\'" + name.get(number)+ "\','" + data + "');");
                Toast toast = Toast.makeText(Menu.this, "Запись добавлена!", Toast.LENGTH_LONG);
                toast.show();

                name.remove(number);

                dbHelper.close();
            }
        });

        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();
                database.execSQL("UPDATE students SET name = 'Иванов Иван Иванович' WHERE id = (SELECT max(id) FROM students)");
                Toast toast = Toast.makeText(Menu.this, "И.И.И. на место прибыл!", Toast.LENGTH_LONG);
                toast.show();

                dbHelper.close();
            }
        });
    }

    private void insertStartInfo() {

        name = new ArrayList<>();
        name.add("Трибогов Мирослав Ишевич");
        name.add("Моралев Петр Силыч");
        name.add("Канистров Дорн Никитович");
        name.add("Листьева Лара Крофтовна");
        name.add("Троцкий Василий Петрович");
        name.add("Борджиа Чезаре Флорентинович");
        name.add("Таницкий Михаил Криллович");
        name.add("Патова Лена Дмитриевна");
        name.add("Соловаьева Ольга Геннадиевна");
        name.add("Жилина Любовь Петровна");
        name.add("Сидорова Симона Мэлсовна");
        name.add("Узумаки Наруто Минатович");
        name.add("Петина Галина Михайловна");
        name.add("Бабочкина Вара Олеговна");
        name.add("Артемов Артем Артемович");


        Random random = new Random();
        int number;

        for (int i = 0; i < 5; i++) {

            number = random.nextInt(name.size());

            Calendar thisDate = Calendar.getInstance();
            String data = format.format(thisDate.getTime());

            database.execSQL("INSERT INTO students(name, time) VALUES (\'" + name.get(number)+ "\',\'" + data + "\');");
            name.remove(number);
        }

    }
}
