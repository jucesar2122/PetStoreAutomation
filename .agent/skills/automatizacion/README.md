# Framework para diseñar skills del ciclo de QA

Este framework sirve para diseñar skills de QA que cumplan el estándar del curso y los criterios de Skill Quality Assurance Framework (SQAF). Cada skill cubre una etapa del ciclo: planificación, diseño, implementación, ejecución, reporte, retrospectiva o mantenimiento.

## Qué hace cada parte

| Parte | Quién la usa | Para qué |
| :---- | :---- | :---- |
| `01-estandar-comun.md` | Tú | Reglas que toda skill debe cumplir, con su motivo. |
| `etapas/*.md` | Tú | Ficha de tu etapa: entradas, salidas, reglas específicas y criterio de salida. |
| `skill-plantilla/SKILL.md` | El agente | Esqueleto que el agente copia y completa. |
| `referencias-etapas/` | El agente | Plantillas y esquemas de salida de cada etapa. |
| `herramientas/check_skill.py` | Tú, al final | Comprueba estructura. Sin modelos. |
| `rubrica-autoevaluacion.md` | Tú, con el agente | Lista de comprobación para decidir si la skill está lista. |

**Importante:** el agente escribe la skill, pero no decide si está bien. Tú verificas con el comprobador, la rúbrica y SQAF.

## Antes de empezar

Necesitas:

- Un agente CLI con acceso a archivos (por ejemplo, Claude Code o Gemini CLI), abierto en tu carpeta de trabajo.  
- Python 3, para ejecutar `check_skill.py`.  
- La carpeta `Skill-Quality-Assurance-Framework-SQAF/`, si vas a evaluar tu skill con SQAF.

Todas las rutas de estas instrucciones deben ser **absolutas**, empezando por la unidad (por ejemplo `C:\...`). Las rutas relativas no funcionan con SQAF.

## Estructura

```
Framework-Diseno-Skills-QA/
├── README.md                       este archivo
├── 01-estandar-comun.md            reglas comunes a toda skill
├── etapas/                         una ficha por etapa
│   ├── 01-analisis.md
│   ├── 02-planificacion.md
│   ├── 03-diseno.md
│   ├── 04-implementacion.md
│   ├── 05-ejecucion.md
│   ├── 06-reporte.md
│   ├── 07-retro.md
│   └── 08-mantenimiento.md
├── referencias-etapas/             plantillas y esquemas por etapa
│   └── 02-planificacion/assets/    hoy solo existe la etapa 2
├── skill-plantilla/SKILL.md        esqueleto de la skill
├── herramientas/check_skill.py     comprobador mecánico
└── rubrica-autoevaluacion.md       lista de comprobación final
```

## Flujo de trabajo

### Atajo: el asistente crea la carpeta por ti

Si prefieres no escribir rutas a mano, ejecuta el asistente. Pregunta la etapa, el nombre de la skill y la ruta de tu proyecto, crea la carpeta y te muestra la instrucción lista para pegar en tu agente.

```
python "C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA\herramientas\nueva_skill.py"
```

**Ejemplo de sesión:**

```
=== Nueva skill de QA ===

Etapas disponibles:
  1. analisis
  2. planificacion
  3. diseno
  4. implementacion
  5. ejecucion
  6. reporte
  7. retro
  8. mantenimiento

Número de etapa: 2
Nombre de la skill: qa-planificacion
Ruta del proyecto donde trabajas (ejemplo: C:\Users\Alumno\Proyectos\mi-app): C:\Users\Alumno\Proyectos\mi-app

Se creará: C:\Users\Alumno\Proyectos\mi-app\skills\qa-planificacion
¿Continuar? (s/n): s

Skill creada en: C:\Users\Alumno\Proyectos\mi-app\skills\qa-planificacion

--- Copia esta instrucción en tu agente CLI ---
(instrucción completa con las rutas ya rellenadas)
--- Fin de la instrucción ---
```

**Qué pasa en cada caso:**

| Situación | Resultado |
| :---- | :---- |
| Número de etapa fuera de la lista | Te pide otro número. |
| Nombre con mayúsculas, espacios o caracteres especiales | Te pide otro nombre. Ejemplo válido: `qa-planificacion`. |
| La ruta del proyecto no existe | Te pide otra ruta. |
| La carpeta `skills\<nombre>` ya existe | Se detiene y no toca nada. |
| Respondes `n` a la confirmación | Cancela y no crea nada. |

