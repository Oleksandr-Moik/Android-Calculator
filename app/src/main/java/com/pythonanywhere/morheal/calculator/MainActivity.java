package com.pythonanywhere.morheal.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private double result = 0;
    private TextView pole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pole = (TextView) findViewById(R.id.pole);
        if (result != 0) pole.setText(String.valueOf(result));
        else pole.setText("");


        setButtonsClick();
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
            case R.id.clear:
                pole.setText("");
                result = 0;
                Toast.makeText(getBaseContext(), R.string.area_cleared, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setButtonsClick() {
        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberClick(v);
            }
        });

        findViewById(R.id.button_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean contain_dot = pole.getText().toString().contains(".");
                if (!contain_dot) pole.append(".");
            }
        });

        findViewById(R.id.button_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintResult();
            }
        });

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClick();
            }
        });
        findViewById(R.id.button_sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubtractClick();
            }
        });

//        findViewById()

    }

    private void setLocale(String mLang) {
        Locale mNewLocale = new Locale(mLang);
        Locale.setDefault(mNewLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = mNewLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);
    }

    private void NumberClick(View view) {
        TextView pole = (TextView) findViewById(R.id.pole);
        pole.append(((Button) view).getText());
    }

    private void PrintResult() {
        pole.setText(String.valueOf(result));
    }

    private void AddClick() {
        double input = Double.parseDouble(pole.getText().toString());
        pole.setText("");
        result += input;
    }

    private void SubtractClick() {
        double input = Double.parseDouble(pole.getText().toString());
        pole.setText("");
        result -= input;
    }

    private void MultipleClick() {
        double input = Double.parseDouble(pole.getText().toString());
        pole.setText("");
        if (result != 0) result *= input;
        else result = input;
    }

    private void DivideClick() {
        double input = Double.parseDouble(pole.getText().toString());
        pole.setText("");
        if (result != 0) {
            result /= input;
        } else result = input;
    }

}
