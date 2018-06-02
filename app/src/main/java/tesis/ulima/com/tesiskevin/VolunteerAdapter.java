package tesis.ulima.com.tesiskevin;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tesis.ulima.com.tesiskevin.Afinidad.Model;
import tesis.ulima.com.tesiskevin.Afinidad.Usuario;
import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.ViewHolder>{

    List<JSONObject> l =new ArrayList<>();
    User u;
    SessionManager session;

    private Usuario volunteer;
    private Usuario adult;

    Context context;

    private float afinidad;
    MaterialDialog md;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        session=new SessionManager(context);
        HashMap<String,String> user=session.getUserDetails();
        Gson g=new Gson();
        u=g.fromJson(user.get(SessionManager.KEY_VALUES),User.class);
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_volunteer,parent,false));
    }

    public VolunteerAdapter(List<JSONObject> l) {
        this.l = l;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final JSONObject o=l.get(position);
        holder.name.setText((String)o.get("nombre"));
        holder.afinidad.setText(String.valueOf(afinidad)+"%");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    md=new MaterialDialog.Builder(context)
                            .content("Cargando")
                            .progress(true,0)
                            .cancelable(false)
                            .backgroundColor(Color.WHITE)
                            .contentColor(Color.BLACK)
                            .show();
                    getVolunteerPreferences(o.get("id").toString(),position);
                }catch (Exception e){
                    Toast.makeText(context, "Error al calcular afinidad: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.afinidad.getText().equals("0.0%")){
                    Toast.makeText(context,"Haga click sobre el usuario para ver su afinidad antes de enviarle una solicitud",Toast.LENGTH_LONG).show();
                }else{
                    FragmentManager fm = ((HomeActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    Fragment fr;
                    fr=VolunteerDetail.newInstance(o.get("id").toString(),String.valueOf(holder.afinidad.getText()));
                    fragmentTransaction.replace(R.id.flaContenido,fr);
                    fragmentTransaction.commit();
                }
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

        TextView name,afinidad;
        ImageView arrow;

        private ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.volunteer_name);
            afinidad=itemView.findViewById(R.id.volunteer_afinidad);
            arrow=itemView.findViewById(R.id.volunteer_arrow);
        }
    }

    public void getVolunteerPreferences(final String volunteer_id,final int position){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://espacioseguro.pe/php_connection/kevin/getPreferences.php?idUsuario="+Integer.valueOf(volunteer_id);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONParser jp = new JSONParser();
                        try {
                            JSONArray a=(JSONArray)jp.parse(response);
                            JSONObject o=(JSONObject)a.get(0);
                            JSONObject o2=(JSONObject)jp.parse((String)o.get("preferencias"));
                            if(a.size()!=0){
                                volunteer=new Usuario(
                                        Integer.valueOf(o2.get("chk_guitarra").toString()),
                                        Integer.valueOf(o2.get("chk_timbales").toString()),
                                        Integer.valueOf(o2.get("chk_piano").toString()),
                                        Integer.valueOf(o2.get("chk_violin").toString()),
                                        Integer.valueOf(o2.get("chk_saxofon").toString()),
                                        Integer.valueOf(o2.get("chk_trompeta").toString()),
                                        Integer.valueOf(o2.get("chk_bateria").toString()),
                                        Integer.valueOf(o2.get("chk_tejido").toString()),
                                        Integer.valueOf(o2.get("chk_costura").toString()),
                                        Integer.valueOf(o2.get("chk_manualidades").toString()),
                                        Integer.valueOf(o2.get("chk_escultura").toString()),
                                        Integer.valueOf(o2.get("chk_ajedrez").toString()),
                                        Integer.valueOf(o2.get("chk_ludo").toString()),
                                        Integer.valueOf(o2.get("chk_damas").toString()),
                                        Integer.valueOf(o2.get("chk_damas_chinas").toString()),
                                        Integer.valueOf(o2.get("chk_ingles").toString()),
                                        Integer.valueOf(o2.get("chk_aleman").toString()),
                                        Integer.valueOf(o2.get("chk_frances").toString()),
                                        Integer.valueOf(o2.get("chk_portugues").toString()),
                                        Integer.valueOf(o2.get("chk_italiano").toString()),
                                        Integer.valueOf(o2.get("chk_hp").toString()),
                                        Integer.valueOf(o2.get("chk_hu").toString()),
                                        Integer.valueOf(o2.get("chk_hc").toString()),
                                        Integer.valueOf(o2.get("chk_ha").toString()),
                                        Integer.valueOf(o2.get("chk_cine").toString()),
                                        Integer.valueOf(o2.get("chk_teatro").toString()),
                                        Integer.valueOf(o2.get("chk_pintura").toString()),
                                        Integer.valueOf(o2.get("chk_arquitectura").toString()),
                                        Integer.valueOf(o2.get("chk_novela").toString()),
                                        Integer.valueOf(o2.get("chk_drama").toString()),
                                        Integer.valueOf(o2.get("chk_historia").toString()),
                                        Integer.valueOf(o2.get("chk_autoayuda").toString()),
                                        Integer.valueOf(o2.get("chk_thriller").toString()),
                                        Integer.valueOf(o2.get("chk_taichi").toString()),
                                        Integer.valueOf(o2.get("chk_yoga").toString()),
                                        Integer.valueOf(o2.get("chk_meditacion").toString()),
                                        Integer.valueOf(o2.get("chk_rmp").toString()),
                                        Integer.valueOf(o2.get("chk_baile").toString()),
                                        Integer.valueOf(o2.get("chk_estiramientos").toString()),
                                        Integer.valueOf(o2.get("chk_caminata").toString()),
                                        Integer.valueOf(o2.get("chk_gimnasia").toString()),
                                        Integer.valueOf(o2.get("chk_biodanza").toString())
                                );
                                getAdultPreferences(position);
//                                md.dismiss();
                            }else{
//                                md.dismiss();
                                Toast.makeText(context,"No se pudo obtener sus preferencias voluntario",Toast.LENGTH_LONG).show();

                            }
                        } catch (Exception e) {
//                            md.dismiss();
                            Toast.makeText(context,"voluntario: "+e.toString(),Toast.LENGTH_LONG).show();
                        }
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

    public void getAdultPreferences(final int position){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://espacioseguro.pe/php_connection/kevin/getPreferences.php?idUsuario="+u.getId();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        JSONParser jp = new JSONParser();
                        try {
                            JSONArray a=(JSONArray)jp.parse(response);
                            System.out.println(a);
                            JSONObject o=(JSONObject)a.get(0);
                            System.out.println(o);
                            JSONObject o2=(JSONObject)jp.parse((String)o.get("preferencias"));
                            System.out.println(o2);
                            if(a.size()!=0){
                                try{
                                    Model model=new Model();
                                    adult=new Usuario(
                                            Integer.valueOf(o2.get("chk_guitarra").toString()),
                                            Integer.valueOf(o2.get("chk_timbales").toString()),
                                            Integer.valueOf(o2.get("chk_piano").toString()),
                                            Integer.valueOf(o2.get("chk_violin").toString()),
                                            Integer.valueOf(o2.get("chk_saxofon").toString()),
                                            Integer.valueOf(o2.get("chk_trompeta").toString()),
                                            Integer.valueOf(o2.get("chk_bateria").toString()),
                                            Integer.valueOf(o2.get("chk_tejido").toString()),
                                            Integer.valueOf(o2.get("chk_costura").toString()),
                                            Integer.valueOf(o2.get("chk_manualidades").toString()),
                                            Integer.valueOf(o2.get("chk_escultura").toString()),
                                            Integer.valueOf(o2.get("chk_ajedrez").toString()),
                                            Integer.valueOf(o2.get("chk_ludo").toString()),
                                            Integer.valueOf(o2.get("chk_damas").toString()),
                                            Integer.valueOf(o2.get("chk_damas_chinas").toString()),
                                            Integer.valueOf(o2.get("chk_ingles").toString()),
                                            Integer.valueOf(o2.get("chk_aleman").toString()),
                                            Integer.valueOf(o2.get("chk_frances").toString()),
                                            Integer.valueOf(o2.get("chk_portugues").toString()),
                                            Integer.valueOf(o2.get("chk_italiano").toString()),
                                            Integer.valueOf(o2.get("chk_hp").toString()),
                                            Integer.valueOf(o2.get("chk_hu").toString()),
                                            Integer.valueOf(o2.get("chk_hc").toString()),
                                            Integer.valueOf(o2.get("chk_ha").toString()),
                                            Integer.valueOf(o2.get("chk_cine").toString()),
                                            Integer.valueOf(o2.get("chk_teatro").toString()),
                                            Integer.valueOf(o2.get("chk_pintura").toString()),
                                            Integer.valueOf(o2.get("chk_arquitectura").toString()),
                                            Integer.valueOf(o2.get("chk_novela").toString()),
                                            Integer.valueOf(o2.get("chk_drama").toString()),
                                            Integer.valueOf(o2.get("chk_historia").toString()),
                                            Integer.valueOf(o2.get("chk_autoayuda").toString()),
                                            Integer.valueOf(o2.get("chk_thriller").toString()),
                                            Integer.valueOf(o2.get("chk_taichi").toString()),
                                            Integer.valueOf(o2.get("chk_yoga").toString()),
                                            Integer.valueOf(o2.get("chk_meditacion").toString()),
                                            Integer.valueOf(o2.get("chk_rmp").toString()),
                                            Integer.valueOf(o2.get("chk_baile").toString()),
                                            Integer.valueOf(o2.get("chk_estiramientos").toString()),
                                            Integer.valueOf(o2.get("chk_caminata").toString()),
                                            Integer.valueOf(o2.get("chk_gimnasia").toString()),
                                            Integer.valueOf(o2.get("chk_biodanza").toString())
                                    );

                                    afinidad=model.calcularAfinidadTotal(adult,volunteer);
                                    VolunteerAdapter.this.notifyItemChanged(position);
                                    md.dismiss();
                                }catch (Exception e){
                                    Toast.makeText(context, "Error al calcular afinidad: "+e, Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(context,"No se pudo obtener sus preferencias adutlo",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(context,"adulto: "+e.toString(),Toast.LENGTH_LONG).show();
                        }
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
}
