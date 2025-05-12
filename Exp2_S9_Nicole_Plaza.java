package exp2_s9_nicole_plaza;
import java.text.DecimalFormat;
import java.util.*;

class Cliente {
    public static int contadorId = 1;
    public final int id;
    public final String nombre;
    public final int edad;
    public final String sexo;
    
    public Cliente(String nombre, int edad, String sexo){
        this.id = contadorId++;
        this.nombre = nombre;
        this.edad = edad; 
        this.sexo = sexo;
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
    
    public String getSexo(){
        return sexo;
    }
    
    @Override
    public String toString(){
        return "Cliente#" + id + ": " + nombre + " (" + edad + ")";
    }
}

class Venta {
    public static int contadorId = 1;
    public final int id;
    public final Cliente cliente;
    public final String asiento;
    public final String zona;
    public final double total;
    public final double descuento;
    
    public Venta(Cliente cliente, String zona, String asiento, double total, double descuento){
        this.id = contadorId++;
        this.cliente = cliente;
        this.asiento = asiento;
        this.zona = zona;
        this.total = total;
        this.descuento = descuento;
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    public String getZona(){
        return zona;
    }
    public String getAsiento(){
        return asiento;
    }
    public double getTotal(){
        return total;
    }
    public double getDescuento(){
        return descuento;
    }
    public int getId(){
        return id;
    }
    
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#,##0");
        return "Venta#" + id + ": " + cliente.getNombre() + ",Zona: " + zona + ", Asiento: " + asiento + ", Total: $" + df.format(total);
    }    
}

class VentaActual {
    /**
     * Agregué esta clase para que funcionara como un carrito de compras temporal
     * (antes de confirmar la compra)
     */
    public String zona;
    public String asiento;
    public Cliente cliente;
    
    /**
     * 
     * @return 
     */
    public String getZona(){
        return zona;
    }
    public void setZona(String zona){
        this.zona = zona;
    }
    public String getAsiento(){
        return asiento;
    }
    public void setAsiento(String asiento){
        this.asiento = asiento;
    }
    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    
    /**
     * para una nueva venta:
     */
    public void reset(){
        this.zona = null;
        this.asiento = null;
        this.cliente = null;
    }
}

public class Exp2_S9_Nicole_Plaza {
    /**
     * Por aquí dejé más ordenado y puse lo más general para que el programa
     * funcione. 
     */
    static String nombreTeatro = "Teatro Moro";
    static ArrayList<Cliente> clientes = new ArrayList<>();      
    static ArrayList<Venta> ventas = new ArrayList<>();
    static VentaActual ventaActual = new VentaActual();
    public static boolean otraVenta = true;
    static Set<String> asientosReservados = new HashSet<>();
    /**
     * "asientosReservados" lo ocupé esta vez para marcar los asientos que ya
     * están ocupados.
     */
    
    /** 
     * Esta parte de aquí tipo "mapeo" me permitió hacer un plano del teatro 
     * más interactivo.
     */
    static List<String> zonasOrdenadas = Arrays.asList(
            "VIP", "PALCOS", "PLATEA BAJA",
            "PLATEA ALTA", "GALERIA");   
    static Map<String, List<String>> asientosPorZona = Map.of(
            "VIP", Arrays.asList("A1", "A2", "A3", "A4", "A5"),
            "PALCOS", Arrays.asList("B1", "B2", "B3", "B4", "B5"),
            "PLATEA BAJA", Arrays.asList("C1", "C2", "C3", "C4", "C5"),
            "PLATEA ALTA", Arrays.asList("D1", "D2", "D3", "D4", "D5"),
            "GALERIA", Arrays.asList("E1", "E2", "E3", "E4", "E5"));  
    static Map<String, Integer> preciosZona = Map.of(
            "VIP", 30000, "PALCOS", 18000, 
            "PLATEA BAJA", 15000, "PLATEA ALTA", 
            10000, "GALERIA", 7000); 
    static final Map<String, Double> descuentos = Map.of(
        "Niño", 0.10,
        "Estudiante", 0.15,
        "Mujer", 0.20,
        "Tercera edad", 0.25);
    
