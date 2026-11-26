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
2. **>= 95%** de pruebas totales con resultado Satisfactorio (Pass).
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
