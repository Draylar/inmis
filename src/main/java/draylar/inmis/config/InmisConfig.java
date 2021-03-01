package draylar.inmis.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

import java.util.Arrays;
import java.util.List;

@Config(name = "inmis")
public class InmisConfig implements ConfigData {
    public List<BackpackInfo> backpacks = Arrays.asList(
            BackpackInfo.of("baby", 3, 1, false),
            BackpackInfo.of("frayed", 9, 1, false),
            BackpackInfo.of("plated", 9, 2, false),
            BackpackInfo.of("gilded", 9, 3, false),
            BackpackInfo.of("bejeweled", 9, 5, false),
            BackpackInfo.of("blazing", 9, 6, true),
            BackpackInfo.of("withered", 11, 6, false),
            BackpackInfo.of("endless", 15, 6, false)
    );

    public boolean unstackablesOnly = false;
}
