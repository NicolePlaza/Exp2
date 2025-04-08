/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp2_s4_nicole_plaza;

/**
 *
 * @author nicol
 */
import java.text.DecimalFormat;
import java.util.Scanner;
public class Exp2_S4_Nicole_Plaza {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
  
        DecimalFormat formato = new
        DecimalFormat("#,###");
        Scanner SC = new Scanner(System.in);
        
        // variables precios
        int entrVIP = 30000;
        int entrPlateaBaja = 15000;
        int entrPlateaAlta = 18000;
        int entrPalcos = 13000;
        
        // variables para calculos
        String tarifa = "";
        String zona = "";
        String letraZona = "";
        int precioEntrada = 0;
        double precioFinal = 0;
        double descuento = 0;
        
        for (int i = 0; i < 10; i++){
        System.out.println("Bienvenido al sistema de ventas de entradas del Teatro Moro");
        System.out.println("MENU PRINCIPAL: ");
        System.out.println("1. Comprar entrada");
        System.out.println("2. Salir");
        
            int opcion = SC.nextInt();
            if (opcion == 1){
                System.out.println("Plano Teatro Moro: ");
                System.out.println("Zona VIP: A1, A2, A3, A4, A5");
                System.out.println("Zona Platea Baja: B1, B2, B3, B4, B5");
                System.out.println("Zona Platea Alta: C1, C2, C3, C4, C5");
                System.out.println("Zona Palcos: D1, D2, D3, D4, D5");
                
                System.out.println("Por favor elija una zona del teatro para reservar su asiento. Por ej: Palcos.");
                SC.nextLine();
                zona = SC.next().toUpperCase();
                if (zona.equals ("VIP")){
                    letraZona = "A";
                    precioEntrada = entrVIP;
                } else if (zona.equals ("PLATEA BAJA")){
                    letraZona = "B";
                    precioEntrada = entrPlateaBaja;
                } else if (zona.equals ("PLATEA ALTA")){
                    letraZona = "C";
                    precioEntrada = entrPlateaAlta;
                } else if (zona.equals ("PALCOS")){
                    letraZona = "D";
                    precioEntrada = entrPalcos;
                } else {
                    System.out.println("Zona invalida. Intente nuevamente.");
                    continue;
                }
                System.out.println("Ha seleccionado la zona " + zona);
                System.out.println("Ingrese el asiento que desea reservar. Por ej: A1.");
                String asiento = SC.next().toUpperCase();
                if (! asiento.startsWith(letraZona)){
                    System.out.println("El asiento elegido no corresponde a la zona seleccionada, intente nuevamente.");
                    continue;
                }
                System.out.println("Ha seleccionado el asiento: " + asiento);
                System.out.println("Por favor ingrese su edad: ");  
                int edad = SC.nextInt();
                if (edad <= 0 || edad >120){
                    System.out.println("La edad ingresada no es valida, intente nuevamente.");
                    continue;
                }
                
                if (edad >= 60){
                    tarifa = "Adulto mayor";
                    descuento = 0.15;
                } else if (edad <= 18) {
                    tarifa = "Estudiante"; 
                    descuento = 0.10;
                } else if (edad > 18 && edad < 60) {
                    tarifa = "Adulto";  
                    descuento = 0;
                } else {
                 System.out.println("Edad invalida. Intente nuevamente.");
                 continue;
                }
                
                boolean repetirCalculos = true;
                while (repetirCalculos){
                    double precioConDescuento = precioEntrada * descuento;
                precioFinal = precioEntrada - precioConDescuento;  
                if (precioFinal > 0 && descuento < 1) {
                    repetirCalculos = false;
                } else {
                    System.out.println("Lo sentimos, se ha producido un error al calcular el precio. Volviendo a calcular...");
                    descuento = 0;
                }
                } 

                System.out.println("Resumen de la compra: ");
                System.out.println("Asiento reservado: " + asiento);
                System.out.println("Precio base: $" + formato.format(precioEntrada));
                System.out.println("Descuento aplicado: " + formato.format(descuento * 100) + "%");
                System.out.println("Precio final  a pagar: $" + formato.format(precioFinal)); 
                
                System.out.println("¿Desea realizar otra compra? (Si/No)");
                String respuesta = SC.next().toLowerCase();
                if (respuesta.equals("no") || respuesta.equals("n")){
                    System.out.println("Gracias por su compra. Hasta pronto.");
                    break;
                }
            } else if (opcion == 2){
                System.out.println("Gracias por usar el sistema de ventas de Teatro Moro. Vuelva pronto.");
                break;
            } else if (opcion <= 0 || opcion > 2) {
                System.out.println("Opcion invalida, intente nuevamente.");
            }
        }
    }
}
