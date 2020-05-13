package utils;

import arbolGeneral.ArbolGeneral;
import listaGenerica.*;

public class Aleatorio {

    //Genera un numero integer aleatorio entre 0 y fin ambos inclusive
    public static int generarInt(int fin){ return (int)Math.floor(Math.random()*fin); }

    //Genera un numero double aleatorio entre 0 y fin
    public static double generarDouble(int fin){ return Math.random()*fin; }

    //Genera un numero integer aleatorio entre inicio y fin ambos inclusive
    public static int generarInt(int inicio, int fin){ return (int)Math.floor(Math.random()*(fin-inicio+1)+inicio); }

    //Genera un numero double aleatorio entre 0 y fin
    public static double generarDouble(int inicio, int fin){ return Math.random()*(fin-inicio)+inicio;}

    //Genera un char integer aleatorio entre inicio y fin
    public static char generarChar(char inicio, char fin){ return (char)generarInt(inicio,fin); }

    //Genera un char aleatorio entre A y Z
    public static char generarUpperChar(){ return (char)generarInt(65,90); }

    //Genera un char aleatorio entre a y z
    public static char generarLowerChar(){ return (char)generarInt(97,122); }

    //Genera un string aleatorio de tamanio aleatorio entre inicio y fin
    public static String generarString(int inicio, int fin, boolean upper){
        StringBuilder sb = new StringBuilder();
        int tamanio = generarInt(inicio, fin);
        for(int i = 0; i < tamanio; i++){
            sb.append(upper? generarUpperChar() : generarLowerChar());
        }
        return sb.toString();
    }

