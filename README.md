<p align="center">
  <img src="https://aguitech.com/images/logo.png" alt="AGUITECH" width="120">
</p>

<h1 align="center">🎬 Edita tu película · Android</h1>

<p align="center">
  <strong>Editor de video mobile-first para Android.</strong><br>
  Graba, corta, ordena clips, agrega música y exporta — todo desde el teléfono,
  sin marca de agua, sin login, sin nube obligatoria.
</p>

<p align="center">
  <a href="#que-es">Qué es</a> ·
  <a href="#features">Features</a> ·
  <a href="#stack">Stack</a> ·
  <a href="#estructura">Estructura</a> ·
  <a href="#run">Cómo correrlo</a> ·
  <a href="#build">Build APK</a> ·
  <a href="#deploy">Deploy</a>
</p>

---

## ¿Qué es

`edita-tu-pelicula-android` es un **editor de video minimalista pensado
para correr 100% en el dispositivo Android del creador**. La idea es quitar
todo lo que sobra de los editores tipo CapCut / VN / Premiere y dejar solo el
flujo esencial:

```
Capturar → Recortar → Ordenar → Música → Tittir 🎬
```

**Promesa:** cualquier persona con un Android medianito (Android 8+, 2 GB RAM)
debe poder editar un video de 2-5 minutos en menos de 5 minutos.

### Casos de uso

- 📱 **Creadores de TikTok / Reels / Shorts** que graban en bloques y quieren unir.
- 🎓 **Estudiantes** que necesitan entregar videos sin marca de agua.
- 🏪 **Pequeños negocios** grabando testimonios de clientes.
- 👨‍👩‍👧 **Familias** armando videos de vacaciones o cumpleaños.
- 📰 **Periodistas ciudadanos** que necesitan editar material rápido antes de publicar.

## Features

| Feature | Estado |
|---------|--------|
| 🎬 Captura de video desde cámara nativa | ✅ |
| ✂️ Recorte por entrada/salida (trim) | ✅ |
| 🧩 Unir múltiples clips en una timeline | ✅ |
| 🎵 Agregar pista de audio (música del dispositivo) | ✅ |
| 📐 Reencuadre (16:9 / 9:16 / 1:1 / 4:5) | ✅ |
| 📝 Texto superpuesto (título + subtítulo) | ✅ |
| 💾 Exportar MP4 al storage local | ✅ |
| 🖼️ Tumbnail del video exportado | ✅ |
| 🌙 Tema claro / oscuro | ✅ |
| 📂 Proyectos guardados localmente | ✅ |
| ⬆️ Compartir a apps externas (WhatsApp, IG, TikTok) | 🚧 |
| 🔀 Transiciones entre clips | 🚧 |
| 🎨 Filtros de video (cámara lenta, aceleración) | 🚧 |
| ☁️ Backup opcional a la nube (no obligatorio) | 📋 |

✅ Implementado · 🚧 En desarrollo · 📋 Planeado

## Stack

- **Android nativo** — Kotlin + Jetpack Compose para UI.
- **CameraX** — captura de video y preview.
- **MediaCodec / MediaMuxer** — encode y muxing para evitar marcas de agua forzosas.
- **ExoPlayer** — preview dentro del editor.
- **Room** — base de datos local para proyectos y metadatos.
- **Coroutines + Flow** — procesamiento asíncrono.
- **Sin telemetría obligatoria**, sin SDKs de tracking, sin ads.

## Estructura

```
edita-tu-pelicula-android/
├── app/
│   ├── src/main/
│   │   ├── java/com/aguitech/editatupelicula/
│   │   │   ├── ui/                  # pantallas Compose
│   │   │   │   ├── capture/         # grabar video
│   │   │   │   ├── trim/            # recortar clips
│   │   │   │   ├── timeline/        # unir y ordenar
│   │   │   │   ├── audio/           # agregar música
│   │   │   │   ├── text/            # subtítulos
│   │   │   │   └── export/          # render final
│   │   │   ├── media/               # MediaCodec / MediaMuxer wrapper
│   │   │   ├── data/                # Room DB · proyectos
│   │   │   └── di/                  # Hilt modules
│   │   ├── res/                       # layouts, strings, temas
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── docs/                            # documentación
│   ├── ARCHITECTURE.md
│   └── EXPORT_PIPELINE.md
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
└── LICENSE
```

## Run

### Requisitos

- Android Studio Hedgehog (2023.1.1) o más nuevo
- Android SDK 34
- JDK 17
- Dispositivo físico Android 8.0+ (API 26) **o** emulador con cámara simulada

### Dev local

```bash
git clone https://github.com/aguitech/edita-tu-pelicula-android.git
cd edita-tu-pelicula-android
./gradlew assembleDebug
./gradlew installDebug    # instala en el dispositivo conectado por ADB
```

## Build APK

```bash
# Debug APK (sin firmar, no publicable)
./gradlew assembleDebug
# → app/build/outputs/apk/debug/app-debug.apk

# Release APK firmado (publicable)
./gradlew assembleRelease
# → app/build/outputs/apk/release/app-release.apk
```

> ⚠️ El `keystore` de firma NO está incluido en el repo.
> Cada desarrollador genera el suyo con `keytool -genkey -v -keystore release.keystore ...`.
> El path y las credenciales se manejan en `~/.gradle/gradle.properties` local, nunca en el repo.

## Deploy

- **App bundle (AAB)** se sube a Google Play Console cuando esté firmada y lista.
- **APK debug** se puede distribuir directo para QA interno.
- **GitHub Releases** se usan para versiones intermedias y betas.

## Identidad visual

| Token | valor |
|-------|--------|
| Azul AGUITECH | `#0066ff` |
| Cyan acento | `#00d4ff` |
| Fondo oscuro | `#0a0e1a` |
| Tipografía | Inter (Android system fallback) |
| Icono app | TBA — diseño AGUITECH en progreso |

## Principios

1. **Funciona offline.** No necesita internet para editar.
2. **Funciona sin cuenta.** Cero login, cero onboarding.
3. **No marca de agua.** El video exportado es tuyo, sin logos sobrepuestos.
4. **No tracking.** Cero analytics por default.
5. **Rápido en hardware modesto.** Optimizado para Android 8 con 2 GB RAM.

## License

MIT — úsalo, modifícalo, repártelo. Si te late, menciónanos.

---

<p align="center">
  Hecho con 🇨 por <a href="https://aguitech.com"><strong>AGUITECH</strong></a> ·
  Ingeniería + Diseño + Sistemas
</p>