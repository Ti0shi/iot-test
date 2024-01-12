import { createSlice } from "@reduxjs/toolkit";

const accountSlice = createSlice({
    name: "account",
    initialState: {
        user: null,
    },
    reducers: {
        connectAccount: (state, action) => {
            state.user = action.payload;
        },
        disconnectAccount: (state) => {
            state.user = null;
        }
    },
});

export default accountSlice;
