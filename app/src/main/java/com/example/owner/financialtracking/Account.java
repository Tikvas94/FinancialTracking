package com.example.owner.financialtracking;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Owner on 23/12/2017.
 */

public class Account {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("Account");
    final String def_pic_url= "https://firebasestorage.googleapis.com/v0/b/finantialtrackingtote.appspot.com/o/default.jpeg?alt=media&token=b7bedaab-bf69-43f9-b387-46506c61fc46";

    private String FirstName, LastName, UserName, Email, Password, Street, PhoneNumber, City, id,Type, picture;
    //Tomer
    private boolean isManager;

    public Account(){
        //for fire base
    }

    public Account(String FirstName, String LastName, String Password, String Email,
                     String street, String phoneNumber, String city,boolean isManager, String id) {
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.Email=Email;
        this.Password=Password;
        this.Street=street;
        this.PhoneNumber=phoneNumber;
        this.City=city;
        this.isManager = isManager;
        this.id=id;
        this.picture = def_pic_url ;
}

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

//    public String getImagePath(){return imagePath; }

//    public void setImagePath(String path){this.imagePath=path;}

    public String getFname() {
        return FirstName;
    }

    public void setFname(String fname) {
        this.FirstName= fname;
    }

    public String getLname() {
        return LastName;
    }

    public void setLname(String lname) {
        this.LastName = lname;
    }

    public String getUname() {
        return UserName;
    }

    public void setUname(String uname) {
        this.UserName = uname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        this.Street = street;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.City = PhoneNumber;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {this.picture = picture;}

    public void save()
    {
        DatabaseReference usersRef = ref.child(id);
        DatabaseReference c;
        c=usersRef.child("FirstName");
        c.setValue(FirstName);
        c=usersRef.child("LastName");
        c.setValue(LastName);
        c=usersRef.child("UserName");
        c.setValue(UserName);
        c=usersRef.child("Email");
        c.setValue(Email);
        c=usersRef.child("Password");
        c.setValue(Password);
        c=usersRef.child("PhoneNumber");
        c.setValue(PhoneNumber);
        c=usersRef.child("City");
        c.setValue(City);
        c=usersRef.child("Street");
        c.setValue(Street);
        c=usersRef.child("isManager");
        c.setValue(isManager);
        c=usersRef.child("Picture");
        c.setValue(picture);

    }
}
