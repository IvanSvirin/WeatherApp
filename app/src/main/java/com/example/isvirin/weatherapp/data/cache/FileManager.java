package com.example.isvirin.weatherapp.data.cache;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class FileManager {
    private Context context;
    public static final String filesDir = "files" + File.separator;
    public static String appDir;

    public FileManager(Context context) {
        this.context = context;
        appDir = context.getFilesDir().getAbsolutePath() + File.separator;
    }

    private File createFile(String fileName) {
        File file;
        String sdState = Environment.getExternalStorageState();
        if (sdState.equals(Environment.MEDIA_MOUNTED)) {
            file = new File(filesDir + fileName);
            file.getParentFile().mkdir();
        } else {
            file = new File(appDir + fileName);
            file.getParentFile().mkdir();
        }
        return file;
    }

    public void saveString(String s) {
        File file = createFile("file.txt");
//        File file = createFile(String.valueOf(System.currentTimeMillis()) + ".txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.append(s);
            osw.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readString(String fileName) {
        String s = "";
        File file = createFile(fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            s = result.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
