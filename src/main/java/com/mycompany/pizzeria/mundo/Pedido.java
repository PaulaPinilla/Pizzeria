
package com.mycompany.pizzeria.mundo;

/**
 *
 * @author 
 */

public class Pedido implements Comparable<Pedido>  {
    
    private String AutorPedido;
    private double precio;
    private int cercania;

    // Constructor
    public Pedido(String nombreAutor, double precio, int cercania) {
        this.AutorPedido = nombreAutor;
        this.precio = precio;
        this.cercania = cercania;
    }

    // Getters
    public String getAutorPedido() {
        return AutorPedido;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCercania() {
        return cercania;
    }

    @Override
    public int compareTo(Pedido otro) {
        // Si se compara por precio (para max-heap), retornamos de mayor a menor
        return Double.compare(this.precio, otro.precio);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "nombreAutor='" + AutorPedido + '\'' +
                ", precio=" + precio +
                ", cercania=" + cercania +
                '}';
    }
}
