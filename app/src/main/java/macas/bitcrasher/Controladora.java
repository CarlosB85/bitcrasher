package macas.bitcrasher;

import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;

public class Controladora {

    private Principal pnpl;
    private boolean pantallaPrincipal = true;
    private float width = 0;
    private float heigth = 0;
    private CountDownTimer tiempo,tiempoCheck;
    ////////// parametros campo \\\\\\\\\\\\\\\\
    private List<Point> puntos = new ArrayList<Point>();
    ////////// parametros jugadores \\\\\\\\\\\\\
    private float x1,x2,y1,y2;
    public boolean juegoTerminado = false;
    // direcciones
    /*
        0
    1       3
        2
     */
    public int direccion2 = 2;
    public int direccion1 = 0;

    public Controladora(){

    }

    public void setPrincipal(Principal prcpl){
        pnpl = prcpl;
    }

    private void establecerLimites(){
        for(int q = 0; q <= width; q += 2){
        int yq = (int) (heigth/8);
            int yy = (int) (heigth - (heigth/8));
            puntos.add(new Point(q,yq));
            puntos.add(new Point(q,yy));
            ////pnpl.dibujar(q,yq,true);
            //pnpl.dibujar(q,yy,true);
        }
        for(int p = 0; p <= heigth;p += 2){
            puntos.add(new Point(2,p));
            puntos.add(new Point(width-2,p));
            ///pnpl.dibujar(0+2,p,false);
           // pnpl.dibujar(width-2,p,false);
        }
    }

    public void tap(float x, float y, float w, float h){
        if(pantallaPrincipal) {
        //esta en la pantalla principal del juego, se inicia el juego, se cambia el fondo
            width = w;
            heigth = h;
            pnpl.cambiarFondo(1);
            pantallaPrincipal = false;
            iniciarJuego();
        }else { // para esta parte ver la imagen "Areas Direccion"
                if(y<(h/6)){ // AREA 1
                if(direccion2 != 2){
                    direccion2 = 0;
                }
                    if(juegoTerminado){
                        reiniciarJuego();
                    }
                }else if(y>(2*(h/6)) && y < (h/2)){ // AREA 4
                    if(direccion2 != 0){
                        direccion2 = 2;
                    }
            } else if(y>(h/6) && y<(2*(h/6))){ // AREA 2 Y 3
             if(x<(w/2)){
                 //AREA 2
                 if(direccion2 != 3){
                     direccion2 = 1;
                 }
             }else{
                 //AREA 3
                 if(direccion2 != 1){
                     direccion2 = 3;
                 }
             }
            }else if(y>(5*(h/6))){ // AREA 8
                    if(direccion1 != 0){
                        direccion1 = 2;
                    }
                    if(juegoTerminado){
                    reiniciarJuego();
                    }
                } else if(y<(5*(h/6)) && y > (4*(h/6))){ // AREA 6 Y 7
                    if(x<(w/2)){
                        //AREA 6
                        if(direccion1 != 3){
                            direccion1 = 1;
                        }
                    }else{
                        //AREA 7
                        if(direccion1 != 1){
                            direccion1 = 3;
                        }
                    }
                }else if(y>(h/2) && y < (4*(h/6))){ // AREA 5
                    if(direccion1 != 2){
                        direccion1 = 0;
                    }
                }

        }
    }

    private void reiniciarJuego(){
        tiempo.cancel();
        tiempoCheck.cancel();
        juegoTerminado = false;
        pnpl.reset();
        puntos = new ArrayList<Point>();
        x1 = width/2;
        y1 = heigth/8;
        x2 = x1;
        y2 = heigth-(heigth/8);
        direccion2 = 2;
        direccion1 = 0;
        pnpl.dibujar(x1,y1,true);
        pnpl.dibujar(x2, y2, false);
        puntos.add(new Point(x1, y1));
        puntos.add(new Point(x2, y2));
        establecerLimites();
        pnpl.cambiarFondo(5);
        tiempoCheck.start();
        tiempo.start();
    }

    private void iniciarJuego(){
        x1 = width/2;
        y1 = heigth/8;
        x2 = x1;
        y2 = heigth-(heigth/8);
        pnpl.dibujar(x1,y1,true);
        pnpl.dibujar(x2,y2,false);
        puntos.add(new Point(x1,y1));
        puntos.add(new Point(x2,y2));
        establecerLimites();
        pnpl.cambiarFondo(5);
        tiempoCheck = new CountDownTimer(70000,200) {
            @Override
            public void onTick(long l) {
            if (juegoTerminado){
                tiempo.cancel();
            }
            }

            @Override
            public void onFinish() {

            }
        }.start();

        tiempo = new CountDownTimer(60000, 50) { //el segundo parametro es la velocidad.

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished/1000 == 57){
                    pnpl.cambiarFondo(1);
                }
                //pnpl.msg = millisUntilFinished/1000;
                pnpl.invalidate();
                avanzar();
            }

