package edu.utep.cs.cs4330.litterreporter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button cont=(Button)findViewById(R.id.cont);
        final EditText city=(EditText)findViewById(R.id.city);
        final EditText state=(EditText)findViewById(R.id.state);
        final EditText address=(EditText)findViewById(R.id.address);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(city!=null&&state!=null&&address!=null) {
                    Intent i = new Intent(MainActivity.this, Report.class);
                    Bundle extras = new Bundle();
                    extras.putString("city", city.getText().toString());
                    extras.putString("state", state.getText().toString());
                    extras.putString("address",address.getText().toString());
                    i.putExtras(extras);
                    startActivity(i);
                }else{
                   toast("Fields incomplete");
                }
            }

    });
    }
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
