/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp2_s6_nicole_plaza;

/**
 *
 * @author nicol
 */
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

public class Exp2_S6_Nicole_Plaza {

    static int totalEntradasVendidas = 0;
    static int ingresosTotales = 0;
    static int cantidadReservas = 0;
    static int contadorUltimaCompra = 0;
    static String [] boletaUltimaCompra = new String[20];
    static String[] asientosReservadosTemp = new String[20];
    static int contadorReservasTemp = 0;
    static Map<String, Long> tiempoReservas = new HashMap<>();
    
    static boolean[][] asientos = {
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false}
    };

        public static void mostrarPlano(){
    System.out.println("************* PLANO TEATRO MORO ************");
    System.out.println("1. ENTRADA VIP: A1, A2, A3, A4, A5");
    System.out.println("   VALOR -> $30.000 PESOS");
    System.out.println("");
    System.out.println("2. ENTRADA PLATEA BAJA: B1, B2, B3, B4, B5");
    System.out.println("   VALOR -> $18.000 PESOS");
    System.out.println("");
    System.out.println("3. ENTRADA PLATEA ALTA: C1, C2, C3, C4, C5");
    System.out.println("   VALOR -> $15.000 PESOS");
    System.out.println("");
    System.out.println("4. ENTRADA PALCOS: D1, D2, D3, D4, D5");
    System.out.println("   VALOR -> $9.000 PESOS");
    System.out.println("*******************************************");
    System.out.println("");
    }
        
    public static void mostrarAsientosReservados(String[] arreglo, int cantidad){
        for(int i = 0; i < cantidad; i++){
            if(arreglo[i] != null){
                System.out.println("- " + arreglo[i]);
            }
        }
    }
            
    public static boolean reservarAsiento(String asiento){
    String[] filas = {"A", "B", "C", "D"};
    int columna = -1, fila = -1;
    char filaLetra = ' ';
    char columnaNumero = ' ';
    
    if(asiento.length() == 2){
        filaLetra = asiento.charAt(0);
        columnaNumero = asiento.charAt(1);
    } else {
        System.out.println("Formato de asiento incorrecto, intente nuevamente.");
        return false;
    }
    for (int i = 0; i < filas.length; i++){
        if(filas[i].equalsIgnoreCase(String.valueOf(filaLetra))){
            fila = i;
            break;
        }
    }
    if(columnaNumero >= '1' && columnaNumero <= '5'){
        columna = columnaNumero - '1';
    }
    if(fila == -1 || columna == -1 || asientos[fila][columna]){
        System.out.println("Asiento no valido o ya reservado.");
        return false;
    } else {
        asientos[fila][columna] = true;
        System.out.println("Asiento " + asiento + " reservado con exito.");
        tiempoReservas.put(asiento, System.currentTimeMillis());
        System.out.println("Reserva iniciada a las: " + new Date(tiempoReservas.get(asiento)));
        return true;
    }
    }

    public static int calcularMontoTotal(String[] asientos, int cantidad){
        int total = 0;
        for (int i = 0; i < cantidad; i++){
            String asiento = asientos[i];
            char fila = asiento.charAt(0);
            
            switch(fila){
                case 'A', 'a' -> total += 30000;
                case 'B', 'b' -> total += 18000;
                case 'C', 'c' -> total += 15000;
                case 'D', 'd' -> total += 9000;
                default -> {
                    System.out.println("Fila desconocida para el asiento: " + asiento);
                }
            }
        }
        return total;
    }
    
    public static int contarAsientosOcupados(){
        int count = 0;
        for(boolean[] fila : asientos){
            for(boolean asiento : fila){
                if(asiento) count++;
            }
        }
        return count;
    }
    
    public static void liberarAsiento(String asiento){
        int fila = asiento.charAt(0) - 'A';
        int columna = Integer.parseInt(asiento.substring(1)) - 1;
        asientos[fila][columna] = false;
        tiempoReservas.remove(asiento);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here

        // variables generales
        DecimalFormat formato = new
        DecimalFormat("#,###");
        Scanner SC = new Scanner(System.in);
        int opcion;
        
        // variables de entradas
        int EntrVIP = 30000;
        int EntrPlateaBaja = 18000;
        int EntrPlateaAlta = 15000;
        int EntrPalcos = 9000;
        int precioEntrada = 0;
        int tipoDeEntrada;
        String asientoEscogido;
        String seguirReservando;
        String asientoDirecto;
        
        // Bienvenida al programa
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println("                                                                 ");
        System.out.println("  Bienvenido al sistema de ventas de entradas de Teatro Moro     ");
        System.out.println("                                                                 ");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        
        // Menú principal
        do{
        System.out.println("");
        System.out.println("*****  MENU PRINCIPAL *****");
        System.out.println("* 1. Reservar entradas    *");
        System.out.println("* 2. Comprar entradas     *");
        System.out.println("* 3. Modificar venta      *");
        System.out.println("* 4. Imprimir boleta      *");
        System.out.println("* 5. Salir                *");
        System.out.println("***************************");  
        System.out.println("");
        System.out.println("Seleccione una opcion (1 - 5): ");
        opcion = SC.nextInt();
        
        switch(opcion) {
            // opcion de reservar:
            case 1 -> {
                do{
             System.out.println("");
             System.out.println("Cargando plano, un momento por favor...");
             System.out.println("");
             try { 
                 for (int i = 0; i < 3; i++)
                    Thread.sleep(300);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             mostrarPlano();
             System.out.println("Seleccione el asiento que desea reservar (ej: C4): ");
             asientoEscogido = SC.next().toUpperCase();
             
             boolean reservado = reservarAsiento(asientoEscogido);
             if(reservado){
                 if(contadorReservasTemp < asientosReservadosTemp.length){
                     asientosReservadosTemp[contadorReservasTemp++] = asientoEscogido;
                     System.out.println("");
                     System.out.println("***NOTIFICACION: El asiento sera reservado por 4 minutos. Realice su compra antes de ese tiempo.***");
                     System.out.println("");
                     System.out.println("[DEBUG] Asiento " + asientoEscogido + " reservado. Tiempo: " + tiempoReservas.get(asientoEscogido));
                 } else{
                     System.out.println("No se pueden agregar más reservas temporales.");
                 }
             }
                    System.out.println("¿Desea reservar otro asiento? (Si/No)");
                    seguirReservando = SC.next();
                } while (seguirReservando.equalsIgnoreCase("si"));   

                System.out.println("Asientos reservados: ");
                mostrarAsientosReservados(asientosReservadosTemp, contadorReservasTemp);
            
                System.out.println("");
                System.out.println("Volviendo al menú principal...");
                System.out.println("");
                try { 
                 for (int i = 0; i < 3; i++)
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                 e.printStackTrace();
                }
            }
            // opción de comprar entradas:
            case 2 -> {
                if (contadorReservasTemp > 0){
                    System.out.println("Usted tiene las siguientes reservas activas: ");
                    long ahora = System.currentTimeMillis();
                    for(int i = 0; i < contadorReservasTemp; i++){
                        String asiento = asientosReservadosTemp[i];
                        if(tiempoReservas.containsKey(asiento)){
                            long tiempoExpiracion = ahora - tiempoReservas.get(asiento);
                            if(tiempoExpiracion > 240000){
                                System.out.println("[AVISO] La reserva del asiento " + asiento + " ha expirado.");
                                liberarAsiento(asiento);
                                asientosReservadosTemp[i] = null;
                            }
                        }
                        System.out.println("- " + asientosReservadosTemp[i]);
                    }
                    System.out.println("¿Desea confirmar su compra? (Si/No)");
                    String confirmar = SC.next();
                    if (confirmar.equalsIgnoreCase("si")){
                        totalEntradasVendidas += contadorReservasTemp;
                        ingresosTotales += calcularMontoTotal(asientosReservadosTemp, contadorReservasTemp);
                        contadorUltimaCompra = 0;
                        for(int i = 0; i < contadorReservasTemp; i++){
                            boletaUltimaCompra[contadorUltimaCompra++] = asientosReservadosTemp[i];
                        }
                        
                        for(int i = 0; i < contadorReservasTemp; i++){
                            String asiento = asientosReservadosTemp[i];
                            char fila = asiento.charAt(0);
                            int precio;
                            
                            switch (fila){
                                case 'A' -> precio = EntrVIP;
                                case 'B' -> precio = EntrPlateaBaja;
                                case 'C' -> precio = EntrPlateaAlta;
                                case 'D' -> precio = EntrPalcos;
                                default -> precio = 0;
                            }
                            System.out.println("Compra confirmada para el asiento " + asiento + " por $" + formato.format(precio));
                        }
                        System.out.println("Total pagado: $" + formato.format(calcularMontoTotal(asientosReservadosTemp, contadorReservasTemp)));
                        
                        for(int i = 0; i < asientosReservadosTemp.length; i++){
                            asientosReservadosTemp[i] = null;
                        }
                        contadorReservasTemp = 0;
                    }
                } else {
                    
                    System.out.println("No hay asientos reservados. Puede comprar directamente.");
                    mostrarPlano();
                    System.out.println("Seleccione tipo de entrada (1 - 4): ");
                    int tipoEntrada = SC.nextInt();
                    System.out.println("Seleccione el asiento que desea comprar (ej: C4): ");
                    asientoDirecto = SC.next().toUpperCase();
                    
                    if(reservarAsiento(asientoDirecto)){
                        totalEntradasVendidas++;
                        int precio = switch(tipoEntrada){
                            case 1 -> EntrVIP;
                            case 2 -> EntrPlateaBaja;
                            case 3 -> EntrPlateaAlta;
                            case 4 -> EntrPalcos;
                            default -> 0;     
                        };
                        ingresosTotales += precio;
                        System.out.println("Compra procesada con exito para el asiento " + asientoDirecto + " por $" + formato.format(precio) + "!");
                    }
                }
            }
            // opción de  modificar venta EXISTENTE
            case 3 -> {
            if(contadorReservasTemp > 0){
                System.out.println("Asientos reservados actualmente: ");
                for(int i = 0; i < contadorReservasTemp; i++){
                    System.out.println("- " + asientosReservadosTemp[i]);
                }
                System.out.println("Ingrese el nombre del asiento que desea modificar (ej: C4): ");
                String asientoModificar = SC.next().toUpperCase();
                
                boolean encontrado = false;
                for(int i = 0; i < contadorReservasTemp; i++){
                    if (asientosReservadosTemp[i].equals(asientoModificar)){
                        encontrado = true;
                        System.out.println("Asiento " + asientoModificar + " encontrado.");
                        System.out.println("¿Qué desea hacer?");
                        System.out.println("1. Cambiar asiento");
                        System.out.println("2. Eliminar asiento");
                        System.out.println("Ingrese una opcion: ");
                        int opcionMod = SC.nextInt();
                        
                        if(opcionMod == 1){
                            mostrarPlano();
                            System.out.println("Ingrese el nuevo numero de asiento que desea reservar: ");
                            String nuevoAsiento = SC.next().toUpperCase();
                            boolean reservadoNuevo = reservarAsiento(nuevoAsiento);
                            if(reservadoNuevo){
                                asientosReservadosTemp[i] = nuevoAsiento;
                                System.out.println("El asiento fue cambiado con exito.");
                                asientos[asientoModificar.charAt(0) - 'A'][asientoModificar.charAt(1) - 1] = false;
                            } else { 
                                System.out.println("No se pudo cambiar el asiento, intente nuevamente.");
                            }
                        } else if (opcionMod == 2){
                            asientos[asientoModificar.charAt(0) - 'A'][asientoModificar.charAt(1) - 1] = false;
                            for(int j = i; j < contadorReservasTemp - 1; j++){
                                asientosReservadosTemp[j] = asientosReservadosTemp[j + 1];
                            }
                            asientosReservadosTemp[contadorReservasTemp - 1] = null;
                            contadorReservasTemp--;
                            System.out.println("Asiento " + asientoModificar + " eliminado.");
                        } else {
                            System.out.println("Opcion invalida.");
                        }
                        System.out.println("Volviendo al menu principal...");
                        try { 
                        for (int k = 0; k < 3; k++)
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                        }
                        break;
                    }
                }
                if (!encontrado){
                    System.out.println("El asiento ingresado no se encuentra en sus reservas.");
                }
            } else{
                System.out.println("No hay reservas para modificar.");
            }
            }

            // opción de imprimir boleta
            case 4 -> {
                if(contadorUltimaCompra > 0){
                System.out.println("********************************************");
                System.out.println("****************** BOLETA ******************");
                System.out.println(" Teatro Moro - " + new Date());
                System.out.println("--------------------------------------------");
                for(int i = 0; i < contadorUltimaCompra; i++){
                    String asiento = boletaUltimaCompra[i];
                    char fila = asiento.charAt(0);
                    int precio = switch(fila){
                        case 'A' -> EntrVIP;
                        case 'B' -> EntrPlateaBaja;
                        case 'C' -> EntrPlateaAlta;
                        case 'D' -> EntrPalcos;
                        default -> 0;
                    };
                    System.out.println("Asiento: " + asiento + " - $" + formato.format(precio));
                }
                int total = calcularMontoTotal(boletaUltimaCompra, contadorUltimaCompra);
                    System.out.println("Total pagado: $" + formato.format(total));
                    System.out.println("Gracias por su compra, hasta pronto!");
                    System.out.println("********************************************");
                } else {
                    System.out.println("No hay ninguna compra registrada para imprimir boleta.");
                }
            }
        }
            System.out.println("[DEBUG] Estado actual: ");
            System.out.println("- Asientos ocupados: " + contarAsientosOcupados());
            System.out.println("- Reservas activas: " + contadorReservasTemp);
            System.out.println("- Tiempo reservas: " + tiempoReservas);
        } while (opcion != 5);
        System.out.println("Gracias por usar el sistema, hasta pronto!");
    }
}
                
