-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-07-2023 a las 16:53:38
-- Versión del servidor: 10.4.28-MariaDBdata.sql
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `died_tp_2023`
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


-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `linea_nodos`
--

CREATE TABLE `linea_nodos` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `camino_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
