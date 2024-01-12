import { useDispatch, useSelector } from "react-redux";
import {
  addProductToGroceryList,
  deleteInListProduct,
  deleteTemplate,
  getAllTemplates,
  getProductsIdByTemplate
} from "../../../api";
import React from "react";
import { Alert, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { hasToBeUpdate, hasToBeUpdateTemplates } from "../../../reducers/store";

export default function TemplatesDisplayer () {
  const dispatch = useDispatch();
  const groceryList = useSelector((state) => state.associatedGroceryList.groceryList);

  const [templates, setTemplates] = React.useState([]);
  const user = useSelector((state) => state.account.user)
  const hasToBeUpdatedTemplates = useSelector((state) => state.update.needUpdateTemplates);

  React.useEffect(() => {
    async function getTemplates () {
      const response = await getAllTemplates(user.id);
      if (response.status === 200)
        setTemplates(response.data);
      dispatch(hasToBeUpdateTemplates(false));
    }
    getTemplates();
  }, [hasToBeUpdatedTemplates]);

  return (
    <ScrollView>
      {
        templates?.map((item, index) => (
          <TemplateItem key={index} item={item} dispatch={dispatch} groceryList={groceryList}/>
        ))
      }
    </ScrollView>
  )
}

function TemplateItem ({item, dispatch, groceryList}) {
  const date = new Date(item.createdAt).toLocaleDateString('fr-FR',
    {day: 'numeric', month: 'long', year: 'numeric'});

  return (
    <View style={styles.mainContainer}>
      <View style={[styles.container]}>
        <View style={{marginTop: 5, marginBottom: 10, marginLeft: 10, gap: 10}}>
          <Text style={styles.title}>{item.label}</Text>
          <Text style={styles.subTitle}>Créé le {date}</Text>
        </View>
        <TouchableOpacity style={styles.deleteContainer} onPress={() => {
          // Create and alert with two buttons to confirm the deletion, or cancel it, the yes button will call the delete function and must be red
          Alert.alert(
            "Suppression",
            "Voulez-vous vraiment supprimer ce template ?",
            [
              {
                text: "Non",
                style: "cancel"
              },
              {
                text: "Oui",
                onPress: async () => {
                  const response = await deleteTemplate(item.id);
                  if (response.status === 200) {
                    dispatch(hasToBeUpdateTemplates(true));
                    Alert.alert(
                      "Suppression",
                      "Le template a bien été supprimé");
                  }
                  else {
                    Alert.alert(
                      "Suppression",
                      "Une erreur est survenue lors de la suppression du template");
                  }
                }
              }
            ]
          )
        }}>
          <Image source={require("../../../images/list/delete.png")} style={{width: 30, height: 30, alignSelf: "center"}}></Image>
        </TouchableOpacity>
      </View>
      <TouchableOpacity style={styles.addButton} onPress={() => {
        Alert.alert(
          "Ajout",
          "Voulez-vous vraiment ajouter ce template à votre liste de courses ?",
          [
            {
              text: "Oui",
              onPress: async () => {
                if (groceryList.groceries.length !== 0) {
                  Alert.alert (
                    "Ajout",
                    "Votre liste de courses n'est pas vide, voulez-vous vraiment ajouter ce template ?",
                    [
                      {
                        text: "Oui",
                        onPress: async () => {
                          await addTemplateToGroceryList({item, dispatch, groceryList});
                        }
                      },
                      {
                        text: "Non",
                      }
                      ]
                  );
                } else {
                  await addTemplateToGroceryList({item, dispatch, groceryList});
                }
              }
            },
            {
              text: "Non",
            }
            ]
        );
      }}>
        <Text style={styles.addText}>Cliquer pour utiliser ce template</Text>
      </TouchableOpacity>
    </View>
  );
}

async function addTemplateToGroceryList ({item, dispatch, groceryList}) {
  const response = await getProductsIdByTemplate(item.id);
  if (response.status === 200) {
    let errorCount = 0;
    for (const productId of response.data) {
      errorCount += await addProductFromTemplateToGroceryList({groceryList, productId});
    }
    if (errorCount === 0) {
      Alert.alert(
        "Ajout",
        "Le template a bien été ajouté à votre liste de courses");
      dispatch(hasToBeUpdate(true));
    }
    else {
      Alert.alert(
        "Ajout",
        "Une erreur est survenue lors de l'ajout du template à votre liste de courses");
    }
  }
}

async function addProductFromTemplateToGroceryList ({groceryList, productId}) {
  const inListProduct = {
    id: "NULL", // will be generated by the backend
    groceryListId: groceryList.id,
    productId: productId,
    quantity: 0,
    wantedQuantity: 1,
    context: "FROM_GROCERY",
  }

  const response = await addProductToGroceryList({inListProduct});
  if (response.status === 200)
    return 0;

  return 1;
}

const styles = StyleSheet.create({
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
    marginBottom: 0,
    flexDirection: "row",
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
  deleteContainer: {
    justifyContent: "flex-end",
    alignSelf: "center",
    marginLeft: "auto",
    marginRight: 20,
  },
});