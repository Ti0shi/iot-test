import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigation } from "@react-navigation/native";
import ConnexionPage from "./ConnexionPage";

export default function Index() {
    const dispatch = useDispatch();
    const navigation = useNavigation();

    return (
        <>
            <ConnexionPage navigation={navigation} dispatch={dispatch} />
        </>
    );
}
