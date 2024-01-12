import React from "react";

import { createStackNavigator } from "@react-navigation/stack";
import { Image } from "react-native";
import Index from "../../pages/Home/Index";

const Stack = createStackNavigator();

export default function HomeScreenStack() {
    return (
        <Stack.Navigator
            screenOptions={{
                cardStyle: { backgroundColor: "#F8F8F8", paddingBottom: 100 },
                headerStyle: {
                    borderColor: "#E8E8E8",
                },
                headerTintColor: "#000",
                headerTitleStyle: {
                    fontWeight: "bold",
                    fontFamily: "IBMPlexSans-SemiBold",
                },
                headerLeft: () => (
                    <Image
                        source={require("../../images/home-active.png")}
                        style={{ width: 40, height: 40, marginLeft: 30 }}
                        resizeMode="contain"
                    />
                ),
            }}
        >
            <Stack.Screen
                name="HomeIndex"
                component={Index}
                options={{
                    title: "Accueil",
                }}
            />
        </Stack.Navigator>
    );
}
