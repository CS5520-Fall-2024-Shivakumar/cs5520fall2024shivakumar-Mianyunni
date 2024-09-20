package com.example.numad24fa_mianyunni;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 设置窗口Insets，保持现有的代码逻辑
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 找到 "About Me" 按钮
        Button aboutMeButton = findViewById(R.id.about_me_button);

        // 设置点击事件监听器
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 显示 Toast 消息，包含名字和电子邮件
                Toast.makeText(MainActivity.this, "Mianyun Ni: ni.mi@northeastern.edu", Toast.LENGTH_LONG).show();
            }
        });
    }
}
