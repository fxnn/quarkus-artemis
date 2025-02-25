package io.quarkus.artemis.core.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigDocMapKey;
import io.quarkus.runtime.annotations.ConfigDocSection;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ConfigRoot(name = "artemis", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class ArtemisBuildTimeConfigs {
    /**
     * The default config
     */
    @ConfigItem(name = ConfigItem.PARENT)
    public ArtemisBuildTimeConfig defaultConfig = new ArtemisBuildTimeConfig();

    /**
     * Additional named configs
     */
    @ConfigDocSection
    @ConfigDocMapKey("configuration-name")
    @ConfigItem(name = ConfigItem.PARENT)
    public Map<String, ArtemisBuildTimeConfig> namedConfigs = new HashMap<>();

    /**
     * Whether a health check is published in case the smallrye-health extension is present.
     * <p>
     * This is a global setting and is not specific to a datasource.
     */
    @ConfigItem(name = "health.enabled")
    public Optional<Boolean> healthEnabled = Optional.empty();

    public ArtemisBuildTimeConfig getDefaultConfig() {
        return defaultConfig;
    }

    private Map<String, ArtemisBuildTimeConfig> getNamedConfigs() {
        return namedConfigs;
    }

    public Map<String, ArtemisBuildTimeConfig> getAllConfigs() {
        HashMap<String, ArtemisBuildTimeConfig> allConfigs = new HashMap<>(getNamedConfigs());
        if (getDefaultConfig() != null && !getDefaultConfig().isEmpty()) {
            allConfigs.put(ArtemisUtil.DEFAULT_CONFIG_NAME, getDefaultConfig());
        }
        return allConfigs;
    }

    public boolean isHealthEnabled() {
        return healthEnabled.orElse(true);
    }

    public boolean isEmpty() {
        return defaultConfig.isEmpty()
                && namedConfigs.isEmpty()
                && healthEnabled.isEmpty();
    }
}
