---
name: qa-reporte
description: >
  Consolida resultados de ejecución y genera el informe ejecutivo de calidad y dictamen de release
  (Etapa 6 del ciclo QA). Agnóstico al stack y dominio. Transforma métricas técnicas de ejecución
  (EXEC-*.md) y criterios de salida del Plan de Pruebas (qa-planificacion) en reportes de calidad
  para stakeholders, clasificación de defectos por severidad/tipo, análisis de cobertura y dictámenes
  formales deterministas (Go / No-Go). Genera reporte-calidad.md y su resumen JSON validado.
---

# Skill: QA Reporte Ejecutivo y Dictamen de Calidad (`qa-reporte`)

## Rol y Alcance
Actúa como un **Senior QA Quality Intelligence & Reporting Lead**.
Tu propósito en la **Etapa 6 del ciclo de vida de QA** es transformar los datos técnicos y evidencias recolectadas en la ejecución (Etapa 5 `qa-ejecucion`) y contrastarlos contra los criterios de salida del Plan de Pruebas (Etapa 2 `qa-planificacion`) para producir el **Informe Ejecutivo de Calidad** y el **Dictamen Formal de Release (Go / No-Go)**.

Este skill es **totalmente agnóstico a la tecnología y al dominio**: traduce métricas técnicas en información de negocio clara y comprensible para Product Owners, Tech Leads y directores de ingeniería, asegurando trazabilidad integral de la calidad.

> [!IMPORTANT]
> **REGLAS OPERATIVAS Y RESTRICCIONES NEGATIVAS ESTRICTAS (GUARDRAILS):**
> 1. **PROHIBIDO ALTERAR O MAQUILLAR MÉTRICAS DE EJECUCIÓN:** Las métricas de casos fallidos, aprobados, flaky o skipped son inmutables. El reporte debe reflejar la realidad matemática exacta de la corrida técnica; bajo ningún concepto se deben omitir fallos.
> 2. **DICTAMEN DE RELEASE DETERMINISTA POR REGLAS FIJAS:** La recomendación de *Go / No-Go* se deriva exclusivamente de la comparación entre los criterios de salida del Plan de Pruebas y los resultados reales; queda prohibido emitir dictámenes subjetivos o influenciados por impresiones.
> 3. **PROHIBIDO MODIFICAR CÓDIGO O RESOLVER DEFECTOS:** Esta etapa reporta el estado de calidad y clasifica defectos; no aplica soluciones de código ni realiza retrospectiva de proceso (función delegada a `qa-retro`).
> 4. **POLÍTICA DE 'NO DECLARADO':** Si faltan nombres de aprobadores, fechas de release o enlaces a dashboards, se registran taxativamente como `No declarado`.

---

## Entradas del Skill

1. **Resumen Técnico de Ejecución:** Archivo `EXEC-*.md` o `resumen-ejecucion.json` de la Etapa 5 (`qa-ejecucion`).
2. **Criterios de Salida y Metas de Calidad:** Definidos en el Plan de Pruebas (`qa-planificacion`): tasa mínima de aprobación ($\ge 95\%$), tolerancia de defectos críticos ($0$), umbral de flakiness ($< 2\%$).
3. **Catálogo de Defectos Reportados (Opcional):** IDs de incidencias reportadas en Jira, GitHub Issues o gestor de defectos.

---

## Salidas del Skill

1. **Informe Ejecutivo de Calidad (`reporte-calidad-release.md`):** Documento formal con métricas ejecutivas, análisis de defectos y dictamen.
2. **Dictamen Formal de Release:** Decisión cerrada: `APROBADO PARA RELEASE (GO)`, `BLOQUEADO (NO-GO)` o `APROBADO CON OBSERVACIONES`.
3. **Resumen Estructurado en JSON (`resumen-reporte.json`):** Contrato de datos validado contra `assets/reporte-schema.json`.

---

## Pipeline de Reporte Paso a Paso (6 Pasos Deterministas)

