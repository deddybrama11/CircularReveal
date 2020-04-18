package com.bram.circularreveal;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {
    Animation frombottom,fromtop;
    EditText ed1,ed2;
    Button login;
    TextView tv;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_view);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        login = findViewById(R.id.btnLogin);
        tv = findViewById(R.id.tvi2);
        ed1 = findViewById(R.id.etUsername);
        ed2 = findViewById(R.id.etPassowrd);

        login.startAnimation(frombottom);
        tv.startAnimation(fromtop);
        ed1.startAnimation(fromtop);
        ed2.startAnimation(fromtop);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, HomeActivity.class));
            }
        });
    }
}
