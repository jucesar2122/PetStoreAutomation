---
name: qa-mantenimiento
description: >
  Ejecuta el mantenimiento evolutivo, correctivo y de optimización del framework y suite
  automatizada (Etapa 8 del ciclo QA). Agnóstico al stack y dominio. Refactoriza selectores
  frágiles hacia locators semánticos y resilientes, actualiza Page Objects y Clientes API ante
  cambios de contratos, erradica flakiness recurrente, poda pruebas redundantes/obsoletas preservando
  la cobertura RTM y optimiza la velocidad de ejecución. Genera mantenimiento-suite.md y su resumen JSON.
---

# Skill: QA Mantenimiento y Refactorización de Automatización (`qa-mantenimiento`)

## Rol y Alcance
Actúa como un **Senior QA Automation Maintainability & Technical Debt Architect**.
Tu propósito en la **Etapa 8 del ciclo de vida de QA** es abordar sistemáticamente la deuda técnica diagnosticada en la Retrospectiva (Etapa 7 `qa-retro`), ejecutando el **mantenimiento evolutivo, correctivo y preventivo** del código de automatización, los Page Objects, los Clientes de API y las dependencias de prueba.

Este skill es **completamente agnóstico a la tecnología y al dominio**: garantiza la longevidad, estabilidad y velocidad de la suite de pruebas a lo largo del tiempo, evitando la degradación del framework (*test rot*) en cualquier proyecto (Playwright, Selenium, Cypress, REST Assured, etc.).

> [!IMPORTANT]
> **REGLAS OPERATIVAS Y RESTRICCIONES NEGATIVAS ESTRICTAS (GUARDRAILS):**
> 1. **PROHIBIDO PODAR O ELIMINAR PRUEBAS SIN JUSTIFICACIÓN Y TRAZABILIDAD:** Ningún test puede ser deprecado o removido arbitrariamente. Cada eliminación debe registrar formalmente su ID (`TC-XXX`), motivo documentado (ej: funcionalidad discontinuada, cobertura redundante migrada a nivel inferior) y aprobación del equipo.
> 2. **PRESERVACIÓN IRREVOCABLE DE COBERTURA RTM CRÍTICA:** Ninguna refactorización o cambio en localizadores puede alterar o degradar la cobertura de los casos de prueba clasificados como `Must` o `Críticos` en la matriz RTM.
> 3. **PROHIBIDO RELAJAR O DEBILITAR ASERCIONES:** Queda terminantemente prohibido reducir la exigencia de las aserciones, eliminar validaciones o capturar excepciones silenciosamente para evitar que un test falle.
> 4. **POLÍTICA DE 'NO DECLARADO' Y VERSIÓN MÍNIMA:** Versiones de librerías o dependencias no especificadas se actualizan siguiendo las versiones estables compatibles del proyecto, marcando `No declarado` en lo no informado.

---

## Entradas del Skill

1. **Backlog de Deuda Técnica y Plan de Acción:** Salida formal de la Etapa 7 (`retrospectiva-calidad.md` / `resumen-retro.json`).
2. **Código Fuente del Framework de Pruebas:** Clases Page Object, API Clients, suites de prueba (`specs/`), fixtures y configuraciones.
3. **Nuevos Contratos de API o Cambios de UI:** Documentación de endpoints modificados o nuevas versiones de componentes visuales.

---

## Salidas del Skill

1. **Bitácora de Mantenimiento y Refactorización (`mantenimiento-refactorizacion.md`):** Registro detallado de selectores actualizados, tests podados, refactorizaciones y métricas de optimización.
2. **Código Refactorizado y Estabilizado:** Archivos de prueba y componentes actualizados en el repositorio.
3. **Resumen Estructurado en JSON (`resumen-mantenimiento.json`):** Contrato de datos validado contra `assets/mantenimiento-schema.json`.

---

## Pipeline de Mantenimiento Paso a Paso (6 Pasos Deterministas)

### Paso 1: Ingestión, Priorización y Análisis de Impacto
1. Ingerir los ítems de deuda técnica diagnosticados en `qa-retro`.
2. Clasificar las acciones de mantenimiento en 3 ejes:
   - **Estabilidad (Anti-Flakiness):** Sustitución de selectores frágiles y esperas desincronizadas.
   - **Rendimiento:** Paralelismo, modularización de precondiciones y reutilización de estados de autenticación.
   - **Evolución:** Actualización de Page Objects y Clientes ante cambios en la aplicación.

### Paso 2: Refactorización y Endurecimiento de Localizadores (Locators)
- Localizar selectores con riesgo de quiebre (XPath absolutos, selectores por posición de CSS, IDs autogenerados).
- Refactorizar hacia selectores semánticos orientados al usuario:
  - Playwright: `getByRole()`, `getByLabel()`, `getByTestId()`.
  - Selenium: `@FindBy` resilientes con atributos semánticos `data-testid` o `aria-*`.
- Centralizar selectores en los Page Objects correspondientes para evitar duplicación (*Single Point of Truth*).

### Paso 3: Optimización de Precondiciones y Gestión de Estado
- Identificar pruebas con pasos repetitivos de UI lentos (ej: login visual en cada test).
- Reemplazar por inyección de estado de sesión (cookies, tokens vía API o Storage State).
- Modularizar fixtures para permitir ejecución aislada e idempotente en múltiples hilos/workers.

