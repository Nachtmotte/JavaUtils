package arbolGeneral;

import listaGenerica.*;
import pilaCola.ColaGenerica;

public class ArbolGeneral<T> {

	private NodoGeneral<T> raiz;

	public ArbolGeneral() {
		this.raiz = null;
	}

	public ArbolGeneral(T dato) {
		this.raiz = new NodoGeneral<T>(dato);
	}

	public ArbolGeneral(T dato, ListaGenerica<ArbolGeneral<T>> lista) {
		this(dato);
		ListaGenerica<NodoGeneral<T>> hijos = new ListaEnlazadaGenerica<>();
		lista.comenzar();
		while (!lista.fin()) {
			ArbolGeneral<T> arbolTemp = lista.proximo();
			hijos.agregarFinal(arbolTemp.getRaiz());
		}
		raiz.setListaHijos(hijos);
	}

	private ArbolGeneral(NodoGeneral<T> nodo) {
		this.raiz = nodo;
	}

	private NodoGeneral<T> getRaiz() {
		return this.raiz;
	}

	public T getDatoRaiz() {
		return this.raiz.getDato();
	}
	
	public ListaGenerica<ArbolGeneral<T>> getHijos() {
		ListaGenerica<ArbolGeneral<T>> lista = new ListaEnlazadaGenerica<>();
		ListaGenerica<NodoGeneral<T>> hijos =  this.getRaiz().getHijos();
		lista.comenzar();
		hijos.comenzar();
		while (!hijos.fin()) {
			lista.agregarFinal(new ArbolGeneral<T>(hijos.proximo()));
		}
		return lista;
	}

	public void agregarHijo(ArbolGeneral<T> unHijo) {
		NodoGeneral<T> hijo = unHijo.getRaiz();
		this.raiz.getHijos().agregarFinal(hijo);
	}
	
	public void eliminarHijo(ArbolGeneral<T> unHijo) {
		NodoGeneral<T> hijo = unHijo.getRaiz();
		boolean ok = false;
		ListaGenerica<NodoGeneral<T>> listaHijos = this.getRaiz().getHijos();
		listaHijos.comenzar();
		while (!listaHijos.fin() && !ok) {
			NodoGeneral<T> hijoTemp = listaHijos.proximo();
			if (hijoTemp.getDato().equals(hijo.getDato())) {
				ok = listaHijos.eliminar(hijoTemp);
			}
		}
	}

	public ListaEnlazadaGenerica<T> preOrden(){
		ListaEnlazadaGenerica<T> lista = new ListaEnlazadaGenerica<>();
		if(!this.esVacio()){ preOrden(lista); }
		return lista;
	}
	private void preOrden(ListaEnlazadaGenerica<T> lista){
		lista.agregarFinal(this.getDatoRaiz());
		ListaGenerica<ArbolGeneral<T>> hijos = this.getHijos();
		hijos.comenzar();
		while(!hijos.fin()){
			hijos.proximo().preOrden(lista);
		}
	}

	public ListaEnlazadaGenerica<T> inOrden(){
		ListaEnlazadaGenerica<T> lista = new ListaEnlazadaGenerica<>();
		if(!this.esVacio()){ inOrden(lista); }
		return lista;
	}
	private void inOrden(ListaEnlazadaGenerica<T> lista){
		ListaGenerica<ArbolGeneral<T>> hijos = this.getHijos();
		hijos.comenzar();
		if(!hijos.fin()){
			hijos.proximo().inOrden(lista);
		}
		lista.agregarFinal(this.getDatoRaiz());
		while(!hijos.fin()){
			hijos.proximo().inOrden(lista);
		}
	}

	public ListaEnlazadaGenerica<T> postOrden(){
		ListaEnlazadaGenerica<T> lista = new ListaEnlazadaGenerica<>();
		if(!this.esVacio()){ postOrden(lista); }
		return lista;
	}
	private void postOrden(ListaEnlazadaGenerica<T> lista){
		ListaGenerica<ArbolGeneral<T>> hijos = this.getHijos();
		hijos.comenzar();
		while(!hijos.fin()){
			hijos.proximo().postOrden(lista);
		}
		lista.agregarFinal(this.getDatoRaiz());
	}

	public ListaEnlazadaGenerica<T> recorridoPorNiveles() {
		ListaEnlazadaGenerica<T> lista = new ListaEnlazadaGenerica<>();
		ArbolGeneral<T> arbol = null;
		ColaGenerica<ArbolGeneral<T>> cola = new ColaGenerica<>();
		cola.encolar(this);
		cola.encolar(null);
		while (!cola.esVacia()) {
			arbol = cola.desencolar();
			if (arbol != null) {
				lista.agregarFinal(arbol.getDatoRaiz());
				ListaGenerica<ArbolGeneral<T>> hijos = arbol.getHijos();
				hijos.comenzar();
				while (!hijos.fin()) {
					cola.encolar(hijos.proximo());
				}
			} else if (!cola.esVacia()) {
				cola.encolar(null);
			}
		}
		return lista;
	}

