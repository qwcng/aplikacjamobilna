package com.gro.cm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        BottomNavigationView dolneMenu = findViewById(R.id.bottomNavigation);
        dolneMenu.setSelectedItemId(R.id.nav_calculator);

        EditText kwotaPoczatkowa = findViewById(R.id.kwotaPoczatkowa);
        EditText oprocentowanie = findViewById(R.id.oprocentowanie);
        EditText liczbaLat = findViewById(R.id.liczbaLat);
        Button przyciskOblicz = findViewById(R.id.przyciskOblicz);
        TextView wynikInwestycji = findViewById(R.id.wynikInwestycji);

        dolneMenu.setOnItemSelectedListener(item -> {
            int wybranyElement = item.getItemId();
            if (wybranyElement == R.id.nav_notes) {
                Intent intencja = new Intent(this, NotesActivity.class);
                startActivity(intencja);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return wybranyElement == R.id.nav_calculator;
        });
        przyciskOblicz.setOnClickListener(v -> {
            String tekstKwoty = kwotaPoczatkowa.getText().toString();
            String tekstOprocentowania = oprocentowanie.getText().toString();
            String tekstLat = liczbaLat.getText().toString();
            if (tekstKwoty.isEmpty() || tekstOprocentowania.isEmpty() ||tekstLat.isEmpty()) {
                Toast.makeText(this, "Wprowadź wszystkie dane finansowe!", Toast.LENGTH_SHORT).show();
                return;
            }
            double kwota = Double.parseDouble(tekstKwoty);
            double stopa = Double.parseDouble(tekstOprocentowania) / 100;
            int lata = Integer.parseInt(tekstLat);

            if (kwota <= 0 || stopa <= 0 || lata <= 0) {
                Toast.makeText(this, "Wartości muszą być większe od 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            double wynik = kwota * Math.pow((1 + stopa), lata);
            wynikInwestycji.setText(String.format("Po %d latach Twoja inwestycja będzie warta:\n%.2f zł", lata, wynik));
        });
    }
}