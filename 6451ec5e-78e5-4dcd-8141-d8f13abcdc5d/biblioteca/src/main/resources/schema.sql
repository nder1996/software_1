DROP TABLE IF EXISTS prestamo;
DROP TABLE IF EXISTS libro;

CREATE TABLE libro (
    id INTEGER NOT NULL AUTO_INCREMENT,
    isbn VARCHAR(20),
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha_publicacion DATE,
    autor VARCHAR(255) NOT NULL,
    editorial VARCHAR(255),
    CONSTRAINT libro_pk PRIMARY KEY (id)
);

CREATE TABLE prestamo (
    id INTEGER NOT NULL AUTO_INCREMENT,
    fecha_prestamo DATE NOT NULL,
    fecha_maxima_devolucion DATE NOT NULL,
    identificacion_usuario VARCHAR(20) NOT NULL,
    tipo_usuario INTEGER,
    id_libro INTEGER NOT NULL,
    CONSTRAINT prestamo_pk PRIMARY KEY (id),
    CONSTRAINT fk_prestamo_libro FOREIGN KEY (id_libro) REFERENCES libro (id)
);

INSERT INTO libro (id, isbn, titulo, descripcion, fecha_publicacion, autor, editorial) VALUES
(1, '9788408187462', 'Cien Años de Soledad', 'Obra maestra del realismo mágico que narra la historia de la familia Buendía', '1967-05-30', 'Gabriel García Márquez', 'Editorial Sudamericana'),
(2, '9788491050145', 'Sapiens: De Animales a Dioses', 'Una breve historia de la humanidad desde la revolución cognitiva', '2011-02-10', 'Yuval Noah Harari', 'Debate'),
(3, '9788420432781', '1984', 'Novela distópica sobre el totalitarismo y la vigilancia gubernamental', '1949-06-08', 'George Orwell', 'Alianza Editorial'),
(4, '9788466331470', 'El Código Da Vinci', 'Thriller de misterio que combina arte, historia y símbolos religiosos', '2003-03-18', 'Dan Brown', 'Planeta'),
(5, '9788437604947', 'Don Quijote de la Mancha', 'Clásico de la literatura española sobre las aventuras del ingenioso hidalgo', '1605-01-16', 'Miguel de Cervantes', 'Cátedra'),
(6, '9788408114314', 'La Sombra del Viento', 'Novela gótica ambientada en la Barcelona de posguerra', '2001-04-17', 'Carlos Ruiz Zafón', 'Planeta'),
(7, '9788466337991', 'El Alquimista', 'Fábula sobre seguir los sueños y el destino personal', '1988-01-01', 'Paulo Coelho', 'Planeta'),
(8, '9788499892719', 'Factfulness', 'Diez razones por las que estamos equivocados sobre el mundo', '2018-04-03', 'Hans Rosling', 'Deusto'),
(9, '9788408043645', 'Los Pilares de la Tierra', 'Épica novela histórica sobre la construcción de una catedral medieval', '1989-10-01', 'Ken Follett', 'Planeta'),
(10, '9788497592567', 'Rayuela', 'Novela experimental que revolucionó la literatura latinoamericana', '1963-06-28', 'Julio Cortázar', 'Punto de Lectura'),
(11, '9788466658935', 'El Principito', 'Clásico cuento filosófico sobre la amistad, el amor y la pérdida', '1943-04-06', 'Antoine de Saint-Exupéry', 'Salamandra'),
(12, '9788466352406', 'Padre Rico, Padre Pobre', 'Guía sobre educación financiera y generación de riqueza', '1997-04-08', 'Robert Kiyosaki', 'Aguilar'),
(13, '9788499926278', 'El Hombre en Busca de Sentido', 'Reflexiones sobre la supervivencia y el propósito de vida', '1946-01-01', 'Viktor Frankl', 'Herder Editorial'),
(14, '9788408131465', 'La Casa de los Espíritus', 'Saga familiar que retrata la historia política de Chile', '1982-01-01', 'Isabel Allende', 'Planeta'),
(15, '9788466334242', 'El Arte de la Guerra', 'Tratado militar sobre estrategia y táctica aplicable a los negocios', '500-01-01', 'Sun Tzu', 'Planeta'),
(16, 'ASDA7884', 'Libro Test Afiliado', 'Libro para testing de usuarios afiliados', '2023-01-01', 'Autor Test', 'Editorial Test'),
(17, 'AWQ489', 'Libro Test Empleado', 'Libro para testing de usuarios empleados', '2023-01-01', 'Autor Test', 'Editorial Test'),
(18, 'EQWQW8545', 'Libro Test Invitado', 'Libro para testing de usuarios invitados', '2023-01-01', 'Autor Test', 'Editorial Test');

INSERT INTO prestamo (id, fecha_prestamo, fecha_maxima_devolucion, identificacion_usuario, tipo_usuario, id_libro) VALUES
(1, '2025-09-10', '2025-09-17', '1234567891', 1, 1),
(2, '2025-09-11', '2025-09-18', '0987654321', 2, 5),
(3, '2025-09-12', '2025-09-19', '1122334455', 3, 8),
(4, '2025-09-13', '2025-09-20', '5566778899', 1, 12),
(5, '2025-09-14', '2025-09-21', '9988776655', 2, 3),
(6, '2025-09-15', '2025-09-22', '4455667788', 3, 7),
(7, '2025-09-16', '2025-09-23', '7788990011', 1, 14);

-- Resetear las secuencias AUTO_INCREMENT para evitar conflictos
ALTER TABLE libro ALTER COLUMN id RESTART WITH 19;
ALTER TABLE prestamo ALTER COLUMN id RESTART WITH 8;
