package tesis.ulima.com.tesiskevin.Afinidad;

public class Model {
    public double calcularAfinidadEntretenimiento(Usuario usuario,Usuario usuario2){
        double afinidadEntretenimiento;

        int contador_instrumentos=0;
        int contador_manualidades=0;
        int contador_juegos=0;
        double regla_instrumentos=0.40;
        double regla_manualidades=0.30;
        double regla_juegos=0.30;
        double factor=0.35;
        /*****Instrumentos musicales****/
        if(usuario.getChk_guitarra_val()== usuario2.getChk_guitarra_val() && usuario.getChk_guitarra_val()!=0 && usuario2.getChk_guitarra_val()!=0){contador_instrumentos++;}
        if(usuario.getChk_timbales_val()== usuario2.getChk_timbales_val() && usuario.getChk_timbales_val()!=0 && usuario2.getChk_timbales_val()!=0){contador_instrumentos++;}
        if(usuario.getChk_piano_val()== usuario2.getChk_piano_val() && usuario.getChk_piano_val()!=0 && usuario2.getChk_piano_val()!=0){contador_instrumentos++;}
        if(usuario.getChk_violin_val()== usuario2.getChk_violin_val() && usuario.getChk_violin_val()!=0 && usuario2.getChk_violin_val()!=0){contador_instrumentos++;}
        if(usuario.getChk_saxofon_val()== usuario2.getChk_saxofon_val() && usuario.getChk_saxofon_val()!=0 && usuario2.getChk_saxofon_val()!=0){contador_instrumentos++;}
        if(usuario.getChk_trompeta_val()== usuario2.getChk_trompeta_val() && usuario.getChk_trompeta_val()!=0 && usuario2.getChk_trompeta_val()!=0){contador_instrumentos++;}
        if(usuario.getChk_bateria_val()== usuario2.getChk_bateria_val() && usuario.getChk_bateria_val()!=0 && usuario2.getChk_bateria_val()!=0){contador_instrumentos++;}

        /*****Manualidades y Confección****/
        if(usuario.getChk_tejido_val()== usuario2.getChk_tejido_val() && usuario.getChk_tejido_val()!=0 && usuario2.getChk_tejido_val()!=0){contador_manualidades++;}
        if(usuario.getChk_costura_val()== usuario2.getChk_costura_val() && usuario.getChk_costura_val()!=0 && usuario2.getChk_costura_val()!=0){contador_manualidades++;}
        if(usuario.getChk_manualidades_val()== usuario2.getChk_manualidades_val() && usuario.getChk_manualidades_val()!=0 && usuario2.getChk_manualidades_val()!=0){contador_manualidades++;}
        if(usuario.getChk_escultura_val()== usuario2.getChk_escultura_val() && usuario.getChk_escultura_val()!=0 && usuario2.getChk_escultura_val()!=0){contador_manualidades++;}

        /*****Juegos de mesa****/
        if(usuario.getChk_ajedrez_val()== usuario2.getChk_ajedrez_val() && usuario.getChk_ajedrez_val()!=0 && usuario2.getChk_ajedrez_val()!=0){contador_juegos++;}
        if(usuario.getChk_ludo_val()== usuario2.getChk_ludo_val() && usuario.getChk_ludo_val()!=0 && usuario2.getChk_ludo_val()!=0){contador_juegos++;}
        if(usuario.getChk_damas_val()== usuario2.getChk_damas_val() && usuario.getChk_damas_val()!=0 && usuario2.getChk_damas_val()!=0){contador_juegos++;}
        if(usuario.getChk_damas_chinas_val()== usuario2.getChk_damas_chinas_val() && usuario.getChk_damas_chinas_val()!=0 && usuario2.getChk_damas_chinas_val()!=0){contador_juegos++;}

        double condicion_instrumentos=0;
        double condicion_manualidades=0;
        double condicion_juegos=0;

        if(contador_instrumentos==1){
            condicion_instrumentos=0.5;
        }else if(contador_instrumentos>=2){
            condicion_instrumentos=1;
        }

        if(contador_manualidades==1){
            condicion_manualidades=0.5;
        }else if(contador_manualidades>=2){
            condicion_manualidades=1;
        }

        if(contador_juegos==1){
            condicion_juegos=0.2;
        }else if(contador_juegos==2){
            condicion_juegos=0.6;
        }else if(contador_juegos>=3){
            condicion_juegos=1;
        }

        double afinidad_instrumentos=regla_instrumentos*condicion_instrumentos;
        double afinidad_manualidades=regla_manualidades*condicion_manualidades;
        double afinidad_juegos=regla_juegos*condicion_juegos;

        afinidadEntretenimiento=factor*(afinidad_instrumentos+afinidad_manualidades+afinidad_juegos);

        return afinidadEntretenimiento;
    }

