import { Image, View, StyleSheet } from "react-native";

export default function Cards () {
  return (
    <View style={{flexDirection: "row", gap: 5, alignSelf: "center"}}>
      <View style={[styles.imageContainer, {borderColor: "#002495"}]}>
        <Image source={require('../../images/cards/visa.png')} style={styles.images}></Image>
      </View>
      <View style={styles.imageContainer}>
        <Image source={require('../../images/cards/mastercard.png')} style={styles.images}></Image>
      </View>
      <View style={styles.imageContainer}>
        <Image source={require('../../images/cards/americanexpress.png')} style={styles.images}></Image>
      </View>
      <View style={styles.imageContainer}>
        <Image source={require('../../images/cards/paypal.png')} style={styles.images}></Image>
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  imageContainer: {
    borderColor: "rgba(162,162,162,0.4)",
    borderWidth: 2,
    borderRadius: 5,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 10,
    shadowOpacity: 0.1,
  },
  images: {
    width: 50,
    height: 31,
    borderRadius: 5,
  }
});