import { Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";

export default function Promotions({navigation, width}) {
  const styles = StyleSheet.create({
      shadow: {
        shadowColor: "#000",
        shadowOffset: { width: 0, height: 0 },
        shadowRadius: 20,
        shadowOpacity: 0.2,
      },
      container: {
        width: width,
        height: 100,
        alignSelf: "center",
        alignContent: "center",
        justifyContent: "center",
        borderRadius: 5,
      },
      fadeImage: {
        width: "100%",
        height: "100%",
        //position: "absolute",
        borderRadius: 5
      },
      promotionText: {
        position: "absolute",
        alignSelf: "center",
        fontFamily: "IBMPlexSans-Bold",
        fontSize: 36,
        color: "white",
      }
    }
  )

  return (
    <TouchableOpacity style={[styles.shadow, styles.container]} onPress={() => {
      navigation.navigate("GroceriesIndex");
    }}>
      <Image source={require("../../images/profile/fade.png")} style={styles.fadeImage}></Image>
      <Text style={styles.promotionText}>
        Promotions !
      </Text>
    </TouchableOpacity>
  )
}

