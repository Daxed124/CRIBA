# CRIBA — GUÍA DE DESARROLLO ANDROID POR FASES
## Sistema de Trazabilidad Analítica e Inteligencia Agrícola
### Equipo: Cuchurrumin FC | UniverMilenium | Desarrollo Apps Móviles

---

## STACK TÉCNICO GLOBAL

| Capa | Tecnología | Razón |
|------|-----------|-------|
| Lenguaje | **Kotlin** | IA genera Kotlin mejor que Java: menos boilerplate, nulo-seguro, lambdas |
| UI | **Jetpack Compose + Material 3** | Declarativo = IA genera composables completos sin errores de XML |
| Arquitectura | **MVVM + Clean Architecture** | 3 capas claras: Data / Domain / Presentation |
| DI | **Hilt** | Estándar Android, IA lo domina bien |
| DB Local | **Room + KSP** | ORM oficial Android, integra con Flow |
| Networking | **Retrofit2 + OkHttp3 + Gson** | Estándar de industria |
| Async | **Coroutines + StateFlow** | Kotlin nativo, sin callbacks |
| Navegación | **Navigation Compose** | Integra perfecto con Compose |
| Mapas | **Google Maps Compose SDK** | Solo mapas, sin Auth |
| Cámara | **CameraX** | API moderna, reemplaza Camera2 |
| Localización | **FusedLocationProvider** | Más preciso que GPS raw |

**Sin:** Firebase Auth, Google Sign-In, soporte iOS, IoT en tiempo real

---

## PALETA CRIBA (extraída del logo)

```
Primary:     #1B4332   verde oscuro   — borde círculo
Secondary:   #2D6A4F   verde medio    — planta/hojas
Accent:      #C8A84B   dorado         — sol/campo
Earth:       #7B4F2E   marrón         — tierra
NavyBlue:    #1B2B4B   azul marino    — texto "CRIBA"
NetBlue:     #2B6CB0   azul red       — nodos conectividad
Background:  #F8F7F2   crema neutro   — fondo general
Surface:     #FFFFFF   blanco         — cards
```

---

## TABLA ASIGNACIÓN RÁPIDA DE MODELOS

| # | Fase | Modelo | Razón clave |
|---|------|--------|-------------|
| 1 | Setup + Gradle + Arquitectura | **Claude Opus** | Config compleja, Clean Architecture |
| 2 | Room DB — Entidades + DAOs | **Claude Opus** | Esquema relacional, queries SQL exactas |
| 3 | Retrofit + Repository Pattern | **Claude Opus** | Data layer, error handling, mappers |
| 4 | Navegación + App Shell + Tema | **Gemini Pro** | Navigation Compose, Material3, Android SDK |
| 5 | Dashboard Screen | **Gemini Pro** | Compose UI compleja, layouts, cards |
| 6 | Módulo Parcelas + Ciclos CRUD | **Gemini Pro** | Formularios, BottomSheets, DatePicker |
| 7 | Control Plagas + CameraX + GPS | **Gemini Pro** | APIs Google: CameraX, FusedLocation |
| 8 | Finanzas + Motor Estadístico | **Claude Opus** | Fórmulas exactas, UseCases, lógica negocio |
| 9 | Historial Climático + Gráficas | **Gemini Pro** | Charts en Compose, series de tiempo |
| 10 | Google Maps + Georreferencia | **Gemini Pro** | Google Maps SDK — producto nativo Google |

---

---

# FASE 1 — ARQUITECTURA BASE
## 🟣 MODELO: CLAUDE OPUS
> **Por qué Claude:** Configuración de Gradle KTS multi-módulo, Clean Architecture de 3 capas,
> estructura de paquetes y setup de Hilt requieren razonamiento técnico profundo y consistencia
> entre archivos. Claude genera configuraciones complejas sin inventar dependencias inexistentes.

---

### Contexto del Proyecto

Estás iniciando el desarrollo de **CRIBA** (Sistema de Trazabilidad Analítica e Inteligencia Agrícola), una aplicación Android 100% nativa en Kotlin con Jetpack Compose. La app permite a productores agrícolas registrar y analizar datos de parcelas, ciclos de cultivo, plagas, clima y finanzas. Se conecta con un API REST backend (Node.js/PHP + MySQL). **No incluye autenticación de ningún tipo.** Solo se usa la API de Google Maps para georreferencia de parcelas.

**Stack:** Kotlin · Jetpack Compose · Material Design 3 · MVVM + Clean Architecture · Hilt · Room · Retrofit2 · Coroutines/Flow · Navigation Compose · Google Maps Compose SDK · CameraX · FusedLocationProvider

**Paleta de colores CRIBA:**
- Primary: `#1B4332` | Secondary: `#2D6A4F` | Accent: `#C8A84B`
- Earth: `#7B4F2E` | NavyBlue: `#1B2B4B` | Background: `#F8F7F2`

---

### Tareas FASE 1

**1.1 — `build.gradle.kts` (Project level)**
- Kotlin DSL
- Plugins block: `com.android.application`, `org.jetbrains.kotlin.android`, `com.google.dagger.hilt.android`, `com.google.devtools.ksp`
- Versiones centralizadas en `libs.versions.toml` (Version Catalog)

**1.2 — `libs.versions.toml` (Version Catalog)**
Incluir versiones y aliases para:
- Compose BOM (latest stable 2024.x)
- Material3
- Hilt 2.51+
- Room 2.6+
- Retrofit 2.11+, OkHttp 4.12+, Gson
- Navigation Compose
- Google Maps Compose
- CameraX (camera-camera2, camera-lifecycle, camera-view)
- play-services-location
- Coil Compose 2.6+
- Accompanist permissions
- Lifecycle ViewModel Compose
- DataStore Preferences
- Kotlin Coroutines

**1.3 — `build.gradle.kts` (App level)**
Usar aliases del Version Catalog. minSdk 26, targetSdk 34, compileSdk 34. Activar Compose, KSP.

**1.4 — Estructura de paquetes**
Generar árbol de directorios `com.criba.app/` con comentario de responsabilidad por paquete:

```
com.criba.app/
├── data/
│   ├── local/
│   │   ├── entity/          ← Room entities
│   │   ├── dao/             ← Room DAOs
│   │   ├── database/        ← CribaDatabase.kt
│   │   └── converter/       ← TypeConverters
│   ├── remote/
│   │   ├── api/             ← Retrofit interfaces
│   │   └── dto/             ← Data Transfer Objects
│   └── repository/          ← Repository implementations
├── domain/
│   ├── model/               ← Domain models (puros Kotlin)
│   ├── repository/          ← Repository interfaces
│   └── usecase/             ← Business logic UseCases
├── presentation/
│   ├── theme/               ← Material3 theme CRIBA
│   ├── navigation/          ← NavHost, Routes, BottomBar
│   ├── dashboard/           ← Dashboard screen + ViewModel
│   ├── parcelas/            ← Terrenos + Ciclos screens
│   ├── plagas/              ← Control Plagas + Salud screens
│   ├── clima/               ← Historial Climático screens
│   ├── finanzas/            ← Finanzas + KPIs screens
│   └── maps/                ← Google Maps screens
├── di/                      ← Hilt Modules
└── util/                    ← Helpers: Camera, Location, Format
```

**1.5 — `CribaApplication.kt`**
```kotlin
@HiltAndroidApp
class CribaApplication : Application()
```
Registrar en `AndroidManifest.xml`.

**1.6 — `AndroidManifest.xml`**
Permisos requeridos:
- `INTERNET`, `ACCESS_NETWORK_STATE`
- `CAMERA`
- `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`
- `READ_MEDIA_IMAGES` (API 33+) con fallback `READ_EXTERNAL_STORAGE`
- Meta-data Google Maps: `<meta-data android:name="com.google.android.geo.API_KEY" android:value="YOUR_MAPS_API_KEY"/>`

**1.7 — `MainActivity.kt`**
- `@AndroidEntryPoint`
- `enableEdgeToEdge()`
- `setContent { CribaTheme { CribaNavHost() } }`

**Output esperado:** Todos los archivos listos para copiar en Android Studio. Verificar que el proyecto compila sin errores antes de continuar.

