import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Home from '../Customer/Home/Home'
import Booking from '../Customer/Booking/Booking'
import SalonDetails from '../Salon/SalonDetails/SalonDetails'
import Notification from '../Customer/Notification/Notification'
import Navbar from '../Customer/Navbar/Navbar'
import NotFound from '../ErrorPage/NotFound'

const CustomerRoutes = () => {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/bookings" element={<Booking />} />
        <Route path="/notification" element={<Notification />} />
        <Route path="/salon/:id" element={<SalonDetails />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  )
}

export default CustomerRoutes
