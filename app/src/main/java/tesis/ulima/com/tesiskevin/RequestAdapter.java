package tesis.ulima.com.tesiskevin;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tesis.ulima.com.tesiskevin.Afinidad.Usuario;
import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    List<JSONObject> l =new ArrayList<>();
    List<DataSnapshot> l2 =new ArrayList<>();
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

    public RequestAdapter(List<JSONObject> l,List<DataSnapshot> l2) {
        this.l = l;
        this.l2 = l2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        getRequest();
//        final JSONObject o=l.get(position);
        final DataSnapshot ds=l2.get(position);
        final HashMap<String,Object> request=(HashMap<String,Object>)ds.getValue();

//        if(request.get("estado")=="0") {
//            holder.card.setVisibility(View.VISIBLE);
//            holder.request_linear.setVisibility(View.VISIBLE);
//        }else{
//            holder.card.setVisibility(View.GONE);
//            holder.request_linear.setVisibility(View.GONE);
//        }
//        holder.name.setText((String)o.get("nombre"));
        holder.name.setText((String)request.get("nombre"));
//        holder.direction.setText((String)o.get("direccion"));
        holder.direction.setText((String)request.get("direccion"));
//        holder.afinidad.setText((String)o.get("afinidad")+"%");
        holder.afinidad.setText((String)request.get("afinidad"));
//        System.out.println(o.get("estado"));
//        if(o.get("estado").equals("1")){
//            holder.btn_cancel.setVisibility(View.GONE);
//            holder.btn_accept.setVisibility(View.GONE);
//        }

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
//                updateRequest(o.get("id").toString(),"1");
                updateRequestFireBase(ds.getKey(),"1");
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
//                updateRequest(o.get("id").toString(),"2");
                updateRequestFireBase(ds.getKey(),"2");
            }
        });
    }

    @Override
    public int getItemCount() {
        if(l2==null){
            return 0;
        }else {
            return l2.size();
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

    public void updateRequest(String request,String status){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://espacioseguro.pe/php_connection/kevin/updateRequest.php?request="+request+"&estado="+status;
        System.out.println(url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("updateRequest: "+response);
                        md.dismiss();
                        getRequest();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getRequest(){
        md=new MaterialDialog.Builder(context)
            .content("Cargando Soliticudes")
            .progress(true,0)
            .cancelable(false)
            .backgroundColor(Color.WHITE)
            .contentColor(Color.BLACK)
            .show();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://espacioseguro.pe/php_connection/kevin/getVolunteerRequest.php?volunteer="+u.getId();
        System.out.println(url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("getRequest: "+response);
                        JSONParser jp = new JSONParser();
                        try {
                            JSONArray ja=(JSONArray)jp.parse(response);
                            l.clear();
                            for(int i=0;i<ja.size();i++){
                                l.add((JSONObject)ja.get(i));
                            }
                            RequestAdapter.this.notifyDataSetChanged();
                            md.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(context,"Intente luego", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            md.dismiss();
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

    public void updateRequestFireBase(String key,String status){
        myRef.child(key).child("estado").setValue(Integer.parseInt(status));
        md.dismiss();
        if(status=="1"){
            Toast.makeText(context, "Solicitud aceptada", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Solicitud rechazada", Toast.LENGTH_SHORT).show();
        }

    }
}
