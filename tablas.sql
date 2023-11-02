create database reservas;

use reservas;

drop table vuelos;
create table vuelos(
	idVuelo bigint primary key unique auto_increment,
    codigoVuelo varchar(10),
    origen varchar(255),
    destino varchar(255),
    fechaPartida datetime,
    fechaLlegada datetime,
    precio double,
    asientos int,
    idTipoVuelo bigint,
    idAerolinea bigint,
    foreign key (idTipoVuelo) references tipo_vuelos(idTipoVuelo),
    foreign key (idAerolinea) references aerolineas(idAerolinea)
);

drop table reservaciones;
create table reservaciones(
	idReservacion bigint primary key auto_increment,
    idVuelo bigint,
    fechaReservacion datetime,
    numeroAsientos int,
    idPasajero bigint,
    foreign key (idPasajero) references pasajeros(idPasajero),
    foreign key (idVuelo) references vuelos(idVuelo)
);

drop table pasajeros;
create table pasajeros(
	idPasajero bigint primary key auto_increment,
	nombre varchar(100),
    apellido varchar(100),
    telefono varchar(20),
    correo varchar(50),
    pais varchar(50),
    ciudad varchar(50),
    direccion varchar(100)
);

drop table tipo_vuelos;
create table tipo_vuelos(
	idTipoVuelo bigint primary key auto_increment,
    nombre varchar(100)
);

drop table aerolineas;
create table aerolineas(
	idAerolinea bigint primary key auto_increment,
    nombre varchar(100)
);