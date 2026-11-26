# Etapa 1: Análisis de requerimientos

**Skill a diseñar:** `qa-analisis-requerimientos` (o el nombre que elijas, en el mismo formato).

## Objetivo

Revisar un documento de requisitos antes de planificar o diseñar pruebas. Detecta gaps, ambigüedades y riesgos, y entrega un veredicto de calidad con preguntas para el Product Owner.

## Entradas

- Ruta a un documento de requisitos en Markdown.  
- Si existe, `context.md` del proyecto (negocio, actores, vocabulario, flujos principales). Se lee antes de analizar. Si no existe, se registra `No declarado`.  
- Opcional: fuente de UI en modo lectura (URL, capturas o notas de inspección).

Ejemplo de referencia completo: `ejemplos/qa-analisis-requerimientos/`.

## Salidas

- Informe de análisis en Markdown: gaps, riesgos, ambigüedades, veredicto y preguntas abiertas.  
- JSON resumen con esquema.

## Reglas específicas

- Un requisito ausente o incompleto es un gap. Una frase que admite varias lecturas o se contradice con otra sección es una ambigüedad. Motivo: sin esta separación, la misma situación se clasifica distinto en cada ejecución.  
- La UI se observa solo en modo lectura. No se escribe en campos, no se envían formularios y no se crean datos. Motivo: la revisión no debe alterar el entorno.  
- Lo que el documento no declara se marca como `No declarado`, nunca se completa con suposiciones. Motivo: un dato supuesto se convierte en requisito sin que nadie lo valide.  
- El veredicto sale de reglas de precedencia fijas, no de una impresión. Motivo: dos revisiones del mismo documento deben dar el mismo veredicto.  
- El análisis no diseña casos de prueba ni ejecuta pruebas. Motivo: esas son las etapas siguientes.

## Errores frecuentes

- Clasificar como ambigüedad un requisito que falta.  
- Dar por cumplido un requisito que la UI contradice sin citar la evidencia.  
- Completar un valor (formato, límite, mensaje de error) que el documento no indica.

## Criterio de salida de la etapa

- [ ] Cada gap, riesgo y ambigüedad con evidencia (cita, sección o observación de UI).  
- [ ] Veredicto calculado con las reglas de precedencia declaradas.  
- [ ] Ningún dato inventado; lo ausente figura como `No declarado`.  
- [ ] Plantilla y esquema creados dentro de la carpeta de la skill, porque esta etapa no tiene carpeta en `referencias-etapas/`.