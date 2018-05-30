package tesis.ulima.com.tesiskevin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tesis.ulima.com.tesiskevin.Utils.SessionManager;
import tesis.ulima.com.tesiskevin.Utils.User;

public class VisitasAdapter extends RecyclerView.Adapter<VisitasAdapter.ViewHolder>{

    List<JSONObject> l =new ArrayList<>();
    List<DataSnapshot> l2 =new ArrayList<>();
    View.OnClickListener listener;
    private MaterialDialog md;
    private String name_alarm;
    private EditText userInput;
    User u;
    SessionManager session;

    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        session=new SessionManager(context);
        HashMap<String,String> user=session.getUserDetails();
        Gson g=new Gson();
        u=g.fromJson(user.get(SessionManager.KEY_VALUES),User.class);
        return new VisitasAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visita,parent,false));
    }

    public VisitasAdapter(List<JSONObject> l,List<DataSnapshot> l2) {
        this.l = l;
        this.l2 = l2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final JSONObject o=l.get(position);
        final DataSnapshot ds=l2.get(position);
        final HashMap<String,String> request=(HashMap<String,String>)ds.getValue();
        System.out.println(request.get("estado").equals("1"));
        System.out.println(request.get("idVoluntario").equals(u.getId()));
        if(request.get("estado").equals("1") && request.get("idVoluntario").equals(u.getId())) {
        }else{
            l2.remove(position);
        }
//        holder.name.setText((String)o.get("nombre"));
//        holder.fecha.setText((String)o.get("fecha")+ " " +o.get("hora"));
        holder.name.setText(request.get("nombre"));
        holder.fecha.setText(request.get("fecha")+ " " +request.get("hora"));
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

        TextView name,fecha;
        ImageView arrow;

        private ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.visita_name);
            fecha=itemView.findViewById(R.id.visita_fecha);
            arrow=itemView.findViewById(R.id.visita_arrow);
        }
    }
}
