import {
    LOGIN_REQUEST,
    LOGIN_SUCCESS,
    LOGIN_FAILURE,
    LOGOUT,
    REGISTER_REQUEST,
    REGISTER_SUCCESS,
    REGISTER_FAILURE,
    FETCH_USER_REQUEST,
    FETCH_USER_SUCCESS,
    FETCH_USER_FAILURE,
} from "./actionTypes";

const token = localStorage.getItem("token");

const initialState = {
    loading: false,
    user: null,
    token: token || null,
    isAuthenticated: !!token,
    error: null,
};

const authReducer = (state = initialState, action) => {
    switch (action.type) {

        /* ================= REGISTER ================= */
        case REGISTER_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };

        case REGISTER_SUCCESS:
            return {
                ...state,
                loading: false,
                user: action.payload?.user || null,
                token: action.payload?.accessToken || null,
                isAuthenticated: true,
                error: null,
            };

        case REGISTER_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
            };

        /* ================= LOGIN ================= */
        case LOGIN_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };

        case LOGIN_SUCCESS:
            return {
                ...state,
                loading: false,
                user: action.payload?.user || null,
                token: action.payload?.accessToken || null,
                isAuthenticated: true,
                error: null,
            };

        case LOGIN_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
                isAuthenticated: false,
            };

        /* ================= FETCH USER ================= */
        case FETCH_USER_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };

        case FETCH_USER_SUCCESS:
            return {
                ...state,
                loading: false,
                user: action.payload,
                isAuthenticated: true,
            };

        case FETCH_USER_FAILURE:
            return {
                ...state,
                loading: false,
                user: null,
                isAuthenticated: false,
                error: action.payload,
            };

        /* ================= LOGOUT ================= */
        case LOGOUT:
            localStorage.removeItem("token");
            localStorage.removeItem("refreshToken");

            return {
                ...state,
                loading: false,
                user: null,
                token: null,
                isAuthenticated: false,
                error: null,
            };

        default:
            return state;
    }
};

export default authReducer;
