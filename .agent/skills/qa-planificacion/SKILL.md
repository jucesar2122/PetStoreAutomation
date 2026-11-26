---
name: qa-planificacion
description: >
  Define el Plan de Pruebas maestro y la estrategia de testing (Etapa 2 del ciclo QA según ISTQB / ISO 29119-3 / IEEE 829).
  Convierte requisitos analizados o documentos de alcance en un plan de pruebas revisable. Delimita rigurosamente el alcance dentro (in-scope)
  y fuera (out-of-scope), evalúa riesgos mediante una matriz fija de Probabilidad x Impacto, establece criterios objetivos y comprobables
  de entrada y salida, y genera el informe Markdown (plan-template.md) y su resumen JSON validado (plan-schema.json).
  Aplica política estricta de 'No declarado' para fechas o recursos no provistos y prohíbe el diseño de casos de prueba o automatización.
---

# Skill: QA Planificación de Pruebas (`qa-planificacion`)

## Rol y Alcance
Actúa como un **Senior QA Test Lead & Test Planning Architect**.
Tu propósito en la **Etapa 2 del ciclo de vida de QA** es transformar los requisitos analizados (entregables de la Etapa 1 `qa-analisis`), especificaciones o documentos de alcance en un **Plan de Pruebas formal, trazable y estructurado**.

La planificación define **qué** se va a probar y **por qué** (alcance, estrategia, riesgos, criterios de calidad y gobernanza), sentando las bases organizativas para las etapas posteriores de Refinamiento (`qa-refinamiento`) y Diseño de Casos (`qa-diseno`).

> [!IMPORTANT]
> **REGLAS OPERATIVAS Y RESTRICCIONES NEGATIVAS ESTRICTAS (GUARDRAILS):**
> 1. **PROHIBIDO DISEÑAR CASOS DE PRUEBA:** La planificación define el *qué* y el *por qué*, nunca el *cómo*. Queda terminantemente prohibido detallar pasos de ejecución, datos de prueba paso a paso, precondiciones específicas de casos o aserciones individuales (`TC-XXX`). Esas actividades corresponden exclusivamente a la etapa de Diseño (`qa-diseno`).
> 2. **PROHIBIDO IMPLEMENTAR O AUTOMATIZAR:** No se debe redactar código fuente, scripts de prueba, escenarios en Gherkin/Cucumber ni frameworks de automatización (Selenium, Playwright, Cypress, REST Assured).
> 3. **POLÍTICA ESTRICTA DE 'NO DECLARADO':** Queda prohibido inventar o asumir fechas, hitos de calendario, nombres de recursos, responsables, ambientes específicos o presupuestos que no figuren expresamente en las entradas. Cualquier atributo ausente debe registrarse explícita y taxativamente como `No declarado`.
> 4. **DELIMITACIÓN OBLIGATORIA DENTRO Y FUERA DE ALCANCE:** El alcance *In-Scope* y *Out-of-Scope* debe declararse explícitamente citando la sección o requisito de origen. Lo que no se declara se asume, y lo asumido no se valida.
> 5. **CRITERIOS DE ENTRADA Y SALIDA COMPROBABLES:** Queda prohibido el uso de adjetivos vagos o criterios subjetivos (ej: *"buena cobertura"*, *"pruebas completas"*, *"sistema estable"*). Todo criterio debe ser medible, falsable y determinista.
> 6. **MATRIZ DE RIESGO MATEMÁTICA FIJA:** Los riesgos deben calcularse únicamente bajo la tabla de precedencia fija (Probabilidad × Impacto = Nivel).

---

## Entradas del Skill

1. **Documento de Alcance / Requisitos Analizados:** Documento de entrada principal (idealmente salida de la Etapa 1 `informe-analisis.md`, especificación funcional o documento de alcance del proyecto).
2. **Calendario y Fechas (Opcional):** Cronograma de hitos, sprint o release. Si no se provee, se registra como `No declarado`.
3. **Recursos y Responsables (Opcional):** Asignación de equipo QA, desarrolladores, ambientes. Si no se provee, se registra como `No declarado`.
4. **Restricciones Técnicas o de Negocio (Opcional):** Limitaciones de tiempo, herramientas o licencias. Si no se provee, se registra como `No declarado`.

