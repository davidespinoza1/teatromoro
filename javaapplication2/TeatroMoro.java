import java.util.ArrayList;
import java.util.Scanner;

class entrada {
    static int contador = 0;
    int id;
    int precio;
    String zona;
    String tipo;
    String estado;
    String descuentoString;
    int fila;
    int columna;
    int descuento;
    int precioF;
    Cliente cli;

    public entrada(Zona x,int col,int fil,Cliente c){
        this.id=contador;
        this.cli=c;
        this.precio=x.valor;
        this.zona=x.nombre;
        this.estado="R";
        this.fila=fil;
        this.columna=col;
        this.precioF=x.valor*(100-c.descuentoEdad)/100;

        switch (c.descuentoEdad) {
            case 0 -> this.tipo="General";
            case 10 -> this.tipo="nino";
            case 25 -> this.tipo="Tercera Edad";
        }
        this.descuentoString=this.tipo;
        if(c.estudiante==true){
            this.precioF=this.precioF*(85)/100;
            this.descuentoString=this.descuentoString+"/Estudiante";
        }
        if(c.Genero.equals("mujer")){
            this.precioF=this.precioF*(80)/100;
            this.descuentoString=this.descuentoString+"/Mujer";
        }
        this.descuento=this.precioF*100/x.valor;
        contador++;
    }

    public void mostrarEntrada() {
        System.out.println("---------------------------");
        System.out.println("ID: " + id);
        System.out.println("Posicion [" + columna + "][" + fila + "]");
        System.out.println("zona:   " + zona + "        precio: " + precio);
        System.out.println("Tipo DE entrada:    " + tipo);
        System.out.println("Descuento:  " + descuento + "%  Sus descuentos fueron:  "+descuentoString);
        System.out.println("estado:"+ estado);
        System.out.println("Precio Final:   " + precioF);
        System.out.println("---------------------------");
        System.out.println("");
    }
}

class Zona {
    String nombre;
    int valor;

    public Zona(String name, int val) {
        this.nombre = name;
        this.valor = val;
    }

    public void mostrarInfo() {
        System.out.println("zona: " + nombre + " precio: " + valor);
    }

   
}

class Cliente {
    static int contadorCliente = 0;
    String RUT;
    String nombre;
    int edad;
    int idCliente;
    int descuentoEdad;
    boolean estudiante;
    String Genero;

    public Cliente(String RUT, int edad, String nombre, String genero,boolean Estudi) {
        this.RUT = RUT;
        this.edad = edad;
        this.Genero= genero;
        this.estudiante= Estudi;
        idCliente = contadorCliente;
        if ((edad <= 18) && (edad >= 6)) {
            this.descuentoEdad = 10;
        } else if (edad >= 60) {
            this.descuentoEdad = 25;
        } else {
            this.descuentoEdad = 0;
        }
        contadorCliente++;
    }
}