La skill se crea en `<tu proyecto>\skills\<nombre>\`. Si quieres otra ubicación, crea la carpeta padre antes o usa los pasos manuales de abajo.

Si ya conoces los datos, puedes pasarlos sin preguntas:

```
python "C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA\herramientas\nueva_skill.py" --etapa 2 --nombre qa-planificacion --proyecto "C:\Users\Alumno\Proyectos\mi-app"
```

El asistente no revisa el contenido de la skill. Después de crearla, sigue los pasos 4 a 7\.

### Paso 1\. Elige tu etapa y lee su ficha

Abre `etapas/` y elige la ficha de tu etapa. Por ejemplo, para planificación, `etapas/02-planificacion.md`. Léela completa. Su criterio de salida es lo que tu skill debe cumplir.

### Paso 2\. Lee el estándar común

Lee `01-estandar-comun.md`. Aplica a todas las etapas.

### Paso 3\. Pide la skill al agente

Copia la instrucción de abajo en tu agente. Antes, reemplaza **cada valor entre corchetes**. No dejes ninguno sin cambiar.

**Ejemplo completo para la etapa de planificación:**

```
Quiero crear una skill de QA para la etapa 2 (planificación) del ciclo de QA.

Sigue el framework en:
C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA

Lee primero estos archivos, en este orden:
1. C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA\01-estandar-comun.md
2. C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA\etapas\02-planificacion.md
3. C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA\skill-plantilla\SKILL.md
4. Los archivos de C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA\referencias-etapas\02-planificacion\assets\

Nombre de la skill: qa-planificacion
Carpeta padre donde crear la skill: C:\Users\Alumno\Documentos\mis-skills
La skill quedará en: C:\Users\Alumno\Documentos\mis-skills\qa-planificacion

Reglas:
- No modifiques ningún archivo dentro de Framework-Diseno-Skills-QA.
- Antes de escribir nada, pregúntame lo que no esté claro sobre entradas, salidas y criterios de éxito.
- Si no te doy un dato, escribe "No declarado". No lo inventes.
- Al terminar, ejecuta:
  python "C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA\herramientas\check_skill.py" "C:\Users\Alumno\Documentos\mis-skills\qa-planificacion"
  y muéstrame la salida completa.
- Después crea autoevaluacion.md dentro de C:\Users\Alumno\Documentos\mis-skills\qa-planificacion usando el formato de la sección "Formato de autoevaluacion.md" de este README.
```

**Qué reemplazar:**

| Valor | Qué poner | Ejemplo |
| :---- | :---- | :---- |
| Ruta del framework | Donde descargaste esta carpeta | `C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA` |
| Número y nombre de etapa | El de tu ficha en `etapas/` | `etapa 2 (planificación)` |
| Nombre de la skill | Minúsculas y guiones. Debe ser igual al nombre de la carpeta. | `qa-planificacion` |
| Carpeta padre | Donde quieres que se cree tu skill. No es la carpeta de la skill. | `C:\Users\Alumno\Documentos\mis-skills` |
| Archivos de lectura | Los de tu etapa en `etapas/` y en `referencias-etapas/`. | `etapas\02-planificacion.md` |

Si tu etapa no tiene carpeta en `referencias-etapas/` (hoy solo la 2 la tiene), quita el punto 4 de la lista de lectura. El agente debe crear sus propias plantillas dentro de la carpeta de tu skill, y explicar por qué.

**Qué debe generar el agente:** la carpeta `[nombre]` con `SKILL.md`, y si la etapa lo requiere, `references/` y `assets/`. Además, `autoevaluacion.md`.

### Paso 4\. Revisa lo que generó

Antes de ejecutar el comprobador, revisa:

- [ ] La carpeta contiene `SKILL.md`.  
- [ ] El campo `name` del frontmatter es igual al nombre de la carpeta.  
- [ ] La `description` dice qué hace, cuándo usarla y qué entrada requiere.  
- [ ] Cada regla importante tiene su motivo.  
- [ ] La salida tiene plantilla en `assets/`, y esquema JSON si aplica.  
- [ ] No hay datos inventados ni datos reales.

### Paso 5\. Ejecuta el comprobador

Desde cualquier carpeta, con la ruta absoluta de tu skill:

```
python "C:\Users\Alumno\Documentos\Framework-Diseno-Skills-QA\herramientas\check_skill.py" "C:\Users\Alumno\Documentos\mis-skills\qa-planificacion"
```

- **Código de salida 0:** todas las comprobaciones pasaron.  
- **Código de salida 1:** hay al menos un FAIL. Lee el campo `detail` del FAIL, corrígelo y vuelve a ejecutar.

Para saber el código de salida en PowerShell, ejecuta `echo $LASTEXITCODE` después del comando.

El comprobador revisa estructura: `name`, longitud de `description`, líneas de `SKILL.md`, rutas referenciadas y JSON válido. **No revisa contenido.** Una skill puede pasarlo y tener instrucciones confusas.

### Paso 6\. Completa la rúbrica

Abre `rubrica-autoevaluacion.md`. Marca un punto solo si puedes señalar la evidencia en tu archivo. Si no puedes, deja el punto sin marcar.

El resultado va en `autoevaluacion.md`, dentro de tu carpeta de skill (ver formato abajo).

### Paso 7\. Evalúa con SQAF

Usa la ruta absoluta del `SKILL.md` de tu skill, y la del coordinador de SQAF:

```
Assess the quality of the following skill: C:\Users\Alumno\Documentos\mis-skills\qa-planificacion\SKILL.md

