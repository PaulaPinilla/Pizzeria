
package com.mycompany.pizzeria.mundo;

import com.mycompany.pizzeria.estructuras.Heap;
import com.mycompany.pizzeria.estructuras.IHeap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Comparator;

public class Pizzeria {
    public final static String RECIBIR_PEDIDO = "RECIBIR";
    public final static String ATENDER_PEDIDO = "ATENDER";
    public final static String DESPACHAR_PEDIDO = "DESPACHAR";
    public final static String FIN = "FIN";

    private IHeap<Pedido> pedidosRecibidos;  // Max-heap de pedidos recibidos (ordenado por precio)
    private IHeap<Pedido> colaDespachos;      // Min-heap de pedidos atendidos (ordenado por cercanía)
    
    // Constructor
    public Pizzeria() {
        // Max-heap para pedidos recibidos, ordenado por precio (Mayor a menor)
        pedidosRecibidos = new Heap<>();
        
        // Min-heap para cola de despachos, ordenado por cercanía (Menor a mayor)
        colaDespachos = new Heap<>(Comparator.comparingInt(Pedido::getCercania));
    }

    public IHeap<Pedido> getPedidosRecibidos() {
        return pedidosRecibidos;
    }
    
    public IHeap<Pedido> getColaDespacho() {
        return colaDespachos;
    }

    // Agregar un nuevo pedido
    public void agregarPedido(String nombreAutor, double precio, int cercania) {
        Pedido nuevoPedido = new Pedido(nombreAutor, precio, cercania);
        this.pedidosRecibidos.add(nuevoPedido);  // Agregar al MaxHeap de pedidos recibidos
    }

    // Atender al pedido más importante de la cola (el de mayor precio)
    public Pedido atenderPedido() {
        Pedido p = pedidosRecibidos.poll();  // Poll obtiene y remueve el elemento de mayor prioridad
        if (p != null) {
            colaDespachos.add(p); // Se agrega al min-heap de despachos
        }
        return p;
    }
    
    // Despachar al pedido próximo a ser despachado (basado en la cercanía)
    public Pedido despacharPedido() {
        return colaDespachos.poll();  // Se saca el pedido por cercanía (orden en que se atendió)
    }

    // Retorna la lista de pedidos recibidos como una lista
    public ArrayList<Pedido> pedidosRecibidosList() {
        return ((Heap<Pedido>) pedidosRecibidos).toList();
    }

    // Retorna la lista de la cola de despachos
    public ArrayList<Pedido> colaDespachosList() {
        return ((Heap<Pedido>) colaDespachos).toList();
    }
}
