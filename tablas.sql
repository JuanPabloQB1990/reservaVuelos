create database reserva_vuelos;

use reserva_vuelos;

create table vuelos(
	idVuelo varchar(10) primary key unique,
    origen varchar(255),
    destino varchar(255),
    fechaPartida datetime,
    fechaLlegada datetime,
    precio double,
    asientos_disponibles int,
    idTipoVuelo int,
    idAerolinea int,
    foreign key (idTipoVuelo) references tipo_vuelos(idTipoVuelo),
    foreign key (idAerolinea) references aerolineas(idAerolinea)
);

create table reservaciones(
	idReservacion int primary key auto_increment,
    idVuelo int,
    fechaReservacion datetime,
    numeroAsientos int,
    idPasajero int,
    foreign key (idPasajero) references pasajeros(idPasajero)
);

create table pasajeros(
	idPasajero int primary key auto_increment,
	nombre varchar(100),
    apellido varchar(100),
    telefono varchar(20),
    correo varchar(50),
    pais varchar(50),
    ciudad varchar(50),
    direccion varchar(100)
);

create table tipo_vuelos(
	idTipoVuelo int primary key auto_increment,
    nombre varchar(100)
);

create table aerolineas(
	idAerolinea int primary key auto_increment,
    nombre varchar(100)
);