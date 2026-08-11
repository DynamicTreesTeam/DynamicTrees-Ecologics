plugins {
    id("net.neoforged.moddev") version "2.0.143"
}

val mcVersion = providers.gradleProperty("mcVersion")
val neoVersion = providers.gradleProperty("neoVersion")
val modName = providers.gradleProperty("modName")
val modId = providers.gradleProperty("modId")
val modVersion = providers.gradleProperty("modVersion")
val modGroup = providers.gradleProperty("group")
val dynamicTreesVersion = providers.gradleProperty("dynamicTreesVersion")
val dynamicTreesPlusVersion = providers.gradleProperty("dynamicTreesPlusVersion")
val dynamicTreesAddonLibVersion = providers.gradleProperty("dynamicTreesAddonLibVersion")
val ecologicsVersion = providers.gradleProperty("ecologicsVersion")

version = "${mcVersion.get()}-${modVersion.get()}"
group = modGroup.get()

base {
    archivesName.set(modName)
}

java {
    withSourcesJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

neoForge {
    version = neoVersion.get()

    runs {
        create("client") {
            client()
        }

        create("server") {
            server()
        }

        create("data") {
            data()

            programArguments.addAll(
                "--all",
                "--existing", file("src/main/resources").absolutePath,
                "--output", file("src/generated/resources/").absolutePath,
                "--mod", modId.get(),
                "--existing-mod", "dynamictrees",
                "--existing-mod", "dynamictreesplus",
                "--existing-mod", "ecologics"
            )
        }
    }

    mods {
        create(modId.get()) {
            sourceSet(sourceSets.main.get())
        }
    }

    parchment {
        minecraftVersion.set(mcVersion)
    }
}

sourceSets {
    main {
        resources {
            srcDir("src/generated/resources")
        }
    }
}

repositories {
    maven("https://ldtteam.jfrog.io/ldtteam/modding/")
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven") {
                name = "Modrinth"
            }
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
    maven("https://www.cursemaven.com") {
        content {
            includeGroup("curse.maven")
        }
    }
}

dependencies {
    implementation("com.dtteam.dynamictrees:dynamictrees-neoforge-${mcVersion.get()}:${dynamicTreesVersion.get()}")
    implementation("com.dtteam.dynamictreesplus:DynamicTreesPlus:${dynamicTreesPlusVersion.get()}")
    implementation("maven.modrinth:dynamic-trees-addon-lib:${dynamicTreesAddonLibVersion.get()}")

    implementation("maven.modrinth:ecologics:${ecologicsVersion.get()}-NeoForge")
}

tasks.processResources {
    val replaceProperties = mapOf(
        "mcVersion" to mcVersion,
        "neoVersion" to neoVersion,
        "modId" to modId,
        "modVersion" to modVersion,
        "dynamicTreesVersion" to dynamicTreesVersion,
        "dynamicTreesPlusVersion" to dynamicTreesPlusVersion,
        "dynamicTreesAddonLibVersion" to dynamicTreesAddonLibVersion,
        "ecologicsVersion" to ecologicsVersion
    )

    inputs.properties(replaceProperties)

    filteringCharset = "UTF-8"

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replaceProperties.mapValues { it.value.get() })
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.jar {
    from("LICENSE")
}
