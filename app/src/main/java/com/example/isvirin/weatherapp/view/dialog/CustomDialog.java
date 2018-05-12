package com.example.isvirin.weatherapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.isvirin.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialog extends Dialog {
    private Context context;
    @BindView(R.id.button_cancel)
    Button button_cancel;
    @BindView(R.id.button_ok)
    Button button_ok;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        this.setContentView(R.layout.dialog);

        ButterKnife.bind(this);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @OnClick(R.id.button_ok)
    void buttonClicked(View v) {
        dismiss();
        Snackbar.make(v, "СПАСИБО", Snackbar.LENGTH_LONG).show();
        Toast.makeText(CustomDialog.this.context, "СПАСИБО", Toast.LENGTH_LONG).show();
    }

}
