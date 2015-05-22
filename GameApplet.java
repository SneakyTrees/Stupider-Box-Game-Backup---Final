package stupiderboxgame;

import javax.swing.JApplet;
import java.security.*;

public class GameApplet extends JApplet {

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    @Override
    public void init() {
        /*
        SecurityManager sm = new MySecurityManager();
        System.setSecurityManager(sm);
        
        AccessController.doPrivileged(new PrivilegedAction() {
            @Override
        public Object run() {
            StupiderBoxGame.main(null);
            return null;
        }
    });
    */    
    }
    
    @Override
    public void start(){

    }

    // TODO overwrite start(), stop() and destroy() methods
}
