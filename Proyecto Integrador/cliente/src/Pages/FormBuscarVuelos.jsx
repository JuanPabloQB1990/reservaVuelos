import React, { useState } from 'react'

const FormBuscarVuelos = () => {
    const [formBuscar, setformBuscar] = useState({
        origen: null,
        destino:null,
        fechaPartida:null,
    });
    const [vuelos, setVuelos] = useState([]);

    const handleChange = (e) => {
      setformBuscar({...formBuscar, [e.target.name]: e.target.value});
    }

    const handleSubmit = async(e) => {
      e.preventDefault();
      console.log(formBuscar);
      const { origen, destino, fechaPartida } = formBuscar; 

      const options = {
        method: "GET"
      };

      if (fechaPartida == null) {

        const response = await fetch(`http://localhost:8090/v1/flights?origen=${origen}&destino=${destino}`,options)
        const data = await response.json();
        setVuelos(data);

      } else {

        const response = await fetch(`http://localhost:8090/v1/flights?origen=${origen}&destino=${destino}&fechaPartida=${fechaPartida}`, options)
        const data = await response.json();
        setVuelos(data);

      }
    }
    
    console.log(vuelos);
    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label htmlFor="origen">Origen</label>
                <input value={formBuscar.origen} onChange={handleChange}  type="text" name="origen" id="origen"/>

                <label htmlFor="destino">Destino</label>
                <input value={formBuscar.destino} onChange={handleChange} type="text" name="destino" id="destino"/>

                <label htmlFor="fechaPartida">Fecha Partida</label>
                <input value={formBuscar.fechaPartida} onChange={handleChange} type="date" name="fechaPartida" id="fechaPartida"/>
                
                <input type="submit" value="Buscar" />
            </form>
        </div>
    )
}

export default FormBuscarVuelos
