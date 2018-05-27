package tesis.ulima.com.tesiskevin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

import tesis.ulima.com.tesiskevin.Afinidad.Model;
import tesis.ulima.com.tesiskevin.Afinidad.Usuario;
import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class VolunteerDetail extends Fragment {
    private static final String idVolunteer = "";

    private String volunteer;
    private long afinidad;
    private Usuario volunteer_pref,adult;
    private Button btn_back,btn_agendar;

    private TextView vol_name,vol_direction,vol_afinidad;

    private User u;
    private SessionManager session;

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    TextView calendar,time;
    Button btn_caledar,btn_time,btn_cancel_agendar,btn_save_agendar;

    public VolunteerDetail() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VolunteerDetail newInstance(String param1) {
        VolunteerDetail fragment = new VolunteerDetail();
        Bundle args = new Bundle();
        args.putString(idVolunteer, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            volunteer = getArguments().getString(idVolunteer);
        }
        session=new SessionManager(getActivity());
        HashMap<String,String> user=session.getUserDetails();
        Gson g=new Gson();
        u=g.fromJson(user.get(SessionManager.KEY_VALUES),User.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_volunteer_detail, container, false);
        getVolunteerDetail();
        vol_name=v.findViewById(R.id.detail_name);
        vol_direction=v.findViewById(R.id.detail_direccion);
        vol_afinidad=v.findViewById(R.id.detail_afinidad);
        btn_back=v.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                Fragment fr;
                fr=VolunteerFragment.newInstance();
                fragmentTransaction.replace(R.id.flaContenido,fr);
                fragmentTransaction.commit();
            }
        });

        btn_agendar=v.findViewById(R.id.btn_agendar);
        btn_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                View promptsView = li.inflate(R.layout.dialog_agendar, null);
                alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setView(promptsView);
                calendar=promptsView.findViewById(R.id.agendar_date);
                time=promptsView.findViewById(R.id.agendar_time);
                btn_caledar=promptsView.findViewById(R.id.btn_fecha);
                btn_time=promptsView.findViewById(R.id.btn_hora);
                btn_save_agendar=promptsView.findViewById(R.id.btn_save_agendar);
                btn_cancel_agendar=promptsView.findViewById(R.id.btn_cancel_agendar);

                btn_caledar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog();
                    }
                });
                btn_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTimePickerDialog();
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                btn_cancel_agendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btn_save_agendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), calendar.getText()+" "+time.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return v;
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "-" + twoDigits((month+1)) + "-" + twoDigits(day);
                calendar.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedTime = twoDigits(hourOfDay) + ":" + twoDigits(minute);
                time.setText(selectedTime);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    public void getVolunteerDetail(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String params="?volunteer="+volunteer;
        String url = "https://espacioseguro.pe/php_connection/kevin/getVolunteer.php"+params;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.println(response);
                        JSONParser jp = new JSONParser();
                        try {
                            JSONArray ja=(JSONArray)jp.parse(response);
                            for(int i=0;i<ja.size();i++){
                                JSONObject o=(JSONObject)ja.get(i);
                                System.out.println(o.get("nombre"));
                                getVolunteerPreferences(o.get("id").toString());
                                vol_name.setText(o.get("nombre").toString());
                                vol_direction.setText(o.get("direccion").toString());
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(),"Intente luego", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
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

    public void getVolunteerPreferences(final String volunteer_id){
//        final MaterialDialog md=new MaterialDialog.Builder(context)
//                .content("Obteniendo preferencias")
//                .progress(true,0)
//                .cancelable(false)
//                .backgroundColor(Color.WHITE)
//                .contentColor(Color.BLACK)
//                .show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://espacioseguro.pe/php_connection/kevin/getPreferences.php?idUsuario="+Integer.valueOf(volunteer_id);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("voluntario "+volunteer_id);
                        System.out.println(response);
                        JSONParser jp = new JSONParser();
                        try {
                            JSONArray a=(JSONArray)jp.parse(response);
                            if(a.size()!=0){
                                JSONObject o=(JSONObject)a.get(0);
                                volunteer_pref=new Usuario(
                                        Integer.valueOf(o.get("chk_guitarra").toString()),
                                        Integer.valueOf(o.get("chk_timbales").toString()),
                                        Integer.valueOf(o.get("chk_piano").toString()),
                                        Integer.valueOf(o.get("chk_violin").toString()),
                                        Integer.valueOf(o.get("chk_saxofon").toString()),
                                        Integer.valueOf(o.get("chk_trompeta").toString()),
                                        Integer.valueOf(o.get("chk_bateria").toString()),
                                        Integer.valueOf(o.get("chk_tejido").toString()),
                                        Integer.valueOf(o.get("chk_costura").toString()),
                                        Integer.valueOf(o.get("chk_manualidades").toString()),
                                        Integer.valueOf(o.get("chk_escultura").toString()),
                                        Integer.valueOf(o.get("chk_ajedrez").toString()),
                                        Integer.valueOf(o.get("chk_ludo").toString()),
                                        Integer.valueOf(o.get("chk_damas").toString()),
                                        Integer.valueOf(o.get("chk_damas_chinas").toString()),
                                        Integer.valueOf(o.get("chk_ingles").toString()),
                                        Integer.valueOf(o.get("chk_aleman").toString()),
                                        Integer.valueOf(o.get("chk_frances").toString()),
                                        Integer.valueOf(o.get("chk_portugues").toString()),
                                        Integer.valueOf(o.get("chk_italiano").toString()),
                                        Integer.valueOf(o.get("chk_hp").toString()),
                                        Integer.valueOf(o.get("chk_hu").toString()),
                                        Integer.valueOf(o.get("chk_hc").toString()),
                                        Integer.valueOf(o.get("chk_ha").toString()),
                                        Integer.valueOf(o.get("chk_cine").toString()),
                                        Integer.valueOf(o.get("chk_teatro").toString()),
                                        Integer.valueOf(o.get("chk_pintura").toString()),
                                        Integer.valueOf(o.get("chk_arquitectura").toString()),
                                        Integer.valueOf(o.get("chk_novela").toString()),
                                        Integer.valueOf(o.get("chk_drama").toString()),
                                        Integer.valueOf(o.get("chk_historia").toString()),
                                        Integer.valueOf(o.get("chk_autoayuda").toString()),
                                        Integer.valueOf(o.get("chk_thriller").toString()),
                                        Integer.valueOf(o.get("chk_taichi").toString()),
                                        Integer.valueOf(o.get("chk_yoga").toString()),
                                        Integer.valueOf(o.get("chk_meditacion").toString()),
                                        Integer.valueOf(o.get("chk_rmp").toString()),
                                        Integer.valueOf(o.get("chk_baile").toString()),
                                        Integer.valueOf(o.get("chk_estiramientos").toString()),
                                        Integer.valueOf(o.get("chk_caminata").toString()),
                                        Integer.valueOf(o.get("chk_gimnasia").toString()),
                                        Integer.valueOf(o.get("chk_biodanza").toString())
                                );
                                getAdultPreferences();
//                                md.dismiss();
                            }else{
//                                md.dismiss();
                                Toast.makeText(getActivity(),"No se pudo obtener sus preferencias",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
//                            md.dismiss();
                            Toast.makeText(getActivity(),"No se pudo obtener sus preferencias",Toast.LENGTH_LONG).show();
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

    public void getAdultPreferences(){
//        final MaterialDialog md=new MaterialDialog.Builder(context)
//                .content("Obteniendo preferencias")
//                .progress(true,0)
//                .cancelable(false)
//                .backgroundColor(Color.WHITE)
//                .contentColor(Color.BLACK)
//                .show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://espacioseguro.pe/php_connection/kevin/getPreferences.php?idUsuario="+u.getId();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("adulto_mayor");
                        System.out.println(response);
                        JSONParser jp = new JSONParser();
                        try {
                            JSONArray a=(JSONArray)jp.parse(response);
                            if(a.size()!=0){
                                JSONObject o=(JSONObject)a.get(0);
                                Gson g=new Gson();
                                adult=g.fromJson(o.toJSONString(),Usuario.class);
//                                md.dismiss();
                                try{
                                    Model model=new Model();
                                    adult=new Usuario(
                                            Integer.valueOf(o.get("chk_guitarra").toString()),
                                            Integer.valueOf(o.get("chk_timbales").toString()),
                                            Integer.valueOf(o.get("chk_piano").toString()),
                                            Integer.valueOf(o.get("chk_violin").toString()),
                                            Integer.valueOf(o.get("chk_saxofon").toString()),
                                            Integer.valueOf(o.get("chk_trompeta").toString()),
                                            Integer.valueOf(o.get("chk_bateria").toString()),
                                            Integer.valueOf(o.get("chk_tejido").toString()),
                                            Integer.valueOf(o.get("chk_costura").toString()),
                                            Integer.valueOf(o.get("chk_manualidades").toString()),
                                            Integer.valueOf(o.get("chk_escultura").toString()),
                                            Integer.valueOf(o.get("chk_ajedrez").toString()),
                                            Integer.valueOf(o.get("chk_ludo").toString()),
                                            Integer.valueOf(o.get("chk_damas").toString()),
                                            Integer.valueOf(o.get("chk_damas_chinas").toString()),
                                            Integer.valueOf(o.get("chk_ingles").toString()),
                                            Integer.valueOf(o.get("chk_aleman").toString()),
                                            Integer.valueOf(o.get("chk_frances").toString()),
                                            Integer.valueOf(o.get("chk_portugues").toString()),
                                            Integer.valueOf(o.get("chk_italiano").toString()),
                                            Integer.valueOf(o.get("chk_hp").toString()),
                                            Integer.valueOf(o.get("chk_hu").toString()),
                                            Integer.valueOf(o.get("chk_hc").toString()),
                                            Integer.valueOf(o.get("chk_ha").toString()),
                                            Integer.valueOf(o.get("chk_cine").toString()),
                                            Integer.valueOf(o.get("chk_teatro").toString()),
                                            Integer.valueOf(o.get("chk_pintura").toString()),
                                            Integer.valueOf(o.get("chk_arquitectura").toString()),
                                            Integer.valueOf(o.get("chk_novela").toString()),
                                            Integer.valueOf(o.get("chk_drama").toString()),
                                            Integer.valueOf(o.get("chk_historia").toString()),
                                            Integer.valueOf(o.get("chk_autoayuda").toString()),
                                            Integer.valueOf(o.get("chk_thriller").toString()),
                                            Integer.valueOf(o.get("chk_taichi").toString()),
                                            Integer.valueOf(o.get("chk_yoga").toString()),
                                            Integer.valueOf(o.get("chk_meditacion").toString()),
                                            Integer.valueOf(o.get("chk_rmp").toString()),
                                            Integer.valueOf(o.get("chk_baile").toString()),
                                            Integer.valueOf(o.get("chk_estiramientos").toString()),
                                            Integer.valueOf(o.get("chk_caminata").toString()),
                                            Integer.valueOf(o.get("chk_gimnasia").toString()),
                                            Integer.valueOf(o.get("chk_biodanza").toString())
                                    );

                                    afinidad=model.calcularAfinidadTotal(adult,volunteer_pref);
                                    System.out.println("afinidad: "+afinidad);
                                    vol_afinidad.setText(String.valueOf(afinidad*100)+"%");
//                                    Toast.makeText(context, String.valueOf(afinidad*100)+"%", Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(getActivity(), "Error al calcular afinidad: "+e, Toast.LENGTH_SHORT).show();
                                }
                            }else{
//                                md.dismiss();
                                Toast.makeText(getActivity(),"No se pudo obtener sus preferencias",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
//                            md.dismiss();
                            Toast.makeText(getActivity(),"No se pudo obtener sus preferencias",Toast.LENGTH_LONG).show();
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
