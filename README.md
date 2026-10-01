# Edita Tu Película — Android WebView

App Android que abre `https://tupeliculafinanciera.com/edita-tu-pelicula/` en un `WebView` optimizado para edición de video.

## 🎯 ¿Qué es?

Una app nativa Android (APK) que envuelve el editor de películas online de **Tu Película Financiera** en un WebView fullscreen con permisos completos de cámara, micrófono y almacenamiento.

## 📱 Características

| | |
|---|---|
| ✅ WebView optimizado | JavaScript habilitado, DOM storage, cookies |
| ✅ Cámara/Mic pre-aprobados | WebChromeClient.onPermissionRequest |
| ✅ Runtime permissions | CAMERA, RECORD_AUDIO, READ_MEDIA_IMAGES/VIDEO |
| ✅ Fullscreen | Sin status bar, edge-to-edge |
| ✅ Orientación libre | Portrait + Landscape (todas las rotaciones) |
| ✅ Network security | HTTP permitido para tupeliculafinanciera.com |
| ✅ Deep link | `edita-tu-pelicula://` |
| ✅ Hardware accelerated | Importante para video |
| ✅ Mixed content mode | Compatible con sitios mixtos |

## 📦 Especificaciones

| | |
|---|---|
| Package | `com.aguitech.editatupelicula` |
| Versión | 1.0 (versionCode 1) |
| minSdk | 21 (Android 5.0 Lollipop) |
| targetSdk | 34 (Android 14) |
| compileSdk | 34 |
| Lenguaje | Java 17 |
| Build | Gradle 8.9 + AGP 8.7.3 |
| APK debug | 5.5 MB |

## 🏗️ Estructura

```
edita-tu-pelicula-android/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/aguitech/editatupelicula/MainActivity.java
│       └── res/
│           ├── drawable/ic_launcher_*.xml
│           ├── mipmap-*/ic_launcher.png (+ round)
│           ├── mipmap-anydpi-v26/ic_launcher.xml
│           ├── values/strings.xml
│           ├── values/styles.xml
│           └── xml/network_security_config.xml
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradle/wrapper/gradle-wrapper.jar + .properties
└── gradlew
```

## 🔐 Permisos declarados

```xml
<!-- AndroidManifest.xml -->
INTERNET, ACCESS_NETWORK_STATE     → WebView
CAMERA                              → grabar videos
RECORD_AUDIO                        → audio
READ_MEDIA_IMAGES, READ_MEDIA_VIDEO → API 33+
READ/WRITE_EXTERNAL_STORAGE         → API ≤28
```

## 🌐 Network Security

`network_security_config.xml` permite HTTP (cleartext) **solo** para `tupeliculafinanciera.com` y subdominios.

## 🚀 Compilar

```bash
cd ~/Projects/edita-tu-pelicula-android
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$JAVA_HOME/bin:$ANDROID_HOME/platform-tools:$PATH"

./gradlew assembleDebug
# APK → app/build/outputs/apk/debug/app-debug.apk
```

## ▶️ Instalar en dispositivo

```bash
# USB conectado + USB debugging ON
adb devices
adb install app/build/outputs/apk/debug/app-debug.apk
adb shell monkey -p com.aguitech.editatupelicula -c android.intent.category.LAUNCHER 1
```

## 🍎 Comparar con iOS

Esta es la versión Android de `ipad-casting` (la versión iOS en WKWebView).

| Feature | iOS (ipad-casting) | Android (edita-tu-pelicula-android) |
|---|---|---|
| URL | `/casting/` | `/edita-tu-pelicula/` |
| WebView engine | WKWebView | android.webkit.WebView |
| Lenguaje | Swift 6.3 | Java 17 |
| Build | xcodebuild | gradle |
| Permissions | Info.plist | AndroidManifest.xml |

## 📋 Requirements

- JDK 17+ (viene con Android Studio)
- Android SDK API 34
- Build Tools 34.0.0+
- Gradle 8.9 (wrapper incluido)

---

**Autor:** Héctor Aguilar ([@aguitech](https://github.com/aguitech))
**Repo:** https://github.com/aguitech/edita-tu-pelicula-android