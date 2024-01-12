import React from "react";

import { View, Pressable, StyleSheet, Text, Image, Platform, TouchableOpacity } from "react-native";

import HomeIcon from "../images/home-innactive.png";
import HomeIconSelected from "../images/home-active.png";

import Profile from "../images/profil-innactif.png";
import ProfileSelected from "../images/profil-active.png";

import Groceries from "../images/grocery-innactive.png";
import GroceriesSelected from "../images/grocery-active.png";
import { useDispatch, useSelector } from "react-redux";
import { setCategory } from "../reducers/store";

const images = [Groceries, HomeIcon, Profile];
const imagesSelected = [GroceriesSelected, HomeIconSelected, ProfileSelected];

const homeLogos = [HomeIcon, HomeIconSelected];

const TabBar = ({ state, descriptors, navigation }) => {
    const show = descriptors[state.routes[state.index].key].options.tabBarShown;
    if (show != null && !show) {
        return null;
    }

    const dispatch = useDispatch();

    return (
        <View style={styles.mainContainer}>
            {state.routes.map((route, index) => {
                const { options } = descriptors[route.key];
                const label =
                    options.tabBarLabel !== undefined
                        ? options.tabBarLabel
                        : options.title !== undefined
                        ? options.title
                        : route.name;

                const isFocused = state.index === index;

                const onPress = () => {
                    dispatch(setCategory(null));
                    const event = navigation.emit({
                        type: "tabPress",
                        target: route.key,
                    });

                    if (!isFocused && !event.defaultPrevented) {
                        navigation.navigate(route.name);
                    }
                };

                if (label === "Accueil") {
                    return (
                        <View key={index} style={[styles.mainItemContainer]}>
                            <Pressable onPress={onPress}>
                                <View
                                    style={[
                                        styles.mainButton,
                                        {
                                            borderColor: isFocused ? "#002495" : "#9E9E9E",
                                            backgroundColor: isFocused ? "#FFFAEB" : "#002495" ,
                                        },
                                    ]}
                                >
                                    <Image source={homeLogos[+ isFocused]} style={{ width: 50, height: 50 }} resizeMode="contain" />
                                </View>
                            </Pressable>
                        </View>
                    );
                }

                return (
                    <View key={index} style={[styles.mainItemContainer]}>
                        <TouchableOpacity onPress={
                            () => {
                                onPress();
                            }
                        }>
                            <View style={styles.mainItem}>
                                <Image
                                    source={isFocused ? imagesSelected[index] : images[index]}
                                    style={{ height: 50, width: 50 }}
                                    resizeMode={"contain"}
                                />
                                <Text style={styles.mainItemText}>{label}</Text>
                                {isFocused && (
                                    <View
                                        style={[
                                            styles.navIndicator,
                                            {
                                                top: Platform.OS === "web" ? -10 : -9,
                                            },
                                        ]}
                                    />
                                )}
                            </View>
                        </TouchableOpacity>
                    </View>
                );
            })}
        </View>
    );
};

const styles = StyleSheet.create({
    mainContainer: {
        flexDirection: "row",
        position: "absolute",
        bottom: 0,
        backgroundColor: "#FFF",
        borderTopLeftRadius: 24,
        borderTopRightRadius: 24,
        borderColor: "#FFF",
        borderWidth: 1,
        elevation: 2,
        shadowColor: "#000",
        shadowOffset: { width: 0, height: -10 },
        shadowRadius: 20,
        shadowOpacity: 0.1,
        width: "100%",
    },
    mainItemContainer: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        marginTop: 10,
        marginBottom: 5,
        borderRadius: 1,
        borderColor: "red",
    },
    mainButton: {
        justifyContent: "center",
        alignItems: "center",
        gap: 12,
        borderRadius: 55,
        width: 80,
        height: 80,
        transform: [{ translateY: -20 }],
        borderWidth: 3,
    },
    mainItem: {
        justifyContent: "center",
        alignItems: "center",
        position: "relative",
        transform: [{ translateY: -10 }],
    },
    mainItemText: {
        fontFamily: "IBMPlexSans-SemiBold",
        fontSize: 10,
        textTransform: "uppercase",
    },
    navIndicator: {
        height: 4,
        width: 22,
        backgroundColor: "#9E9E9E",
        borderRadius: 1,
        position: "absolute",
        top: -19,
    },
});

export default TabBar;
