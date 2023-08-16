package com.example.final_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InstructionActivity extends AppCompatActivity {
    TextView instructionText;

    String instruction;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_instruction_detail);

        if (getIntent().hasExtra("bundle")) {
            Bundle bundle = getIntent().getBundleExtra("bundle");
            instruction = bundle.getString("instruction");
        }

        instructionText = findViewById(R.id.drinkInstructionText);

        instructionText.setText(instruction);

        setTitle("Instruction Detail");
    }
}
