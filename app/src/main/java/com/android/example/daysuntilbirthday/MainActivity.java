package com.android.example.daysuntilbirthday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText editText;
    private TextView textView;
    private Button button2;
    private CalculateDays calculateDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(this);

        calculateDays = new CalculateDays();

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.monthNames, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        final int[] longMonths = {0, 2, 4, 6, 7, 9, 11};
        final int[] shortMonths = {3, 5, 8, 10};


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int longMonth : longMonths) {
                    if (longMonth == position) {
                        editText.setFilters(new InputFilter[]{new InputFilterMinMax(1, 31)});
                    }
                }
                for (int shortMonth : shortMonths) {
                    if (shortMonth == position) {
                        editText.setFilters(new InputFilter[]{new InputFilterMinMax(1, 30)});
                        if (!editText.getText().toString().isEmpty()) {
                            if (Integer.parseInt(editText.getText().toString()) > 30) {
                                editText.setText("30");
                            }
                        }
                    }
                }
                if (position == 1) {
                    editText.setFilters(new InputFilter[]{new InputFilterMinMax(1, 29)});
                    if (!editText.getText().toString().isEmpty()) {
                        if (Integer.parseInt(editText.getText().toString()) > 29) {
                            editText.setText("29");
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editText = findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new InputFilterMinMax(1, 31)});

        textView = findViewById(R.id.textView2);
        textView.setVisibility(View.INVISIBLE);

        button2 = findViewById(R.id.button2);
        button2.setVisibility(View.INVISIBLE);
    }

    public void buttonClicked(View view) {
        if (!editText.getText().toString().isEmpty()) {
            textView.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            textView.setText(calculateDays.calcuteDays(Integer.parseInt(editText.getText().toString()), spinner.getSelectedItemPosition() + 1));
        }
        else {
            Toast toast = Toast.makeText(this, "Input a date.", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void shareButtonClicked(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, calculateDays.getShareMessage());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}
