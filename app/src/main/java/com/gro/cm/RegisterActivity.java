package com.gro.cm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        EditText age = findViewById(R.id.age);
        RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);
        Spinner spinnerExperience = findViewById(R.id.spinnerExperience);
        Button btnRegister = findViewById(R.id.btnRegister);

        String[] poziomy = {
                "Nowicjusz",
                "Średnio zaawansowany",
                "CEO CTO Business Entrepreneur lvl 150"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, poziomy);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExperience.setAdapter(adapter);

        btnRegister.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String ageStr = age.getText().toString().trim();
            int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();

            if (user.isEmpty() || pass.isEmpty() || ageStr.isEmpty() || selectedGenderId == -1) {
                Toast.makeText(this, "Uzupełnij wszystkie dane rejestracji!", Toast.LENGTH_SHORT).show();
                return;
            }

            int wiek = Integer.parseInt(ageStr);
            if (wiek < 18) {
                Toast.makeText(this, "Musisz mieć ukończone 18 lat!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedGender = findViewById(selectedGenderId);
            String plec = selectedGender.getText().toString();
            String wybranyPoziom = spinnerExperience.getSelectedItem().toString();

            Toast.makeText(this, "Konto utworzone pomyślnie!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, CalculatorActivity.class);
            startActivity(intent);
            finish();
        });
    }
}