import java.util.ArrayList;
import java.util.Scanner;

class entrada{
    static int contador=0;
    int id;
    int precio;
    String zona;
    String tipo;
    String estado;
    int fila;
    int columna;
    int descuento;
    int precioF;
    public entrada(Zona x,int col,int fil,int desc){
        this.id=contador;
        this.precio=x.valor;
        this.zona=x.nombre;
        this.estado="R";
        this.fila=fil;
        this.columna=col;
        this.descuento=desc;
        this.precioF=x.valor*(100-desc)/100;
        switch (descuento) {
            case 0 -> this.tipo="General";
            case 10 -> this.tipo="estudiante";
            case 15 -> this.tipo="Tercera Edad";
        }
        contador++;

    }
    public void mostrarEntrada(){
        System.out.println("---------------------------");
        System.out.println("ID: "+id);
        System.out.println("Posicion [" + columna + "][" + fila + "]");
        System.out.println("zona:   "+zona+"        precio: "+precio);
        System.out.println("Tipo DE entrada:    "+tipo);
        System.out.println("Descuento:  "+descuento+"%  estado: "+estado);
        System.out.println("Precio Final:   "+precioF);
        System.out.println("---------------------------");
        System.out.println("");
    }


}
class Zona{
    String nombre;
    int valor;
    public Zona(String name,int val) {
        this.nombre=name;
        this.valor=val;
    }
    public void mostrarInfo(){
        System.out.println("zona: "+ nombre +"precio: "+valor);
    }
}

