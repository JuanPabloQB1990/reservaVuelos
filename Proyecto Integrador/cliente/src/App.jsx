import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom"
import FormCrearVuelo from './Pages/FormCrearVuelo'
import FormBuscarVuelos from './Pages/FormBuscarVuelos'
import ListaVuelos from './Pages/ListaVuelos'

function App() {
  

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/vuelos/crear' element={<FormCrearVuelo/>}/>
        <Route path='/vuelos/buscar' element={<FormBuscarVuelos/>}/>
        <Route path='/vuelos/:page/:size' element={<ListaVuelos/>}/>

      </Routes>
    </BrowserRouter>
  )
}

export default App
