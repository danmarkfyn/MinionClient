/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visuals;

import java.util.Map;
import minioning.common.data.Entity;
import minioning.common.services.IProcessingService;
import org.openide.util.lookup.ServiceProvider;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/**
 *
 * @author Jakob
 */


@ServiceProvider (service = IProcessingService.class)
public class VisualProcess implements IProcessingService{

    @Override
    public void process(Map<String, Entity> world, Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public void process(Map<String, Entity> world, ShapeRenderer sr) {
//               System.out.println("LOLOLOLOLOLOLLOLOLOLOLOLOLLOLOLOLOLOLOLLOLOLOLOLOLOLLOLOLOLOLOLOLLOLOLOLOLOLOL");
//        
//                for (Entity entity : world.values()) {
//
//            sr.setColor(0, 1, 1, 0);
//
//            sr.begin(ShapeRenderer.ShapeType.Line);
//
//            sr.circle(entity.getX(), entity.getY(), 100);
//
//            sr.end();
//    }
//
//
//}
}
