import React from 'react'
import { useLocation, Link } from 'react-router-dom'
import LoginForm from './LoginForm'
import SignupForm from './SignupForm'

const Auth = () => {
  const location = useLocation()
  const isRegister = location.pathname === '/register'

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-100 to-green-300">
      <div className="bg-white rounded-2xl shadow-xl w-full max-w-4xl flex overflow-hidden">

        {/* LEFT IMAGE / BRAND */}
        <div className="hidden md:flex w-1/2 bg-green-600 text-white p-10 flex-col justify-between">
          <div>
            <h1 className="text-3xl font-bold">Salon Manager</h1>
            <p className="mt-3 text-green-100">
              Manage bookings, services and customers easily.
            </p>
          </div>

          <div className="text-sm text-green-200">
            © 2025 Salon System
          </div>
        </div>

        {/* RIGHT FORM */}
        <div className="w-full md:w-1/2 p-8">
          {isRegister ? <SignupForm /> : <LoginForm />}

          <div className="mt-6 text-center text-sm">
            {isRegister ? (
              <>
                Already have an account?{" "}
                <Link to="/login" className="text-green-600 font-medium hover:underline">
                  Login
                </Link>
              </>
            ) : (
              <>
                Don’t have an account?{" "}
                <Link to="/register" className="text-green-600 font-medium hover:underline">
                  Register
                </Link>
              </>
            )}
          </div>
        </div>

      </div>
    </div>
  )
}

export default Auth
