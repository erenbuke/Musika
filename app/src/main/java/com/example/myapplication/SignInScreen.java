package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignInScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        Button signup = findViewById(R.id.button3);
        TextView username = findViewById(R.id.SUusername);
        TextView password = findViewById(R.id.SUpassword);
        TextView email = findViewById(R.id.SUemail);
        TextView tel = findViewById(R.id.SUtel);
        TextView password2 = findViewById(R.id.SUpassword2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText() != null && password.getText() != null && password2.getText() != null && email.getText() != null && tel.getText() != null){
                    String susername = username.getText().toString();
                    String spassword = password.getText().toString();
                    String spassword2 = password2.getText().toString();
                    String semail = email.getText().toString();
                    String stel = tel.getText().toString();

                    boolean control = false;
                    int i = 0;

                    while(!control && i < User.users.size()){
                        if(User.users.get(i).username.equals(susername)){
                            control = true;
                        }
                        else{
                            i++;
                        }
                    }

                    if(control){
                        Toast toast = Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else if(!spassword.equals(spassword2)){
                        Toast toast = Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else if(stel.length() != 10){
                        Toast toast = Toast.makeText(getApplicationContext(), "Telephone number not valid", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        Intent sendemail = new Intent(Intent.ACTION_SEND);

                        sendemail.putExtra(Intent.EXTRA_EMAIL, new String[]{semail});
                        sendemail.putExtra(Intent.EXTRA_SUBJECT, "uygulama kayıt");
                        sendemail.putExtra(Intent.EXTRA_TEXT, susername + "\n" + spassword + "\n" + stel);
                        sendemail.setData(Uri.parse("mailto:"));

                        Intent chooser = Intent.createChooser(sendemail, "select");

                        if(chooser.resolveActivity(getPackageManager()) != null){
                            startActivity(chooser);
                        }

                        User user = new User();

                        user.username = susername;
                        user.password = spassword;
                        user.email = semail;
                        user.tel = stel;

                        User.users.add(user);

                        end();
                    }


                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please fill all of the boxes", Toast.LENGTH_LONG);
                }

                //sendemail.setType("message/rfc822");
            }
        });
    }

    private void end(){
        this.finish();
    }
}