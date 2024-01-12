import { Dimensions, Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";

export default function Header({user, caddie, navigation}) {
  return (
    <>
      <View style={[styles.backImageContainer, styles.shadow]}>
        <Image source={require("../../images/profile/back.png")} style={styles.backImage}/>
      </View>
      <View style={[styles.transparent, styles.shadow]}>
        <View style={styles.textHolder}>
          <Text style={styles.names}>{user.firstName} {user.lastName}</Text>
          {caddie
            ?
            <Text style={styles.caddieInfo}>Caddie N°{caddie.label}</Text>
            :
            <Text style={styles.caddieInfo}>Vous n'utilisez aucun caddie</Text>
          }
        </View>
        <View style={[styles.clickable, {flexDirection: "row", flexWrap: "wrap", gap: 30}]}>
          <TouchableOpacity style={{gap: 5}} onPress={() => {
            navigation.navigate("ReceiptsPage");
          }}>
            <Image source={require("../../images/profile/caddie.png")} style={styles.caddieImage}/>
            <Text style={styles.commands}>Reçus</Text>
          </TouchableOpacity>
          <View style={{borderLeftColor: "#868686", borderLeftWidth: 1, height: 55, marginHorizontal: 10}}></View>
          <TouchableOpacity style={{gap: 5}} onPress={() => {
            navigation.navigate("TemplatesPage");
          }}>
            <Image source={require("../../images/profile/lists.png")} style={styles.caddieImage}/>
            <Text style={styles.commands}>Templates</Text>
          </TouchableOpacity>
        </View>
      </View>
      <TouchableOpacity style={[styles.shadow]}>
        <Image source={{uri: user.profilePictureUrl}} style={[styles.profilePicture]}/>
      </TouchableOpacity>
    </>
  )
}

const styles = StyleSheet.create({
  transparent: {
    backgroundColor: "rgba(255, 255, 255, 0.9)",
    //position: "absolute",
    alignSelf: "center",
    alignContent: "center",
    marginHorizontal: 20,
    marginVertical: -150,
    width: "80%",
    height: 230,
    borderRadius: 5,
    blurRadius: 10,
  },
  backImageContainer: {
    //position: "absolute",
    alignSelf: "center",
    alignContent: "center",
    marginHorizontal: 20,
    marginVertical: 20,
    width: "90%",
    height: 225,
    borderRadius: 5,
  },
  backImage: {
    //position: "absolute",
    width: "100%",
    height: "100%",
    borderRadius: 5,
  },
  profilePicture: {
    //position: "absolute",
    alignSelf: "center",
    alignContent: "center",
    marginHorizontal: 20,
    marginVertical: -125,
    width: 110,
    height: 110,
    borderRadius: 5,
    backgroundColor: "white"
  },
  shadow: {
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 20,
    shadowOpacity: 0.1,
  },
  textHolder: {
    marginHorizontal: 20,
    marginVertical: 70,
  },
  names: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 28,
    color: "#1E1E1E",
    textAlign: "center",
  },
  caddieInfo: {
    fontFamily: "IBMPlexSans-LightItalic",
    fontSize: 16,
    color: "#515151",
    textAlign: "center",
    marginTop: 5,
  },
  clickable: {
    position: "absolute",
    alignSelf: "center",
    bottom: 20,
  },
  caddieImage: {
    width: 40,
    height: 40,
    alignSelf: "center"
  },
  commands: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 12,
    color: "#515151",
  },
})