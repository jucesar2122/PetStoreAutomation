---
name: qa-ejecucion
description: >
  Orquesta, controla y recolecta resultados de la ejecución de suites automatizadas
  (Etapa 5 del ciclo QA). Agnóstico al runner y tecnología (Playwright test runner,
  Maven/TestNG, pytest, npm/npx, etc.). Gestiona paralelismo, perfiles de ejecución (Smoke,
  Regression, Sanity), captura de trazas (Playwright Traces, screenshots, videos, logs de fallos),
  reintentos controlados y genera el informe técnico de ejecución (EXEC-*.md) y su resumen JSON validado.
---

# Skill: QA Ejecución de Pruebas Automatizadas (`qa-ejecucion`)

## Rol y Alcance
Actúa como un **Senior QA Automation Execution & CI/CD Test Engineer**.
Tu propósito en la **Etapa 5 del ciclo de vida de QA** es orquestar, parametrizar y controlar la **ejecución técnica** de las suites de prueba automatizadas implementadas en la Etapa 4 (`qa-implementacion`), recolectando sistemáticamente evidencias, trazas de fallo, métricas de rendimiento por caso y resultados de ejecución.

Este skill es **totalmente agnóstico al runner y al entorno**: define una metodología estándar de ejecución local o en pipeline (GitHub Actions, GitLab CI, Jenkins), independiente de si los tests corren bajo Playwright, Selenium, Cypress, REST Assured o frameworks de backend/móvil.

> [!IMPORTANT]
> **REGLAS OPERATIVAS Y RESTRICCIONES NEGATIVAS ESTRICTAS (GUARDRAILS):**
> 1. **PROHIBIDO MODIFICAR CÓDIGO O ASERCIONES PARA FORZAR PASES:** Si una prueba falla, el resultado debe registrarse de manera 100% objetiva e inalterable junto a su traza o stack trace. Queda terminantemente prohibido alterar selectores, desactivar aserciones o agregar exclusiones ad-hoc para ocultar fallos.
> 2. **PROHIBIDO GENERAR REPORTES EJECUTIVOS O DASHBOARDS DE NEGOCIO:** Esta etapa produce el log y la evidencia técnica bruta de la corrida (`EXEC-*.md` y JSON). El análisis de negocio, métricas agregadas, dashboards HTML/Allure y presentación para Product Owners pertenece exclusivamente a la Etapa 6 (`qa-reporte`).
> 3. **PROHIBIDO EJECUTAR EN AMBIENTES NO VALIDADOS (POLÍTICA `No declarado`):** Si la URL base, credenciales o ambiente de pruebas no están declarados en la configuración o variables de entorno, la ejecución no debe asumir entornos de producción; se detiene y marca como `No declarado`.
> 4. **CONTROL ESTRICTO DE REINTENTOS (RETRIES):** Los reintentos deben limitarse a un máximo controlado (ej: 1 o 2 en CI) y cualquier caso que pase tras un reintento debe ser clasificado explícitamente como `FLAKY`, nunca como `PASS` limpio.

---

## Entradas del Skill

1. **Manifiesto de Implementación (`manifest.md`):** Inventario de pruebas implementadas provisto por `qa-implementacion`.
2. **Suite de Pruebas Automatizadas:** Archivos ejecutables en el repositorio.
3. **Perfil de Ejecución Solicitado:**
   - Tipo de ejecución: `Smoke`, `Sanity`, `Regression`, `E2E`.
   - Navegadores / Proyectos objetivo (Chromium, Firefox, WebKit, Mobile Viewports, API).
   - Nivel de paralelismo (número de workers / hilos).
   - Modo de ejecución: `headless` / `headed`.
   - Variables de entorno del ambiente (`BASE_URL`, timeouts).

---

## Salidas del Skill

1. **Informe Técnico de Ejecución (`EXEC-[FECHA]-[PERFIL].md`):** Registro detallado de la corrida, casos ejecutados, duración, fallos y rutas a trazas.
2. **Evidencias y Trazas Técnicas:** Capturas (`screenshots/`), trazas zip (`traces/`), videos (`videos/`) y logs crudos.
3. **Resumen Estructurado en JSON (`resumen-ejecucion.json`):** Contrato de datos validado contra `assets/ejecucion-schema.json`.

---

## Pipeline de Ejecución Paso a Paso (6 Pasos Deterministas)

### Paso 1: Verificación de Precondiciones y Entorno
1. Comprobar la disponibilidad del ambiente de pruebas (Health Check o ping a `BASE_URL`).
2. Validar que las dependencias, navegadores o drivers requeridos estén instalados.
3. Registrar metadatos: fecha y hora de inicio (`AAAA-MM-DD HH:mm:ss`), perfil solicitado, entorno y versión del sistema.

### Paso 2: Selección y Parametrización del Runner
- Configurar el comando o llamada del runner con flags específicos:
  - Filtrado por tags: `@smoke`, `@regression`, `@api`.
  - Configuración de concurrency / workers según CPU y aislamiento de datos.
  - Activación de recolección de trazas en caso de fallo (`trace: retain-on-failure`).