public class TeatroMoro{
    public static void main(String[] args) {
    Scanner lectura =new Scanner(System.in);
    ArrayList<entrada> reserva=new ArrayList<>();
    ArrayList<entrada> compras=new ArrayList<>();
    int columna;
    int fila;
    int aux;
    entrada auxEntrada;
    int edad;
    int descuentos = 0;
    ArrayList<Zona> listaZonas = new ArrayList<>();
    int respuesta=1;
    TeatroMoro.llenadoZona(listaZonas);
    entrada asientos[][]= new entrada[6][10];
    do {
        do{
            TeatroMoro.mostraropciones();
        aux=lectura.nextInt();
        }while(TeatroMoro.errorOption(1, 4, aux));
        
        switch(aux){
            case 1: //reservar entradas
            //mostrar asientos
                TeatroMoro.mostrarAsientos(asientos);
                edad=0;
                while (edad<5) {
                    System.out.println("indiquenos su edad para ver si tiene algun descuento");
                    edad=lectura.nextInt();
                    descuentos=0;
                    if((edad<=18)&&(edad>=6)){
                        descuentos=10;
                    }else if(edad>=60){
                        descuentos=15;
                    }else if(edad<6){
                        System.out.println("Edad invalida");
                    }
                }
                //eleccion de asiento
                do {
                    do {
                        System.out.println("elija el numero de columna");
                        columna=lectura.nextInt();
                                
                    } while (TeatroMoro.errorOption(1, 6, columna));
                    do { 
                        System.out.println("elija el numero de  fila ");
                        fila=lectura.nextInt();
                        
                    } while (TeatroMoro.errorOption(1, 10, fila));
                    aux=TeatroMoro.verificasionAsiento(asientos, columna, fila);
                }while(aux==0);
                    //rellenado de datos entrada
                TeatroMoro.zonaAutomatica(listaZonas, columna, fila, descuentos, asientos);
                reserva.add(asientos[columna-1][fila-1]);
                break;
            case 2://comprar entradas reservadas
                
                TeatroMoro.mostrarVoleta(reserva);
                do {
                    System.out.println("Quiere 1 comprar o 2 cancelar");
                    aux=lectura.nextInt();
                } while (TeatroMoro.errorOption(1, 2, aux));
                
                if(aux==1){
                    for (int i = 0; i <reserva.size(); i++) {
                        auxEntrada=reserva.get(i);
                        asientos[auxEntrada.columna-1][auxEntrada.fila-1].estado="C";
                        compras.add(auxEntrada);
                    }
                }else{
                    for (int i = 0; i <reserva.size(); i++) {
                        auxEntrada=reserva.get(i);
                        asientos[auxEntrada.columna-1][auxEntrada.fila-1]=null;
                    }
                }

                reserva.clear();

                break;
            case 3://modificar una venta
                System.out.println("Denos el id del voleto a modificar");
                aux=lectura.nextInt();
                for (int i = 0; i < asientos.length; i++) {
                    for (int j = 0; j < asientos[i].length; j++) {
                        if(aux==asientos[i][j].id){
                            auxEntrada=asientos[i][j];
                            auxEntrada.mostrarEntrada();
                            do {
                                System.out.println("Que desea hacer");
                                System.out.println("1 devolucion");
                                System.out.println("2 cambiar de asiento");
                                aux=lectura.nextInt();
                            } while (TeatroMoro.errorOption(1, 2, aux));
                            
                            if(aux==1){
                                System.out.println("Se les devolvera:   "+auxEntrada.precioF);
                                asientos[auxEntrada.columna-1][auxEntrada.fila-1]=null;

                            }else{
                                int dif=0;
                                do {
                                TeatroMoro.mostrarAsientos(asientos);
                                do {
                                    System.out.println("elija el numero de columna");
                                    columna=lectura.nextInt();
                                            
                                } while (TeatroMoro.errorOption(1, 6, columna));
                                do { 
                                    System.out.println("elija el numero de  fila ");
                                    fila=lectura.nextInt();
                                    
                                } while (TeatroMoro.errorOption(1, 10, fila));
                                aux=TeatroMoro.verificasionAsiento(asientos, columna, fila);
                                } while (aux==0);
                                TeatroMoro.zonaAutomatica(listaZonas, columna, fila, auxEntrada.descuento, asientos);
                                dif=auxEntrada.precioF-asientos[columna-1][fila-1].precioF;
                                if(dif<0){
                                    dif=dif*(-1);
                                    System.out.println("El total a pagar por la diferencia es:  "+dif);
                                }else{
                                    System.out.println("El total a devolver es:"+dif);
                                }
                                asientos[columna-1][fila-1].estado="C";
                                asientos[auxEntrada.columna-1][auxEntrada.fila-1].estado=null;

                            }
            
                        }
                    }
                }

                break;
            case 4:
               TeatroMoro.mostrarVoleta(compras);
                compras.clear();
                break;
            }
    
            System.out.println("decea hacer otra accion");
            System.out.println("1 si");
            System.out.println("2 No");
            respuesta=lectura.nextInt();
        } while (respuesta != 2);
    TeatroMoro.mostrarVoleta(reserva);
                do {
                    System.out.println("Quiere 1 comprar o 2 cancelar");
                    aux=lectura.nextInt();
                } while (TeatroMoro.errorOption(1, 2, aux));
                
                if(aux==1){
                    for (int i = 0; i <reserva.size(); i++) {
                        auxEntrada=reserva.get(i);
                        asientos[auxEntrada.columna-1][auxEntrada.fila-1].estado="C";
                        compras.add(auxEntrada);
                    }
                }else{
                    for (int i = 0; i <reserva.size(); i++) {
                        auxEntrada=reserva.get(i);
                        asientos[auxEntrada.columna-1][auxEntrada.fila-1]=null;
                    }
                }

                reserva.clear();

    }
    public static  void mostrarAsientos(entrada[][] asientos){
        int aux=0;
        System.out.println("L=libre R=reservado C=Comprado");
        System.out.println(   "  espacio    1    2    3    4    5    6    7    8    9   10");
                for(int i =0; i<asientos.length;i++){
                    aux=i+1;
                    if(i<2){
                        System.out.print(   aux+" vip        ");
                        
                    }else if(i<4){
                        System.out.print(   aux+" sylver     ");
                    }else{
                        System.out.print(   aux+" General    ");
                    }
                    for (int j = 0; j<asientos[i].length;j++) {
                        if(asientos[i][j]== null){
                            System.out.print("L"+"    ");
                        }else{
                            System.out.print(asientos[i][j].estado+"    ");
                        }
                    
                    }
                    System.out.println(" ");
                    
                }

    }
    public static int verificasionAsiento(entrada[][] asientos,int columna,int fila) {
        columna=columna-1;
        fila=fila-1;
        if(asientos[columna][fila]!=null){
            System.err.println("asiento ya ocupado");
            return 0;
        }
        return 1;

        
    }
    public static void zonaAutomatica(ArrayList<Zona> z,int columna,int fila,int descuento,entrada[][] asientos){
        Zona x;
        
        if(columna<=2){
            x=z.get(0);
            asientos[columna-1][fila-1]=new entrada(x,columna,fila,descuento);
            
        }else if(columna<=4){
            x=z.get(1);
            asientos[columna-1][fila-1]=new entrada(x,columna,fila,descuento);
        }else{
            x=z.get(2);
            asientos[columna-1][fila-1]=new entrada(x,columna,fila,descuento);
        }

    }
    public static void mostrarVoleta(ArrayList<entrada> lista){
        int total=0;
        entrada auxEntrada;
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("entrada numero: "+(i+1));
            auxEntrada=lista.get(i);
            total+=auxEntrada.precioF;
            auxEntrada.mostrarEntrada();
        }
        System.out.println("El total es:  "+total);
    }
    private static void llenadoZona(ArrayList<Zona> listaZonas){
        listaZonas.add(new Zona("VIP",20000));
        listaZonas.add(new Zona("Plateada",15000));
        listaZonas.add(new Zona("General",10000));
    }
    private static void mostraropciones(){
        System.out.println("1   Reservar un voleto");
        System.out.println("2   Pagar entradas reservadas");
        System.out.println("3   modificar entrada");
        System.out.println("4   mostrar Voleta");

    }
    private static boolean errorOption(int menor,int mayor,int aux){
        if(aux>mayor||aux<menor){
            System.err.println("opcion invalida");
            return true;
        }
        return false;
    }
}
