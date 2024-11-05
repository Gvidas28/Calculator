package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView TvResult, TvSolution;
    MaterialButton buttonC, buttonRoot, buttonSquare, buttonNegate, buttonDivide, buttonDuplicate, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TvResult = findViewById(R.id.TvResult);
        TvSolution = findViewById(R.id.TvSolution);

        assignId(buttonC, R.id.buttonC);
        assignId(buttonRoot, R.id.buttonRoot);
        assignId(buttonSquare, R.id.buttonSquare);
        assignId(buttonNegate, R.id.buttonNegate);
        assignId(buttonDivide, R.id.buttonDivide);
        assignId(buttonDuplicate, R.id.buttonDuplicate);
        assignId(buttonPlus, R.id.buttonPlus);
        assignId(buttonMinus, R.id.buttonMinus);
        assignId(buttonEquals, R.id.buttonEquals);
        assignId(button0, R.id.button0);
        assignId(button1, R.id.button1);
        assignId(button2, R.id.button2);
        assignId(button3, R.id.button3);
        assignId(button4, R.id.button4);
        assignId(button5, R.id.button5);
        assignId(button6, R.id.button6);
        assignId(button7, R.id.button7);
        assignId(button8, R.id.button8);
        assignId(button9, R.id.button9);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = TvSolution.getText().toString();

        if (buttonText.equals("C")) {
            TvSolution.setText("");
            TvResult.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            String finalResult = getResult(dataToCalculate);
            TvResult.setText(finalResult);
            TvSolution.setText(finalResult);
            return;
        }

        if (buttonText.equals("√")) {
            dataToCalculate = "Math.sqrt(" + dataToCalculate + ")";
        }

        else if (buttonText.equals("x²")) {
            dataToCalculate = "(" + dataToCalculate + ") * (" + dataToCalculate + ")";
        }


        else if (buttonText.equals("+/-")) {
            if (!dataToCalculate.isEmpty()) {
                if (dataToCalculate.startsWith("-")) {
                    dataToCalculate = dataToCalculate.substring(1);
                } else {
                    dataToCalculate = "-" + dataToCalculate;
                }
            }
        }

        else {
            dataToCalculate += buttonText;
        }

        TvSolution.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        TvResult.setText(finalResult);
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1); // Important for Android compatibility
            Scriptable scriptable = context.initStandardObjects();
            data = data.replaceAll("×", "*").replaceAll("÷", "/");

            String finalResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString();
            return finalResult;
        } catch (ArithmeticException ae) {
            return "Error";
        } catch (Exception e) {
            return "Invalid Input";
        } finally {
            Context.exit();
        }
    }
}
