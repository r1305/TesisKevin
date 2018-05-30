package tesis.ulima.com.tesiskevin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;

import tesis.ulima.com.tesiskevin.Afinidad.Model;
import tesis.ulima.com.tesiskevin.Afinidad.Usuario;
import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class VolunteerDetail extends Fragment {
    private static final String idVolunteer = "vol";
    private static final String afinidad_param ="afinidad";

    private String volunteer;
    private String afinidad;
    private Button btn_back,btn_agendar;

    private TextView vol_name,vol_direction,vol_afinidad;

    private User u;
    private SessionManager session;
    MaterialDialog md;

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    TextView calendar,time;
    Button btn_caledar,btn_time,btn_cancel_agendar,btn_save_agendar;

    FirebaseDatabase database;
    DatabaseReference myRef;

    public VolunteerDetail() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VolunteerDetail newInstance(String param1,String param2) {
        VolunteerDetail fragment = new VolunteerDetail();
        Bundle args = new Bundle();
        args.putString(idVolunteer, param1);
        args.putString(afinidad_param, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            volunteer = getArguments().getString(idVolunteer);
            System.out.println(volunteer);
            afinidad=getArguments().getString(afinidad_param);
        }
        session=new SessionManager(getActivity());
        HashMap<String,String> user=session.getUserDetails();
        Gson g=new Gson();
        u=g.fromJson(user.get(SessionManager.KEY_VALUES),User.class);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("request");
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
                        md=new MaterialDialog.Builder(getActivity())
                                .content("Agendando")
                                .progress(true,0)
                                .cancelable(false)
                                .backgroundColor(Color.WHITE)
                                .contentColor(Color.BLACK)
                                .show();
//                        createRequest();
                        createRequestFireBase();
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
        System.out.println(url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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
                                System.out.println(o.get("id").toString());
                                vol_name.setText(o.get("nombre").toString());
                                vol_direction.setText(o.get("direccion").toString());
                                vol_afinidad.setText(String.valueOf(afinidad));
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

    public void createRequest(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String params="?idAdulto="+u.getId()+"&idVoluntario="+volunteer+"&fecha="+calendar.getText()+"&hora="+time.getText()+"&afinidad="+afinidad;
        String url ="https://espacioseguro.pe/php_connection/kevin/createRequest.php"+params;
        System.out.println(url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        md.dismiss();
                        if(response.equals("true")){
                            alertDialog.dismiss();
                            Toast.makeText(getActivity(), "Solicitud registrada exitosamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Ocurrió un error\nIntente nuevamente", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                md.dismiss();
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void createRequestFireBase(){
        Map<String, String> data= new HashMap<String, String>();
        data.put("idAdulto",u.getId());
        data.put("idVoluntario",volunteer);
        data.put("fecha",calendar.getText().toString());
        data.put("hora",time.getText().toString());
        data.put("afinidad",afinidad);
        data.put("estado","0");
        data.put("nombre",u.getNombre());
        data.put("direccion",u.getDireccion());

        myRef.push().setValue(data);
        alertDialog.dismiss();
        Toast.makeText(getContext(), "Solicitud registrada correctamente", Toast.LENGTH_SHORT).show();
    }
}
