package Exp2_S7_Nicole_Plaza;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Exp2_S7_Nicole_Plaza {
    
    static double totalIngresos = 0;
    static int totalEntradasVendidas = 0;
    static double descuento;
    
    static ArrayList<String> asientosReservados = new ArrayList<>();      
    static List<String> ventasUbicacion = new ArrayList<>();
    static List<Double> ventasCostoBase = new ArrayList<>();
    static List<Double> ventasDescuento = new ArrayList<>();
    static List<Double> ventasPrecioFinal = new ArrayList<>();
    
    // precios
    int[]precioEntradas = {30000, 18000, 15000, 9000};
    String[]tipoEntradas = {"VIP", "Platea Baja", "Platea Alta", "Palcos"};
    
    // para calculos y varios
    static String nombreTeatro = "Teatro Moro";
    static String tarifa = "";
    static String zona = "";
    static String letraZona = "";
    
    static String asiento = "";
    static int precioEntrada = 0;
    static int edad = 0;
 
    
    public static void mostrarMenu(){
        System.out.println("");
        System.out.println("- - - -  MENU PRINCIPAL - - - -");
        System.out.println(" 1. Comprar entradas      ");
        System.out.println(" 2. Ver resumen de ventas ");
        System.out.println(" 3. Imprimir boleta       ");
        System.out.println(" 4. Salir                 ");
        System.out.println("- - - - - - - - - - - - - - - -");
        System.out.println("");
        System.out.println("Seleccione una opcion (1 - 4): ");
    }
    
    public static void mostrarPlano(){
        System.out.println("- - - - - - - - - PLANO TEATRO MORO - - - - - - -");
        System.out.println("1. ENTRADA VIP: A1, A2, A3, A4, A5");
        System.out.println("   VALOR -> $30.000 PESOS");
        System.out.println("");
        System.out.println("2. ENTRADA PLATEA BAJA: B1, B2, B3, B4, B5");
        System.out.println("   VALOR -> $18.000 PESOS");
        System.out.println("");
        System.out.println("3. ENTRADA PLATEA ALTA: C1, C2, C3, C4, C5");
        System.out.println("   VALOR -> $15.000 PESOS");
        System.out.println("4. ENTRADA PALCOS: D1, D2, D3, D4, D5");
        System.out.println("   VALOR -> $9.000 PESOS");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println("");
    }
    
    public static void pedirZona(Scanner SC){
        System.out.println("Por favor elija una zona del teatro para reservar su asiento. Por ej: Palcos.");
        SC.nextLine();
        zona = SC.next().toUpperCase();
        
        switch(zona){
            case "VIP" -> {
                letraZona = "A";
                precioEntrada = 30000;
            }
            case "PLATEA BAJA" -> {
                letraZona = "B";
                precioEntrada = 18000;
            }
            case "PLATEA ALTA" -> {
                letraZona = "C";
                precioEntrada = 15000;
            }
            case "PALCOS" -> {
                letraZona = "D";
                precioEntrada = 9000;
            }
            default ->
                System.out.println("Zona invalida, intente nuevamente.");
        }
        System.out.println("Ha seleccionado la zona " + zona);
    }

    public static void pedirAsiento(Scanner SC){
        System.out.println("Ingrese el asiento que desea reservar (ej: B1): ");
        asiento = SC.next().toUpperCase();
        if(asiento.startsWith(letraZona)){
            System.out.println("Ha seleccionado el asiento: " + asiento);
        } else {
            System.out.println("El asiento elegido no corresponde a la zona seleccionada, intente nuevamente.");
            pedirAsiento(SC);
        }
    }
    
    public static void validarAsiento(Scanner SC){
        while(asientosReservados.contains(asiento)){
            System.out.println("El asiento " + asiento + " ya está reservado, elija otro.");
            pedirAsiento(SC);
            validarAsiento(SC);
        } 
            asientosReservados.add(asiento);
            System.out.println("Asiento reservado exitosamente.");
        }

    public static void pedirEdadYDescuento(Scanner SC){
        System.out.println("Por favor ingrese su edad: ");
        try {
        edad = Integer.parseInt(SC.next());
        } catch (NumberFormatException e){
            System.out.println("Por favor ingrese un valor numerico para la edad.");
        }
        if(edad <= 0 || edad > 120){
            System.out.println("La edad ingresada no es valida, intente nuevamente.");
            pedirEdadYDescuento(SC);
        } else {
        if(edad >= 60){
            tarifa = "Adulto mayor";
            descuento = 0.15;
        } else if (edad <= 18){
            tarifa = "Estudiante";
            descuento = 0.10;
        } else if (edad > 18 && edad < 60){
            tarifa = "Adulto";
            descuento = 0;
        } else {
            System.out.println("Edad invalida, intente nuevamente.");
            pedirEdadYDescuento(SC);
        }
        }
    }

    public static void calcularPrecioFinal(Scanner SC){
        double precioFinal = precioEntrada - (precioEntrada * descuento);
        totalIngresos += precioFinal;
        totalEntradasVendidas++;
            
        ventasUbicacion.add(zona + " - " + asiento);
        ventasCostoBase.add((double)precioEntrada);
        ventasDescuento.add(descuento * 100);       
        ventasPrecioFinal.add(precioFinal);
    }
    
    public static void ventaEntradas(Scanner SC){
        boolean otraCompra = true;
        
        while(otraCompra) {
            mostrarPlano();
            pedirZona(SC);
            pedirAsiento(SC);
            validarAsiento(SC);
            pedirEdadYDescuento(SC);
            calcularPrecioFinal(SC);
            
            System.out.println("¿Desea realizar otra compra? (Si/No): ");
            String respuesta = SC.next().toLowerCase();
            if(respuesta.equals("no") || respuesta.equals("n")){
                otraCompra = false;
            }
        }
        System.out.println("Gracias por su compra, hasta pronto!");
    }
    
    public static void mostrarResumenVentas(Scanner SC){
        DecimalFormat df = new DecimalFormat("#,###,###");
        System.out.println("- - - - - RESUMEN DE VENTAS - - - - -");
        System.out.println("");
        
        if(ventasUbicacion.isEmpty()){
            System.out.println("No se han realizado ventas aun.");
        } else {
            for(int i = 0; i < ventasUbicacion.size(); i++){
                System.out.println("Venta " + (i + 1) + ": ");
                System.out.println("- Ubicacion: " + ventasUbicacion.get(i));
                System.out.println("- Precio final: $ " + df.format(ventasCostoBase.get(i)));
                System.out.println("- Descuento aplicado: " + ventasDescuento.get(i) + "%");
                System.out.println("");
            }
            System.out.println("Total de entradas vendidas: " + totalEntradasVendidas);
            System.out.println("Total de ingresos generados: $" + df.format(totalIngresos));
        }
    }
    
    public static void generarBoleta(Scanner SC){
        DecimalFormat df = new DecimalFormat("#,###,###");
        
        if(ventasUbicacion.isEmpty()){
            System.out.println("No hay compras registradas para generar boleta.");
            return;
        }
        for(int i = 0; i < ventasUbicacion.size(); i++){
            System.out.println("- - - -  - - - - - - - - - - - - -");
            System.out.println("");
            System.out.println("           " + nombreTeatro + "     ");
            System.out.println("");
            System.out.println("- - - - - - - - - - - - - - - - -");
            System.out.println("");
            System.out.println("Ubicacion: " + ventasUbicacion.get(i));
            System.out.println("Costo Base: $" + df.format(ventasCostoBase.get(i)));
            System.out.println("Descuento Aplicado: " + ventasDescuento.get(i) + "%");
            System.out.println("Costo Final: $" + df.format(ventasPrecioFinal.get(i)));
            System.out.println("- - - - - - - - - - - - - - - - -");
            System.out.println("");
            System.out.println("Gracias por su visita al " + nombreTeatro);
            System.out.println("");
            System.out.println("- - - - - - - - - - - - - - - - -");
        }
    }
     
    public static void main(String[] args){
        boolean seguir = true;
        Scanner SC = new Scanner(System.in);
        while(seguir){
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println("                                                                 ");
            System.out.println("  Bienvenido al sistema de ventas de entradas de Teatro Moro     ");
            System.out.println("                                                                 ");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            mostrarMenu();
            int opcion = SC.nextInt();
            
            switch(opcion){
                case 1 -> {
                    ventaEntradas(SC);
                }
                case 2 -> {
                    mostrarResumenVentas(SC);
                }
                case 3 -> {
                    generarBoleta(SC);
                }
                case 4 -> {
                    System.out.println("Gracias por usar el sistema de ventas, hasta pronto!");
                    seguir = false;
                }
            }
        }
    }
}
