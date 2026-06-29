# Publishing ComposeNavMotion

This guide covers **JitPack**, **Maven Central**, **GitHub Actions**, and verification.

## Coordinates (current)

| Channel | Dependency |
|---------|------------|
| **Maven Central** | `io.github.saadkhalidkhan:composenavmotion:1.0.0` |
| **JitPack** | `com.github.saadkhalidkhan:ComposeNavMotion:1.0.0` |

For new versions: bump `COMPOSE_NAV_MOTION_VERSION_NAME` in `gradle.properties` and tag `vX.Y.Z`.

---

## Quick links

| Resource | URL |
|----------|-----|
| Maven Central artifact | https://central.sonatype.com/artifact/io.github.saadkhalidkhan/composenavmotion |
| Maven repo browser | https://repo1.maven.org/maven2/io/github/saadkhalidkhan/composenavmotion/ |
| JitPack builds | https://jitpack.io/#saadkhalidkhan/ComposeNavMotion |
| GitHub Releases | https://github.com/saadkhalidkhan/ComposeNavMotion/releases |

---

## Consumers — installation

### Maven Central

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.github.saadkhalidkhan:composenavmotion:1.0.0")
}
```

Sync Gradle after changing the version. First-time availability on Central can take **10–30 minutes** after publish.

### JitPack

**Step 1.** Add the JitPack repository in `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**Step 2.** Add the dependency:

```kotlin
dependencies {
    implementation("com.github.saadkhalidkhan:ComposeNavMotion:1.0.0")
}
```

**Important:** Replace `1.0.0` with your tag (e.g. `1.0.1`). JitPack only builds **tagged** commits.

---

## JitPack — first time & each release

1. Ensure the repo is public: [saadkhalidkhan/ComposeNavMotion](https://github.com/saadkhalidkhan/ComposeNavMotion).
2. Create and push a tag (version must match `COMPOSE_NAV_MOTION_VERSION_NAME`):
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
3. Open **https://jitpack.io/#saadkhalidkhan/ComposeNavMotion/v1.0.0** and wait for a green build.
4. Click **Get it** to see the Gradle line.

`jitpack.yml` configures JDK 17, `assembleRelease`, and publish to Maven Local (signing disabled for JitPack).

### JitPack troubleshooting

| Problem | Fix |
|---------|-----|
| Build failed | Open **Log** on JitPack; run locally: `./gradlew :nav-animation:assembleRelease :nav-animation:publishMavenPublicationToMavenLocal -Psigning.required=false` |
| Old version cached | Use **Look up** with the exact tag, or `1.0.0` not `v1.0.0` in the dependency string |
| Dependency not found | Add `maven { url = uri("https://jitpack.io") }` in **settings**, not project `build.gradle` |

---

## Maven Central — Sonatype setup (one-time)

If you already published PinFlow under `io.github.saadkhalidkhan`, **no new namespace setup is required**. The same Central Portal account and GPG key work for this artifact.

### Local credentials (recommended location)

Copy [gradle.properties.example](gradle.properties.example) and set values in **`~/.gradle/gradle.properties`** only:

```properties
mavenCentralUsername=YOUR_TOKEN_USER
mavenCentralPassword=YOUR_TOKEN_PASSWORD
signing.keyId=YOUR_KEY_ID
signing.password=YOUR_KEY_PASSPHRASE
signing.useGpgCmd=true
```

### Publish locally

```bash
./gradlew :nav-animation:publishToMavenCentral
```

Dry run (no upload):

```bash
./gradlew :nav-animation:publishToMavenLocal -Psigning.required=false
```

Artifacts: `~/.m2/repository/io/github/saadkhalidkhan/composenavmotion/`

---

## GitHub Actions — CI releases

Automate Maven Central uploads on every `v*` tag.

### Step 1 — Add secrets

Follow [.github/SETUP_SECRETS.md](.github/SETUP_SECRETS.md). You can reuse the same four secrets from PinFlow — copy them into this repo's Actions secrets.

### Step 2 — Release via tag

```bash
# After bumping COMPOSE_NAV_MOTION_VERSION_NAME and committing:
git tag v1.0.0
git push origin master
git push origin v1.0.0
```

The **Release** workflow runs:

```bash
./gradlew :nav-animation:testDebugUnitTest :nav-animation:publishToMavenCentral
```

---

## Verify a release

### Maven Central

```bash
https://repo1.maven.org/maven2/io/github/saadkhalidkhan/composenavmotion/1.0.0/
```

In a test app:

```kotlin
implementation("io.github.saadkhalidkhan:composenavmotion:1.0.0")
```

Refresh Gradle; confirm imports from `com.composenavmotion`.

### JitPack

1. Green build at https://jitpack.io/#saadkhalidkhan/ComposeNavMotion/v1.0.0
2. Test dependency resolves in a clean project.

---

## Security

- Never commit `mavenCentralPassword`, GPG keys, or `signing.properties`.
- `.gitignore` excludes `central.properties`, `release.properties`, `signing.properties`.
- Rotate Central tokens if exposed.
- CI secrets: only in GitHub **Actions secrets**, not in the repo.

---

## Full release flow (summary)

1. Bump `COMPOSE_NAV_MOTION_VERSION_NAME` + README version lines
2. Merge to `master`
3. `./gradlew :navmotion-core:testDebugUnitTest :navmotion-material:testDebugUnitTest :navmotion-shared:testDebugUnitTest :navmotion-sample:assembleDebug`
4. Publish Maven Central (local **or** push tag for CI)
5. Push same tag for JitPack → verify green on jitpack.io
6. Optional: GitHub Release with changelog