public class TeatroMoro {
    public static void main(String[] args) {
        ArrayList<entrada> reserva = new ArrayList<>();
        ArrayList<entrada> compras = new ArrayList<>();
        ArrayList<Cliente> clients = new ArrayList<>();
        clients.add(new Cliente("null", 0, "David","Genero",true));
        Cliente c = null;
        int columna;
        ArrayList<entrada> allcomprayreserva = new ArrayList<>();
        int fila;
        int aux;
        String auxRut;
        String auxString;
        Scanner lectura = new Scanner(System.in);
        entrada auxEntrada;
        int edad;
        int descuentos = 0;
        String auxGenero;
        boolean auxBoolean;
        ArrayList<Zona> listaZonas = new ArrayList<>();
        int respuesta = 1;
        TeatroMoro.llenadoZona(listaZonas);
        entrada asientos[][] = new entrada[5][10];
        do {
            do {
                TeatroMoro.mostraropciones();
                aux = (int) leerHastaNumero();
            } while (TeatroMoro.errorOption(1, 4, aux));

            switch (aux) {
                case 1: //reservar entradas
                    //mostrar asientos
                    TeatroMoro.mostrarAsientos(asientos);
                    System.out.println("ingrese su rut para ver si esta en el sistema");
                    auxRut = lectura.nextLine();
                    for (int i = 0; i < clients.size(); i++) {
                        if (clients.get(i).RUT.equals(auxRut) ) {
                            System.out.println("El cliente ya existe");
                            c = clients.get(i);
                            break;
                        } else {
                            c = null;
                        }
                    }
                    if (c == null) {
                        System.out.println("No encontramos su usuario en el sistema, nos indica su edad");
                        edad = (int) leerHastaNumero();
                        System.out.println("y su nombre");
                        auxString = lectura.nextLine();
                        System.out.println("¿Cual es su GENERO?");
                        auxGenero= lectura.nextLine();
                        System.out.println("¿Es Estudiante? 1= SI. 2= NO");
                         do {
                               aux = (int) leerHastaNumero();
                           } while (TeatroMoro.errorOption(1, 4, aux));
                         if(aux==1){
                        auxBoolean=true;
                    }else{
                        auxBoolean=false;
                    }
                        c = new Cliente(auxRut, edad, auxString, auxGenero,auxBoolean);
                        clients.add(c);
                    }
                    //eleccion de asiento
                    do {
                        TeatroMoro.mostrarAsientos(asientos);
                        do {
                            System.out.println("elija el numero de columna");
                            columna = (int) leerHastaNumero();

                        } while (TeatroMoro.errorOption(1, 6, columna));
                        do {
                            System.out.println("elija el numero de  fila ");
                            fila = (int) leerHastaNumero();

                        } while (TeatroMoro.errorOption(1, 10, fila));
                        aux = TeatroMoro.verificasionAsiento(asientos, columna, fila);
                    } while (aux == 0);
                    //rellenado de datos entrada
                    TeatroMoro.zonaAutomatica(listaZonas, columna, fila, c, asientos);
                    reserva.add(asientos[columna - 1][fila - 1]);
                    break;
                case 2: //comprar entradas reservadas

                    TeatroMoro.mostrarBoleta(reserva);
                    do {
                        System.out.println("Quiere 1 comprar o 2 cancelar");
                        aux = (int) leerHastaNumero();
                    } while (TeatroMoro.errorOption(1, 2, aux));

                    if (aux == 1) {
                        for (int i = 0; i < reserva.size(); i++) {
                            auxEntrada = reserva.get(i);
                            asientos[auxEntrada.columna - 1][auxEntrada.fila - 1].estado = "C";
                            compras.add(auxEntrada);

                        }
                    } else {
                        for (int i = 0; i < reserva.size(); i++) {
                            auxEntrada = reserva.get(i);
                            asientos[auxEntrada.columna - 1][auxEntrada.fila - 1] = null;
                        }
                    }

                    reserva.clear();

                    break;
                case 3: //modificar una venta
                    System.out.println("Denos el id del Boleto a modificar");
                    aux = (int) leerHastaNumero();
                    for (int i = 0; i < asientos.length; i++) {
                        for (int j = 0; j < asientos[i].length; j++) {
                            if (aux == asientos[i][j].id) {
                                auxEntrada = asientos[i][j];
                                auxEntrada.mostrarEntrada();
                                do {
                                    System.out.println("Que desea hacer");
                                    System.out.println("1 devolucion");
                                    System.out.println("2 cambiar de asiento");
                                    aux = (int) leerHastaNumero();
                                } while (TeatroMoro.errorOption(1, 2, aux));

                                if (aux == 1) {
                                    System.out.println("Se les devolvera:   " + auxEntrada.precioF);
                                    asientos[auxEntrada.columna - 1][auxEntrada.fila - 1] = null;

                                } else {
                                    int dif = 0;
                                    do {
                                        TeatroMoro.mostrarAsientos(asientos);
                                        do {
                                            System.out.println("elija el numero de columna");
                                            columna = (int) leerHastaNumero();

                                        } while (TeatroMoro.errorOption(1, 5, columna));
                                        do {
                                            System.out.println("elija el numero de  fila ");
                                            fila = (int) leerHastaNumero();

                                        } while (TeatroMoro.errorOption(1, 10, fila));
                                        aux = verificasionAsiento(asientos, columna, fila);
                                    } while (aux == 0);
                                    TeatroMoro.zonaAutomatica(listaZonas, columna, fila, auxEntrada.cli, asientos);
                                    dif = auxEntrada.precioF - asientos[columna - 1][fila - 1].precioF;
                                    if (dif < 0) {
                                        dif = dif * (-1);
                                        System.out.println("El total a pagar por la diferencia es:  " + dif);
                                    } else {
                                        System.out.println("El total a devolver es:" + dif);
                                    }
                                    asientos[columna - 1][fila - 1].estado = "C";
                                    asientos[auxEntrada.columna - 1][auxEntrada.fila - 1].estado = null;

                                }

                            }
                        }
                    }

                    break;
                case 4:
                    TeatroMoro.mostrarBoleta(compras);
                    compras.clear();
                    break;
            }

            System.out.println("decea hacer otra accion");
            System.out.println("1 si");
            System.out.println("2 No");
            respuesta = (int) leerHastaNumero();
             
        } while (respuesta != 2);
        TeatroMoro.mostrarBoleta(reserva);
                    do {
                        System.out.println("Quiere 1 comprar o 2 cancelar");
                        aux = (int) leerHastaNumero();
                    } while (TeatroMoro.errorOption(1, 2, aux));

                    if (aux == 1) {
                        for (int i = 0; i < reserva.size(); i++) {
                            auxEntrada = reserva.get(i);
                            asientos[auxEntrada.columna - 1][auxEntrada.fila - 1].estado = "C";
                            compras.add(auxEntrada);

                        }
                    } else {
                        for (int i = 0; i < reserva.size(); i++) {
                            auxEntrada = reserva.get(i);
                            asientos[auxEntrada.columna - 1][auxEntrada.fila - 1] = null;
                        }
                    }

                    reserva.clear();
    }

