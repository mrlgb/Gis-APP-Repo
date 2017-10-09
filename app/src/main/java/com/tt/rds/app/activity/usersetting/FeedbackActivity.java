package com.tt.rds.app.activity.usersetting;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.tt.rds.app.R;
import com.tt.rds.app.activity.BaseSaveActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private static final String TAG = FeedbackActivity.class.getSimpleName();

    EditText et_content,et_contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initToolBar();
        initViews();
    }

    private void initToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackActivity.this.onBackPressed();

            }
        });
    }
    private void initViews(){
        et_content =(EditText)findViewById(R.id.fb_content);
        et_contact =(EditText)findViewById(R.id.fb_contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_commit,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId();
        if(itemId==R.id.commit_activity){
            String content=et_content.getText().toString();
            String contact=et_contact.getText().toString();
            try {
                JSONObject json = new JSONObject();
                json.put("content",content);
                json.put("contact",contact);
            }
            catch (Exception e){
                Log.e(TAG,e.toString());
            }
            //TODO 提交给服务器

            FeedbackActivity.this.onBackPressed();
        }
        return true;
    }
}
