package com.example.numad24fa_mianyunni;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // 启用返回按钮
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 加载联系人列表
        loadContacts();

        contactAdapter = new ContactAdapter(contactList);
        recyclerView.setAdapter(contactAdapter);

        FloatingActionButton fabAddContact = findViewById(R.id.fabAddContact);
        fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddContactDialog();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveContacts(); // 保存联系人列表到 SharedPreferences
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // 返回主界面
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Contact");

        final EditText nameInput = new EditText(this);
        nameInput.setHint("Name");
        final EditText phoneInput = new EditText(this);
        phoneInput.setHint("Phone Number");
        phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(nameInput);
        layout.addView(phoneInput);
        builder.setView(layout);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();
                if (!name.isEmpty() && !phone.isEmpty()) {
                    addContact(name, phone);
                } else {
                    Toast.makeText(ContactsActivity.this, "Please enter both name and phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addContact(String name, String phone) {
        Contact newContact = new Contact(name, phone);
        contactList.add(newContact);
        contactAdapter.notifyItemInserted(contactList.size() - 1);

        Snackbar.make(recyclerView, "Contact added", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contactList.remove(newContact);
                        contactAdapter.notifyItemRemoved(contactList.size());
                    }
                }).show();
    }

    // 保存联系人列表到 SharedPreferences
    private void saveContacts() {
        SharedPreferences sharedPreferences = getSharedPreferences("contacts_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(contactList); // 将列表转换为 JSON 格式
        editor.putString("contacts_list", json);
        editor.apply();
    }

    // 从 SharedPreferences 加载联系人列表
    private void loadContacts() {
        SharedPreferences sharedPreferences = getSharedPreferences("contacts_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("contacts_list", null);

        if (json != null) {
            Type type = new TypeToken<List<Contact>>() {}.getType();
            contactList = gson.fromJson(json, type); // 将 JSON 转换回 List<Contact>
        } else {
            contactList = new ArrayList<>(); // 如果没有数据，则创建一个新的列表
        }
    }
}