---

> ⛔ **DETENER AQUÍ — FASE 1 COMPLETA**
> Verificar compilación limpia en Android Studio.
> **Cambiar a CLAUDE OPUS para FASE 2.**

---

---

# FASE 2 — CAPA DE DATOS: ROOM DATABASE
## 🟣 MODELO: CLAUDE OPUS
> **Por qué Claude:** Diseño de esquema relacional con ForeignKeys, TypeConverters para Enums,
> queries DAO complejas con JOIN, y data classes de relación requieren precisión técnica.
> Claude no inventa nombres de columnas ni tipos incompatibles con Room.

---

### Contexto

App **CRIBA** (Kotlin/Compose/Hilt). Fase 1 completada: proyecto configurado con todas las dependencias en Version Catalog, estructura de paquetes creada, Hilt inicializado. Ahora construir la capa de persistencia local con Room.

El esquema proviene del documento de diseño original (MySQL) adaptado a Room/Android:

**Tablas requeridas:**
- `Terreno` — parcelas físicas con GPS
- `CicloCultivo` — temporadas de cultivo por terreno
- `HistorialClimatico` — registros meteorológicos por ciclo
- `ControlPlagas` — incidencias patógenas con foto y severidad
- `FinanzaAgricola` — transacciones financieras por ciclo

---

### Tareas FASE 2

**2.1 — Enums Kotlin** (`data/local/entity/enums/`)

```kotlin
enum class SeveridadPlaga { BAJO, MEDIO, CRITICO }
enum class TipoTransaccion { GASTO, INGRESO }
enum class EstadoCiclo { ACTIVO, COSECHADO, CANCELADO }
enum class EtapaSequia { NORMAL, LEVE, MODERADO, SEVERO, CRITICO }
enum class TipoSuelo { ARCILLOSO, FRANCO, ARENOSO, LIMOSO, FRANCO_ARCILLOSO }
```

**2.2 — TypeConverters** (`data/local/converter/`)
Converters para: `LocalDate` ↔ `String` (ISO-8601), todos los Enums ↔ `String`.

**2.3 — Entities Room** (`data/local/entity/`)

`TerrenoEntity`:
```
id: Int (PK autoGenerate), nombre: String, hectareas: Double,
tipoSuelo: TipoSuelo, latitud: Double?, longitud: Double?,
coordenadasPoligono: String?, fechaRegistro: LocalDate
```

`CicloCultivoEntity`:
```
id: Int (PK), idTerreno: Int (FK→Terreno CASCADE DELETE),
cultivo: String, fechaSiembra: LocalDate, fechaCosechaEstimada: LocalDate?,
fechaCosechaReal: LocalDate?, estado: EstadoCiclo (default ACTIVO),
volumenCosechadoKg: Double? (para calcular Erend)
```

`HistorialClimaticoEntity`:
```
id: Long (PK), idCiclo: Int (FK→CicloCultivo CASCADE DELETE),
fecha: LocalDate, lluviaMm: Double, temperatura: Double,
etapaSequia: EtapaSequia, notas: String?
```

`ControlPlagasEntity`:
```
id: Int (PK), idCiclo: Int (FK→CicloCultivo CASCADE DELETE),
fecha: LocalDate, tipoPlaga: String, severidad: SeveridadPlaga,
urlFotoLocal: String?, descripcion: String?,
latitud: Double?, longitud: Double?
```

`FinanzaAgricolaEntity`:
```
id: Int (PK), idCiclo: Int (FK→CicloCultivo CASCADE DELETE),
fecha: LocalDate, tipoTransaccion: TipoTransaccion,
monto: Double, categoria: String, descripcion: String?
```

**2.4 — DAOs** (`data/local/dao/`)

Para cada entidad, un DAO con:
- `insert(entity): Long` — `@Insert(onConflict = OnConflictStrategy.REPLACE)`
- `update(entity)` — `@Update`
- `delete(entity)` — `@Delete`
- `getAll(): Flow<List<Entity>>`
- `getById(id): Entity?` — `suspend fun`

Queries adicionales requeridas:

`TerrenoDao`:
```sql
-- Terrenos con su ciclo activo
SELECT * FROM terrenos t LEFT JOIN ciclos_cultivo c
ON t.id = c.id_terreno AND c.estado = 'ACTIVO'

-- Búsqueda por nombre
SELECT * FROM terrenos WHERE nombre LIKE '%' || :query || '%'
```

`CicloCultivoDao`:
```sql
-- Ciclos por terreno ordenados por fecha
SELECT * FROM ciclos_cultivo WHERE id_terreno = :idTerreno
ORDER BY fecha_siembra DESC

-- Ciclo activo de un terreno
SELECT * FROM ciclos_cultivo WHERE id_terreno = :idTerreno
AND estado = 'ACTIVO' LIMIT 1
```

`FinanzaAgricolaDao`:
```sql
-- Total gastos por ciclo
SELECT COALESCE(SUM(monto), 0.0) FROM finanzas_agricolas
WHERE id_ciclo = :idCiclo AND tipo_transaccion = 'GASTO'

-- Total ingresos por ciclo
SELECT COALESCE(SUM(monto), 0.0) FROM finanzas_agricolas
WHERE id_ciclo = :idCiclo AND tipo_transaccion = 'INGRESO'

-- Gastos agrupados por categoria
SELECT categoria, SUM(monto) as total FROM finanzas_agricolas
WHERE id_ciclo = :idCiclo AND tipo_transaccion = 'GASTO'
GROUP BY categoria
```

`ControlPlagasDao`:
```sql
-- Conteo por severidad en un ciclo
SELECT severidad, COUNT(*) as total FROM control_plagas
WHERE id_ciclo = :idCiclo GROUP BY severidad
```

**2.5 — Relation data classes** (`data/local/entity/`)

```kotlin
data class TerrenoConCiclos(
    @Embedded val terreno: TerrenoEntity,
    @Relation(parentColumn = "id", entityColumn = "id_terreno")
    val ciclos: List<CicloCultivoEntity>
)

data class CicloCompleto(
    @Embedded val ciclo: CicloCultivoEntity,
    @Relation(parentColumn = "id", entityColumn = "id_ciclo")
    val plagas: List<ControlPlagasEntity>,
    @Relation(parentColumn = "id", entityColumn = "id_ciclo")
    val finanzas: List<FinanzaAgricolaEntity>,
    @Relation(parentColumn = "id", entityColumn = "id_ciclo")
    val historialClimatico: List<HistorialClimaticoEntity>
)
```

**2.6 — `CribaDatabase.kt`** (`data/local/database/`)
- `@Database(entities = [...], version = 1)`
- `addTypeConverter(Converters::class)`
- `fallbackToDestructiveMigration()` para desarrollo
- Singleton con `companion object` e inicialización con `synchronized`
- Prepopulate: insertar 1 terreno de prueba en `createFromAsset` o `addCallback`

**2.7 — `DatabaseModule.kt`** (`di/`)
Hilt module `@InstallIn(SingletonComponent::class)`:
- Provee `CribaDatabase` como `@Singleton`
- Provee cada DAO individualmente

**Output esperado:** Archivos en `data/local/`. Compilación sin errores, Room genera código KSP correctamente.

---

> ⛔ **DETENER AQUÍ — FASE 2 COMPLETA**
> Build exitoso. Room genera tablas correctamente (verificar con DB Inspector).
> **Cambiar a CLAUDE OPUS para FASE 3.**

---

---

# FASE 3 — CAPA REMOTA: RETROFIT + REPOSITORY PATTERN
## 🟣 MODELO: CLAUDE OPUS
> **Por qué Claude:** Diseño de contratos Repository, manejo de errores con sealed classes,
> mapeo DTO↔Domain↔Entity y estrategia offline-first requieren razonamiento arquitectónico.
> Claude mantiene consistencia entre interfaces y sus implementaciones sin perder capas.

---

### Contexto

App **CRIBA** (Kotlin/Compose/Hilt). Fases 1-2 completadas: proyecto + Room con 5 entidades. Construir la capa remota (Retrofit) y el patrón Repository que unifica datos locales y remotos.

