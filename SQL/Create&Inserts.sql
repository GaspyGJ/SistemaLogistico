

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


CREATE SCHEMA `gestion_logistica`;

USE `gestion_logistica`;

--
-- Base de datos: `gestion_logistica`
--
-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `camino`
--

CREATE TABLE `camino` (
  `capacidad_maxima` double DEFAULT NULL,
  `estado` tinyint(4) DEFAULT NULL,
  `tiempo_transito` varchar(255) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `sucursal_destino_id` bigint(20) DEFAULT NULL,
  `sucursal_origen_id` bigint(20) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `camino`
--

INSERT INTO `camino` (`capacidad_maxima`, `estado`, `tiempo_transito`, `id`, `sucursal_destino_id`, `sucursal_origen_id`, `nombre`) VALUES
(6, 1, '00:15', 78, 67, 68, 'D->Centro'),
(4, 1, '03:00', 79, 67, 69, 'F->Centro'),
(6, 1, '01:00', 80, 67, 70, 'E->Centro'),
(3, 1, '00:45', 81, 69, 70, 'E->F'),
(4, 1, '00:15', 82, 70, 71, 'B->E'),
(3, 1, '01:00', 83, 71, 72, 'C->B'),
(2, 1, '01:00', 84, 70, 72, 'C->E'),
(2, 1, '01:30', 85, 69, 72, 'C->F'),
(5, 1, '00:30', 86, 68, 73, 'A->D'),
(8, 1, '00:45', 87, 73, 66, 'Puerto->A'),
(5, 1, '00:50', 88, 71, 66, 'Puerto->B'),
(4, 1, '01:00', 89, 72, 73, 'A->C');

--
-- Disparadores `camino`
--
DELIMITER $$
CREATE TRIGGER `deleteCamino_linea` BEFORE DELETE ON `camino` FOR EACH ROW BEGIN
DELETE FROM linea_nodos WHERE linea_nodos.camino_id = OLD.id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `insertCamino` AFTER INSERT ON `camino` FOR EACH ROW BEGIN
INSERT INTO linea_nodos(camino_id)
VALUES (NEW.id);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `item_orden_provision`
--

CREATE TABLE `item_orden_provision` (
  `cantidad` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `ordenProvision_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `item_orden_provision`
--

INSERT INTO `item_orden_provision` (`cantidad`, `id`, `ordenProvision_id`, `producto_id`) VALUES
(3, 6, 4, 5),
(5, 7, 4, 1),
(6, 8, 5, 5),
(2, 9, 5, 3),
(5, 10, 5, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_nodos`
--

CREATE TABLE `linea_nodos` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `camino_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `linea_nodos`
--

INSERT INTO `linea_nodos` (`id`, `nombre`, `camino_id`) VALUES
(90, NULL, 78),
(91, NULL, 79),
(92, NULL, 80),
(93, NULL, 81),
(94, NULL, 82),
(95, NULL, 83),
(96, NULL, 84),
(97, NULL, 85),
(98, NULL, 86),
(99, NULL, 87),
(100, NULL, 88),
(101, NULL, 89);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nodo_sucursal`
--

CREATE TABLE `nodo_sucursal` (
  `id` bigint(20) NOT NULL,
  `h` int(11) DEFAULT NULL,
  `w` int(11) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `sucursal_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nodo_sucursal`
--

INSERT INTO `nodo_sucursal` (`id`, `h`, `w`, `x`, `y`, `sucursal_id`) VALUES
(58, 40, 40, 50, 159, 66),
(59, 40, 40, 659, 244, 67),
(60, 40, 40, 582, 78, 68),
(61, 40, 40, 413, 208, 69),
(62, 40, 40, 332, 441, 70),
(63, 40, 40, 83, 383, 71),
(64, 40, 40, 269, 253, 72),
(65, 40, 40, 229, 97, 73);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_provision`
--

CREATE TABLE `orden_provision` (
  `estado` tinyint(4) DEFAULT NULL,
  `fecha` varchar(255) DEFAULT NULL,
  `tiempo_maximo` varchar(255) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `sucursalDestino_id` bigint(20) DEFAULT NULL,
  `camino` varchar(255) DEFAULT NULL,
  `camino_asignado` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `orden_provision`
--

INSERT INTO `orden_provision` (`estado`, `fecha`, `tiempo_maximo`, `id`, `sucursalDestino_id`, `camino`, `camino_asignado`) VALUES
(1, '29-07-2023', '24:00', 4, 68, NULL, 'Puerto  >  A  >  D'),
(0, '29-07-2023', '08:00', 5, 69, NULL, NULL);

--
-- Disparadores `orden_provision`
--
DELIMITER $$
CREATE TRIGGER `deleteOrden_provision` BEFORE DELETE ON `orden_provision` FOR EACH ROW BEGIN
DELETE FROM item_orden_provision WHERE item_orden_provision.ordenProvision_id = OLD.id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `peso_kg` double DEFAULT NULL,
  `precio_unitario` double DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`peso_kg`, `precio_unitario`, `id`, `descripcion`, `nombre`) VALUES
(10, 1500, 1, 'Descripción del Producto A', 'Producto A'),
(5, 200.75, 2, 'Descripción del Producto B', 'Producto B'),
(2.5, 1565.8, 3, 'Descripción del Producto C', 'Producto C'),
(1, 100, 4, 'Descripción del Producto D', 'Producto D'),
(0.5, 999, 5, 'Descripción del Producto E', 'Producto E'),
(0.25, 100, 6, 'Descripción del Producto F', 'Producto F');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stock_producto`
--

CREATE TABLE `stock_producto` (
  `cantidad` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `sucursal_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `stock_producto`
--

INSERT INTO `stock_producto` (`cantidad`, `id`, `producto_id`, `sucursal_id`) VALUES
(10, 9, 1, 66),
(18, 10, 2, 66),
(12, 11, 3, 66),
(10, 12, 4, 66),
(15, 13, 5, 66),
(5, 14, 6, 66),
(5, 15, 1, 71),
(5, 16, 2, 71),
(5, 17, 3, 71),
(5, 18, 4, 71),
(5, 19, 5, 71),
(5, 20, 6, 71),
(16, 21, 1, 73),
(4, 22, 2, 73),
(19, 23, 3, 73),
(20, 24, 4, 73),
(1, 25, 5, 73),
(0, 26, 6, 73),
(0, 27, 1, 72),
(0, 28, 2, 72),
(5, 29, 3, 72),
(7, 30, 4, 72),
(13, 31, 5, 72),
(5, 32, 6, 72),
(5, 33, 4, 69),
(4, 34, 2, 69),
(5, 35, 6, 69),
(12, 36, 3, 68),
(4, 37, 1, 68),
(0, 38, 5, 68),
(17, 39, 6, 68),
(20, 40, 1, 67),
(20, 41, 2, 67),
(20, 42, 3, 67),
(20, 43, 4, 67),
(20, 44, 5, 67),
(20, 45, 6, 67);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE `sucursal` (
  `estado` tinyint(4) DEFAULT NULL,
  `horario_apertura` varchar(255) DEFAULT NULL,
  `horario_cierre` varchar(255) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `sucursal`
--

INSERT INTO `sucursal` (`estado`, `horario_apertura`, `horario_cierre`, `id`, `nombre`) VALUES
(1, '06:00', '18:00', 66, 'Puerto'),
(1, '06:00', '18:00', 67, 'Centro'),
(1, '07:00', '19:00', 68, 'D'),
(1, '07:30', '20:00', 69, 'F'),
(1, '18:00', '06:00', 70, 'E'),
(1, '21:00', '09:00', 71, 'B'),
(1, '00:00', '12:00', 72, 'C'),
(1, '06:00', '18:00', 73, 'A');

--
-- Disparadores `sucursal`
--
DELIMITER $$
CREATE TRIGGER `deleteSucursal` BEFORE DELETE ON `sucursal` FOR EACH ROW BEGIN
DELETE FROM camino WHERE camino.sucursal_origen_id = OLD.id;
DELETE FROM camino WHERE camino.sucursal_destino_id = OLD.id;
DELETE FROM nodo_sucursal WHERE nodo_sucursal.sucursal_id = OLD.id;
DELETE FROM stock_producto WHERE stock_producto.sucursal_id = OLD.id;
DELETE FROM orden_provision WHERE orden_provision.sucursalDestino_id= OLD.id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `insertSucursal` AFTER INSERT ON `sucursal` FOR EACH ROW BEGIN
    INSERT INTO nodo_sucursal (h, w, x, y, sucursal_id) VALUES (40,40,40,40, NEW.id);
END
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `camino`
--
ALTER TABLE `camino`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqgvtmse1d123938ehpqjkybp9` (`sucursal_destino_id`),
  ADD KEY `FKg9yj7c0w7a8c174wd0mi7cwp3` (`sucursal_origen_id`);

--
-- Indices de la tabla `item_orden_provision`
--
ALTER TABLE `item_orden_provision`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKhw3u88qjfclnbtxbgvj3ojd7t` (`ordenProvision_id`),
  ADD KEY `FKhc8owc9yy5bi3ippipsm0qss4` (`producto_id`);

--
-- Indices de la tabla `linea_nodos`
--
ALTER TABLE `linea_nodos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_oj2thqdjd79wy5mdr6dgv3nwx` (`camino_id`);

--
-- Indices de la tabla `nodo_sucursal`
--
ALTER TABLE `nodo_sucursal`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_h41rfjccuhiud1clu3flkvw6j` (`sucursal_id`);

--
-- Indices de la tabla `orden_provision`
--
ALTER TABLE `orden_provision`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsjibxaxg7d5t5n9dnqvxdw7kx` (`sucursalDestino_id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `stock_producto`
--
ALTER TABLE `stock_producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9g0jln4joaku0bje83cssluxv` (`producto_id`),
  ADD KEY `FKcxhgefin13j8wvq7dg9cwup0v` (`sucursal_id`);

--
-- Indices de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `camino`
--
ALTER TABLE `camino`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=93;

--
-- AUTO_INCREMENT de la tabla `item_orden_provision`
--
ALTER TABLE `item_orden_provision`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `linea_nodos`
--
ALTER TABLE `linea_nodos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT de la tabla `nodo_sucursal`
--
ALTER TABLE `nodo_sucursal`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT de la tabla `orden_provision`
--
ALTER TABLE `orden_provision`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `stock_producto`
--
ALTER TABLE `stock_producto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `camino`
--
ALTER TABLE `camino`
  ADD CONSTRAINT `FKg9yj7c0w7a8c174wd0mi7cwp3` FOREIGN KEY (`sucursal_origen_id`) REFERENCES `sucursal` (`id`),
  ADD CONSTRAINT `FKqgvtmse1d123938ehpqjkybp9` FOREIGN KEY (`sucursal_destino_id`) REFERENCES `sucursal` (`id`);

--
-- Filtros para la tabla `item_orden_provision`
--
ALTER TABLE `item_orden_provision`
  ADD CONSTRAINT `FKhc8owc9yy5bi3ippipsm0qss4` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  ADD CONSTRAINT `FKhw3u88qjfclnbtxbgvj3ojd7t` FOREIGN KEY (`ordenProvision_id`) REFERENCES `orden_provision` (`id`);

--
-- Filtros para la tabla `linea_nodos`
--
ALTER TABLE `linea_nodos`
  ADD CONSTRAINT `FK7kdk9x35qgcqpkd8yjyyq82hv` FOREIGN KEY (`camino_id`) REFERENCES `camino` (`id`);

--
-- Filtros para la tabla `nodo_sucursal`
--
ALTER TABLE `nodo_sucursal`
  ADD CONSTRAINT `FK9mfrssxuwn9siqcsi69cmolvw` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id`);

--
-- Filtros para la tabla `orden_provision`
--
ALTER TABLE `orden_provision`
  ADD CONSTRAINT `FKsjibxaxg7d5t5n9dnqvxdw7kx` FOREIGN KEY (`sucursalDestino_id`) REFERENCES `sucursal` (`id`);

--
-- Filtros para la tabla `stock_producto`
--
ALTER TABLE `stock_producto`
  ADD CONSTRAINT `FK9g0jln4joaku0bje83cssluxv` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  ADD CONSTRAINT `FKcxhgefin13j8wvq7dg9cwup0v` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id`);
COMMIT;