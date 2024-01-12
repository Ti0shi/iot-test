import React from "react";

import { NavigationContainer } from "@react-navigation/native";
import Tabs from "./src/navigation/Tabs";
import { useFonts } from "expo-font";
import { StatusBar } from "expo-status-bar";
import * as SplashScreen from "expo-splash-screen";
import {Provider, useSelector} from "react-redux";
import store from "./src/reducers/store";

function App() {
    const [fontsLoaded] = useFonts({
        "IBMPlexSans-SemiBold": require("./assets/fonts/IBMPlexSans-SemiBold.ttf"),
        "IBMPlexSans-Bold": require("./assets/fonts/IBMPlexSans-Bold.ttf"),
        "IBMPlexSans-Medium": require("./assets/fonts/IBMPlexSans-Medium.ttf"),
        "IBMPlexSans-Regular": require("./assets/fonts/IBMPlexSans-Regular.ttf"),
        "IBMPlexSans-Light": require("./assets/fonts/IBMPlexSans-Light.ttf"),
        "IBMPlexSans-Thin": require("./assets/fonts/IBMPlexSans-Thin.ttf"),
        "IBMPlexSans-ExtraLight": require("./assets/fonts/IBMPlexSans-ExtraLight.ttf"),
        "IBMPlexSans-MediumItalic": require("./assets/fonts/IBMPlexSans-MediumItalic.ttf"),
        "IBMPlexSans-LightItalic": require("./assets/fonts/IBMPlexSans-LightItalic.ttf"),
    });

    const onLayoutRootView = React.useCallback(async () => {
        if (fontsLoaded) {
            await SplashScreen.hideAsync();
        }
    }, [fontsLoaded]);

    if (!fontsLoaded) {
        return null;
    }

    return (
        <Provider store={store}>
            <NavigationContainer>
                <StatusBar style="dark" />
                <Tabs />
            </NavigationContainer>
        </Provider>
    );
}

export default App;
