package com.example.numad24fa_mianyunni;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        // 设置窗口Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 找到 "About Me" 按钮
        Button aboutMeButton = findViewById(R.id.about_me_button);
        // 设置点击事件监听器，跳转到 AboutMeActivity
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
            }
        });

        // 找到 "Quic Calc" 按钮
        Button quicCalcButton = findViewById(R.id.btn_quic_calc);
        // 设置点击事件监听器，跳转到 CalcActivity
        quicCalcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalcActivity.class);
                startActivity(intent);
            }
        });

        // 找到 "Contacts Collector" 按钮
        Button contactsCollectorButton = findViewById(R.id.contactsCollectorButton);
        // 设置点击事件监听器，跳转到 ContactsActivity
        contactsCollectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });
    }
}
