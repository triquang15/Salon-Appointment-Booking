import api from "../../config/api";
import {
    CREATE_SALON_FAILURE,
    CREATE_SALON_REQUEST,
    CREATE_SALON_SUCCESS,
    FETCH_SALON_BY_ID_FAILURE,
    FETCH_SALON_BY_ID_REQUEST,
    FETCH_SALON_BY_ID_SUCCESS,
    FETCH_SALON_BY_OWNER_FAILURE,
    FETCH_SALON_BY_OWNER_REQUEST,
    FETCH_SALON_BY_OWNER_SUCCESS,
    FETCH_SALON_FAILURE,
    FETCH_SALON_REQUEST,
    FETCH_SALON_SUCCESS,
    SEARCH_SALON_FAILURE,
    SEARCH_SALON_REQUEST,
    SEARCH_SALON_SUCCESS,
    UPDATE_SALON_FAILURE,
    UPDATE_SALON_REQUEST,
    UPDATE_SALON_SUCCESS,
} from "./actionTypes";

/* ================= CREATE SALON ================= */
export const createSalon = (salonData) => async (dispatch) => {
    dispatch({ type: CREATE_SALON_REQUEST });

    try {
        const response = await api.post("/api/salons", salonData);

        dispatch({
            type: CREATE_SALON_SUCCESS,
            payload: response.data,
        });
    } catch (error) {
        dispatch({
            type: CREATE_SALON_FAILURE,
            payload: error.response?.data || error.message,
        });
    }
};

/* ================= UPDATE SALON ================= */
export const updateSalon = (salonId, salon) => async (dispatch) => {
    dispatch({ type: UPDATE_SALON_REQUEST });

    try {
        const response = await api.put(`/api/salons/${salonId}`, salon);

        dispatch({
            type: UPDATE_SALON_SUCCESS,
            payload: response.data,
        });
    } catch (error) {
        dispatch({
            type: UPDATE_SALON_FAILURE,
            payload: error.response?.data || error.message,
        });
    }
};

/* ================= FETCH ALL SALONS ================= */
export const fetchSalon = () => async (dispatch) => {
    dispatch({ type: FETCH_SALON_REQUEST });

    try {
        const response = await api.get("/api/salons");

        dispatch({
            type: FETCH_SALON_SUCCESS,
            payload: response.data,
        });
    } catch (error) {
        dispatch({
            type: FETCH_SALON_FAILURE,
            payload: error.response?.data || error.message,
        });
    }
};

/* ================= FETCH BY ID ================= */
export const fetchSalonById = (salonId) => async (dispatch) => {
    dispatch({ type: FETCH_SALON_BY_ID_REQUEST });

    try {
        const response = await api.get(`/api/salons/${salonId}`);

        dispatch({
            type: FETCH_SALON_BY_ID_SUCCESS,
            payload: response.data,
        });
    } catch (error) {
        dispatch({
            type: FETCH_SALON_BY_ID_FAILURE,
            payload: error.response?.data || error.message,
        });
    }
};

/* ================= FETCH BY OWNER ================= */
export const fetchSalonByOwner = () => async (dispatch) => {
    dispatch({ type: FETCH_SALON_BY_OWNER_REQUEST });

    try {
        const response = await api.get("/api/salons/owner");

        dispatch({
            type: FETCH_SALON_BY_OWNER_SUCCESS,
            payload: response.data,
        });
    } catch (error) {
        dispatch({
            type: FETCH_SALON_BY_OWNER_FAILURE,
            payload: error.response?.data || error.message,
        });
    }
};

/* ================= SEARCH ================= */
export const searchSalon = (city) => async (dispatch) => {
    dispatch({ type: SEARCH_SALON_REQUEST });

    try {
        const response = await api.get("/api/salons/search", {
            params: { city },
        });

        dispatch({
            type: SEARCH_SALON_SUCCESS,
            payload: response.data,
        });
    } catch (error) {
        dispatch({
            type: SEARCH_SALON_FAILURE,
            payload: error.response?.data || error.message,
        });
    }
};
