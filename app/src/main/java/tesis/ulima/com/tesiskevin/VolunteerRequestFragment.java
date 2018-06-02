package tesis.ulima.com.tesiskevin;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class VolunteerRequestFragment extends Fragment {

    Context context;
    RecyclerView activity;
    RequestAdapter adapter;
    List<DataSnapshot> l=new ArrayList<>();
    SessionManager session;
    MaterialDialog md;
    SwipeRefreshLayout swipeRefreshLayout;
    User u;
    Query query;

    public VolunteerRequestFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VolunteerRequestFragment newInstance() {
        VolunteerRequestFragment fragment = new VolunteerRequestFragment();
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
        View v=inflater.inflate(R.layout.fragment_volunteer_request, container, false);
        session=new SessionManager(getActivity());
        HashMap<String,String> user=session.getUserDetails();
        Gson g=new Gson();
        u=g.fromJson(user.get(SessionManager.KEY_VALUES),User.class);
        activity=v.findViewById(R.id.recycler_view_volunteer_request);
        activity.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new RequestAdapter(l);
        swipeRefreshLayout=v.findViewById(R.id.swipe_volunteer_request);
        query=FirebaseDatabase.getInstance().getReference("request")
                .orderByChild("estado").equalTo(0);
        query.addValueEventListener(valueEventListener);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                query.addValueEventListener(valueEventListener);
            }
        });

        md=new MaterialDialog.Builder(getContext())
                .content("Cargando")
                .progress(true,0)
                .cancelable(false)
                .backgroundColor(Color.WHITE)
                .contentColor(Color.BLACK)
                .show();
        query.addValueEventListener(valueEventListener);
        activity.setAdapter(adapter);
        return v;
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            l.clear();
            for (DataSnapshot values: dataSnapshot.getChildren()) {
                // TODO: handle the post
                final HashMap<String,Object> request=(HashMap<String,Object>)values.getValue();
                if((long)request.get("idVoluntario")==Integer.parseInt(u.getId()))
                    l.add(values);
            }
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            md.dismiss();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(context, "No se pudo obtener las solicitudes", Toast.LENGTH_SHORT).show();
        }
    };
}
