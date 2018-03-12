/*
 * Copyright (C) 2017 Mortuss Terra Team
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see https://github.com/kadeska/MT_Core/blob/master/LICENSE.
 */
package com.mortuusterra.utils.nmsentities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.entity.EntityType;

import com.mortuusterra.misc.DayZombie;

import net.minecraft.server.v1_12_R1.BiomeBase;
import net.minecraft.server.v1_12_R1.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.EntityZombie;

/**
 * Util enum to register the zombie.
 * @author Horsey
 *
 */
public enum CustomEntityType {
	// The zombie
    DAY_ZOMBIE("Zombie", 54, EntityType.ZOMBIE, EntityZombie.class, DayZombie.class);

    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;

    CustomEntityType(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass,
    		Class<? extends EntityInsentient> customClass){
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }

    public String getName(){
        return this.name;
    }

    public int getID(){
        return this.id;
    }

    public EntityType getEntityType(){
        return this.entityType;
    }

    public Class<? extends EntityInsentient> getNMSClass(){
        return this.nmsClass;
    }

    public Class<? extends EntityInsentient> getCustomClass(){
        return this.customClass;
    }
    
    /**
     * Register the entity; all zombies will spawn with the custom class.
     *  IMPORTANT: MUST BE UPDATED EVERY VERSION
     */
    public void registerEntity() {
        try {
        	// Add to the EntityType
            Method method = EntityTypes.class.getDeclaredMethod("a", int.class, String.class, Class.class, String.class);
            method.setAccessible(true);
            method.invoke(null, id, name.toLowerCase(), customClass, name);
            
            // Now loop over all biomes, to register the custom zombie
            for (Object biomeBase : BiomeBase.i){
                if (biomeBase == null){
                    break;
                }
                
                // These lists contain the mobs.
                for (String field : new String[]{"t", "u", "v", "w"}){
                    try{
                        Field list = BiomeBase.class.getDeclaredField(field);
                        list.setAccessible(true);
                        @SuppressWarnings("unchecked")
                        List<BiomeMeta> mobList = (List<BiomeMeta>) list.get(biomeBase);

                        for (BiomeMeta meta : mobList){
                            if (nmsClass.equals(meta.b)){
                            	// Change to our class.
                                meta.b = customClass;
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