    public static void mostrarAsientos(entrada[][] asientos) {
        int aux = 0;
        System.out.println("L=libre R=reservado C=Comprado");
        System.out.println("  espacio       1    2    3    4    5    6    7    8    9   10");
        for (int i = 0; i < asientos.length; i++) {
            aux = i + 1;
            if (i < 1) {
                System.out.print(aux + " vip           ");

            } else if (i < 2) {
                System.out.print(aux + " Palco         ");
                } else if (i < 3) {
                System.out.print(aux + " platea Alta   ");
                } else if (i < 4) {
                System.out.print(aux + " platea Baja   ");                
            } else {
                System.out.print(aux + " Galeria       ");
            }
            for (int j = 0; j < asientos[i].length; j++) {
                if (asientos[i][j] == null) {
                    System.out.print("L" + "    ");
                } else {
                    System.out.print(asientos[i][j].estado + "    ");
                }

            }
            System.out.println(" ");

        }

    }

    public static int verificasionAsiento(entrada[][] asientos, int columna, int fila) {
        columna = columna - 1;
        fila = fila - 1;
        if (asientos[columna][fila] != null) {
            System.err.println("asiento ya ocupado");
            return 0;
        }
        return 1;
    }

    public static void zonaAutomatica(ArrayList<Zona> z, int columna, int fila, Cliente c, entrada[][] asientos) {
        Zona x;
            x = z.get(columna-1);
            asientos[columna - 1][fila - 1] = new entrada(x, columna, fila, c);
        }
    public static void mostrarBoleta(ArrayList<entrada> lista) {
        int total = 0;
        entrada auxEntrada;
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("entrada numero: " + (i + 1));
            auxEntrada = lista.get(i);
            total += auxEntrada.precioF;
            auxEntrada.mostrarEntrada();
        }
        System.out.println("El total es:  " + total);
    }

    private static void llenadoZona(ArrayList<Zona> listaZonas) {
        listaZonas.add(new Zona("VIP", 40000));
        listaZonas.add(new Zona("Palco", 30000));
        listaZonas.add(new Zona("Platea Alta", 25000));
        listaZonas.add(new Zona("platea Baja", 20000));
        listaZonas.add(new Zona("Galeria", 15000));
    }

    private static void mostraropciones() {
        System.out.println("1   Reservar un Boleto");
        System.out.println("2   Pagar entradas reservadas");
        System.out.println("3   modificar entrada");
        System.out.println("4   mostrar Boleta");

    }

    private static boolean errorOption(int menor, int mayor, int aux) {
        if (aux > mayor || aux < menor) {
            System.err.println("opcion invalida");
            return true;
        }
        return false;
    }

    public static double leerHastaNumero() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String entrada = scanner.nextLine();
            try {
                double numero = Double.parseDouble(entrada); // Usa Integer.parseInt(entrada) si quieres solo enteros
                return numero;
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un numero. Intenta de nuevo.");
            }
        }
    }
}
