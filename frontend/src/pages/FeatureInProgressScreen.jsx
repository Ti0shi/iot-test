import { View, Text, Image, Dimensions, StyleSheet } from "react-native";
import React from "react";

export default function FeatureInProgressScreen() {

  return (
        <View style={styles.container}>
            <Image
                source={require("../images/home-active.png")}
                style={{
                    height: Dimensions.get("window").height * 0.5,
                    width: Dimensions.get("window").width * 0.8,
                }}
                resizeMode={"contain"}
            ></Image>
            <Text style={styles.title}>Nous sommes en train de développer cette page</Text>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        alignItems: "center",
        flex: 1,
    },
    title: {
        fontFamily: "IBMPlexSans-SemiBold",
        fontSize: 20,
        marginTop: 20,
        textAlign: "center",
    },
});
