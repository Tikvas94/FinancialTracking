package com.example.owner.financialtracking;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button Login = (Button) findViewById(R.id.LoginActivityButton);
        final Button Readme = (Button) findViewById(R.id.Readme);
        final Button Map = (Button) findViewById(R.id.Map);

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(LoginIntent);
            }
        });

        Readme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent ReadmeIntent = new Intent(MainActivity.this, ReadmeActivity.class);
                MainActivity.this.startActivity(ReadmeIntent);
            }
        });

        Map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent MapIntent = new Intent(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity(MapIntent);
            }
        });
    }
}
