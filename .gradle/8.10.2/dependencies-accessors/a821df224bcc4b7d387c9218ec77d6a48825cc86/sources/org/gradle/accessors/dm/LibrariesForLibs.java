package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final BetterrtpLibraryAccessors laccForBetterrtpLibraryAccessors = new BetterrtpLibraryAccessors(owner);
    private final ComLibraryAccessors laccForComLibraryAccessors = new ComLibraryAccessors(owner);
    private final DeLibraryAccessors laccForDeLibraryAccessors = new DeLibraryAccessors(owner);
    private final DevLibraryAccessors laccForDevLibraryAccessors = new DevLibraryAccessors(owner);
    private final FrLibraryAccessors laccForFrLibraryAccessors = new FrLibraryAccessors(owner);
    private final IoLibraryAccessors laccForIoLibraryAccessors = new IoLibraryAccessors(owner);
    private final MeLibraryAccessors laccForMeLibraryAccessors = new MeLibraryAccessors(owner);
    private final MysqlLibraryAccessors laccForMysqlLibraryAccessors = new MysqlLibraryAccessors(owner);
    private final NetLibraryAccessors laccForNetLibraryAccessors = new NetLibraryAccessors(owner);
    private final OrgLibraryAccessors laccForOrgLibraryAccessors = new OrgLibraryAccessors(owner);
    private final RedisLibraryAccessors laccForRedisLibraryAccessors = new RedisLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Group of libraries at <b>betterrtp</b>
     */
    public BetterrtpLibraryAccessors getBetterrtp() {
        return laccForBetterrtpLibraryAccessors;
    }

    /**
     * Group of libraries at <b>com</b>
     */
    public ComLibraryAccessors getCom() {
        return laccForComLibraryAccessors;
    }

    /**
     * Group of libraries at <b>de</b>
     */
    public DeLibraryAccessors getDe() {
        return laccForDeLibraryAccessors;
    }

    /**
     * Group of libraries at <b>dev</b>
     */
    public DevLibraryAccessors getDev() {
        return laccForDevLibraryAccessors;
    }

    /**
     * Group of libraries at <b>fr</b>
     */
    public FrLibraryAccessors getFr() {
        return laccForFrLibraryAccessors;
    }

    /**
     * Group of libraries at <b>io</b>
     */
    public IoLibraryAccessors getIo() {
        return laccForIoLibraryAccessors;
    }

    /**
     * Group of libraries at <b>me</b>
     */
    public MeLibraryAccessors getMe() {
        return laccForMeLibraryAccessors;
    }

    /**
     * Group of libraries at <b>mysql</b>
     */
    public MysqlLibraryAccessors getMysql() {
        return laccForMysqlLibraryAccessors;
    }

    /**
     * Group of libraries at <b>net</b>
     */
    public NetLibraryAccessors getNet() {
        return laccForNetLibraryAccessors;
    }

    /**
     * Group of libraries at <b>org</b>
     */
    public OrgLibraryAccessors getOrg() {
        return laccForOrgLibraryAccessors;
    }

    /**
     * Group of libraries at <b>redis</b>
     */
    public RedisLibraryAccessors getRedis() {
        return laccForRedisLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class BetterrtpLibraryAccessors extends SubDependencyFactory {
        private final BetterrtpMeLibraryAccessors laccForBetterrtpMeLibraryAccessors = new BetterrtpMeLibraryAccessors(owner);

        public BetterrtpLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>betterrtp.me</b>
         */
        public BetterrtpMeLibraryAccessors getMe() {
            return laccForBetterrtpMeLibraryAccessors;
        }

    }

    public static class BetterrtpMeLibraryAccessors extends SubDependencyFactory {

        public BetterrtpMeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>superronancraft</b> with <b>BetterRTP:me.SuperRonanCraft</b> coordinates and
         * with version reference <b>betterrtp.me.superronancraft</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSuperronancraft() {
            return create("betterrtp.me.superronancraft");
        }

    }

    public static class ComLibraryAccessors extends SubDependencyFactory {
        private final ComArcaniaxLibraryAccessors laccForComArcaniaxLibraryAccessors = new ComArcaniaxLibraryAccessors(owner);
        private final ComGithubLibraryAccessors laccForComGithubLibraryAccessors = new ComGithubLibraryAccessors(owner);
        private final ComGoogleLibraryAccessors laccForComGoogleLibraryAccessors = new ComGoogleLibraryAccessors(owner);
        private final ComVelocitypoweredLibraryAccessors laccForComVelocitypoweredLibraryAccessors = new ComVelocitypoweredLibraryAccessors(owner);
        private final ComViaversionLibraryAccessors laccForComViaversionLibraryAccessors = new ComViaversionLibraryAccessors(owner);
        private final ComZaxxerLibraryAccessors laccForComZaxxerLibraryAccessors = new ComZaxxerLibraryAccessors(owner);

        public ComLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.arcaniax</b>
         */
        public ComArcaniaxLibraryAccessors getArcaniax() {
            return laccForComArcaniaxLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.github</b>
         */
        public ComGithubLibraryAccessors getGithub() {
            return laccForComGithubLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.google</b>
         */
        public ComGoogleLibraryAccessors getGoogle() {
            return laccForComGoogleLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.velocitypowered</b>
         */
        public ComVelocitypoweredLibraryAccessors getVelocitypowered() {
            return laccForComVelocitypoweredLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.viaversion</b>
         */
        public ComViaversionLibraryAccessors getViaversion() {
            return laccForComViaversionLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.zaxxer</b>
         */
        public ComZaxxerLibraryAccessors getZaxxer() {
            return laccForComZaxxerLibraryAccessors;
        }

    }

    public static class ComArcaniaxLibraryAccessors extends SubDependencyFactory {
        private final ComArcaniaxHeaddatabaseLibraryAccessors laccForComArcaniaxHeaddatabaseLibraryAccessors = new ComArcaniaxHeaddatabaseLibraryAccessors(owner);

        public ComArcaniaxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.arcaniax.headdatabase</b>
         */
        public ComArcaniaxHeaddatabaseLibraryAccessors getHeaddatabase() {
            return laccForComArcaniaxHeaddatabaseLibraryAccessors;
        }

    }

    public static class ComArcaniaxHeaddatabaseLibraryAccessors extends SubDependencyFactory {

        public ComArcaniaxHeaddatabaseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>com.arcaniax:HeadDatabase-API</b> coordinates and
         * with version reference <b>com.arcaniax.headdatabase.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("com.arcaniax.headdatabase.api");
        }

    }

    public static class ComGithubLibraryAccessors extends SubDependencyFactory {
        private final ComGithubMilkbowlLibraryAccessors laccForComGithubMilkbowlLibraryAccessors = new ComGithubMilkbowlLibraryAccessors(owner);
        private final ComGithubPlanLibraryAccessors laccForComGithubPlanLibraryAccessors = new ComGithubPlanLibraryAccessors(owner);
        private final ComGithubRevxrsalLibraryAccessors laccForComGithubRevxrsalLibraryAccessors = new ComGithubRevxrsalLibraryAccessors(owner);
        private final ComGithubTechnicallycodedLibraryAccessors laccForComGithubTechnicallycodedLibraryAccessors = new ComGithubTechnicallycodedLibraryAccessors(owner);

        public ComGithubLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.github.milkbowl</b>
         */
        public ComGithubMilkbowlLibraryAccessors getMilkbowl() {
            return laccForComGithubMilkbowlLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.github.plan</b>
         */
        public ComGithubPlanLibraryAccessors getPlan() {
            return laccForComGithubPlanLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.github.revxrsal</b>
         */
        public ComGithubRevxrsalLibraryAccessors getRevxrsal() {
            return laccForComGithubRevxrsalLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.github.technicallycoded</b>
         */
        public ComGithubTechnicallycodedLibraryAccessors getTechnicallycoded() {
            return laccForComGithubTechnicallycodedLibraryAccessors;
        }

    }

    public static class ComGithubMilkbowlLibraryAccessors extends SubDependencyFactory {
        private final ComGithubMilkbowlVaultapiLibraryAccessors laccForComGithubMilkbowlVaultapiLibraryAccessors = new ComGithubMilkbowlVaultapiLibraryAccessors(owner);

        public ComGithubMilkbowlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.github.milkbowl.vaultapi</b>
         */
        public ComGithubMilkbowlVaultapiLibraryAccessors getVaultapi() {
            return laccForComGithubMilkbowlVaultapiLibraryAccessors;
        }

    }

    public static class ComGithubMilkbowlVaultapiLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public ComGithubMilkbowlVaultapiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>vaultapi</b> with <b>com.github.MilkBowl:VaultAPI</b> coordinates and
         * with version reference <b>com.github.milkbowl.vaultapi</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("com.github.milkbowl.vaultapi");
        }

        /**
         * Dependency provider for <b>x1</b> with <b>com.github.MilkBowl:VaultAPI</b> coordinates and
         * with version reference <b>com.github.milkbowl.vaultapi.x1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getX1() {
            return create("com.github.milkbowl.vaultapi.x1");
        }

    }

    public static class ComGithubPlanLibraryAccessors extends SubDependencyFactory {
        private final ComGithubPlanPlayerLibraryAccessors laccForComGithubPlanPlayerLibraryAccessors = new ComGithubPlanPlayerLibraryAccessors(owner);

        public ComGithubPlanLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.github.plan.player</b>
         */
        public ComGithubPlanPlayerLibraryAccessors getPlayer() {
            return laccForComGithubPlanPlayerLibraryAccessors;
        }

    }

    public static class ComGithubPlanPlayerLibraryAccessors extends SubDependencyFactory {
        private final ComGithubPlanPlayerAnalyticsLibraryAccessors laccForComGithubPlanPlayerAnalyticsLibraryAccessors = new ComGithubPlanPlayerAnalyticsLibraryAccessors(owner);

        public ComGithubPlanPlayerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.github.plan.player.analytics</b>
         */
        public ComGithubPlanPlayerAnalyticsLibraryAccessors getAnalytics() {
            return laccForComGithubPlanPlayerAnalyticsLibraryAccessors;
        }

    }

    public static class ComGithubPlanPlayerAnalyticsLibraryAccessors extends SubDependencyFactory {

        public ComGithubPlanPlayerAnalyticsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>plan</b> with <b>com.github.plan-player-analytics:Plan</b> coordinates and
         * with version reference <b>com.github.plan.player.analytics.plan</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPlan() {
            return create("com.github.plan.player.analytics.plan");
        }

    }

    public static class ComGithubRevxrsalLibraryAccessors extends SubDependencyFactory {
        private final ComGithubRevxrsalLampLibraryAccessors laccForComGithubRevxrsalLampLibraryAccessors = new ComGithubRevxrsalLampLibraryAccessors(owner);

        public ComGithubRevxrsalLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.github.revxrsal.lamp</b>
         */
        public ComGithubRevxrsalLampLibraryAccessors getLamp() {
            return laccForComGithubRevxrsalLampLibraryAccessors;
        }

    }

    public static class ComGithubRevxrsalLampLibraryAccessors extends SubDependencyFactory {

        public ComGithubRevxrsalLampLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>bukkit</b> with <b>com.github.Revxrsal.Lamp:bukkit</b> coordinates and
         * with version reference <b>com.github.revxrsal.lamp.bukkit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBukkit() {
            return create("com.github.revxrsal.lamp.bukkit");
        }

        /**
         * Dependency provider for <b>bungee</b> with <b>com.github.Revxrsal.Lamp:bungee</b> coordinates and
         * with version reference <b>com.github.revxrsal.lamp.bungee</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBungee() {
            return create("com.github.revxrsal.lamp.bungee");
        }

        /**
         * Dependency provider for <b>common</b> with <b>com.github.Revxrsal.Lamp:common</b> coordinates and
         * with version reference <b>com.github.revxrsal.lamp.common</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommon() {
            return create("com.github.revxrsal.lamp.common");
        }

        /**
         * Dependency provider for <b>velocity</b> with <b>com.github.Revxrsal.Lamp:velocity</b> coordinates and
         * with version reference <b>com.github.revxrsal.lamp.velocity</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getVelocity() {
            return create("com.github.revxrsal.lamp.velocity");
        }

    }

    public static class ComGithubTechnicallycodedLibraryAccessors extends SubDependencyFactory {

        public ComGithubTechnicallycodedLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>folialib</b> with <b>com.github.technicallycoded:FoliaLib</b> coordinates and
         * with version reference <b>com.github.technicallycoded.folialib</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getFolialib() {
            return create("com.github.technicallycoded.folialib");
        }

    }

    public static class ComGoogleLibraryAccessors extends SubDependencyFactory {
        private final ComGoogleCodeLibraryAccessors laccForComGoogleCodeLibraryAccessors = new ComGoogleCodeLibraryAccessors(owner);

        public ComGoogleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.google.code</b>
         */
        public ComGoogleCodeLibraryAccessors getCode() {
            return laccForComGoogleCodeLibraryAccessors;
        }

    }

    public static class ComGoogleCodeLibraryAccessors extends SubDependencyFactory {
        private final ComGoogleCodeGsonLibraryAccessors laccForComGoogleCodeGsonLibraryAccessors = new ComGoogleCodeGsonLibraryAccessors(owner);

        public ComGoogleCodeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.google.code.gson</b>
         */
        public ComGoogleCodeGsonLibraryAccessors getGson() {
            return laccForComGoogleCodeGsonLibraryAccessors;
        }

    }

    public static class ComGoogleCodeGsonLibraryAccessors extends SubDependencyFactory {

        public ComGoogleCodeGsonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>gson</b> with <b>com.google.code.gson:gson</b> coordinates and
         * with version reference <b>com.google.code.gson.gson</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGson() {
            return create("com.google.code.gson.gson");
        }

    }

    public static class ComVelocitypoweredLibraryAccessors extends SubDependencyFactory {
        private final ComVelocitypoweredVelocityLibraryAccessors laccForComVelocitypoweredVelocityLibraryAccessors = new ComVelocitypoweredVelocityLibraryAccessors(owner);

        public ComVelocitypoweredLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.velocitypowered.velocity</b>
         */
        public ComVelocitypoweredVelocityLibraryAccessors getVelocity() {
            return laccForComVelocitypoweredVelocityLibraryAccessors;
        }

    }

    public static class ComVelocitypoweredVelocityLibraryAccessors extends SubDependencyFactory {

        public ComVelocitypoweredVelocityLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>com.velocitypowered:velocity-api</b> coordinates and
         * with version reference <b>com.velocitypowered.velocity.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("com.velocitypowered.velocity.api");
        }

    }

    public static class ComViaversionLibraryAccessors extends SubDependencyFactory {
        private final ComViaversionViaversionLibraryAccessors laccForComViaversionViaversionLibraryAccessors = new ComViaversionViaversionLibraryAccessors(owner);

        public ComViaversionLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.viaversion.viaversion</b>
         */
        public ComViaversionViaversionLibraryAccessors getViaversion() {
            return laccForComViaversionViaversionLibraryAccessors;
        }

    }

    public static class ComViaversionViaversionLibraryAccessors extends SubDependencyFactory {

        public ComViaversionViaversionLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>com.viaversion:viaversion-api</b> coordinates and
         * with version reference <b>com.viaversion.viaversion.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("com.viaversion.viaversion.api");
        }

    }

    public static class ComZaxxerLibraryAccessors extends SubDependencyFactory {

        public ComZaxxerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>hikaricp</b> with <b>com.zaxxer:HikariCP</b> coordinates and
         * with version reference <b>com.zaxxer.hikaricp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getHikaricp() {
            return create("com.zaxxer.hikaricp");
        }

    }

    public static class DeLibraryAccessors extends SubDependencyFactory {
        private final DeThemoepLibraryAccessors laccForDeThemoepLibraryAccessors = new DeThemoepLibraryAccessors(owner);

        public DeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>de.themoep</b>
         */
        public DeThemoepLibraryAccessors getThemoep() {
            return laccForDeThemoepLibraryAccessors;
        }

    }

    public static class DeThemoepLibraryAccessors extends SubDependencyFactory {
        private final DeThemoepMinedownLibraryAccessors laccForDeThemoepMinedownLibraryAccessors = new DeThemoepMinedownLibraryAccessors(owner);

        public DeThemoepLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>de.themoep.minedown</b>
         */
        public DeThemoepMinedownLibraryAccessors getMinedown() {
            return laccForDeThemoepMinedownLibraryAccessors;
        }

    }

    public static class DeThemoepMinedownLibraryAccessors extends SubDependencyFactory {

        public DeThemoepMinedownLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>adventure</b> with <b>de.themoep:minedown-adventure</b> coordinates and
         * with version reference <b>de.themoep.minedown.adventure</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAdventure() {
            return create("de.themoep.minedown.adventure");
        }

    }

    public static class DevLibraryAccessors extends SubDependencyFactory {
        private final DevDejvokepLibraryAccessors laccForDevDejvokepLibraryAccessors = new DevDejvokepLibraryAccessors(owner);

        public DevLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>dev.dejvokep</b>
         */
        public DevDejvokepLibraryAccessors getDejvokep() {
            return laccForDevDejvokepLibraryAccessors;
        }

    }

    public static class DevDejvokepLibraryAccessors extends SubDependencyFactory {
        private final DevDejvokepBoostedLibraryAccessors laccForDevDejvokepBoostedLibraryAccessors = new DevDejvokepBoostedLibraryAccessors(owner);

        public DevDejvokepLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>dev.dejvokep.boosted</b>
         */
        public DevDejvokepBoostedLibraryAccessors getBoosted() {
            return laccForDevDejvokepBoostedLibraryAccessors;
        }

    }

    public static class DevDejvokepBoostedLibraryAccessors extends SubDependencyFactory {

        public DevDejvokepBoostedLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>yaml</b> with <b>dev.dejvokep:boosted-yaml</b> coordinates and
         * with version reference <b>dev.dejvokep.boosted.yaml</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getYaml() {
            return create("dev.dejvokep.boosted.yaml");
        }

    }

    public static class FrLibraryAccessors extends SubDependencyFactory {
        private final FrIbanLibraryAccessors laccForFrIbanLibraryAccessors = new FrIbanLibraryAccessors(owner);

        public FrLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>fr.iban</b>
         */
        public FrIbanLibraryAccessors getIban() {
            return laccForFrIbanLibraryAccessors;
        }

    }

    public static class FrIbanLibraryAccessors extends SubDependencyFactory {

        public FrIbanLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>bungeehomes</b> with <b>fr.iban:BungeeHomes</b> coordinates and
         * with version reference <b>fr.iban.bungeehomes</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBungeehomes() {
            return create("fr.iban.bungeehomes");
        }

        /**
         * Dependency provider for <b>warps</b> with <b>fr.iban:Warps</b> coordinates and
         * with version reference <b>fr.iban.warps</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getWarps() {
            return create("fr.iban.warps");
        }

    }

    public static class IoLibraryAccessors extends SubDependencyFactory {
        private final IoGithubLibraryAccessors laccForIoGithubLibraryAccessors = new IoGithubLibraryAccessors(owner);
        private final IoPapermcLibraryAccessors laccForIoPapermcLibraryAccessors = new IoPapermcLibraryAccessors(owner);

        public IoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>io.github</b>
         */
        public IoGithubLibraryAccessors getGithub() {
            return laccForIoGithubLibraryAccessors;
        }

        /**
         * Group of libraries at <b>io.papermc</b>
         */
        public IoPapermcLibraryAccessors getPapermc() {
            return laccForIoPapermcLibraryAccessors;
        }

    }

    public static class IoGithubLibraryAccessors extends SubDependencyFactory {
        private final IoGithubWaterfallmcLibraryAccessors laccForIoGithubWaterfallmcLibraryAccessors = new IoGithubWaterfallmcLibraryAccessors(owner);

        public IoGithubLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>io.github.waterfallmc</b>
         */
        public IoGithubWaterfallmcLibraryAccessors getWaterfallmc() {
            return laccForIoGithubWaterfallmcLibraryAccessors;
        }

    }

    public static class IoGithubWaterfallmcLibraryAccessors extends SubDependencyFactory {
        private final IoGithubWaterfallmcWaterfallLibraryAccessors laccForIoGithubWaterfallmcWaterfallLibraryAccessors = new IoGithubWaterfallmcWaterfallLibraryAccessors(owner);

        public IoGithubWaterfallmcLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>io.github.waterfallmc.waterfall</b>
         */
        public IoGithubWaterfallmcWaterfallLibraryAccessors getWaterfall() {
            return laccForIoGithubWaterfallmcWaterfallLibraryAccessors;
        }

    }

    public static class IoGithubWaterfallmcWaterfallLibraryAccessors extends SubDependencyFactory {

        public IoGithubWaterfallmcWaterfallLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>io.github.waterfallmc:waterfall-api</b> coordinates and
         * with version reference <b>io.github.waterfallmc.waterfall.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("io.github.waterfallmc.waterfall.api");
        }

    }

    public static class IoPapermcLibraryAccessors extends SubDependencyFactory {
        private final IoPapermcPaperLibraryAccessors laccForIoPapermcPaperLibraryAccessors = new IoPapermcPaperLibraryAccessors(owner);

        public IoPapermcLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>io.papermc.paper</b>
         */
        public IoPapermcPaperLibraryAccessors getPaper() {
            return laccForIoPapermcPaperLibraryAccessors;
        }

    }

    public static class IoPapermcPaperLibraryAccessors extends SubDependencyFactory {
        private final IoPapermcPaperPaperLibraryAccessors laccForIoPapermcPaperPaperLibraryAccessors = new IoPapermcPaperPaperLibraryAccessors(owner);

        public IoPapermcPaperLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>io.papermc.paper.paper</b>
         */
        public IoPapermcPaperPaperLibraryAccessors getPaper() {
            return laccForIoPapermcPaperPaperLibraryAccessors;
        }

    }

    public static class IoPapermcPaperPaperLibraryAccessors extends SubDependencyFactory {

        public IoPapermcPaperPaperLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>io.papermc.paper:paper-api</b> coordinates and
         * with version reference <b>io.papermc.paper.paper.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("io.papermc.paper.paper.api");
        }

    }

    public static class MeLibraryAccessors extends SubDependencyFactory {
        private final MeClipLibraryAccessors laccForMeClipLibraryAccessors = new MeClipLibraryAccessors(owner);
        private final MeNeznamyLibraryAccessors laccForMeNeznamyLibraryAccessors = new MeNeznamyLibraryAccessors(owner);

        public MeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>me.clip</b>
         */
        public MeClipLibraryAccessors getClip() {
            return laccForMeClipLibraryAccessors;
        }

        /**
         * Group of libraries at <b>me.neznamy</b>
         */
        public MeNeznamyLibraryAccessors getNeznamy() {
            return laccForMeNeznamyLibraryAccessors;
        }

    }

    public static class MeClipLibraryAccessors extends SubDependencyFactory {

        public MeClipLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>placeholderapi</b> with <b>me.clip:placeholderapi</b> coordinates and
         * with version reference <b>me.clip.placeholderapi</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPlaceholderapi() {
            return create("me.clip.placeholderapi");
        }

    }

    public static class MeNeznamyLibraryAccessors extends SubDependencyFactory {
        private final MeNeznamyTabLibraryAccessors laccForMeNeznamyTabLibraryAccessors = new MeNeznamyTabLibraryAccessors(owner);

        public MeNeznamyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>me.neznamy.tab</b>
         */
        public MeNeznamyTabLibraryAccessors getTab() {
            return laccForMeNeznamyTabLibraryAccessors;
        }

    }

    public static class MeNeznamyTabLibraryAccessors extends SubDependencyFactory {

        public MeNeznamyTabLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>me.neznamy:tab-api</b> coordinates and
         * with version reference <b>me.neznamy.tab.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("me.neznamy.tab.api");
        }

        /**
         * Dependency provider for <b>shared</b> with <b>me.neznamy:tab-shared</b> coordinates and
         * with version reference <b>me.neznamy.tab.shared</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getShared() {
            return create("me.neznamy.tab.shared");
        }

    }

    public static class MysqlLibraryAccessors extends SubDependencyFactory {
        private final MysqlMysqlLibraryAccessors laccForMysqlMysqlLibraryAccessors = new MysqlMysqlLibraryAccessors(owner);

        public MysqlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>mysql.mysql</b>
         */
        public MysqlMysqlLibraryAccessors getMysql() {
            return laccForMysqlMysqlLibraryAccessors;
        }

    }

    public static class MysqlMysqlLibraryAccessors extends SubDependencyFactory {
        private final MysqlMysqlConnectorLibraryAccessors laccForMysqlMysqlConnectorLibraryAccessors = new MysqlMysqlConnectorLibraryAccessors(owner);

        public MysqlMysqlLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>mysql.mysql.connector</b>
         */
        public MysqlMysqlConnectorLibraryAccessors getConnector() {
            return laccForMysqlMysqlConnectorLibraryAccessors;
        }

    }

    public static class MysqlMysqlConnectorLibraryAccessors extends SubDependencyFactory {

        public MysqlMysqlConnectorLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>java</b> with <b>mysql:mysql-connector-java</b> coordinates and
         * with version reference <b>mysql.mysql.connector.java</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJava() {
            return create("mysql.mysql.connector.java");
        }

    }

    public static class NetLibraryAccessors extends SubDependencyFactory {
        private final NetEss3LibraryAccessors laccForNetEss3LibraryAccessors = new NetEss3LibraryAccessors(owner);
        private final NetLuckpermsLibraryAccessors laccForNetLuckpermsLibraryAccessors = new NetLuckpermsLibraryAccessors(owner);
        private final NetWilliam278LibraryAccessors laccForNetWilliam278LibraryAccessors = new NetWilliam278LibraryAccessors(owner);

        public NetLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>net.ess3</b>
         */
        public NetEss3LibraryAccessors getEss3() {
            return laccForNetEss3LibraryAccessors;
        }

        /**
         * Group of libraries at <b>net.luckperms</b>
         */
        public NetLuckpermsLibraryAccessors getLuckperms() {
            return laccForNetLuckpermsLibraryAccessors;
        }

        /**
         * Group of libraries at <b>net.william278</b>
         */
        public NetWilliam278LibraryAccessors getWilliam278() {
            return laccForNetWilliam278LibraryAccessors;
        }

    }

    public static class NetEss3LibraryAccessors extends SubDependencyFactory {

        public NetEss3LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>essentialsx</b> with <b>net.ess3:EssentialsX</b> coordinates and
         * with version reference <b>net.ess3.essentialsx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEssentialsx() {
            return create("net.ess3.essentialsx");
        }

    }

    public static class NetLuckpermsLibraryAccessors extends SubDependencyFactory {

        public NetLuckpermsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>net.luckperms:api</b> coordinates and
         * with version reference <b>net.luckperms.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("net.luckperms.api");
        }

    }

    public static class NetWilliam278LibraryAccessors extends SubDependencyFactory {

        public NetWilliam278LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>papiproxybridge</b> with <b>net.william278:papiproxybridge</b> coordinates and
         * with version reference <b>net.william278.papiproxybridge</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPapiproxybridge() {
            return create("net.william278.papiproxybridge");
        }

    }

    public static class OrgLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheLibraryAccessors laccForOrgApacheLibraryAccessors = new OrgApacheLibraryAccessors(owner);
        private final OrgGeysermcLibraryAccessors laccForOrgGeysermcLibraryAccessors = new OrgGeysermcLibraryAccessors(owner);
        private final OrgJetbrainsLibraryAccessors laccForOrgJetbrainsLibraryAccessors = new OrgJetbrainsLibraryAccessors(owner);
        private final OrgOcpsoftLibraryAccessors laccForOrgOcpsoftLibraryAccessors = new OrgOcpsoftLibraryAccessors(owner);

        public OrgLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.apache</b>
         */
        public OrgApacheLibraryAccessors getApache() {
            return laccForOrgApacheLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.geysermc</b>
         */
        public OrgGeysermcLibraryAccessors getGeysermc() {
            return laccForOrgGeysermcLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.jetbrains</b>
         */
        public OrgJetbrainsLibraryAccessors getJetbrains() {
            return laccForOrgJetbrainsLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.ocpsoft</b>
         */
        public OrgOcpsoftLibraryAccessors getOcpsoft() {
            return laccForOrgOcpsoftLibraryAccessors;
        }

    }

    public static class OrgApacheLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheCommonsLibraryAccessors laccForOrgApacheCommonsLibraryAccessors = new OrgApacheCommonsLibraryAccessors(owner);

        public OrgApacheLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.apache.commons</b>
         */
        public OrgApacheCommonsLibraryAccessors getCommons() {
            return laccForOrgApacheCommonsLibraryAccessors;
        }

    }

    public static class OrgApacheCommonsLibraryAccessors extends SubDependencyFactory {
        private final OrgApacheCommonsCommonsLibraryAccessors laccForOrgApacheCommonsCommonsLibraryAccessors = new OrgApacheCommonsCommonsLibraryAccessors(owner);

        public OrgApacheCommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.apache.commons.commons</b>
         */
        public OrgApacheCommonsCommonsLibraryAccessors getCommons() {
            return laccForOrgApacheCommonsCommonsLibraryAccessors;
        }

    }

    public static class OrgApacheCommonsCommonsLibraryAccessors extends SubDependencyFactory {

        public OrgApacheCommonsCommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>pool2</b> with <b>org.apache.commons:commons-pool2</b> coordinates and
         * with version reference <b>org.apache.commons.commons.pool2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPool2() {
            return create("org.apache.commons.commons.pool2");
        }

    }

    public static class OrgGeysermcLibraryAccessors extends SubDependencyFactory {
        private final OrgGeysermcFloodgateLibraryAccessors laccForOrgGeysermcFloodgateLibraryAccessors = new OrgGeysermcFloodgateLibraryAccessors(owner);

        public OrgGeysermcLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.geysermc.floodgate</b>
         */
        public OrgGeysermcFloodgateLibraryAccessors getFloodgate() {
            return laccForOrgGeysermcFloodgateLibraryAccessors;
        }

    }

    public static class OrgGeysermcFloodgateLibraryAccessors extends SubDependencyFactory {

        public OrgGeysermcFloodgateLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>org.geysermc.floodgate:api</b> coordinates and
         * with version reference <b>org.geysermc.floodgate.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("org.geysermc.floodgate.api");
        }

    }

    public static class OrgJetbrainsLibraryAccessors extends SubDependencyFactory {

        public OrgJetbrainsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotations</b> with <b>org.jetbrains:annotations</b> coordinates and
         * with version reference <b>org.jetbrains.annotations</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("org.jetbrains.annotations");
        }

    }

    public static class OrgOcpsoftLibraryAccessors extends SubDependencyFactory {
        private final OrgOcpsoftPrettytimeLibraryAccessors laccForOrgOcpsoftPrettytimeLibraryAccessors = new OrgOcpsoftPrettytimeLibraryAccessors(owner);

        public OrgOcpsoftLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.ocpsoft.prettytime</b>
         */
        public OrgOcpsoftPrettytimeLibraryAccessors getPrettytime() {
            return laccForOrgOcpsoftPrettytimeLibraryAccessors;
        }

    }

    public static class OrgOcpsoftPrettytimeLibraryAccessors extends SubDependencyFactory {
        private final OrgOcpsoftPrettytimePrettytimeLibraryAccessors laccForOrgOcpsoftPrettytimePrettytimeLibraryAccessors = new OrgOcpsoftPrettytimePrettytimeLibraryAccessors(owner);

        public OrgOcpsoftPrettytimeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.ocpsoft.prettytime.prettytime</b>
         */
        public OrgOcpsoftPrettytimePrettytimeLibraryAccessors getPrettytime() {
            return laccForOrgOcpsoftPrettytimePrettytimeLibraryAccessors;
        }

    }

    public static class OrgOcpsoftPrettytimePrettytimeLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public OrgOcpsoftPrettytimePrettytimeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>prettytime</b> with <b>org.ocpsoft.prettytime:prettytime</b> coordinates and
         * with version reference <b>org.ocpsoft.prettytime.prettytime</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("org.ocpsoft.prettytime.prettytime");
        }

        /**
         * Dependency provider for <b>x1</b> with <b>org.ocpsoft.prettytime:prettytime</b> coordinates and
         * with version reference <b>org.ocpsoft.prettytime.prettytime.x1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getX1() {
            return create("org.ocpsoft.prettytime.prettytime.x1");
        }

    }

    public static class RedisLibraryAccessors extends SubDependencyFactory {
        private final RedisClientsLibraryAccessors laccForRedisClientsLibraryAccessors = new RedisClientsLibraryAccessors(owner);

        public RedisLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>redis.clients</b>
         */
        public RedisClientsLibraryAccessors getClients() {
            return laccForRedisClientsLibraryAccessors;
        }

    }

    public static class RedisClientsLibraryAccessors extends SubDependencyFactory {

        public RedisClientsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>jedis</b> with <b>redis.clients:jedis</b> coordinates and
         * with version reference <b>redis.clients.jedis</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJedis() {
            return create("redis.clients.jedis");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final BetterrtpVersionAccessors vaccForBetterrtpVersionAccessors = new BetterrtpVersionAccessors(providers, config);
        private final ComVersionAccessors vaccForComVersionAccessors = new ComVersionAccessors(providers, config);
        private final DeVersionAccessors vaccForDeVersionAccessors = new DeVersionAccessors(providers, config);
        private final DevVersionAccessors vaccForDevVersionAccessors = new DevVersionAccessors(providers, config);
        private final FrVersionAccessors vaccForFrVersionAccessors = new FrVersionAccessors(providers, config);
        private final IoVersionAccessors vaccForIoVersionAccessors = new IoVersionAccessors(providers, config);
        private final MeVersionAccessors vaccForMeVersionAccessors = new MeVersionAccessors(providers, config);
        private final MysqlVersionAccessors vaccForMysqlVersionAccessors = new MysqlVersionAccessors(providers, config);
        private final NetVersionAccessors vaccForNetVersionAccessors = new NetVersionAccessors(providers, config);
        private final OrgVersionAccessors vaccForOrgVersionAccessors = new OrgVersionAccessors(providers, config);
        private final RedisVersionAccessors vaccForRedisVersionAccessors = new RedisVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.betterrtp</b>
         */
        public BetterrtpVersionAccessors getBetterrtp() {
            return vaccForBetterrtpVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com</b>
         */
        public ComVersionAccessors getCom() {
            return vaccForComVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.de</b>
         */
        public DeVersionAccessors getDe() {
            return vaccForDeVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.dev</b>
         */
        public DevVersionAccessors getDev() {
            return vaccForDevVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.fr</b>
         */
        public FrVersionAccessors getFr() {
            return vaccForFrVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.io</b>
         */
        public IoVersionAccessors getIo() {
            return vaccForIoVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.me</b>
         */
        public MeVersionAccessors getMe() {
            return vaccForMeVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.mysql</b>
         */
        public MysqlVersionAccessors getMysql() {
            return vaccForMysqlVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.net</b>
         */
        public NetVersionAccessors getNet() {
            return vaccForNetVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org</b>
         */
        public OrgVersionAccessors getOrg() {
            return vaccForOrgVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.redis</b>
         */
        public RedisVersionAccessors getRedis() {
            return vaccForRedisVersionAccessors;
        }

    }

    public static class BetterrtpVersionAccessors extends VersionFactory  {

        private final BetterrtpMeVersionAccessors vaccForBetterrtpMeVersionAccessors = new BetterrtpMeVersionAccessors(providers, config);
        public BetterrtpVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.betterrtp.me</b>
         */
        public BetterrtpMeVersionAccessors getMe() {
            return vaccForBetterrtpMeVersionAccessors;
        }

    }

    public static class BetterrtpMeVersionAccessors extends VersionFactory  {

        public BetterrtpMeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>betterrtp.me.superronancraft</b> with value <b>3.2.1-4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSuperronancraft() { return getVersion("betterrtp.me.superronancraft"); }

    }

    public static class ComVersionAccessors extends VersionFactory  {

        private final ComArcaniaxVersionAccessors vaccForComArcaniaxVersionAccessors = new ComArcaniaxVersionAccessors(providers, config);
        private final ComGithubVersionAccessors vaccForComGithubVersionAccessors = new ComGithubVersionAccessors(providers, config);
        private final ComGoogleVersionAccessors vaccForComGoogleVersionAccessors = new ComGoogleVersionAccessors(providers, config);
        private final ComVelocitypoweredVersionAccessors vaccForComVelocitypoweredVersionAccessors = new ComVelocitypoweredVersionAccessors(providers, config);
        private final ComViaversionVersionAccessors vaccForComViaversionVersionAccessors = new ComViaversionVersionAccessors(providers, config);
        private final ComZaxxerVersionAccessors vaccForComZaxxerVersionAccessors = new ComZaxxerVersionAccessors(providers, config);
        public ComVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.arcaniax</b>
         */
        public ComArcaniaxVersionAccessors getArcaniax() {
            return vaccForComArcaniaxVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.github</b>
         */
        public ComGithubVersionAccessors getGithub() {
            return vaccForComGithubVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.google</b>
         */
        public ComGoogleVersionAccessors getGoogle() {
            return vaccForComGoogleVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.velocitypowered</b>
         */
        public ComVelocitypoweredVersionAccessors getVelocitypowered() {
            return vaccForComVelocitypoweredVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.viaversion</b>
         */
        public ComViaversionVersionAccessors getViaversion() {
            return vaccForComViaversionVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.zaxxer</b>
         */
        public ComZaxxerVersionAccessors getZaxxer() {
            return vaccForComZaxxerVersionAccessors;
        }

    }

    public static class ComArcaniaxVersionAccessors extends VersionFactory  {

        private final ComArcaniaxHeaddatabaseVersionAccessors vaccForComArcaniaxHeaddatabaseVersionAccessors = new ComArcaniaxHeaddatabaseVersionAccessors(providers, config);
        public ComArcaniaxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.arcaniax.headdatabase</b>
         */
        public ComArcaniaxHeaddatabaseVersionAccessors getHeaddatabase() {
            return vaccForComArcaniaxHeaddatabaseVersionAccessors;
        }

    }

    public static class ComArcaniaxHeaddatabaseVersionAccessors extends VersionFactory  {

        public ComArcaniaxHeaddatabaseVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.arcaniax.headdatabase.api</b> with value <b>1.3.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("com.arcaniax.headdatabase.api"); }

    }

    public static class ComGithubVersionAccessors extends VersionFactory  {

        private final ComGithubMilkbowlVersionAccessors vaccForComGithubMilkbowlVersionAccessors = new ComGithubMilkbowlVersionAccessors(providers, config);
        private final ComGithubPlanVersionAccessors vaccForComGithubPlanVersionAccessors = new ComGithubPlanVersionAccessors(providers, config);
        private final ComGithubRevxrsalVersionAccessors vaccForComGithubRevxrsalVersionAccessors = new ComGithubRevxrsalVersionAccessors(providers, config);
        private final ComGithubTechnicallycodedVersionAccessors vaccForComGithubTechnicallycodedVersionAccessors = new ComGithubTechnicallycodedVersionAccessors(providers, config);
        public ComGithubVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.github.milkbowl</b>
         */
        public ComGithubMilkbowlVersionAccessors getMilkbowl() {
            return vaccForComGithubMilkbowlVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.github.plan</b>
         */
        public ComGithubPlanVersionAccessors getPlan() {
            return vaccForComGithubPlanVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.github.revxrsal</b>
         */
        public ComGithubRevxrsalVersionAccessors getRevxrsal() {
            return vaccForComGithubRevxrsalVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.github.technicallycoded</b>
         */
        public ComGithubTechnicallycodedVersionAccessors getTechnicallycoded() {
            return vaccForComGithubTechnicallycodedVersionAccessors;
        }

    }

    public static class ComGithubMilkbowlVersionAccessors extends VersionFactory  {

        private final ComGithubMilkbowlVaultapiVersionAccessors vaccForComGithubMilkbowlVaultapiVersionAccessors = new ComGithubMilkbowlVaultapiVersionAccessors(providers, config);
        public ComGithubMilkbowlVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.github.milkbowl.vaultapi</b>
         */
        public ComGithubMilkbowlVaultapiVersionAccessors getVaultapi() {
            return vaccForComGithubMilkbowlVaultapiVersionAccessors;
        }

    }

    public static class ComGithubMilkbowlVaultapiVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public ComGithubMilkbowlVaultapiVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.github.milkbowl.vaultapi</b> with value <b>1.7.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("com.github.milkbowl.vaultapi"); }

        /**
         * Version alias <b>com.github.milkbowl.vaultapi.x1</b> with value <b>1.7</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getX1() { return getVersion("com.github.milkbowl.vaultapi.x1"); }

    }

    public static class ComGithubPlanVersionAccessors extends VersionFactory  {

        private final ComGithubPlanPlayerVersionAccessors vaccForComGithubPlanPlayerVersionAccessors = new ComGithubPlanPlayerVersionAccessors(providers, config);
        public ComGithubPlanVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.github.plan.player</b>
         */
        public ComGithubPlanPlayerVersionAccessors getPlayer() {
            return vaccForComGithubPlanPlayerVersionAccessors;
        }

    }

    public static class ComGithubPlanPlayerVersionAccessors extends VersionFactory  {

        private final ComGithubPlanPlayerAnalyticsVersionAccessors vaccForComGithubPlanPlayerAnalyticsVersionAccessors = new ComGithubPlanPlayerAnalyticsVersionAccessors(providers, config);
        public ComGithubPlanPlayerVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.github.plan.player.analytics</b>
         */
        public ComGithubPlanPlayerAnalyticsVersionAccessors getAnalytics() {
            return vaccForComGithubPlanPlayerAnalyticsVersionAccessors;
        }

    }

    public static class ComGithubPlanPlayerAnalyticsVersionAccessors extends VersionFactory  {

        public ComGithubPlanPlayerAnalyticsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.github.plan.player.analytics.plan</b> with value <b>5.5.2150</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPlan() { return getVersion("com.github.plan.player.analytics.plan"); }

    }

    public static class ComGithubRevxrsalVersionAccessors extends VersionFactory  {

        private final ComGithubRevxrsalLampVersionAccessors vaccForComGithubRevxrsalLampVersionAccessors = new ComGithubRevxrsalLampVersionAccessors(providers, config);
        public ComGithubRevxrsalVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.github.revxrsal.lamp</b>
         */
        public ComGithubRevxrsalLampVersionAccessors getLamp() {
            return vaccForComGithubRevxrsalLampVersionAccessors;
        }

    }

    public static class ComGithubRevxrsalLampVersionAccessors extends VersionFactory  {

        public ComGithubRevxrsalLampVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.github.revxrsal.lamp.bukkit</b> with value <b>3.1.7</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBukkit() { return getVersion("com.github.revxrsal.lamp.bukkit"); }

        /**
         * Version alias <b>com.github.revxrsal.lamp.bungee</b> with value <b>3.1.7</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBungee() { return getVersion("com.github.revxrsal.lamp.bungee"); }

        /**
         * Version alias <b>com.github.revxrsal.lamp.common</b> with value <b>3.1.7</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCommon() { return getVersion("com.github.revxrsal.lamp.common"); }

        /**
         * Version alias <b>com.github.revxrsal.lamp.velocity</b> with value <b>3.1.7</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getVelocity() { return getVersion("com.github.revxrsal.lamp.velocity"); }

    }

    public static class ComGithubTechnicallycodedVersionAccessors extends VersionFactory  {

        public ComGithubTechnicallycodedVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.github.technicallycoded.folialib</b> with value <b>main-SNAPSHOT</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getFolialib() { return getVersion("com.github.technicallycoded.folialib"); }

    }

    public static class ComGoogleVersionAccessors extends VersionFactory  {

        private final ComGoogleCodeVersionAccessors vaccForComGoogleCodeVersionAccessors = new ComGoogleCodeVersionAccessors(providers, config);
        public ComGoogleVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.google.code</b>
         */
        public ComGoogleCodeVersionAccessors getCode() {
            return vaccForComGoogleCodeVersionAccessors;
        }

    }

    public static class ComGoogleCodeVersionAccessors extends VersionFactory  {

        private final ComGoogleCodeGsonVersionAccessors vaccForComGoogleCodeGsonVersionAccessors = new ComGoogleCodeGsonVersionAccessors(providers, config);
        public ComGoogleCodeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.google.code.gson</b>
         */
        public ComGoogleCodeGsonVersionAccessors getGson() {
            return vaccForComGoogleCodeGsonVersionAccessors;
        }

    }

    public static class ComGoogleCodeGsonVersionAccessors extends VersionFactory  {

        public ComGoogleCodeGsonVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.google.code.gson.gson</b> with value <b>2.10</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGson() { return getVersion("com.google.code.gson.gson"); }

    }

    public static class ComVelocitypoweredVersionAccessors extends VersionFactory  {

        private final ComVelocitypoweredVelocityVersionAccessors vaccForComVelocitypoweredVelocityVersionAccessors = new ComVelocitypoweredVelocityVersionAccessors(providers, config);
        public ComVelocitypoweredVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.velocitypowered.velocity</b>
         */
        public ComVelocitypoweredVelocityVersionAccessors getVelocity() {
            return vaccForComVelocitypoweredVelocityVersionAccessors;
        }

    }

    public static class ComVelocitypoweredVelocityVersionAccessors extends VersionFactory  {

        public ComVelocitypoweredVelocityVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.velocitypowered.velocity.api</b> with value <b>3.3.0-SNAPSHOT</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("com.velocitypowered.velocity.api"); }

    }

    public static class ComViaversionVersionAccessors extends VersionFactory  {

        private final ComViaversionViaversionVersionAccessors vaccForComViaversionViaversionVersionAccessors = new ComViaversionViaversionVersionAccessors(providers, config);
        public ComViaversionVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.viaversion.viaversion</b>
         */
        public ComViaversionViaversionVersionAccessors getViaversion() {
            return vaccForComViaversionViaversionVersionAccessors;
        }

    }

    public static class ComViaversionViaversionVersionAccessors extends VersionFactory  {

        public ComViaversionViaversionVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.viaversion.viaversion.api</b> with value <b>4.5.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("com.viaversion.viaversion.api"); }

    }

    public static class ComZaxxerVersionAccessors extends VersionFactory  {

        public ComZaxxerVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.zaxxer.hikaricp</b> with value <b>5.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getHikaricp() { return getVersion("com.zaxxer.hikaricp"); }

    }

    public static class DeVersionAccessors extends VersionFactory  {

        private final DeThemoepVersionAccessors vaccForDeThemoepVersionAccessors = new DeThemoepVersionAccessors(providers, config);
        public DeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.de.themoep</b>
         */
        public DeThemoepVersionAccessors getThemoep() {
            return vaccForDeThemoepVersionAccessors;
        }

    }

    public static class DeThemoepVersionAccessors extends VersionFactory  {

        private final DeThemoepMinedownVersionAccessors vaccForDeThemoepMinedownVersionAccessors = new DeThemoepMinedownVersionAccessors(providers, config);
        public DeThemoepVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.de.themoep.minedown</b>
         */
        public DeThemoepMinedownVersionAccessors getMinedown() {
            return vaccForDeThemoepMinedownVersionAccessors;
        }

    }

    public static class DeThemoepMinedownVersionAccessors extends VersionFactory  {

        public DeThemoepMinedownVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>de.themoep.minedown.adventure</b> with value <b>1.7.1-SNAPSHOT</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAdventure() { return getVersion("de.themoep.minedown.adventure"); }

    }

    public static class DevVersionAccessors extends VersionFactory  {

        private final DevDejvokepVersionAccessors vaccForDevDejvokepVersionAccessors = new DevDejvokepVersionAccessors(providers, config);
        public DevVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.dev.dejvokep</b>
         */
        public DevDejvokepVersionAccessors getDejvokep() {
            return vaccForDevDejvokepVersionAccessors;
        }

    }

    public static class DevDejvokepVersionAccessors extends VersionFactory  {

        private final DevDejvokepBoostedVersionAccessors vaccForDevDejvokepBoostedVersionAccessors = new DevDejvokepBoostedVersionAccessors(providers, config);
        public DevDejvokepVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.dev.dejvokep.boosted</b>
         */
        public DevDejvokepBoostedVersionAccessors getBoosted() {
            return vaccForDevDejvokepBoostedVersionAccessors;
        }

    }

    public static class DevDejvokepBoostedVersionAccessors extends VersionFactory  {

        public DevDejvokepBoostedVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>dev.dejvokep.boosted.yaml</b> with value <b>1.3.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getYaml() { return getVersion("dev.dejvokep.boosted.yaml"); }

    }

    public static class FrVersionAccessors extends VersionFactory  {

        private final FrIbanVersionAccessors vaccForFrIbanVersionAccessors = new FrIbanVersionAccessors(providers, config);
        public FrVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.fr.iban</b>
         */
        public FrIbanVersionAccessors getIban() {
            return vaccForFrIbanVersionAccessors;
        }

    }

    public static class FrIbanVersionAccessors extends VersionFactory  {

        public FrIbanVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>fr.iban.bungeehomes</b> with value <b>1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBungeehomes() { return getVersion("fr.iban.bungeehomes"); }

        /**
         * Version alias <b>fr.iban.warps</b> with value <b>1.0-SNAPSHOT</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getWarps() { return getVersion("fr.iban.warps"); }

    }

    public static class IoVersionAccessors extends VersionFactory  {

        private final IoGithubVersionAccessors vaccForIoGithubVersionAccessors = new IoGithubVersionAccessors(providers, config);
        private final IoPapermcVersionAccessors vaccForIoPapermcVersionAccessors = new IoPapermcVersionAccessors(providers, config);
        public IoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.io.github</b>
         */
        public IoGithubVersionAccessors getGithub() {
            return vaccForIoGithubVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.io.papermc</b>
         */
        public IoPapermcVersionAccessors getPapermc() {
            return vaccForIoPapermcVersionAccessors;
        }

    }

    public static class IoGithubVersionAccessors extends VersionFactory  {

        private final IoGithubWaterfallmcVersionAccessors vaccForIoGithubWaterfallmcVersionAccessors = new IoGithubWaterfallmcVersionAccessors(providers, config);
        public IoGithubVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.io.github.waterfallmc</b>
         */
        public IoGithubWaterfallmcVersionAccessors getWaterfallmc() {
            return vaccForIoGithubWaterfallmcVersionAccessors;
        }

    }

    public static class IoGithubWaterfallmcVersionAccessors extends VersionFactory  {

        private final IoGithubWaterfallmcWaterfallVersionAccessors vaccForIoGithubWaterfallmcWaterfallVersionAccessors = new IoGithubWaterfallmcWaterfallVersionAccessors(providers, config);
        public IoGithubWaterfallmcVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.io.github.waterfallmc.waterfall</b>
         */
        public IoGithubWaterfallmcWaterfallVersionAccessors getWaterfall() {
            return vaccForIoGithubWaterfallmcWaterfallVersionAccessors;
        }

    }

    public static class IoGithubWaterfallmcWaterfallVersionAccessors extends VersionFactory  {

        public IoGithubWaterfallmcWaterfallVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>io.github.waterfallmc.waterfall.api</b> with value <b>1.19-R0.1-SNAPSHOT</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("io.github.waterfallmc.waterfall.api"); }

    }

    public static class IoPapermcVersionAccessors extends VersionFactory  {

        private final IoPapermcPaperVersionAccessors vaccForIoPapermcPaperVersionAccessors = new IoPapermcPaperVersionAccessors(providers, config);
        public IoPapermcVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.io.papermc.paper</b>
         */
        public IoPapermcPaperVersionAccessors getPaper() {
            return vaccForIoPapermcPaperVersionAccessors;
        }

    }

    public static class IoPapermcPaperVersionAccessors extends VersionFactory  {

        private final IoPapermcPaperPaperVersionAccessors vaccForIoPapermcPaperPaperVersionAccessors = new IoPapermcPaperPaperVersionAccessors(providers, config);
        public IoPapermcPaperVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.io.papermc.paper.paper</b>
         */
        public IoPapermcPaperPaperVersionAccessors getPaper() {
            return vaccForIoPapermcPaperPaperVersionAccessors;
        }

    }

    public static class IoPapermcPaperPaperVersionAccessors extends VersionFactory  {

        public IoPapermcPaperPaperVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>io.papermc.paper.paper.api</b> with value <b>1.21.1-R0.1-SNAPSHOT</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("io.papermc.paper.paper.api"); }

    }

    public static class MeVersionAccessors extends VersionFactory  {

        private final MeClipVersionAccessors vaccForMeClipVersionAccessors = new MeClipVersionAccessors(providers, config);
        private final MeNeznamyVersionAccessors vaccForMeNeznamyVersionAccessors = new MeNeznamyVersionAccessors(providers, config);
        public MeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.me.clip</b>
         */
        public MeClipVersionAccessors getClip() {
            return vaccForMeClipVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.me.neznamy</b>
         */
        public MeNeznamyVersionAccessors getNeznamy() {
            return vaccForMeNeznamyVersionAccessors;
        }

    }

    public static class MeClipVersionAccessors extends VersionFactory  {

        public MeClipVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>me.clip.placeholderapi</b> with value <b>2.10.9</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPlaceholderapi() { return getVersion("me.clip.placeholderapi"); }

    }

    public static class MeNeznamyVersionAccessors extends VersionFactory  {

        private final MeNeznamyTabVersionAccessors vaccForMeNeznamyTabVersionAccessors = new MeNeznamyTabVersionAccessors(providers, config);
        public MeNeznamyVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.me.neznamy.tab</b>
         */
        public MeNeznamyTabVersionAccessors getTab() {
            return vaccForMeNeznamyTabVersionAccessors;
        }

    }

    public static class MeNeznamyTabVersionAccessors extends VersionFactory  {

        public MeNeznamyTabVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>me.neznamy.tab.api</b> with value <b>4.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("me.neznamy.tab.api"); }

        /**
         * Version alias <b>me.neznamy.tab.shared</b> with value <b>4.0.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getShared() { return getVersion("me.neznamy.tab.shared"); }

    }

    public static class MysqlVersionAccessors extends VersionFactory  {

        private final MysqlMysqlVersionAccessors vaccForMysqlMysqlVersionAccessors = new MysqlMysqlVersionAccessors(providers, config);
        public MysqlVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.mysql.mysql</b>
         */
        public MysqlMysqlVersionAccessors getMysql() {
            return vaccForMysqlMysqlVersionAccessors;
        }

    }

    public static class MysqlMysqlVersionAccessors extends VersionFactory  {

        private final MysqlMysqlConnectorVersionAccessors vaccForMysqlMysqlConnectorVersionAccessors = new MysqlMysqlConnectorVersionAccessors(providers, config);
        public MysqlMysqlVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.mysql.mysql.connector</b>
         */
        public MysqlMysqlConnectorVersionAccessors getConnector() {
            return vaccForMysqlMysqlConnectorVersionAccessors;
        }

    }

    public static class MysqlMysqlConnectorVersionAccessors extends VersionFactory  {

        public MysqlMysqlConnectorVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>mysql.mysql.connector.java</b> with value <b>8.0.33</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJava() { return getVersion("mysql.mysql.connector.java"); }

    }

    public static class NetVersionAccessors extends VersionFactory  {

        private final NetEss3VersionAccessors vaccForNetEss3VersionAccessors = new NetEss3VersionAccessors(providers, config);
        private final NetLuckpermsVersionAccessors vaccForNetLuckpermsVersionAccessors = new NetLuckpermsVersionAccessors(providers, config);
        private final NetWilliam278VersionAccessors vaccForNetWilliam278VersionAccessors = new NetWilliam278VersionAccessors(providers, config);
        public NetVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.net.ess3</b>
         */
        public NetEss3VersionAccessors getEss3() {
            return vaccForNetEss3VersionAccessors;
        }

        /**
         * Group of versions at <b>versions.net.luckperms</b>
         */
        public NetLuckpermsVersionAccessors getLuckperms() {
            return vaccForNetLuckpermsVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.net.william278</b>
         */
        public NetWilliam278VersionAccessors getWilliam278() {
            return vaccForNetWilliam278VersionAccessors;
        }

    }

    public static class NetEss3VersionAccessors extends VersionFactory  {

        public NetEss3VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>net.ess3.essentialsx</b> with value <b>2.18.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getEssentialsx() { return getVersion("net.ess3.essentialsx"); }

    }

    public static class NetLuckpermsVersionAccessors extends VersionFactory  {

        public NetLuckpermsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>net.luckperms.api</b> with value <b>5.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("net.luckperms.api"); }

    }

    public static class NetWilliam278VersionAccessors extends VersionFactory  {

        public NetWilliam278VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>net.william278.papiproxybridge</b> with value <b>1.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPapiproxybridge() { return getVersion("net.william278.papiproxybridge"); }

    }

    public static class OrgVersionAccessors extends VersionFactory  {

        private final OrgApacheVersionAccessors vaccForOrgApacheVersionAccessors = new OrgApacheVersionAccessors(providers, config);
        private final OrgGeysermcVersionAccessors vaccForOrgGeysermcVersionAccessors = new OrgGeysermcVersionAccessors(providers, config);
        private final OrgJetbrainsVersionAccessors vaccForOrgJetbrainsVersionAccessors = new OrgJetbrainsVersionAccessors(providers, config);
        private final OrgOcpsoftVersionAccessors vaccForOrgOcpsoftVersionAccessors = new OrgOcpsoftVersionAccessors(providers, config);
        public OrgVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.apache</b>
         */
        public OrgApacheVersionAccessors getApache() {
            return vaccForOrgApacheVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.geysermc</b>
         */
        public OrgGeysermcVersionAccessors getGeysermc() {
            return vaccForOrgGeysermcVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.jetbrains</b>
         */
        public OrgJetbrainsVersionAccessors getJetbrains() {
            return vaccForOrgJetbrainsVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.ocpsoft</b>
         */
        public OrgOcpsoftVersionAccessors getOcpsoft() {
            return vaccForOrgOcpsoftVersionAccessors;
        }

    }

    public static class OrgApacheVersionAccessors extends VersionFactory  {

        private final OrgApacheCommonsVersionAccessors vaccForOrgApacheCommonsVersionAccessors = new OrgApacheCommonsVersionAccessors(providers, config);
        public OrgApacheVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.apache.commons</b>
         */
        public OrgApacheCommonsVersionAccessors getCommons() {
            return vaccForOrgApacheCommonsVersionAccessors;
        }

    }

    public static class OrgApacheCommonsVersionAccessors extends VersionFactory  {

        private final OrgApacheCommonsCommonsVersionAccessors vaccForOrgApacheCommonsCommonsVersionAccessors = new OrgApacheCommonsCommonsVersionAccessors(providers, config);
        public OrgApacheCommonsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.apache.commons.commons</b>
         */
        public OrgApacheCommonsCommonsVersionAccessors getCommons() {
            return vaccForOrgApacheCommonsCommonsVersionAccessors;
        }

    }

    public static class OrgApacheCommonsCommonsVersionAccessors extends VersionFactory  {

        public OrgApacheCommonsCommonsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.apache.commons.commons.pool2</b> with value <b>2.12.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPool2() { return getVersion("org.apache.commons.commons.pool2"); }

    }

    public static class OrgGeysermcVersionAccessors extends VersionFactory  {

        private final OrgGeysermcFloodgateVersionAccessors vaccForOrgGeysermcFloodgateVersionAccessors = new OrgGeysermcFloodgateVersionAccessors(providers, config);
        public OrgGeysermcVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.geysermc.floodgate</b>
         */
        public OrgGeysermcFloodgateVersionAccessors getFloodgate() {
            return vaccForOrgGeysermcFloodgateVersionAccessors;
        }

    }

    public static class OrgGeysermcFloodgateVersionAccessors extends VersionFactory  {

        public OrgGeysermcFloodgateVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.geysermc.floodgate.api</b> with value <b>2.2.0-SNAPSHOT</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("org.geysermc.floodgate.api"); }

    }

    public static class OrgJetbrainsVersionAccessors extends VersionFactory  {

        public OrgJetbrainsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.jetbrains.annotations</b> with value <b>24.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAnnotations() { return getVersion("org.jetbrains.annotations"); }

    }

    public static class OrgOcpsoftVersionAccessors extends VersionFactory  {

        private final OrgOcpsoftPrettytimeVersionAccessors vaccForOrgOcpsoftPrettytimeVersionAccessors = new OrgOcpsoftPrettytimeVersionAccessors(providers, config);
        public OrgOcpsoftVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.ocpsoft.prettytime</b>
         */
        public OrgOcpsoftPrettytimeVersionAccessors getPrettytime() {
            return vaccForOrgOcpsoftPrettytimeVersionAccessors;
        }

    }

    public static class OrgOcpsoftPrettytimeVersionAccessors extends VersionFactory  {

        private final OrgOcpsoftPrettytimePrettytimeVersionAccessors vaccForOrgOcpsoftPrettytimePrettytimeVersionAccessors = new OrgOcpsoftPrettytimePrettytimeVersionAccessors(providers, config);
        public OrgOcpsoftPrettytimeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.ocpsoft.prettytime.prettytime</b>
         */
        public OrgOcpsoftPrettytimePrettytimeVersionAccessors getPrettytime() {
            return vaccForOrgOcpsoftPrettytimePrettytimeVersionAccessors;
        }

    }

    public static class OrgOcpsoftPrettytimePrettytimeVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public OrgOcpsoftPrettytimePrettytimeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.ocpsoft.prettytime.prettytime</b> with value <b>5.0.3.Final</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("org.ocpsoft.prettytime.prettytime"); }

        /**
         * Version alias <b>org.ocpsoft.prettytime.prettytime.x1</b> with value <b>5.0.9.Final</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getX1() { return getVersion("org.ocpsoft.prettytime.prettytime.x1"); }

    }

    public static class RedisVersionAccessors extends VersionFactory  {

        private final RedisClientsVersionAccessors vaccForRedisClientsVersionAccessors = new RedisClientsVersionAccessors(providers, config);
        public RedisVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.redis.clients</b>
         */
        public RedisClientsVersionAccessors getClients() {
            return vaccForRedisClientsVersionAccessors;
        }

    }

    public static class RedisClientsVersionAccessors extends VersionFactory  {

        public RedisClientsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>redis.clients.jedis</b> with value <b>5.1.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJedis() { return getVersion("redis.clients.jedis"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
