package com.example.notes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.R;

public class AddNotesActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String DATABASE_NAME = "MyNotes";
    TextView textViewCancel;
    EditText editTextTitle, editTextDesc;
    Button buttonAdd;

    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDesc = findViewById(R.id.editTextDesc);
        textViewCancel = findViewById(R.id.cancel);
        buttonAdd = findViewById(R.id.buttonAddNotes);

        buttonAdd.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


    }
    private boolean validate(String title, String desc) {
        if (title.isEmpty() && desc.isEmpty())
            return false;
        return true;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonAddNotes:
                addNotes();
                startActivity(new Intent(AddNotesActivity.this, NotesActivity.class));
                finish();
                break;

            case R.id.cancel:
                startActivity(new Intent(AddNotesActivity.this, NotesActivity.class));
                finish();
                break;
        }
    }

    private void addNotes() {
        String title = editTextTitle.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();

        if(validate(title, desc)){
            String insertSQL = "INSERT INTO notes \n" +
                    "(title, description)\n" +
                    "VALUES \n" +
                    "(?, ?);";

            mDatabase.execSQL(insertSQL, new String[]{title, desc});

            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();

        }
    }


}