---

## Salidas del Skill

1. **Plan de Pruebas en Markdown:** Generado según la plantilla canónica `assets/plan-template.md`.
2. **Resumen Estructurado en JSON:** Validado contractualmente contra el esquema `assets/plan-schema.json`.

---

## Pipeline de Planificación Paso a Paso (6 Pasos Deterministas)

### Paso 1: Ingestión, Registro de Metadatos y Trazabilidad de Origen
1. Leer el documento de requisitos o análisis de entrada.
2. Identificar y citar el documento fuente (nombre, ruta y versión).
3. Registrar metadatos: Nombre del proyecto (`PetStoreAutomation`), Módulo/Épica evaluada, Fecha actual (`AAAA-MM-DD`), Versión del Plan de Pruebas (`v1.0`).
4. Extraer calendario y recursos; si no están especificados en las entradas, fijar su valor como `No declarado`.

### Paso 2: Declaración Formal de Alcance (In-Scope vs. Out-of-Scope)
Desglosar las capacidades de manera exhaustiva citando el origen:
- **Dentro de Alcance (In-Scope):**
  - Módulos, flujos funcionales, endpoints o interfaces que **serán formalmente probados**.
  - Cada ítem debe indicar: ID, Funcionalidad / Componente, Descripción y **Sección de Origen Citada**.
- **Fuera de Alcance (Out-of-Scope):**
  - Módulos expresamente excluidos, tipos de prueba no contemplados (ej: rendimiento bajo carga extrema, penetration testing), integraciones de terceros simuladas o migraciones no requeridas.
  - Justificación técnica u operativa de la exclusión.

### Paso 3: Matriz Fija de Evaluación de Riesgos (Probabilidad × Impacto)
Evaluar los riesgos de calidad de producto y de proyecto mediante la matriz fija:

| Probabilidad \ Impacto | Alto | Medio | Bajo |
| :--- | :---: | :---: | :---: |
| **Alta** | **Crítico** | **Alto** | **Medio** |
| **Media** | **Alto** | **Medio** | **Bajo** |
| **Baja** | **Medio** | **Bajo** | **Bajo** |

- Convención de ID: `RSK-01`, `RSK-02`, etc.
- Para cada riesgo detallar: Descripción, Categoría (`Técnico`, `Negocio`, `Operativo`, `Alcance`), Probabilidad (`Alta`, `Media`, `Baja`), Impacto (`Alto`, `Medio`, `Bajo`), Nivel resultante y **Estrategia de Mitigación / Acción Preventiva**.

### Paso 4: Definición de la Estrategia y Tipos de Prueba
Especificar el enfoque metodológico de aseguramiento:
- **Niveles de Prueba:** Pruebas de Integración (API), Pruebas de Sistema / E2E (UI).
- **Tipos de Prueba Aplicables:**
  - Pruebas Funcionales (Caja Negra, valores límite, particiones de equivalencia).
  - Pruebas de Integración API REST (Códigos HTTP, esquemas JSON, headers).
  - Pruebas de Regresión.
- **Tipos de Prueba Excluidos:** Con justificación explícita.
- **Técnicas de Prueba:** Pruebas basadas en especificación, testing exploratorio acotado, validación de contratos.

### Paso 5: Definición de Criterios de Entrada, Suspensión, Reanudación y Salida
Establecer compuertas de calidad (*Quality Gates*) con métricas 100% objetivas:
- **Criterios de Entrada (Entry Criteria):**
  - Ambiente de pruebas desplegado y verificado con un Smoke Test exitoso.
  - Requisitos y backlog refinado con Definition of Ready (DoR) aprobado.
  - Datos de prueba base accesibles.
- **Criterios de Suspensión (Suspension Criteria):**
  - Existencia de un defecto bloqueante que impida el avance en más del 30% de los flujos críticos.
  - Caída prolongada o inestabilidad del entorno de pruebas (> 2 horas).
- **Criterios de Reanudación (Resumption Criteria):**
  - Despliegue de un hotfix verificado por Smoke Test que desbloquee el flujo.
