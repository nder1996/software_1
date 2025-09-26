# 📚 DOCUMENTACIÓN COMPLETA - SISTEMA DE BIBLIOTECA

## 🎯 PROPÓSITO DEL PROYECTO

El **Sistema de Biblioteca** es una aplicación que permite gestionar préstamos de libros a diferentes tipos de usuarios. Los usuarios pueden solicitar libros prestados y el sistema aplica reglas específicas según el tipo de usuario.

### Funcionalidades Principales:
- ✅ **Prestar libros** a usuarios (AFILIADOS, EMPLEADOS, INVITADOS)
- ✅ **Consultar información** de préstamos existentes
- ✅ **Validar reglas de negocio** automáticamente
- ✅ **Calcular fechas de devolución** según tipo de usuario

---

## 🏗️ ARQUITECTURA DEL PROYECTO (Explicación Simple)

Imagina que la aplicación es como una **casa de 3 pisos**:

### 🏠 **PISO 1: INFRAESTRUCTURA** (`infrastructure/`)
- **¿Qué es?** La "plomería" y "electricidad" de la casa
- **¿Qué hace?** Se comunica con el mundo exterior (internet, base de datos)
- **Contiene:**
  - 🌐 **Controladores REST** - Reciben peticiones HTTP
  - 💾 **Base de datos** - Guardan y recuperan información
  - 🔧 **Configuraciones** - Ajustes de la aplicación

### 🏠 **PISO 2: APLICACIÓN** (`application/`)
- **¿Qué es?** Los "casos de uso" específicos
- **¿Qué hace?** Coordina las acciones que puede hacer el usuario
- **Contiene:**
  - 📝 **Casos de Uso** - "Prestar libro", "Consultar préstamo"
  - 📦 **DTOs** - Formatos de datos para transferir información

### 🏠 **PISO 3: DOMINIO** (`domain/`)
- **¿Qué es?** El "cerebro" de la aplicación
- **¿Qué hace?** Contiene las reglas de negocio y lógica principal
- **Contiene:**
  - 👤 **Modelos** - Prestamo, Libro, TipoUsuario
  - ⚖️ **Reglas de negocio** - Validaciones y cálculos
  - 🚫 **Excepciones** - Errores específicos del negocio

---

## 🔌 ¿QUÉ SON LOS "PUERTOS"? (Explicación Simple)

Los **puertos** son como **contratos** o **acuerdos** entre diferentes partes del código.

### 🔍 **Analogía del Enchufe:**
- Un **puerto** es como un **enchufe en la pared**
- No importa qué aparato conectes (televisor, computadora, lámpara)
- El enchufe siempre funciona igual
- Los aparatos son **intercambiables**

### 📝 **En el Código:**
```java
// PUERTO (Interface) = El "enchufe"
public interface PrestamoRepositoryPort {
    Prestamo save(Prestamo prestamo);
    Optional<Prestamo> findById(Integer id);
}

// ADAPTADOR (Implementación) = El "aparato"
public class PrestamoRepositoryAdapter implements PrestamoRepositoryPort {
    // Implementación específica con JPA/Base de datos
}
```

### 🎯 **¿Por qué es útil?**
- Si queremos cambiar de base de datos H2 a MySQL, solo cambiamos el "aparato"
- El "enchufe" (puerto) sigue siendo el mismo
- El resto del código no se entera del cambio

---

## 📋 ¿QUÉ ES UN "COMMAND"? (Explicación Simple)

Un **Command** es como un **formulario** que contiene toda la información necesaria para realizar una acción.

### 📝 **Analogía del Formulario de Banco:**
Cuando vas al banco a abrir una cuenta, llenas un formulario con:
- Tu nombre
- Tu cédula
- Tipo de cuenta que quieres

### 💻 **En el Código:**
```java
// COMMAND = "Formulario para prestar libro"
public class PrestarLibroCommand {
    private String isbn;                    // ¿Qué libro?
    private String identificacionUsuario;   // ¿Quién lo pide?
    private Integer tipoUsuario;           // ¿Qué tipo de usuario?
}
```