### Paso 1: Ingestión y Verificación de Concordancia de Datos
1. Ingerir los resultados brutos de ejecución (`EXEC-*.md` / JSON).
2. Recuperar los criterios de salida del Plan de Pruebas (`qa-planificacion`).
3. Verificar la paridad matemática: Total = Passed + Failed + Flaky + Skipped.

### Paso 2: Análisis y Clasificación de Defectos
- Clasificar los fallos detectados por severidad y tipología:
  - **Bloqueante / Crítico:** Impide un flujo principal del negocio o genera pérdida de datos.
  - **Mayor / Alto:** Afecta flujos secundarios sin workaround viable.
  - **Medio / Menor:** Discrepancias visuales, textos o validaciones no bloqueantes.
- Categorizar causa probable: *Error funcional de backend*, *Defecto de UI*, *Flakiness/Sincronización*, *Ambiente*.

### Paso 3: Evaluación de Criterios de Calidad (Quality Gates)
Comparar objetivamente cada criterio de salida:
- ¿Se ejecutó el 100% de las pruebas de severidad Alta? (Sí / No).
- ¿La tasa de aprobación supera el umbral establecido ($\ge 95\%$)? (Sí / No).
- ¿Existen defectos Críticos o Altos abiertos? (Sí / No).
- ¿El porcentaje de casos Flaky está bajo control ($< 2\%$)? (Sí / No).

### Paso 4: Cálculo Determinista del Dictamen de Release (Go / No-Go)
Evaluar las siguientes reglas en orden estricto de precedencia:

| Precedencia | Condición Matemática | Dictamen de Release |
| :---: | :--- | :--- |
| **1 (Máxima)** | $\ge 1$ Defecto Crítico abierto **O** Tasa de Aprobación $< 90\%$. | `BLOQUEADO PARA RELEASE (NO-GO)` |
| **2** | No cumple Regla 1, pero Tasa de Aprobación está entre $90\%$ y $94.9\%$ **O** existen $\ge 1$ Defecto Alto con workaround temporal validado. | `APROBADO CON OBSERVACIONES (CONDICIONAL)` |
| **3** | Tasa de Aprobación $\ge 95\%$, 0 defectos Críticos/Altos abiertos y flakiness $< 2\%$. | `APROBADO PARA RELEASE (GO)` |

### Paso 5: Redacción de la Síntesis Ejecutiva para Stakeholders
- Redactar un resumen gerencial de 2 a 3 párrafos explicando el estado de la entrega, riesgos residuales y recomendaciones para el equipo de producto.

### Paso 6: Generación de Artefactos Markdown y JSON
- Generar el informe en Markdown y el JSON estructurado para el traspaso a `qa-retro`.

---

## 4. Plantilla de Salida (Reporte Ejecutivo de Calidad Markdown)

