package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User admin = new User();

        admin.email = "erenbuke2000@gmail.com";
        admin.password = "123";
        admin.tel = "5452466926";
        admin.username = "admin";
        admin.id = 0;

        User.users.add(admin);

        Button login = findViewById(R.id.button);
        Button signup = findViewById(R.id.button2);

        TextView username = findViewById(R.id.editTextTextPersonName);
        TextView password = findViewById(R.id.editTextTextPassword);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInScreen.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname = username.getText().toString();
                String pword = password.getText().toString();

                int end = 0;

                while(end > -1 && end < User.users.size()){

                    User user = User.users.get(end);

                    if(user.username.equals(uname) && user.password.equals(pword)){
                        end = -1;
                        count = 0;
                        startActivity(new Intent(MainActivity.this, MainPage.class));
                    }
                    else{
                        end++;
                    }
                }

                if(end == User.users.size() && count < 3){
                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_LONG);
                    toast.show();
                    count++;
                }
                else if(count == 3){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please sign up", Toast.LENGTH_LONG);
                    toast.show();

                    startActivity(new Intent(MainActivity.this, SignInScreen.class));
                }
            }
        });
        /*
        Uri number = Uri.parse("tel:5555555555");
        Intent call = new Intent(Intent.ACTION_DIAL, number);

        Uri web = Uri.parse("https://www.google.com");
        */
    }
}