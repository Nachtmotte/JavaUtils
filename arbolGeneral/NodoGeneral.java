package arbolGeneral;

import listaGenerica.*;

public class NodoGeneral<T> {

    private T dato;
    private ListaGenerica<NodoGeneral<T>> listaHijos;
    
    public NodoGeneral(T dato){
        this.dato = dato;
        listaHijos = new ListaEnlazadaGenerica<>();
    }

    public void setDato(T dato){
        this.dato = dato;
    }

    public T getDato(){
        return this.dato;
    }
    
    public void setListaHijos(ListaGenerica<NodoGeneral<T>> lista){
        this.listaHijos = lista;
    }

    public ListaGenerica<NodoGeneral<T>> getHijos(){
        return this.listaHijos;
    }
}
