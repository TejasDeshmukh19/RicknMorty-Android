pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Rick n Morty"
include(":app")
include(":ui-resources")
include(":helpers:base")
include(":router")
include(":features")
include(":features:characters")
include(":features:episodes")
include(":features:locations")
include(":helpers:network")
include(":data")
