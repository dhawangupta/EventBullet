package br.liveo.ndrawer.ui.activity;


    import java.util.ArrayList;
    import java.util.Timer;
    import java.util.TimerTask;
    import java.util.concurrent.ExecutionException;

    import android.app.ActionBar;
    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v7.app.ActionBarActivity;
    import android.support.v7.app.AppCompatActivity;
    import android.view.Window;

    import br.liveo.ndrawer.HelperMethods;
    import br.liveo.ndrawer.MongoLabPlace.GetPlacesAsyncTask;
    import br.liveo.ndrawer.MongoLabPlace.Place;
    import br.liveo.ndrawer.R;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                finish();
                Intent intent = new Intent(MainActivity.this,HomePage.class);
                startActivity(intent);
            }
        };


        Timer timer = new Timer();
        timer.schedule(task,5000);

    }


}