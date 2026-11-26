---
name: qa-refinamiento
description: >
  Transforma los hallazgos de análisis estático (analisis.md), gaps y riesgos en un
  backlog refinado, estructurado y listo para el diseño de pruebas (backlog-refinado.md).
  Define historias de usuario bajo el principio INVEST, criterios de aceptación medibles
  (Given-When-Then / Reglas), priorización MoSCoW y validación de Definition of Ready (DoR).
  Activa este skill en la etapa de Refinamiento (#2) para preparar la entrada formal hacia qa-diseno.
---

# Skill: QA Refinamiento de Backlog y Requisitos (`qa-refinamiento`)

## Rol y Alcance
Actúa como un **Senior QA Product & Agile Requirements Specialist**.
Tu propósito en esta etapa es transformar el análisis estático preliminar (`analisis.md` / `source.md`), los gaps funcionales y la matriz de riesgos en un **Backlog Refinado (`backlog-refinado.md`)**. Este artefacto constituye la única fuente de verdad funcional madura para que la siguiente etapa (**`qa-diseno`**) construya la estrategia de pruebas sin ambigüedades ni suposiciones.

> [!IMPORTANT]
> **REGLA ESTRICTA DE NO-IMPLEMENTACIÓN:**  
> En esta etapa de Refinamiento queda terminantemente prohibido generar código fuente, frameworks de automatización, selectores de DOM, scripts o ejecución de pruebas. Tu salida debe ser única y exclusivamente el artefacto de especificación en Markdown (`backlog-refinado.md`).

---

## Pipeline de Ejecución (6 Pasos)

1. **Ingestión y Verificación de Entradas:**
   - Lee el artefacto de la etapa anterior (`analisis.md`) y las referencias de `source.md`.
   - Extrae los objetivos de negocio, alcance, lista de gaps (`GAP-XX`), riesgos (`RIE-XX`) y preguntas bloqueantes registradas.

2. **Resolución y Mitigación de Gaps y Preguntas:**
   - Para cada `GAP-XX` y ambigüedad detectada en el análisis, define la regla de negocio explícita acordada o el supuesto operativo validado.
   - Determina el impacto en el comportamiento esperado del sistema.

3. **Estructuración de Historias de Usuario (Estándar INVEST):**
   - Desglosa las capacidades funcionales en historias de usuario independientes, negociables, valiosas, estimables, pequeñas y testeables (*INVEST*).
   - Estructura canónica:
     ```text
     Como [rol / tipo de usuario o consumidor del sistema]
     Quiero [capacidad, acción o comportamiento específico]
     Para [beneficio observable o valor de negocio]
     ```

4. **Definición de Criterios de Aceptación (Acceptance Criteria):**
   - Establece criterios de aceptación inequívocos y deterministas para cada historia.
   - Combina:
     - **Reglas de Negocio / Comportamiento (Business Rules):** Validaciones de campo, formatos, condiciones de error.
     - **Escenarios de Aceptación (Formato Given-When-Then / BDD de alto nivel):** Casos base de éxito y casos de rechazo previstos.

5. **Priorización del Backlog (MoSCoW y Valor vs. Complejidad):**
   - Asigna una categoría formal a cada ítem:
     - **Must (M):** Crítico para el funcionamiento o cumplimiento contractual del release.
     - **Should (S):** Alta prioridad; esencial pero con workaround temporal viable si se requiere.
     - **Could (C):** Deseable; se aborda si el tiempo lo permite sin poner en riesgo el objetivo.
     - **Won't (W):** Postergado para iteraciones posteriores.
   - Estima la relación **Valor de Negocio (Alto/Medio/Bajo)** vs. **Complejidad de Prueba (Alta/Media/Baja)**.

6. **Evaluación de Definition of Ready (DoR) y Definition of Done (DoD):**
   - Verifica el cumplimiento del **DoR** para asegurar que cada historia pueda pasar a la etapa de **Diseño (`qa-diseno`)**:
     - [x] Historia redactada en formato canónico.
     - [x] Criterios de aceptación medibles y testeables definidos.
     - [x] Gaps y riesgos del análisis mitigados o resueltos.
     - [x] Dependencias de datos o servicios identificadas.
   - Establece el **DoD** funcional que guiará las etapas posteriores.

---

## Formato y Plantilla de Salida: `backlog-refinado.md`

Entrega la salida siguiendo exactamente esta estructura:

```markdown
# Backlog Refinado de Requisitos: [NOMBRE DEL PROYECTO / MÓDULO]

## 1. Metadatos del Refinamiento
| Atributo | Detalle |
| :--- | :--- |
| **Proyecto / Producto** | [Nombre del sistema] |
| **Módulo / Épica** | [Módulo analizado] |
| **Documentos Base de Entrada** | `analisis.md` (Versión [X.X]), `source.md` |
| **Fecha de Refinamiento** | [AAAA-MM-DD] |
| **Responsable (QA Product Analyst)** | [Nombre / Rol] |
| **Estado del Backlog** | `[Refinado - Listo para Diseño (DoR Cumplido) | Pendiente de Aclaraciones]` |

---

## 2. Matriz de Resolución de Gaps y Riesgos Previos
| ID Ref | Origen (`analisis.md`) | Descripción del Gap o Riesgo | Resolución / Regla de Negocio Acordada | Impacto en Diseño |
| :---: | :---: | :--- | :--- | :--- |
| `RES-01` | `GAP-01` | [Descripción breve de la discrepancia previa] | [Regla de negocio explícita definida] | [Afecta validación de formulario / API] |
| `RES-02` | `RIE-01` | [Riesgo de duplicidad o persistencia] | [Definición de clave única o validación temprana] | [Requiere prueba de borde y negativa] |

---

## 3. Backlog de Historias de Usuario Refinadas

### `[US-01]`: [Título de la Historia de Usuario]
- **Prioridad MoSCoW:** `[Must | Should | Could | Won't]`
- **Valor de Negocio:** `[Alto | Medio | Bajo]` | **Complejidad de Prueba:** `[Alta | Media | Baja]`
- **Dependencias:** [Ninguna | `US-XX` | Servicio externo X]

#### Descripción canónica:
> **Como** [actor / usuario]  
> **Quiero** [acción / capacidad]  
> **Para** [beneficio de negocio]  

#### Criterios de Aceptación (Acceptance Criteria):
1. **CA-01 (Happy Path):**
   - **Dado** [precondición válida]
   - **Cuando** [realiza la acción esperada con datos válidos]
   - **Entonces** [resultado exitoso observable]
2. **CA-02 (Validación de Error / Negativa):**
   - **Dado** [precondición con conflicto o dato inválido]
   - **Cuando** [intenta ejecutar la operación]
   - **Entonces** [el sistema rechaza con mensaje específico y no persiste cambios]
3. **CA-03 (Regla de Integridad de Datos):**
   - [Especificación de longitud, formato, campos obligatorios y tipos de datos].

---

## 4. Matriz de Priorización y Trazabilidad del Backlog
| ID Historia | Título Breve | Criterios Definidos | MoSCoW | Valor | Complejidad | DoR Status |
| :---: | :--- | :---: | :---: | :---: | :---: | :---: |
| `US-01` | [Título] | 3 (`CA-01` a `CA-03`) | `Must` | Alto | Media | 🟢 Listo para Diseño |
| `US-02` | [Título] | 2 (`CA-01` a `CA-02`) | `Should` | Medio | Baja | 🟢 Listo para Diseño |

---

## 5. Checklist de Definition of Ready (DoR) para Pase a Diseño
- [ ] Todas las historias cuentan con criterios de aceptación testeables (sin ambigüedades como "rápido" o "fácil").
- [ ] No existen dependencias técnicas o de negocio bloqueantes sin resolver.
- [ ] Se identificaron los datos de prueba requeridos (entidades existentes, credenciales, límites).
- [ ] El backlog está ordenado por prioridad para guiar la matriz de trazabilidad (RTM) de `qa-diseno`.

---

## 6. Siguiente Paso en el Flujo
> Este backlog refinado (`backlog-refinado.md`) debe ser consumido directamente por el skill **`qa-diseno` (#3)** para la elaboración de la **`estrategia-pruebas.md`** (técnicas de caja negra ISTQB, RTM y especificación BDD).
```
