/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.core;

import org.openide.modules.ModuleInstall;

/**
 *
 * @author Jakob
 */
public class Installer extends ModuleInstall {

    
   
    @Override
    public void restored() {

     

        
        runLauncher();
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.scheduleAtFixedRate(gameServer.run(), initialDelay, period, TimeUnit.MILLISECONDS);
    
    
    }

    @Override
    public void uninstalled() {
        
        
//        t.stop();
        super.uninstalled(); //To change body of generated methods, choose Tools | Templates.

        
    }

    private static void runLauncher() {
//        t.run();
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(Launcher.class);
            }
        }.start();
        new Thread(new Core()).start();
//t.start();
    }
//     public void run() {
//                javafx.application.Application.launch(Launcher.class);
//            }

}