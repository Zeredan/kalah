pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Kalah"
include(":app")
include(":features")
include(":features:play")
include(":features:profile")
include(":features:settings")
include(":data")
include(":data:settings")
include(":features:language")
include(":data:user")
include(":data:networking_core")
include(":features:game_vs_bot")
include(":features:lobby_management")
include(":data:lobby")
include(":data:kalah_game")
include(":features:game_vs_human")
