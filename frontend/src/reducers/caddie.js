import { createSlice } from "@reduxjs/toolkit";

const caddieSlice = createSlice({
  name: "caddie",
  initialState: {
    usedCaddie : null,
  },
  reducers: {
    connectCaddie: (state, action) => {
      state.usedCaddie = action.payload;
    },
  },
});

export default caddieSlice;
