import { Image, Text, View, StyleSheet, TextInput, TouchableOpacity, Alert, ScrollView, Dimensions } from "react-native";
import React from "react";
import {connectUser} from "../../api";
import { connectAccount, setSupermarket } from "../../reducers/store";
import { useSelector } from "react-redux";

export default function ConnexionPage({ navigation, dispatch }) {
  const [emailText, changeEmailText] = React.useState('');
  const [passwordText, changePasswordText] = React.useState('');
  const [hidePassword, changeHidePassword] = React.useState(true);
  const caddie = useSelector((state) => state.caddie.usedCaddie)

  return (
    <ScrollView bounces={false}>
      <View style={styles.mainContainer}>
        <Image
          source={require("../../images/logo-main.png")}
          style={{
            height: 163,
            width: 163,
          }}
          resizeMode={"contain"}
        ></Image>
        <Text style={[styles.caddieTitle, {marginTop: 20}]}>
          Bienvenue !
        </Text>
        <>
          {
            caddie
              ?
              <Text style={styles.caddieTitle}>
                Vous utilisez le caddie : N°{caddie.label}
              </Text>
              :
              <Text style={styles.caddieTitle}>
                Connectez-vous ou scannez un caddie
              </Text>
          }
        </>

        <View style={{marginTop: 60}}>
          <Text style={styles.textInputsTitles}>
            Adresse email
          </Text>
          <TextInput
            style={[styles.textInputs, styles.textInputsContents, {fontFamily: emailText === '' ? "IBMPlexSans-MediumItalic" : "IBMPlexSans-SemiBold",
            }]}
            placeholder="Saisissez votre adresse email"
            onChangeText={changeEmailText}
            value={emailText}
            autoCapitalize='none'
            autoCorrect={false}
            autoCompleteType='email'
          >
          </TextInput>
        </View>
        <View style={{marginTop: 15}}>
          <Text style={styles.textInputsTitles}>
            Mot de passe
          </Text>
          <TextInput
            style={[styles.textInputs, styles.textInputsContents, {fontFamily: passwordText === '' ? "IBMPlexSans-MediumItalic" : "IBMPlexSans-SemiBold",
            }]}
            placeholder="Saisissez votre mot de passe"
            onChangeText={changePasswordText}
            value={passwordText}
            autoCapitalize='none'
            autoCorrect={false}
            autoCompleteType='current-password'
            secureTextEntry={hidePassword}
          >
          </TextInput>
        </View>
        <TouchableOpacity style={styles.connexionButton} onPress={async () => {
          if (emailText === '' && passwordText === '') {
            Alert.alert("Erreur", "Veuillez saisir votre adresse email et votre mot de passe");
          }
          else if (emailText === '') {
            Alert.alert("Erreur", "Veuillez saisir votre adresse email");
          }
          else if (passwordText === '') {
            Alert.alert("Erreur", "Veuillez saisir votre mot de passe");
          }
          else {
            if (!emailText.match(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)) {
              Alert.alert("Erreur", "Veuillez saisir une adresse email valide");
            }
            const response = await connectUser(emailText, passwordText);

            if (response.status === 200) {
              Alert.alert("Succès", "Vous êtes maintenant connecté");
              dispatch(connectAccount(response.data));
            }
            else {
              Alert.alert("Erreur", "Email ou mot de passe invalide");
            }
          }
        }}>
          <Text style={{fontSize: 24, fontFamily: "IBMPlexSans-SemiBold", color: "#FFF"}}>
            Se connecter
          </Text>
        </TouchableOpacity>
      </View>
      <View style={[styles.createAccountButton, {flexDirection: "row"}]}>
        <Text style={styles.createAccountText}>
          Vous n’avez pas de compte ?
        </Text>
        <TouchableOpacity onPress={() => {
          navigation.navigate("CreateAccount");
        }}>
          <Text style={[styles.createAccountText, {color: "#002495", marginLeft: 4, textDecorationLine: 'underline'}]}>
            Créez-en un !
          </Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  mainContainer: {
    flexDirection: "column",
    position: "absolute",
    alignSelf: "center",
    alignItems: "center",
    marginTop: 125,
    width: 327,
  },
  caddieTitle: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 20,
    color: "#002495",
    textAlign: "center",
  },
  textInputs: {
    width: 327,
    height: 55,
    backgroundColor: "#fff",
    borderRadius: 5,
    borderWidth: 2,
    borderColor: "#002495",
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 20,
    shadowOpacity: 0.1,
  },
  textInputsContents: {
    paddingLeft: 10,
    color: "#002495",
  },
  textInputsTitles: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 16,
    color: "#9E9E9E",
    marginBottom: 3,
  },
  connexionButton: {
    backgroundColor: "#002495",
    width: 327,
    height: 55,
    borderRadius: 5,
    elevation: 2,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 10,
    shadowOpacity: 0.1,
    marginTop: 30,
    alignItems: "center",
    justifyContent: "center",
  },
  createAccountButton: {
    position: "absolute",
    alignSelf: "center",
    top: Dimensions.get("window").height - 50,
  },
  createAccountText: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 15,
    color: "#9E9E9E",
  }
});