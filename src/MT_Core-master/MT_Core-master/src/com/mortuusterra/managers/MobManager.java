/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.managers;

import java.util.Arrays;

import org.bukkit.Chunk;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.mortuusterra.MortuusTerraCore;

public class MobManager {
	private MortuusTerraCore main;
	public MobManager(MortuusTerraCore main) {
		this.main = main;
	}

	/**
	 * Loop over all entities and kill them if they are not a zombie, pig, enderman, cow, squid or sheep.
	 * @param loaded the chunk which was loaded
	 */
	public void clearUnwantedMobs(Chunk loaded){
        Arrays.stream(loaded.getEntities()).filter(e -> e instanceof LivingEntity).filter(entity ->
                (entity.getType().isAlive() && entity.getType() != EntityType.PLAYER
                && entity.getType() != EntityType.ZOMBIE && entity.getType() != EntityType.ENDERMAN
                && entity.getType() != EntityType.PIG && entity.getType() != EntityType.COW
                && entity.getType() != EntityType.SHEEP && entity.getType() != EntityType.SQUID)
                ).forEach(entity -> ((LivingEntity)entity).damage(((LivingEntity) entity).getHealth() + 1));
	}

}
