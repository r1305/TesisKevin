package tesis.ulima.com.tesiskevin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tesis.ulima.com.tesiskevin.Utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new SessionManager(this);
        session.checkLogin();
    }
}
