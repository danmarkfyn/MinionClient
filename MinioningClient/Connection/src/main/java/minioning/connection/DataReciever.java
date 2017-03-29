package minioning.connection;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import minioning.common.data.Entity;
import static minioning.common.data.LocalData.setClientID;
import minioning.common.services.IWorldUpdate;
import static minioning.connection.Installer.clearTempData;
import static minioning.connection.Installer.getTempData;
import static minioning.connection.WorldUpdater.updateWorld;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jakob
 */
@ServiceProvider(service = IWorldUpdate.class)
public class DataReciever implements IWorldUpdate {

    private static List<String[]> tempData;

    @Override
    public void update(Map<UUID, Entity> world) {
        tempData = getTempData();
        for (String[] data : tempData) {
            String event = data[0].trim();
            switch (event) {
                case "LOGINSUCCESS":
                    System.out.println("loginsuccess found");

                    setUUID(data[1].trim());
                    break;
                case "WORLD":
                    for (String entity : data) {
                        if(entity.equals("WORLD")){
                            continue;
                        }
                        updateWorld(data, world);
                    }
                    break;
            }
        }
        clearTempData();
    }

    private void setUUID(String raw) {

        UUID ID = UUID.fromString(raw);
        System.out.println(ID);
        setClientID(ID);

    }

}
