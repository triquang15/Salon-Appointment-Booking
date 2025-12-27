import React, { useEffect } from 'react'
import { Formik, Form, Field, ErrorMessage } from 'formik'
import * as Yup from 'yup'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { registerUser } from '../Redux/Auth/action'

/* ================= VALIDATION ================= */
const SignupSchema = Yup.object({
  fullName: Yup.string().required('Full name is required'),
  username: Yup.string().required('Username is required'),
  email: Yup.string().email('Invalid email').required('Email is required'),
  password: Yup.string().min(6, 'Minimum 6 characters').required('Password is required'),
})

const SignupForm = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const { loading, error, isAuthenticated } = useSelector(
    (state) => state.auth
  )

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/salon-dashboard')
    }
  }, [isAuthenticated, navigate])

  const handleSubmit = (values, { setSubmitting }) => {
    dispatch(registerUser(values))
    setSubmitting(false)
  }

  return (
    <div>
      <h2 className="text-2xl font-bold mb-2">Create Account ✨</h2>
      <p className="text-gray-500 mb-6">Start managing your salon today</p>

      {/* Backend error */}
      {error && (
        <p className="mb-4 text-sm text-red-500 bg-red-50 p-2 rounded">
          {error}
        </p>
      )}

      <Formik
        initialValues={{
          fullName: '',
          username: '',
          email: '',
          password: '',
          role: 'CUSTOMER',
        }}
        validationSchema={SignupSchema}
        onSubmit={handleSubmit}
      >
        {({ isSubmitting, isValid }) => (
          <Form className="space-y-4">

            {/* Full Name */}
            <div>
              <label className="text-sm font-medium">Full Name</label>
              <Field
                name="fullName"
                placeholder="Nguyen Van A"
                className="w-full mt-1 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500"
              />
              <ErrorMessage name="fullName" component="p" className="text-xs text-red-500" />
            </div>

            {/* Username */}
            <div>
              <label className="text-sm font-medium">Username</label>
              <Field
                name="username"
                placeholder="nguyenvana"
                className="w-full mt-1 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500"
              />
              <ErrorMessage name="username" component="p" className="text-xs text-red-500" />
            </div>

            {/* Email */}
            <div>
              <label className="text-sm font-medium">Email</label>
              <Field
                name="email"
                type="email"
                placeholder="example@email.com"
                className="w-full mt-1 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500"
              />
              <ErrorMessage name="email" component="p" className="text-xs text-red-500" />
            </div>

            {/* Password */}
            <div>
              <label className="text-sm font-medium">Password</label>
              <Field
                name="password"
                type="password"
                placeholder="Minimum 6 characters"
                className="w-full mt-1 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500"
              />
              <ErrorMessage name="password" component="p" className="text-xs text-red-500" />
            </div>

            {/* Submit */}
            <button
              type="submit"
              disabled={!isValid || isSubmitting || loading}
              className={`w-full py-2 rounded-lg text-white transition
                ${loading || !isValid
                  ? 'bg-gray-400 cursor-not-allowed'
                  : 'bg-green-600 hover:bg-green-700'}
              `}
            >
              {loading ? 'Creating account...' : 'Register'}
            </button>

          </Form>
        )}
      </Formik>
    </div>
  )
}

export default SignupForm
