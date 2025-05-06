package exp2_s8_nicole_plaza;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Cliente {
    private static int contador = 1;
    private int id;
    private String nombre;
    private int edad;
    
    public Cliente(String nombre, int edad){
        this.id = contador++;
        this.nombre = nombre;
        this.edad = edad;
    }
    
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    
    public int getEdad(){
        return edad;
    }
    public String toString(){
        return "Cliente#" + id + ": " + nombre + " (" + edad + ")";
    }
}

class Reserva {
    private static int contador = 1;
    private int id;
    private Cliente cliente;
    private String asiento;
    private String zona;
    
    public Reserva(Cliente cliente, String zona, String asiento){
        this.id = contador++;
        this.cliente = cliente;
        this.zona = zona;
        this.asiento = asiento;
    }
    
    public int getId(){
        return id;
    }
    public Cliente getCliente(){
        return cliente;
    }
    public String getAsiento(){
        return asiento;
    }
    public String getZona(){
        return zona;
    }
    
    public String toString(){
        return "Reserva#" + id + ": " + cliente + ", Asiento: " + asiento + ", Zona: " + zona;
    }
}

class Venta {
    private static int contador = 1;
    private int id;
    private Reserva reserva;
    private double total;
    
    public Venta(Reserva reserva, double total){
        this.id = contador++;
        this.reserva = reserva;
        this.total = total;
    }
    
    public int getId(){
        return id;
    }
    public Reserva getReserva(){
        return reserva;
    }
    public double getTotal(){
        return total;
    }
    
    public String toString(){
        return "Venta#" + id + ": " + reserva + ", Total: $" + (int)total;
    }
}
public class Exp2_S8_Nicole_Plaza {
    static String nombreTeatro = "Teatro Moro";
    static ArrayList<Cliente> clientes = new ArrayList<>();
    static ArrayList<Reserva> reservas = new ArrayList<>();
    static ArrayList<Venta> ventas = new ArrayList<>();
    static Map<String, Double> descuentos = new HashMap<>();
    static Set<String> asientosReservados = new HashSet<>();
    
    static {
        descuentos.put("Estudiante", 0.10);
        descuentos.put("Tercera edad", 0.15);
    }
    
    static Map<String, Integer> preciosZona = Map.of("VIP", 30000, "PLATEA BAJA", 18000, "PLATEA ALTA", 15000, "PALCOS", 9000);
    static Map<String, List<String>> asientosPorZona = Map.of("VIP", Arrays.asList("A1", "A2", "A3", "A4", "A5"), "PLATEA BAJA", Arrays.asList("B1", "B2", "B3", "B4", "B5"), "PLATEA ALTA", Arrays.asList("C1", "C2", "C3", "C4", "C5"), "PALCOS", Arrays.asList("D1", "D2", "D3", "D4", "D5"));
    
    public static void mostrarMenu(){
        System.out.println("");
        System.out.println("- - - -  MENU PRINCIPAL - - - -");
        System.out.println(" 1. Comprar entradas      ");
        System.out.println(" 2. Ver resumen de ventas ");
        System.out.println(" 3. Imprimir boleta       ");
        System.out.println(" 4. Cancelar reserva      ");
        System.out.println(" 5. Salir                 ");
        System.out.println("- - - - - - - - - - - - - - - -");
        System.out.println("");
        System.out.println("Seleccione una opcion (1 - 5): ");        
    }
    
    public static void mostrarPlano(){
        System.out.println("- - - - - - - - - PLANO TEATRO MORO - - - - - - -");
        asientosPorZona.forEach((zona, asientos) -> {
        System.out.println(zona + ": " + asientos);
        System.out.println("Valor -> $" + preciosZona.get(zona));
    });
        System.out.println("");
    }
    
