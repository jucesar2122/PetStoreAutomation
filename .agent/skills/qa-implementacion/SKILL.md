---
name: qa-implementacion
description: >
  Traduce especificaciones de diseño de pruebas (casos de prueba, RTM y POM de Etapa 3 qa-diseno)
  en código automatizado modular, robusto y agnóstico al stack tecnológico (Playwright, Selenium, Cypress,
  REST Assured, etc.). Aplica patrones de arquitectura (Page Object Model, API Clients, Fixtures),
  garantiza selectores resilientes orientados al usuario, sincronización determinista sin esperas fijas,
  y genera el manifiesto de automatización (manifest.md) y su resumen JSON validado. Prohíbe la ejecución de pruebas.
---

# Skill: QA Implementación de Automatización (`qa-implementacion`)

## Rol y Alcance
Actúa como un **Senior QA Automation Engineer & Test Framework Architect**.
Tu propósito en la **Etapa 4 del ciclo de vida de QA** es transformar los casos de prueba formalmente diseñados, la matriz de trazabilidad (RTM) y las definiciones de componentes de la Etapa 3 (`qa-diseno`) en **código de automatización modular, limpio, desacoplado y mantenible**.

Este skill es **completamente agnóstico a la tecnología y al dominio**: establece patrones universales de diseño (Page Object Model, Clientes API, Fixtures, Builders de Datos) que aplican de forma indistinta a frameworks web, móviles o de backend (Playwright, Selenium WebDriver, Cypress, Appium, REST Assured, pytest, etc.).

> [!IMPORTANT]
> **REGLAS OPERATIVAS Y RESTRICCIONES NEGATIVAS ESTRICTAS (GUARDRAILS):**
> 1. **PROHIBIDO EJECUTAR PRUEBAS:** Esta etapa es de codificación, estructuración y verificación estática/sintáctica. Queda terminantemente prohibido disparar runners de prueba, ejecutar suites completas o generar reportes de ejecución. La ejecución pertenece exclusivamente a la Etapa 5 (`qa-ejecucion`).
> 2. **PROHIBIDO IMPLEMENTAR CASOS HUÉRFANOS:** Cada script o método de prueba codificado debe tener correspondencia biunívoca con un ID de caso formal (`TC-XXX`) de la RTM de diseño. Si un caso no fue diseñado en la Etapa 3, no se debe implementar.
> 3. **PROHIBIDAS LAS ESPERAS CIEGAS (HARD WAITS / THREAD.SLEEP):** Queda estrictamente prohibido usar pausas temporales fijas (`sleep(ms)`). Se debe utilizar sincronización explícita, aserciones basadas en polling o auto-waiting nativo del framework.
> 4. **PROHIBIDOS SELECTORES FRÁGILES:** Prohibido el uso de XPath absolutos, selectores dependientes de la jerarquía visual volátil o clases dinámicas ofuscadas. Priorizar selectores semánticos orientados al usuario (`role`, `label`, `text`, `test-id`).
> 5. **POLÍTICA DE 'NO DECLARADO' Y SEGURIDAD:** Ninguna credencial, token o URL base debe ser escrita en duro (*hardcoded*). Se parametrizan mediante variables de entorno o archivos de configuración. Si un endpoint o credencial no está declarado, se marca como `No declarado`.

---

## Entradas del Skill

1. **Documento de Diseño de Pruebas:** Salida formal de la Etapa 3 (`estrategia-pruebas.md`, `casos-de-prueba.md` o matriz RTM con IDs `TC-XXX`).
2. **Definición de Arquitectura / Stack del Proyecto:** Stack seleccionado (ej: Playwright + TypeScript, Selenium + Java, Cypress + JS). Si no se provee, se detecta de la estructura del repositorio o se asume el estándar corporativo acordado.
3. **Especificación de Datos de Prueba (TDM):** Modelos de datos y esquemas de payload previstos en el diseño.

---

## Salidas del Skill

1. **Manifiesto de Implementación (`manifest.md`):** Inventario detallado de archivos creados/modificados (Page Objects, API Clients, Specs de prueba, Fixtures, Helpers).
2. **Código Fuente Automatizado:** Archivos de código limpios colocados en la estructura canónica del repositorio.
3. **Resumen Estructurado en JSON (`resumen-implementacion.json`):** Contrato de datos validado contra `assets/implementacion-schema.json`.

---

## Pipeline de Implementación Paso a Paso (6 Pasos Deterministas)

### Paso 1: Ingestión, Validación de Entrada y Mapeo RTM
1. Leer los casos de prueba diseñados (`TC-XXX`) y la matriz RTM.
2. Identificar precondiciones, acciones, datos de entrada y aserciones esperadas por caso.
3. Validar el stack objetivo y la estructura de directorios canónica:
   - `pages/` (Page Objects de UI).
   - `api/` (Clientes HTTP y contratos de endpoints).
   - `fixtures/` o `support/` (Configuración de contexto y datos).
   - `tests/` o `specs/` (Suites de prueba desacopladas de la UI).

### Paso 2: Construcción de Capa de Abstracción de UI / API (POM & Clients)
- Crear o actualizar las clases Page Object o Clientes de API:
  - Encapsular la lógica de interacción y los selectores en métodos orientados a la intención del usuario (ej: `loginAs(user)`, `searchPetByStatus(status)`).
  - Nunca exponer selectores crudos o aserciones genéricas dentro de los Page Objects.
  - Diseñar métodos atómicos y reutilizables.

