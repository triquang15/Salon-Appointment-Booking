import { thunk } from "redux-thunk";
import { applyMiddleware, combineReducers, legacy_createStore } from "redux";
import { salonReducer } from "./Salon/reducer";
import authReducer from "./Auth/reducer";


const rootReducer = combineReducers({
    salon: salonReducer,
    auth: authReducer,
});

export const store = legacy_createStore(rootReducer, applyMiddleware(thunk));