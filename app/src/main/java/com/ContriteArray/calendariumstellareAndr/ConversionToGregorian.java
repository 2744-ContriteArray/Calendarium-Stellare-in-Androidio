package com.ContriteArray.calendariumstellareAndr;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.time.*;

public class ConversionToGregorian extends AppCompatActivity {

    //private final String DEFAULT_STARDATE = "0000000.0000";
    //private final LocalDateTime DEFAULT_GREG = LocalDateTime.of(1970,8,13,0,0,0);

    protected stardate pulsar; // A stardate object for use in all calculations herein
    protected LocalDateTime Greg; // A LocalDateTime object for use in all calculations herein

    // Declare text input fields for activity
    EditText strdtInput;
    EditText gregInput;

    // Declare buttons
    ImageButton copyStrdtButton;
    ImageButton copyGregButton;
    ImageButton con2Star;
    ImageButton con2Greg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversion_to_gregorian);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pulsar = new stardate();
        Greg = LocalDateTime.of(1970,8,13,0,0,0);
    }
}