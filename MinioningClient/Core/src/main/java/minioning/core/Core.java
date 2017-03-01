///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package minioning.core;
//
//import com.badlogic.gdx.ApplicationListener;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//import minioning.common.services.IPluginService;
//import org.openide.util.Lookup;
//import org.openide.util.LookupEvent;
//import org.openide.util.LookupListener;
//
//public class Core implements ApplicationListener {
//
//    private final Lookup lookup = Lookup.getDefault();
//    private List<IPluginService> gamePlugins = new CopyOnWriteArrayList<>();
//    private Lookup.Result<IPluginService> result;
//
//    @Override
//    public void create() {
//
//  
//
//
//        Lookup.Result<IPluginService> result = lookup.lookupResult(IPluginService.class);
//        result.addLookupListener(lookupListener);
//        gamePlugins = new ArrayList<>(result.allInstances());
//        result.allItems();
//
//        for (IPluginService plugin : gamePlugins) {
//            plugin.start();
//        }
////        update();
////        System.out.println(lookup.lookupAll(IEntityProcessingService.class).size() + " entity processors was found");
//    }
//
//    @Override
//    public void render() {
//      
//    }
//
//    private void update() {
////        //Update
////        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
////            for (Entity e : world.values()) {
////                entityProcessorService.process(gameData, world, e);
////            }
////        }
//    }
//
//    private void draw() {
//    }
//
//    @Override
//    public void resize(int width, int height) {
//    }
//
//    @Override
//    public void pause() {
//    }
//
//    @Override
//    public void resume() {
//    }
//
//    @Override
//    public void dispose() {
//    }
//
////    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
////        return lookup.lookupAll(IEntityProcessingService.class);
////    }
//
//    private final LookupListener lookupListener = new LookupListener() {
//        @Override
//        public void resultChanged(LookupEvent le) {
//
//            Collection<? extends IPluginService> updated = result.allInstances();
//
//            for (IPluginService us : updated) {
//                // Found modules
//                if (!gamePlugins.contains(us)) {
//                    us.start();
//                    gamePlugins.add(us);
//                }
//            }
//
//            // Uninstall modules
//            for (IPluginService gs : gamePlugins) {
//                if (!updated.contains(gs)) {
//                    gs.stop();
//                    gamePlugins.remove(gs);
//                }
//            }
//        }
//    };
//}
