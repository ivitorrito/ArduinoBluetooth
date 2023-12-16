package com.ivitorrito.arduinobluetooth;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/*
Un codigo que permite recibir multiples datos simultaneos datos desde el
 * Arduino. Debe ser utilizado con el codigo multiple_data_semd.ino
 */
public class rxMultiple {
//Se crea una variable tipo PanamaHitek_Arduino

    static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    /**
     * Se crea una instancia de la clase PanamaHitek_MultiMessage, indicando la
     * implementacion de 4 sensores
     */
    static PanamaHitek_MultiMessage multi = new PanamaHitek_MultiMessage(4, ino);
    //Se crea un eventListener para el puerto serie
    static SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        //Si se recibe algun dato en el puerto serie, se ejecuta el siguiente metodo
        public void serialEvent(SerialPortEvent serialPortEvent) {
            try {
                /*
                Los datos en el puerto serie se envian caracter por caracter. Si se
                desea esperar a terminar de recibir el mensaje antes de imprimirlo, 
                el metodo dataReceptionCompleted() devolvera TRUE cuando se haya terminado
                de recibir el mensaje, el cual podra ser impreso a traves del metodo
                printMessage()
                 */

                if (multi.dataReceptionCompleted()) {
                    /**
                     * Se imprimen los datos recibidos del Arduino en el mismo
                     * orden en el que son enviados. Los indices 0, 1, 2 y 3
                     * indican el cual dato se debe imprimir
                     */

                    Inicio.txtnegro.setText(multi.getMessage(0)+"Gramos");
                    Inicio.txtcian.setText(multi.getMessage(1)+"Gramos");
                    Inicio.txtmagenta.setText(multi.getMessage(2)+"Gramos");
                    Inicio.txtamarillo.setText(multi.getMessage(3)+"Gramos");

                    String negro = multi.getMessage(0);
                    double dNegro = Double.parseDouble(negro);
                    int INegro = (int) dNegro;

                    String cian = multi.getMessage(1);
                    double dCian = Double.parseDouble(cian);
                    int ICian = (int) dCian;

                    String magenta = multi.getMessage(2);
                    double dMagenta = Double.parseDouble(magenta);
                    int IMagenta = (int) dMagenta;

                    String amarillo = multi.getMessage(3);
                    double dAmarillo = Double.parseDouble(amarillo);
                    int IAmarillo = (int) dAmarillo;

                    Inicio.BarNegro.setValue(INegro);
                    Inicio.BarCian.setValue(ICian);
                    Inicio.BarMagenta.setValue(IMagenta);
                    Inicio.BarAmarillo.setValue(IAmarillo);
                   if(negro == "350"){
                   Inicio.BoteNegro.setText("1");
                   }

                    /*
                    Cuando se ha terminado de imprimir los datos, se invoca el 
                    flushBuffer para que se pueda recibir el siguiente set de datos
                     */
                    multi.flushBuffer();
                }
               
               
            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(rxMultiple.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

}
