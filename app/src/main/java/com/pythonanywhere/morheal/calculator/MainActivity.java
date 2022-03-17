package com.pythonanywhere.morheal.calculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private double result = 0;
    private double first = 0;
    private double second = 0;
    private File langFile;
    private TextView pole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitComponents();
        setButtonsClick();
        if (savedInstanceState != null) {
            result = savedInstanceState.getDouble("result");
            first = savedInstanceState.getDouble("first");
            second = savedInstanceState.getDouble("second");
            pole.setText(savedInstanceState.getString("input"));
        }else{
            langFile = new File("lang.bin");
            try {
                if(!langFile.exists())langFile.createNewFile();
                BufferedReader br = new BufferedReader(new FileReader(langFile));
                String st;
                while ((st = br.readLine()) != null) ;
                setLocale(st);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void InitComponents() {
        pole = (TextView) findViewById(R.id.pole);
        registerForContextMenu(pole);
        if (result != 0) pole.setText(String.valueOf(result));
        else pole.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.set_en:
                Toast.makeText(MainActivity.this, "Set english language!", Toast.LENGTH_LONG).show();
                setLocale("en");
                return true;
            case R.id.set_uk:
                Toast.makeText(MainActivity.this, "Встановлено українську мову!", Toast.LENGTH_LONG).show();
                setLocale("uk");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.pole:
                menu.add(0, 1, 0, getString(R.string.clear));
                menu.add(0, 2, 0, getString(R.string.copy));
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                clearAll();
                return true;
            case 2:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("result", pole.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    private void setButtonsClick() {
        findViewById(R.id.button_clear).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return clearAll();
            }
        });
        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete();
            }
        });

        List<View> list = new ArrayList<>();
        list.add(findViewById(R.id.button0));
        list.add(findViewById(R.id.button1));
        list.add(findViewById(R.id.button2));
        list.add(findViewById(R.id.button3));
        list.add(findViewById(R.id.button4));
        list.add(findViewById(R.id.button5));
        list.add(findViewById(R.id.button6));
        list.add(findViewById(R.id.button7));
        list.add(findViewById(R.id.button8));
        list.add(findViewById(R.id.button9));
        for (View view : list) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NumberClick(v);
                }
            });
        }
        list.clear();


        findViewById(R.id.button_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDot();
            }
        });

        list.add(findViewById(R.id.button_result));
        list.add(findViewById(R.id.button_add));
        list.add(findViewById(R.id.button_sub));
        list.add(findViewById(R.id.button_multiple));
        list.add(findViewById(R.id.button_div));
        list.add(findViewById(R.id.button_pow));
        for (View view : list) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CalculateWithTwoArgs(v);
                }
            });
        }
        list.clear();

        list.add(findViewById(R.id.button_sin));
        list.add(findViewById(R.id.button_cos));
        list.add(findViewById(R.id.button_tan));
        list.add(findViewById(R.id.button_ctan));
        list.add(findViewById(R.id.button_log));
        list.add(findViewById(R.id.button_sqrt));
        list.add(findViewById(R.id.button_procent));
        for (View view : list) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CalculateWithOneArg(v);
                }
            });
        }
    }

    private void setLocale(String mLang) {
        Locale mNewLocale = new Locale(mLang);
        Locale.setDefault(mNewLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = mNewLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        try {
            FileWriter wr = new FileWriter(langFile);
            wr.write(config.locale.toString());
            wr.close();
        } catch (IOException e) {
        }
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    private void AddDot() {
        if (pole.getText().length() == 0) {
            pole.append("0.");
        } else {
            boolean contain_dot = pole.getText().toString().contains(".");
            if (!contain_dot) pole.append(".");
        }
    }

    private boolean clearAll() {
        pole.setText("");
        result = 0;
        first = 0;
        second = 0;
        last_operation = "";
        Toast.makeText(getBaseContext(), R.string.area_cleared, Toast.LENGTH_SHORT).show();
        return true;
    }

    private void Delete() {
        String old = pole.getText().toString();
        switch (old.length()) {
            case 0:
            case 1:
                pole.setText("0");
                return;
            default: {
                pole.setText(old.substring(0, old.length() - 1));
                return;
            }
        }

    }

    private void NumberClick(View view) {
        String number = ((Button) view).getText().toString();
        String input = pole.getText().toString();
        if (input.length() != 0) {
            if (input.contains("0.") && input.charAt(0) == '0') {
                input = input.substring(0, input.length());
            } else if (input.charAt(0) == '0') {
                input = input.substring(1, input.length());
            }
        }
        pole.setText(input + number);
    }

    private String last_operation = "";
    private void CalculateWithTwoArgs(View v) {
        try {
            String input = pole.getText().toString();
            String operation = ((Button) v).getText().toString();
            pole.setText("");
            switch (operation) {
                case "+":
                case "-":
                    if (input.length() == 0) {
                        first = 0;
                        pole.setText("-");
                        last_operation = operation;
                        break;
                    }
                case "*":
                case "/":
                case "POWER":
                    first = Double.parseDouble(input);
                    result = first;
                    last_operation = operation;
                    break;

                case "=": {
                    second = Double.parseDouble(input);
                    switch (last_operation) {
                        case "+":
                            result = first + second;
                            break;
                        case "-":
                            if (first == 0) {
                                result = second;
                            } else {
                                result = first - second;
                            }
                            break;
                        case "*":
                            result = first * second;
                            break;
                        case "/":
                            if (second != 0) {
                                result = first / second;
                            } else {
                                clearAll();
                                Toast.makeText(this, getString(R.string.divByZero), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "POWER":
                            result = Math.pow(first, second);
                            break;
                        case "":
                            return;
                    }
                    last_operation = "";
                    first = result;
                    pole.setText(String.valueOf(result));
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CalculateWithOneArg(View v) {
        try {
            String input = pole.getText().toString();
            String operation = ((Button) v).getText().toString();
            switch (operation) {
                case "SIN":
                    result = Math.sin(Double.parseDouble(input));
                    break;
                case "COS":
                    result = Math.cos(Double.parseDouble(input));
                    break;
                case "TAN":
                    result = Math.tan(Double.parseDouble(input));
                    break;
                case "CTAN":
                    result = 1 / Math.tan(Double.parseDouble(input));
                    break;
                case "SQRT":
                    result = Math.sqrt(Double.parseDouble(input));
                    break;
                case "LOG":
                    result = Math.log(Double.parseDouble(input));
                    break;
                case "%":
                    result = Double.parseDouble(input) * 0.01;
                    break;
            }
            if (String.valueOf(result) == "NaN") {
                clearAll();
                Toast.makeText(this, getString(R.string.NaNmessage), Toast.LENGTH_SHORT).show();
            } else {
                pole.setText(String.valueOf(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble("result", result);
        outState.putDouble("first", first);
        outState.putDouble("second", second);
        outState.putString("input", pole.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