Use as workflow coordinator: C:\Users\Alumno\Documentos\Skill-Quality-Assurance-Framework-SQAF\orchestrator.md

No eval.json is provided: run design review only and skip the eval-reviewer.
```

Este paso se ejecuta en un agente CLI que tenga SQAF disponible. El informe se genera en la carpeta `framework-assessment/` de SQAF.

Si añades un `eval.json` a tu skill, SQAF también evalúa la ejecución. Eso requiere casos con entrada y resultado esperado, y se hace en una etapa posterior.

## Formato de autoevaluacion.md

Usa exactamente esta estructura, para que cualquier revisor pueda comprobar cada punto:

```markdown
# Autoevaluación: [nombre de la skill]

## A. Comprobaciones mecánicas
- [x] name igual al nombre de la carpeta. Evidencia: salida de check_skill.py.
- [ ] description ≤ 1024 caracteres. Evidencia: ninguna, FAIL en el comprobador.

## B. Intent
- [x] description indica qué hace, cuándo usarla y qué entrada requiere. Evidencia: SKILL.md, frontmatter.

(repite con las secciones B, C, D y E de rubrica-autoevaluacion.md)

## Resultado
Puntos marcados: [número] de [total].
Puntos pendientes y motivo: [lista].
```

&nbsp;

Un punto marcado sin evidencia cuenta como no cumplido.

## Qué mide SQAF

| Revisor | Qué revisa | Estados |
| :---- | :---- | :---- |
| Intent | Metadatos, propósito y alcance | PASS, PARTIAL, FAIL |
| Instruction | Claridad, determinismo y consistencia | PASS, PARTIAL, FAIL |
| QA | Contexto, prevención de alucinaciones, contrato de salida, determinismo y testabilidad | PASS, PARTIAL, FAIL |
| Eval (solo con `eval.json`) | Aserciones sobre la ejecución | PASS o FAIL por aserción |

El estado lo asigna cada revisor según si la definición está completa. El score es una consecuencia, no un umbral.

**Meta del curso:** score de 90 % o más en los tres revisores de diseño, sin ERROR ni CRITICAL. Es una meta: los revisores son modelos de lenguaje y su puntuación puede variar entre ejecuciones.

## Errores frecuentes

- **`name` distinto de la carpeta.** El comprobador lo marca como FAIL.  
- **Descripción de más de 1024 caracteres.** Recórtala a lo que el agente necesita para decidir cuándo activar la skill.  
- **Plantilla o esquema citado que no existe.** Cada ruta mencionada en `SKILL.md` debe existir dentro de la carpeta de la skill.  
- **Reglas sin motivo.** Cada regla importante debe decir por qué existe.  
- **Términos subjetivos sin criterio.** Por ejemplo, "severidad alta" sin definir qué la hace alta.

## Estado actual del framework

- La etapa 2 (planificación) tiene plantilla y esquema en `referencias-etapas/02-planificacion/assets/`.  
- Las demás etapas describen sus salidas, pero aún no tienen plantilla ni esquema. El agente debe crearlos dentro de la carpeta de la skill.  
- El framework no se ha probado de punta a punta con estudiantes. El objetivo de 90 % no está validado.