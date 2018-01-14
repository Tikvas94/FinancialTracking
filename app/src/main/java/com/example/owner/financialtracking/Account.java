package com.example.owner.financialtracking;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Created by TOTE on 23/12/2017.
 *
 * This class handle the account details.
 */

public class Account {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference ref = database.getReference("Account");
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private String Street;
    private String PhoneNumber;
    private String City;
    private String id;
    private String picture;
    private boolean isManager;

//    @SuppressWarnings("unused")
//    public Account(){
//        //for fire base
//    }

    Account(Context context, String FirstName, String LastName, String Password, String Email,
            String street, String phoneNumber, String city, boolean isManager, String id) {
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.Email=Email;
        this.Password=Password;
        this.Street=street;
        this.PhoneNumber=phoneNumber;
        this.City=city;
        this.isManager = isManager;
        this.id=id;
        this.picture = context.getString(R.string.Account_default_pic_url);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    void save()
    {
        DatabaseReference usersRef = ref.child(id);
        DatabaseReference c;
        c=usersRef.child("FirstName");
        c.setValue(getFirstName());
        c=usersRef.child("LastName");
        c.setValue(getLastName());
        c=usersRef.child("Email");
        c.setValue(getEmail());
        c=usersRef.child("Password");
        c.setValue(getPassword());
        c=usersRef.child("PhoneNumber");
        c.setValue(getPhoneNumber());
        c=usersRef.child("City");
        c.setValue(getCity());
        c=usersRef.child("Street");
        c.setValue(getStreet());
        c=usersRef.child("isManager");
        c.setValue(isManager());
        c=usersRef.child("Picture");
        c.setValue(getPicture());
    }

    String getFirstName() {
        return FirstName;
    }

    String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    String getPassword() {
        return Password;
    }

    String getStreet() {
        return Street;
    }

    String getPhoneNumber() {
        return PhoneNumber;
    }

    String getCity() {
        return City;
    }

    private String getPicture() {
        return picture;
    }

    private boolean isManager() {
        return isManager;
    }
}
