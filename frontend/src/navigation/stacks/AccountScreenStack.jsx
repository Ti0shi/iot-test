import React from "react";

import { createStackNavigator } from "@react-navigation/stack";
import { Image } from "react-native";
import AccountCreationPage from "../../pages/ProfileCreation/AccountCreationPage";
import Index from "../../pages/ProfileCreation/Index";

const Stack = createStackNavigator();

export default function AccountScreenStack() {
    return (
        <Stack.Navigator screenOptions={{
          headerShown: false
        }}>
          <Stack.Screen
            name="Connection"
            component={Index}
            options={{
              title: "Connection",
            }}
          />
          <Stack.Screen
            name="CreateAccount"
            component={AccountCreationPage}
            options={{
              title: "Créer un compte",
            }}
          />
        </Stack.Navigator>
    );
}
