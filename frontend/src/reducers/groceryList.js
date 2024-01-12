import { createSlice } from "@reduxjs/toolkit";

const groceryListSlice = createSlice({
  name: "associatedGroceryList",
  initialState: {
    groceryList: null,
  },
  reducers: {
    associateGroceryList: (state, action) => {
      state.groceryList = action.payload;
    },
    updateGroceryList: (state, action) => {
      if (state.groceryList === null) {
        return;
      }
      state.groceryList.groceries = action.payload;
    }
  }
});

export default groceryListSlice;