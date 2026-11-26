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
- **Mensaje de Error:** `Error: Timed out 5000ms waiting for locator(...)`
- **Ubicación en Código:** `tests/e2e/[spec].ts:42`
- **Artefactos Recolectados:**
  - Captura: `evidencias/screenshots/TC-003-failure.png`
  - Traza Playwright: `evidencias/traces/TC-003-trace.zip`
