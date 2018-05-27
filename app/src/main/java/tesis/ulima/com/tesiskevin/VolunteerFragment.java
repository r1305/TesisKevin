package tesis.ulima.com.tesiskevin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;


public class VolunteerFragment extends Fragment {

    Context context;
    RecyclerView activity;
    VolunteerAdapter adapter;
    List<JSONObject> l=new ArrayList<>();
    SessionManager session;
    SwipeRefreshLayout srefresh;
    static MaterialDialog md;

    public VolunteerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VolunteerFragment newInstance() {
        VolunteerFragment fragment = new VolunteerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_volunteer, container, false);
        activity=view.findViewById(R.id.recycler_view_volunteer);
        activity.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new VolunteerAdapter(l);

        /*srefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srefresh.setRefreshing(true);
                getVolunteers();
            }
        });*/
        md=new MaterialDialog.Builder(getContext())
                .content("Cargando")
                .progress(true,0)
                .cancelable(false)
                .backgroundColor(Color.WHITE)
                .contentColor(Color.BLACK)
                .show();
        getVolunteers();

        activity.setAdapter(adapter);
        return view;
    }

    public void getVolunteers(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://espacioseguro.pe/php_connection/kevin/getVolunteers.php";
        System.out.println(url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        JSONParser jp = new JSONParser();
                        try {
                            JSONArray ja=(JSONArray)jp.parse(response);
                            l.clear();
                            for(int i=0;i<ja.size();i++){
                                l.add((JSONObject)ja.get(i));
                            }
//                            srefresh.setRefreshing(false);
                            adapter.notifyDataSetChanged();
                            md.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getContext(),"Intente luego", Toast.LENGTH_SHORT).show();
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
}
