package com.example.myapplicationphonebookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private OnContactActionListener contactActionListener;

    public ContactAdapter(Context context, List<Contact> contacts, OnContactActionListener listener) {
        super(context, 0, contacts);
        this.contactActionListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.contactName);
        TextView phoneTextView = convertView.findViewById(R.id.contactPhone);
        TextView departmentTextView = convertView.findViewById(R.id.contactDepartment);
        Button editContactBtn = convertView.findViewById(R.id.editContactBtn);
        Button deleteContactBtn = convertView.findViewById(R.id.deleteContactBtn);

        nameTextView.setText(contact.getName());
        phoneTextView.setText(contact.getPhoneNumber());
        departmentTextView.setText(contact.getDepartment());

        // Edit Button Logic
        editContactBtn.setOnClickListener(v -> contactActionListener.onEditContact(position));

        // Delete Button Logic
        deleteContactBtn.setOnClickListener(v -> contactActionListener.onDeleteContact(position));

        return convertView;
    }

    // Interface to handle edit and delete actions
    public interface OnContactActionListener {
        void onEditContact(int position);
        void onDeleteContact(int position);
    }
}
