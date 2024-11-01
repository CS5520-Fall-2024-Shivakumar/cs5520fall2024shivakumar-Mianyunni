package com.example.numad24fa_mianyunni;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false); // 引用 item_contact.xml
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.phoneTextView.setText(contact.getPhoneNumber());

        // 单击事件 - 拨打电话
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL); // 使用 ACTION_DIAL Intent
                intent.setData(Uri.parse("tel:" + contact.getPhoneNumber())); // 设置电话号码
                v.getContext().startActivity(intent); // 启动拨号应用
            }
        });

        // 长按事件 - 编辑或删除联系人
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 显示编辑/删除选项对话框
                AlertDialog.Builder optionsBuilder = new AlertDialog.Builder(v.getContext());
                optionsBuilder.setTitle("Choose an action")
                        .setItems(new CharSequence[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) { // 编辑
                                    showEditDialog(contact, holder);
                                } else if (which == 1) { // 删除
                                    deleteContact(v, contact, holder.getAdapterPosition());
                                }
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    // 显示编辑对话框
    private void showEditDialog(Contact contact, ContactViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle("Edit Contact");

        // 创建输入框
        final EditText nameInput = new EditText(holder.itemView.getContext());
        nameInput.setText(contact.getName());
        final EditText phoneInput = new EditText(holder.itemView.getContext());
        phoneInput.setText(contact.getPhoneNumber());
        phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);

        // 布局用于放置输入框
        LinearLayout layout = new LinearLayout(holder.itemView.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(nameInput);
        layout.addView(phoneInput);
        builder.setView(layout);

        // 设置对话框按钮
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contact.setName(nameInput.getText().toString());
                contact.setPhoneNumber(phoneInput.getText().toString());
                notifyItemChanged(holder.getAdapterPosition());
                Toast.makeText(holder.itemView.getContext(), "Contact updated", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // 删除联系人
    private void deleteContact(View view, Contact contact, int position) {
        Contact removedContact = contactList.remove(position);
        notifyItemRemoved(position);

        // 显示 Snackbar 撤销删除
        Snackbar.make(view, "Contact deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contactList.add(position, removedContact);
                        notifyItemInserted(position);
                    }
                }).show();
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView phoneTextView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView); // 确保引用正确
        }
    }
}