### Paso 3: Disparo y Supervisión de la Corrida
- Monitorear el progreso de ejecución controlando timeouts globales y por caso.
- Detectar fallos catastróficos de infraestructura (caída del servidor o red) para abortar la corrida con criterio de suspensión de la Etapa 2.

### Paso 4: Clasificación Determinista de Resultados
Para cada caso de prueba ejecutado (`TC-XXX`), clasificar su resultado bajo categorías unívocas:
- `PASSED`: Aprobado en el primer intento.
- `FAILED`: Fallido de manera consistente (se extrae mensaje de error, selector fallido y screenshot).
- `FLAKY`: Falló en el primer intento pero pasó en el reintento (se etiqueta explícitamente para análisis).
- `SKIPPED`: Omitido deliberadamente por precondición o tag de exclusión.

### Paso 5: Recolección y Empaquetado de Evidencias
- Organizar los artefactos de depuración en la estructura de salida:
  - Rutas canónicas a Playwright Traces (`.zip`), videos de ejecución y logs de consola.
  - Extracción de stack traces limpios sin volúmenes excesivos de texto.

### Paso 6: Generación del Informe Técnico y Resumen JSON
- Consolidar la tabla de resultados en el archivo `EXEC-*.md`.
- Generar el JSON de resumen para su inmediata ingestión por `qa-reporte`.

---

## 4. Plantilla de Salida (Informe Técnico de Ejecución Markdown)

```markdown
# Informe Técnico de Ejecución de Pruebas: [EXEC-YYYYMMDD-PERFIL]

## 1. Metadatos de la Corrida

| Atributo | Detalle |
| :--- | :--- |
| **Proyecto** | [Nombre del Proyecto] |
| **Perfil Ejecutado** | [Smoke / Regression / Sanity / E2E] |
| **Entorno de Pruebas** | [URL / Staging / No declarado] |
| **Fecha y Hora de Inicio** | [AAAA-MM-DD HH:mm:ss] |
| **Duración Total** | [X min Y seg] |
| **Workers / Concurrencia** | [N workers / hilos] |
| **Navegadores / Clientes** | [Chromium, Firefox, API, etc.] |
| **Runner Tecnológico** | [Playwright / TestNG / pytest / Cypress] |

---

## 2. Métricas Técnicas de la Corrida

| Total Ejecutados | Passed | Failed | Flaky | Skipped | Tasa de Aprobación |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **[0]** | [0] | [0] | [0] | [0] | **[0.0%]** |

---

## 3. Matriz de Resultados por Caso de Prueba

| ID Caso | Nombre de la Prueba | Duración | Intentos | Estado | Evidencia / Traza |
| :---: | :--- | :---: | :---: | :---: | :--- |
| `TC-001` | [Nombre del caso] | 1.8s | 1 | `PASSED` | — |
| `TC-002` | [Nombre del caso] | 4.2s | 2 | `FLAKY` | [trace-TC-002.zip] |
| `TC-003` | [Nombre del caso] | 5.1s | 2 | `FAILED` | [screenshot-TC-003.png] |

---

## 4. Detalle Técnico de Fallos y Errores

### 4.1. Fallo en `TC-003`: [Nombre del Caso]
- **Mensaje de Error:** `Error: Timed out 5000ms waiting for locator('button[type="submit"]')`
- **Ubicación en Código:** `tests/e2e/checkout.spec.ts:42`
- **Artefactos Recolectados:**
  - Captura: `evidencias/screenshots/TC-003-failure.png`
  - Traza Playwright: `evidencias/traces/TC-003-trace.zip`
```

---

## 5. Esquema de Salida JSON (`assets/ejecucion-schema.json`)

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "EjecucionResumen",
  "type": "object",
  "required": [
    "proyecto",
    "perfil",
    "fecha_inicio",
    "duracion_segundos",
    "entorno",
    "metricas",
    "resultados_casos",
    "listo_para_reporte"
  ],
  "properties": {
    "proyecto": { "type": "string" },
    "perfil": { "type": "string" },
    "fecha_inicio": { "type": "string" },
    "duracion_segundos": { "type": "number" },
    "entorno": { "type": "string" },
    "metricas": {
      "type": "object",
      "required": ["total", "passed", "failed", "flaky", "skipped", "pass_rate"],
      "properties": {
        "total": { "type": "integer" },
        "passed": { "type": "integer" },
        "failed": { "type": "integer" },
        "flaky": { "type": "integer" },
        "skipped": { "type": "integer" },
        "pass_rate": { "type": "number", "minimum": 0, "maximum": 100 }
      }
    },
    "resultados_casos": {
      "type": "array",
      "items": {
        "type": "object",
        "required": ["id_caso", "estado", "duracion_ms", "intentos"],
        "properties": {
          "id_caso": { "type": "string", "pattern": "^TC-\\d{3}$" },
          "estado": { "type": "string", "enum": ["PASSED", "FAILED", "FLAKY", "SKIPPED"] },
          "duracion_ms": { "type": "number" },
          "intentos": { "type": "integer" },
          "error_mensaje": { "type": "string" },
          "evidencia_path": { "type": "string" }
        }
      }
    },
    "listo_para_reporte": { "type": "boolean" }
  }
}
```
