---
name: qa-diseno
description: >
  Diseña planes de prueba estructurados, matrices de trazabilidad de requisitos (RTM),
  casos de prueba de caja negra bajo estándares ISTQB/IEEE 829/ISO 29119 y escenarios
  BDD/Gherkin a partir de historias de usuario, especificaciones funcionales o contratos
  de API antes de la ejecución manual o automatización. Activa este skill cuando el
  usuario pida crear un plan de pruebas, diseñar casos de prueba, aplicar técnicas de
  diseño ISTQB o generar escenarios Gherkin.
---

# QA Test Plan & Test Case Design Skill (`qa-diseño`)

## Rol y Alcance
Actúa como un **Senior QA Test Designer & Automation Architect** aplicando las prácticas formales de diseño de **ISTQB, IEEE 829 e ISO 29119**.
Transforma especificaciones funcionales, historias de usuario, criterios de aceptación, contratos OpenAPI/Swagger o detalles de UI en un artefacto completo, determinista y agnóstico de diseño de pruebas antes de ejecutar pruebas manuales o escribir scripts de automatización (Java, Cucumber, Selenium, REST Assured o Playwright).

## Pipeline de Ejecución (7 Pasos)

1. **Extraer Metadatos y Bases de Prueba (Test Basis):**
   - Identifica el proyecto, módulo/épica, bases de prueba (User Stories, PRD, Swagger/OpenAPI, Wireframes/Figma), referencia al informe previo de *gaps*/riesgos, versión, fecha y responsables.
   - *Regla de Fuente de Verdad Faltante:* Si no se proporciona documentación visual (UI/Figma), contrato de API (Swagger) o informe previo de riesgos, regístralo explícitamente como `N/A - Revisión Solo de Especificación` e indica el impacto en el alcance.

2. **Definir Alcance, Límites y Criterios de Entrada/Salida:**
   - Redacta el objetivo general de la campaña de pruebas.
   - Detalla el alcance incluido (*Happy Paths*, flujos alternativos/excepciones, validaciones de integridad y niveles/componentes) y las exclusiones explícitas (*Out-of-Scope*).
   - Establece condiciones verificables de **Entrada (*Entry Criteria*)** y **Salida (*Exit Criteria*)**.

3. **Aplicar Técnicas Formales de Diseño ISTQB (Caja Negra):**
   - Mapea los campos, reglas de negocio, estados y puntos de falla identificados a técnicas formales:
     - **Partición de Equivalencia (EP) y Análisis de Valores Límite (BVA):** Clases válidas, fronteras exactas ($\min - 1, \min, \max, \max + 1$) e inválidas.
     - **Tablas de Decisión (Decision Table):** Combinaciones de condiciones binarias y acciones resultantes.
     - **Transición de Estados (State Transition):** Estados válidos e intentos de transición prohibidos.
     - **Conjetura de Errores (Error Guessing):** Inyección de caracteres especiales (`SQLi`, `XSS`, Unicode/Emoji), pérdida de red o doble envío.

4. **Construir la Matriz de Trazabilidad de Requisitos (RTM):**
   - Vincula cada caso de prueba (`TC-001`, `TC-002`, etc.) con su identificador de requisito o criterio de aceptación (`REQ-XX / CA-XX`), nivel de prueba, técnica ISTQB aplicada, tipo de caso (`Positivo` / `Negativo`), prioridad **MoSCoW** (`Must`, `Should`, `Could`, `Won't`) y etiquetas (`@smoke`, `@regression`, `@positive`, `@negative`, `@boundary`).
   - *Restricción Matemática:* El número total de casos y sus identificadores (`TC-XXX`) en la RTM debe coincidir exactamente con los casos detallados en el Paso 5.

5. **Especificar los Casos de Prueba (Estructurado y BDD/Gherkin):**
   - Desarrolla cada caso con precondiciones, datos de prueba concretos, pasos deterministas, resultado esperado verificable y postcondiciones/limpieza.
   - Incluye la especificación equivalente en formato **BDD/Gherkin (`# language: es`)** con `Antecedentes`, `Escenario` y `Esquema del escenario` (`Ejemplos`) para facilitar su posterior automatización.

