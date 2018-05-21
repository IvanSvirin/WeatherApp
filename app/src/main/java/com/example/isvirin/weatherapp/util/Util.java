package com.example.isvirin.weatherapp.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.isvirin.weatherapp.R;
import com.example.isvirin.weatherapp.service.RequestService;
import com.example.isvirin.weatherapp.view.activity.MainActivity;

public class Util {
    public static final String  url = "http://openweathermap.org/data/2.5/";
    public static final String appId = "b6907d289e10d714a6e88b30761fae22";
    public static void showOurDialog(final Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.qustion_title)
                .setMessage(message)
                .setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startService(new Intent(context, RequestService.class));
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
