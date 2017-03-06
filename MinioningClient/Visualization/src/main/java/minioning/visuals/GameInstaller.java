/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;
/**
 *
 * @author Jakob
 */
public class GameInstaller extends ModuleInstall{
    
    
     @Override
    public void restored() {
//
//           LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
//           
//            cfg.width = 1024;
//            cfg.height = 576;
//            cfg.useGL30 = false;
//            cfg.resizable = false;
//            
//            
//            new LwjglApplication(new VisualProcess(), cfg);
}
        @Override
    public void uninstalled() {
        
        
//        t.stop();
        super.uninstalled(); //To change body of generated methods, choose Tools | Templates.

        
    }
}


