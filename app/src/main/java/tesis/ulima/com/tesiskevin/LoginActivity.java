package tesis.ulima.com.tesiskevin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText usuario,clave;
    Context context;
    MaterialDialog md;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=this;
        session=new SessionManager(getApplicationContext());
        usuario=findViewById(R.id.login_user);
        clave=findViewById(R.id.login_psw);
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md=new MaterialDialog.Builder(context)
                        .content("Validando")
                        .progress(true,0)
                        .cancelable(false)
                        .backgroundColor(Color.WHITE)
                        .contentColor(Color.BLACK)
                        .show();
                login(usuario.getText().toString(),clave.getText().toString());
            }
        });
    }

    public void login(final String user,final String psw){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://espacioseguro.pe/php_connection/kevin/login.php?usuario="+user+"&clave="+psw;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONParser jp = new JSONParser();
                        try {
                            JSONArray a=(JSONArray)jp.parse(response);
                            if(a.size()!=0){
                                md.dismiss();
                                JSONObject o=(JSONObject)a.get(0);
                                session.createLoginSession(o.toJSONString());
                                Gson gson=new Gson();
                                gson.fromJson(o.toJSONString(),User.class);
                                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                md.dismiss();
                                Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {

                            md.dismiss();
                            Toast.makeText(getApplicationContext(),"Credenciales incorrectas",Toast.LENGTH_LONG).show();
                            System.out.println(e);
                            System.out.println(response);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                md.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