### 🎯 **¿Por qué es útil?**
- ✅ Organiza toda la información en un solo lugar
- ✅ Es fácil de validar
- ✅ Se puede enviar por la red o guardar en logs
- ✅ Es independiente de cómo llegó la información (web, móvil, etc.)

---

## 🗂️ ESTRUCTURA DE CARPETAS EXPLICADA

```
📁 biblioteca/
├── 📁 src/main/java/com/ceiba/biblioteca/
│   ├── 📁 domain/                          ← CEREBRO (Reglas de negocio)
│   │   ├── 📁 model/                       ← Entidades principales
│   │   │   ├── 📄 Prestamo.java           ← Representa un préstamo
│   │   │   ├── 📄 Libro.java              ← Representa un libro
│   │   │   └── 📄 TipoUsuario.java        ← AFILIADO/EMPLEADO/INVITADO
│   │   ├── 📁 service/                     ← Servicios de negocio
│   │   │   ├── 📄 ValidadorPrestamo.java  ← Valida reglas de préstamos
│   │   │   └── 📄 CalculadorFechaDevolucion.java ← Calcula cuándo devolver
│   │   ├── 📁 port/                        ← CONTRATOS (Interfaces)
│   │   │   ├── 📁 in/                      ← Contratos de ENTRADA
│   │   │   │   ├── 📄 PrestarLibroUseCase.java
│   │   │   │   └── 📄 ConsultarPrestamoUseCase.java
│   │   │   └── 📁 out/                     ← Contratos de SALIDA
│   │   │       ├── 📄 PrestamoRepositoryPort.java
│   │   │       └── 📄 LibroRepositoryPort.java
│   │   └── 📁 exception/                   ← Errores específicos
│   │       ├── 📄 DomainException.java
│   │       ├── 📄 TipoUsuarioNoPermitidoException.java
│   │       ├── 📄 UsuarioConPrestamoExistenteException.java
│   │       └── 📄 PrestamoNoEncontradoException.java
│   ├── 📁 application/                      ← COORDINADOR (Casos de uso)
│   │   ├── 📁 dto/                         ← Formatos de datos
│   │   │   ├── 📄 PrestarLibroCommand.java ← "Formulario" para prestar
│   │   │   ├── 📄 ResultadoPrestar.java    ← Respuesta del préstamo
│   │   │   └── 📄 ConsultaPrestamoResponse.java ← Respuesta de consulta
│   │   └── 📁 usecase/                     ← Implementaciones
│   │       ├── 📄 PrestarLibroUseCaseImpl.java
│   │       └── 📄 ConsultarPrestamoUseCaseImpl.java
│   ├── 📁 infrastructure/                   ← PLOMERÍA (Conexiones externas)
│   │   ├── 📁 adapter/
│   │   │   ├── 📁 in/web/                  ← API REST (Internet)
│   │   │   │   ├── 📄 PrestamoController.java ← Recibe peticiones HTTP
│   │   │   │   ├── 📁 dto/                 ← Formatos para web
│   │   │   │   └── 📁 mapper/              ← Conversores de datos
│   │   │   └── 📁 out/persistence/         ← Base de datos
│   │   │       ├── 📁 entity/              ← Tablas de base de datos
│   │   │       ├── 📁 repository/          ← Consultas SQL
│   │   │       ├── 📁 adapter/             ← Implementaciones de puertos
│   │   │       └── 📁 mapper/              ← Conversores BD ↔ Objetos
│   │   └── 📁 config/                      ← Configuraciones
│   │       ├── 📄 BeanConfiguration.java   ← Conecta las piezas
│   │       └── 📄 GlobalExceptionHandler.java ← Maneja errores
│   └── 📄 BibliotecaApplication.java       ← ENTRADA PRINCIPAL
└── 📁 src/test/                            ← Pruebas automatizadas
```

---

## 🌊 FLUJO COMPLETO DE LA APLICACIÓN

### 📖 **ESCENARIO: Usuario quiere prestar un libro**

#### 1️⃣ **CLIENTE ENVÍA PETICIÓN HTTP**
```http
POST http://localhost:8080/prestamo
Content-Type: application/json

{
  "isbn": "978-3-16-148410-0",
  "identificacionUsuario": "12345678",
  "tipoUsuario": 1
}
```

