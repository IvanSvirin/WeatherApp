package com.example.isvirin.weatherapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.isvirin.weatherapp.R;

public class CustomDialog extends Dialog {
    private Context context;
    private Button button_cancel;
    private Button button_ok;
    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        this.setContentView(R.layout.dialog);

        button_cancel = findViewById(R.id.button_cancel);
        button_ok = findViewById(R.id.button_ok);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Snackbar.make(v, "СПАСИБО", Snackbar.LENGTH_LONG).show();
                Toast.makeText(CustomDialog.this.context, "СПАСИБО", Toast.LENGTH_LONG).show();
            }
        });
    }

}