	//Imprime el arbol en su forma de arbol
	public void imprimir() { if(!this.esVacio()){ imprimir("", true);} }
	private void imprimir(String prefijo, boolean esCola) {
		System.out.println(prefijo + (esCola ? "└── " : "├── ") + this.getDatoRaiz());
		ListaGenerica<ArbolGeneral<T>> hijos = this.getHijos();
		for (int i = hijos.tamanio(); i > 1; i--) {
			hijos.elemento(i).imprimir(prefijo + (esCola ? "    " : "│   "), false);
		}
		if (!hijos.esVacia()) {
			hijos.elemento(1).imprimir(prefijo + (esCola ? "    " : "│   "), true);
		}
	}

	public ListaEnlazadaGenerica<T> hojas(){
		ListaEnlazadaGenerica<T> lista = new ListaEnlazadaGenerica<>();
		if(!this.esVacio()){ buscarHojas(lista); }
		return lista;
	}
	private void buscarHojas(ListaEnlazadaGenerica<T> lista){
		if(this.esHoja()){
			lista.agregarFinal(this.getDatoRaiz());
		}else {
			ListaGenerica<ArbolGeneral<T>> hijos = this.getHijos();
			hijos.comenzar();
			while (!hijos.fin()) {
				hijos.proximo().buscarHojas(lista);
			}
		}
	}

	public int altura() { return esVacio()? -1 : altura(0); }
	private int altura(int nivel) {
		int max = nivel;
		ListaGenerica<ArbolGeneral<T>> hijos = this.getHijos();
		hijos.comenzar();
		while (!hijos.fin()) {
			max = Math.max(max, hijos.proximo().altura(nivel + 1));
		}
		return max;
	}

	public int nivel(T dato){
		return esVacio()? -1 : nivel(dato, 0);
	}
	private int nivel(T dato, int profundidad){
		int nivel = - 1;
		if(getDatoRaiz() == dato) {
			return profundidad;
		}else{
			ListaGenerica<ArbolGeneral<T>> hijos = getHijos();
			hijos.comenzar();
			while(!hijos.fin() && (nivel == -1)){
				nivel = hijos.proximo().nivel(dato, profundidad + 1);
			}
		}
		return nivel;
	}

	public int ancho(){
		if(!esVacio()){
			int contador = 0;
			int max = 0;
			ArbolGeneral<T> arbol = null;
			ColaGenerica<ArbolGeneral<T>> cola = new ColaGenerica<>();
			cola.encolar(this);
			cola.encolar(null);
			while(!cola.esVacia()){
				arbol = cola.desencolar();
				if(arbol != null){
					contador++;
					ListaGenerica<ArbolGeneral<T>> hijos = arbol.getHijos();
					hijos.comenzar();
					while(!hijos.fin()){
						cola.encolar(hijos.proximo());
					}
				}else if(!cola.esVacia()){
					cola.encolar(null);
					max = Math.max(max, contador);
					contador = 0;
				}else if(contador > max){
					max = contador;
				}
			}
			return max;
		}
		return 0;
	}

	public boolean esHoja(){
		return !esVacio() && getRaiz().getHijos().esVacia();
	}

	public boolean esVacio(){
		return getRaiz() == null;
	}

	private ArbolGeneral<T> buscarNodo(T a){
		ArbolGeneral<T> nodo = null;
		if(!esVacio()){
			if(a == getDatoRaiz()){
				return this;
			}
			ListaGenerica<ArbolGeneral<T>> hijos = getHijos();
			hijos.comenzar();
			while(!hijos.fin() && (nodo == null)){
				nodo = hijos.proximo().buscarNodo(a);
			}
		}
		return nodo;
	}
	private boolean buscarCamino(ArbolGeneral<T> a, T b){
		boolean encontrado = false;
		if(b == a.getDatoRaiz()){
			return true;
		}
		ListaGenerica<ArbolGeneral<T>> hijos = a.getHijos();
		hijos.comenzar();
		while(!hijos.fin() && !encontrado){
			encontrado = buscarCamino(hijos.proximo(), b);
		}
		return encontrado;
	}
	public Boolean esAncestro(T a, T b){
		if(a != b){
			ArbolGeneral<T> nodo = buscarNodo(a);
			return !(nodo == null) && buscarCamino(nodo, b);
		}
		return false;
	}
}
