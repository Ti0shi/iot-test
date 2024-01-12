import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

import HomeScreenStack from "./stacks/HomeScreenStack";
import GroceriesScreenStack from "./stacks/GroceriesScreenStack";
import ProfileScreenStack from "./stacks/ProfileScreenStack";
import TabBar from "./TabBar";
import { useDispatch, useSelector } from "react-redux";
import { associateGroceryList, connectAccount, connectCaddie, setSupermarket } from "../reducers/store";
import { createGroceryList } from "../api";
import { StackActions } from "@react-navigation/native";

const Tab = createBottomTabNavigator();

export default function Tabs() {
    const user = useSelector((state) => state.account.user);
    const caddie = useSelector((state) => state.caddie.usedCaddie);
    const supermarket = useSelector((state) => state.currentSupermarket.supermarket);
    const dispatch = useDispatch();

    const simCaddie = true;

    if (user === null) {
        if (caddie === null && simCaddie) { // TEMPORARY
            dispatch(connectCaddie({id: 1, label: 34}));
            dispatch(setSupermarket({id: 1, label: "LIDL"}));
        }

        // Just for testing purposes (faster then connecting at each refresh XDDDD)
        dispatch(connectAccount({id: "5757f109-dba4-416d-b62e-a43968e2c4c5",
            caddieId: 1,
            email: "alexandre.morin94700@gmail.com",
            firstName: "Alexandre",
            lastName: "Morin",
            phoneNumber: "0681306593",
            profilePictureUrl: "https://demeter-iot.s3.eu-west-3.amazonaws.com/F2cQ_SyacAEeLZY.jpeg",
            role: "USER"}));
        //return <AccountScreenStack />;
    }

    if (user !== null && caddie !== null) {
        createGroceryList(user.id, caddie.id).then(response => {
            if (response.status === 200) {
                let groceryList = response.data;
                groceryList.groceries = [];
                dispatch(associateGroceryList(groceryList));
            }
        })
    }

    return (
        <Tab.Navigator
            tabBar={(props) => <TabBar {...props} />}
            sceneContainerStyle={{ backgroundColor: "#fff" }}
            id="tabBar"
        >
            <Tab.Screen
                name="Articles"
                component={GroceriesScreenStack}
                listeners={resetTabStacksOnBlur}
                options={{
                    headerShown: false,
                }}
            />
            <Tab.Screen
                name="Accueil"
                component={HomeScreenStack}
                options={{
                    headerShown: false,
                }}
            />
            <Tab.Screen
                name="Profil"
                component={ProfileScreenStack}
                options={{
                    headerShown: false,
                }}
            />
        </Tab.Navigator>
    );
}

/**
 * Resets tabs with stackNavigators to the first route when navigation to another tab
 */
const resetTabStacksOnBlur = ({navigation}) => ({
    blur: () => {
        const state = navigation.getState();

        state.routes.forEach((route, tabIndex) => {
            if (state?.index !== tabIndex && route.state?.index > 0) {
                navigation.dispatch(StackActions.popToTop());
            }
        });
    },
});
