import React, { useState } from 'react'
import { Outlet } from 'react-router-dom'

import SalonDrawerList from './Components/SalonDrawerList'
import Navbar from './Components/Navbar'

const SalonDashboard = () => {
  const [open, setOpen] = useState(false)

  return (
    <div className="min-h-screen bg-gray-100">

      {/* Navbar */}
      <Navbar onToggle={() => setOpen(!open)} />

      <div className="flex">
        {/* Sidebar */}
        <SalonDrawerList open={open} onClose={() => setOpen(false)} />

        {/* Content */}
        <main className="flex-1 p-6 lg:ml-64">
          <Outlet /> {/* 👈 CHILD ROUTES RENDER HERE */}
        </main>
      </div>

    </div>
  )
}

export default SalonDashboard