### Paso 4: Poda y Consolidación de Suites de Prueba (Test Pruning)
- Evaluar tests que aportan valor nulo o redundante:
  - Fusionar casos combinatorios en pruebas parametrizadas (Data-Driven Testing).
  - Deprecar formalmente tests de funcionalidades obsoletas actualizando la matriz RTM.
  - Asegurar que no se genere ninguna brecha de cobertura en requerimientos activos.

### Paso 5: Sincronización con Nuevos Contratos de API
- Si hubo cambios en Swagger/OpenAPI o contratos backend:
  - Actualizar payloads y métodos de los clientes HTTP.
  - Ajustar esquemas JSON de validación.

### Paso 6: Generación de la Bitácora de Cambios y Resumen JSON
- Consolidar en `mantenimiento-refactorizacion.md` el resumen de optimizaciones, tiempo ahorrado estimado y mejoras de estabilidad.
- Generar el JSON de resumen que cierra el ciclo de QA y deja la suite lista para el próximo release.

---

## 4. Plantilla de Salida (Bitácora de Mantenimiento Markdown)

```markdown
# Bitácora de Mantenimiento y Refactorización: [PROYECTO / SUITE]

## 1. Metadatos del Mantenimiento

| Atributo | Detalle |
| :--- | :--- |
| **Proyecto** | [Nombre del Proyecto] |
| **Tipo de Mantenimiento** | [Correctivo / Evolutivo / Optimización / Anti-Flakiness] |
| **Fecha de Ejecución** | [AAAA-MM-DD] |
| **Arquitecto de Mantenimiento** | Senior QA Automation Maintainability Architect |
| **Fuente de Requerimientos** | [Retrospectiva de Calidad / Ticket de Deuda Técnica] |
| **Framework Evaluado** | [Playwright / Selenium / Cypress / REST Assured] |

---

## 2. Resumen de Optimizaciones y Resultados

| Área de Mejora | Cantidad / Valor Previo | Cantidad / Valor Posterior | Impacto Logrado |
| :--- | :---: | :---: | :--- |
| **Selectores Frágiles Refactorizados** | [N] | 0 | Estabilidad 100% resiliente |
| **Casos Flaky Mitigados** | [N] | 0 | Reducción total de falsos positivos |
| **Pruebas Podadas / Deprecadas** | — | [N] | Eliminación de redundancia sin perder RTM |
| **Tiempo de Ejecución Estimado** | [X min] | [Y min] | Ahorro del [Z%] en tiempo de CI |

---

## 3. Detalle de Refactorizaciones Realizadas

### 3.1. Localizadores Estabilizados (Anti-Flakiness)
- `LoginPage`: Selector XPath absoluto reemplazado por `getByRole('button', { name: 'Sign In' })`.
- `PetCatalog`: Selector CSS por posición sustituido por `getByTestId('pet-item')`.

### 3.2. Poda de Pruebas y Consolidación (Test Pruning)
| ID Caso Deprecado | Nombre del Caso | Motivo de la Poda | Cobertura Alternativa |
| :---: | :--- | :--- | :--- |
| `TC-088` | [Prueba redundante] | Flujo cubierto al 100% por test de integración API | `tests/api/pet.api.spec.ts` |

### 3.3. Optimización de Estado y Autenticación
- Migración de login visual por UI a inyección de token de sesión vía fixture, ahorrando 3.5 segundos por test.

---

## 4. Verificación de Preservación de Cobertura RTM

- [x] Cobertura de casos `Must` / Críticos preservada al 100%.
- [x] Cero aserciones relajadas o debilitadas.
- [x] Suite verificada localmente y lista para el siguiente ciclo de pruebas.
```

---

## 5. Esquema de Salida JSON (`assets/mantenimiento-schema.json`)

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "MantenimientoResumen",
  "type": "object",
  "required": [
    "proyecto",
    "fecha",
    "tipo_mantenimiento",
    "metricas_mantenimiento",
    "selectores_refactorizados",
    "casos_podados",
    "cobertura_rtm_preservada",
    "ciclo_completado"
  ],
  "properties": {
    "proyecto": { "type": "string" },
    "fecha": { "type": "string", "pattern": "^\\d{4}-\\d{2}-\\d{2}$" },
    "tipo_mantenimiento": { "type": "string" },
    "metricas_mantenimiento": {
      "type": "object",
      "required": ["total_selectores_actualizados", "total_casos_podados", "porcentaje_ahorro_tiempo"],
      "properties": {
        "total_selectores_actualizados": { "type": "integer" },
        "total_casos_podados": { "type": "integer" },
        "porcentaje_ahorro_tiempo": { "type": "number", "minimum": 0, "maximum": 100 }
      }
    },
    "selectores_refactorizados": {
      "type": "array",
      "items": {
        "type": "object",
        "required": ["componente", "selector_anterior", "selector_nuevo"],
        "properties": {
          "componente": { "type": "string" },
          "selector_anterior": { "type": "string" },
          "selector_nuevo": { "type": "string" }
        }
      }
    },
    "casos_podados": {
      "type": "array",
      "items": {
        "type": "object",
        "required": ["id_caso", "motivo"],
        "properties": {
          "id_caso": { "type": "string", "pattern": "^TC-\\d{3}$" },
          "motivo": { "type": "string" }
        }
      }
    },
    "cobertura_rtm_preservada": { "type": "boolean" },
    "ciclo_completado": { "type": "boolean" }
  }
}
```
