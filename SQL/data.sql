data.sql-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-07-2023 a las 16:53:38
-- Versión del servidor: 10.4.28-MariaDB
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

-- --------------------------------------------------------
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
-- Volcado de datos para la tabla `orden_provision`
--

INSERT INTO `orden_provision` (`estado`, `fecha`, `tiempo_maximo`, `id`, `sucursalDestino_id`, `camino`, `camino_asignado`) VALUES
(1, '29-07-2023', '24:00', 4, 68, NULL, 'Puerto  >  A  >  D'),
(0, '29-07-2023', '08:00', 5, 69, NULL, NULL);

-- --------------------------------------------------------

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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
