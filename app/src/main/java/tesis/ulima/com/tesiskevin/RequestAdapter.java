package tesis.ulima.com.tesiskevin;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    List<DataSnapshot> l =new ArrayList<>();
    User u;
    SessionManager session;
    Context context;
    MaterialDialog md;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        session=new SessionManager(context);
        HashMap<String,String> user=session.getUserDetails();
        Gson g=new Gson();
        u=g.fromJson(user.get(SessionManager.KEY_VALUES),User.class);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("request");
        return new RequestAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request,parent,false));
    }

    public RequestAdapter(List<DataSnapshot> l) {
        this.l = l;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataSnapshot ds=l.get(position);
        final HashMap<String,Object> request=(HashMap<String,Object>)ds.getValue();

        holder.name.setText((String)request.get("nombre"));
        holder.direction.setText((String)request.get("direccion"));
        holder.afinidad.setText((String)request.get("afinidad"));

        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                md=new MaterialDialog.Builder(context)
                        .content("Aceptando")
                        .progress(true,0)
                        .cancelable(false)
                        .backgroundColor(Color.WHITE)
                        .contentColor(Color.BLACK)
                        .show();
                updateRequestFireBase(ds.getKey(),"1",request.get("idAdulto").toString());
            }
        });

        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                md=new MaterialDialog.Builder(context)
                        .content("Cancelando")
                        .progress(true,0)
                        .cancelable(false)
                        .backgroundColor(Color.WHITE)
                        .contentColor(Color.BLACK)
                        .show();
                updateRequestFireBase(ds.getKey(),"2",request.get("idAdulto").toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(l==null){
            return 0;
        }else {
            return l.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,direction,afinidad;
        Button btn_cancel,btn_accept;
        CardView card;
        LinearLayout request_linear;

        private ViewHolder(View itemView) {
            super(itemView);
            request_linear=itemView.findViewById(R.id.request_linear);
            card=itemView.findViewById(R.id.request_card_view);
            name=itemView.findViewById(R.id.request_name);
            direction=itemView.findViewById(R.id.request_direction);
            afinidad=itemView.findViewById(R.id.request_afinidad);
            btn_cancel=itemView.findViewById(R.id.btn_cancel_request);
            btn_accept=itemView.findViewById(R.id.btn_accept_request);
        }
    }

    public void updateRequestFireBase(String key,String status,String adult){
        myRef.child(key).child("estado").setValue(Integer.parseInt(status));
        md.dismiss();
        if(status=="1"){
            sendNotification(adult);
            Toast.makeText(context, "Solicitud aceptada", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Solicitud rechazada", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendNotification(String adult){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://espacioseguro.pe/php_connection/kevin/acceptRequest.php?idUser="+Integer.parseInt(adult);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.println(response);
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
