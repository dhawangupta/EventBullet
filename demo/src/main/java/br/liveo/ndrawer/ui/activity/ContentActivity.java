package br.liveo.ndrawer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.liveo.ndrawer.R;


public class ContentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity);

        setTitle(R.id.title);

        Intent intent = getIntent();
        int imageviewId = Integer.parseInt(intent.getExtras().getString("imageviewId").substring(9,10));
        String place_name = intent.getExtras().getString("place_name"+imageviewId);
        String place_content = intent.getExtras().getString("place_content"+imageviewId);

        TextView t1 = (TextView)findViewById(R.id.place_name);
        TextView t2 = (TextView)findViewById(R.id.place_content);

        t1.setText(place_name);
        t2.setText(place_content);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContentActivity.this, "Added to List", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
