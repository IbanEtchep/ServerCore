rootProject.name = "Core"
include(":CoreCommon")
include(":CoreVelocity")
include(":CorePaper")
include(":CoreSurvival")
project(":CoreCommon").projectDir = file("Core-common")
project(":CoreVelocity").projectDir = file("Core-velocity")
project(":CorePaper").projectDir = file("Core-paper")
project(":CoreSurvival").projectDir = file("Core-survival")
