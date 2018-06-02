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

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class VisitasFragment extends Fragment {

    Context context;
    RecyclerView activity;
    VisitasAdapter adapter;
    List<JSONObject> l=new ArrayList<>();
    SwipeRefreshLayout srefresh;
    MaterialDialog md;
    User u;
    SessionManager session;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<DataSnapshot> l2=new ArrayList<>();

    public VisitasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VisitasFragment newInstance() {
        VisitasFragment fragment = new VisitasFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this.getContext();
        session=new SessionManager(context);
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
        View view=inflater.inflate(R.layout.fragment_visitas, container, false);
        activity=view.findViewById(R.id.recycler_view_visitas);
        activity.setLayoutManager(new LinearLayoutManager(getContext()));
        srefresh=view.findViewById(R.id.swipe_visitas);
        adapter=new VisitasAdapter(l,l2);
        final Query query=FirebaseDatabase.getInstance().getReference("request")
                .orderByChild("estado").equalTo(1);
        query.addValueEventListener(valueEventListener);
        srefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srefresh.setRefreshing(true);
//                getVisitas();
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
//        getVisitas();
        activity.setAdapter(adapter);
        return view;
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            l2.clear();
            for (DataSnapshot values: dataSnapshot.getChildren()) {
                // TODO: handle the post
                final HashMap<String,Object> request=(HashMap<String,Object>)values.getValue();
                if((long)request.get("idVoluntario")==Integer.parseInt(u.getId()))
                    l2.add(values);
            }
            adapter.notifyDataSetChanged();
            srefresh.setRefreshing(false);
            md.dismiss();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
