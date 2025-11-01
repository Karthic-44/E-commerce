import './App.css'
import Home from './components/home/Home'
import Products from './components/products/Products'
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom'

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Home/>} />
        <Route path='/' element={<Products/>} />
      </Routes>
    </Router>  
  )
}

export default App
