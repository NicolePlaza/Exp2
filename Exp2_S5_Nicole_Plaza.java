/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp2_s5_nicole_plaza;

/**
 *
 * @author nicol
 */
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;

class Entrada {
    int numero;
    String zona;
    String tarifa;
    double precioFinal;
    
    public Entrada(int numero, String zona, String tarifa, double precioFinal) {
        this.numero = numero;
        this.zona = zona;
        this.tarifa = tarifa;
        this.precioFinal = precioFinal;
    }
}

public class Exp2_S5_Nicole_Plaza {
    static ArrayList<Entrada> entradasVendidas = new ArrayList<>();
    static int totalEntradasVendidas = 0;
    static double ingresosTotales = 0.0;
    static double totalCompradores = 0;
    
    public static void main(String[] args) {

        DecimalFormat formato = new DecimalFormat("#,###");
        Scanner SC = new Scanner(System.in);

        int entrVIP = 30000;
        int entrPlateaBaja = 15000;
        int entrPlateaAlta = 18000;
        int entrPalcos = 13000;

        int contadorEntradas = 1;

        for (int i = 0; i < 10; i++) {
            System.out.println("Bienvenido al sistema de ventas de entradas del Teatro Moro");
            System.out.println("MENU PRINCIPAL: ");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Promociones");
            System.out.println("3. Busqueda de entradas");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Salir");

            int opcion = SC.nextInt();

            if (opcion == 1) {
                System.out.println("Lista de zonas disponibles: ");
                System.out.println("1. VIP ($30.000)");
                System.out.println("2. Platea Baja ($15.000)");
                System.out.println("3. Platea Alta ($18.000)");
                System.out.println("4. Palcos ($13.000)");
                System.out.println("Elija una zona (1 - 4): ");
                int zona = SC.nextInt();

                String nombreZona = "";
                int precioBase = 0;

                if (zona == 1) {
                    nombreZona = "VIP";
                    precioBase = entrVIP;
                } else if (zona == 2) {
                    nombreZona = "Platea Baja";
                    precioBase = entrPlateaBaja;
                } else if (zona == 3) {
                    nombreZona = "Platea Alta";
                    precioBase = entrPlateaAlta;
                } else if (zona == 4) {
                    nombreZona = "Palcos";
                    precioBase = entrPalcos;
                } else {
                    System.out.println("Zona invalida. Intente nuevamente.");
                    continue;
                }

                System.out.println("Ingrese su edad:");
                int edad = SC.nextInt();
                if (edad <= 0 || edad > 120) {
                    System.out.println("Edad invalida. Intente nuevamente.");
                    continue;
                }

                String tarifa = "";
                double descuento = 0;

                if (edad >= 60) {
                    tarifa = "Adulto mayor";
                    descuento = 0.15;
                } else if (edad <= 18) {
                    tarifa = "Estudiante";
                    descuento = 0.10;
                } else {
                    tarifa = "Adulto";
                }

                System.out.println("¿Cuantas entradas desea comprar?");
                int cantidadEntradas = SC.nextInt();
                if (cantidadEntradas <= 0) {
                    System.out.println("Cantidad invalida.");
                    continue;
                }

                double descuentoExtra = 0;
                if (cantidadEntradas > 10) {
                    descuentoExtra = 0.15;
                } else if (cantidadEntradas > 5) {
                    descuentoExtra = 0.10;
                }

                double precioFinal = precioBase * (1 - descuento) * (1 - descuentoExtra);

                for (int j = 0; j < cantidadEntradas; j++) {
                    Entrada nuevaEntrada = new Entrada(contadorEntradas, nombreZona, tarifa, precioFinal);
                    entradasVendidas.add(nuevaEntrada);
                    totalEntradasVendidas++;
                    ingresosTotales += precioFinal;
                    System.out.println("Entrada N° " + contadorEntradas + " registrada con exito. Precio: $" + formato.format(precioFinal));
                    contadorEntradas++;
                }
                totalCompradores++;
                
                System.out.println("Resumen de compra: ");
                System.out.println("Zona: " + nombreZona);
                System.out.println("Edad: " + edad + "años (" + tarifa + ")");
                System.out.println("Entradas: " + cantidadEntradas);
                System.out.println("Precio normal: $" + formato.format(precioBase));
                System.out.println("Descuento: " + (int)((descuento + descuentoExtra) * 100) + "%");
                System.out.println("Total: $" + formato.format(precioFinal * cantidadEntradas));
                System.out.println("");
            }
        if (opcion == 2){
            System.out.println("Promociones disponibles: ");
            System.out.println("1. Por compras sobre 5 entradas, descuento de 10%");
            System.out.println("2. Por compras sobre 10 entradas, descuento de 15%");
        }
        
        if (opcion == 3){
            System.out.println("Seleccione el tipo de busqueda: ");
            System.out.println("1. Por numero de entrada");
            System.out.println("2. Por zona");
            System.out.println("3. Por tipo de entrada (tarifa)");
            int tipoBusqueda = SC.nextInt();
            boolean encontrada = false;
            
            if (tipoBusqueda == 1){
                System.out.println("Ingrese el numero de entrada: ");
                int numBusqueda = SC.nextInt();
                for (Entrada e : entradasVendidas){
                    if (e.numero == numBusqueda){
                        System.out.println("Entrada encontrada: ");
                        System.out.println("Numero: " + e.numero);
                        System.out.println("Zona: " + e.zona);
                        System.out.println("Tarifa: " + e.tarifa);
                        
                        System.out.println("Precio final: $" + formato.format(e.precioFinal));
                        encontrada = true;
                        break;
                    }
                }
            } else if (tipoBusqueda == 2){
                SC.nextLine();
                System.out.println("Ingrese la zona (VIP, Platea Baja, Platea Alta, Palcos): ");
                String zonaBusqueda = SC.nextLine();
                for (Entrada e : entradasVendidas){
                    if (e.zona.equalsIgnoreCase(zonaBusqueda)){
                        if (!encontrada)
                            System.out.println("Entradas encontrada: ");
                        System.out.println("Numero: " + e.numero);
                        System.out.println("Zona: " + e.zona);
                        System.out.println("Tarifa: " + e.tarifa);
                        System.out.println("Precio final: $" + formato.format(e.precioFinal));
                        encontrada = true;
                    }
                }
            } else if ((tipoBusqueda == 3)){
                SC.nextLine();
                System.out.println("Ingrese el tipo de entrada (Estudiante, Adulto Mayor o Adulto): ");
                String tipoBusquedaEntrada = SC.nextLine();
                for (Entrada e : entradasVendidas){
                    if (e.tarifa.equalsIgnoreCase(tipoBusquedaEntrada)){
                        if (!encontrada)
                            System.out.println("Entradas encontrada: ");
                        System.out.println("Numero: " + e.numero);
                        System.out.println("Zona: " + e.zona);
                        System.out.println("Tarifa: " + e.tarifa);
                        System.out.println("Precio final: $" + formato.format(e.precioFinal));
                        encontrada = true;
                    }
                }
            } else {
                System.out.println("Opcion de busqueda invalida. Intente nuevamente.");
            }
            if (!encontrada)
                System.out.println("No se encontraron entradas con los datos ingresados, intente nuevamente.");
        }
        
        if (opcion == 4){ 
            System.out.println("Ingrese el numero de entrada que desea eliminar: ");
            int numEliminar = SC.nextInt();
            boolean eliminada = false;
            
                for (int j = entradasVendidas.size() - 1; j >=0; j--) {
                if (entradasVendidas.get(j).numero == numEliminar){
                    entradasVendidas.remove(j);
                    System.out.println("Entrada N° " + numEliminar + " eliminada con exito.");
                    eliminada = true;
                    break;
                }
            }
            if (!eliminada){
                System.out.println("No se encontro uan entrada que coincida, intente nuevamente.");
            }
        }
        if (opcion == 5){
          System.out.println("Gracias por usar el sistema de ventas de Teatro Moro. Vuelva pronto.");  
          break;
        }
        }
    }
}
