package tesis.ulima.com.tesiskevin;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.HashMap;

import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager session;
    User u;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(getApplicationContext());
        session=new SessionManager(getApplicationContext());
        HashMap<String,String> user=session.getUserDetails();
        Gson g=new Gson();
        u=g.fromJson(user.get(SessionManager.KEY_VALUES),User.class);
        updateFcm();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.BLUE);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toolbar.setNavigationIcon(R.drawable.drawer_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(Gravity.START)){
                    drawer.closeDrawer(Gravity.START);
                    toolbar.setNavigationIcon(R.drawable.drawer_icon);
                }else{
                    drawer.openDrawer(Gravity.START);
                    toolbar.setNavigationIcon(R.drawable.left_arrow);
                }
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fr;
        fr=HomeFragment.newInstance();
        fragmentTransaction.replace(R.id.flaContenido,fr);
        fragmentTransaction.commit();
        toolbar.setTitle("Preferencias");

        if(u.getTipo_usuario()==2){
            navigationView.getMenu().getItem(1).setVisible(false);
        }

        if(u.getTipo_usuario()==1){
            navigationView.getMenu().getItem(2).setVisible(false);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fr;
        if (id == R.id.nav_preferences) {
            fr=PreferencesFragment.newInstance();
            fragmentTransaction.replace(R.id.flaContenido,fr);
            fragmentTransaction.commit();
            toolbar.setTitle("Preferencias");
        } else if (id == R.id.nav_volunteers) {
            fr=VolunteerFragment.newInstance();
            fragmentTransaction.replace(R.id.flaContenido,fr);
            fragmentTransaction.commit();
            Toast.makeText(this, "Voluntarios", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("Lista de Voluntarios");
        }else if(id==R.id.nav_requests){
            if(u.getTipo_usuario()==1){
                fr=AdultRequestFragment.newInstance();
            }else{
                fr=VolunteerRequestFragment.newInstance();
            }

            fragmentTransaction.replace(R.id.flaContenido,fr);
            fragmentTransaction.commit();
//            Toast.makeText(this, "Solicitudes", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("Solicitudes");

        }else if(id==R.id.nav_logout) {
            session.logoutUser();
        }else if(id==R.id.nav_programs){
            fr=VisitasFragment.newInstance();
            fragmentTransaction.replace(R.id.flaContenido,fr);
            fragmentTransaction.commit();
//            Toast.makeText(this, "Visitas Programadas", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("Visitas Programadas");
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        toolbar.setNavigationIcon(R.drawable.drawer_icon);
        return true;
    }

    public void updateFcm(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String params="?idUser="+u.getId()+"&fcm="+ FirebaseInstanceId.getInstance().getToken();
        String url = "https://espacioseguro.pe/php_connection/kevin/updateFCM.php"+params;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(response!="[]"){
                            u.setFcm(FirebaseInstanceId.getInstance().getToken());
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        // Access the RequestQueue through your singleton class.
        queue.add(postRequest);
    }
}