            public void onFinish() {
                //pnpl.msg = 0;
                //pnpl.cambiarFondo(4);
                //juegoTerminado = true;
            }
        };
        tiempo.start();
    }
    private void avanzar(){

    float avance = 5;
        //jugador 1
        /* Direcciones
        0
    1       3
        2
        */
        switch(direccion2) {
            case 0 : // arriba
                y1-=avance;
                break;
            case 1 : // izquierda
                x1-=avance;
                break;
            case 2 : // abajo
                y1+=avance;
                break;
            case 3 : // derecha
                x1+=avance;
                break;
            default:
                break;
        }
        //jugador 2
        switch (direccion1){
            case 0 : // arriba
                y2-=avance;
                break;
            case 1 : // izquierda
                x2-=avance;
                break;
            case 2 : // abajo
                y2+=avance;
                break;
            case 3 : // derecha
                x2+=avance;
                break;
            default:
                break;
        }
        switch (verificarChoque()){
            case 0:
                pnpl.dibujar(x1, y1, true);
                pnpl.dibujar(x2,y2,false);
                puntos.add(new Point(x1,y1));
                puntos.add(new Point(x2,y2));
                break;
            case 1://perdio 1
                pnpl.cambiarFondo(2);
                juegoTerminado = true;
                pnpl.pV++;
                break;
            case 2://perdio 2
                pnpl.cambiarFondo(3);
                juegoTerminado = true;
                pnpl.pR++;
                break;
            case 3:// empate
                pnpl.cambiarFondo(4);
                juegoTerminado = true;
                break;
        }
    }

    private int verificarChoque(){
    int salida = 0 ; //  0=nada, 1=choco 1, 2=choco 2, 3=empate

    float x;
    float y;
        if(!juegoTerminado) {
            for (Point pot : puntos) {
                x = pot.x;
                y = pot.y;
                switch (direccion2) {
                    case 0://arriba
                        if ((y <= y1 && y >= (y1 - 8) && x <= x1 && x1 <= (x + 8)) || (y2 <= y1 && y2 >= (y1 - 8) && x2 <= x1 && x1 <= (x2 + 8))) {
                            salida = 1;
                        }
                        break;
                    case 1://izq
                        if ((x <= x1 && x >= (x1 - 8) && y <= y1 && y1 <= (y + 8)) || (x2 <= x1 && x2 >= (x1 - 8) && y2 <= y1 && y1 <= (y2 + 8))) {
                            salida = 1;
                        }
                        break;
                    case 2://abajo
                        if ((y >= y1 && y <= (y1 + 8) && x <= x1 && x1 <= (x + 8)) || (y2 >= y1 && y2 <= (y1 + 8) && x2 <= x1 && x1 <= (x2 + 8))) {
                            salida = 1;
                        }
                        break;
                    case 3://dere
                        if ((x >= x1 && x <= (x1 + 8) && y <= y1 && y1 <= (y + 8)) || (x2 >= x1 && x2 <= (x1 + 8) && y2 <= y1 && y1 <= (y2 + 8))){
                            salida = 1;
                        }
                        break;
                }
                switch (direccion1) {
                    case 0://arriba
                        if ((y <= y2 && y >= (y2 - 8) && x <= x2 && x2 <= (x + 8)) || (y1 <= y2 && y1 >= (y2 - 8) && x1 <= x2 && x2 <= (x1 + 8))){
                            if (salida == 1) {
                                salida = 3;
                            } else {
                                salida = 2;
                            }
                        }
                        break;
                    case 1://izq
                        if ((x <= x2 && x >= (x2 - 8) && y <= y2 && y2 <= (y + 8)) || (x1 <= x2 && x1 >= (x2 - 8) && y1 <= y2 && y2 <= (y1 + 8))) {
                            if (salida == 1) {
                                salida = 3;
                            } else {
                                salida = 2;
                            }
                        }
                        break;
                    case 2://abajo
                        if ((y >= y2 && y <= (y2 + 8) && x <= x2 && x2 <= (x + 8)) || (y1 >= y2 && y1 <= (y2 + 8) && x1 <= x2 && x2 <= (x1 + 8))) {
                            if (salida == 1) {
                                salida = 3;
                            } else {
                                salida = 2;
                            }
                        }
                        break;
                    case 3://dere
                        if ((x >= x2 && x <= (x2 + 8) && y <= y2 && y2 <= (y + 8)) || (x1 >= x2 && x1 <= (x2 + 8) && y1 <= y2 && y2 <= (y1 + 8))) {
                            if (salida == 1) {
                                salida = 3;
                            } else {
                                salida = 2;
                            }
                        }
                        break;
                }
                if (salida != 0) {
                    break;
                }
            }
        }
        return salida;
    }

}
