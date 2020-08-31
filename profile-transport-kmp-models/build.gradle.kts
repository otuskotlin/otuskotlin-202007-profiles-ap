plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

kotlin {
    /* Targets configuration omitted.
*  To find out how to configure the targets, please follow the link:
*  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    js {
        browser()
        nodejs()
    }

    jvm()

    sourceSets {
        val serializationVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-js"))
            }
        }
    }
}