    public double calcularAfinidadAprendizaje(Usuario usuario,Usuario usuario2){
        double afinidadAprendizaje;
        int contador_idiomas=0;
        int contador_historia=0;
        double regla_idiomas=0.6;
        double regla_historia=0.4;
        double factor=0.20;

        /*****Instrumentos idiomas****/
        if(usuario.getChk_ingles_val()== usuario2.getChk_ingles_val() && usuario.getChk_ingles_val()!=0 && usuario2.getChk_ingles_val()!=0){contador_idiomas++;}
        if(usuario.getChk_aleman_val()== usuario2.getChk_aleman_val() && usuario.getChk_aleman_val()!=0 && usuario2.getChk_aleman_val()!=0){contador_idiomas++;}
        if(usuario.getChk_frances_val()== usuario2.getChk_frances_val() && usuario.getChk_frances_val()!=0 && usuario2.getChk_frances_val()!=0){contador_idiomas++;}
        if(usuario.getChk_portugues_val()== usuario2.getChk_portugues_val() && usuario.getChk_portugues_val()!=0 && usuario2.getChk_portugues_val()!=0){contador_idiomas++;}
        if(usuario.getChk_italiano_val()== usuario2.getChk_italiano_val() && usuario.getChk_italiano_val()!=0 && usuario2.getChk_italiano_val()!=0){contador_idiomas++;}


        /*****Historia****/
        if(usuario.getChk_hp_val()== usuario2.getChk_hp_val() && usuario.getChk_hp_val()!=0 && usuario2.getChk_hp_val()!=0){contador_historia++;}
        if(usuario.getChk_hu_val()== usuario2.getChk_hu_val() && usuario.getChk_hu_val()!=0 && usuario2.getChk_hu_val()!=0){contador_historia++;}
        if(usuario.getChk_hc_val()== usuario2.getChk_hc_val() && usuario.getChk_hc_val()!=0 && usuario2.getChk_hc_val()!=0){contador_historia++;}
        if(usuario.getChk_ha_val()== usuario2.getChk_ha_val() && usuario.getChk_ha_val()!=0 && usuario2.getChk_ha_val()!=0){contador_historia++;}

        double condicion_idiomas=0;
        double condicion_historia=0;

        if(contador_idiomas==1){
            condicion_idiomas=0.5;
        }else if(contador_idiomas>=2){
            condicion_idiomas=1;
        }

        if(contador_historia==1){
            condicion_historia=0.5;
        }else if(contador_historia>=2){
            condicion_historia=1;
        }

        double afinidad_idiomas=regla_idiomas*condicion_idiomas;
        double afinidad_historia=regla_historia*condicion_historia;

        afinidadAprendizaje=factor*(afinidad_idiomas+afinidad_historia);
        return afinidadAprendizaje;
    }

    public double calcularAfinidadArte(Usuario usuario,Usuario usuario2){
        double afinidadArteLiteratura;
        int contador_arte=0;
        int contador_lectura=0;
        double regla_arte=0.5;
        double regla_lectura=0.5;
        double factor=0.1;

        /*****Arte****/
        if(usuario.getChk_cine_val()== usuario2.getChk_cine_val() && usuario.getChk_cine_val()!=0 && usuario2.getChk_cine_val()!=0){contador_arte++;}
        if(usuario.getChk_teatro_val()== usuario2.getChk_teatro_val() && usuario.getChk_teatro_val()!=0 && usuario2.getChk_teatro_val()!=0){contador_arte++;}
        if(usuario.getChk_pintura_val()== usuario2.getChk_pintura_val() && usuario.getChk_pintura_val()!=0 && usuario2.getChk_pintura_val()!=0){contador_arte++;}
        if(usuario.getChk_arquitectura_val()== usuario2.getChk_arquitectura_val() && usuario.getChk_arquitectura_val()!=0 && usuario2.getChk_arquitectura_val()!=0){contador_arte++;}

        /*****Lectura****/
        if(usuario.getChk_novela_val()== usuario2.getChk_novela_val() && usuario.getChk_novela_val()!=0 && usuario2.getChk_novela_val()!=0){contador_lectura++;}
        if(usuario.getChk_drama_val()== usuario2.getChk_drama_val() && usuario.getChk_drama_val()!=0 && usuario2.getChk_drama_val()!=0){contador_lectura++;}
        if(usuario.getChk_historia_val()== usuario2.getChk_historia_val() && usuario.getChk_historia_val()!=0 && usuario2.getChk_historia_val()!=0){contador_lectura++;}
        if(usuario.getChk_autoayuda_val()== usuario2.getChk_autoayuda_val() && usuario.getChk_autoayuda_val()!=0 && usuario2.getChk_autoayuda_val()!=0){contador_lectura++;}
        if(usuario.getChk_thriller_val()== usuario2.getChk_thriller_val() && usuario.getChk_thriller_val()!=0 && usuario2.getChk_thriller_val()!=0){contador_lectura++;}

        double condicion_arte=0;
        double condicion_lectura=0;
        if(contador_arte==1){
            condicion_arte=0.5;
        }else if(contador_arte>=2){
            condicion_arte=1;
        }

        if(contador_lectura==1){
            condicion_lectura=0.5;
        }else if(contador_lectura>=2){
            condicion_lectura=01;
        }

        double afinidadArte=regla_arte*condicion_arte;
        double afinidadLectura=regla_lectura*condicion_lectura;
        afinidadArteLiteratura=factor*(afinidadArte+afinidadLectura);
        return afinidadArteLiteratura;
    }

