import { Alert, Image, ScrollView, StyleSheet, Text, TextInput, TouchableOpacity, View, KeyboardAvoidingView } from "react-native";
import React from "react";
import { createUser } from "../../api";
import { useNavigation } from "@react-navigation/native";

export default function AccountCreationPage({ }) {
  const [firstName, changeFirstName] = React.useState('');
  const [lastName, changeLastName] = React.useState('');
  const [emailText, changeEmailText] = React.useState('');
  const [phoneNumberText, changePhoneNumberText] = React.useState('');
  const [passwordText, changePasswordText] = React.useState('');

  const navigation = useNavigation();


  return (
    <ScrollView bounces={false}>
      <TouchableOpacity onPress={() => navigation.goBack()} style={{marginTop: 75, paddingHorizontal: 20}}>
        <Image source={require("../../images/left-arrow.png")} style={{width: 30, height: 30}}></Image>
      </TouchableOpacity>
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
          Création de compte
        </Text>
          <View style={{marginTop: 40, flexDirection: "row", flexWrap: "wrap"}}>
            <View style={{width: "48%"}}>
              <Text style={styles.textInputsTitles}>
                Prénom
              </Text>
              <TextInput
                style={[styles.textInputs,
                  styles.textInputsContents,
                  {fontFamily: firstName === '' ? "IBMPlexSans-MediumItalic" : "IBMPlexSans-SemiBold", width: "100%"}]}
                placeholder="Votre prénom"
                onChangeText={changeFirstName}
                value={firstName}
                autoCapitalize='none'
                autoCorrect={false}
              >
              </TextInput>
            </View>
            <View style={{flexGrow: 1}}></View>
            <View style={{width: "48%"}}>
              <Text style={styles.textInputsTitles}>
                Nom
              </Text>
              <TextInput
                style={[styles.textInputs,
                  styles.textInputsContents,
                  {fontFamily: lastName === '' ? "IBMPlexSans-MediumItalic" : "IBMPlexSans-SemiBold", width: "100%"}]}
                placeholder="Votre nom"
                onChangeText={changeLastName}
                value={lastName}
                autoCapitalize='none'
                autoCorrect={false}
              >
              </TextInput>
            </View>
          </View>
          <View style={{marginTop: 15}}>
            <Text style={styles.textInputsTitles}>
              Numéro de téléphone
            </Text>
            <TextInput
              style={[styles.textInputs, styles.textInputsContents, {fontFamily: phoneNumberText === '' ? "IBMPlexSans-MediumItalic" : "IBMPlexSans-SemiBold",
              }]}
              placeholder="Saisissez votre numéro de téléphone"
              onChangeText={changePhoneNumberText}
              value={phoneNumberText}
              autoCapitalize='none'
              autoCorrect={false}
              keyboardType='numeric'
            >
            </TextInput>
          </View>
          <View style={{marginTop: 15}}>
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
              secureTextEntry={true}
            >
            </TextInput>
          </View>
          <TouchableOpacity style={styles.connexionButton} onPress={async () => {
            if (firstName === '' || lastName === '' || emailText === '' || phoneNumberText === '' || passwordText === '') {
              Alert.alert("Erreur", "Veuillez remplir tous les champs.");
            }
            else if (!emailText.match(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)) {
              Alert.alert("Erreur", "Veuillez saisir une adresse email valide");
            }
            else if (!phoneNumberText.match(/^06\d{8}$/)) {
              Alert.alert("Erreur", "Veuillez saisir un numéro de téléphone valide");
            }
            else {
              const response = await createUser(firstName, lastName, emailText, phoneNumberText, passwordText);

              if (response.status === 200) {
                Alert.alert("Succès", "Votre compte a bien été créé. Veuillez vous connecter");
                navigation.navigate("Connection");
              }
              else {
                Alert.alert("Erreur", "Une erreur est survenue lors de la création de votre compte. Veuillez réessayer plus tard.");
              }
            }
          }}>
            <Text style={{fontSize: 24, fontFamily: "IBMPlexSans-SemiBold", color: "#FFF"}}>
              Créer mon compte
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
    marginTop: 100,
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
    bottom: 25,
    alignSelf: "center",
  },
  createAccountText: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 15,
    color: "#9E9E9E",
  }
});