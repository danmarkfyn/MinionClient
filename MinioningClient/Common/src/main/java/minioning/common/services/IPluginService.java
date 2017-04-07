/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minioning.common.services;

import java.util.Map;
import java.util.UUID;
import minioning.common.data.Entity;

/**
 *
 * @author Jakob
 */
public interface IPluginService  {
    void start(Map<UUID, Entity> world);
    void stop(Map<UUID, Entity> world);

}
