package com.example.myapplicationphonebookapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnContactActionListener {

    private ArrayList<Contact> contacts;
    private ContactAdapter contactAdapter;
    private ListView contactListView;
    private EditText searchContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new ArrayList<>();
        contactAdapter = new ContactAdapter(this, contacts, this);
        contactListView = findViewById(R.id.contactListView);
        contactListView.setAdapter(contactAdapter);

        searchContact = findViewById(R.id.searchContact);
        Button addContactBtn = findViewById(R.id.addContactBtn);

        // Add contact button logic
        addContactBtn.setOnClickListener(v -> showAddContactDialog());
    }

    // Method to show dialog to add a new contact
    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_contact, null);
        EditText nameInput = dialogView.findViewById(R.id.contactName);
        EditText phoneInput = dialogView.findViewById(R.id.contactPhone);
        EditText departmentInput = dialogView.findViewById(R.id.contactDepartment);

        builder.setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = nameInput.getText().toString();
                    String phone = phoneInput.getText().toString();
                    String department = departmentInput.getText().toString();
                    contacts.add(new Contact(name, phone, department));
                    contactAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Edit contact logic
    @Override
    public void onEditContact(int position) {
        Contact contact = contacts.get(position);
        showEditContactDialog(contact, position);
    }

    private void showEditContactDialog(Contact contact, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_contact, null);
        EditText nameInput = dialogView.findViewById(R.id.contactName);
        EditText phoneInput = dialogView.findViewById(R.id.contactPhone);
        EditText departmentInput = dialogView.findViewById(R.id.contactDepartment);

        nameInput.setText(contact.getName());
        phoneInput.setText(contact.getPhoneNumber());
        departmentInput.setText(contact.getDepartment());

        builder.setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = nameInput.getText().toString();
                    String newPhone = phoneInput.getText().toString();
                    String newDepartment = departmentInput.getText().toString();

                    contact.setName(newName);
                    contact.setPhoneNumber(newPhone);
                    contact.setDepartment(newDepartment);

                    contacts.set(position, contact);
                    contactAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Delete contact logic
    @Override
    public void onDeleteContact(int position) {
        contacts.remove(position);
        contactAdapter.notifyDataSetChanged();
    }
}