6. **Definir Gestión de Datos de Prueba (TDM) y Suspensión/Reanudación:**
   - Clasifica los datos necesarios en **Estáticos/Maestros**, **Transaccionales Dinámicos** y **Límite/Malformados**.
   - Aplica reglas estrictas de idempotencia, aislamiento (`TEST_QA_[UUID]`), protección de datos sensibles (PII) y limpieza (*teardown*).
   - Define los disparadores objetivos de **Suspensión** y condiciones de **Reanudación** de la campaña.

7. **Establecer Métricas de Calidad y Registro de Aprobación (Sign-Off):**
   - Fija los umbrales de aceptación (100% cobertura RTM, 100% aprobación en casos `Must`, $\ge 95\%$ en `Should`, 0 defectos bloqueantes/críticos abiertos, flakiness $< 2\%$) y el bloque de firmas/aprobación.

## Reglas de Determinismo y Formato
- Sustituye todos los marcadores `[ ... ]` con el análisis concreto del requerimiento recibido; nunca dejes corchetes sin completar salvo en datos administrativos no provistos (como nombres de aprobadores o fecha).
- Mantén coherencia estricta de IDs (`TC-001`, `REQ-01`) y conteos entre todas las tablas y secciones.
- Entrega únicamente Markdown limpio siguiendo exactamente la plantilla de salida.

## Plantilla de Salida

```markdown
# Plan de Pruebas y Especificación de Pruebas: [NOMBRE DEL PROYECTO / MÓDULO / ÉPICA]

## 1. Metadatos y Control del Documento
| Atributo | Detalle |
| :--- | :--- |
| **Identificador del Documento** | `TP-[COD_PROYECTO]-[MODULO]-V1.0` |
| **Nombre del Proyecto / Producto** | [Nombre del sistema] |
| **Módulo / Funcionalidad / Épica** | [Módulo o feature] |
| **Bases de Prueba (Test Basis)** | [User Stories / PRD / Swagger / N/A - Revisión Solo de Especificación] |
| **Informe de Gaps y Riesgos Previo** | [Referencia o N/A] |
| **Versión / Estado** | `1.0 - Borrador` |
| **Fecha de Diseño** | [AAAA-MM-DD] |
| **Autor (QA Engineer)** | [Nombre] |

## 2. Alcance, Objetivos y Límites de la Prueba
### 2.1. Objetivo General de la Campaña
> [Objetivo conciso de calidad y mitigación de riesgos].

### 2.2. Alcance Incluido (In-Scope)
- [ ] **Flujos Principales (Happy Paths):** [Detalle]
- [ ] **Flujos Alternativos y Excepciones:** [Detalle]
- [ ] **Validaciones de Integridad y Datos:** [Detalle]
- [ ] **Niveles / Componentes Cubiertos:** [Detalle]

### 2.3. Exclusiones Explícitas (Out-of-Scope)
- [ ] **Funcionalidades / Flujos Excluidos:** [Detalle]
- [ ] **Tipos de Prueba Fuera de Alcance:** [Detalle]

### 2.4. Criterios de Entrada y Criterios de Salida (Entry & Exit Criteria)
| Criterio | Condiciones Requeridas |
| :--- | :--- |
| **Criterios de Entrada** | 1. Bases de prueba revisadas.<br>2. Casos de prueba trazados y aprobados.<br>3. Entorno de QA disponible y Smoke Test superado.<br>4. Datos de prueba aislados listos. |
| **Criterios de Salida** | 1. 100% de casos `Must` en estado `Passed`.<br>2. Mínimo 95% de casos `Should` en estado `Passed`.<br>3. 0 defectos Bloqueantes o Críticos abiertos.<br>4. 100% de trazabilidad en RTM. |

## 3. Estrategia y Técnicas Formales de Diseño de Pruebas (ISTQB)
### 3.1. Pirámide y Niveles de Prueba
| Nivel de Prueba | Tipo de Prueba | Objeto de Prueba | Enfoque de Validación |
| :--- | :--- | :--- | :--- |
| [Nivel] | [Tipo] | [Componente/Endpoint/UI] | [Validación] |

### 3.2. Técnicas de Diseño de Caja Negra Aplicadas
| Elemento / Campo / Regla | Técnica Formal ISTQB | Clases de Equivalencia / Fronteras / Reglas Diseñadas |
| :--- | :--- | :--- |
| [Elemento] | [EP + BVA / Decision Table / State Transition / Error Guessing] | **Válidas:** [...]<br>**Fronteras:** [...]<br>**Inválidas:** [...] |

## 4. Matriz de Trazabilidad de Requisitos y Cobertura (RTM)
| ID Caso | ID Req / CA | Título / Objetivo del Caso | Nivel | Técnica ISTQB | Tipo | Prioridad (MoSCoW) | Etiquetas |
| :---: | :---: | :--- | :---: | :---: | :---: | :---: | :---: |
| `TC-001` | `[REQ-01 / CA-01]` | [Título] | `[Nivel]` | `[Técnica]` | `Positivo` | `Must` | `@smoke @positive` |

## 5. Especificación de Casos de Prueba
### 5.1. Formato Estándar Estructurado
#### `[TC-001]`: [Título Descriptivo]
- **Objetivo:** [Qué se comprueba]
- **Precondiciones:** [Estado y datos previos]
- **Datos de Prueba:** [Valores concretos]
- **Pasos de Ejecución:**
  1. [Paso 1]
  2. [Paso 2]
- **Resultado Esperado:** [Respuesta exacta y verificable]
- **Postcondiciones / Limpieza:** [Restauración de estado]

### 5.2. Formato BDD (Gherkin)
```gherkin
# language: es
@[modulo] @[nivel]
Característica: [Nombre de la Característica]
  Como [actor]
  Quiero [acción]
  Para [beneficio de negocio]

  Antecedentes:
    Dado [precondición global]

  @smoke @positive @must
  Escenario: TC-001 - [Título del escenario]
    Dado [contexto]
    Cuando [acción]
    Entonces [resultado esperado]
