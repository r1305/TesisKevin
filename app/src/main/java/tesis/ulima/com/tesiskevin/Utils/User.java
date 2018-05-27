package tesis.ulima.com.tesiskevin.Utils;

import java.util.List;

/**
 * Created by Julian on 4/11/2017.
 */

public class User {

    public String id;
    public String nombre;
    public String fcm;
    public String direccion;



    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
