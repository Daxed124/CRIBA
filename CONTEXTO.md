# Contexto del Proyecto: Criba (Gestión Agrícola Inteligente)

Este archivo sirve como memoria principal del estado actual del proyecto, la arquitectura decidida y los pasos a seguir. Su propósito es mantener la continuidad del desarrollo.

## 🎯 Objetivo del Proyecto
Desarrollar una aplicación móvil nativa en Android para la gestión inteligente de predios agrícolas (lotes/fincas). La app permite a los agricultores llevar un registro histórico y financiero de sus ciclos de cultivo, monitorear plagas, registrar el clima y visualizar analíticas financieras (Dashboard).

## 🛠 Stack Técnico y Reglas Arquitectónicas (Estrictas)
1. **Lenguaje:** Kotlin (100%, sin código heredado en Java).
2. **Arquitectura:** MVVM + Clean Architecture + Repository Pattern.
   * **Domain:** Entidades puras y UseCases (Lógica de negocio). Sin dependencias de Android.
   * **Data:** Repositorios, DAOs (Room), DTOs (Retrofit) y Workers.
   * **Presentation:** ViewModels (StateFlow) y UI (Jetpack Compose).
3. **UI/UX:** Construido íntegramente con **Jetpack Compose** y componentes de Material Design 3.
4. **Persistencia Local:** Room Database (`CribaDatabase`).
5. **Estrategia de Red/Sincronización:** Enfoque *Offline-First*. Todos los registros en base de datos tienen un flag `isSynced`. Un `SyncWorker` (WorkManager) se encarga de subir los datos locales a la nube en segundo plano cuando hay internet.
6. **Inyección de Dependencias:** Hilt Dagger.
7. **Autenticación:** **Inicio de sesión local simple** (Registro/Login con Correo y Contraseña). Los usuarios y sus contraseñas se almacenan en una base de datos local Room (`users`) y la sesión activa se persiste localmente usando Preferences DataStore (`AuthRepositoryImpl`). *(Nota: Inicialmente se planteó Google Sign-In, pero se descartó para simplificar el flujo).*

## ✅ Lo que ya está Implementado
*   **Base de datos local:** Completamente funcional con Room. Tablas para:
    *   `TerrainEntity` (Terrenos/Lotes)
    *   `CycleEntity` (Ciclos de cultivo)
    *   `TransactionEntity` (Ingresos y egresos)
    *   `PestEntity` (Monitoreo de plagas e incidencias)
    *   `ClimateEntity` (Registros de clima y etapas de sequía)
    *   `UserEntity` (Usuarios locales para registro/login)
*   **Use Cases:** Completados (Cálculos de métricas, login, operaciones CRUD básicas).
*   **UI - Jetpack Compose:**
    *   `LoginScreen`: Pantalla de inicio de sesión y registro local usando correo, contraseña y nombre completo.
    *   `MapScreen`: Mapa para la visualización de terrenos usando Google Maps.
    *   `TerrainFormScreen` y `CycleScreen`: Formularios para registrar fincas y ciclos.
    *   `FinanceScreen`: Registro de ingresos y gastos de la finca.
    *   `PestScreen` y `ClimateScreen`: Registro de observaciones en campo.
    *   `DashboardScreen`: Vista que consolida el clima del ciclo activo, resumen del cultivo, historial y sugerencias.
*   **Verificación:** El proyecto compila 100% exitoso bajo `assembleDebug`.

## 🚧 Pendientes y Próximos Pasos (Backlog Futuro)
1. **Google Maps API Key:**
   * Generar una *API Key* para Google Maps e insertarla en `AndroidManifest.xml` en lugar del valor placeholder `YOUR_GOOGLE_MAPS_API_KEY`.
2. **Backend de Sincronización:**
   * Configurar el servidor y la URL base real (`BASE_URL`) en `NetworkModule.kt` cuando el backend esté listo para recibir las peticiones de sincronización en segundo plano.
3. **Mejoras Visuales en el Dashboard:**
   * Agregar bibliotecas de gráficos en Compose (ej. Vico o YCharts) o realizar gráficos personalizados usando `Canvas` para visualizar gastos e ingresos en `DashboardScreen`.
   * Crear un panel de alertas visuales en el Dashboard consolidando datos críticos (ej. plagas con `Severity.CRITICO`).
4. **Integración de IA (Opcional Futuro):**
   * Generar proyecciones automáticas de cosecha basadas en clima y plagas registradas utilizando algún servicio LLM / Modelo predictivo.

## 🚀 Cómo correr el proyecto localmente
1. Abre la carpeta raíz en **Android Studio**.
2. Espera a que Gradle sincronice las dependencias (Kotlin 2.0.21, Hilt, Room, Compose).
3. Selecciona un emulador (preferible API 34 o 35) y presiona **Run (Shift+F10)**.
4. Si tienes errores de "SDK location not found", asegúrate de crear el archivo `local.properties` apuntando a tu SDK local (`sdk.dir=C\:\\Ruta\\A\\Tu\\Sdk`).

## 📚 Buenas Prácticas y Convenciones
1. **Testing:**
   * **Capa Domain:** Probar los `UseCases` con JUnit y MockK. Sin dependencias de Android.
   * **Capa UI:** Usar Compose Testing para probar componentes y flujos de UI.
2. **Manejo de Errores:**
   * Retornar clases de tipo `Result<T>` o encapsular errores en ViewModels para actualizar el estado del UI de forma segura.
3. **Estructura de Carpetas:**
   * `domain/`: `model` (entidades), `usecase` (lógica), `repository` (interfaces).
   * `data/`: `local` (Room), `remote` (Retrofit), `repository` (implementaciones), `worker` (WorkManager).
   * `presentation/`: `ui/screens` (pantallas), `viewmodel` (ViewModels), `theme` (estilos).
