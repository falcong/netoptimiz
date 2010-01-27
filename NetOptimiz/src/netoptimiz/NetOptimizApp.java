/*
 * NetOptimizApp.java
 */

package netoptimiz;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class NetOptimizApp extends SingleFrameApplication {

    private NetOptimizView vuePrincipale = null;

    public NetOptimizView getVuePrincipale() {
        return vuePrincipale;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        vuePrincipale = new NetOptimizView(this);
        show(vuePrincipale);
        //show(new NetOptimizView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of NetOptimizApp
     */
    public static NetOptimizApp getApplication() {
        return Application.getInstance(NetOptimizApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(NetOptimizApp.class, args);
    }
}
