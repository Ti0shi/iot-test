import { View, StyleSheet, Text, Image } from "react-native";

export default function ProfileInfos({user}) {
  return (
    <View style={styles.container}>
      <View style={styles.textHolder}>
        <Text style={styles.mainText}>Informations personnelles</Text>
        <View style={{flexDirection: "row", gap: 5}}>
          <Text style={styles.fields}>Prénom:</Text>
          <Text style={styles.infoText}>{user.firstName}</Text>
        </View>
        <View style={{flexDirection: "row", gap: 5}}>
          <Text style={styles.fields}>Nom:</Text>
          <Text style={styles.infoText}>{user.lastName}</Text>
        </View>
        <View style={{flexDirection: "row", gap: 5}}>
          <Text style={styles.fields}>Numéro de téléphone:</Text>
          <Text style={styles.infoText}>{user.phoneNumber}</Text>
        </View>
        <View style={{flexDirection: "row", gap: 5}}>
          <Text style={styles.fields}>Adresse email:</Text>
          <Text style={styles.infoText}>{user.email}</Text>
        </View>
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    alignSelf: "center",
    marginHorizontal: 20,
    width: "90%",
    backgroundColor: "white",
    borderRadius: 5,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 20,
    shadowOpacity: 0.1,
  },
  textHolder: {
    marginVertical: 10,
    marginLeft: 10,
    gap: 10
  },
  mainText: {
    fontSize: 24,
    fontFamily: "IBMPlexSans-Bold",
  },
  fields: {
    fontSize: 14,
    fontFamily: "IBMPlexSans-Light",
    color: "#444444",
  },
  infoText: {
    fontSize: 14,
    fontFamily: "IBMPlexSans-LightItalic",
    color: "#444444",
  },
});