package macas.bitcrasher;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class Home extends Activity {

    private Principal plp;
    private Controladora control;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full screen view
        control = new Controladora();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        plp = new Principal(this, control);
        plp.cambiarFondo(0);
        setContentView(plp);
        plp.requestFocus();
    }
}
