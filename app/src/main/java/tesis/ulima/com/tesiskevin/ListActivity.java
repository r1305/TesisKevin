package tesis.ulima.com.tesiskevin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    Context context;
    Button btn_preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        context=this;
        btn_preferences=findViewById(R.id.btn_register_preferences);
        btn_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(context, "Debe terminar con el registro de sus gustos", Toast.LENGTH_SHORT).show();
    }
}
