package tesis.ulima.com.tesiskevin;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class PreferencesFragment extends Fragment {
    Context context;
    CheckBox
            chk_guitarra,
            chk_timbales,
            chk_piano,
            chk_violin,
            chk_saxofon,
            chk_trompeta,
            chk_bateria,
            chk_tejido,
            chk_costura,
            chk_manualidades,
            chk_escultura,
            chk_ajedrez,
            chk_ludo,
            chk_damas,
            chk_damas_chinas,
            chk_ingles,
            chk_aleman,
            chk_frances,
            chk_portugues,
            chk_italiano,
            chk_hp,
            chk_hu,
            chk_hc,
            chk_ha,
            chk_cine,
            chk_teatro,
            chk_pintura,
            chk_arquitectura,
            chk_novela,
            chk_drama,
            chk_historia,
            chk_autoayuda,
            chk_thriller,
            chk_taichi,
            chk_yoga,
            chk_meditacion,
            chk_rmp,
            chk_baile,
            chk_estiramientos,
            chk_caminata,
            chk_gimnasia,
            chk_biodanza;

    int chk_guitarra_val,
        chk_timbales_val,
        chk_piano_val,
        chk_violin_val,
        chk_saxofon_val,
        chk_trompeta_val,
        chk_bateria_val,
        chk_tejido_val,
        chk_costura_val,
        chk_manualidades_val,
        chk_escultura_val,
        chk_ajedrez_val,
        chk_ludo_val,
        chk_damas_val,
        chk_damas_chinas_val,
        chk_ingles_val,
        chk_aleman_val,
        chk_frances_val,
        chk_portugues_val,
        chk_italiano_val,
        chk_hp_val,
        chk_hu_val,
        chk_hc_val,
        chk_ha_val,
        chk_cine_val,
        chk_teatro_val,
        chk_pintura_val,
        chk_arquitectura_val,
        chk_novela_val,
        chk_drama_val,
        chk_historia_val,
        chk_autoayuda_val,
        chk_thriller_val,
        chk_taichi_val,
        chk_yoga_val,
        chk_meditacion_val,
        chk_rmp_val,
        chk_baile_val,
        chk_estiramientos_val,
        chk_caminata_val,
        chk_gimnasia_val,
        chk_biodanza_val;

    SessionManager session;
    User u;
    Button btn_guardar;

    MaterialDialog md;

    public PreferencesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PreferencesFragment newInstance() {
        PreferencesFragment fragment = new PreferencesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_preferences, container, false);
        context=getActivity();
        session=new SessionManager(context);
        HashMap<String,String> user=session.getUserDetails();
        Gson g=new Gson();
        u=g.fromJson(user.get(SessionManager.KEY_VALUES),User.class);
        getPreferences();

        btn_guardar=v.findViewById(R.id.btn_save_preferences);
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePreference();
            }
        });
        chk_guitarra=v.findViewById(R.id.chk_guitarra);
        chk_timbales=v.findViewById(R.id.chk_timbales);
        chk_piano=v.findViewById(R.id.chk_piano);
        chk_violin=v.findViewById(R.id.chk_violin);
        chk_saxofon=v.findViewById(R.id.chk_saxofon);
        chk_trompeta=v.findViewById(R.id.chk_trompeta);
        chk_bateria=v.findViewById(R.id.chk_bateria);
        chk_tejido=v.findViewById(R.id.chk_tejido);
        chk_costura=v.findViewById(R.id.chk_costura);
        chk_manualidades=v.findViewById(R.id.chk_manualidades);
        chk_escultura=v.findViewById(R.id.chk_escultura);
        chk_ajedrez=v.findViewById(R.id.chk_ajedrez);
        chk_ludo=v.findViewById(R.id.chk_ludo);
        chk_damas=v.findViewById(R.id.chk_damas);
        chk_damas_chinas=v.findViewById(R.id.chk_damas_chinas);
        chk_ingles=v.findViewById(R.id.chk_ingles);
        chk_aleman=v.findViewById(R.id.chk_aleman);
        chk_frances=v.findViewById(R.id.chk_frances);
        chk_portugues=v.findViewById(R.id.chk_portugues);
        chk_italiano=v.findViewById(R.id.chk_italiano);
        chk_hp=v.findViewById(R.id.chk_hp);
        chk_hu=v.findViewById(R.id.chk_hu);
        chk_hc=v.findViewById(R.id.chk_hc);
        chk_ha=v.findViewById(R.id.chk_ha);
        chk_cine=v.findViewById(R.id.chk_cine);
        chk_teatro=v.findViewById(R.id.chk_teatro);
        chk_pintura=v.findViewById(R.id.chk_pintura);
        chk_arquitectura=v.findViewById(R.id.chk_arquitectura);
        chk_novela=v.findViewById(R.id.chk_novela);
        chk_drama=v.findViewById(R.id.chk_drama);
        chk_historia=v.findViewById(R.id.chk_historia);
        chk_autoayuda=v.findViewById(R.id.chk_autoayuda);
        chk_thriller=v.findViewById(R.id.chk_thriller);
        chk_taichi=v.findViewById(R.id.chk_taichi);
        chk_yoga=v.findViewById(R.id.chk_yoga);
        chk_meditacion=v.findViewById(R.id.chk_meditacion);
        chk_rmp=v.findViewById(R.id.chk_rmp);
        chk_baile=v.findViewById(R.id.chk_baile);
        chk_estiramientos=v.findViewById(R.id.chk_estiramientos);
        chk_caminata=v.findViewById(R.id.chk_caminata);
        chk_gimnasia=v.findViewById(R.id.chk_gimnasia);
        chk_biodanza=v.findViewById(R.id.chk_biodanza);

        chk_guitarra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                System.out.println(compoundButton.isChecked());
                if(b){
                    chk_guitarra_val=1;
                }else{
                    chk_guitarra_val=0;
                }

            }
        });

        chk_timbales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_timbales_val=1;
                }else{
                    chk_timbales_val=0;
                }
            }
        });
        chk_piano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_piano_val=1;
                }else{
                    chk_piano_val=0;
                }
            }
        });

        chk_violin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_violin_val=1;
                }else{
                    chk_violin_val=0;
                }
            }
        });

        chk_saxofon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_saxofon_val=1;
                }else{
                    chk_saxofon_val=0;
                }
            }
        });

        chk_trompeta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_trompeta_val=1;
                }else{
                    chk_trompeta_val=0;
                }
            }
        });

        chk_bateria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_bateria_val=1;
                }else{
                    chk_bateria_val=0;
                }
            }
        });

        chk_tejido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                
                if(b){
                    chk_tejido_val=1;
                }else{
                    chk_tejido_val=0;
                }
            }
        });

        chk_costura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                
                if(b){
                    chk_costura_val=1;
                }else{
                    chk_costura_val=0;
                }
            }
        });

        chk_manualidades.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_manualidades_val=1;
                }else{
                    chk_manualidades_val=0;
                }
            }
        });

        chk_escultura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_escultura_val=1;
                }else{
                    chk_escultura_val=0;
                }
            }
        });

        chk_ajedrez.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_ajedrez_val=1;
                }else{
                    chk_ajedrez_val=0;
                }
            }
        });

        chk_ludo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_ludo_val=1;
                }else{
                    chk_ludo_val=0;
                }
            }
        });

        chk_damas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_damas_val=1;
                }else{
                    chk_damas_val=0;
                }
            }
        });

        chk_damas_chinas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_damas_chinas_val=1;
                }else{
                    chk_damas_chinas_val=0;
                }
            }
        });

        chk_ingles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_ingles_val=1;
                }else{
                    chk_ingles_val=0;
                }
            }
        });

        chk_aleman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_aleman_val=1;
                }else{
                    chk_aleman_val=0;
                }
            }
        });

        chk_frances.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_frances_val=1;
                }else{
                    chk_frances_val=0;
                }
            }
        });

        chk_portugues.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_portugues_val=1;
                }else{
                    chk_portugues_val=0;
                }
            }
        });

        chk_italiano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_italiano_val=1;
                }else{
                    chk_italiano_val=0;
                }
            }
        });

        chk_hp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_hp_val=1;
                }else{
                    chk_hp_val=0;
                }
            }
        });

        chk_hu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_hu_val=1;
                }else{
                    chk_hu_val=0;
                }
            }
        });

        chk_hc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_hc_val=1;
                }else{
                    chk_hc_val=0;
                }
            }
        });

        chk_ha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_ha_val=1;
                }else{
                    chk_ha_val=0;
                }
            }
        });

        chk_cine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_cine_val=1;
                }else{
                    chk_cine_val=0;
                }
            }
        });

        chk_teatro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_teatro_val=1;
                }else{
                    chk_teatro_val=0;
                }
            }
        });

        chk_pintura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_pintura_val=1;
                }else{
                    chk_pintura_val=0;
                }
            }
        });

        chk_arquitectura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_arquitectura_val=1;
                }else{
                    chk_arquitectura_val=0;
                }
            }
        });

        chk_novela.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_novela_val=1;
                }else{
                    chk_novela_val=0;
                }
            }
        });

        chk_drama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_drama_val=1;
                }else{
                    chk_drama_val=0;
                }
            }
        });

        chk_historia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_historia_val=1;
                }else{
                    chk_historia_val=0;
                }
            }
        });

        chk_autoayuda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_autoayuda_val=1;
                }else{
                    chk_autoayuda_val=0;
                }
            }
        });

        chk_thriller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_thriller_val=1;
                }else{
                    chk_thriller_val=0;
                }
            }
        });

        chk_taichi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_taichi_val=1;
                }else{
                    chk_taichi_val=0;
                }
            }
        });

        chk_yoga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_yoga_val=1;
                }else{
                    chk_yoga_val=0;
                }
            }
        });

        chk_meditacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_meditacion_val=1;
                }else{
                    chk_meditacion_val=0;
                }
            }
        });

        chk_rmp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_rmp_val=1;
                }else{
                    chk_rmp_val=0;
                }
            }
        });

        chk_baile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_baile_val=1;
                }else{
                    chk_baile_val=0;
                }
            }
        });

        chk_estiramientos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_estiramientos_val=1;
                }else{
                    chk_estiramientos_val=0;
                }
            }
        });

        chk_caminata.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_caminata_val=1;
                }else{
                    chk_caminata_val=0;
                }
            }
        });

        chk_gimnasia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_gimnasia_val=1;
                }else{
                    chk_gimnasia_val=0;
                }
            }
        });

        chk_biodanza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    chk_biodanza_val=1;
                }else{
                    chk_biodanza_val=0;
                }
            }
        });
        return v;
    }

    public void updatePreference(){
        md=new MaterialDialog.Builder(context)
                .content("Guardando preferencias")
                .progress(true,0)
                .cancelable(false)
                .backgroundColor(Color.WHITE)
                .contentColor(Color.BLACK)
                .show();
        JSONObject json=new JSONObject();

        json.put("chk_biodanza",chk_biodanza_val);
        json.put("chk_gimnasia",chk_gimnasia_val);
        json.put("chk_caminata",chk_caminata_val);
        json.put("chk_estiramientos",chk_estiramientos_val);
        json.put("chk_baile",chk_baile_val);
        json.put("chk_rmp",chk_rmp_val);
        json.put("chk_meditacion",chk_meditacion_val);
        json.put("chk_yoga",chk_yoga_val);
        json.put("chk_taichi",chk_taichi_val);
        json.put("chk_thriller",chk_thriller_val);
        json.put("chk_autoayuda",chk_autoayuda_val);
        json.put("chk_historia",chk_historia_val);
        json.put("chk_drama",chk_drama_val);
        json.put("chk_novela",chk_novela_val);
        json.put("chk_arquitectura",chk_arquitectura_val);
        json.put("chk_pintura",chk_pintura_val);
        json.put("chk_teatro",chk_teatro_val);
        json.put("chk_cine",chk_cine_val);
        json.put("chk_ha",chk_ha_val);
        json.put("chk_hc",chk_hc_val);
        json.put("chk_hu",chk_hu_val);
        json.put("chk_hp",chk_hp_val);
        json.put("chk_italiano",chk_italiano_val);
        json.put("chk_frances",chk_frances_val);
        json.put("chk_portugues",chk_portugues_val);
        json.put("chk_aleman",chk_aleman_val);
        json.put("chk_ingles",chk_ingles_val);
        json.put("chk_damas_chinas",chk_damas_chinas_val);
        json.put("chk_damas",chk_damas_val);
        json.put("chk_ludo",chk_ludo_val);
        json.put("chk_ajedrez",chk_ajedrez_val);
        json.put("chk_escultura",chk_escultura_val);
        json.put("chk_manualidades",chk_manualidades_val);
        json.put("chk_costura",chk_costura_val);
        json.put("chk_tejido",chk_tejido_val);
        json.put("chk_bateria",chk_bateria_val);
        json.put("chk_trompeta",chk_trompeta_val);
        json.put("chk_saxofon",chk_saxofon_val);
        json.put("chk_violin",chk_violin_val);
        json.put("chk_piano",chk_piano_val);
        json.put("chk_timbales",chk_timbales_val);
        json.put("chk_guitarra",chk_guitarra_val);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.getCache().clear();
//        String url ="https://espacioseguro.pe/php_connection/kevin/updatePreference.php?usuario="+u.getId()+"&attr="+label+"&val="+chk;
        System.out.println(json.toJSONString());
        String url ="https://espacioseguro.pe/php_connection/kevin/updatePreference.php?usuario="+u.getId()+"&prefs="+json.toString();
        // Request a string response from the provided URL.
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String data) {
                        System.out.println(data);
                        md.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                md.dismiss();
                System.out.println(error.toString());
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getPreferences(){
        final MaterialDialog md=new MaterialDialog.Builder(getActivity())
                .content("Obteniendo preferencias")
                .progress(true,0)
                .cancelable(false)
                .backgroundColor(Color.WHITE)
                .contentColor(Color.BLACK)
                .show();
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://espacioseguro.pe/php_connection/kevin/getPreferences.php?idUsuario="+u.getId();
        System.out.println(url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String pref) {
                        JSONParser jp = new JSONParser();
                        try {
                            JSONArray a;
                            a=(JSONArray)jp.parse(pref);
                           JSONObject o=(JSONObject)a.get(0);
                           JSONObject o2=(JSONObject)jp.parse((String)o.get("preferencias"));

                            if(a.size()!=0){
                                if((long)o2.get("chk_guitarra")==1){
                                    chk_guitarra.setChecked(true);
                                }
                                if((long)o2.get("chk_timbales")==1){
                                    chk_timbales.setChecked(true);
                                }
                                if((long)o2.get("chk_piano")==1){
                                    chk_piano.setChecked(true);
                                }
                                if((long)o2.get("chk_violin")==1){
                                    chk_violin.setChecked(true);
                                }
                                if((long)o2.get("chk_saxofon")==1){
                                    chk_saxofon.setChecked(true);
                                }
                                if((long)o2.get("chk_trompeta")==1){
                                    chk_trompeta.setChecked(true);
                                }
                                if((long)o2.get("chk_bateria")==1){
                                    chk_bateria.setChecked(true);
                                }
                                if((long)o2.get("chk_tejido")==1){
                                    chk_tejido.setChecked(true);
                                }
                                if((long)o2.get("chk_costura")==1){
                                    chk_costura.setChecked(true);
                                }
                                if((long)o2.get("chk_manualidades")==1){
                                    chk_manualidades.setChecked(true);
                                }
                                if((long)o2.get("chk_escultura")==1){
                                    chk_escultura.setChecked(true);
                                }
                                if((long)o2.get("chk_ajedrez")==1){
                                    chk_ajedrez.setChecked(true);
                                }
                                if((long)o2.get("chk_ludo")==1){
                                    chk_ludo.setChecked(true);
                                }
                                if((long)o2.get("chk_damas")==1){
                                    chk_damas.setChecked(true);
                                }
                                if((long)o2.get("chk_damas_chinas")==1){
                                    chk_damas_chinas.setChecked(true);
                                }
                                if((long)o2.get("chk_ingles")==1){
                                    chk_ingles.setChecked(true);
                                }
                                if((long)o2.get("chk_aleman")==1){
                                    chk_aleman.setChecked(true);
                                }
                                if((long)o2.get("chk_frances")==1){
                                    chk_frances.setChecked(true);
                                }
                                if((long)o2.get("chk_portugues")==1){
                                    chk_portugues.setChecked(true);
                                }
                                if((long)o2.get("chk_italiano")==1){
                                    chk_italiano.setChecked(true);
                                }
                                if((long)o2.get("chk_hp")==1){
                                    chk_hp.setChecked(true);
                                }
                                if((long)o2.get("chk_hu")==1){
                                    chk_hu.setChecked(true);
                                }
                                if((long)o2.get("chk_hc")==1){
                                    chk_hc.setChecked(true);
                                }
                                if((long)o2.get("chk_ha")==1){
                                    chk_ha.setChecked(true);
                                }
                                if((long)o2.get("chk_cine")==1){
                                    chk_cine.setChecked(true);
                                }
                                if((long)o2.get("chk_teatro")==1){
                                    chk_teatro.setChecked(true);
                                }
                                if((long)o2.get("chk_pintura")==1){
                                    chk_pintura.setChecked(true);
                                }
                                if((long)o2.get("chk_arquitectura")==1){
                                    chk_arquitectura.setChecked(true);
                                }
                                if((long)o2.get("chk_novela")==1){
                                    chk_novela.setChecked(true);
                                }
                                if((long)o2.get("chk_drama")==1){
                                    chk_drama.setChecked(true);
                                }
                                if((long)o2.get("chk_historia")==1){
                                    chk_historia.setChecked(true);
                                }
                                if((long)o2.get("chk_autoayuda")==1){
                                    chk_autoayuda.setChecked(true);
                                }
                                if((long)o2.get("chk_thriller")==1){
                                    chk_thriller.setChecked(true);
                                }
                                if((long)o2.get("chk_taichi")==1){
                                    chk_taichi.setChecked(true);
                                }
                                if((long)o2.get("chk_yoga")==1){
                                    chk_yoga.setChecked(true);
                                }
                                if((long)o2.get("chk_meditacion")==1){
                                    chk_meditacion.setChecked(true);
                                }
                                if((long)o2.get("chk_rmp")==1){
                                    chk_rmp.setChecked(true);
                                }
                                if((long)o2.get("chk_baile")==1){
                                    chk_baile.setChecked(true);
                                }
                                if((long)o2.get("chk_estiramientos")==1){
                                    chk_estiramientos.setChecked(true);
                                }
                                if((long)o2.get("chk_caminata")==1){
                                    chk_caminata.setChecked(true);
                                }
                                if((long)o2.get("chk_gimnasia")==1){
                                    chk_gimnasia.setChecked(true);
                                }
                                if((long)o2.get("chk_biodanza")==1){
                                    chk_biodanza.setChecked(true);
                                }
                                md.dismiss();
                            }else{
                                md.dismiss();
                                Toast.makeText(getActivity(),"No se pudo obtener sus preferencias",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            md.dismiss();
//                            Toast.makeText(getActivity(),"No se pudo obtener sus preferencias",Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
