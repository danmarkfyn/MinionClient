/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.visuals;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import minioning.common.services.IGameInitializer;
import org.openide.util.lookup.ServiceProvider;
import static minioning.common.data.LocalData.getWidth;
import static minioning.common.data.LocalData.getHeight;

/**
 *
 * @author Jakob
 */
@ServiceProvider(service = IGameInitializer.class)
public class GameInitializer implements IGameInitializer {

    @Override
    public void install() {
        System.out.println("Install running in Vizualisation!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        cfg.width = getWidth();
        cfg.height = getHeight();
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(new VisualProcess(), cfg);

    }

}