**URL base API:** `http://10.0.2.2:3000/api/v1/` (emulador) / `https://api.criba.app/v1/` (producción)
Estrategia: **Offline-First** — Room como fuente de verdad, API como sincronización.

---

### Tareas FASE 3

**3.1 — `Resource<T>` sealed class** (`util/`)

```kotlin
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<Nothing>(
        val message: String,
        val code: Int? = null,
        val throwable: Throwable? = null
    ) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
```

**3.2 — DTOs** (`data/remote/dto/`)

Crear DTO por entidad que mapee 1:1 con el JSON del API REST. Usar `@SerializedName` de Gson.

Wrapper genérico de respuesta:
```kotlin
data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: T?,
    @SerializedName("message") val message: String?,
    @SerializedName("errors") val errors: List<String>?
)
```

DTOs requeridos: `TerrenoDto`, `CicloCultivoDto`, `HistorialClimaticoDto`,
`ControlPlagasDto`, `FinanzaAgricolaDto`, `ResumenEstadisticasDto` (para endpoint dashboard).

**3.3 — Mappers** (`data/remote/dto/` y `data/local/entity/`)

Extension functions:
```kotlin
// DTO → Entity
fun TerrenoDto.toEntity(): TerrenoEntity
// Entity → Domain Model
fun TerrenoEntity.toDomain(): Terreno
// Domain Model → Entity (para inserts)
fun Terreno.toEntity(): TerrenoEntity
```
Crear para cada tipo. **Domain models** van en `domain/model/` — son data classes Kotlin puras, sin anotaciones Room ni Gson.

**3.4 — `CribaApiService.kt`** (`data/remote/api/`)
Interface Retrofit con todos los endpoints:

```kotlin
// TERRENOS
@GET("terrenos") suspend fun getTerrenos(): ApiResponse<List<TerrenoDto>>
@POST("terrenos") suspend fun createTerreno(@Body terreno: TerrenoDto): ApiResponse<TerrenoDto>
@PUT("terrenos/{id}") suspend fun updateTerreno(@Path("id") id: Int, @Body terreno: TerrenoDto): ApiResponse<TerrenoDto>
@DELETE("terrenos/{id}") suspend fun deleteTerreno(@Path("id") id: Int): ApiResponse<Unit>

// CICLOS
@GET("ciclos") suspend fun getCiclos(@Query("idTerreno") idTerreno: Int): ApiResponse<List<CicloCultivoDto>>
@POST("ciclos") suspend fun createCiclo(@Body ciclo: CicloCultivoDto): ApiResponse<CicloCultivoDto>
@PUT("ciclos/{id}") suspend fun updateCiclo(@Path("id") id: Int, @Body ciclo: CicloCultivoDto): ApiResponse<CicloCultivoDto>

// PLAGAS (con imagen Multipart)
@GET("plagas") suspend fun getPlagas(@Query("idCiclo") idCiclo: Int): ApiResponse<List<ControlPlagasDto>>
@Multipart
@POST("plagas")
suspend fun createPlaga(
    @Part("data") plaga: ControlPlagasDto,
    @Part foto: MultipartBody.Part?
): ApiResponse<ControlPlagasDto>
@DELETE("plagas/{id}") suspend fun deletePlaga(@Path("id") id: Int): ApiResponse<Unit>

// CLIMA
@GET("clima") suspend fun getClima(@Query("idCiclo") idCiclo: Int): ApiResponse<List<HistorialClimaticoDto>>
@POST("clima") suspend fun createClima(@Body clima: HistorialClimaticoDto): ApiResponse<HistorialClimaticoDto>

// FINANZAS
@GET("finanzas") suspend fun getFinanzas(@Query("idCiclo") idCiclo: Int): ApiResponse<List<FinanzaAgricolaDto>>
@POST("finanzas") suspend fun createFinanza(@Body finanza: FinanzaAgricolaDto): ApiResponse<FinanzaAgricolaDto>
@DELETE("finanzas/{id}") suspend fun deleteFinanza(@Path("id") id: Int): ApiResponse<Unit>

// DASHBOARD — estadísticas calculadas en backend
@GET("dashboard/stats")
suspend fun getEstadisticas(@Query("idCiclo") idCiclo: Int): ApiResponse<ResumenEstadisticasDto>
```

**3.5 — `NetworkModule.kt`** (`di/`)

Hilt module `@InstallIn(SingletonComponent::class)`:
- `OkHttpClient` con `HttpLoggingInterceptor` (solo en DEBUG), timeout 30s
- `Gson` con `GsonConverterFactory`
- `Retrofit` como `@Singleton`
- Provee `CribaApiService`

**3.6 — Repository Interfaces** (`domain/repository/`)

```kotlin
interface TerrenoRepository {
    fun getTerrenosConCiclos(): Flow<Resource<List<TerrenoConCiclos>>>
    suspend fun getTerrenoById(id: Int): Resource<Terreno>
    suspend fun createTerreno(terreno: Terreno): Resource<Terreno>
    suspend fun updateTerreno(terreno: Terreno): Resource<Terreno>
    suspend fun deleteTerreno(id: Int): Resource<Unit>
    suspend fun syncWithServer(): Resource<Unit>
}
// Crear interfaces similares para: CicloRepository, PlagasRepository,
// ClimaRepository, FinanzasRepository
```

**3.7 — Repository Implementations** (`data/repository/`)

Para cada repositorio, implementar estrategia **offline-first**:
1. Emitir `Resource.Loading`
2. Servir datos desde Room inmediatamente (caché)
3. Intentar llamada a API en background
4. Si API responde: actualizar Room → Room emite nuevo valor via Flow
5. Si API falla: emitir `Resource.Error` pero mantener datos de Room visibles

```kotlin
class TerrenoRepositoryImpl @Inject constructor(
    private val dao: TerrenoDao,
    private val api: CribaApiService
) : TerrenoRepository {
    override fun getTerrenosConCiclos(): Flow<Resource<List<TerrenoConCiclos>>> = flow {
        emit(Resource.Loading)
        // 1. Emitir datos locales
        dao.getTerrenosConCiclos().collect { localData ->
            emit(Resource.Success(localData.map { it.toDomain() }))
        }
    }.catch { e -> emit(Resource.Error(e.message ?: "Error desconocido")) }
    // Implementar todas las funciones CRUD con try-catch y sincronización
}
```

**3.8 — `RepositoryModule.kt`** (`di/`)
Bind interfaces → implementaciones con `@Binds` y `@Singleton`.

**Output esperado:** Archivos en `data/remote/`, `data/repository/`, `domain/model/`, `domain/repository/`, `di/`. Compilación sin errores.

---

> ⛔ **DETENER AQUÍ — FASE 3 COMPLETA**
> Verificar que Hilt resuelve todas las dependencias (build exitoso).
> **Cambiar a GEMINI PRO para FASE 4.**

---

---

# FASE 4 — NAVEGACIÓN + APP SHELL + TEMA CRIBA
## 🟢 MODELO: GEMINI PRO
> **Por qué Gemini:** Navigation Compose, Material Design 3, BottomBar, TopAppBar
> y theming son APIs de Google. Gemini conoce las versiones actuales de estas APIs
> mejor que ningún otro modelo. Evita deprecados de Compose antiguo.

---

### Contexto

App **CRIBA** (Kotlin/Compose/Hilt). Fases 1-3 completadas: arquitectura limpia + Room + Retrofit. Construir el sistema de navegación, tema visual y shell principal de la app.

