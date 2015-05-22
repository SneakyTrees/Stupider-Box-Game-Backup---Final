package stupiderboxgame;

import java.security.*;

public class MySecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
        return;
    }
}

//I'm done. Fucking massive long, comples, multi-step process just to get an online applet even running. Jesus christ.