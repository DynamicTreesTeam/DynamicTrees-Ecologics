import me.modmuss50.mpp.ReleaseType
import org.gradle.api.publish.maven.MavenPublication

plugins {
    id("java-library")
    id("maven-publish")
    id("net.neoforged.moddev") version "2.0.143"
    id("me.modmuss50.mod-publish-plugin") version "2.+"
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
val versionType = providers.gradleProperty("versionType")
val curseProjectId = providers.gradleProperty("curseProjectId")
val rinthProjectId = providers.gradleProperty("rinthProjectId")

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
    flatDir {
        dir("libs")
    }
    mavenLocal()
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

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven("file://${project.projectDir}/repo")
    }
}

publishMods {
    file.set(tasks.jar.flatMap { it.archiveFile })
    additionalFiles.from(tasks.named("sourcesJar"))
    displayName.set("${modName.get()}-NeoForge-${project.version}")
    val changelogFile = file("build/changelog.md")
    if (changelogFile.exists()) {
        changelog.set(changelogFile.readText())
    }
    type.set(versionType.map(String::uppercase).map(ReleaseType::of))
    modLoaders.add("neoforge")

    curseforge {
        projectId.set(curseProjectId)
        projectSlug.set("dynamic-trees-ecologics")
        accessToken.set(System.getenv("CURSEFORGE_API_KEY"))
        minecraftVersions.add(mcVersion)
        requires("dynamictrees")
        optional("dynamictreesplus")
        requires("dynamic-trees-addon-lib")
        requires("ecologics")
    }
    modrinth {
        projectId.set(rinthProjectId)
        accessToken.set(System.getenv("MODRINTH_TOKEN"))
        minecraftVersions.add(mcVersion)
        requires("vdjF5PL5") //dt
        optional("qaO9Dqpu") //dt+
        requires("ju42L8G7") //dynamic-trees-addon-lib
        requires("NCKpPR0Z") //ecologics
    }
}
