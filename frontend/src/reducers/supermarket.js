import { createSlice } from "@reduxjs/toolkit";

const supermarketSlice = createSlice({
    name: "currentSupermarket",
    initialState: {
        supermarket: null,
    },
    reducers: {
      setSupermarket: (state, action) => {
        state.supermarket = action.payload;
      },
    }
});

export default supermarketSlice;