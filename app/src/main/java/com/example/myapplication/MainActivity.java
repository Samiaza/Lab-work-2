package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int counter = 0;
    private int value = 0;
    private Chronometer mChronometer;
    private Switch mSwitch;
    private long chronometerTimeShift;
    private long chronometerValue;
    private EditText editText;
    private Spinner spinner;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometerTimeShift = 0;
        mChronometer = findViewById(R.id.chronometer);
        mChronometer.setFormat("%s");
        mChronometer.setBase(SystemClock.elapsedRealtime());

        View red_light = findViewById(R.id.red_light_view);
        View yellow_light = findViewById(R.id.yellow_light_view);
        View green_light = findViewById(R.id.green_light_view);
        Drawable redDrawable = this.getDrawable(R.drawable.red_light);
        Drawable yellowDrawable = this.getDrawable(R.drawable.yellow_light);
        Drawable greenDrawable = this.getDrawable(R.drawable.green_light);
        Drawable defaultDrawable = this.getDrawable(R.drawable.default_light);


        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                chronometerValue = SystemClock.elapsedRealtime();
                if (chronometerValue / 1000 % 3 == 0) {
                    red_light.setBackground(redDrawable);
                } else {
                    red_light.setBackground(defaultDrawable);
                }
                if (chronometerValue / 1000 % 3 == 1) {
                    yellow_light.setBackground(yellowDrawable);
                } else {
                    yellow_light.setBackground(defaultDrawable);
                }
                if (chronometerValue / 1000 % 3 == 2) {
                    green_light.setBackground(greenDrawable);
                } else {
                    green_light.setBackground(defaultDrawable);
                }
            }
        });

        mSwitch = findViewById(R.id.switch2);
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((Switch)view).isChecked()) {
                    startSecondsCounter(view);
                } else {
                    stopSecondsCounter(view);
                }
            }
        });

        textView = findViewById(R.id.textView3);

        editText = findViewById(R.id.editTextNumber);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                long decimalContent = Integer.parseInt(charSequence.toString());
                int spinnerPosition = spinner.getSelectedItemPosition();
                switch (spinnerPosition) {
                    case 0:
                        textView.setText(Long.toBinaryString(decimalContent));
                        break;
                    case 1:
                        textView.setText(Long.toOctalString(decimalContent));
                        break;
                    case 2:
                        textView.setText(Long.toHexString(decimalContent));
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                long decimalContent = Integer.parseInt(editText.getText().toString());
                switch (i) {
                    case 0:
                        textView.setText(Long.toBinaryString(decimalContent));
                        break;
                    case 1:
                        textView.setText(Long.toOctalString(decimalContent));
                        break;
                    case 2:
                        textView.setText(Long.toHexString(decimalContent));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void reverseTextInputString(View view) {
        this.counter++;
        TextView textView = findViewById(R.id.textView2);
        textView.setText(Integer.toString(this.counter));

        com.google.android.material.textfield.TextInputEditText textInputEditText= findViewById(R.id.textInputEditText);
        char[] tmpCharArray = textInputEditText.getText().toString().toCharArray();
        int tmpCharArrayLength = tmpCharArray.length;
        char [] reversedCharArray = tmpCharArray.clone();
        for (int i = 0; i < tmpCharArrayLength; i++) {
            reversedCharArray[tmpCharArrayLength - i - 1] = tmpCharArray[i];
        }
        textInputEditText.setText(new String(reversedCharArray));
    }

    public void incrementValue(View view) {
        value++;
        TextView textView = findViewById(R.id.textView11);
        textView.setText(Integer.toString(this.value));
    }

    public void decrementValue(View view) {
        value--;
        TextView textView = findViewById(R.id.textView11);
        textView.setText(Integer.toString(this.value));
    }

    public void startSecondsCounter(View view) {
        mChronometer.setBase(SystemClock.elapsedRealtime() - chronometerTimeShift);
        mChronometer.start();
    }

    public void stopSecondsCounter(View view) {
        mChronometer.stop();
        chronometerTimeShift = SystemClock.elapsedRealtime() - mChronometer.getBase();
    }

    public void resetSecondsCounter(View view) {
        chronometerTimeShift = SystemClock.elapsedRealtime();
        mChronometer.setBase(chronometerTimeShift);
    }
}