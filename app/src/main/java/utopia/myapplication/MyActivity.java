package utopia.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class MyActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.utopia.myapplication.myactivity.MESSAGE";
    private VideoView mVideoView;
    private EditText mEditText;
    private  RadioGroup mRadioGroup;
    private String mUri="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // add for Vitamio libs initialization
        if(!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;

        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mVideoView = (VideoView) this.findViewById(R.id.videoView);
        mEditText = (EditText) this.findViewById(R.id.video_url);
        mRadioGroup = (RadioGroup) this.findViewById(R.id.rg_url);
        mUri = getString(R.string.local_video_addr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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

    public void playVideoView(View v)
    {
        VideoView vv = mVideoView;

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) MyActivity.this.findViewById(radioButtonId);
                updateUri(radioButton);
            }

            private void updateUri(RadioButton rb)
            {
                mUri = rb.getText().toString();
                if ( mUri.equals(getString(R.string.net_stream_in)) )
                {
                    mUri = mEditText.getText().toString();
                    if (mUri.isEmpty())
                    {
                        mUri = getString(R.string.local_video_addr);
                    }
                }
            }
        });

        Uri videoUri;
        vv.setMediaController(new MediaController(this));
        videoUri = Uri.parse(mUri);
        vv.setVideoURI(videoUri);
        vv.start();
    }
}