#### 2️⃣ **CONTROLADOR RECIBE LA PETICIÓN**
📁 `PrestamoController.java`
```java
@PostMapping
public ResponseEntity<ResultadoPrestarDto> prestarLibro(@RequestBody SolicitudPrestarLibroDto solicitud) {
    // 1. Convierte DTO web → Command
    PrestarLibroCommand command = mapper.toCommand(solicitud);

    // 2. Ejecuta caso de uso
    ResultadoPrestar resultado = prestarLibroUseCase.ejecutar(command);

    // 3. Convierte resultado → DTO web
    return ResponseEntity.ok(mapper.toDto(resultado));
}
```

#### 3️⃣ **CASO DE USO PROCESA LA LÓGICA**
📁 `PrestarLibroUseCaseImpl.java`
```java
public ResultadoPrestar ejecutar(PrestarLibroCommand command) {
    // 1. ¿Es válido el tipo de usuario?
    validadorPrestamo.validarTipoUsuario(command.getTipoUsuario());

    // 2. ¿Es un invitado con préstamo existente?
    TipoUsuario tipoUsuario = TipoUsuario.fromValor(command.getTipoUsuario());
    validadorPrestamo.validarUsuarioInvitado(command.getIdentificacionUsuario(), tipoUsuario);

    // 3. ¿Existe el libro?
    Libro libro = libroRepository.findByIsbn(command.getIsbn());

    // 4. ¿Cuándo debe devolverlo?
    LocalDate fechaDevolucion = calculadorFecha.calcular(tipoUsuario);

    // 5. Crear el préstamo
    Prestamo prestamo = Prestamo.crear(
        command.getIdentificacionUsuario(),
        tipoUsuario,
        libro,
        fechaDevolucion
    );

    // 6. Guardar en base de datos
    Prestamo prestamoGuardado = prestamoRepository.save(prestamo);

    // 7. Retornar resultado
    return ResultadoPrestar.builder()
        .id(prestamoGuardado.getId())
        .fechaMaximaDevolucion(fechaDevolucion.format("dd/MM/yyyy"))
        .build();
}
```

#### 4️⃣ **VALIDADOR VERIFICA REGLAS**
📁 `ValidadorPrestamo.java`
```java
public void validarUsuarioInvitado(String identificacion, TipoUsuario tipo) {
    if (tipo.esInvitado()) {
        long prestamosActivos = prestamoRepository.countByIdentificacionUsuario(identificacion);
        if (prestamosActivos > 0) {
            throw new UsuarioConPrestamoExistenteException(identificacion);
        }
    }
}
```

#### 5️⃣ **CALCULADOR DETERMINA FECHA**
📁 `CalculadorFechaDevolucion.java`
```java
public LocalDate calcular(TipoUsuario tipoUsuario) {
    // AFILIADO = 10 días, EMPLEADO = 8 días, INVITADO = 7 días
    return addDaysSkippingWeekends(LocalDate.now(), tipoUsuario.getDiasPrestamo());
}
```

#### 6️⃣ **REPOSITORIO GUARDA EN BASE DE DATOS**
📁 `PrestamoRepositoryAdapter.java`
```java
public Prestamo save(Prestamo prestamo) {
    // Convierte Prestamo → PrestamoJpaEntity
    PrestamoJpaEntity entity = mapper.toEntity(prestamo);

    // Guarda en base de datos
    PrestamoJpaEntity savedEntity = jpaRepository.save(entity);

    // Convierte PrestamoJpaEntity → Prestamo
    return mapper.toDomain(savedEntity);
}
```

