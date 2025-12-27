import React from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../Redux/Auth/action";

const LoginSchema = Yup.object({
  email: Yup.string()
    .email("Invalid email")
    .required("Email is required"),
  password: Yup.string()
    .min(6, "Minimum 6 characters")
    .required("Password is required"),
});

const LoginForm = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { isLoading: loading, error } = useSelector(
    (state) => state.auth
  );

  const handleSubmit = (values) => {
    dispatch(
      loginUser({
        data: values,
        navigate,
      })
    );
  };

  return (
    <div>
      <h2 className="text-2xl font-bold mb-2">Welcome Back 👋</h2>
      <p className="text-gray-500 mb-6">Login to manage your salon</p>

      {error && (
        <p className="mb-4 text-sm text-red-500 bg-red-50 p-2 rounded">
          {error?.response?.data?.message || "Login failed"}
        </p>
      )}

      <Formik
        initialValues={{ email: "", password: "" }}
        validationSchema={LoginSchema}
        onSubmit={handleSubmit}
      >
        {({ isSubmitting, isValid }) => (
          <Form className="space-y-4">

            <div>
              <label className="text-sm font-medium">Email</label>
              <Field
                name="email"
                type="email"
                className="w-full mt-1 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500"
              />
              <ErrorMessage
                name="email"
                component="p"
                className="text-xs text-red-500"
              />
            </div>

            <div>
              <label className="text-sm font-medium">Password</label>
              <Field
                name="password"
                type="password"
                className="w-full mt-1 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500"
              />
              <ErrorMessage
                name="password"
                component="p"
                className="text-xs text-red-500"
              />
            </div>

            <button
              type="submit"
              disabled={!isValid || isSubmitting || loading}
              className={`w-full py-2 rounded-lg text-white
                ${
                  loading
                    ? "bg-gray-400 cursor-not-allowed"
                    : "bg-green-600 hover:bg-green-700"
                }
              `}
            >
              {loading ? "Logging in..." : "Login"}
            </button>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default LoginForm;
