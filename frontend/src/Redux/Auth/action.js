import api from "../../config/api";
import {
    REGISTER_REQUEST,
    REGISTER_SUCCESS,
    REGISTER_FAILURE,
    LOGIN_SUCCESS,
    LOGOUT,
    FETCH_USER_FAILURE,
    FETCH_USER_SUCCESS,
    FETCH_USER_REQUEST,
    LOGIN_FAILURE,
    LOGIN_REQUEST,
} from "./actionTypes";

// Handle user registration
export const registerUser = (userData) => async (dispatch) => {
    dispatch({ type: REGISTER_REQUEST });

    try {
        const response = await api.post("/auth/signup", userData);

        const authData = response.data;

        // Save tokens if returned
        if (authData?.accessToken) {
            localStorage.setItem("token", authData.accessToken);
        }
        if (authData?.refreshToken) {
            localStorage.setItem("refreshToken", authData.refreshToken);
        }

        dispatch({
            type: REGISTER_SUCCESS,
            payload: authData,
        });

        console.log("Registered user:", authData);

    } catch (error) {
        dispatch({
            type: REGISTER_FAILURE,
            payload:
                error.response?.data?.message ||
                error.response?.data ||
                error.message,
        });
    }
};

export const loginUser = (loginData) => async (dispatch) => {
    dispatch({ type: LOGIN_REQUEST });

    try {
        const response = await api.post("/auth/login", loginData);
        const authData = response.data;

        // Save tokens
        if (authData?.accessToken) {
            localStorage.setItem("token", authData.accessToken);
        }
        if (authData?.refreshToken) {
            localStorage.setItem("refreshToken", authData.refreshToken);
        }

        dispatch({
            type: LOGIN_SUCCESS,
            payload: authData,
        });
        console.log("Logged in user:", authData);

    } catch (error) {
        dispatch({
            type: LOGIN_FAILURE,
            payload:
                error.response?.data?.message ||
                error.response?.data ||
                error.message,
        });
    }
};


/* ================= FETCH CURRENT USER ================= */
/**
 * Use this if you have /users/me or similar endpoint
 * If not available yet, you can skip this
 */
export const fetchCurrentUser = () => async (dispatch) => {
    dispatch({ type: FETCH_USER_REQUEST });

    try {
        const response = await api.get("/api/users/profile");

        dispatch({
            type: FETCH_USER_SUCCESS,
            payload: response.data,
        });
        console.log("Fetched user:", response.data);

    } catch (error) {
        dispatch({
            type: FETCH_USER_FAILURE,
            payload:
                error.response?.data?.message ||
                error.response?.data ||
                error.message,
        });
    }
};

/* ================= LOGOUT ================= */
export const logoutUser = () => (dispatch) => {
    localStorage.removeItem("token");
    localStorage.removeItem("refreshToken");

    dispatch({ type: LOGOUT });
};

/* ================= REFRESH TOKEN ================= */
export const refreshToken = () => async (dispatch) => {
    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
        dispatch({ type: LOGOUT });
        return;
    }

    try {
        const response = await api.get(`/auth/refresh-token/${refreshToken}`);
        const authData = response.data;

        localStorage.setItem("token", authData.accessToken);

        dispatch({
            type: LOGIN_SUCCESS,
            payload: authData,
        });

    } catch (error) {
        dispatch({ type: LOGOUT });
    }
};
