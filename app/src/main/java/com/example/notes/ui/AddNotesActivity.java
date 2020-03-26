package com.example.notes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.R;

public class AddNotesActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String DATABASE_NAME = "MyNotes";
    public static final String CYAN = "#7AFCFF";
    public static final String YELLOW = "#FFCB08";
    public static final String CRIMSON_RED = "#DC143C";
    public static final String DARK_PINK = "#F83784";
    public static final String LIGHT_PINK = "#FF7EB9";
    TextView textViewCancel;
    EditText editTextTitle, editTextDesc;
    String color;
    Button buttonAdd;
    ImageView colorLightPink, colorDarkPink, colorCyan, colorRed, colorYellow;

    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDesc = findViewById(R.id.editTextDesc);

        colorCyan = findViewById(R.id.color_cyan);
        colorLightPink = findViewById(R.id.color_light_pink);
        colorDarkPink = findViewById(R.id.color_dark_pink);
        colorRed = findViewById(R.id.color_crimson_red);
        colorYellow = findViewById(R.id.color_yellow);
        textViewCancel = findViewById(R.id.cancel);
        buttonAdd = findViewById(R.id.buttonAddNotes);

        buttonAdd.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
        colorRed.setOnClickListener(this);
        colorDarkPink.setOnClickListener(this);
        colorLightPink.setOnClickListener(this);
        colorYellow.setOnClickListener(this);
        colorCyan.setOnClickListener(this);

        color = "#FFCB08";
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

            case R.id.color_cyan:
                color = CYAN;
                colorCyan.setBackgroundColor(Color.parseColor("#D3D3D3"));
                colorRed.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorDarkPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorLightPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorYellow.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                break;
            case R.id.color_crimson_red:
                color = CRIMSON_RED;
                colorCyan.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorRed.setBackgroundColor(Color.parseColor("#D3D3D3"));
                colorDarkPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorLightPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorYellow.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                break;
            case R.id.color_yellow:
                color = YELLOW;
                colorCyan.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorRed.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorDarkPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorLightPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorYellow.setBackgroundColor(Color.parseColor("#D3D3D3"));
                break;
            case R.id.color_dark_pink:
                color = DARK_PINK;
                colorCyan.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorRed.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorDarkPink.setBackgroundColor(Color.parseColor("#D3D3D3"));
                colorLightPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorYellow.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                break;
            case R.id.color_light_pink:
                color = LIGHT_PINK;
                colorCyan.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorRed.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorDarkPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                colorLightPink.setBackgroundColor(Color.parseColor("#D3D3D3"));
                colorYellow.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                break;
        }
    }

    private void addNotes() {
        String title = editTextTitle.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();

        if(validate(title, desc)){
            String insertSQL = "INSERT INTO notes \n" +
                    "(title, description, color)\n" +
                    "VALUES \n" +
                    "(?, ?, ?);";

            mDatabase.execSQL(insertSQL, new String[]{title, desc, color});

            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();

        }
    }


}
