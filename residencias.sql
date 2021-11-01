-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-05-2018 a las 15:52:52
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `residencias`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asesores`
--

CREATE TABLE `asesores` (
  `idasesor` varchar(5) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `idcategoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `asesores`
--

INSERT INTO `asesores` (`idasesor`, `nombre`, `idcategoria`) VALUES
('123', 'GABRIEL ARTURO LUGO MORALES', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idcategoria` int(11) NOT NULL,
  `nombre` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idcategoria`, `nombre`) VALUES
(1, 'REDES'),
(2, 'DESARROLLO WEB'),
(3, 'MOVILES'),
(4, 'SEGURIDAD'),
(5, 'AUDITORIA DE SOFTWARE'),
(6, 'DESARROLLO DE SOFTWARE'),
(7, 'ACTIVIDADES PROFESIONALES'),
(8, 'INTERNET DE LAS COSAS'),
(9, 'DESARROLLO DE VIDEOJUEGOS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresas`
--

CREATE TABLE `empresas` (
  `idempresa` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empresas`
--

INSERT INTO `empresas` (`idempresa`, `nombre`, `telefono`, `mail`, `direccion`) VALUES
(1, 'CRIPT', '619123456', 'criptk@gmail.com', 'SIN ESPE'),
(2, 'EISEI', '6181513680', 'eiseii@gmail.com', 'CALLE AGUSTIN MELGAR #105'),
(4, 'YAZAKI', '6181111111', 'yazaki@gmail.com', 'SIN ESPECIFICAR');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyectos`
--

CREATE TABLE `proyectos` (
  `idproyecto` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `idcategoria` int(11) NOT NULL,
  `idempresa` int(11) NOT NULL,
  `cantidadest` int(11) NOT NULL,
  `observaciones` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `proyectos`
--

INSERT INTO `proyectos` (`idproyecto`, `nombre`, `idcategoria`, `idempresa`, `cantidadest`, `observaciones`) VALUES
(2, 'MOVIL PARA AUTOS', 1, 1, 1, 'NINGUNA'),
(3, 'SISTEMA DE VENTAS', 6, 2, 1, 'NINGUNA'),
(4, 'AUDITORIA', 5, 1, 2, 'NINGUNA'),
(5, 'SISTEMA DE CONTABILIDAD', 6, 1, 1, 'NINGUNA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `residentes`
--

CREATE TABLE `residentes` (
  `nocontrol` varchar(10) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `idasesor` varchar(5) DEFAULT NULL,
  `idproyecto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `residentes`
--

INSERT INTO `residentes` (`nocontrol`, `nombre`, `idasesor`, `idproyecto`) VALUES
('16041201', 'HELADIO AMAYA COLACION', NULL, NULL),
('16041247', 'JAIRO  RUIZ ARAMBULA', NULL, NULL),
('16041248', 'BERNARDO SALINAS JAQUEZ', NULL, NULL),
('16041249', 'EDWIN ISAAC SERRANO TORRES', NULL, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asesores`
--
ALTER TABLE `asesores`
  ADD PRIMARY KEY (`idasesor`),
  ADD KEY `idcategoria` (`idcategoria`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idcategoria`);

--
-- Indices de la tabla `empresas`
--
ALTER TABLE `empresas`
  ADD PRIMARY KEY (`idempresa`);

--
-- Indices de la tabla `proyectos`
--
ALTER TABLE `proyectos`
  ADD PRIMARY KEY (`idproyecto`),
  ADD KEY `idempresa` (`idempresa`),
  ADD KEY `idcategoria` (`idcategoria`);

--
-- Indices de la tabla `residentes`
--
ALTER TABLE `residentes`
  ADD PRIMARY KEY (`nocontrol`),
  ADD KEY `idproyecto` (`idproyecto`),
  ADD KEY `idasesor` (`idasesor`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idcategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `empresas`
--
ALTER TABLE `empresas`
  MODIFY `idempresa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `proyectos`
--
ALTER TABLE `proyectos`
  MODIFY `idproyecto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asesores`
--
ALTER TABLE `asesores`
  ADD CONSTRAINT `asesores_ibfk_1` FOREIGN KEY (`idcategoria`) REFERENCES `categoria` (`idcategoria`);

--
-- Filtros para la tabla `proyectos`
--
ALTER TABLE `proyectos`
  ADD CONSTRAINT `proyectos_ibfk_1` FOREIGN KEY (`idempresa`) REFERENCES `empresas` (`idempresa`),
  ADD CONSTRAINT `proyectos_ibfk_2` FOREIGN KEY (`idempresa`) REFERENCES `empresas` (`idempresa`),
  ADD CONSTRAINT `proyectos_ibfk_3` FOREIGN KEY (`idcategoria`) REFERENCES `categoria` (`idcategoria`);

--
-- Filtros para la tabla `residentes`
--
ALTER TABLE `residentes`
  ADD CONSTRAINT `residentes_ibfk_1` FOREIGN KEY (`idproyecto`) REFERENCES `proyectos` (`idproyecto`),
  ADD CONSTRAINT `residentes_ibfk_2` FOREIGN KEY (`idasesor`) REFERENCES `asesores` (`idasesor`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
