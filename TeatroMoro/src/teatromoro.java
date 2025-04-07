import java.util.Scanner;

public class teatromoro {
    public static void main(String[] args) throws Exception {
        int respuesta = 2;
        //el do while es para que se quede en un ciclo hasta que el usuario decida cerrarlo
        do { 
            //lectura es un auxiliar para activar la lectura por terminal
            Scanner lectura =new Scanner(System.in);
            //Se ocupa un for para que mande las opciones repetidas veces mientras no seleccione una de las opciones validas
            for(int i=1 ;i>0;i++){
                System.out.println("seleccione 1 para Comprar entrada ");
            
                System.out.println("seleccione 0 para para cerrar ");
                
                respuesta= lectura.nextInt();
                if ((respuesta==1)||(respuesta==0)){
                    break;
                }else{
                    System.err.println("eleccion invalida");
                }
            }
            //si la respuesta es comprar una entrada se inicializan los datos necesarios y se dan opciones
            if (respuesta==1) {
                int nzona; 
                int zona=0;
                int precio=0;
                int edad=0;
                int descuento=0;

                //como al elegir una zona cambiara el valor del precio se ocupo el precio como base para el while
                while (precio==0) { 
                   
                   
                    System.out.println("En que zona quiere estar(seleccione por el numero)");
                    System.out.println("1 Zona 1 por 10000");
                    System.out.println("2 Zona 2 por 15000");
                    System.out.println("3 Zona 3 por 20000");
                    nzona=lectura.nextInt();
                    zona=nzona;
                    //el switch se ocupo porque es mas practico para una eleccion  
                    switch(nzona){
                        case 1 -> precio=10000;
                        case 2 -> precio=15000;
                        case 3 -> precio=20000;
                        default -> System.err.println("numero invalido");
                    }
                }
                           
                //suponiendo que estudiante es mayor que 5 menor o igual que 18 y tercera edad es mayor que o igual que 60, y que edad menor que 5 es invalida   
                while (edad<5) {    
                    System.out.println("indiquenos su edad para ver si tiene algun descuento");
                    edad=lectura.nextInt();
                    if((edad<=18)&&(edad>=6)){
                        descuento=10;        
                    }else if(edad>=60){
                        descuento=15;
                    }else if(edad<6){
                        System.err.println("Edad invalida");        
                    }else{
                        descuento=0;
                    }
                }
                int preciofinal=precio;
                //se calcula el precio final 
                while((preciofinal==precio)&&(descuento!=0)){
                    preciofinal=preciofinal*(100-descuento)/100;

                }
                System.out.println("Resumen");
                System.out.println("zona: zona "+zona);
                System.out.println("precio de la zona:"+precio);
                System.out.println("descuento aplicado:"+descuento+"%");
                System.out.println("precio final:"+preciofinal);
                System.out.println("Quiere seguir comprando?");

                respuesta=2;
              
                while((respuesta!=1)&&(respuesta!=0)){
                    System.out.println("1 = si");
                    System.out.println("0 = no");
                    respuesta=lectura.nextInt();
                    if((respuesta!=1)&&(respuesta!=0)){
                        System.err.println("Respuesta invalida");    
                    }
                }    
            }else{
                System.out.println("estaseguro");
                System.out.println("0 = si");
                System.out.println("1 = no");
                respuesta=2;
                while(respuesta>1){
                    respuesta=lectura.nextInt();
                    if(respuesta>1){
                        System.err.println("Respuesta invalida");    
                    }
                }

            }

            
            
        } while (respuesta!=0);
    }
}