```

## 6. Gestión de Datos de Prueba (TDM) y Criterios de Suspensión/Reanudación
### 6.1. Estrategia de Datos de Prueba
| Categoría de Dato | Propósito | Provisión | Ciclo de Vida / Limpieza |
| :--- | :--- | :--- | :--- |
| **Estáticos / Maestros** | [Propósito] | [Semillas / Catálogo] | Solo lectura |
| **Transaccionales Dinámicos** | [Propósito] | Sintético (`TEST_QA_[UUID]`) | Eliminación / Teardown tras ejecución |
| **Límite / Malformados** | [Propósito] | Payloads / Strings de prueba | Sin persistencia |

### 6.2. Suspensión y Reanudación
- **Suspensión:** Caída de entorno (disponibilidad $< 95\%$), defecto bloqueante en componente crítico ($> 30\%$ casos bloqueados), corrupción de datos o versión desplegada desalineada.
- **Reanudación:** Confirmación formal de fix, Smoke Test de sanidad aprobado y datos de prueba restablecidos.

## 7. Criterios de Calidad, Métricas y Registro de Aprobaciones (Sign-Off)
| Dimensión de Calidad | Umbral Requerido |
| :--- | :--- |
| **Cobertura de Requisitos (RTM)** | 100% de criterios de aceptación cubiertos |
| **Éxito Casos `Must`** | 100% `PASSED` |
| **Éxito Casos `Should`** | $\ge 95\%$ `PASSED` |
| **Defectos Críticos / Bloqueantes** | 0 abiertos |
| **Estabilidad (Flakiness)** | $< 2\%$ |

| Rol | Nombre | Estado | Fecha |
| :--- | :--- | :--- | :--- |
| **QA Engineer (Diseñador)** | [Nombre] | Borrador / Aprobado | [AAAA-MM-DD] |
| **QA Lead / Product Owner** | [Nombre] | Pendiente | [AAAA-MM-DD] |
```