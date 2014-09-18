package macas.bitcrasher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class Principal extends View{

    private List<Point> pointsRojos = new ArrayList<Point>();
    private List<Point> pointsVerdes = new ArrayList<Point>();
    //private Paint pnt = new Paint();
    private Paint pntRojo = new Paint();
    private Paint pntVerde = new Paint();
    private Point punto;
    private Controladora ctrl;
    private Bitmap bmR0,bmR1,bmR2,bmR3,bmV0,bmV1,bmV2,bmV3;
    //public float msg = 99;
    public int pR = 0;
    public int pV = 0;

   public Principal(Context contexto, Controladora control){
       super(contexto);
       ctrl = control;
       ctrl.setPrincipal(this);
       setFocusable(true);
       setFocusableInTouchMode(true);
       pntRojo.setAntiAlias(true);
       pntVerde.setAntiAlias(true);
       punto = new Point(0, 0);
       pntVerde.setARGB(200,100,120,0);
       pntRojo.setARGB(200,100,0,0);
       pntRojo.setTextSize(20);
       pntVerde.setTextSize(20);
       bmV0 = BitmapFactory.decodeResource(getResources(),R.drawable.p10);
       bmV1 = BitmapFactory.decodeResource(getResources(),R.drawable.p11);
       bmV2 = BitmapFactory.decodeResource(getResources(),R.drawable.p12);
       bmV3 = BitmapFactory.decodeResource(getResources(),R.drawable.p13);
       bmR0 = BitmapFactory.decodeResource(getResources(),R.drawable.p20);
       bmR1 = BitmapFactory.decodeResource(getResources(),R.drawable.p21);
       bmR2 = BitmapFactory.decodeResource(getResources(),R.drawable.p22);
       bmR3 = BitmapFactory.decodeResource(getResources(),R.drawable.p23);
       setOnTouchListener(new OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent event) {
               float px = event.getX();
               float py = event.getY();
               float w = view.getWidth();
               float h = view.getHeight();
               ctrl.tap(px, py, w, h);
               return true;
           }
       });
   }

    public void reset(){
        pointsRojos = new ArrayList<Point>();
        pointsVerdes = new ArrayList<Point>();

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

        if(ctrl.juegoTerminado){
            pntRojo.setAlpha(50);
            pntVerde.setAlpha(50);
        }else{
            pntRojo.setAlpha(255);
            pntVerde.setAlpha(255);
        }
        for(Point pot : pointsRojos){
            canvas.drawCircle(pot.x,pot.y,4,pntRojo);
        }
        for(Point pot : pointsVerdes){
            canvas.drawCircle(pot.x,pot.y,4,pntVerde);
        }
        if(pointsVerdes.size()>=1 && pointsRojos.size()>=1) {
            switch (ctrl.direccion1) {//jugador 1 - bmVx
                case 0:
                    canvas.drawBitmap(bmV0, pointsVerdes.get(pointsVerdes.size()-1).x - (bmV0.getWidth()/2), pointsVerdes.get(pointsVerdes.size()-1).y, pntVerde);
                    break;
                case 1:
                    canvas.drawBitmap(bmV1, pointsVerdes.get(pointsVerdes.size()-1).x, pointsVerdes.get(pointsVerdes.size()-1).y - (bmV1.getHeight()/2), pntVerde);
                    break;
                case 2:
                    canvas.drawBitmap(bmV2, pointsVerdes.get(pointsVerdes.size()-1).x - (bmV2.getWidth()/2), pointsVerdes.get(pointsVerdes.size()-1).y - bmV2.getHeight(), pntVerde);
                    break;
                case 3:
                    canvas.drawBitmap(bmV3, pointsVerdes.get(pointsVerdes.size()-1).x - bmV3.getWidth(), pointsVerdes.get(pointsVerdes.size()-1).y - (bmV3.getHeight()/2), pntVerde);
                    break;
            }
            switch (ctrl.direccion2) {//jugador 2 - bmRx
                case 0:
                    canvas.drawBitmap(bmR0, pointsRojos.get(pointsRojos.size()-1).x - (bmR0.getWidth()/2), pointsRojos.get(pointsRojos.size()-1).y, pntRojo);
                    break;
                case 1:
                    canvas.drawBitmap(bmR1, pointsRojos.get(pointsRojos.size()-1).x, pointsRojos.get(pointsRojos.size()-1).y - (bmR1.getHeight()/2), pntRojo);
                    break;
                case 2:
                    canvas.drawBitmap(bmR2, pointsRojos.get(pointsRojos.size()-1).x - (bmR2.getWidth()/2), pointsRojos.get(pointsRojos.size()-1).y - bmV2.getHeight(), pntRojo);
                    break;
                case 3:
                    canvas.drawBitmap(bmR3, pointsRojos.get(pointsRojos.size()-1).x - bmR3.getWidth(), pointsRojos.get(pointsRojos.size()-1).y - (bmR3.getHeight()/2), pntRojo);
                    break;
            }

            canvas.rotate(180, canvas.getWidth() / 2, canvas.getHeight() / 2);
            canvas.drawText("Puntaje : " + pR, 10, canvas.getHeight() - 10, pntRojo);
            canvas.rotate(180, canvas.getWidth() / 2, canvas.getHeight() / 2);
            canvas.drawText("Puntaje : " + pV, 10, canvas.getHeight() - 10, pntVerde);
        }
    }
}