#### 7️⃣ **RESPUESTA AL CLIENTE**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 123,
  "fechaMaximaDevolucion": "15/01/2024"
}
```

---

## ⚖️ REGLAS DE NEGOCIO IMPLEMENTADAS

### 👥 **TIPOS DE USUARIO**

| Tipo | Código | Días Préstamo | Restricciones |
|------|--------|---------------|---------------|
| 🏆 **AFILIADO** | `1` | 10 días | Ninguna |
| 👔 **EMPLEADO** | `2` | 8 días | Privilegios especiales |
| 👤 **INVITADO** | `3` | 7 días | ⚠️ Solo 1 libro a la vez |

### 📅 **CÁLCULO DE FECHAS**
- ✅ Se **excluyen fines de semana** (sábado y domingo)
- ✅ Solo se cuentan **días hábiles** (lunes a viernes)
- ✅ Fecha base: día actual del préstamo

**Ejemplo:**
- Préstamo: Viernes
- Usuario: AFILIADO (10 días)
- Devolución: Jueves de la semana siguiente (saltando fin de semana)

### 🚫 **VALIDACIONES**

#### ❌ **Errores Posibles:**
1. **Tipo de usuario inválido**
   - Código: `400 Bad Request`
   - Mensaje: "Tipo de usuario no permitido en la biblioteca"

2. **Invitado con préstamo existente**
   - Código: `400 Bad Request`
   - Mensaje: "El usuario con identificación 12345678 ya tiene un libro prestado..."

3. **Libro no encontrado**
   - Código: `500 Internal Server Error`
   - Mensaje: "Libro con ISBN 978-3-16-148410-0 no encontrado"

4. **Préstamo no existe** (en consultas)
   - Código: `404 Not Found`
   - Mensaje: "El préstamo con ID 123 no existe"

---

## 🔄 PATRONES DE DISEÑO UTILIZADOS

### 1️⃣ **ARQUITECTURA HEXAGONAL**
- **Propósito:** Separar la lógica de negocio de detalles técnicos
- **Beneficio:** Cambiar base de datos o framework sin afectar reglas de negocio

### 2️⃣ **COMMAND PATTERN**
- **Propósito:** Encapsular solicitudes como objetos
- **Ejemplo:** `PrestarLibroCommand` contiene todos los datos del préstamo

### 3️⃣ **REPOSITORY PATTERN**
- **Propósito:** Abstraer el acceso a datos
- **Ejemplo:** `PrestamoRepositoryPort` define QUÉ hacer, no CÓMO

### 4️⃣ **FACTORY METHOD**
- **Propósito:** Crear objetos con lógica específica
- **Ejemplo:** `Prestamo.crear()` establece fecha actual automáticamente

### 5️⃣ **DOMAIN SERVICES**
- **Propósito:** Lógica de negocio que no pertenece a una entidad específica
- **Ejemplo:** `ValidadorPrestamo`, `CalculadorFechaDevolucion`

---

## 🛠️ TECNOLOGÍAS UTILIZADAS

### ☕ **BACKEND**
- **Spring Boot 2.2.1** - Framework principal
- **Spring Data JPA** - Acceso a base de datos
- **H2 Database** - Base de datos en memoria
- **Lombok** - Reducir código boilerplate
- **Jackson** - Serialización JSON

### 🏗️ **ARQUITECTURA**
- **Clean Architecture** - Separación por capas
- **Hexagonal Architecture** - Ports & Adapters
- **Domain Driven Design** - Modelado del dominio

### 🔧 **HERRAMIENTAS**
- **Gradle** - Gestión de dependencias
- **JUnit** - Pruebas unitarias
- **Maven** - Construcción del proyecto

---

## 🚀 CÓMO EJECUTAR EL PROYECTO

### 1️⃣ **Requisitos**
- ☕ Java 8 o superior
- 🐘 Gradle instalado

### 2️⃣ **Ejecutar**
```bash
cd biblioteca
./gradlew bootRun
```

### 3️⃣ **Probar**
```bash
# Prestar libro
curl -X POST http://localhost:8080/prestamo \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "978-3-16-148410-0",
    "identificacionUsuario": "12345678",
    "tipoUsuario": 1
  }'

# Consultar préstamo
curl http://localhost:8080/prestamo/123
```

---

## 📚 CONCLUSIÓN

Este proyecto implementa un **sistema robusto y escalable** usando:

✅ **Arquitectura limpia** que separa responsabilidades
✅ **Reglas de negocio claras** y bien definidas
✅ **Patrones de diseño** reconocidos en la industria
✅ **Código documentado** y fácil de mantener
✅ **API REST** simple y funcional

La estructura permite **agregar nuevas funcionalidades** fácilmente y **cambiar tecnologías** sin afectar la lógica principal del negocio.