    public static void ventaEntradas(Scanner SC) {
        try {
            System.out.print("Nombre cliente: ");
            String nombre = SC.nextLine().trim();
            if(nombre.isEmpty()) {
              System.out.println("El nombre no puede estar vacío.");
              return;
            }

            System.out.print("Edad del cliente: ");
            int edad = Integer.parseInt(SC.nextLine());
            if(edad < 5 || edad > 120) {
                System.out.println("Edad fuera de rango (debe estar entre 5 y 120 años).");
                return;
            }
        
            Cliente cliente = new Cliente(nombre, edad);
            clientes.add(cliente);
        
            mostrarPlano();
            System.out.println("Zonas disponibles: " + asientosPorZona.keySet());
            System.out.print("Seleccione una zona: ");
            String zona = SC.nextLine().toUpperCase();
        
            if(!asientosPorZona.containsKey(zona)) {
                System.out.println("Zona inválida.");
                return;
            }
        
            System.out.print("Asientos disponibles en " + zona + ": ");
            asientosPorZona.get(zona).stream().filter(asiento -> !asientosReservados.contains(asiento)).forEach(asiento -> System.out.print(asiento + " "));
            System.out.println();
        
            System.out.print("Seleccione un asiento: ");
            String asiento = SC.nextLine().toUpperCase();
        
            if(!asientosPorZona.get(zona).contains(asiento)) {
                System.out.println("El asiento no pertenece a esta zona.");
                return;
            }
        
            if(asientosReservados.contains(asiento)) {
                System.out.println("Asiento ya reservado.");
                return;
            }
        
            double precio = preciosZona.get(zona);
            double descuento = (edad < 18) ? descuentos.get("Estudiante") : (edad >= 60 ? descuentos.get("Tercera edad") : 0);
            double total = precio * (1 - descuento);
        
            System.out.println("Total a pagar: $" + (int)total);
            System.out.print("¿Confirmar compra? (Si/No): ");
            String confirmacion = SC.nextLine().toUpperCase();
        
            if(!confirmacion.equals("SI")) {
                System.out.println("Compra cancelada.");
                return;
            }
        
            asientosReservados.add(asiento);
            Reserva reserva = new Reserva(cliente, zona, asiento);
            reservas.add(reserva);
            Venta venta = new Venta(reserva, total);
            ventas.add(venta);
            System.out.println("Venta realizada con éxito. Total pagado: $" + (int)total);
        
        } catch(NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido para la edad.");
        } catch(Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public static void cancelarReserva(Scanner SC) {
        /*/ este metodo fue un poco confuso porque la verdad
        es que no entendí del todo como funcionaban las ID, me disculpo
        si no funciona como se esperaba.
        */
        
        if(reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
    
        System.out.print("Ingrese el ID de la reserva a cancelar: ");
        try {
            int id = Integer.parseInt(SC.nextLine());
            Reserva reserva = reservas.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
            
            if(reserva == null) {
                System.out.println("Reserva no encontrada.");
                return;
            }

            System.out.print("¿Está seguro de cancelar la reserva #" + id + "? (Si/No): ");
            String confirmacion = SC.nextLine().toUpperCase();
        
            if(!confirmacion.equals("Si")) {
                System.out.println("Cancelación abortada.");
                return;
            }
        
            reservas.remove(reserva);
            asientosReservados.remove(reserva.getAsiento());
            ventas.removeIf(v -> v.getReserva().getId() == id);
        
            System.out.println("Reserva y venta canceladas exitosamente.");
        
        } catch(NumberFormatException e) {
            System.out.println("ID inválido. Debe ingresar un número.");
        }
    }
    
    public static void generarBoleta(){
    if (ventas.isEmpty()) {
        System.out.println("No hay ventas registradas.");
        return;
    }
    Venta venta = ventas.get(ventas.size() - 1);  
    
    Cliente cliente = venta.getReserva().getCliente();
    String zona = venta.getReserva().getZona();
    String asiento = venta.getReserva().getAsiento();
    double precioBase = preciosZona.get(zona);
    double total = venta.getTotal(); 

    System.out.println("- - - - - - - -  - - Boleta - - - -  - - -");
    System.out.println("           " + nombreTeatro + "     ");
    System.out.println("---------------------------------------");
    System.out.println("Cliente: " + cliente.getNombre());
    System.out.println("ID Cliente: " + cliente.getId());
    System.out.println("Edad: " + cliente.getEdad() + " años");
    System.out.println("Zona: " + zona);
    System.out.println("Asiento: " + asiento);
    System.out.println("Precio base: $" + (int)precioBase);
    System.out.println("TOTAL PAGADO: $" + (int)total);
    System.out.println("---------------------------------------");
    System.out.println("Gracias por su compra en " + nombreTeatro + "!");
    System.out.println("- - - - - - - - - - - - - - - - - - - -");

    }
    
    public static void resumenVentas(){
        int totalVentas = ventas.size();
        double totalIngresos = ventas.stream().mapToDouble(Venta::getTotal).sum();
        System.out.println("Resumen: " + totalVentas + " ventas realizadas. Total ingresos: $" + (int)totalIngresos);
    }
    
    public static void main(String[] args){
        Scanner SC = new Scanner(System.in);
        while(true){
            mostrarMenu();
            String input = SC.nextLine();
            switch(input){
                case "1" -> ventaEntradas(SC);
                case "2" -> resumenVentas();
                case "3" -> generarBoleta();
                case "4" -> cancelarReserva(SC);
                case "5" -> {
                    System.out.println("Gracias por el usar el sistema de ventas del Teatro Moro, hasta pronto!");
                    return;
                }
                default -> {
                    System.out.println("Opcion invalida, intente nuevamente.");
                }
            }
        }
    }
}