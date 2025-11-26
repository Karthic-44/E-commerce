import './App.css'
import Home from './components/home/Home'
import Products from './components/products/Products'
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from './components/shared/Navbar'
import About from './components/About'
import Contact from './components/Contact'
import React from 'react'
import {Toaster} from 'react-hot-toast';
import Cart from './components/cart/Cart'
import LogIn from './components/auth/Login'
import PrivateRoute from './components/PrivateRoute'
import Register from './components/auth/Register'
import Checkout from './components/checkout/Checkout'
import AdminLayout from './components/admin/AdminLayout'
import Dashboard from './components/admin/dashboard/Dashboard'
import AdminProducts from './components/admin/products/AdminProducts'
import Sellers from './components/admin/sellers/Sellers'
import Category from './components/admin/categories/Category'
import Orders from './components/admin/orders/Orders'

function App() {
  return (
    <React.Fragment>
      <Router>
        <Navbar />
        <Routes>
          <Route path='/' element={<Home/>} />
          <Route path='/products' element={<Products/>} />
          <Route path='/about' element={<About />} />
          <Route path='/contact' element={<Contact />} />
          <Route path='/cart' element={<Cart />} />
          

                <Route path='/' element={< PrivateRoute/>}>
                  <Route path='/checkout' element={<Checkout />} />
                </Route>

          <Route path='/' element={< PrivateRoute publicPage />}>
            <Route path='/login' element={<LogIn />} />
            <Route path='/register' element={<Register />} />
          </Route>

          
          <Route path='/' element={<PrivateRoute adminOnly />}>
            <Route path='/admin' element={ <AdminLayout />}>
              <Route path='' element={<Dashboard />} />
              <Route path='products' element={<AdminProducts />} />
              <Route path='sellers' element={<Sellers />} />
              <Route path='orders' element={<Orders />} />
              <Route path='categories' element={<Category />} />
            </Route>
            
          </Route>

        </Routes>
      </Router>
      <Toaster position= 'bottom-center' /> 
    </React.Fragment>
  )
}

export default App
