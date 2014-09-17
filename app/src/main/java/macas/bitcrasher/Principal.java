package macas.bitcrasher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class Principal extends View{

    private List<Point> pointsRojos = new ArrayList<Point>();
    private List<Point> pointsVerdes = new ArrayList<Point>();
    private Canvas cvs;
    private Paint pnt = new Paint();
    private Paint pntRojo = new Paint();
    private Paint pntVerde = new Paint();
    private Point punto;
    private Controladora ctrl;
    public float msg = 99;

   public Principal(Context contexto, Controladora control){
       super(contexto);
       ctrl = control;
       ctrl.setPrincipal(this);
       setFocusable(true);
       setFocusableInTouchMode(true);
       //pnt.setStyle(Paint.Style.STROKE);
       pnt.setAntiAlias(true);
       pntRojo.setAntiAlias(true);
       pntVerde.setAntiAlias(true);
       punto = new Point(0, 0);
       pntVerde.setARGB(255,180,255,0);
       pntRojo.setARGB(255,255,0,0);
       pnt.setARGB(255,255,255,255);
       setOnTouchListener(new OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent event) {
               float px = event.getX();
               float py = event.getY();
               float w = view.getWidth();
               float h = view.getHeight();
               ctrl.tap(px,py,w,h);
               return true;
           }
       });
   }

    public void dibujar(float x, float y, boolean p1){
        if (p1){
            punto = new Point(x,y);
            pointsRojos.add(punto);
            invalidate();
        }else{
            punto = new Point(x,y);
            pointsVerdes.add(punto);
            invalidate();
        }
    }

    public void cambiarFondo(int opcion){
        switch (opcion){
            case 0:
                setBackgroundDrawable(getResources().getDrawable(R.drawable.inicio));
                break;
            case 1:
                setBackgroundDrawable(getResources().getDrawable(R.drawable.fondo_juego));
                break;
            case 2:
                setBackgroundDrawable(getResources().getDrawable(R.drawable.win1));
                break;
            case 3:
                setBackgroundDrawable(getResources().getDrawable(R.drawable.win2));
                break;
            case 4:// empate
                setBackgroundDrawable(getResources().getDrawable(R.drawable.empate));
                break;
            case 5:
                setBackgroundDrawable(getResources().getDrawable(R.drawable.pre_inicio));
                break;
            default:
                setBackgroundDrawable(getResources().getDrawable(R.drawable.inicio));
                break;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        /*
        for (int g = 1 ; g < pointsRojos.size()-1 ; g++) {
            Point pot = pointsRojos.get(g);
            Point prePot = pointsRojos.get(g-1);
            Point posPot = pointsVerdes.get(g+1);
            if ((prePot.x==pot.x && posPot.x==pot.x) || (prePot.y==pot.y && posPot.y==pot.y)){
             pointsRojos.remove(pot);
            }
                canvas.drawLine(prePot.x, prePot.y, pot.x, pot.y, pntRojo);

        }
        for (int g = 1 ; g < pointsVerdes.size()-1 ; g++) {
            Point pot = pointsVerdes.get(g);
            Point prePot = pointsVerdes.get(g-1);
            Point posPot = pointsVerdes.get(g+1);
            if ((prePot.x==pot.x && posPot.x==pot.x) || (prePot.y==pot.y && posPot.y==pot.y)){
                pointsVerdes.remove(pot);
            }
                canvas.drawLine(prePot.x, prePot.y, pot.x, pot.y, pntVerde);

        }*/

        for(Point pot : pointsRojos){
            canvas.drawCircle(pot.x,pot.y,4,pntRojo);
        }
        for(Point pot : pointsVerdes){
            canvas.drawCircle(pot.x,pot.y,4,pntVerde);
        }
        canvas.drawText(pointsRojos.size() + " puntos Rojos",10,10,pnt);
        canvas.drawText(pointsVerdes.size() + " puntos Verdes",10,20,pnt);
        canvas.drawText(msg+" ",10,canvas.getHeight()/2,pnt);
    }
}
