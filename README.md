# CRIBA — Gestión Agrícola Inteligente

**CRIBA** es una aplicación móvil nativa para Android que permite a productores agrícolas
registrar y dar seguimiento a sus **parcelas, ciclos de cultivo, plagas, finanzas y clima**,
funcionando **sin conexión a internet** (offline-first). Su objetivo es llevar la trazabilidad
y las analíticas de una finca desde el celular, de forma simple y accesible.

---

## 🎯 Problema que resuelve

Los pequeños y medianos productores agrícolas suelen llevar el control de sus cultivos en
libretas o de memoria, lo que provoca pérdida de información, decisiones sin datos y falta de
análisis financiero. En muchas zonas rurales **no hay internet** en la parcela. CRIBA centraliza
toda esa información en el celular, calcula indicadores (utilidad, ROI, rendimiento, salud del
cultivo) y funciona **100% offline**, sincronizando a la nube cuando hay conexión.

---

## 🛠️ Stack técnico

| Área | Tecnología |
|------|-----------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Arquitectura | MVVM + Clean Architecture (Data / Domain / Presentation) |
| Inyección de dependencias | Hilt (Dagger) |
| Base de datos local | Room |
| Red (sincronización) | Retrofit + OkHttp + Gson |
| Tareas en segundo plano | WorkManager |
| Sesión / preferencias | DataStore |
| Mapas y ubicación | Google Maps Compose + FusedLocationProvider |
| Cámara | Intent de cámara + CameraX |
| Imágenes | Coil |

---

## 🏗️ Arquitectura

El proyecto sigue **MVVM + Clean Architecture** con separación estricta en 3 capas. La regla de
dependencias es unidireccional: `presentation → domain ← data`. La capa `domain` no conoce
Android, Room ni Retrofit (Kotlin puro).

```
com.app.criba/
├── data/
│   ├── local/        Room: entidades (entity/), DAOs (dao/), CribaDatabase
│   ├── remote/       ApiService (Retrofit)
│   ├── mapper/       Conversores Entity ⇄ Domain
│   ├── repository/   Implementaciones de los repositorios
│   └── worker/       SyncWorker (WorkManager)
├── domain/
│   ├── model/        Modelos de negocio puros (Terrain, CropCycle, ...)
│   ├── repository/   Interfaces (contratos)
│   └── usecase/      Casos de uso (lógica de negocio)
├── di/               Módulos de Hilt (Database, Network, Repository)
├── presentation/
│   ├── ui/           Pantallas Compose (login, dashboard, parcelas, plagas, ...)
│   ├── viewmodel/    ViewModels (StateFlow)
│   └── state/        Estados de UI (UiState)
└── util/             Helpers (LocationHelper, CameraHelper, GeoUtils, ...)
```

---

## ✅ Requisitos previos

- **Android Studio** (Ladybug o superior).
- **JDK 17**.
- **Android SDK 35** (compileSdk/targetSdk 35, minSdk 26).
- Un **dispositivo o emulador** con Android 8.0 (API 26) o superior.
- Una **API Key de Google Maps** (solo para la función de mapas). Ver instalación.

---

## 🚀 Instalación y ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Daxed124/CRIBA.git
   cd CRIBA
   ```

2. **Configurar el SDK y la API Key** en el archivo `local.properties` (en la raíz; **no se sube a git**):
   ```properties
   sdk.dir=C:\\Users\\TU_USUARIO\\AppData\\Local\\Android\\Sdk
   MAPS_API_KEY=TU_API_KEY_DE_GOOGLE_MAPS
   ```
   > La API Key se inyecta en el `AndroidManifest.xml` mediante `manifestPlaceholders`, por lo
   > que **nunca queda expuesta en el código versionado**.

3. **Abrir en Android Studio** y esperar a que Gradle sincronice las dependencias.

4. **Ejecutar** en un emulador o dispositivo con **Run (Shift + F10)**.

### Compilar el APK por línea de comandos
```bash
./gradlew assembleDebug
# APK generado en: app/build/outputs/apk/debug/app-debug.apk
```

---

## 📱 Funcionalidades principales

- **Autenticación local** (registro / inicio de sesión) con aceptación de términos y condiciones.
- **Parcelas:** alta, edición y baja; medición del área **marcando el contorno en el mapa**
  (cálculo automático de hectáreas y m², con límite de 4 ha).
- **Ciclos de cultivo:** iniciar, monitorear (días transcurridos) y cosechar/cerrar.
- **Plagas:** registro con **foto (cámara/galería)** y **ubicación GPS**; panel de **Salud** con
  score y recomendaciones.
- **Finanzas:** registro de ingresos/gastos y **motor estadístico** (Utilidad Neta, ROI, Erend,
  desglose por categoría).
- **Clima:** registro y **gráficas** (temperatura y precipitación) por ciclo; historial por ciclo.
- **Mapa:** visualización de parcelas con marcadores y polígonos de área.
- **Configuración:** perfil editable (nombre y foto), ayuda (manual) y cerrar sesión.
- **Diseño adaptativo** a teléfonos y tabletas.

---

## 📂 Documentación adicional

- `CRIBA_Manual_Usuario.pdf` / `.docx` — Manual de usuario paso a paso.
- `CONTEXTO.md` — Contexto y decisiones de arquitectura.
- `CRIBA_PROMPTS_DESARROLLO.md` — Guía de desarrollo por fases.

---

## 👥 Equipo

Proyecto desarrollado por el equipo **Cuchurrumin FC** — UniverMilenium, Desarrollo de
Aplicaciones Móviles.

*(Completar aquí los nombres y roles de cada integrante.)*
