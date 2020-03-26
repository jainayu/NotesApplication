package com.example.notes.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.adapter.NotesAdapter;
import com.example.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

import static com.example.notes.ui.AddNotesActivity.CRIMSON_RED;
import static com.example.notes.ui.AddNotesActivity.CYAN;
import static com.example.notes.ui.AddNotesActivity.DARK_PINK;
import static com.example.notes.ui.AddNotesActivity.LIGHT_PINK;
import static com.example.notes.ui.AddNotesActivity.YELLOW;

public class NotesActivity extends AppCompatActivity {
    List<Note> noteList;
    SQLiteDatabase mDatabase;
    GridView gridViewNotes;
    NotesAdapter adapter;
    TextView inst;
    ImageView addNotes;
    String color;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        gridViewNotes = findViewById(R.id.gridViewNotes);
        inst = findViewById(R.id.instruction);
        addNotes = findViewById(R.id.imageViewAdd);
        noteList = new ArrayList<>();
        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesActivity.this, AddNotesActivity.class));
                finish();
            }
        });
        mDatabase = openOrCreateDatabase(AddNotesActivity.DATABASE_NAME, MODE_PRIVATE, null);
        createNotesTable();
        showNotes();
    }


    private void showNotes() {

        Cursor cursorNotes = mDatabase.rawQuery("SELECT * FROM notes ORDER BY timestamp DESC Limit 100000", null);
        if(cursorNotes.getCount() <=0){
            inst.setVisibility(View.VISIBLE);
        }
        if (cursorNotes.moveToFirst()) {
            inst.setVisibility(View.GONE);
            do {

                noteList.add(new Note(
                        cursorNotes.getInt(0),
                        cursorNotes.getString(1),
                        cursorNotes.getString(2),
                        cursorNotes.getString(3),
                        cursorNotes.getString(4)
                ));
            } while (cursorNotes.moveToNext());
        }

        cursorNotes.close();

        adapter = new NotesAdapter(this, R.layout.list_layout_notes, noteList);

        gridViewNotes.setAdapter(adapter);

        gridViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                final View updateView = inflater.inflate(R.layout.layout_update_notes, null);
                builder.setView(updateView);

                final EditText updateEditTextTitle = updateView.findViewById(R.id.update_notes_title);
                final EditText updateEditTextDesc = updateView.findViewById(R.id.update_notes_description);
                final ImageView save = updateView.findViewById(R.id.save);
                final ImageView delete = updateView.findViewById(R.id.delete);

                final ImageView colorCyan = updateView.findViewById(R.id.update_color_cyan);
                final ImageView colorLightPink = updateView.findViewById(R.id.update_color_light_pink);
                final ImageView colorDarkPink = updateView.findViewById(R.id.update_color_dark_pink);
                final ImageView colorRed = updateView.findViewById(R.id.update_color_crimson_red);
                final ImageView colorYellow = updateView.findViewById(R.id.update_color_yellow);

                updateEditTextTitle.setText(noteList.get(position).getTitle());
                updateEditTextDesc.setText(noteList.get(position).getDesc());
                color = noteList.get(position).getColorCode();
                switch (color){
                    case CRIMSON_RED:
                        colorRed.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        break;
                    case  AddNotesActivity.CYAN:
                        colorCyan.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        break;
                    case  YELLOW:
                        colorYellow.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        break;
                    case DARK_PINK:
                        colorDarkPink.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        break;
                    case LIGHT_PINK:
                        colorLightPink.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        break;
                }

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        String title = updateEditTextTitle.getText().toString().trim();
                        String desc = updateEditTextDesc.getText().toString().trim();

                        String sql = "UPDATE notes\n" +
                                "SET title=?,\n" +
                                "description=?,\n" +
                                "timestamp= CURRENT_TIMESTAMP\n," +
                                "color=?" +
                                "WHERE id=?;\n";

                        mDatabase.execSQL(sql,new String[]{title, desc, color, String.valueOf(noteList.get(position).getId())});
                        Toast.makeText(updateView.getContext(), "Notes Updated",Toast.LENGTH_LONG).show();
                        reloadEmployeesFromDatabase();
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = updateEditTextTitle.getText().toString().trim();
                        String desc = updateEditTextDesc.getText().toString().trim();

                        String sql = "UPDATE notes\n" +
                                "SET title=?,\n" +
                                "description=?,\n" +
                                "timestamp= CURRENT_TIMESTAMP\n," +
                                "color=?" +
                                "WHERE id=?;\n";

                        mDatabase.execSQL(sql,new String[]{title, desc, color,String.valueOf(noteList.get(position).getId())});
                        Toast.makeText(view.getContext(), "Notes Updated",Toast.LENGTH_LONG).show();
                        reloadEmployeesFromDatabase();
                        dialog.dismiss();
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sql = "DELETE FROM notes WHERE id=?";
                        mDatabase.execSQL(sql, new Integer[]{noteList.get(position).getId()});
                        Toast.makeText(view.getContext(), "Deleted",Toast.LENGTH_LONG).show();
                        reloadEmployeesFromDatabase();
                        dialog.dismiss();
                    }
                });


                colorCyan.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        color = CYAN;
                        colorCyan.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        colorRed.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorDarkPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorLightPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorYellow.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    }
                });

                colorLightPink.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        color = LIGHT_PINK;
                        colorCyan.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorRed.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorDarkPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorLightPink.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        colorYellow.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    }
                });
                colorDarkPink.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        color = DARK_PINK;
                        colorCyan.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorRed.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorDarkPink.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        colorLightPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorYellow.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    }
                });
                colorRed.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        color = CRIMSON_RED;
                        colorCyan.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorRed.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        colorDarkPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorLightPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorYellow.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    }
                });
                colorYellow.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        color = YELLOW;
                        colorCyan.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorRed.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorDarkPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorLightPink.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorYellow.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    }
                });



            }
        });
    }
    private void reloadEmployeesFromDatabase() {
        Cursor cursorNotes = mDatabase.rawQuery("SELECT * FROM notes ORDER BY timestamp DESC Limit 100000", null);
        noteList.clear();
        if(cursorNotes.getCount() <=0){
            inst.setVisibility(View.VISIBLE);
        }
        if (cursorNotes.moveToFirst()) {
            inst.setVisibility(View.GONE);
            do {

                noteList.add(new Note(
                        cursorNotes.getInt(0),
                        cursorNotes.getString(1),
                        cursorNotes.getString(2),
                        cursorNotes.getString(3),
                        cursorNotes.getString(4)
                ));
            } while (cursorNotes.moveToNext());
        }

        cursorNotes.close();
        adapter = new NotesAdapter(this, R.layout.list_layout_notes, noteList);

        gridViewNotes.setAdapter(adapter);
    }
    private void createNotesTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS notes (\n" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    title VARCHAR(200),\n" +
                        "    description VARCHAR(200),\n" +
                        "    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                        "    color VARCHAR(7)" +
                        ");"
        );
    }
}
