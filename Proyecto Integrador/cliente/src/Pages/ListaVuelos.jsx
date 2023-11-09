import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';

const ListaVuelos = () => {

    const {page, size} = useParams()
    const [listaVuelos, setListaVuelos] = useState([]);

    useEffect(() => {
        const obtenerVuelos = async(url, options) => {
          const response = await fetch(url, options);
          const data = await response.json();
          setListaVuelos(data);
        }

        const options = {
            method: "GET"
        };
        

        obtenerVuelos(`http://localhost:8090/v1/flights/all?page=${page}&size=${size}`, options)
    }, []);

    console.log(page);
    console.log(listaVuelos);
  return (
    <div>
      
    </div>
  )
}

export default ListaVuelos
