package com.gro.cm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotesActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "inwestowanie";
    private static final String KEY_NOTES = "inwestowanie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        BottomNavigationView dolneMenu = findViewById(R.id.bottomNavigation);
        dolneMenu.setSelectedItemId(R.id.nav_notes);

        EditText poleNotatek = findViewById(R.id.poleNotatek);
        Button przyciskZapiszNotatki = findViewById(R.id.przyciskZapiszNotatki);

        SharedPreferences ustawienia = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String zapisaneNotatki = ustawienia.getString(KEY_NOTES, "");
        poleNotatek.setText(zapisaneNotatki);

        dolneMenu.setOnItemSelectedListener(item -> {
            int wybranyElement = item.getItemId();
            if (wybranyElement == R.id.nav_calculator) {
                Intent intencja = new Intent(this, CalculatorActivity.class);
                startActivity(intencja);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return wybranyElement == R.id.nav_notes;
        });

        przyciskZapiszNotatki.setOnClickListener(v -> {
            String tekstNotatki = poleNotatek.getText().toString().trim();

            if (tekstNotatki.isEmpty()) {
                Toast.makeText(this, "Nie możesz zapisać pustej notatki!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor edytor = ustawienia.edit();
            edytor.putString(KEY_NOTES, tekstNotatki);
            edytor.apply();

            Toast.makeText(this, "Plan inwestycyjny został zapisany!", Toast.LENGTH_SHORT).show();
        });
    }
}