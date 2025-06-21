// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechRecognizer extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView textSpeech, statusText;
    private Button btnSpeak, btnClear;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognizer);

        textSpeech = findViewById(R.id.textSpeech);
        statusText = findViewById(R.id.statusText);
        btnSpeak = findViewById(R.id.btnSpeak);
        btnClear = findViewById(R.id.btnClear);
        btnBack = findViewById(R.id.btnBack);

        btnSpeak.setOnClickListener(view -> startSpeechToText());
        
        btnBack.setOnClickListener(v -> finish());
        
        btnClear.setOnClickListener(v -> {
            textSpeech.setText("Your speech will appear here...");
            statusText.setText("Ready to listen");
        });
    }

    private void startSpeechToText() {
        statusText.setText("Listening...");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition not available", Toast.LENGTH_SHORT).show();
            statusText.setText("Speech recognition not available");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQ_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            statusText.setText("Processing...");
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                textSpeech.setText(result.get(0));
                statusText.setText("Ready to listen");
            }
        } else {
            statusText.setText("Ready to listen");
        }
    }
}