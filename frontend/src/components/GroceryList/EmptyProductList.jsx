import { Dimensions, Image, StyleSheet, Text, View } from "react-native";

export default function EmptyProductList () {
  return (
    <>
      <View style={styles.container}>
        <Image
          source={require("../../images/profile/caddie.png")}
          style={{
            height: Dimensions.get("window").height * 0.3,
            width: Dimensions.get("window").width * 0.6,
          }}
          resizeMode={"contain"}
        ></Image>
        <Text style={styles.title}>Vous n'avez aucun article dans votre liste/caddie !</Text>
      </View>
    </>
  )
}

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    justifyContent: "center",
    flex: 1,
  },
  title: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 20,
    marginTop: 20,
    marginHorizontal: 20,
    textAlign: "center",
  },
});