    //Genera una lista de int aleatorios de tamanio pasado por parametro, los valores se pueden repetir
    public static ListaGenerica<Integer> generarListaInt(int tamanio){
        ListaGenerica<Integer> lista = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < tamanio; i++){
            lista.agregarFinal(generarInt(tamanio));
        }
        return lista;
    }
    //Genera una lista de int aleatorios de rango inicio a fin de tamanio pasado por parametro, los valores se pueden repetir
    public static ListaGenerica<Integer> generarListaInt(int tamanio, int inicio, int fin){
        ListaGenerica<Integer> lista = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < tamanio; i++){
            lista.agregarFinal(generarInt(inicio, fin));
        }
        return lista;
    }

    //Genera una lista de int aleatorios de rango inicio a fin de tamanio pasado por parametro, los valores no se repiten
    public static ListaGenerica<Integer> generarListaIntSinRepetir(int tamanio, int inicio, int fin){
        ListaGenerica<Integer> lista = new ListaEnlazadaGenerica<>();
        if((fin - inicio) >= (tamanio - 1)){
            ListaGenerica<Integer> numeros = new ListaEnlazadaGenerica<>();
            for(int i = inicio; i <= fin; i++){
                numeros.agregarFinal(i);
            }
            for(int i = 0; i < tamanio; i++){
                int indice = generarInt(1, numeros.tamanio());
                int elemento = numeros.elemento(indice);
                numeros.eliminarEn(indice);
                lista.agregarFinal(elemento);
            }
        }else{
            System.out.println("El rango debe ser igual o mayor que el tamanio");
        }
        return lista;
    }

    //Genera una lista de char aleatorios de tamanio pasado por parametro, los valores se pueden repetir
    public static ListaGenerica<Character> generarListaChar(int tamanio, boolean upper){
        ListaGenerica<Character> lista = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < tamanio; i++){
            lista.agregarFinal(upper? generarUpperChar() : generarLowerChar());
        }
        return lista;
    }

    //Genera una lista de char aleatorios de rango inicio a fin de tamanio pasado por parametro, los valores se pueden repetir
    public static ListaGenerica<Character> generarListaChar(int tamanio, char inicio, char fin){
        ListaGenerica<Character> lista = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < tamanio; i++){
            lista.agregarFinal(generarChar(inicio, fin));
        }
        return lista;
    }

    //Genera una lista de char aleatorios de rango inicio a fin de tamanio pasado por parametro, los valores no se repiten
    public static ListaGenerica<Character> generarListaCharSinRepetir(int tamanio, char inicio, char fin){
        ListaGenerica<Character> lista = new ListaEnlazadaGenerica<>();
        if((fin - inicio) >= (tamanio - 1)){
            ListaGenerica<Character> caracteres = new ListaEnlazadaGenerica<>();
            for(int i = inicio; i <= fin; i++){
                caracteres.agregarFinal((char)i);
            }
            for(int i = 0; i < tamanio; i++){
                int indice = generarInt(1, caracteres.tamanio());
                char elemento = caracteres.elemento(indice);
                caracteres.eliminarEn(indice);
                lista.agregarFinal(elemento);
            }
        }else{
            System.out.println("El rango debe ser igual o mayor que el tamanio");
        }
        return lista;
    }

    //Genera una lista de string aleatorios de tamanio variable, el tamanio de la lista es igual al pasado por parametro,
    // los valores se pueden repetir
    public static ListaGenerica<String> generarListaString(int tamanio, boolean upper){
        ListaGenerica<String> lista = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < tamanio; i++){
            lista.agregarFinal(generarString(4 ,8, upper));
        }
        return lista;
    }

    //Genera un arbol de int de altura, grado maximo pasado por parametros, los posible valores de sus nodos estan
    //dentro del rango pasado por parametro, se puede especificar si es un arbol lleno, los valores se pueden repetir
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

    //Genera un arbol de int de altura, grado maximo pasado por parametros, los posible valores de sus nodos estan
    //dentro del rango pasado por parametro, se puede especificar si es un arbol lleno, los valores no se repiten
    //Para generar el arbol, el rango de valores debe ser igual o mayor a la cantidad de nodos en el arbol lleno
    public static ArbolGeneral<Integer> generarArbolIntSinRepetir(int altura, int grado, int inicio, int fin, boolean lleno) {
        int tamanio = fin - inicio;
        if(tamanio < 0){ tamanio *= -1; }
        tamanio++;
        ArbolGeneral<Integer> arbol = null;
        if((Math.pow(grado, altura+1)-1)/(grado-1) > tamanio){
            arbol = new ArbolGeneral<>();
            System.out.println("El rango de valores es mas pequeño que el arbol");
        }else {
            ListaGenerica<Integer> numeros = generarListaIntSinRepetir(tamanio, inicio, fin);
            arbol = generarArbolIntSinRepetir(altura, grado, numeros, lleno);
        }
        return arbol;
    }
    private static ArbolGeneral<Integer> generarArbolIntSinRepetir(int altura, int grado, ListaGenerica<Integer> numeros, boolean lleno){
        if(altura == 0){
            int indice = generarInt(1, numeros.tamanio());
            int valor = numeros.elemento(indice);
            numeros.eliminarEn(indice);
            return new ArbolGeneral<>(valor);
        }
        ListaGenerica<ArbolGeneral<Integer>> hijos = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < grado; i++){
            hijos.agregarFinal(generarArbolIntSinRepetir(altura-1, lleno? grado : generarInt(grado), numeros, lleno));
        }
        int indice = generarInt(1, numeros.tamanio());
        int valor = numeros.elemento(indice);
        numeros.eliminarEn(indice);
        return new ArbolGeneral<>(valor, hijos);
    }

    //Genera un arbol de char de altura, grado maximo pasado por parametros, los posible valores de sus nodos estan
    //dentro del rango pasado por parametro, se puede especificar si es un arbol lleno, los valores se pueden repetir
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

    //Genera un arbol de char de altura, grado maximo pasado por parametros, los posible valores de sus nodos estan
    //dentro del rango pasado por parametro, se puede especificar si es un arbol lleno, los valores no se repiten
    //Para generar el arbol, el rango de valores debe ser igual o mayor a la cantidad de nodos en el arbol lleno
    public static ArbolGeneral<Character> generarArbolCharSinRepetir(int altura, int grado, char inicio, char fin, boolean lleno) {
        int tamanio = fin - inicio;
        if(tamanio < 0){ tamanio *= -1; }
        tamanio++;
        ArbolGeneral<Character> arbol = null;
        if((Math.pow(grado, altura+1)-1)/(grado-1) > tamanio){
            arbol = new ArbolGeneral<>();
            System.out.println("El rango de valores es mas pequeño que el arbol");
        }else {
            ListaGenerica<Character> caracteres = generarListaCharSinRepetir(tamanio, inicio, fin);
            arbol = generarArbolCharSinRepetir(altura, grado, caracteres, lleno);
        }
        return arbol;
    }
    private static ArbolGeneral<Character> generarArbolCharSinRepetir(int altura, int grado, ListaGenerica<Character> caracteres, boolean lleno){
        if(altura == 0){
            int indice = generarInt(1, caracteres.tamanio());
            char valor = caracteres.elemento(indice);
            caracteres.eliminarEn(indice);
            return new ArbolGeneral<>(valor);
        }
        ListaGenerica<ArbolGeneral<Character>> hijos = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < grado; i++){
            hijos.agregarFinal(generarArbolCharSinRepetir(altura-1, lleno? grado : generarInt(grado), caracteres, lleno));
        }
        int indice = generarInt(1, caracteres.tamanio());
        char valor = caracteres.elemento(indice);
        caracteres.eliminarEn(indice);
        return new ArbolGeneral<>(valor, hijos);
    }

    //Genera un arbol de string de altura, grado maximo pasado por parametros, los posible valores de sus nodos estan
    //dentro del rango pasado por parametro, se puede especificar si es un arbol lleno, los valores se pueden repetir
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

    //Genera un arbol de string de altura, grado maximo pasado por parametros, los posible valores de sus nodos estan
    //dentro de la lista pasada por parametro, se puede especificar si es un arbol lleno, los valores se pueden repetir
    public static ArbolGeneral<String> generarArbolString(int altura, int grado, ListaGenerica<String> lista, boolean lleno){
        if(altura == 0){
            return new ArbolGeneral<>(lista.elemento(generarInt(1, lista.tamanio())));
        }
        ListaGenerica<ArbolGeneral<String>> hijos = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < grado; i++){
            hijos.agregarFinal(generarArbolString(altura-1, lleno? grado : generarInt(grado), lista, lleno));
        }
        return new ArbolGeneral<>(lista.elemento(generarInt(1, lista.tamanio())), hijos);
    }

    //Genera un arbol de string de altura, grado maximo pasado por parametros.
    //Los nodos que contengan hijos tomaran los valores de la primera lista pasada por parametro y los nodos hojas
    //tomaran valores de la segunda lista pasada por parametro
    //Se puede especificar si es un arbol lleno, los valores se pueden repetir
    public static ArbolGeneral<String> generarArbolString(int altura, int grado, ListaGenerica<String> padres, ListaGenerica<String> hojas, boolean lleno){
        if(altura == 0){
            return new ArbolGeneral<>(hojas.elemento(generarInt(1, hojas.tamanio())));
        }
        ListaGenerica<ArbolGeneral<String>> hijos = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < grado; i++){
            hijos.agregarFinal(generarArbolString(altura-1, lleno? grado : generarInt(grado), padres, hojas, lleno));
        }
        return new ArbolGeneral<>(padres.elemento(generarInt(1, padres.tamanio())), hijos);
    }

    //Genera un arbol de string de altura, grado maximo pasado por parametros, los posible valores de sus nodos estan
    //dentro de la lista pasada por parametro, se puede especificar si es un arbol lleno, los valores no se repiten
    //Para generar el arbol, el tamanio de la lista debe ser igual o mayor a la cantidad de nodos en el arbol lleno
    public static ArbolGeneral<String> generarArbolStringSinRepetir(int altura, int grado, ListaGenerica<String> lista, boolean lleno) {
        ArbolGeneral<String> arbol = null;
        if((Math.pow(grado, altura+1)-1)/(grado-1) > lista.tamanio()){
            arbol = new ArbolGeneral<>();
            System.out.println("El rango de valores es mas pequeño que el arbol");
        }else {
            ListaGenerica<String> sts = lista.clonar();
            arbol = generarArbolStringSinRepetirPrivado(altura, grado, sts, lleno);
        }
        return arbol;
    }
    private static ArbolGeneral<String> generarArbolStringSinRepetirPrivado(int altura, int grado, ListaGenerica<String> lista, boolean lleno){
        if(altura == 0){
            int indice = generarInt(1, lista.tamanio());
            String valor = lista.elemento(indice);
            lista.eliminarEn(indice);
            return new ArbolGeneral<>(valor);
        }
        ListaGenerica<ArbolGeneral<String>> hijos = new ListaEnlazadaGenerica<>();
        for(int i = 0; i < grado; i++){
            hijos.agregarFinal(generarArbolStringSinRepetirPrivado(altura-1, lleno? grado : generarInt(grado), lista, lleno));
        }
        int indice = generarInt(1, lista.tamanio());
        String valor = lista.elemento(indice);
        lista.eliminarEn(indice);
        return new ArbolGeneral<>(valor, hijos);
    }
}