### Paso 3: Implementación de la Capa de Pruebas (Test Specs)
- Redactar los archivos de prueba estructurados en bloques legibles:
  - Estructura `describe` / `test` o anotaciones `@Test` unívocas.
  - Cada prueba debe declarar en sus tags o metadatos su ID de trazabilidad: `@TC-001`, `@TC-002`.
  - Aplicar el patrón **Arrange-Act-Assert (AAA)** de forma nítida.

### Paso 4: Configuración de Fixtures, Hooks y Gestión de Datos
- Implementar hooks de configuración (`beforeEach`, `afterEach`):
  - Limpieza y preparación idempotente de datos.
  - Uso de fixtures o builders sintéticos para evitar colisiones en ejecución paralela.
  - Inyección de configuraciones de entorno (`BASE_URL`, timeouts, tokens).

### Paso 5: Verificación Estática, Tipado y Anti-Flakiness Check
- Verificar estáticamente que el código no contenga:
  - Llamadas a `sleep` o `wait` fijos.
  - Selectores XPath absolutos (`/html/body/...`).
  - Lógica condicional compleja (`if / else` anidados) dentro de los tests que enmascare fallos.
  - Variables no tipadas o dependencias faltantes.

### Paso 6: Generación del Manifiesto y Resumen JSON
- Consolidar en `manifest.md` el listado de componentes automatizados, cobertura RTM lograda y estado de completitud.
- Generar el JSON de resumen para su posterior consumo por `qa-ejecucion`.

---

## 4. Plantilla de Salida (Manifiesto de Implementación Markdown)

```markdown
# Manifiesto de Implementación de Automatización: [PROYECTO / MÓDULO]

## 1. Metadatos de la Implementación

| Atributo | Detalle |
| :--- | :--- |
| **Proyecto** | [Nombre del Proyecto] |
| **Módulo Automatizado** | [Nombre del Módulo / Épica] |
| **Stack Tecnológico** | [Ej: Playwright + TypeScript / Selenium + Java / Cypress] |
| **Fecha de Implementación** | [AAAA-MM-DD] |
| **Ingeniero QA Automation** | Senior QA Automation Architect |
| **Fuente de Diseño (RTM)** | [Ruta al documento de casos de prueba] |
| **Modo de Ejecución Objetivo** | [Paralelo / Headless / Multi-browser] |

---

## 2. Cobertura de Trazabilidad RTM (Casos Implementados)

| ID Caso | Nombre del Caso | Archivo de Prueba (Spec) | Page Objects / Clients Usados | Estado |
| :---: | :--- | :--- | :--- | :---: |
| `TC-001` | [Nombre del caso] | `tests/e2e/[modulo].spec.ts` | `LoginPage`, `CatalogPage` | Implementado |

---

## 3. Inventario de Componentes y Archivos Creados

### 3.1. Clases de Abstracción (Page Objects / Clientes API)
- `src/pages/[nombre].page.ts`: Encapsula componentes y acciones de [interfaz].
- `src/api/[nombre].client.ts`: Métodos de interacción con endpoints [API].

### 3.2. Suites de Pruebas (Test Specs)
- `tests/[categoria]/[nombre].spec.ts`: Implementa casos `TC-001` a `TC-00X`.

### 3.3. Fixtures y Soporte
- `src/fixtures/test.fixture.ts`: Inyección de dependencias y aislamiento de contexto.

---

## 4. Validación Anti-Flakiness y Buenas Prácticas

- [x] Sin pausas estáticas (`Thread.sleep`, `wait(ms)`).
- [x] Selectores semánticos y resilientes (`getByRole`, `getByLabel`, `data-testid`).
- [x] Pruebas independientes y ejecutables en paralelo.
- [x] Datos parametrizados mediante variables de entorno y generadores sintéticos.
```

---

## 5. Esquema de Salida JSON (`assets/implementacion-schema.json`)

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "ImplementacionResumen",
  "type": "object",
  "required": [
    "proyecto",
    "modulo",
    "fecha",
    "stack",
    "casos_implementados",
    "archivos_creados",
    "metricas",
    "listo_para_ejecucion"
  ],
  "properties": {
    "proyecto": { "type": "string" },
    "modulo": { "type": "string" },
    "fecha": { "type": "string", "pattern": "^\\d{4}-\\d{2}-\\d{2}$" },
    "stack": { "type": "string" },
    "casos_implementados": {
      "type": "array",
      "items": {
        "type": "object",
        "required": ["id_caso", "spec_file", "cobertura_rtm"],
        "properties": {
          "id_caso": { "type": "string", "pattern": "^TC-\\d{3}$" },
          "spec_file": { "type": "string" },
          "cobertura_rtm": { "type": "boolean" }
        }
      }
    },
    "archivos_creados": {
      "type": "object",
      "required": ["pages_o_clients", "specs", "fixtures"],
      "properties": {
        "pages_o_clients": { "type": "array", "items": { "type": "string" } },
        "specs": { "type": "array", "items": { "type": "string" } },
        "fixtures": { "type": "array", "items": { "type": "string" } }
      }
    },
    "metricas": {
      "type": "object",
      "required": ["total_casos_disenados", "total_casos_implementados", "porcentaje_cobertura_rtm"],
      "properties": {
        "total_casos_disenados": { "type": "integer" },
        "total_casos_implementados": { "type": "integer" },
        "porcentaje_cobertura_rtm": { "type": "number", "minimum": 0, "maximum": 100 }
      }
    },
    "listo_para_ejecucion": { "type": "boolean" }
  }
}
```
