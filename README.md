

# reserva Vuelos
El proyecto integrador del Bootcamp Makaia dedicado a la reserva de vuelos es una solución tecnológica que optimizaa el proceso de búsqueda y reserva de vuelos aéreos.

# Objetivo

Mediante el desarrollo de software y el pensamiento logico realizar la estructura Back-End de un sistema de vuelos el cual simplifique el crear reservas de vuelos aéreos, optimizando la información 
proporcionada por esta misma siendo corta, sencilla y llegando a ser objetiva para el usuario.

# Contenido del proyecto

El proyecto esta conformado por:

  1. **Paquetes:** Consta de 5 paquetes en los cuales se almacenan las siguientes funcionalidades en general:
    
      - **Controllers (Controladores):** Contiene las clases que manejan las solicitudes entrantes y las respuestas salientes de nuestra aplicación. Los controladores interactúan con el usuario o con otros sistemas, gestionando la lógica de presentación y coordinando las acciones solicitadas por el usuario.
        
      - **DTO (Data Transfer Objects):** Contiene las clases que se utilizan para transferir datos entre capas.
        
      - **Excepcion (Excepción):** En este paquete, estan las clases relacionadas con el manejo de excepciones.

      - **Models (Modelos):** Estan las clases que representan la estructura de datos y la lógica de negocio fundamental de la aplicación.
    
      - **Repositories (Repositorios):** Contiene clases o interfaces responsables de interactuar con la capa de almacenamiento de datos.
    
      - **Services (Servicios):** Los servicios contienen la lógica de negocio y funcionalidades específicas de la aplicación.
    

  2. **Conexion base de datos relacional local:** Esta base de datos tiene la capacidad de almacenar y consultar información localmente, proporcionando un entorno de almacenamiento eficiente y consultas de datos para la aplicación asociada.
  
  3. **Funcionamiento Postman:** Para facilitar el proceso de realizar solicitudes HTTP, visualizar respuestas y realizar pruebas, para la consulta y obtención de información a través de API o servicios web.

# Diagrama de clases

[![Proyecto-Gestion-Vuelos.jpg](https://i.postimg.cc/3JfGhwHw/Proyecto-Gestion-Vuelos.jpg)](https://postimg.cc/F7czgNzt)

# Diagrama de entida relación

![imagen](https://github.com/JuanPabloQB1990/reservaVuelos/assets/118224188/c328d418-8a88-4610-b81e-8a384b2a1c7b)

# Explicacion EndPoints

1. **Crear Reservas:** POST /api/vuelos/vuelo

   Este endpoint se utiliza para crear reservas de vuelos. Se espera que se envíe un JSON con la información del vuelo que se desea reservar, incluyendo origen, destino, fechas, precio, asientos disponibles, tipo de vuelo y aerolínea:


       {
          "origen": "Medellin",
          "destino": "Nueva York",
          "fechaPartida": "2023-11-05T12:55:02",
          "fechaLlegada": "2023-11-06T18:55:02",
          "precio": 2600,
          "asientos": 5,
          "tipoVuelo": {
              "idTipoVuelo": 1
          },
          "aerolinea": {
          "idAerolinea": 1
          }
        }


    El JSON describe un nuevo vuelo con información sobre su origen, destino, fechas, precio, asientos disponibles, tipo de vuelo y aerolínea. Al realizar una solicitud POST a este endpoint, se crearía una nueva reserva en la base de datos.

2. Obtener vuelo por ID: GET /api/vuelos/vuelo{id}
   
   Este endpoint se utiliza para obtener información detallada sobre una reserva específica. Se realiza una solicitud GET proporcionando el ID de la reserva:

        "GET http://localhost:8090/api/vuelos/5"
   
   La respuesta esperada es un JSON que contiene detalles específicos de la reserva identificada por el ID proporcionado:

        "{
            "codVuelo": "EA0001",
            "origen": "Bogota",
            "destino": "Nueva York",
            "fechaPartida": "2023-11-01T12:55:02",
            "fechaLlegada": "2023-11-01T18:55:02",
            "precio": 21600.0,
            "asientos": 5,
            "tipoVuelo": "publico",
            "aerolinea": "Easyfly"
        }"

   
4. Obtener Lista de Reservas por escalas Fechas y Rutas: GET /api/vuelos/criterio:

   Este endpoint se utiliza para obtener una lista paginada de reservas según criterios específicos como origen, destino, fecha de partida, etc. Se realiza una solicitud GET proporcionando parámetros de consulta:

       "GET http://localhost:8090/api/vuelos/busqueda/criterio?origen=Bogota&destino=Medellin&fechaPartida=2023-11-02&page=0&size=1"

    La respuesta esperada es un JSON que contiene una lista paginada de reservas que cumplen con los criterios proporcionados:

          ""content": [
                {
                    "idVuelo": 9,
                    "codigoVuelo": "SA0003",
                    "origen": "Bogota",
                    "destino": "Medellin",
                    "fechaPartida": "2023-11-02T12:55:02",
                    "fechaLlegada": "2023-11-02T15:55:02",
                    "precio": 2500.0,
                    "asientos": 5
                }
            ],
            "pageable": {
            "pageNumber": 0,
            "pageSize": 1,
            "sort": {
                "empty": true,
                "unsorted": true,
                "sorted": false
            },
            "offset": 0,
            "unpaged": false,
            "paged": true
          },
          "last": false,
          "totalElements": 3,
          "totalPages": 3,
          "size": 1,
          "number": 0,
          "sort": {
            "empty": true,
            "unsorted": true,
            "sorted": false
          },
          "first": true,
          "numberOfElements": 1,
          "empty": false
        }"

5. link para visualizar la API documentada
   
   "http://localhost:8090/swagger-ui/index.html"

