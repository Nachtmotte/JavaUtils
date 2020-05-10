package utils;

import arbolGeneral.ArbolGeneral;
import listaGenerica.*;

public class Aleatorio {

    public static int generarInt(int fin){ return (int)Math.floor(Math.random()*fin); }

    public static double generarDouble(int fin){ return Math.random()*fin; }

    public static int generarInt(int inicio, int fin){ return (int)Math.floor(Math.random()*(fin-inicio+1)+inicio); }

    public static double generarDouble(int inicio, int fin){ return Math.random()*(fin-inicio)+inicio;}

    public static char generarUpperChar(){ return (char)generarInt(65,90); }

    public static char generarLowerChar(){ return (char)generarInt(97,122); }

    public static String generarString(int inicio, int fin, boolean upper){
        StringBuilder sb = new StringBuilder();
        int tamanio = generarInt(inicio, fin);
        for(int i = 0; i < tamanio; i++){
            sb.append(upper? generarUpperChar() : generarLowerChar());
        }
        return sb.toString();
    }

    public static String generarMail(){ return generarString(5,10, false) + "@mail.com"; }

    public static int generarDni(){ return generarInt(10000000,99000000); }

    public static ListaGenerica<Integer> generarListaInt(int tamanio){
        ListaGenerica<Integer> lista = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < tamanio; i++){
            lista.agregarFinal(generarInt(tamanio));
        }
        return lista;
    }

    public static ListaGenerica<Character> generarListaChar(int tamanio){
        ListaGenerica<Character> lista = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < tamanio; i++){
            lista.agregarFinal(generarUpperChar());
        }
        return lista;
    }

    public static ListaGenerica<String> generarListaString(int tamanio, boolean upper){
        ListaGenerica<String> lista = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < tamanio; i++){
            lista.agregarFinal(generarString(4 ,8, upper));
        }
        return lista;
    }

    public static ArbolGeneral<Integer> generarArbolInt(int altura, int grado, int inicio, int fin, boolean lleno){
        if(altura == 0){
            return new ArbolGeneral<>(generarInt(inicio, fin));
        }
        ListaGenerica<ArbolGeneral<Integer>> hijos = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < grado; i++){
            hijos.agregarFinal(generarArbolInt(altura-1, lleno? grado : generarInt(grado), inicio, fin, lleno));
        }
        return new ArbolGeneral<>(generarInt(inicio, fin), hijos);
    }

    public static ArbolGeneral<Character> generarArbolChar(int altura, int grado, boolean lleno){
        if(altura == 0){
            return new ArbolGeneral<>(generarUpperChar());
        }
        ListaGenerica<ArbolGeneral<Character>> hijos = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < grado; i++){
            hijos.agregarFinal(generarArbolChar(altura-1, lleno? grado : generarInt(grado), lleno));
        }
        return new ArbolGeneral<>(generarUpperChar(), hijos);
    }

    public static ArbolGeneral<String> generarArbolString(int altura, int grado, boolean upper, boolean lleno){
        if(altura == 0){
            return new ArbolGeneral<>(generarString(4, 8, upper));
        }
        ListaGenerica<ArbolGeneral<String>> hijos = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < grado; i++){
            hijos.agregarFinal(generarArbolString(altura-1, lleno? grado : generarInt(grado), upper, lleno));
        }
        return new ArbolGeneral<>(generarString(4, 8, upper), hijos);
    }
}
