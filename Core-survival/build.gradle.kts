/**
 * CoreSurvival
 */
repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.dmulloy2.net/nexus/repository/public/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://maven.playpro.com")
    maven("https://jitpack.io")
    maven("https://repo.essentialsx.net/releases/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation(project(":CoreCommon"))
    compileOnly(project(":CorePaper"))
    compileOnly("org.ocpsoft.prettytime:prettytime:5.0.9.Final")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("net.essentialsx:EssentialsX:2.20.0")
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly(files("${projectDir}/lib/BetterRTP-3.6.1.jar")) // Dépendance système pour BetterRTP
    compileOnly("fr.iban:Warps:1.0-SNAPSHOT")
    compileOnly("fr.iban:BungeeHomes:1.0")

    compileOnly("com.github.Revxrsal.Lamp:common:3.1.7")
    compileOnly("com.github.Revxrsal.Lamp:bukkit:3.1.7")

    compileOnly("com.github.technicallycoded:FoliaLib:main-SNAPSHOT")
}

tasks.compileJava {
    dependsOn(":CoreCommon:shadowJar")
    dependsOn(":CorePaper:shadowJar")
}