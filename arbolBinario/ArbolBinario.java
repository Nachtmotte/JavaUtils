package arbolBinario;

import arbolGeneral.ArbolGeneral;
import listaGenerica.ListaEnlazadaGenerica;
import listaGenerica.ListaGenerica;
import pilaCola.ColaGenerica;

public class ArbolBinario<T> {

	private NodoBinario<T> raiz;

	public ArbolBinario() {
		this.raiz = null;
	}

	public ArbolBinario(T dato) {
		this.raiz = new NodoBinario<T>(dato);
	}

	private ArbolBinario(NodoBinario<T> nodo) {
		this.raiz = nodo;
	}

	private NodoBinario<T> getRaiz() {
		return raiz;
	}

	public T getDatoRaiz() {
		if (this.getRaiz() != null) {
			return this.getRaiz().getDato();
		} else {
			return null;
		}
	}

	public ArbolBinario<T> getHijoIzquierdo() {
		return new ArbolBinario<T>(this.raiz.getHijoIzquierdo());
	}

	public ArbolBinario<T> getHijoDerecho() {
		return new ArbolBinario<T>(this.raiz.getHijoDerecho());
	}

	public void agregarHijoIzquierdo(ArbolBinario<T> hijo) {
		this.raiz.setHijoIzquierdo(hijo.getRaiz());
	}

	public void agregarHijoDerecho(ArbolBinario<T> hijo) {
		this.raiz.setHijoDerecho(hijo.getRaiz());
	}

	public void eliminarHijoIzquierdo() {
		this.raiz.setHijoIzquierdo(null);
	}

	public void eliminarHijoDerecho() {
		this.raiz.setHijoDerecho(null);
	}

	public boolean esVacio() {
		return this.getDatoRaiz() == null;
	}

	public boolean esHoja() {
		return this.getDatoRaiz() != null && this.getHijoIzquierdo().esVacio() && this.getHijoDerecho().esVacio();
	}

	public int contarHojas(){
		return esHoja()? 1 : getHijoIzquierdo().esVacio()? 0 : getHijoIzquierdo().contarHojas() + (getHijoDerecho().esVacio()? 0 : getHijoDerecho().contarHojas());
	}

	public ArbolBinario<T> espejo(){
		ArbolBinario<T> espejo = new ArbolBinario<T>(getDatoRaiz());
		if(!getHijoDerecho().esVacio()){espejo.agregarHijoIzquierdo(getHijoDerecho().espejo());}
		if(!getHijoIzquierdo().esVacio()){espejo.agregarHijoDerecho(getHijoIzquierdo().espejo());}
		return espejo;
	}

	public void entreNiveles(int n, int m){
		ArbolBinario<T> arbol = null;
		ColaGenerica<ArbolBinario<T>> cola = new ColaGenerica<>();
		int contador = 0;
		cola.encolar(this);
		cola.encolar(null);
		while(!cola.esVacia() && contador <= m){
			arbol = cola.desencolar();
			if(arbol != null){
				if(contador >= n){
					System.out.print(arbol.getDatoRaiz() + " ");
				}
				if(!arbol.getHijoIzquierdo().esVacio()){
					cola.encolar(arbol.getHijoIzquierdo());
				}
				if(!arbol.getHijoDerecho().esVacio()){
					cola.encolar(arbol.getHijoDerecho());
				}
			}else if(!cola.esVacia()){
				if(contador >= n && contador <= m){
					System.out.println();
				}
				cola.encolar(null);
				contador++;
			}
		}
	}

	public void entreNiveles(){
		ListaGenerica<ListaGenerica<T>> listaDeListas = new ListaEnlazadaGenerica<>();
		entreNiveles(this, 0, listaDeListas);
		listaDeListas.comenzar();
		while(!listaDeListas.fin()){
			ListaGenerica<T> lista = listaDeListas.proximo();
			lista.comenzar();
			while(!lista.fin()){
				System.out.print(lista.proximo() + " ");
			}
			System.out.println();
		}
	}
	private void entreNiveles(ArbolBinario<T> arbol, int nivel, ListaGenerica<ListaGenerica<T>> listaDeListas){
		if(!arbol.esVacio()){
			if(listaDeListas.elemento(nivel+1) == null){
				ListaGenerica<T> lista = new ListaEnlazadaGenerica<>();
				lista.agregarFinal(arbol.getDatoRaiz());
				listaDeListas.agregarFinal(lista);
			}else{
				listaDeListas.elemento(nivel+1).agregarFinal(arbol.getDatoRaiz());
			}
			entreNiveles(arbol.getHijoIzquierdo(), nivel+1, listaDeListas);
			entreNiveles(arbol.getHijoDerecho(), nivel+1, listaDeListas);
		}
	}

	public void recorridoPorNiveles() {
		ArbolBinario<T> arbol = null;
		ColaGenerica<ArbolBinario<T>> cola = new ColaGenerica<>();
		cola.encolar(this);
		cola.encolar(null);
		while (!cola.esVacia()) {
			arbol = cola.desencolar();
			if (arbol != null) {
				System.out.print(arbol.getDatoRaiz() + " ");
				if (!arbol.getHijoIzquierdo().esVacio()) {
					cola.encolar(arbol.getHijoIzquierdo());
				}
				if (!arbol.getHijoDerecho().esVacio()){
					cola.encolar(arbol.getHijoDerecho());
				}
			} else if (!cola.esVacia()) {
				System.out.println();
				cola.encolar(null);
			}
		}
	}

	public void imprimirInOrden(){
		if(!esVacio()){
			getHijoIzquierdo().imprimirInOrden();
			System.out.println(getDatoRaiz());
			getHijoDerecho().imprimirInOrden();
		}
	}

	//Imprime el arbol en su forma de arbol
	public void imprimir() { if((!this.esVacio())){ imprimir("", true);} }
	private void imprimir(String prefijo, boolean esCola) {
		System.out.println(prefijo + (esCola ? "└── " : "├── ") + this.getDatoRaiz());
		if(!getHijoDerecho().esVacio()) {
			getHijoDerecho().imprimir(prefijo + (esCola ? "    " : "│   "), false);
		}
		if(!getHijoIzquierdo().esVacio()) {
			getHijoIzquierdo().imprimir(prefijo + (esCola ? "    " : "│   "), true);
		}
	}
}