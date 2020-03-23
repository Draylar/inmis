package draylar.inmis.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

import java.util.Arrays;
import java.util.List;

@Config(name = "inmis")
public class InmisConfig implements ConfigData {
    public List<BackpackInfo> backpacks = Arrays.asList(
            BackpackInfo.of("baby", 3, 1),
            BackpackInfo.of("frayed", 9, 1),
            BackpackInfo.of("plated", 9, 2),
            BackpackInfo.of("gilded", 9, 3),
            BackpackInfo.of("bejeweled", 9, 5),
            BackpackInfo.of("withered", 11, 6),
            BackpackInfo.of("endless", 15, 6)
    );
}
