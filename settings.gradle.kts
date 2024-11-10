rootProject.name = "Core"
include(":common")
include(":velocity")
include(":paper")
include(":survival")
project(":common").projectDir = file("Core-common")
project(":velocity").projectDir = file("Core-velocity")
project(":paper").projectDir = file("Core-paper")
project(":survival").projectDir = file("Core-survival")
