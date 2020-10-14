/**
 * author Joel Martin
 * La clase representa a un parking de una ciudad europea
 * que dispone de dos tarifas de aparcamiento para los clientes
 * que lo usen: la tarifa regular (que incluye una tarifa plana para
 * entradas "tempranas") y la tarifa comercial para clientes que trabajan
 * cerca del parking, aparcan un nº elevado de horas y se benefician de esta 
 * tarifa más económica
 * (leer enunciado)
 * 
 */
public class Parking{
    private final char REGULAR = 'R';
    private final char COMERCIAL= 'C';
    private final double PRECIO_BASE_REGULAR = 2.0;
    private final double PRECIO_MEDIA_REGULAR_HASTA11 = 3.0;
    private final double PRECIO_MEDIA_REGULAR_DESPUES11 = 5.0;
    private final int HORA_INICIO_ENTRADA_TEMPRANA = 6 * 60;
    private final int HORA_FIN_ENTRADA_TEMPRANA = 8 * 60 + 30;
    private final int HORA_INICIO_SALIDA_TEMPRANA = 15 * 60;
    private final int HORA_FIN_SALIDA_TEMPRANA = 18 * 60;
    private final double PRECIO_TARIFA_PLANA_REGULAR = 15.0;
    private final double PRECIO_PRIMERAS3_COMERCIAL = 5.00;
    private final double PRECIO_MEDIA_COMERCIAL = 3.00;
    private String nombre;
    private int cliente;
    private double importeTotal;
    private int regular;
    private int comercial;
    private int clientesLunes;
    private int clientesSabado;
    private int clientesDomingo;
    private int clienteMaximoComercial; 
    private double importeMaximoComercial;

    /**
     * Inicializa el parking con el nombre indicada por el parámetro.
     * El resto de atributos se inicializan a 0 
     */
    public Parking (String queNombre) {
        nombre = queNombre;
        cliente = 0;
        importeTotal = 0;
        regular = 0;
        comercial = 0;
        clientesLunes = 0;
        clientesSabado = 0;
        clientesDomingo = 0;
        clienteMaximoComercial = 0;
        importeMaximoComercial = 0;
    }

    /**
     * accesor para el nombre del parking
     *  
     */
    public String getNombre() {
        return nombre;

    }

    /**
     * mutador para el nombre del parking
     *  
     */
    public void setNombre(String queNombre) {
        nombre = queNombre;

    }

