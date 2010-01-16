/*
 * NetOptimizApp.java
 */

package netoptimiz;

import netoptimiz.graphe.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class NetOptimizApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new NetOptimizView(this));
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

    @Action
    public void LoadNoeudsFile() {
        JFrame mainFrame = NetOptimizApp.getApplication().getMainFrame();
        JFileChooser jfc1 = new JFileChooser();
        mainFrame.add(jfc1);
        jfc1.showOpenDialog(null);
        File file = jfc1.getSelectedFile();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            String[] elem = null;
            br.readLine();
            while ((line = br.readLine()) != null) {
                elem = line.split("\t");
                new Noeud(elem[0], Double.parseDouble(elem[1]), Double.parseDouble(elem[2]));
            }
            br.close();
        } catch (IOException x) {
            System.err.println(x);
        }
        Noeud.displayNoeuds();
        Graphe.getSingleton().getnoeuds().addAll(Noeud.getnoeuds());
    }

    @Action
    public void LoadArcsFile() {
        JFrame mainFrame = NetOptimizApp.getApplication().getMainFrame();
        JFileChooser jfc1 = new JFileChooser();
        mainFrame.add(jfc1);
        jfc1.showOpenDialog(null);
        File file = jfc1.getSelectedFile();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            String[] elem = null;
            br.readLine();
            while ((line = br.readLine()) != null) {
                elem = line.split("\t");
                new Arc(Noeud.getNoeud(elem[0]), Noeud.getNoeud(elem[1]), Double.parseDouble(elem[2]), Double.parseDouble(elem[3]));
            }
            br.close();
        } catch (IOException x) {
            System.err.println(x);
        }
        Arc.displayArcs();
        Graphe.getSingleton().getarcs().addAll(Arc.getarcs());
    }

    @Action
    public void LoadDemandesFile() {
        JFrame mainFrame = NetOptimizApp.getApplication().getMainFrame();
        JFileChooser jfc1 = new JFileChooser();
        mainFrame.add(jfc1);
        jfc1.showOpenDialog(null);
        File file = jfc1.getSelectedFile();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            String[] elem = null;
            br.readLine();
            while ((line = br.readLine()) != null) {
                elem = line.split("\t");
                new Demande(Noeud.getNoeud(elem[0]), Noeud.getNoeud(elem[1]), Double.parseDouble(elem[2]));
            }
            br.close();
        } catch (IOException x) {
            System.err.println(x);
        }
        Demande.displayDemandes();
        Graphe.getSingleton().getdemandes().addAll(Demande.getdemandes());
        System.out.println(Graphe.getSingleton().toString());
    }
}
