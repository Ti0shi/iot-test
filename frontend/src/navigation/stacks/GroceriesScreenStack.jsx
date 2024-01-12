import React from "react";

import { createStackNavigator } from "@react-navigation/stack";
import { Image } from "react-native";
import Index from "../../pages/Groceries/Index";
import ProductsPage from "../../pages/Groceries/ProductsPage";
import { useSelector } from "react-redux";

const Stack = createStackNavigator();

export default function GroceriesScreenStack() {
  const category = useSelector((state) => state.products.category);

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
            source={require("../../images/grocery-active.png")}
            style={{ width: 40, height: 40, marginLeft: 30 }}
            resizeMode="contain"
          />
        ),
      }}
    >
      <Stack.Screen
        name="GroceriesIndex"
        component={Index}
        options={{
            title: "Articles",
        }}
      />
      <Stack.Screen
        name={"Products"}
        component={ProductsPage}
        options={{
          title: category === null ? "Produits" : category.label,
        }}
      />
    </Stack.Navigator>
  );
}