    public double calcularAfinidadEjercicio(Usuario usuario,Usuario usuario2){
        double afinidadEjercicio;
        int contador_relajacion=0;
        int contador_fisicos=0;
        double regla_relajacion=0.5;
        double regla_fisicos=0.5;
        double factor=0.35;

        /*****Ejercicios de Relajación****/
        if(usuario.getChk_taichi_val()== usuario2.getChk_taichi_val() && usuario.getChk_taichi_val()!=0 && usuario2.getChk_taichi_val()!=0){contador_relajacion++;}
        if(usuario.getChk_yoga_val()== usuario2.getChk_yoga_val() && usuario.getChk_yoga_val()!=0 && usuario2.getChk_yoga_val()!=0){contador_relajacion++;}
        if(usuario.getChk_meditacion_val()== usuario2.getChk_meditacion_val() && usuario.getChk_meditacion_val()!=0 && usuario2.getChk_meditacion_val()!=0){contador_relajacion++;}
        if(usuario.getChk_rmp_val()== usuario2.getChk_rmp_val() && usuario.getChk_rmp_val()!=0 && usuario2.getChk_rmp_val()!=0){contador_relajacion++;}

        /*****Ejercicios Físicos****/
        if(usuario.getChk_baile_val()== usuario2.getChk_baile_val() && usuario.getChk_baile_val()!=0 && usuario2.getChk_baile_val()!=0){contador_fisicos++;}
        if(usuario.getChk_estiramientos_val()== usuario2.getChk_estiramientos_val() && usuario.getChk_estiramientos_val()!=0 && usuario2.getChk_estiramientos_val()!=0){contador_fisicos++;}
        if(usuario.getChk_caminata_val()== usuario2.getChk_caminata_val() && usuario.getChk_caminata_val()!=0 && usuario2.getChk_caminata_val()!=0){contador_fisicos++;}
        if(usuario.getChk_gimnasia_val()== usuario2.getChk_gimnasia_val() && usuario.getChk_gimnasia_val()!=0 && usuario2.getChk_gimnasia_val()!=0){contador_fisicos++;}
        if(usuario.getChk_biodanza_val()== usuario2.getChk_biodanza_val() && usuario.getChk_biodanza_val()!=0 && usuario2.getChk_biodanza_val()!=0){contador_fisicos++;}

        double condicion_relajacion=0;
        double condicion_fisicos=0;

        if(contador_relajacion==1){
            condicion_relajacion=0.5;
        }else if(contador_relajacion>=2){
            condicion_relajacion=1;
        }

        if(contador_fisicos==1){
            condicion_fisicos=0.5;
        }else if(contador_fisicos>=2){
            condicion_fisicos=1;
        }

        double afinidadRelacion=regla_relajacion*condicion_relajacion;
        double afinidadEjercicios=regla_fisicos*condicion_fisicos;

        afinidadEjercicio=factor*(afinidadRelacion+afinidadEjercicios);
        return afinidadEjercicio;
    }

    public long calcularAfinidadTotal(Usuario a,Usuario b){
        double afinidadEntretenimiento=this.calcularAfinidadEntretenimiento(a, b);
        double afinidadAprendizaje=this.calcularAfinidadAprendizaje(a, b);
        double afinidadArte=this.calcularAfinidadArte(a, b);
        double afinidadEjercicio=this.calcularAfinidadEjercicio(a, b);

        double afinidad=afinidadEntretenimiento+afinidadAprendizaje+afinidadArte+afinidadEjercicio;
        long afinidadTotal=Math.round(afinidad);
        return afinidadTotal;
    }
}