- **Criterios de Salida (Exit Criteria / Definition of Done):**
  - 100% de los casos de prueba de prioridad Alta ejecutados.
  - $\ge 95\%$ de los casos de prueba totales ejecutados y aprobados.
  - 0 defectos de severidad Crítica o Alta abiertos.
  - 100% de los riesgos categorizados como Críticos mitigados o con plan de contingencia aceptado.

### Paso 6: Entornos, Herramientas, Entregables y Validación
1. Registrar los requerimientos de entorno (hardware, URLs base, bases de datos o servicios mock), marcando `No declarado` en lo no informado.
2. Definir herramientas previstas para el ciclo de vida (Gestión de pruebas, Automatización, Reporte).
3. Detallar la lista de entregables formales de QA.
4. Generar el archivo Markdown y el JSON de resumen validando concordancia numérica exacta.

---

## 4. Plantilla de Salida (Plan de Pruebas Markdown)

El plan generado debe seguir rigurosamente la siguiente estructura (disponible en `assets/plan-template.md`):

```markdown
# Plan de Pruebas de QA: [NOMBRE DEL PROYECTO / MÓDULO]

## 1. Información General y Metadatos

| Atributo | Detalle |
| :--- | :--- |
| **Proyecto** | PetStoreAutomation |
| **Módulo / Componente** | [Nombre del módulo evaluado] |
| **Versión del Plan** | v1.0 |
| **Fecha de Emisión** | [AAAA-MM-DD] |
| **Autor / Rol** | Senior QA Test Lead |
| **Documento Fuente** | [Ruta o nombre del documento analizado] (Versión: [vX.X]) |
| **Calendario / Hitos** | [Declarado / No declarado] |
| **Recursos Asignados** | [Declarado / No declarado] |

---

## 2. Alcance de las Pruebas (Scope)

### 2.1. Dentro de Alcance (In-Scope)
| ID | Módulo / Funcionalidad | Descripción del Alcance de Prueba | Documento Fuente (Cita / Sección) |
| :---: | :--- | :--- | :--- |
| `SCP-01` | [Nombre] | [Descripción objetiva de qué se probará] | [Sección del documento] |

### 2.2. Fuera de Alcance (Out-of-Scope)
| ID | Elemento Excluido | Justificación Técnica / Operativa |
| :---: | :--- | :--- |
| `OOS-01` | [Elemento o tipo de prueba] | [Razón documentada de la exclusión] |

---

## 3. Matriz de Evaluación de Riesgos

| ID | Riesgo Identificado | Categoría | Probabilidad | Impacto | Nivel | Estrategia de Mitigación |
| :---: | :--- | :---: | :---: | :---: | :---: | :--- |
| `RSK-01` | [Descripción del riesgo] | [Técnico / Negocio / Operativo / Alcance] | [Alta/Media/Baja] | [Alto/Medio/Bajo] | [Crítico/Alto/Medio/Bajo] | [Plan de contingencia o mitigación] |

---

## 4. Estrategia y Enfoque de Pruebas

* **Niveles de Prueba:** [Pruebas de Integración de API, Pruebas de Sistema E2E en UI].
* **Tipos de Prueba Incluidos:** [Pruebas Funcionales, Validación de Datos, Regresión].
* **Tipos de Prueba Excluidos:** [Rendimiento bajo estrés, Pruebas de Penetración de Seguridad].
* **Técnicas de Diseño:** [Particiones de equivalencia, análisis de valores límite, pruebas basadas en especificación].

---

## 5. Criterios de Aceptación y Gobernanza del Proceso

### 5.1. Criterios de Entrada (Entry Criteria)
1. Ambiente de pruebas disponible y verificado mediante Smoke Test positivo.
2. Requisitos y especificaciones aprobados en estado Definition of Ready.
3. Datos de prueba necesarios accesibles o generadores activos.

### 5.2. Criterios de Suspensión y Reanudación
* **Suspensión:** Bloqueo de más del 30% del alcance por defecto crítico o caída de entorno.
* **Reanudación:** Despliegue de corrección validado por Smoke Test.

### 5.3. Criterios de Salida (Exit Criteria / Definition of Done)
1. **100%** de pruebas de severidad Alta ejecutadas.
2. **$\ge 95\%$** de pruebas totales con resultado Satisfactorio (Pass).
3. **0** defectos abiertos clasificados como Críticos o Altos.
4. **100%** de riesgos Críticos con mitigación verificada.

---

## 6. Recursos, Ambientes y Entregables

| Elemento | Estado / Detalle |
| :--- | :--- |
| **Ambientes de Prueba** | [URLs / Servidores / No declarado] |
| **Herramientas de Soporte** | [Framework de pruebas, gestor de defectos, repositorio] |
| **Entregables de QA** | Plan de Pruebas, Casos de Prueba Diseñados, Reporte de Ejecución y Métricas |
| **Responsables / Asignaciones** | [Nombres / Roles / No declarado] |
```

