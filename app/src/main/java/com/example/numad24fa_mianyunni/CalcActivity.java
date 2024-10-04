package com.example.numad24fa_mianyunni;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalcActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private StringBuilder input = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc); // 关联到你的 XML 布局文件

        tvDisplay = findViewById(R.id.tv_display);

        // 数字按钮 (0-9)
        int[] numberButtonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2,
                R.id.btn_3, R.id.btn_4, R.id.btn_5,
                R.id.btn_6, R.id.btn_7, R.id.btn_8,
                R.id.btn_9
        };
        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    input.append(button.getText().toString()); // 将数字追加到输入框
                    tvDisplay.setText(input.toString()); // 显示当前输入
                }
            });
        }

        // 运算符按钮 (+, -, x)
        int[] operatorButtonIds = {R.id.btn_plus, R.id.btn_minus, R.id.btn_multiply};
        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    input.append(" " + button.getText().toString() + " "); // 添加运算符和空格以便清晰显示
                    tvDisplay.setText(input.toString());
                }
            });
        }

        // "=" 按钮 - 计算表达式
        Button btnEquals = findViewById(R.id.btn_equals);
        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateExpression();
            }
        });

        // "x" 按钮 - 删除最后一个字符
        Button btnClear = findViewById(R.id.btn_multiply); // 假设“x”是删除键
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 0) {
                    input.deleteCharAt(input.length() - 1); // 删除最后一个字符
                    tvDisplay.setText(input.toString());
                }
            }
        });


    }

    // 计算输入表达式
    private void evaluateExpression() {
        try {
            Expression expression = new ExpressionBuilder(input.toString()).build();
            double result = expression.evaluate(); // 计算结果
            tvDisplay.setText(String.valueOf(result));
            input.setLength(0); // 计算完后清空输入
            input.append(result); // 保存结果以便后续计算
        } catch (Exception e) {
            tvDisplay.setText("Error"); // 如果出现计算错误
        }
    }


}
