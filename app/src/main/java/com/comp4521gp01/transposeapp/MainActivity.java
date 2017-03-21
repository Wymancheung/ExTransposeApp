package com.comp4521gp01.transposeapp;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Environment;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import android.app.ListActivity;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView debug;
    private Toolbar toolbar;
    private ArrayAdapter<String> listAdapter;
    private String[] list = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};

    ImageView imageView;
    Button btnProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        debug = (TextView) findViewById(R.id.debug);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        listView = (ListView) findViewById(R.id.listview);
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(listAdapter);

        setSupportActionBar(toolbar);
        FloatingActionButton mFab_Cam = (FloatingActionButton) findViewById(R.id.camera_fab);
        FloatingActionButton mFab_Gal = (FloatingActionButton) findViewById(R.id.gallery_fab);
        mFab_Cam.setOnClickListener(clickListener);
        mFab_Gal.setOnClickListener(clickListener);

        imageView = (ImageView) findViewById(R.id.imageView);
        btnProcess = (Button) findViewById(R.id.button);

        final Bitmap bitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.test
        );
        //imageView.setImageBitmap(bitmap);

        btnProcess.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                TextRecognizer textRecognizer =  new TextRecognizer.Builder(getApplicationContext()).build();
                if(!textRecognizer.isOperational()){
                    Log.e("ERROR", "Detector dependencies are not yet availble");
                }else{
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = textRecognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i=0; i<items.size();i++){
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }
                    debug.setText(stringBuilder.toString());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.camera_fab:
                    debug.setText("Camera");
                    break;
                case R.id.gallery_fab:
                    debug.setText("Gallery");
                    break;
            }
        }
    };
}
