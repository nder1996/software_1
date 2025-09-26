# 🏗️ DOCUMENTACIÓN COMPLETA - CAPA DE INFRAESTRUCTURA

## 📋 ÍNDICE
1. [🌐 Adaptadores de Entrada (Web)](#web-adapters)
2. [💾 Adaptadores de Salida (Persistencia)](#persistence-adapters)
3. [🗃️ Entidades JPA](#jpa-entities)
4. [🔍 Repositorios JPA](#jpa-repositories)
5. [🔄 Mappers](#mappers)
6. [⚙️ Configuraciones](#configurations)

---

## 🌐 ADAPTADORES DE ENTRADA (WEB) {#web-adapters}

### **PrestamoController.java** - Controlador REST Principal
```java
@RestController
@RequestMapping("prestamo")
public class PrestamoController
```

**¿Qué hace?**
- ✅ **Expone endpoints HTTP** para operaciones de préstamos
- ✅ **Recibe peticiones JSON** del cliente web/móvil
- ✅ **Convierte datos** de formato web a formato de aplicación
- ✅ **Retorna respuestas HTTP** con códigos de estado apropiados

**Endpoints:**
- `POST /prestamo` - Crear nuevo préstamo
- `GET /prestamo/{id}` - Consultar préstamo existente

### **DTOs Web (infrastructure/adapter/in/web/dto/)**

#### **SolicitudPrestarLibroDto.java** - Entrada para préstamos
```java
{
  "isbn": "978-3-16-148410-0",
  "identificacionUsuario": "12345678",
  "tipoUsuario": 1
}
```
**Propósito:** Define la estructura exacta del JSON que el cliente debe enviar.

#### **ResultadoPrestarDto.java** - Respuesta de préstamos
```java
{
  "id": 123,
  "fechaMaximaDevolucion": "15/01/2024"
}
```
**Propósito:** Define qué información se retorna al cliente tras un préstamo exitoso.

#### **ConsultaPrestamoResponseDto.java** - Respuesta de consultas
```java
{
  "id": 123,
  "isbn": "978-3-16-148410-0",
  "titulo": "El Señor de los Anillos",
  "fechaMaximaDevolucion": "15/01/2024",
  "identificacionUsuario": "12345678",
  "tipoUsuario": 1
}
```
**Propósito:** Proporciona información completa de un préstamo existente.

#### **ErrorResponseDto.java** - Respuestas de error estandarizadas
```java
{
  "mensaje": "Tipo de usuario no permitido en la biblioteca"
}
```
**Propósito:** Estructura uniforme para todos los errores de la API.

### **PrestamoRestMapper.java** - Transformador Web ↔ Aplicación
```java
@Component
public class PrestamoRestMapper
```

**¿Qué hace?**
- ✅ **Convierte DTOs web → Commands** de aplicación
- ✅ **Convierte Responses → DTOs web** para respuesta
- ✅ **Aísla la aplicación** de cambios en la API
- ✅ **Permite versionado** independiente de contratos

**Métodos:**
- `toCommand(SolicitudPrestarLibroDto)` → `PrestarLibroCommand`
- `toDto(ResultadoPrestar)` → `ResultadoPrestarDto`
- `toDto(ConsultaPrestamoResponse)` → `ConsultaPrestamoResponseDto`

---

## 💾 ADAPTADORES DE SALIDA (PERSISTENCIA) {#persistence-adapters}

### **PrestamoRepositoryAdapter.java** - Persistencia de Préstamos
```java
@Repository
public class PrestamoRepositoryAdapter implements PrestamoRepositoryPort
```

**¿Qué hace?**
- ✅ **Implementa el puerto** `PrestamoRepositoryPort`
- ✅ **Traduce entre dominio y JPA** usando mappers
- ✅ **Maneja relaciones complejas** (préstamo ↔ libro)
- ✅ **Evita conflictos de cascada** en persistencia

**Métodos Implementados:**
```java
// Guardar préstamo (nuevo o actualización)
Prestamo save(Prestamo prestamo)

// Buscar por ID único
Optional<Prestamo> findById(Integer id)

// Buscar por usuario (para validaciones)
Optional<Prestamo> findByIdentificacionUsuario(String identificacion)

// Contar préstamos activos de un usuario
long countByIdentificacionUsuario(String identificacion)
```

**Lógica Especial:**
- Busca el `LibroJpaEntity` existente por ISBN antes de crear el préstamo
- Construye manualmente `PrestamoJpaEntity` para evitar problemas de cascada

### **LibroRepositoryAdapter.java** - Catálogo de Libros
```java
@Repository
public class LibroRepositoryAdapter implements LibroRepositoryPort
```

**¿Qué hace?**
- ✅ **Gestiona el catálogo** de libros de la biblioteca
- ✅ **Búsquedas por ISBN** para validación de préstamos
- ✅ **Persistencia** de nuevos libros en el catálogo

**Métodos Implementados:**
```java
// Guardar libro en catálogo
Libro save(Libro libro)

// Buscar libro por ISBN (crítico para préstamos)
Optional<Libro> findByIsbn(String isbn)
```

---

## 🗃️ ENTIDADES JPA {#jpa-entities}

### **PrestamoJpaEntity.java** - Tabla 'prestamo'
```java
@Entity
@Table(name = "prestamo")
public class PrestamoJpaEntity
```

**Estructura de Tabla:**
```sql
CREATE TABLE prestamo (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    fecha_prestamo DATE NOT NULL,
    fecha_maxima_devolucion DATE NOT NULL,
    identificacion_usuario VARCHAR(20) NOT NULL,
    tipo_usuario INTEGER,
    id_libro INTEGER NOT NULL,
    FOREIGN KEY (id_libro) REFERENCES libro(id)
);
```

**Campos:**
- `id` - PK autogenerada
- `fechaPrestamo` - Cuándo se realizó el préstamo
- `fechaMaximaDevolucion` - Hasta cuándo puede devolverlo
- `identificacionUsuario` - Documento del usuario
- `tipoUsuario` - Tipo (1=AFILIADO, 2=EMPLEADO, 3=INVITADO)
- `libro` - Relación `@ManyToOne` con LibroJpaEntity

**Anotaciones Importantes:**
- `@ManyToOne(fetch = FetchType.LAZY)` - Muchos préstamos, un libro
- `@JoinColumn(name = "id_libro")` - Columna de referencia
- `FetchType.LAZY` - Carga el libro solo cuando se necesita

### **LibroJpaEntity.java** - Tabla 'libro'
```java
@Entity
@Table(name = "libro")
public class LibroJpaEntity
```

**Estructura de Tabla:**
```sql
CREATE TABLE libro (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20),
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha_publicacion DATE,
    autor VARCHAR(255) NOT NULL,
    editorial VARCHAR(255)
);
```

**Campos:**
- `id` - PK autogenerada
- `isbn` - Código único internacional (max 20 chars)
- `titulo` - Título del libro (obligatorio)
- `descripcion` - Sinopsis (campo TEXT largo)
- `fechaPublicacion` - Fecha de publicación original
- `autor` - Autor del libro (obligatorio)
- `editorial` - Casa editorial

---

## 🔍 REPOSITORIOS JPA {#jpa-repositories}

### **PrestamoJpaRepository.java** - Consultas de Préstamos
```java
@Repository
public interface PrestamoJpaRepository extends JpaRepository<PrestamoJpaEntity, Integer>
```

**¿Qué hereda de JpaRepository?**
```java
// Métodos automáticos de Spring Data JPA:
save(entity)           // Guardar/actualizar
findById(id)          // Buscar por ID
findAll()             // Traer todos
delete(entity)        // Eliminar
count()              // Contar registros
existsById(id)       // Verificar existencia
```

**Consultas Personalizadas:**
```java
// Buscar préstamo por usuario (para validaciones)
@Query("SELECT p FROM PrestamoJpaEntity p WHERE p.identificacionUsuario = :identificacionUsuario")
Optional<PrestamoJpaEntity> findByIdentificacionUsuario(@Param("identificacionUsuario") String identificacionUsuario);

// Contar préstamos activos de un usuario (regla invitados)
@Query("SELECT COUNT(p) FROM PrestamoJpaEntity p WHERE p.identificacionUsuario = :identificacionUsuario")
long countByIdentificacionUsuario(@Param("identificacionUsuario") String identificacionUsuario);
```

### **LibroJpaRepository.java** - Consultas de Libros
```java
@Repository
public interface LibroJpaRepository extends JpaRepository<LibroJpaEntity, Integer>
```

**Consultas Personalizadas:**
```java
// Buscar libro por ISBN (crítico para préstamos)
@Query("SELECT l FROM LibroJpaEntity l WHERE l.isbn = :isbn")
Optional<LibroJpaEntity> findByIsbn(@Param("isbn") String isbn);
```

**¿Por qué @Query explícita?**
- Mayor control sobre la consulta SQL generada
- Mejor rendimiento que query methods automáticos
- Claridad en el intent del código

---

## 🔄 MAPPERS {#mappers}

### **PrestamoJpaMapper.java** - Dominio ↔ JPA Préstamos
```java
@Component
public class PrestamoJpaMapper
```

**¿Qué hace?**
- ✅ **Convierte** `Prestamo` (dominio) ↔ `PrestamoJpaEntity` (JPA)
- ✅ **Maneja relaciones** complejas con libros
- ✅ **Transforma enums** `TipoUsuario` ↔ `Integer`

**Métodos:**

#### `toEntity(Prestamo prestamo)` - Dominio → JPA
```java
public PrestamoJpaEntity toEntity(Prestamo prestamo) {
    // 1. Busca LibroJpaEntity existente por ISBN
    LibroJpaEntity libroEntity = libroRepository.findByIsbn(prestamo.getLibro().getIsbn())
        .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

    // 2. Construye PrestamoJpaEntity
    return PrestamoJpaEntity.builder()
        .id(prestamo.getId())
        .fechaPrestamo(prestamo.getFechaPrestamo())
        .fechaMaximaDevolucion(prestamo.getFechaMaximaDevolucion())
        .identificacionUsuario(prestamo.getIdentificacionUsuario())
        .tipoUsuario(prestamo.getTipoUsuario().getValor())  // Enum → Integer
        .libro(libroEntity)  // Relación establecida
        .build();
}
```

#### `toDomain(PrestamoJpaEntity entity)` - JPA → Dominio
```java
public Prestamo toDomain(PrestamoJpaEntity entity) {
    return Prestamo.builder()
        .id(entity.getId())
        .fechaPrestamo(entity.getFechaPrestamo())
        .fechaMaximaDevolucion(entity.getFechaMaximaDevolucion())
        .identificacionUsuario(entity.getIdentificacionUsuario())
        .tipoUsuario(TipoUsuario.fromValor(entity.getTipoUsuario()))  // Integer → Enum
        .libro(libroMapper.toDomain(entity.getLibro()))  // Delega al mapper de libros
        .build();
}
```

### **LibroJpaMapper.java** - Dominio ↔ JPA Libros
```java
@Component
public class LibroJpaMapper
```

**Métodos:**
- `toEntity(Libro libro)` - Convierte modelo dominio → entidad JPA
- `toDomain(LibroJpaEntity entity)` - Convierte entidad JPA → modelo dominio

**Mapeo Simple:** Todos los campos se mapean 1:1 sin transformaciones especiales.

---

## ⚙️ CONFIGURACIONES {#configurations}

### **BeanConfiguration.java** - Configuración de Dependencias
```java
@Configuration
public class BeanConfiguration
```

**¿Qué configura?**
- ✅ **Servicios de dominio** sin anotaciones (@Component)
- ✅ **Casos de uso** con sus dependencias inyectadas
- ✅ **Conexión entre puertos** y adaptadores

**Beans Configurados:**
```java
@Bean
public CalculadorFechaDevolucion calculadorFechaDevolucion() {
    return new CalculadorFechaDevolucion();  // Sin dependencias
}

@Bean
public ValidadorPrestamo validadorPrestamo(PrestamoRepositoryPort prestamoRepository) {
    return new ValidadorPrestamo(prestamoRepository);  // Con dependencia
}

@Bean
public PrestarLibroUseCase prestarLibroUseCase(
    PrestamoRepositoryPort prestamoRepository,
    LibroRepositoryPort libroRepository,
    CalculadorFechaDevolucion calculadorFecha,
    ValidadorPrestamo validadorPrestamo) {

    return new PrestarLibroUseCaseImpl(prestamoRepository, libroRepository,
                                      calculadorFecha, validadorPrestamo);
}
```

### **GlobalExceptionHandler.java** - Manejo Global de Errores
```java
@RestControllerAdvice
public class GlobalExceptionHandler
```

**¿Qué maneja?**
- ✅ **Excepciones de dominio** → HTTP 400 Bad Request
- ✅ **Recursos no encontrados** → HTTP 404 Not Found
- ✅ **Errores internos** → HTTP 500 Internal Server Error

**Handlers:**
```java
@ExceptionHandler(DomainException.class)
public ResponseEntity<ErrorResponseDto> handleDomainException(DomainException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDto(e.getMessage()));
}

@ExceptionHandler(PrestamoNoEncontradoException.class)
public ResponseEntity<ErrorResponseDto> handlePrestamoNoEncontrado(PrestamoNoEncontradoException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDto(e.getMessage()));
}
```

---

## 🔗 FLUJO COMPLETO EN LA CAPA DE INFRAESTRUCTURA

### **Flujo de Entrada (Web → Aplicación):**
1. `PrestamoController` recibe petición HTTP
2. `@RequestBody` deserializa JSON → `SolicitudPrestarLibroDto`
3. `PrestamoRestMapper` convierte DTO → `PrestarLibroCommand`
4. Se ejecuta caso de uso de aplicación
5. `PrestamoRestMapper` convierte resultado → DTO de respuesta
6. `ResponseEntity` retorna JSON con código HTTP

### **Flujo de Salida (Aplicación → Base de Datos):**
1. Caso de uso llama `PrestamoRepositoryPort.save()`
2. `PrestamoRepositoryAdapter` implementa la llamada
3. `PrestamoJpaMapper` convierte `Prestamo` → `PrestamoJpaEntity`
4. Se busca `LibroJpaEntity` existente por ISBN
5. `PrestamoJpaRepository` persiste en base de datos
6. `PrestamoJpaMapper` convierte resultado → `Prestamo` de dominio

### **Manejo de Errores:**
1. Excepción ocurre en cualquier capa
2. `GlobalExceptionHandler` captura la excepción
3. Se mapea a código HTTP apropiado
4. Se retorna `ErrorResponseDto` con mensaje claro

---

## 🎯 BENEFICIOS DE ESTA ARQUITECTURA

### **Separación de Responsabilidades:**
- ✅ **Web**: Solo manejo HTTP y JSON
- ✅ **Persistencia**: Solo acceso a datos y SQL
- ✅ **Mappers**: Solo transformaciones de datos
- ✅ **Configuración**: Solo cableado de dependencias

### **Flexibilidad:**
- ✅ **Cambiar de REST a GraphQL**: Solo cambiar adaptadores web
- ✅ **Cambiar de H2 a PostgreSQL**: Solo cambiar configuración
- ✅ **Cambiar estructura JSON**: Solo cambiar DTOs web
- ✅ **Agregar validaciones**: Solo modificar mappers

### **Testabilidad:**
- ✅ **Mocks fáciles**: Interfaces bien definidas
- ✅ **Tests unitarios**: Cada componente aislado
- ✅ **Tests de integración**: Adaptadores específicos

### **Mantenibilidad:**
- ✅ **Responsabilidades claras**: Cada clase tiene un propósito
- ✅ **Bajo acoplamiento**: Cambios localizados
- ✅ **Alta cohesión**: Funciones relacionadas juntas

La capa de infraestructura actúa como la **"plomería"** del sistema, conectando el dominio puro con el mundo exterior (web, base de datos, configuraciones) de manera limpia y desacoplada.