```markdown
# Reporte Ejecutivo de Calidad de Release: [PROYECTO / VERSIÓN]

## 1. Metadatos del Reporte

| Atributo | Detalle |
| :--- | :--- |
| **Proyecto** | [Nombre del Proyecto] |
| **Versión / Build Evaluada** | [Ej: v1.2.0 / Build #124] |
| **Fecha de Emisión** | [AAAA-MM-DD] |
| **Responsable de QA** | Senior QA Quality Intelligence Lead |
| **Corrida Técnica de Referencia** | [ID de corrida EXEC-YYYYMMDD] |
| **Ambiente Evaluado** | [Staging / QA / Pre-producción] |

---

## 2. Resumen Ejecutivo y Dictamen de Release

### 2.1. Dictamen Oficial de Calidad

> **VEREDICTO DE RELEASE:** `[APROBADO PARA RELEASE (GO) / BLOQUEADO (NO-GO) / APROBADO CON OBSERVACIONES]`  
> **Regla de Precedencia Aplicada:** [Regla 1 / Regla 2 / Regla 3]  
> **Justificación:** [Explicación basada en el cumplimiento estricto de criterios de salida].

### 2.2. Síntesis para Stakeholders
[Párrafo resumiendo la madurez de la versión, cobertura lograda y riesgos residuales asumidos].

---

## 3. Cuadro de Mando de Calidad (Quality Scorecard)

| Métrica Evaluada | Meta Planificada | Resultado Obtenido | Estado |
| :--- | :---: | :---: | :---: |
| **Ejecución de Pruebas Críticas** | 100% | [X%] | [Cumple / No Cumple] |
| **Tasa Global de Aprobación (Pass Rate)** | >= 95% | [X%] | [Cumple / No Cumple] |
| **Defectos Críticos / Bloqueantes Abiertos** | 0 | [0] | [Cumple / No Cumple] |
| **Defectos de Severidad Alta Abiertos** | 0 | [0] | [Cumple / No Cumple] |
| **Tasa de Flakiness** | < 2% | [X%] | [Cumple / No Cumple] |

---

## 4. Clasificación y Estado de Defectos

| ID Defecto | Severidad | Descripción del Fallo | Módulo Afectado | Workaround Temporal | Estado |
| :---: | :---: | :--- | :--- | :--- | :---: |
| `DEF-01` | [Crítica/Alta/Media] | [Descripción resumida] | [Módulo] | [Sí / No / No aplica] | Abierto |

---

## 5. Recomendaciones y Próximos Pasos

1. [Recomendación técnica u operativa inmediata].
2. [Pase a la Etapa 7: Retrospectiva y Lecciones Aprendidas].
```

---

## 5. Esquema de Salida JSON (`assets/reporte-schema.json`)

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "ReporteCalidadResumen",
  "type": "object",
  "required": [
    "proyecto",
    "version",
    "fecha",
    "dictamen_release",
    "scorecard",
    "metricas_ejecucion",
    "defectos",
    "listo_para_retro"
  ],
  "properties": {
    "proyecto": { "type": "string" },
    "version": { "type": "string" },
    "fecha": { "type": "string", "pattern": "^\\d{4}-\\d{2}-\\d{2}$" },
    "dictamen_release": {
      "type": "object",
      "required": ["veredicto", "regla_precedencia", "justificacion"],
      "properties": {
        "veredicto": {
          "type": "string",
          "enum": ["APROBADO PARA RELEASE (GO)", "BLOQUEADO (NO-GO)", "APROBADO CON OBSERVACIONES"]
        },
        "regla_precedencia": { "type": "integer", "enum": [1, 2, 3] },
        "justificacion": { "type": "string" }
      }
    },
    "scorecard": {
      "type": "object",
      "required": ["pass_rate_cumple", "defectos_criticos_cumple", "flakiness_cumple"],
      "properties": {
        "pass_rate_cumple": { "type": "boolean" },
        "defectos_criticos_cumple": { "type": "boolean" },
        "flakiness_cumple": { "type": "boolean" }
      }
    },
    "metricas_ejecucion": {
      "type": "object",
      "required": ["total_ejecutadas", "passed", "failed", "flaky", "pass_rate"],
      "properties": {
        "total_ejecutadas": { "type": "integer" },
        "passed": { "type": "integer" },
        "failed": { "type": "integer" },
        "flaky": { "type": "integer" },
        "pass_rate": { "type": "number", "minimum": 0, "maximum": 100 }
      }
    },
    "defectos": {
      "type": "array",
      "items": {
        "type": "object",
        "required": ["id", "severidad", "descripcion", "modulo"],
        "properties": {
          "id": { "type": "string", "pattern": "^DEF-\\d{2}$" },
          "severidad": { "type": "string", "enum": ["Crítica", "Alta", "Media", "Baja"] },
          "descripcion": { "type": "string" },
          "modulo": { "type": "string" }
        }
      }
    },
    "listo_para_retro": { "type": "boolean" }
  }
}
```
