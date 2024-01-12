import { Text, TouchableOpacity, View, StyleSheet } from "react-native";
import React from "react";

export default function CreationButton ({showTemplateCreation, setShowTemplateCreation}) {
  const styles = StyleSheet.create({
    roundContainer : {
      width: 50,
      height: 50,
      borderRadius: 50,
      backgroundColor: showTemplateCreation ? "#002495" :  "#fff",
      justifyContent: "center",
      alignSelf: "center",
    },
    roundText : {
      color: showTemplateCreation ? "#fff" : "#002495",
      fontSize: 25,
      textAlign: "center"
    },
    addContainer: {
      flexDirection: "row",
      gap: 10,
      backgroundColor: showTemplateCreation ? "#fff" : "#002495",
      padding: 10,
      marginHorizontal: 20,
      shadowColor: "#000",
      shadowOffset: { width: 0, height: 0 },
      shadowRadius: 20,
      shadowOpacity: 0.2,
      justifyContent: "center",
      borderRadius: 5,
    },
    addText: {
      fontSize: 20,
      fontFamily: "IBMPlexSans-Medium",
      alignSelf: "center",
      textAlign: "center",
      flex: 1,
      color: showTemplateCreation ? "#002495" : "#fff"
    },
  });

  return (
    <>
      <TouchableOpacity style={styles.addContainer} onPress={() => {
        setShowTemplateCreation(!showTemplateCreation);
      }}>
        <View style={styles.roundContainer}>
          {
            showTemplateCreation
              ?
              <Text style={styles.roundText}>x</Text>
              :
              <Text style={styles.roundText}>+</Text>
          }
        </View>
        {
          showTemplateCreation
            ?
            <Text style={styles.addText}>Annuler la création de la liste 📝</Text>
            :
            <Text style={styles.addText}>Créer une nouvelle liste 📝</Text>
        }
      </TouchableOpacity>
    </>
  )
}