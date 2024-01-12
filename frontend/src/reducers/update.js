import { createSlice } from "@reduxjs/toolkit";

const updateSlice = createSlice({
  name: "update",
  initialState: {
    needUpdate : false,
    needUpdateTemplates : false,
  },
  reducers: {
    hasToBeUpdate: (state, action) => {
      state.needUpdate = action.payload;
    },
    hasToBeUpdateTemplates: (state, action) => {
      state.needUpdateTemplates = action.payload;
    }
  },
});

export default updateSlice;
