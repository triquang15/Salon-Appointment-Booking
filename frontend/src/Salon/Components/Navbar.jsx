import React from 'react'
import { Bars3Icon } from '@heroicons/react/24/outline'

const Navbar = ({ onToggle }) => {
  return (
    <header className="h-16 bg-white border-b flex items-center px-4 lg:px-6 sticky top-0 z-30">
      
      {/* Mobile Toggle */}
      <button
        onClick={onToggle}
        className="lg:hidden mr-3 text-gray-700"
      >
        <Bars3Icon className="w-6 h-6" />
      </button>

      <h2 className="text-lg font-semibold text-gray-800">
        Salon Dashboard
      </h2>

      <div className="ml-auto flex items-center gap-4">
        <span className="text-sm text-gray-500">Admin</span>
        <img
          src="https://i.pravatar.cc/40"
          alt="avatar"
          className="w-8 h-8 rounded-full"
        />
      </div>
    </header>
  )
}

export default Navbar