**Referencia visual:** Dashboard limpio estilo AgroMonitor — fondo crema (#F8F7F2), cards con sombra suave y bordes redondeados 12dp, BottomBar oscuro con indicador dorado en item activo, TopBar con logo y estado del sistema.

**Paleta:** Primary `#1B4332` | Secondary `#2D6A4F` | Accent `#C8A84B` | NavyBlue `#1B2B4B` | Background `#F8F7F2`

---

### Tareas FASE 4

**4.1 — `CribaTheme.kt`** (`presentation/theme/`)

Material3 theme completo:
```kotlin
private val CribaColorScheme = lightColorScheme(
    primary = Color(0xFF1B4332),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF2D6A4F),
    secondary = Color(0xFFC8A84B),
    onSecondary = Color(0xFF1B2B4B),
    background = Color(0xFFF8F7F2),
    surface = Color.White,
    // ... completar todos los slots Material3
)
```

Typography con fuentes del sistema, jerarquía: `displayMedium`, `headlineMedium`, `titleLarge`, `titleMedium`, `bodyLarge`, `bodyMedium`, `labelLarge`, `labelMedium`.

Shapes: `extraSmall=4dp`, `small=8dp`, `medium=12dp`, `large=16dp`, `extraLarge=24dp`.

**4.2 — Rutas de navegación** (`presentation/navigation/`)

```kotlin
sealed class Screen(val route: String) {
    // Bottom Nav (4 tabs principales)
    object Dashboard : Screen("dashboard")
    object Parcelas : Screen("parcelas")
    object Historial : Screen("historial")
    object Salud : Screen("salud")
    // Sub-pantallas
    object DetalleParcela : Screen("parcela/{terrenoId}") {
        fun createRoute(terrenoId: Int) = "parcela/$terrenoId"
    }
    object NuevoCiclo : Screen("ciclo/nuevo/{terrenoId}") {
        fun createRoute(terrenoId: Int) = "ciclo/nuevo/$terrenoId"
    }
    object RegistrarPlaga : Screen("plaga/nueva/{cicloId}") {
        fun createRoute(cicloId: Int) = "plaga/nueva/$cicloId"
    }
    object RegistrarClima : Screen("clima/nuevo/{cicloId}") {
        fun createRoute(cicloId: Int) = "clima/nuevo/$cicloId"
    }
    object RegistrarFinanza : Screen("finanza/nueva/{cicloId}") {
        fun createRoute(cicloId: Int) = "finanza/nueva/$cicloId"
    }
    object MapaTerreno : Screen("mapa/{terrenoId}") {
        fun createRoute(terrenoId: Int) = "mapa/$terrenoId"
    }
    object SeleccionarUbicacion : Screen("ubicacion/seleccionar")
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
```

**4.3 — `CribaBottomBar.kt`** (`presentation/navigation/`)

NavigationBar Material3:
- `containerColor = Color(0xFF1B4332)`
- Item seleccionado: `indicatorColor = Color(0xFFC8A84B)`, icono y label `Color.White`
- Item no seleccionado: icono y label `Color(0xFF2D6A4F).copy(alpha = 0.6f)`
- 4 items: Dashboard (Home icon), Parcelas (Landscape), Historial (History), Salud (HealthAndSafety)
- `alwaysShowLabel = true`

**4.4 — `CribaTopBar.kt`** (`presentation/navigation/`)

Material3 `TopAppBar`:
- `containerColor = Color(0xFF1B4332)`
- Left: `Image` logo CRIBA pequeño (placeholder `painterResource`) + `Text` título pantalla actual
- Right: Row con `●` verde "Sistema Activo" + `FilterChip` con nombre parcela activa (fondo semi-transparente)
- `navigationIcon`: visible solo en sub-pantallas (BackIcon blanco)

**4.5 — `CribaNavHost.kt`** (`presentation/navigation/`)

NavHost completo con `composable()` por cada Screen. En sub-screens con argumentos usar `navArgument("id") { type = NavType.IntType }`. Pasar ViewModels con `hiltViewModel()`.

**4.6 — `MainScaffold.kt`** (`presentation/navigation/`)

Scaffold con:
- `topBar = { CribaTopBar(currentRoute, navController) }`
- `bottomBar = { if (showBottomBar) CribaBottomBar(navController) }` (ocultar en sub-pantallas)
- `content = { padding -> CribaNavHost(navController, Modifier.padding(padding)) }`

**Output esperado:** App lanza con BottomBar funcional navegando entre las 4 pantallas stub. TopBar muestra título correcto por pantalla.

---

> ⛔ **DETENER AQUÍ — FASE 4 COMPLETA**
> Verificar navegación entre tabs y animaciones de transición en dispositivo/emulador.
> **Cambiar a GEMINI PRO para FASE 5.**

---

---

# FASE 5 — PANTALLA DASHBOARD
## 🟢 MODELO: GEMINI PRO
> **Por qué Gemini:** Layout complejo con múltiples cards Compose, LazyColumn anidado,
> StateFlow → Compose state, y Material3 Cards requieren conocimiento profundo
> de APIs de Compose actuales. Gemini genera código Compose sin deprecated APIs.

---

### Contexto

App **CRIBA**. Fases 1-4 completadas. Construir la pantalla Dashboard — pantalla principal de la app.

**Referencia visual (AgroMonitor Pro adaptado):**
- Fondo `#F8F7F2`, cards blancas con sombra `elevation=2dp`, `shape=12dp`
- Columna única en teléfono con `LazyColumn`, padding 16dp entre cards
- Card clima: ícono sol + temperatura grande + detalles
- Card parcela activa: chip cultivo verde + días desde siembra bold + próximo riego dorado
- Card sugerencia IA: borde izquierdo 4dp dorado `#C8A84B`, fondo `#FFF8E7`
- Card mapa miniatura: fondo verde suave `#D4EDDA`, dots sensores
- Card historial: tabla con 3 filas: época | cultivo | estado (chip) | rendimiento

---

### Tareas FASE 5

**5.1 — Domain Models requeridos** (`domain/model/`)

```kotlin
data class ResumenDashboard(
    val terreno: Terreno?,
    val cicloActivo: CicloCultivo?,
    val diasDesdeSiembra: Int,
    val totalGastos: Double,
    val totalIngresos: Double,
    val utilidadNeta: Double,
    val roi: Double,
    val ultimosCiclos: List<CicloCultivo>,
    val plagasActivas: Int,
    val alertasSeveridadCritica: Int
)
```

**5.2 — `DashboardViewModel.kt`** (`presentation/dashboard/`)

```kotlin
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val terrenoRepo: TerrenoRepository,
    private val finanzasRepo: FinanzasRepository,
    private val plagasRepo: PlagasRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    // cargarDashboard() en init{} con viewModelScope.launch
    // calcularUtilidad(): Double = totalIngresos - totalGastos
    // calcularROI(): Double = if(totalGastos > 0) (utilidad/totalGastos)*100 else 0.0
    // generarSugerenciaIA(): String — lógica simple basada en alertas
}

data class DashboardUiState(
    val terreno: Terreno? = null,
    val cicloActivo: CicloCultivo? = null,
    val diasDesdeSiembra: Int = 0,
    val totalGastos: Double = 0.0,
    val totalIngresos: Double = 0.0,
    val utilidadNeta: Double = 0.0,
    val roi: Double = 0.0,
    val ultimosCiclos: List<CicloCultivo> = emptyList(),
    val plagasActivas: Int = 0,
    val sugerenciaIA: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)
```

**5.3 — Composables individuales** (`presentation/dashboard/components/`)

`WeatherCard(temperatura: Double, descripcion: String, humedad: Int, viento: Int)`:
- Icono sol animado (rotación infinita suave con `rememberInfiniteTransition`)
- Temperatura como `Text(style = MaterialTheme.typography.displayMedium)`
- Row: humedad | viento con iconos pequeños

`ParcelaResumenCard(ciclo: CicloCultivo?, diasSiembra: Int, onVerDetalle: () -> Unit)`:
- `AssistChip` verde con nombre cultivo
- Días como número `displaySmall` bold
- Próximo riego en `labelLarge` color `#C8A84B`
- `Button("Ver Detalle")` estilo outlined

`SugerenciaIACard(texto: String)`:
- `Row` con indicador vertical `Box(width=4dp, color=Accent)` a la izquierda
- Fondo `Color(0xFFFFF8E7)`
- Icono `Icons.Rounded.Lightbulb` color Accent
- `Text` cuerpo sugerencia

`MapaMiniaturaCard(nombreSector: String, onVerMapa: () -> Unit)`:
- `Box` con fondo `Color(0xFFD4EDDA)`, height 160dp
- `Text` sector centrado con estilo `labelLarge`
- Dos `Box` circulares verdes oscuros simulando sensores (posición offset)
- `FloatingActionButton` "+" esquina inferior derecha color Primary

`HistorialPlantacionesCard(ciclos: List<CicloCultivo>)`:
- Header: `Text("HISTORIAL DE PLANTACIONES")` + `Badge` "Últimos 3 ciclos"
- `LazyColumn` con máximo 3 items
- Por fila: `Row` con Época | Cultivo | `FilterChip` estado (verde/gris/rojo) | rendimiento t/ha o "--"

**5.4 — `DashboardScreen.kt`** (`presentation/dashboard/`)

```kotlin
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToParcela: (Int) -> Unit,
    onNavigateToMapa: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) { /* CribaLoadingIndicator */ }
    else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color(0xFFF8F7F2)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { WeatherCard(...) }
            item { ParcelaResumenCard(...) }
            item { SugerenciaIACard(uiState.sugerenciaIA) }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MapaMiniaturaCard(Modifier.weight(1f), ...)
                    // KPI mini card ROI al lado
                }
            }
            item { HistorialPlantacionesCard(uiState.ultimosCiclos) }
        }
    }
}
```

**Output esperado:** Dashboard visual completo con datos del ViewModel. Hacer @Preview con datos mock.

---

> ⛔ **DETENER AQUÍ — FASE 5 COMPLETA**
> Revisar Preview en Android Studio. Confirmar que StateFlow conecta con UI.
> **Cambiar a GEMINI PRO para FASE 6.**

---

---

# FASE 6 — MÓDULO PARCELAS + CICLOS CRUD
## 🟢 MODELO: GEMINI PRO
> **Por qué Gemini:** ModalBottomSheet, DatePickerDialog Material3, ExposedDropdownMenuBox,
> swipe-to-delete y LazyColumn son componentes Android/Compose. Gemini conoce
> las APIs actuales y evita los BottomSheet deprecated de versiones anteriores.

---

### Contexto

App **CRIBA**. Fases 1-5 completadas. Construir gestión completa de Terrenos (parcelas) y sus Ciclos de cultivo.

---

### Tareas FASE 6

**6.1 — `ParcelasViewModel.kt`** (`presentation/parcelas/`)

StateFlow con `List<TerrenoConCiclos>`. Funciones:
- `cargarTerrenos()` — Flow desde TerrenoRepository
- `agregarTerreno(terreno: Terreno)` — con validación
- `eliminarTerreno(id: Int)` — con confirmación
- `iniciarCiclo(ciclo: CicloCultivo)` — valida que no haya ciclo ACTIVO en ese terreno
- `cerrarCiclo(idCiclo: Int, volumenKg: Double?)` — cambia estado a COSECHADO

**6.2 — `ParcelasScreen.kt`** (`presentation/parcelas/`)

LazyColumn de terrenos:
- `TerrenoCard` por item: nombre bold, hectáreas, chip cultivo activo (o "Sin cultivo" gris)
- Swipe-to-delete con `SwipeToDismissBox` y confirmación `AlertDialog`
- Botón "+" en cada card para iniciar nuevo ciclo
- `FloatingActionButton` principal "+" para nuevo terreno
- Estado vacío: `Column` centrado con ícono agricultura + "Sin parcelas registradas" + botón "Agregar primera parcela"

**6.3 — `NuevoTerrenoSheet.kt`** (ModalBottomSheet)

Campos con validación inline (`isError = true` cuando campo inválido):
- `OutlinedTextField` Nombre del terreno (no vacío)
- `OutlinedTextField` Hectáreas (Double > 0, teclado decimal)
- `ExposedDropdownMenuBox` Tipo de suelo: Arcilloso | Franco | Arenoso | Limoso | Franco-Arcilloso
- Sección GPS: `Row` con botón "📍 Obtener Ubicación" + texto lat/lng o "Sin coordenadas"
- `Button("Guardar Parcela")` habilitado solo si nombre y hectáreas válidos

**6.4 — `DetalleParcela.kt`** (`presentation/parcelas/`)

`Scaffold` con `TopAppBar` (BackButton + nombre terreno):
- Header card: nombre, hectáreas, tipo suelo, coordenadas
- `TabRow` con 3 tabs: "Ciclo Actual" | "Historial" | "Mapa"

Tab **Ciclo Actual**:
- Si no hay ciclo: card "Sin ciclo activo" + botón "Iniciar Ciclo"
- Si hay ciclo: cultivo nombre, fecha siembra, días transcurridos (ProgressIndicator lineal), estado chip
- Botones acceso rápido en `Row`: "🌿 Plagas" | "🌧 Clima" | "💰 Finanzas"

Tab **Historial**:
- LazyColumn de ciclos anteriores con rendimiento (t/ha), fechas, estado chip

Tab **Mapa**:
- Componente `MapaTerrenoMini` (placeholder "Disponible en Fase 10")

**6.5 — `NuevoCicloSheet.kt`** (ModalBottomSheet)

- `OutlinedTextField` Cultivo (texto libre, ej: "Maíz Blanco", "Tomate Saladette")
- `DatePickerDialog` Material3 para Fecha Siembra
- `DatePickerDialog` Material3 para Fecha Cosecha Estimada (debe ser > siembra)
- Validación: mostrar error si fechas inválidas
- `Button("Iniciar Ciclo")`

**Output esperado:** Archivos en `presentation/parcelas/`. Flujo: listar → agregar → detalle → nuevo ciclo, funcional end-to-end con Room.

---

> ⛔ **DETENER AQUÍ — FASE 6 COMPLETA**
> Probar flujo completo CRUD en emulador con datos persistidos en Room.
> **Cambiar a GEMINI PRO para FASE 7.**

---

---

# FASE 7 — MÓDULO CONTROL DE PLAGAS (CAMERAХ + GPS)
## 🟢 MODELO: GEMINI PRO
> **Por qué Gemini:** CameraX, FusedLocationProviderClient, PhotoPicker de Android 13+,
> Accompanist Permissions y manejo de Uri de archivos son APIs de Google/Android.
> Gemini conoce los patrones actuales de permisos runtime y CameraX Lifecycle.

---

### Contexto

App **CRIBA**. Fases 1-6 completadas. Módulo crítico: registro de incidencias de plagas con captura de foto de evidencia y coordenadas GPS del punto de detección.

---

### Tareas FASE 7

**7.1 — `LocationHelper.kt`** (`util/`)

```kotlin
class LocationHelper @Inject constructor(@ApplicationContext private val context: Context) {
    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    suspend fun getCurrentLocation(): Pair<Double, Double>? // lat, lng
    suspend fun getLastKnownLocation(): Pair<Double, Double>?
}
```
Manejo de: permiso denegado, location null, timeout con `withTimeoutOrNull(5000)`.

**7.2 — `CameraHelper.kt`** (`util/`)

```kotlin
class CameraHelper @Inject constructor(@ApplicationContext private val context: Context) {
    fun createImageFile(): File  // en getExternalFilesDir(DIRECTORY_PICTURES)
    fun getUriForFile(file: File): Uri  // usando FileProvider
}
```
FileProvider configurado en `AndroidManifest.xml` y `res/xml/file_paths.xml`.

**7.3 — `PlagasViewModel.kt`** (`presentation/plagas/`)

StateFlow por ciclo activo. Funciones:
- `cargarPlagas(idCiclo: Int)`
- `registrarPlaga(plaga: ControlPlagas, fotoUri: Uri?)` — guarda Uri local, sube foto al API async
- `eliminarPlaga(id: Int)`
- `obtenerUbicacionActual()` — llama LocationHelper, actualiza state
- `calcularScoreSalud(plagas: List<ControlPlagas>): Int` — 100 - (Crítico×30 + Medio×10 + Bajo×3), mínimo 0

**7.4 — `RegistrarPlagaScreen.kt`** (`presentation/plagas/`)

Formulario completo en `LazyColumn`:

*Sección Tipo y Severidad:*
- `OutlinedTextField` + `ExposedDropdownMenu` sugerencias: Pulgón | Trips | Mosca Blanca | Roya | Fusarium | Tizón | Araña Roja | Nematodos | Otro
- Selector severidad — 3 `FilterChip` en Row:
  - 🟢 `BAJO` (borde verde `#2D6A4F`)
  - 🟡 `MEDIO` (borde naranja `#E87C1B`)
  - 🔴 `CRÍTICO` (borde rojo `#C62828`)
- `DatePickerDialog` fecha detección (default hoy)

*Sección Foto Evidencia:*
- Si no hay foto: `OutlinedCard` punteado con ícono cámara + dos botones Row: "📷 Tomar Foto" | "🖼 Galería"
- Si hay foto: `AsyncImage(Coil)` preview + botón "Cambiar Foto"
- `rememberLauncherForActivityResult(TakePicture)` para cámara
- `rememberLauncherForActivityResult(PickVisualMedia)` para galería (Android 13+ PhotoPicker)

*Sección GPS:*
- `OutlinedCard` con botón "📍 Detectar Ubicación"
- `LinearProgressIndicator` mientras carga
- Texto coordenadas si obtenidas: "Lat: 19.4326° | Lng: -99.1332°"

*Botón guardar:*
- `Button("Registrar Plaga")` — habilitado solo si `tipoPlaga.isNotBlank() && severidadSeleccionada != null`

**7.5 — `PlagasListScreen.kt`** (`presentation/plagas/`)

`LazyColumn` por ciclo:
- `PlagaCard`: thumbnail foto (AsyncImage Coil, placeholder insecto icon), nombre plaga, `AssistChip` severidad, fecha
- Chip color: BAJO=verde, MEDIO=naranja, CRÍTICO=rojo oscuro
- Swipe-to-delete
- Header con resumen: total registros + distribución SegmentedProgressBar

**7.6 — `SaludScreen.kt`** (tab "Salud" del BottomNav) (`presentation/plagas/`)

Vista consolidada:

*Score de Salud:*
- Círculo grande con `Canvas` — arco de progreso color gradiente verde→rojo según score
- Número score `displayLarge` centrado
- Label "SALUD DEL CULTIVO" debajo

*Distribución de Plagas:*
- 3 `LinearProgressIndicator` coloreadas (Bajo/Medio/Crítico) con conteo
- Texto explicativo del nivel

*Recomendaciones automáticas:*
- `LazyColumn` de cards con recomendaciones basadas en plagas registradas:
  - CRÍTICO detectado → "⚠️ Aplicar control inmediato. Contactar agrónomo."
  - MEDIO detectado → "🔎 Monitorear cada 48h. Evaluar fungicida."
  - Sin plagas → "✅ Cultivo saludable. Continuar monitoreo regular."

**Output esperado:** Archivos en `presentation/plagas/`, `util/CameraHelper.kt`, `util/LocationHelper.kt`. Probar en dispositivo físico — emulador no tiene cámara real.

---

> ⛔ **DETENER AQUÍ — FASE 7 COMPLETA**
> Prueba obligatoria en dispositivo físico (cámara + GPS).
> Verificar que fotos se guardan y cargan correctamente con Coil.
> **Cambiar a CLAUDE OPUS para FASE 8.**

---

---

# FASE 8 — MÓDULO FINANZAS + MOTOR ESTADÍSTICO
## 🟣 MODELO: CLAUDE OPUS
> **Por qué Claude:** Las fórmulas financieras exactas (ROI, Utilidad Neta, Erend),
> el patrón UseCase de Domain Layer y el manejo preciso de Double para dinero
> requieren razonamiento matemático riguroso. Claude no redondea mal ni
> divide por cero. También maneja mejor la lógica de negocio compleja.

---

### Contexto

App **CRIBA**. Fases 1-7 completadas. Módulo central de negocio: registro de transacciones financieras y cálculo de indicadores analíticos.

**Fórmulas del documento de diseño (implementar exactamente):**

```
Utilidad Neta (Uneta) = Σ(Ingresos) − Σ(Gastos Operativos)

ROI Agrícola (%) = (Uneta / Σ(Gastos Operativos)) × 100
    → Si Gastos = 0: ROI = 0.0 (no dividir por cero)

Eficiencia Rendimiento (Erend) = Volumen Cosechado (Kg) / Superficie (Hectáreas)
    → Solo calculable si ciclo tiene volumenCosechadoKg y terreno tiene hectáreas
```

**Categorías de egresos válidas:**
`Semillas` | `Fertilizantes` | `Insumos` | `Mano de Obra` | `Combustible` | `Maquinaria` | `Otros`

---

### Tareas FASE 8

**8.1 — Domain Models** (`domain/model/`)

```kotlin
data class ResumenFinanciero(
    val idCiclo: Int,
    val totalGastos: Double,
    val totalIngresos: Double,
    val utilidadNeta: Double,        // puede ser negativa
    val roi: Double,                  // puede ser negativa
    val esRentable: Boolean,          // utilidadNeta > 0
    val gastosPorCategoria: Map<String, Double>,
    val eficienciaRendimiento: Double?, // null si no hay datos de cosecha
    val porcentajeGastoPorCategoria: Map<String, Double> // categoria → % del total
)
```

**8.2 — UseCases** (`domain/usecase/`)

`CalcularUtilidadNetaUseCase`:
```kotlin
class CalcularUtilidadNetaUseCase @Inject constructor(
    private val dao: FinanzaAgricolaDao
) {
    suspend operator fun invoke(idCiclo: Int): Double {
        val ingresos = dao.getTotalIngresosByCiclo(idCiclo)
        val gastos = dao.getTotalGastosByCiclo(idCiclo)
        return ingresos - gastos
    }
}
```

`CalcularROIUseCase`:
```kotlin
operator fun invoke(utilidadNeta: Double, totalGastos: Double): Double =
    if (totalGastos > 0.0) (utilidadNeta / totalGastos) * 100.0 else 0.0
```

`CalcularEficienciaRendimientoUseCase`:
```kotlin
operator fun invoke(volumenKg: Double?, hectareas: Double?): Double? =
    if (volumenKg != null && hectareas != null && hectareas > 0)
        volumenKg / hectareas
    else null
```

`GenerarResumenFinancieroUseCase`:
```kotlin
class GenerarResumenFinancieroUseCase @Inject constructor(
    private val dao: FinanzaAgricolaDao,
    private val calcularUtilidad: CalcularUtilidadNetaUseCase,
    private val calcularROI: CalcularROIUseCase,
    private val calcularErend: CalcularEficienciaRendimientoUseCase
) {
    suspend operator fun invoke(
        idCiclo: Int,
        volumenKg: Double? = null,
        hectareas: Double? = null
    ): ResumenFinanciero
    // Implementar con todos los campos del data class
}
```

**8.3 — `FinanzasViewModel.kt`** (`presentation/finanzas/`)

```kotlin
@HiltViewModel
class FinanzasViewModel @Inject constructor(
    private val finanzasRepo: FinanzasRepository,
    private val generarResumen: GenerarResumenFinancieroUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FinanzasUiState())
    val uiState: StateFlow<FinanzasUiState> = _uiState.asStateFlow()

    fun cargarFinanzas(idCiclo: Int)
    fun registrarGasto(monto: Double, categoria: String, descripcion: String, fecha: LocalDate)
    fun registrarIngreso(monto: Double, descripcion: String, fecha: LocalDate, volumenKg: Double?)
    fun eliminarTransaccion(id: Int)
    fun formatearMoneda(monto: Double): String  // "$ 12,345.67"
    fun formatearROI(roi: Double): String        // "+34.5%" o "-12.3%"
}

data class FinanzasUiState(
    val transacciones: List<FinanzaAgricola> = emptyList(),
    val resumen: ResumenFinanciero? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
```

**8.4 — `RegistrarTransaccionSheet.kt`** (ModalBottomSheet) (`presentation/finanzas/`)

Toggle tipo al inicio: `SegmentedButton` Material3 con "💸 GASTO" | "💰 INGRESO"

**Si GASTO:**
- `ExposedDropdownMenuBox` Categoría con las 7 categorías válidas
- `OutlinedTextField` Monto con `prefix { Text("$") }` y `keyboardOptions = KeyboardType.Decimal`
- `DatePickerDialog` fecha (default hoy)
- `OutlinedTextField` Descripción (opcional)

**Si INGRESO:**
- `OutlinedTextField` Descripción (ej: "Venta maíz temporada")
- `OutlinedTextField` Volumen vendido en Kg (opcional, para Erend)
- `OutlinedTextField` Precio por Kg → calcula monto automáticamente con `derivedStateOf`
- `OutlinedTextField` Monto total (editable, se auto-llena si precio×volumen dados)

Botón `Button("Registrar ${if(esGasto) "Gasto" else "Ingreso"}")` — habilitado si monto > 0.

**8.5 — `FinanzasScreen.kt`** (`presentation/finanzas/`)

*Row KPIs (horizontalScroll):*
- `KpiCard("Utilidad Neta", resumen.utilidadNeta, colorPositivo/negativo, "$ %,.2f")`
- `KpiCard("ROI", resumen.roi, colorPositivo/negativo, "%.1f%%")`
- `KpiCard("Total Gastos", resumen.totalGastos, colorRojo, "$ %,.2f")`
- `KpiCard("Total Ingresos", resumen.totalIngresos, colorVerde, "$ %,.2f")`
- `KpiCard("Erend", resumen.eficienciaRendimiento, colorPrimary, "%.1f t/ha")` (si no null)

*Desglose de Gastos por Categoría:*
- `Text("DESGLOSE DE GASTOS")` bold
- Por cada categoría con monto > 0: `LinearProgressIndicator` + `Text("Semillas: $1,200 (23%)")`
- Colores distintos por categoría (asignar lista de colores fijos)

*Lista Transacciones (LazyColumn):*
- Agrupadas por mes: header sticky `Text("Junio 2025", style=labelLarge)`
- Por transacción: `TransaccionRow` con fecha | descripción | `AssistChip` categoría | monto rojo/verde
- `SwipeToDismissBox` para eliminar con `AlertDialog` confirmación

`FloatingActionButton` "+" lanza `RegistrarTransaccionSheet`.

**Output esperado:** Archivos en `domain/usecase/`, `presentation/finanzas/`. Probar cálculos con datos mock verificables manualmente.

---

> ⛔ **DETENER AQUÍ — FASE 8 COMPLETA**
> Verificar cálculos con datos de prueba: ej. Ingresos=$10,000 Gastos=$6,000 → Uneta=$4,000 ROI=66.7%.
> **Cambiar a GEMINI PRO para FASE 9.**

---

---

# FASE 9 — MÓDULO HISTORIAL CLIMÁTICO + GRÁFICAS
## 🟢 MODELO: GEMINI PRO
> **Por qué Gemini:** Gráficas en Jetpack Compose con Canvas, `vico` chart library
> o `Compose Charts`, animaciones de series de tiempo, y Material3 Sliders/RadioButtons
> son componentes Android. Gemini genera código Canvas Compose correcto y sin errores de API.

---

### Contexto

App **CRIBA**. Fases 1-8 completadas. Módulo de registro meteorológico y visualización de series de tiempo climáticas por ciclo de cultivo.

**Dependencia adicional necesaria** (agregar a `libs.versions.toml`):
```toml
# Opción A (recomendada): vico charts para Compose
patrykandpatrick-vico = "2.0.0-alpha.x"  # verificar última versión estable

# Opción B (alternativa sin deps externas): usar Canvas de Compose nativo
```

---

### Tareas FASE 9

**9.1 — `ClimaViewModel.kt`** (`presentation/clima/`)

```kotlin
@HiltViewModel
class ClimaViewModel @Inject constructor(
    private val climaRepo: ClimaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ClimaUiState())
    val uiState: StateFlow<ClimaUiState> = _uiState.asStateFlow()

    fun cargarClima(idCiclo: Int)
    fun registrarRegistroClimatico(registro: HistorialClimatico)
    fun eliminarRegistro(id: Long)
    fun calcularPromedioTemperatura(registros: List<HistorialClimatico>): Double
    fun calcularLluviaAcumulada(registros: List<HistorialClimatico>): Double
    fun calcularDiasSinLluvia(registros: List<HistorialClimatico>): Int
    fun getEtapaSequiaActual(registros: List<HistorialClimatico>): EtapaSequia
}

data class ClimaUiState(
    val registros: List<HistorialClimatico> = emptyList(),
    val promedioTemp: Double = 0.0,
    val lluviaAcumulada: Double = 0.0,
    val diasSinLluvia: Int = 0,
    val etapaActual: EtapaSequia = EtapaSequia.NORMAL,
    val isLoading: Boolean = true
)
```

**9.2 — `RegistrarClimaSheet.kt`** (ModalBottomSheet) (`presentation/clima/`)

Campos:
- `DatePickerDialog` fecha (default hoy)
- **Lluvia mm:** `Slider(0f..200f, steps=200)` + `OutlinedTextField` numérico sincronizados con `by remember { mutableFloatStateOf(0f) }`
- **Temperatura °C:** `Slider(-5f..45f, steps=50)` + `OutlinedTextField` sincronizados
- **Etapa Sequía:** `RadioButton` group vertical:
  - 🟢 Normal | 🟡 Leve | 🟠 Moderado | 🔴 Severo | 🔴 Crítico
  - Cada option con `RadioButton` + `Text` + chip color indicativo

`Button("Registrar Datos Climáticos")`

**9.3 — Componente Gráfica Temperatura** (`presentation/clima/components/`)

`TemperaturaChart(registros: List<HistorialClimatico>, modifier: Modifier)`:

Implementar con `Canvas` de Compose (sin dependencias externas):
- Eje X: fechas (últimos N registros, labels cada 7 días)
- Eje Y: temperatura °C con gridlines horizontales
- Línea suave entre puntos (`cubicTo` con `Path`)
- Área bajo la curva con `drawPath` semi-transparente
- Color degradado: `Brush.verticalGradient(verde→amarillo→rojo)` según temperatura
- Puntos `drawCircle` en cada registro, tooltip al tap con `pointerInput`
- Líneas de referencia: 0°C (azul) y 35°C (rojo)

**9.4 — Componente Gráfica Lluvia** (`presentation/clima/components/`)

`LluviaChart(registros: List<HistorialClimatico>, modifier: Modifier)`:

Barras verticales con `Canvas`:
- Agrupadas por semana (suma de mm)
- Ancho de barra proporcional al espacio disponible
- Color azul `Color(0xFF1565C0)` con opacidad proporcional: `alpha = (mmSemana / maxMm).coerceIn(0.2f, 1f)`
- Eje Y: escala en mm, gridlines horizontales grises
- Label mm encima de barra si > 10mm

**9.5 — `HistorialClimaScreen.kt`** (`presentation/clima/`)

```kotlin
LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
    // Cards resumen horizontal
    item { ResumenClimaRow(viewModel.uiState) }  // 4 mini-cards en Row scrollable

    // Gráfica temperatura
    item {
        Card { Column(padding=16.dp) {
            Text("TEMPERATURA °C", style=labelLarge)
            Spacer(8.dp)
            TemperaturaChart(registros, Modifier.fillMaxWidth().height(200.dp))
        }}
    }

    // Gráfica lluvia
    item {
        Card { Column(padding=16.dp) {
            Text("PRECIPITACIÓN SEMANAL (mm)", style=labelLarge)
            LluviaChart(registros, Modifier.fillMaxWidth().height(160.dp))
        }}
    }

    // Lista registros individuales
    items(registros, key = { it.id }) { registro ->
        RegistroClimaRow(registro, onDelete = { viewModel.eliminarRegistro(it) })
    }
}
```

`FloatingActionButton` "+" lanza `RegistrarClimaSheet`.

**9.6 — `HistorialScreen.kt`** (tab "Historial" del BottomNav) (`presentation/historial/`)

- `ExposedDropdownMenuBox` selector ciclo/temporada al tope
- Al seleccionar ciclo: mostrar `HistorialClimaScreen` + resumen plagas del ciclo
- Tab interno opcional: "Clima" | "Plagas"

**Output esperado:** Archivos en `presentation/clima/`, `presentation/historial/`. Gráficas visibles con datos mock en @Preview.

---

> ⛔ **DETENER AQUÍ — FASE 9 COMPLETA**
> Verificar que gráficas renderizan sin overflow con 30+ registros.
> **Cambiar a GEMINI PRO para FASE 10.**

---

---

# FASE 10 — GOOGLE MAPS + GEORREFERENCIA PARCELAS
## 🟢 MODELO: GEMINI PRO
> **Por qué Gemini:** Google Maps Compose SDK es un producto de Google. Gemini conoce
> `rememberCameraPositionState`, `GoogleMap`, `Marker`, `MarkerInfoWindow`,
> `MapUiSettings` y la integración con FusedLocationProvider mejor que ningún otro modelo.
> Evita APIs de Maps deprecated.

---

### Contexto

App **CRIBA**. Fases 1-9 completadas. Integrar Google Maps para visualizar y georeferenciar parcelas.

**Dependencia Maps ya configurada** en `libs.versions.toml` desde Fase 1: `maps-compose`.
**API Key** ya declarada en `AndroidManifest.xml`: `YOUR_MAPS_API_KEY` (reemplazar con clave real).

Para obtener API Key: [Google Cloud Console](https://console.cloud.google.com) → Maps SDK for Android.

---

### Tareas FASE 10

**10.1 — `MapaViewModel.kt`** (`presentation/maps/`)

```kotlin
@HiltViewModel
class MapaViewModel @Inject constructor(
    private val terrenoRepo: TerrenoRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapaUiState())
    val uiState: StateFlow<MapaUiState> = _uiState.asStateFlow()

    fun cargarTerrenos()
    suspend fun obtenerUbicacionActual(): LatLng?
    fun guardarCoordenadas(idTerreno: Int, latitud: Double, longitud: Double)
    fun getTerrenosConCoordenadas(): List<Terreno>  // filtra solo con lat/lng no null
}

data class MapaUiState(
    val terrenos: List<Terreno> = emptyList(),
    val terrenoSeleccionado: Terreno? = null,
    val ubicacionUsuario: LatLng? = null,
    val isLoading: Boolean = true
)
```

**10.2 — `MapaTerrenosScreen.kt`** (`presentation/maps/`)

```kotlin
@Composable
fun MapaTerrenosScreen(
    viewModel: MapaViewModel = hiltViewModel(),
    onNavigateToDetalle: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(23.6345, -102.5528), 5f) // México
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = true,
                myLocationButtonEnabled = true,
                mapToolbarEnabled = false
            ),
            properties = MapProperties(isMyLocationEnabled = true)
        ) {
            // Marker por cada terreno con coordenadas
            uiState.terrenos.filter { it.latitud != null }.forEach { terreno ->
                MarkerInfoWindow(
                    state = MarkerState(LatLng(terreno.latitud!!, terreno.longitud!!)),
                    title = terreno.nombre,
                    snippet = "${terreno.hectareas} ha",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                ) { // InfoWindow custom
                    TerrenoInfoWindowContent(terreno, onNavigateToDetalle)
                }
            }
        }

        // Panel inferior: terreno seleccionado
        if (uiState.terrenoSeleccionado != null) {
            TerrenoMapaCard(
                terreno = uiState.terrenoSeleccionado!!,
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
                onVerDetalle = { onNavigateToDetalle(uiState.terrenoSeleccionado!!.id) }
            )
        }
    }
}
```

**10.3 — `TerrenoInfoWindowContent`** (composable InfoWindow personalizado)
- Card pequeño: nombre terreno bold + hectáreas + cultivo activo chip + botón "Ver Detalle"
- Fondo blanco, sombra, bordes 8dp

**10.4 — `SeleccionarUbicacionScreen.kt`** (`presentation/maps/`)

Pantalla para pin drag-and-drop al crear terreno:

```kotlin
Box(Modifier.fillMaxSize()) {
    GoogleMap(
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng -> viewModel.actualizarUbicacionSeleccionada(latLng) }
    ) {
        // Marker arrastrable
        if (ubicacionSeleccionada != null) {
            Marker(
                state = MarkerState(ubicacionSeleccionada),
                draggable = true,
                onMarkerDragEnd = { marker ->
                    viewModel.actualizarUbicacionSeleccionada(marker.position)
                }
            )
        }
    }

    // Panel inferior con coordenadas y botones
    Card(Modifier.align(Alignment.BottomCenter).padding(16.dp)) {
        Column(padding=16.dp) {
            Text("Toca el mapa o arrastra el marcador", style=bodyMedium)
            if (ubicacionSeleccionada != null) {
                Text("Lat: ${ubicacionSeleccionada.latitude.format(6)}°", style=labelMedium)
                Text("Lng: ${ubicacionSeleccionada.longitude.format(6)}°", style=labelMedium)
            }
            Row {
                OutlinedButton("Usar mi GPS") { viewModel.centrarEnUbicacionActual() }
                Spacer(8.dp)
                Button("Confirmar") { onConfirmar(ubicacionSeleccionada) }
            }
        }
    }

    // Botón centrar en ubicación actual
    FloatingActionButton(
        modifier = Modifier.align(Alignment.TopEnd).padding(16.dp),
        onClick = { viewModel.centrarEnUbicacionActual() }
    ) { Icon(Icons.Rounded.MyLocation, "") }
}
```

**10.5 — `MapaTerrenoMini.kt`** (composable embebido en DetalleParcela Tab Mapa)

```kotlin
@Composable
fun MapaTerrenoMini(latitud: Double, longitud: Double, nombre: String, onAbrirCompleto: () -> Unit) {
    Box(Modifier.fillMaxWidth().height(200.dp)) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LatLng(latitud, longitud), 14f)
            },
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                scrollGesturesEnabled = false,
                rotationGesturesEnabled = false
            )
        ) {
            Marker(state = MarkerState(LatLng(latitud, longitud)), title = nombre)
        }
        // Overlay "Abrir mapa completo" en esquina
        TextButton(
            onClick = onAbrirCompleto,
            modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                .background(Color.White.copy(alpha=0.85f), RoundedCornerShape(4.dp))
        ) { Text("⛶ Expandir", style=labelSmall) }
    }
}
```

**10.6 — Integrar en NuevoTerrenoSheet (actualizar Fase 6)**

Reemplazar botón GPS placeholder:
```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    OutlinedButton(onClick = { viewModel.usarGPSActual() }) {
        Icon(Icons.Rounded.GpsFixed, ""); Spacer(4.dp); Text("GPS")
    }
    Button(onClick = { navController.navigate(Screen.SeleccionarUbicacion.route) }) {
        Icon(Icons.Rounded.Map, ""); Spacer(4.dp); Text("Elegir en Mapa")
    }
}
// Mostrar coordenadas obtenidas
if (latitud != null) {
    Text("📍 ${latitud.format(4)}°, ${longitud.format(4)}°", style=labelMedium, color=Primary)
}
```

**Output esperado:** Archivos en `presentation/maps/`. Probar con dispositivo físico con API Key real configurada.

---

> ⛔ **DETENER AQUÍ — FASE 10 COMPLETA**
> App CRIBA 100% funcional. Hacer APK debug para demo.
> Integración final: verificar que todas las pantallas navegan correctamente.

---

---

## CHECKLIST FINAL POST-DESARROLLO

```
□ FASE 1  — Build limpio, Hilt inicializado, estructura de paquetes correcta
□ FASE 2  — Room genera tablas, DB Inspector muestra esquema correcto
□ FASE 3  — Retrofit conecta con backend, offline-first funcional
□ FASE 4  — Navegación entre 4 tabs, TopBar y BottomBar correctos
□ FASE 5  — Dashboard carga datos reales desde Room/API
□ FASE 6  — CRUD terrenos y ciclos persiste en Room
□ FASE 7  — Foto con CameraX se guarda y carga, GPS obtiene coordenadas
□ FASE 8  — Cálculos ROI/Utilidad verificados manualmente con datos conocidos
□ FASE 9  — Gráficas renderizan sin crash con 0 datos y con 50+ registros
□ FASE 10 — Maps muestra markers, drag-and-drop de pin funcional
```

---

*CRIBA — Cuchurrumin FC | UniverMilenium 801 | 2026*
