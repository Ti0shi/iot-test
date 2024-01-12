import { createSlice } from "@reduxjs/toolkit";

const categorySlice = createSlice({
    name: "products",
    initialState: {
        category: null,
    },
    reducers: {
      setCategory: (state, action) => {
        state.category = action.payload;
      },
    }
});

export default categorySlice;