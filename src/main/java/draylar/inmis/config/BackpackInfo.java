package draylar.inmis.config;

/**
 * Stores information about a single backpack.
 * A backpack has a name (which is used in registry as name + _backpack), row width, and number of rows.
 * Row width represents how long a row is, while number of rows is used for the number of horizontal slot groups.
 *
 * BackpackInfo instances are automatically read from the config file and used in {@link draylar.inmis.content.BackpackItem}.
 */
public class BackpackInfo {

    private final String name;
    private final int rowWidth;
    private final int numberOfRows;
    private final boolean isFireImmune;

    /**
     * Creates a new BackpackInfo instance.
     *
     * @param name  name of this backpack. Used in registry as "name + _backpack".
     * @param rowWidth  width of each row
     * @param numberOfRows  number of horizontal slot groups
     * @param isFireImmune  whether this backpack is fire-immune (Netherite)
     */
    public BackpackInfo(String name, int rowWidth, int numberOfRows, boolean isFireImmune) {
        this.name = name;
        this.rowWidth = rowWidth;
        this.numberOfRows = numberOfRows;
        this.isFireImmune = isFireImmune;
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

    /**
     * Creates a new BackpackInfo instance.
     *
     * @param name  name of this backpack. Used in registry as "name + _backpack".
     * @param rowWidth  width of each row
     * @param numberOfRows  number of horizontal slot groups
     */
    public static BackpackInfo of(String name, int rowWidth, int numberOfRows, boolean isFireImmune) {
        return new BackpackInfo(name, rowWidth, numberOfRows, isFireImmune);
    }
}
