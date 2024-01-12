import React from "react";

import { createStackNavigator } from "@react-navigation/stack";
import { Image } from "react-native";
import Index from "../../pages/Profile/Index";
import ReceiptsPage from "../../pages/Profile/ReceiptsPage";
import TemplatesPage from "../../pages/Profile/TemplatesPage";

const Stack = createStackNavigator();

export default function ProfileScreenStack() {
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
                        source={require("../../images/profil-active.png")}
                        style={{ width: 40, height: 40, marginLeft: 30 }}
                        resizeMode="contain"
                    />
                ),
            }}
        >
            <Stack.Screen
                name="ProfileIndex"
                component={Index}
                options={{
                    title: "Profil",
                }}
            />
          <Stack.Screen
            name="ReceiptsPage"
            component={ReceiptsPage}
            options={{
              title: "Reçus",
            }}
          />
          <Stack.Screen
            name="TemplatesPage"
            component={TemplatesPage}
            options={{
              title: "Modèles de liste",
            }}
            />
        </Stack.Navigator>
    );
}
