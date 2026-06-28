-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 28, 2026 at 08:28 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `toko_pakaian`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_toko`
--

CREATE TABLE `admin_toko` (
  `id_admin` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `nama_toko` varchar(100) DEFAULT NULL,
  `banner` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` int(11) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `nama_kategori`) VALUES
(1, 'Sepatu'),
(2, 'Celana'),
(3, 'Hoodie'),
(4, 'Kaos'),
(5, 'Jaket'),
(6, 'Kemeja');

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `id_produk` int(11) NOT NULL,
  `nama_produk` varchar(100) NOT NULL,
  `harga` decimal(10,2) NOT NULL,
  `kategori` enum('Sepatu','Hoodie','Kaos','Celana','Kemeja','Jaket') DEFAULT NULL,
  `deskripsi` text DEFAULT NULL,
  `gambar` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(20) DEFAULT 'aktif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`id_produk`, `nama_produk`, `harga`, `kategori`, `deskripsi`, `gambar`, `created_at`, `status`) VALUES
(1, 'hoodie black', 3000000.00, 'Hoodie', 'bagus no minus', 'C:\\Users\\LOQ\\OneDrive\\Documents\\produk penjual\\HoodieThrift.jpg', '2026-06-25 03:30:11', 'aktif'),
(2, 'Seneakers', 40000000.00, 'Sepatu', 'bagus no minus', 'C:\\Users\\LOQ\\OneDrive\\Documents\\produk penjual\\SepatuThrift.jpeg', '2026-06-25 15:07:50', 'aktif'),
(3, 'celana chinos', 4000000.00, 'Celana', 'bagus minus luntur sedikit', 'C:\\Users\\LOQ\\OneDrive\\Documents\\produk penjual\\CelanaThrift.jpg', '2026-06-28 01:27:54', 'aktif'),
(4, 'jaket parca', 5000000.00, 'Jaket', 'no minus', 'C:\\Users\\LOQ\\OneDrive\\Documents\\produk penjual\\JaketThrift.jpg', '2026-06-28 01:32:01', 'aktif'),
(5, 'kaos adidas original', 300000.00, 'Kaos', 'no minus ', 'C:\\Users\\LOQ\\OneDrive\\Documents\\produk penjual\\KaosThrift.jpg', '2026-06-28 01:32:32', 'aktif'),
(6, 'kemeja fanel', 400000.00, 'Kemeja', 'no minus', 'C:\\Users\\LOQ\\OneDrive\\Documents\\produk penjual\\KemejaThrift.jpg', '2026-06-28 01:33:17', 'aktif');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(100) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `alamat_email` varchar(50) NOT NULL,
  `nomor_telp` varchar(20) DEFAULT NULL,
  `jenis_kelamin` varchar(20) DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `password` varchar(225) NOT NULL,
  `Foto` varchar(255) DEFAULT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'user',
  `banner_toko` varchar(255) DEFAULT NULL,
  `banner_promosi` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `nama`, `alamat_email`, `nomor_telp`, `jenis_kelamin`, `tanggal_lahir`, `password`, `Foto`, `role`, `banner_toko`, `banner_promosi`) VALUES
(7, 'Toko Irul', 'tokoirul@gmail.com', '08123456789', 'Laki - Laki', '2007-02-17', '401e2c743359aea95be9ba8a7deac6ed88413f28ec0ad281af82be0a2aadaeef', 'C:\\Users\\LOQ\\Downloads\\Logo.jpeg', 'Admin', 'C:\\Users\\LOQ\\Downloads\\BannerToko.png', 'C:\\Users\\LOQ\\Downloads\\Banner (2).png'),
(8, 'irul', 'irul@gmail.com', '08123466789', 'Laki - Laki', '2006-06-01', '41e48c21f52bc3f1bd646190373c2e98a475e0d4a01fed5310ef349c1e259720', 'C:\\Users\\LOQ\\OneDrive\\Documents\\Pictures\\foto\\WhatsApp Image 2025-07-01 at 19.29.59_33a6566a.jpg', 'User', NULL, NULL),
(9, NULL, 'sulist@gmail.com', NULL, NULL, NULL, '9e89404fd6b822dd4ce29081ce02766e138909bd72c66c351428f28494800609', NULL, 'User', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_toko`
--
ALTER TABLE `admin_toko`
  ADD PRIMARY KEY (`id_admin`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`),
  ADD KEY `id_kategori` (`kategori`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_toko`
--
ALTER TABLE `admin_toko`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `produk`
--
ALTER TABLE `produk`
  MODIFY `id_produk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin_toko`
--
ALTER TABLE `admin_toko`
  ADD CONSTRAINT `admin_toko_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