    public static void mostrarMenuPrincipal(){
        /** 
         * El menú principal se mantiene igual, únicamente lo ordené un poquito.
         */
        System.out.println("");
        System.out.println("======== MENU PRINCIPAL ========");
        System.out.println(" 1. Comprar entradas      ");
        System.out.println(" 2. Modificar compra      ");
        System.out.println(" 3. Imprimir boleta       ");
        System.out.println(" 4. Ver resumen de compras ");
        System.out.println(" 5. Salir                 ");
        System.out.println("=". repeat(32));
        System.out.println("");
        System.out.println("Seleccione una opcion (1 - 5): ");        
    }
    
    public static Cliente pedirDatosCliente(Scanner SC){
        /** 
         * Añadí esta sección para optimizar el programa, traté de modularizar
         * bastante a lo largo del código para que esté más "limpio" por así decirlo.
         */
        String nombre;
        int edad = 0;
        String sexo;
        
        System.out.printf("%-10s", "Nombre cliente: ");
        nombre = SC.nextLine().trim();
        while(nombre.isEmpty() || nombre.matches(".*\\d.*")){
            System.out.println("Error: Nombre no valido (no debe estar vacio o contener numeros).");
            nombre = SC.nextLine().trim();
        }
        
        boolean edadValida;
        do{
            System.out.printf("%-10s", "Edad del cliente: ");
            try{
                edad = Integer.parseInt(SC.nextLine());
                edadValida = (edad >= 5 && edad <= 120);
                if(!edadValida){
                    System.out.println("Error: Edad debe estar entre 5 y 120 años");
                }
            } catch(NumberFormatException e){
                System.out.println("Error: Ingrese un numero valido.");
                edadValida = false;
            }
        } while(!edadValida);
        
        do{
            System.out.printf("%-10s", "Sexo del cliente (H/M): ");
            sexo = SC.nextLine().trim().toUpperCase();
            if(!sexo.equals("H") && !sexo.equals("M")){
                System.out.println("Error: Ingrese 'H' o 'M'");
            }
        } while(!sexo.equals("H") && !sexo.equals("M"));
        return new Cliente(nombre, edad, sexo);
    }
    
    public static void mostrarPlanoTeatro(){
        /** 
         * Acá decidí aprender y mejorar un poco (bastante) la visualización del
         * plano para que se vea más estético y sea funcional. El código en general ocupa esta
         * misma configuración en la consola.
         */
        System.out.println("================== PLANO TEATRO MORO ================");
        System.out.printf("%-15s %-25s %-15s\n", "ZONA", "ASIENTOS", "PRECIO");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - -");

        for(String zona : zonasOrdenadas){
            StringBuilder asientosStr = new StringBuilder();
            for(String asiento : asientosPorZona.get(zona)){
                asientosStr.append(asientosReservados.contains(asiento) ? "[" + asiento + "]" : asiento + " ");
                
            }
            System.out.printf("%-15s %-25s $%-15d\n", zona, asientosStr.toString().trim(), preciosZona.get(zona));
        }
        System.out.println("- ".repeat(27));
        System.out.println("Leyenda: [XX] = Asiento ocupado");
        /** 
         * Ocupé una leyenda que le permita al usuario saber cuales asientos están
         * disponibles y cuales no para facilitar el uso del programa.
         */
        System.out.println("=".repeat(53));
        
    }
    public static void pedirZona(Scanner SC){
        String zona;
        do{
            System.out.printf("%-10s", "Seleccione una zona: ");
            zona = SC.nextLine().toUpperCase();
            if(!asientosPorZona.containsKey(zona)) {
                System.out.println("Zona invalida.");
            }          
        } while(!asientosPorZona.containsKey(zona));
        ventaActual.setZona(zona);
    }
    
    public static boolean pedirAsiento(Scanner SC){
        String zona = ventaActual.getZona();
        if(zona == null){
            return false;
        }
        boolean asientoValido;
        do{
            /**
             * Aparte de la leyenda que ocupé en el plano, decidí igualmente
             * agregar una sección que permita visualizar qué asientos están 
             * disponibles para comprar, solo para asegurarme.
             */
            System.out.println("");
            System.out.printf("%-15s", "=============== ASIENTOS DISPONIBLES ================");
            int count = 0;
            for(String asiento : asientosPorZona.get(zona)){
                if(!asientosReservados.contains(asiento)){
                    System.out.println(asiento + " ");
                    count++;
                    if(count % 5 == 0){
                        System.out.println();
                    }
                }
            }
            if(count == 0){
                System.out.println("No hay asientos disponibles en esta zona.");
                return false;
            }
            System.out.println("=".repeat(53));
            System.out.print("Seleccione un asiento: ");
            String asiento = SC.nextLine().toUpperCase();
            asientoValido = asientosPorZona.get(ventaActual.getZona()).contains(asiento) && !asientosReservados.contains(asiento);
            
            if(!asientoValido){
            System.out.println("Error: Asiento no valido o ya reservado.");
            } else {
                ventaActual.setAsiento(asiento);
                return true;
            }
        } while(!asientoValido);
        return false;
    }
    
