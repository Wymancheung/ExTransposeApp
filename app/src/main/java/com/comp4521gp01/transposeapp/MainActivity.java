package com.comp4521gp01.transposeapp;

import java.io.File;

import android.os.Bundle;

import android.os.Environment;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton mFab_Cam = (FloatingActionButton) findViewById(R.id.camera_fab);
        FloatingActionButton mFab_Gal = (FloatingActionButton) findViewById(R.id.gallery_fab);
        mFab_Cam.setOnClickListener(clickListener);
        mFab_Gal.setOnClickListener(clickListener);
        ImageButton img = (ImageButton) findViewById(R.id.imageButton);

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
                    Intent intent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File tmpFile = new File(
                            Environment.getExternalStorageDirectory(), "image.jpg");
                    outputFileUri = Uri.fromFile(tmpFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(intent, PICK_FROM_CAMERA);
                    break;
                case R.id.gallery_fab:
                    Intent i = new Intent(Intent.ACTION_PICK, null);
                    i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(i, PICK_FROM_GALLERY);
                    break;
            }
        }
    };
}