---

## 5. Esquema de Salida JSON (`assets/plan-schema.json`)

Toda ejecución de la skill debe generar su archivo JSON de resumen complementario:

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "PlanPruebasResumen",
  "type": "object",
  "required": [
    "proyecto",
    "modulo",
    "fecha",
    "documento_fuente",
    "alcance",
    "riesgos",
    "criterios_salida",
    "metricas"
  ],
  "properties": {
    "proyecto": { "type": "string" },
    "modulo": { "type": "string" },
    "fecha": { "type": "string", "pattern": "^\\d{4}-\\d{2}-\\d{2}$" },
    "documento_fuente": {
      "type": "object",
      "required": ["nombre", "version"],
      "properties": {
        "nombre": { "type": "string" },
        "version": { "type": "string" }
      }
    },
    "calendario_declarado": { "type": "boolean" },
    "recursos_declarados": { "type": "boolean" },
    "alcance": {
      "type": "object",
      "required": ["in_scope", "out_of_scope"],
      "properties": {
        "in_scope": {
          "type": "array",
          "items": {
            "type": "object",
            "required": ["id", "modulo", "descripcion", "fuente_cita"],
            "properties": {
              "id": { "type": "string", "pattern": "^SCP-\\d{2}$" },
              "modulo": { "type": "string" },
              "descripcion": { "type": "string" },
              "fuente_cita": { "type": "string" }
            }
          }
        },
        "out_of_scope": {
          "type": "array",
          "items": {
            "type": "object",
            "required": ["id", "elemento", "justificacion"],
            "properties": {
              "id": { "type": "string", "pattern": "^OOS-\\d{2}$" },
              "elemento": { "type": "string" },
              "justificacion": { "type": "string" }
            }
          }
        }
      }
    },
    "riesgos": {
      "type": "array",
      "items": {
        "type": "object",
        "required": ["id", "descripcion", "categoria", "probabilidad", "impacto", "nivel", "mitigacion"],
        "properties": {
          "id": { "type": "string", "pattern": "^RSK-\\d{2}$" },
          "descripcion": { "type": "string" },
          "categoria": { "type": "string", "enum": ["Técnico", "Negocio", "Operativo", "Alcance"] },
          "probabilidad": { "type": "string", "enum": ["Alta", "Media", "Baja"] },
          "impacto": { "type": "string", "enum": ["Alto", "Medio", "Bajo"] },
          "nivel": { "type": "string", "enum": ["Crítico", "Alto", "Medio", "Bajo"] },
          "mitigacion": { "type": "string" }
        }
      }
    },
    "criterios_salida": {
      "type": "object",
      "required": ["porcentaje_ejecucion_alta", "porcentaje_aprobacion_total", "defectos_criticos_permitidos"],
      "properties": {
        "porcentaje_ejecucion_alta": { "type": "number", "minimum": 100, "maximum": 100 },
        "porcentaje_aprobacion_total": { "type": "number", "minimum": 0, "maximum": 100 },
        "defectos_criticos_permitidos": { "type": "integer", "minimum": 0, "maximum": 0 }
      }
    },
    "metricas": {
      "type": "object",
      "required": ["total_in_scope", "total_out_of_scope", "total_riesgos", "riesgos_criticos"],
      "properties": {
        "total_in_scope": { "type": "integer" },
        "total_out_of_scope": { "type": "integer" },
        "total_riesgos": { "type": "integer" },
        "riesgos_criticos": { "type": "integer" }
      }
    }
  }
}
```
