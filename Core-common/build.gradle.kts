/**
 * CoreCommon
 */
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("redis.clients:jedis:5.1.3")
    compileOnly("com.google.code.gson:gson:2.10")
}