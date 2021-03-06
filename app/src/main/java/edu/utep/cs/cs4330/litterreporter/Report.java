package edu.utep.cs.cs4330.litterreporter;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Report extends AppCompatActivity {
    String city;
    String state;
    String address;
    String id;
    String body;
    EditText summary;
    EditText name;
    EditText phone;
    Random random;
    Button capture;
    File pic;
    boolean isPicture;
    String picName;
    String directory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Button submit=(Button)findViewById(R.id.submit);
        Button cancel=(Button)findViewById(R.id.cancel);
        capture=(Button)findViewById(R.id.capture);
        summary=(EditText)findViewById(R.id.summary);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        Bundle extras = getIntent().getExtras();
        isPicture=false;
        directory="/storage/3631-3663/DCIM/Camera/";
        random=new Random();

        if (extras != null) {
            city = extras.getString("city");
            state=extras.getString("state");
            address=extras.getString("address");
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(summary.getText().toString()!=null&&name.getText().toString()!=null&&phone.getText().toString()!=null) {
                    id= Integer.toString(random.nextInt(10000-1000)+1000);
                    body="A report has been filed at "+city+" "+state+" "+address+"\n"+"Desctription:\n"+summary.getText().toString()+"\n"+"Call back info:\nNumber :"+phone.getText().toString()+"  "+"Name: "+name.getText().toString()+"\n\nID:"+id;
                    Intent i = new Intent(Intent.ACTION_SEND);
                    //i.setType("message/rfc822");
                    i.setType("applicatin/image");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"thomaspayan91@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Litter Report id:"+id);
                    i.putExtra(Intent.EXTRA_TEXT   , body);
                    if(isPicture) {
                        String filePath=pic.getAbsolutePath();
                        Uri uPath=Uri.parse("file://"+filePath);
                        i.putExtra(Intent.EXTRA_STREAM, uPath);
                    }
                    try {
                        startActivity(Intent.createChooser(i, "Report Sent"));

                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Report.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
               }else{
                    toast("Fields incomplete");
                }
            }

        });
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    captureIncident();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isPicture=true;
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //stuff for canceling submission
            }

        });
    }
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void captureIncident() throws IOException {
        picName=Integer.toString(random.nextInt(10000-1000)+1000);
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES) ;
        pic =  File.createTempFile(picName,".jpg",dir);
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile(pic));
        startActivityForResult(i,1);

    }
}
