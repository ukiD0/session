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
        maven { url  = uri("https://jitpack.io")
                 uri ("http://maven.google.com/")}
        //для пдф
        jcenter()
    }
}

rootProject.name = "session2"
include(":app")
 