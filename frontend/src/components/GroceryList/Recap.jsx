import { useDispatch, useSelector } from "react-redux";
import { getProductById, pay } from "../../api";
import React from "react";
import {
  ActivityIndicator, Alert,
  Dimensions,
  Image,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View
} from "react-native";
import ModalHalf from "../Modal";
import Cards from "./Cards";
import { associateGroceryList, connectCaddie, setSupermarket } from "../../reducers/store";

export default function Recap ({onlyManualAdd, setOnlyManualAdd, onlyGroceryAdd, setOnlyGroceryAdd}) {
  const groceryList = useSelector((state) => state.associatedGroceryList.groceryList);
  const [totalPrice, setTotalPrice] = React.useState(0);
  const [inCaddie, setInCaddie] = React.useState(0);
  const [wanted, setWanted] = React.useState(0);
  const [loading, setLoading] = React.useState(true);
  const modalRef = React.useRef();
  const dispatch = useDispatch();

  const openModal = () => {
    modalRef?.current.open();
  }

  const closeModal = () => {
    modalRef?.current.close();
  }

  React.useEffect(() => {
    async function computeTotalPrice () {
      let currentTotalPrice = 0;
      let currentInCaddie = 0;
      let currentWanted = 0;

      for (let index = 0; index < groceryList.groceries.length; index++) {
        const inListProduct = groceryList.groceries[index];
        const response = await getProductById(inListProduct.productId);

        if (response.status === 200) {
          const gotProduct = response.data;
          currentTotalPrice += (gotProduct.price * inListProduct.quantity);
        }
        currentInCaddie += inListProduct.quantity;
        currentWanted += inListProduct.wantedQuantity;
      }
      setTotalPrice(currentTotalPrice);
      setInCaddie(currentInCaddie);
      setWanted(currentWanted);
      setLoading(false);
    }
    computeTotalPrice();
  }, [groceryList.groceries]);

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
      fontSize: 24,
      fontFamily: "IBMPlexSans-Bold",
    },
    fields: {
      fontSize: 18,
      fontFamily: "IBMPlexSans-Medium",
      color: "#444444",
    },
    infoText: {
      fontSize: 18,
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
      height: 55,
      borderBottomLeftRadius: 5,
      borderBottomRightRadius: 5,
      justifyContent: "center",
      alignItems: "center",
    },
    addText: {
      fontFamily: "IBMPlexSans-SemiBold",
      fontSize: 24,
      color: "#FFF",
    },
    active: {
      backgroundColor: "rgba(0,6,255,0.18)",
      padding: 10,
      borderRadius: 5
    },
    modalContent: {
      backgroundColor: "white",
      paddingVertical: 10,
      paddingHorizontal: 15,
      gap: 15,
      height: Dimensions.get("window").height / 2,
    },
    titleModal: {
      fontSize: 24,
      fontFamily: "IBMPlexSans-Medium",
    },
    payementButton: {
      backgroundColor: "#002495",
      height: 55,
      borderRadius: 5,
      shadowColor: "#000",
      shadowOffset: { width: 0, height: 0 },
      shadowRadius: 10,
      shadowOpacity: 0.1,
      justifyContent: "center",
      alignItems: "center",
    },
    payementText: {
      fontFamily: "IBMPlexSans-SemiBold",
      fontSize: 24,
      color: "#FFF",
    },
    textInputs: {
      height: 40,
      backgroundColor: "#fff",
      borderRadius: 5,
      borderWidth: 2,
      borderColor: "#002495",
      shadowColor: "#000",
      shadowOffset: { width: 0, height: 0 },
      shadowRadius: 20,
      shadowOpacity: 0.1,
      paddingLeft: 10,
      color: "#002495",
    },
    textInputsTitles: {
      fontFamily: "IBMPlexSans-SemiBold",
      fontSize: 14,
      color: "#9E9E9E",
      marginBottom: 3,
    },
  });

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#000000" />
      </View>
    );
  }

  let inCaddieText = inCaddie === 1 ? "article" : "articles";
  let wantedText = wanted === 1 ? "article" : "articles";

  return (
    <>
      <View style={styles.mainContainer}>
        <View style={styles.container}>
          <View style={{marginVertical: 5, marginLeft: 10, gap: 10}}>
            <Text style={styles.title}>Récapitulatif</Text>
            <View style={[styles.clickable, {flexDirection: "row", gap: 10}]}>
              <TouchableOpacity style={[{gap: 5}, onlyManualAdd ? styles.active : null]} onPress={() => {
                setOnlyManualAdd(!onlyManualAdd);
                setOnlyGroceryAdd(false);
              }}>
                <Image source={require("../../images/profile/caddie.png")} style={styles.caddieImage}/>
                <Text style={styles.commands}>{inCaddie} {inCaddieText} dans le caddie</Text>
              </TouchableOpacity>
              <View style={{borderLeftColor: "#868686", borderLeftWidth: 1, height: 55, marginHorizontal: 10}}></View>
              <TouchableOpacity style={[{gap: 5}, onlyGroceryAdd ? styles.active : null]} onPress={() => {
                setOnlyGroceryAdd(!onlyGroceryAdd);
                setOnlyManualAdd(false );
              }}>
                <Image source={require("../../images/profile/lists.png")} style={styles.caddieImage}/>
                <Text style={styles.commands}>{wanted} {wantedText} dans la liste</Text>
              </TouchableOpacity>
            </View>
            <View style={{flexDirection: "row", gap: 3, justifyContent: "center", marginTop: 10}}>
              <Text style={styles.fields}>Montant total :</Text>
              <Text style={styles.infoText}>{totalPrice}€</Text>
            </View>
          </View>
        </View>
        <TouchableOpacity style={styles.addButton} onPress={openModal}>
          <Text style={styles.addText}>Valider et payer 💶</Text>
        </TouchableOpacity>
      </View>
      <ModalHalf ref={modalRef}>
        <View style={styles.modalContent}>
          <Text style={styles.titleModal}>Valider et payer</Text>
          <Cards></Cards>
          <View>
            <Text style={styles.textInputsTitles}>Nom sur la carte</Text>
            <TextInput style={styles.textInputs} value={"Meilleur Projet IoT"} editable={false}></TextInput>
          </View>
          <View>
            <Text style={styles.textInputsTitles}>Numéro de la carte</Text>
            <TextInput style={styles.textInputs} value={"2671-9860-8300-0202"} editable={false}></TextInput>
          </View>
          <View style={{flexDirection: "row", gap: 10}}>
            <View style={{flex: 1}}>
              <Text style={styles.textInputsTitles}>Date d'expiration</Text>
              <TextInput style={[styles.textInputs]} value={"12/25"} editable={false}></TextInput>
            </View>
            <View style={{flex: 1}}>
              <Text style={styles.textInputsTitles}>CVV</Text>
              <TextInput style={[styles.textInputs, {flex: 1}]} value={"666"} editable={false}></TextInput>
            </View>
          </View>
          <TouchableOpacity style={styles.payementButton} onPress={async () => {
            const response = await pay(groceryList.id, totalPrice);
            if (response.status === 200)
              Alert.alert("Paiement effectué", "Votre paiement a bien été effectué, vous pouvez maintenant récupérer vos courses.");
            else
              Alert.alert("Erreur", "Une erreur est survenue lors du paiement, veuillez réessayer ultérieurement.");
            closeModal();
            await dispatch(connectCaddie(null));
            await dispatch(setSupermarket(null));
            await dispatch(associateGroceryList(null));
          }}>
            <Text style={styles.payementText}>🔓 Payer {totalPrice}€</Text>
          </TouchableOpacity>
        </View>
      </ModalHalf>
    </>
  );
}