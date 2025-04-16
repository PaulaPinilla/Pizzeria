package com.mycompany.pizzeria.interfaz;

import com.mycompany.pizzeria.mundo.Pedido;
import com.mycompany.pizzeria.mundo.Pizzeria;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    static BufferedReader br;
    static String ln;
    static Pizzeria pizzeria;

    static final int INTERFAZ_USUARIO = 1;
    static final int INGRESO_MANUAL = 2;
    static final int ARCHIVO_DE_PRUEBAS = 3;
    static final int SALIR = 4;

    private static final String ARCHIVO_PRUEBAS = "data/tests.txt";

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        boolean end = false;

        while (!end) {
            imprimirArchivo("data/header.txt");
            Integer option = Integer.parseInt(br.readLine());
            if (option == INTERFAZ_USUARIO) {
                interfazUsuario();
            } else if (option == INGRESO_MANUAL) {
                imprimirArchivo("data/ingresoManual.txt");
                ingresoManual();
            } else if (option == ARCHIVO_DE_PRUEBAS) {
                ingresoArchivoPruebas();
            } else if (option == SALIR) {
                System.out.println("SALIENDO");
                end = true;
            }
        }
    }

    static void interfazUsuario() throws Exception {
        pizzeria = new Pizzeria();
        boolean end = false;
        while (!end) {
            imprimirArchivo("data/interfazUsuario.txt");
            System.out.println("Seleccione una opcion:");
            int option = Integer.parseInt(br.readLine());
            if (option == 1) {
                System.out.println("Escriba el primer nombre del autor del pedido:");
                String nombre = br.readLine();
                System.out.println("Escriba el precio del pedido:");
                double precio = Double.parseDouble(br.readLine());
                System.out.println("Escriba la cercania del pedido (1-5):");
                int cercania = Integer.parseInt(br.readLine());
                pizzeria.agregarPedido(nombre, precio, cercania);
                System.out.println("Pedido agregado.");
            } else if (option == 2) {
                Pedido p = pizzeria.atenderPedido();
                if (p == null) System.out.println("Cola vacía");
                else System.out.println("Pedido atendido: " + p.getAutorPedido() + " - " + p.getPrecio() + " - " + p.getCercania());
            } else if (option == 3) {
                Pedido p = pizzeria.despacharPedido();
                if (p == null) System.out.println("Cola vacía");
                else System.out.println("Pedido despachado: " + p.getAutorPedido() + " - " + p.getPrecio() + " - " + p.getCercania());
            } else if (option == 4) {
                imprimirColas(pizzeria);
            } else if (option == 5) {
                end = true;
            }
        }
    }

    static void ingresoComandos(BufferedReader br) throws Exception {
        pizzeria = new Pizzeria();

        while ((ln = br.readLine()) != null && !ln.equalsIgnoreCase("SALIR")) {
            String[] partes = ln.split(" ");
            String comando = partes[0];
            if (comando.equalsIgnoreCase(Pizzeria.RECIBIR_PEDIDO)) {
                String nombre = partes[1];
                Double precio = Double.parseDouble(partes[2]);
                Integer cercania = Integer.parseInt(partes[3]);
                pizzeria.agregarPedido(nombre, precio, cercania);
            } else if (comando.equalsIgnoreCase(Pizzeria.ATENDER_PEDIDO)) {
                pizzeria.atenderPedido();
            } else if (comando.equalsIgnoreCase(Pizzeria.DESPACHAR_PEDIDO)) {
                pizzeria.despacharPedido();
            }
            imprimirColas(pizzeria);
        }
    }

    static void ingresoManual() throws Exception {
        ingresoComandos(br);
    }

    static void ingresoArchivoPruebas() throws Exception {
        InputStream in = Main.class.getClassLoader().getResourceAsStream(ARCHIVO_PRUEBAS);
        if (in == null) {
            System.out.println("Archivo de pruebas no encontrado");
            return;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            ingresoComandos(br);
        }
    }

    static void imprimirColas(Pizzeria pizzeria) {
        imprimirColaPedidosRecibidos(pizzeria);
        imprimirColaDespachos(pizzeria);
        System.out.println();
        System.out.println();
    }

    private static void imprimirColaPedidosRecibidos(Pizzeria pizzeria) {
        System.out.println("    PEDIDOS RECIBIDOS    ");
        int index = 1;

        try {
            for (Pedido p : pizzeria.pedidosRecibidosList()) {
                System.out.printf("%d. %s ($%.2f)%n", index++, p.getAutorPedido(), p.getPrecio());
            }
        } catch (NullPointerException e) {
            System.out.println("Cola pizzería vacía");
        }
        System.out.println();
    }

    private static void imprimirColaDespachos(Pizzeria pizzeria) {
        System.out.println("      COLA DESPACHOS      ");
        int index = 1;
        try {
            for (Pedido p : pizzeria.colaDespachosList()) {
                System.out.printf("%d. %s (%d mts.)%n", index++, p.getAutorPedido(), p.getCercania());
            }
        } catch (NullPointerException e) {
            System.out.println("Cola pizzería vacía");
        }
    }

    static void imprimirArchivo(String nombre) {
        try (InputStream in = Main.class.getClassLoader().getResourceAsStream(nombre)) {
            if (in == null) {
                System.out.println("Archivo no encontrado: " + nombre);
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Error leyendo archivo: " + nombre);
            System.out.println(e.getMessage());
        }
    }
}
