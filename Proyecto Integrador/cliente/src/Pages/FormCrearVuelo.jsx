import React, { useEffect, useState } from 'react'

const FormCrearVuelo = () => {
    const [tipoVuelos, setTipoVuelos] = useState([]);
    const [aerolineas, setAerolineas] = useState([]);

    useEffect(() => {
        const cargarTipoVuelos = async(urlTipoVuelos, urlAerolineas) => {
            const options = {
                method: "GET"
            };

            const [resTipoVuelos, resAerolineas] = await Promise.all([
                fetch(urlTipoVuelos, options),
                fetch(urlAerolineas, options),
            ]);
            const dataTipoVuelos = await resTipoVuelos.json();
            const dataAerolineas = await resAerolineas.json();
      
            setTipoVuelos(dataTipoVuelos);
            setAerolineas(dataAerolineas);
            
            
        }
        const urlTipoVuelos = "http://localhost:8090/v1/typeFlights"
        const urlAerolineas = "http://localhost:8090/v1/aerolineas"

        
        cargarTipoVuelos(urlTipoVuelos, urlAerolineas);
    }, []);

    const [formVuelos, setFormVuelos] = useState({
        origen: null,
        destino:null,
        fechaPartida:null,
        fechaLlegada:null,
        precio: null,
        asientos: null,
        idTipoVuelo: null,
        idAerolinea: null
    });

    const handleChange = (e) => {
        setFormVuelos({
            ...formVuelos,
            [e.target.name]:e.target.value,
        });
    }
    
    const handleSubmit = async(e) => {
      e.preventDefault();
      
      const formEnvio = {
        origen: formVuelos.origen,
        destino:formVuelos.destino,
        fechaPartida:formVuelos.fechaPartida,
        fechaLlegada:formVuelos.fechaLlegada,
        precio: formVuelos.precio,
        asientos: formVuelos.asientos,
        tipoVuelo: {
            idTipoVuelo: formVuelos.idTipoVuelo
        },
        aerolinea: {
            idAerolinea: formVuelos.idAerolinea
        }
      }
      
      const options = {
        method: "POST",
        headers: { "Content-Type": "application/json"},
        body: JSON.stringify(formEnvio)
      };
      const response = await fetch("http://localhost:8090/v1/flights", options)
      const data = await response.json();
      console.log(data);
    }
    


  return (
    <div>
      <form onSubmit={handleSubmit}>
        <label htmlFor="origen">Origen</label>
        <input value={formVuelos.origen} onChange={handleChange}  type="text" name="origen" id="origen"/>

        <label htmlFor="destino">Destino</label>
        <input value={formVuelos.destino} onChange={handleChange} type="text" name="destino" id="destino"/>

        <label htmlFor="fechaPartida">Fecha Partida</label>
        <input value={formVuelos.fechaPartida} onChange={handleChange} type="datetime-local" min={Date.now()} name="fechaPartida" id="fechaPartida"/>

        <label htmlFor="fechaLlegada">Fecha Llegada</label>
        <input value={formVuelos.fechaLlegada} onChange={handleChange} type="datetime-local" min={new Date().toISOString} name="fechaLlegada" id="fechaLlegada"/>

        <label htmlFor="precio">Precio</label>
        <input value={formVuelos.precio} onChange={handleChange} type="number" name="precio" id="precio" />

        <label htmlFor="numAsientos">Asientos</label>
        <input value={formVuelos.numAsientos} onChange={handleChange} type="number" name="asientos" id="numAsientos" />

        <label htmlFor="tipoVuelo">Seleccione el tipo de vuelo</label>
        <select onChange={handleChange} name="idTipoVuelo" id="tipoVuelo">
            <option value="">--</option>
            {tipoVuelos.map((tipoVuelo, key)=>{
              return <option key={key} value={tipoVuelo.idTipoVuelo}>{tipoVuelo.nombre}</option>
                    

            })}
        </select>
        <label htmlFor="aerolinea">Seleccione la Aerolinea</label>
        <select onChange={handleChange} name="idAerolinea" id="aerolinea">
        <option value="">--</option>
            {aerolineas.map((aerolinea, key)=>{
              return <option key={key} value={aerolinea.idAerolinea}>{aerolinea.nombre}</option>
            })}
        </select>
        <input type="submit" value="Crear Vuelo" />
      </form>
    </div>
  )
}

export default FormCrearVuelo