    /**
     *  Recibe cuatro parámetros que supondremos correctos:
     *    tipoTarifa - un carácter 'R' o 'C'
     *    entrada - hora de entrada al parking
     *    salida – hora de salida del parking
     *    dia – nº de día de la semana (un valor entre 1 y 7)
     *    
     *    A partir de estos parámetros el método debe calcular el importe
     *    a pagar por el cliente y mostrarlo en pantalla 
     *    y  actualizará adecuadamente el resto de atributos
     *    del parking para poder mostrar posteriormente (en otro método) las estadísticas
     *   
     *    Por simplicidad consideraremos que un cliente entra y sale en un mismo día
     *    
     *    (leer enunciado del ejercicio)
     */
    public void facturarCliente(char tipoTarifa, int entrada, int salida, int dia) {
        cliente ++;
        int horasEntrada = entrada / 100;
        int minutosEntrada = entrada % 100;
        int horasSalida = salida / 100;
        int minutosSalida = salida % 100;
        int momentoEntrada = horasEntrada * 60 + minutosEntrada;
        int momentoSalida = horasSalida * 60 + minutosSalida;
        double importe;
        String hEntrada;
        String mEntrada;
        String hSalida;
        String mSalida;
        String queTarifa;

        if (horasEntrada < 10){
            hEntrada = "0" + horasEntrada + ":" ;
        }else{
            hEntrada = "" + horasEntrada + ":";
        }

        if (minutosEntrada < 10){
            mEntrada = "0" + minutosEntrada + ":" ;
        }else{
            mEntrada = "" + minutosEntrada + ":";
        }

        if (horasSalida < 10){
            hSalida = "0" + horasSalida + ":" ;
        }else{
            hSalida = "" + horasSalida + ":";
        }

        if (minutosSalida < 10){
            mSalida = "0" + minutosSalida + ":" ;
        }else{
            mSalida = "" + minutosSalida + ":";
        }

        switch (tipoTarifa){
            case 'R': 
            if (momentoEntrada >= HORA_INICIO_ENTRADA_TEMPRANA && momentoEntrada <= HORA_FIN_ENTRADA_TEMPRANA && momentoSalida >= HORA_INICIO_SALIDA_TEMPRANA && momentoSalida <= 
            HORA_FIN_SALIDA_TEMPRANA){
                regular++;
                queTarifa = "REGULAR y TEMPRANA";
                importe = PRECIO_TARIFA_PLANA_REGULAR;
            }
            else{ 
                importe = PRECIO_BASE_REGULAR;
                if (entrada < 1100){
                    if (salida > 1100){
                        importe += (11 * 60 - momentoEntrada) / 30 * PRECIO_MEDIA_REGULAR_HASTA11 + (momentoSalida - 11 * 60) / 30 * PRECIO_MEDIA_REGULAR_DESPUES11;
                    }else{
                        importe += (momentoSalida - momentoEntrada) / 30 * PRECIO_MEDIA_REGULAR_HASTA11;
                    }
                }
                else{
                    importe += (momentoSalida - momentoEntrada) / 30 * PRECIO_MEDIA_REGULAR_DESPUES11;
                }
                queTarifa = "REGULAR"; 
            }
            regular ++;
            break;
            default:
            if (momentoSalida - momentoEntrada > 180) {
                importe = PRECIO_PRIMERAS3_COMERCIAL + (momentoSalida - momentoEntrada - 180) / 30 * PRECIO_MEDIA_COMERCIAL;
            }
            else {
                importe = PRECIO_PRIMERAS3_COMERCIAL;
            }
            if (importe > importeMaximoComercial ){
                clienteMaximoComercial = cliente; 
                importeMaximoComercial = importe;
            }
            queTarifa = "COMERCIAL";
            comercial ++;
            break;
        }

        switch (dia) {
            case 1: 
            clientesLunes++;
            break;
            case 6: 
            clientesSabado++;
            break;
            case 7: 
            clientesDomingo++;
            break;
        }
        importeTotal += importe;
        
        System.out.println("*******************");
        System.out.println("Cliente nº: " + cliente);
        System.out.println("Hora entrada: " + hEntrada + mEntrada);
        System.out.println("Hora salida: " + hSalida + mSalida);
        System.out.println("Tarifa a aplicar: " + queTarifa);
        System.out.println("Importe a pagar: " + importe + "€");
        System.out.println("*******************");
    }

    /**
     * Muestra en pantalla las estadísticcas sobre el parking  
     *   
     * (leer enunciado)
     *  
     */
    public void printEstadísticas() {
        System.out.println("*******************");
        System.out.println("Importe total clientes:" + importeTotal + "€" );
        System.out.println("Nº clientes tarifa regular: " + regular );
        System.out.println("Nº clientes tarifa comercial: " + comercial);
        System.out.println("Cliente tarifa COMERCIAL con factura  máxima fue el número: " + clienteMaximoComercial);
        System.out.println("y pagó: " + importeMaximoComercial + "€");
        System.out.println("*******************");
    }

    /**
     *  Calcula y devuelve un String que representa el nombre del día
     *  en el que más clientes han utilizado el parking - "SÁBADO"   "DOMINGO" o  "LUNES"
     */
    public String diaMayorNumeroClientes() {
        if (clientesSabado > clientesDomingo && clientesSabado > clientesLunes){
            return "SÁBADO";
        }
        else if (clientesDomingo > clientesLunes){
            return "DOMINGO";
        }
        else{
            return "LUNES";
        }
    }
}
