package draylar.inmis.config;

import draylar.inmis.mixin.SoundEventAccessor;
import net.minecraft.sound.SoundEvent;

/**
 * Stores information about a single backpack.
 * A backpack has a name (which is used in registry as name + _backpack), row width, and number of rows.
 * Row width represents how long a row is, while number of rows is used for the number of horizontal slot groups.
 *
 * BackpackInfo instances are automatically read from the config file and used in {@link draylar.inmis.item.BackpackItem}.
 */
public class BackpackInfo {

    private final String name;
    private final int rowWidth;
    private final int numberOfRows;
    private final boolean isFireImmune;
    private String openSound;

    /**
     * Creates a new BackpackInfo instance.
     *
     * @param name  name of this backpack. Used in registry as "name + _backpack".
     * @param rowWidth  width of each row
     * @param numberOfRows  number of horizontal slot groups
     * @param isFireImmune  whether this backpack is fire-immune (Netherite)
     */
    public BackpackInfo(String name, int rowWidth, int numberOfRows, boolean isFireImmune, String openSound) {
        this.name = name;
        this.rowWidth = rowWidth;
        this.numberOfRows = numberOfRows;
        this.isFireImmune = isFireImmune;
        this.openSound = openSound;
    }

    public String getName() {
        return name;
    }

    public int getRowWidth() {
        return rowWidth;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public boolean isFireImmune() {
        return isFireImmune;
    }

    public String getOpenSound() {
        return openSound;
    }

    public void setOpenSound(String sound) {
        this.openSound = sound;
    }

    /**
     * Creates a new BackpackInfo instance.
     *
     * @param name  name of this backpack. Used in registry as "name + _backpack".
     * @param rowWidth  width of each row
     * @param numberOfRows  number of horizontal slot groups
     */
    public static BackpackInfo of(String name, int rowWidth, int numberOfRows, boolean isFireImmune, SoundEvent openSound) {
        return new BackpackInfo(name, rowWidth, numberOfRows, isFireImmune, ((SoundEventAccessor) openSound).getId().toString());
    }
}
