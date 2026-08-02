package sqyro.classessmp.items;

import net.minecraft.world.entity.EntityType;

public class SpawnerItem extends ClassRestrictedItem {
    private final String requiredClassID;
    private final EntityType SpawnedEntity;

    public SpawnerItem(Properties properties, String requiredClassID, EntityType SpawnedEntity) {
        super(properties);
        this.requiredClassID = requiredClassID;
        this.SpawnedEntity = SpawnedEntity;
    }

    @Override
    protected String getRequiredClass() {
        return requiredClassID;
    }
}
