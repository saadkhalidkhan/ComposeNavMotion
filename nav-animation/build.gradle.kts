plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.maven.publish)
    signing
}

val composeNavMotionGroup = providers.gradleProperty("COMPOSE_NAV_MOTION_GROUP").get()
val composeNavMotionArtifactId = providers.gradleProperty("COMPOSE_NAV_MOTION_ARTIFACT_ID").get()
val composeNavMotionVersion = providers.gradleProperty("COMPOSE_NAV_MOTION_VERSION_NAME").get()

android {
    namespace = "com.composenavmotion.publish"
    compileSdk = 36
    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures { compose = true }
}

dependencies {
    api(project(":navmotion-core"))
    api(project(":navmotion-material"))
    api(project(":navmotion-shared"))
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    val signingRequired = providers.gradleProperty("signing.required")
        .map { it.toBoolean() }
        .getOrElse(true)
    if (signingRequired) {
        signAllPublications()
    }

    coordinates(composeNavMotionGroup, composeNavMotionArtifactId, composeNavMotionVersion)

    pom {
        name.set("ComposeNavMotion")
        description.set(
            "Animated navigation library for Jetpack Compose with type-safe routes, Material motion " +
                "presets, shared elements, and accessibility-aware configuration.",
        )
        inceptionYear.set("2026")
        url.set("https://github.com/saadkhalidkhan/ComposeNavMotion")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("saadkhalidkhan")
                name.set("Saad Khan")
                url.set("https://github.com/saadkhalidkhan")
            }
        }
        scm {
            url.set("https://github.com/saadkhalidkhan/ComposeNavMotion")
            connection.set("scm:git:git://github.com/saadkhalidkhan/ComposeNavMotion.git")
            developerConnection.set("scm:git:ssh://git@github.com/saadkhalidkhan/ComposeNavMotion.git")
        }
    }
}

signing {
    val inMemoryKey = providers.gradleProperty("signingInMemoryKey").orNull
    if (!inMemoryKey.isNullOrBlank()) {
        val password = providers.gradleProperty("signingInMemoryKeyPassword").orNull
        useInMemoryPgpKeys(inMemoryKey, password)
    } else {
        useGpgCmd()
    }
}
