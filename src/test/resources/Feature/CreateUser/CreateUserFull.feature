# language: es
@web @signup
Característica: Registro de Nueva Cuenta de Usuario (Sign Up)
  Como un usuario nuevo de JPetStore
  Quiero registrar mis credenciales, datos de contacto y preferencias
  Para disponer de una cuenta activa y poder comprar en la tienda

  Antecedentes:
    Dado que el usuario navega a la página de registro de PetStore

  # ---------------------------------------------------------------------------
  # FLUJOS POSITIVOS (HAPPY PATH)
  # ---------------------------------------------------------------------------

  @smoke @positive
  Escenario: TC-SIGNUP-01 - Registro exitoso con valores por defecto en preferencias de perfil
    Cuando ingresa un User ID único de 10 caracteres alfanuméricos
    Y completa una contraseña válida con mayúsculas, minúsculas, números y caracteres especiales
    Y confirma la contraseña exactamente igual
    Y completa los datos personales con nombre "Carlos", apellido "Tester" y un email único
    Y completa el teléfono con un número válido de 9 dígitos "+123456789"
    Y completa la dirección obligatoria "Av. Principal 123", ciudad "Bogotá", estado "Cundinamarca", zip "110111" y país "Colombia"
    Y mantiene las preferencias de perfil por defecto: idioma "english" y categoría "FISH"
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema redirige al usuario a la página de inicio "/catalog"
    Y la cabecera muestra el enlace "Sign In", confirmando que la sesión no fue iniciada automáticamente

  @regression @positive
  Escenario: TC-SIGNUP-02 - Registro exitoso con preferencias personalizadas y dirección complementaria
    Cuando ingresa un User ID único de 10 caracteres alfanuméricos
    Y completa una contraseña válida y su confirmación idéntica
    Y completa datos personales válidos con un email único
    Y completa el teléfono con 9 dígitos sin prefijo "987654321"
    Y completa la dirección principal "Calle 45 # 12-34" y la dirección complementaria "Apto 302"
    Y completa ciudad "Lima", estado "Lima", zip "15001" y país "Perú"
    Y selecciona el idioma "japanese" y la categoría favorita "DOGS"
    Y habilita las opciones "Enable MyList" y "Enable MyBanner"
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema redirige al usuario a la página de inicio "/catalog"
    Y la cuenta queda creada y lista para iniciar sesión

  # ---------------------------------------------------------------------------
  # FLUJOS DE EXCEPCIÓN: UNICIDAD Y DUPLICIDAD
  # ---------------------------------------------------------------------------

  @regression @negative
  Escenario: TC-SIGNUP-03 - Rechazo de registro por User ID duplicado de forma insensible a mayúsculas
    Dado que existe una cuenta registrada con el User ID "TESTUSER01"
    Cuando un nuevo usuario intenta registrarse utilizando el User ID "testuser01"
    Y completa el resto de campos obligatorios con datos válidos
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema no completa el registro y permanece en la página de cuenta
    Y muestra un mensaje de advertencia indicando que el User ID ya se encuentra en uso

  @regression @negative
  Escenario: TC-SIGNUP-04 - Rechazo de registro por correo electrónico duplicado
    Dado que existe una cuenta registrada con el correo "usuario.existente@testpetstore.com"
    Cuando un nuevo usuario intenta registrarse utilizando el correo "usuario.existente@testpetstore.com"
    Y completa el resto de campos obligatorios con datos válidos
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema rechaza el registro
    Y muestra un mensaje de error notificando la duplicidad del correo electrónico

  # ---------------------------------------------------------------------------
  # FLUJOS DE EXCEPCIÓN: VALIDACIÓN DE CONTRASEÑAS
  # ---------------------------------------------------------------------------

  @regression @negative
  Escenario: TC-SIGNUP-05 - Rechazo de registro por confirmación de contraseña no coincidente
    Cuando ingresa un User ID único válido
    Y completa la contraseña con "Passw0rd!+"
    Y en el campo Repeat Password ingresa "Passw0rdDiferente!+"
    Y completa los demás datos obligatorios
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema impide la creación de la cuenta
    Y muestra un mensaje de advertencia sobre la discordancia de contraseñas

  @regression @boundary @negative
  Esquema del escenario: TC-SIGNUP-06 - Validación de política de complejidad y longitud de contraseñas
    Cuando ingresa una contraseña que incumple la política: "<password_invalida>"
    Y repite exactamente la misma contraseña en el campo de confirmación
    Y completa los demás campos obligatorios con datos válidos
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema impide el registro por incumplimiento de la regla: "<motivo_falla>"

    Ejemplos:
      | password_invalida             | motivo_falla                                   |
      | Pass1!                        | Menor a 8 caracteres (frontera inferior - 1)   |
      | Passw0rdSuperLarga2026!+Extra | Mayor a 20 caracteres (frontera superior + 1)  |
      | password123!+                 | Sin letra mayúscula requerida                  |
      | PASSWORD123!+                 | Sin letra minúscula requerida                  |
      | PasswordSegura!+              | Sin número requerido                           |
      | PasswordSegura123             | Sin carácter especial requerido                |

  # ---------------------------------------------------------------------------
  # FLUJOS DE EXCEPCIÓN: CAMPOS OBLIGATORIOS FALTANTES
  # ---------------------------------------------------------------------------

  @regression @negative
  Esquema del escenario: TC-SIGNUP-07 - Rechazo de registro por omisión de campos obligatorios
    Cuando completa el formulario dejando el campo obligatorio "<campo_omitido>" vacío
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema no procesa el alta de la cuenta
    Y permanece en el formulario señalando la obligatoriedad del campo

    Ejemplos:
      | campo_omitido |
      | username      |
      | password      |
      | firstName     |
      | lastName      |
      | email         |
      | phone         |
      | address1      |
      | city          |
      | state         |
      | zip           |
      | country       |

  # ---------------------------------------------------------------------------
  # VALORES LÍMITE: TELÉFONO, USER ID Y ZIP
  # ---------------------------------------------------------------------------

  @regression @boundary @negative
  Esquema del escenario: TC-SIGNUP-08 - Validación de límites y formatos en el campo Teléfono
    Cuando ingresa en el campo de teléfono el valor "<telefono_invalido>"
    Y completa los demás campos con datos válidos
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema rechaza el teléfono por formato inválido

    Ejemplos:
      | telefono_invalido | descripcion_frontera                          |
      | 12345678          | 8 dígitos numéricos (menor a 9 dígitos)       |
      | 1234567890        | 10 dígitos numéricos sin prefijo internacional |
      | +12345678         | 8 dígitos con prefijo (longitud total 9)      |
      | 123-456-789       | Uso de guiones intermedios no admitidos       |
      | 12345ABCD         | Caracteres alfabéticos en número de contacto  |

  @regression @boundary @negative
  Esquema del escenario: TC-SIGNUP-09 - Validación de límites de longitud en User ID
    Cuando ingresa en el User ID el valor "<userid_invalido>"
    Y completa los demás campos requeridos
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema rechaza el User ID por no cumplir la longitud exacta de 10 caracteres

    Ejemplos:
      | userid_invalido | descripcion_frontera                  |
      | UserID009       | 9 caracteres (frontera inferior - 1)  |
      | UserID00011     | 11 caracteres (frontera superior + 1) |
      | User_ID!10      | 10 caracteres con símbolos inválidos  |

  # ---------------------------------------------------------------------------
  # EXPERIENCIA DE USUARIO Y SEGURIDAD
  # ---------------------------------------------------------------------------

  @regression @ux
  Escenario: TC-SIGNUP-11 - Preservación de datos válidos y vaciado de contraseñas tras error
    Cuando el usuario completa el formulario con datos válidos excepto el correo que ya existe
    Y hace clic en el botón "Save Account Information"
    Entonces el sistema recarga la página mostrando el error
    Y los campos de texto "username", "firstName", "lastName", "address1" y "phone" conservan los datos ingresados
    Y los campos "password" y "repeatedPassword" quedan completamente vacíos por seguridad

  @smoke @security
  Escenario: TC-SIGNUP-12 - Verificación de ausencia de sesión automática post-registro
    Cuando el usuario completa satisfactoriamente su registro
    Y es redirigido a la página de catálogo
    Entonces la barra de navegación superior exhibe el enlace "Sign In"
    Y no se exhibe ningún indicador de sesión de usuario autenticado