    public static boolean validarAsiento(String zona, String asiento){
        return asientosPorZona.get(zona).contains(asiento) && !asientosReservados.contains(asiento);
    }
    
    public static double calcularDescuento( int edad, String sexo){
        double descuento = 0.0;
        
        // descuento por edades
        if(edad <12){
            descuento += descuentos.get("Niño");
        } else if(edad < 18){
            descuento += descuentos.get("Estudiante");
        } else if (edad >= 60){
            descuento += descuentos.get("Tercera edad");
        }
        
        // descuento por género
        if(sexo.equalsIgnoreCase("M")){
            descuento += descuentos.get("Mujer");
        }
        return Math.min(descuento, 0.5);
        
        /** Añadí esta validación para permitir la acumulación de descuentos, pero
           asegurando que el total no supere el 50%. Esto evita que, en casos
           excepcionales con múltiples descuentos aplicables, se llegue a un descuento
           del 100%
        */
    }
    
    public static void calcularPrecioFinal(String zona, double descuento){
        double precio = preciosZona.get(zona);
        System.out.println("Total a pagar: $" + (int)(precio * (1 - descuento)));        
    }

    public static void confirmarVenta(Scanner SC, double descuento){
        System.out.printf("%10s", "¿Confirmar compra? (Si/No): ");        
        if(!SC.nextLine().equalsIgnoreCase("si")) {
            System.out.println("Compra cancelada.");
            return;
        }   
        
        String asiento = ventaActual.getAsiento();
        if(!asientosReservados.contains(asiento)){
            double precioBase = preciosZona.get(ventaActual.getZona());
            double totalFinal = precioBase * (1 - descuento);  
            
            ventas.add(new Venta(ventaActual.getCliente(), ventaActual. getZona(), asiento, totalFinal, descuento));
            asientosReservados.add(asiento);
            
            /**
             * Agregué de todas formas el uso de ID que, aunque me costó bastante 
             * entenderlas en la entrega pasada, las implementé de todas formas 
             * para llevar un orden en las ventas y al final terminé aprendiendo
             * cómo se usaban :)
             */
            System.out.println("Compra registrada. Su ID de cliente es: " + ventaActual.getCliente().getId());
        } else {
            System.out.println("Error: El asiento ya fue reservado, intente con otro.");
        }
    }

    public static boolean preguntarOtraVenta(Scanner SC){
        System.out.printf("%-10s", "¿Desea realizar otra compra? (Si/No): ");
        String respuesta = SC.nextLine().toLowerCase();
        return respuesta.startsWith("si") || respuesta.equals("s");
    }
    
    public static void ventaEntradas(Scanner SC){
        boolean continuarVenta = true;
        
        do{
            ventaActual.setCliente(pedirDatosCliente(SC));
            mostrarPlanoTeatro();
            pedirZona(SC);
            
            if (!pedirAsiento(SC)) {
                System.out.println("No hay asientos disponibles para esta zona.");
                System.out.println("¿Qué desea hacer?");
                System.out.println(" 1. Elegir otra zona");
                System.out.println(" 2. Volver al menu principal");
                String opcion = SC.nextLine();
                
                if(opcion.equals("2")){
                    return;                    
                }
                continue;
            }
   
        double descuento = calcularDescuento(
            ventaActual.getCliente().getEdad(),
            ventaActual.getCliente().getSexo());
        calcularPrecioFinal(ventaActual.getZona(), descuento);
        confirmarVenta(SC, descuento);
        mostrarResumenVenta();
        
        if(!preguntarOtraVenta(SC)){
            /** 
         * Y por aquí quise agregar esta pestaña para volver al menú principal y
         * que no sea tan abrupto. Es solo para decoración y para que en consola
         * se vea más ordenado.
         */
            System.out.println("Volviendo al menu principal...");
            return;
        } 
        ventaActual.reset();
        } while(true);
    }

