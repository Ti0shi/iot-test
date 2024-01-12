import { StyleSheet, Text, View } from "react-native";
import React from "react";

export default function ReceiptItem ({item}) {
  const date = new Date(item.payedAt).toLocaleDateString('fr-FR',
    {day: 'numeric', month: 'long', year: 'numeric'});
  const hour = new Date(item.payedAt).toLocaleTimeString('fr-FR',{hour: '2-digit', minute:'2-digit', hour12: false});

  return (
    <View style={styles.mainContainer}>
      <View style={[styles.container]}>
        <View style={{marginTop: 5, marginBottom: 10, marginLeft: 10, gap: 10}}>
          <Text style={styles.title}>Reçu du {date}</Text>
          <Text style={styles.subTitle}>À {hour}</Text>
          <Text style={styles.subTitle}>Vous avez utilisé le caddie n°{item.caddieLabel} à {item.supermarketLabel}</Text>
        </View>
      </View>
      <View style={styles.addButton}>
        <Text style={styles.addText}>Montant total : {item.totalPrice}€ 💶</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  loadingContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  mainContainer: {
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 10,
    shadowOpacity: 0.2,
    marginHorizontal: 20,
    marginTop: 20,
  },
  container: {
    borderTopLeftRadius: 5,
    borderTopRightRadius: 5,
    backgroundColor: "#fff",
    marginBottom: 0
  },
  title: {
    fontSize: 20,
    fontFamily: "IBMPlexSans-Bold",
  },
  subTitle: {
    fontSize: 14,
    fontFamily: "IBMPlexSans-Light",
  },
  fields: {
    fontSize: 16,
    fontFamily: "IBMPlexSans-Medium",
    color: "#444444",
  },
  infoText: {
    fontSize: 16,
    fontFamily: "IBMPlexSans-Medium",
    color: "#444444",
  },
  clickable: {
    alignSelf: "center",
  },
  caddieImage: {
    width: 40,
    height: 40,
    alignSelf: "center"
  },
  commands: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 10,
    color: "#515151",
  },
  addButton: {
    backgroundColor: "#002495",
    height: 45,
    borderBottomLeftRadius: 5,
    borderBottomRightRadius: 5,
    justifyContent: "center",
    alignItems: "center",
  },
  addText: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 20,
    color: "#FFF",
  },
});