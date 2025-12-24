import React, { useState } from 'react'
import {
  BellIcon,
  UserCircleIcon,
  ChevronDownIcon
} from '@heroicons/react/24/outline'
import Button from '@mui/material/Button'

const Navbar = () => {

  // 🔐 Fake auth state (replace by real auth later)
  const isAuthenticated = true
  const user = {
    username: 'quang.dev',
    role: 'CUSTOMER'
  }

  const [open, setOpen] = useState(false)

  return (
    <nav className="sticky top-0 z-50 bg-white shadow-sm">
      <div className="max-w-7xl mx-auto px-5 py-3 flex items-center justify-between">

        {/* Left */}
        <div className="flex items-center gap-10">
          <h1 className="text-2xl font-bold text-green-600 cursor-pointer">
            Salon<span className="text-pink-500">Booking</span>
          </h1>

          <ul className="hidden md:flex items-center gap-6 text-gray-600 font-medium">
            <li className="hover:text-green-600 cursor-pointer">Home</li>
            <li className="hover:text-green-600 cursor-pointer">Services</li>
            <li className="hover:text-green-600 cursor-pointer">Booking</li>
            <li className="hover:text-green-600 cursor-pointer">Notification</li>
          </ul>
        </div>

        {/* Right */}
        <div className="flex items-center gap-4 relative">

          {/* Notification */}
          <div className="relative cursor-pointer">
            <BellIcon className="w-6 h-6 text-gray-600 hover:text-green-600" />
            <span className="absolute -top-1 -right-1 w-2 h-2 bg-red-500 rounded-full" />
          </div>

          {/* Auth Section */}
          {!isAuthenticated ? (
            <Button
              variant="contained"
              color="primary"
              size="small"
              sx={{ borderRadius: 2 }}
            >
              Login
            </Button>
          ) : (
            <div className="relative">
              {/* User trigger */}
              <div
                onClick={() => setOpen(!open)}
                className="flex items-center gap-2 cursor-pointer px-3 py-1 rounded-lg hover:bg-slate-100"
              >
                <UserCircleIcon className="w-7 h-7 text-green-600" />
                <span className="text-sm font-medium text-gray-700">
                  {user.username}
                </span>
                <ChevronDownIcon className="w-4 h-4 text-gray-500" />
              </div>

              {/* Dropdown */}
              {open && (
                <div className="absolute right-0 mt-2 w-56 bg-white border rounded-xl shadow-lg overflow-hidden">
                  
                  {/* User info */}
                  <div className="px-4 py-3 border-b">
                    <p className="text-sm font-semibold text-gray-800">
                      {user.username}
                    </p>
                    <p className="text-xs text-gray-500">
                      Customer Account
                    </p>
                  </div>

                  {/* Menu */}
                  <ul className="text-sm text-gray-600">
                    <li className="px-4 py-2 hover:bg-slate-100 cursor-pointer">
                      Become Partner
                    </li>
                    <li className="px-4 py-2 hover:bg-slate-100 cursor-pointer">
                      Help & Support
                    </li>
                    <li className="px-4 py-2 hover:bg-slate-100 cursor-pointer text-red-500">
                      Logout
                    </li>
                  </ul>
                </div>
              )}
            </div>
          )}
        </div>

      </div>
    </nav>
  )
}

export default Navbar