    public static void eliminarVenta(Scanner SC, Venta ventaEncontrada){
        System.out.printf("%-10s", "¿Desea confirmar la eliminación? (Si/No):");
        String confirmacion = SC.nextLine().toUpperCase();
        if (confirmacion.equals("SI")){
            asientosReservados.remove(ventaEncontrada.getAsiento());
            ventas.remove(ventaEncontrada);
            System.out.println("Compra eliminada exitosamente.");
        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }
    
    public static String seleccionarConValidacion(
        Scanner SC, 
        String mensajeOpciones,
        String mensajeInput, 
        String valorActual, 
        Collection<String> opcionesValidas){
        
        System.out.println(mensajeOpciones);
        System.out.print(mensajeInput);
        String input = SC.nextLine().toUpperCase();
        
        if(input.isEmpty()){
            return valorActual;
        }
        if(!opcionesValidas.contains(input)){
            System.out.println("Valor invalido. Se mantiene: " + valorActual);
            return valorActual;
        }
        return input;
    }
    
    /** "actualizar venta" me permitió gestionar las modificaciones de las entradas,
     *  me facilitó bastante el trabajo.
     * @param ventaVieja
     * @param nuevaZona
     * @param nuevoAsiento 
     */
    
    public static void actualizarVenta(Venta ventaVieja, String nuevaZona, String nuevoAsiento) {
        if(!nuevoAsiento.equals(ventaVieja.getAsiento()) && !validarAsiento(nuevaZona, nuevoAsiento)){
            System.out.println("Error: El asiento " + nuevoAsiento + " no esta disponible en la zona " + nuevaZona + ".");
            return;
        }

        asientosReservados.remove(ventaVieja.getAsiento());
        asientosReservados.add(nuevoAsiento);
        
        double precioBase = preciosZona.get(nuevaZona);
        double descuento = calcularDescuento(
            ventaVieja.getCliente().getEdad(),
            ventaVieja.getCliente().getSexo());
        
        Venta ventaNueva = new Venta(
            ventaVieja.getCliente(),
            nuevaZona,
            nuevoAsiento,
            precioBase * (1 - descuento),
            descuento);
        
        ventas.set(ventas.indexOf(ventaVieja), ventaNueva);
        System.out.println("Venta actualizada: " + nuevoAsiento + " en " + nuevaZona);
    }

    
    public static void editarVenta(Scanner SC, Venta ventaEncontrada){
        System.out.printf("%-15s", "- - - - EDITAR COMPRA - - - -");
        mostrarPlanoTeatro();
        
        String nuevaZona = seleccionarConValidacion(
        SC, "Zonas disponibles: " + String.join(", ", asientosPorZona.keySet()), 
        "Nueva zona (ENTER para mantener '" + ventaEncontrada.getZona() + "'): ", ventaEncontrada.getZona(),
        asientosPorZona.keySet());
        
        List<String> asientosDisponibles = asientosPorZona.get(nuevaZona).stream()
        .filter(a -> !asientosReservados.contains(a) || a.equals(ventaEncontrada.getAsiento())).toList();
        
        String nuevoAsiento = seleccionarConValidacion(
            SC, 
            "Asientos disponibles en " + nuevaZona + ": " + String.join(" ", asientosDisponibles), 
            "Nuevo asiento (ENTER para mantener '" + ventaEncontrada.getAsiento() + "'): ", ventaEncontrada.getAsiento(), 
            new HashSet<>(asientosDisponibles));
        
        if(!nuevaZona.equals(ventaEncontrada.getZona()) || !nuevoAsiento.equals(ventaEncontrada.getAsiento())){
            actualizarVenta(ventaEncontrada, nuevaZona, nuevoAsiento);
            System.out.println("Compra actualizada: " + nuevaZona + " - " + nuevoAsiento);
        } else {
            System.out.println("No se realizaron cambios.");
        }
    
    }
    
    public static void modificarVenta(Scanner SC){
        if(ventas.isEmpty()){
            System.out.println("No hay compras para modificar.");
            return;
        }
        
        System.out.println("Ingrese el ID del cliente para buscar su compra: ");
        int idBuscado;
        try{
            idBuscado = Integer.parseInt(SC.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error: ID debe ser un numero. Intente nuevamente.");
            return;
        }
        
        Venta ventaEncontrada = null;
        for(Venta venta : ventas){
            if(venta.getCliente().getId() == idBuscado){
                ventaEncontrada = venta;
                break;
            }
        }
        if(ventaEncontrada == null){
            System.out.println("Error: No se encontro ninguna venta con ese ID.");
            return;
        }
        
        System.out.println("============= COMPRA ENCONTRADA ===========");
        System.out.println(ventaEncontrada);
        System.out.println("=".repeat(53));
        System.out.println(" 1. Editar zona/asiento");
        System.out.println(" 2. Eliminar compra");
        System.out.println(" 3. Volver al menu principal");
        System.out.println("=".repeat(53));
        System.out.println("Seleccione una opcion para continuar: ");
        
        String opcion = SC.nextLine();
        switch(opcion){
            case "1" -> {
                editarVenta(SC, ventaEncontrada);
            }
            case "2" -> {
                eliminarVenta(SC, ventaEncontrada);
            }
            case "3" -> {
                mostrarMenuPrincipal();
            }
        }
    }
    
    public static void mostrarResumenVenta(){
        if(ventas.isEmpty()){
            System.out.println("No hay ventas realizadas.");
            return;
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println();
        System.out.println("============= RESUMEN GENERAL DE VENTAS =============");
        System.out.println(" Total de entradas vendidas: " + ventas.size());
        double ingresosTotales = ventas.stream().mapToDouble(Venta::getTotal).sum();
        System.out.println(" Ingresos totales: $" + df.format(ingresosTotales));
        System.out.println();
        System.out.println("================= DETALLE DE VENTA ==================");
        for(Venta venta : ventas){
            System.out.println(" ID Venta: " + venta.getId()); 
            System.out.println("-".repeat(53));
            System.out.println(" Cliente: " + venta.getCliente().getNombre() + " (" + venta.getCliente().getEdad() + " años)");
            System.out.println(" Zona: " + venta.getZona() + " | Asiento: " + venta.getAsiento());
            System.out.println(" Total: " + df.format(venta.getTotal()));
            System.out.println("-".repeat(53));
        }
    }
    
    public static void generarBoleta(){
    if (ventas.isEmpty()) {
        System.out.println("No hay ventas registradas.");
        return;
    }
    DecimalFormat df = new DecimalFormat("0.0");
    Venta venta = ventas.get(ventas.size() - 1);  
    Cliente cliente = venta.getCliente();
    String zona = venta.getZona();
    String asiento = venta.getAsiento();
    double precioBase = preciosZona.get(zona);
    double descuentoAplicado = venta.getDescuento() * 100;
    double total = venta.getTotal();
    
    System.out.println();
    System.out.println("===================== BOLETA =================");
    System.out.println("");
    System.out.println("             " + nombreTeatro + "           ");
    System.out.println("");
    System.out.println("=".repeat(53));
    System.out.println("Cliente: " + cliente.getNombre());
    System.out.println("ID Cliente: " + cliente.getId());
    System.out.println("Edad: " + cliente.getEdad() + " años");
    System.out.println("Zona: " + zona);
    System.out.println("Asiento: " + asiento);
    System.out.println("Precio base: $" + (int)precioBase);
    System.out.println("Descuento aplicado: " + df.format(descuentoAplicado) +"%");
    System.out.println("TOTAL PAGADO: $" + (int)total);
    System.out.println("=".repeat(53));
    System.out.println();
    System.out.println("Gracias por su compra en " + nombreTeatro + "!");
    System.out.println();
    System.out.println("=".repeat(53));    
    }
    
    
    public static void main(String[] args) {
        try (Scanner SC = new Scanner(System.in)) {
            while(otraVenta){
                mostrarMenuPrincipal();
                String opcion = SC.nextLine();
                switch(opcion){
                    case "1" -> {
                        ventaEntradas(SC);
                    }
                    case "2" -> {
                        modificarVenta(SC);
                    }
                    case "3" -> {
                        generarBoleta();
                    }
                    case "4" -> {
                        mostrarResumenVenta();
                    }
                    case "5" -> {
                        System.out.println("Gracias por usar el sistema de ventas, hasta pronto!");
                        otraVenta = false;
                    }
                    default -> {
                        System.out.println("Opcion invalida.");
                    }
                }
            }
        }
    }
} 