import { configureStore, combineReducers } from "@reduxjs/toolkit";
import accountSlice from "./account";
import supermarketSlice from "./supermarket";
import categorySlice from "./category";
import caddieSlice from "./caddie";
import groceryListSlice from "./groceryList";
import updateSlice from "./update";

export const { connectAccount, disconnectAccount } = accountSlice.actions;
export const { setSupermarket } = supermarketSlice.actions;
export const { setCategory } = categorySlice.actions;
export const { connectCaddie } = caddieSlice.actions;
export const { associateGroceryList, updateGroceryList } = groceryListSlice.actions;
export const { hasToBeUpdate, hasToBeUpdateTemplates } = updateSlice.actions;

const rootReducer = combineReducers({
    account: accountSlice.reducer,
    currentSupermarket: supermarketSlice.reducer,
    products: categorySlice.reducer,
    caddie: caddieSlice.reducer,
    associatedGroceryList: groceryListSlice.reducer,
    update: updateSlice.reducer,
});

const store = configureStore({
    reducer: rootReducer,
});

export default store;
