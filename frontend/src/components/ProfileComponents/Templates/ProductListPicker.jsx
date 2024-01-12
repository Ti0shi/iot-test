import {
  Alert,
  Image,
  Platform,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View
} from "react-native";
import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { createTemplateList, deleteInListProduct, getAllInListProducts, getProductsBySupermarket } from "../../../api";
import { hasToBeUpdateTemplates } from "../../../reducers/store";
import Loading from "../../Loading";

export default function ProductListPicker ({setShowTemplateCreation}) {
  const dispatch = useDispatch();

  const [listTitle, setListTitle] = React.useState("");
  const [loading, setLoading] = React.useState(true);
  const [products, setProducts] = React.useState([]);
  let selectedProducts = [];

  const user = useSelector((state) => state.account.user);
  const supermarket = useSelector((state) => state.currentSupermarket.supermarket);

  React.useEffect(() => {
    async function getTemplates () {
      const response = await getProductsBySupermarket(supermarket.id);
      if (response.status === 200)
        setProducts(response.data);

      setLoading(false);
    }
    getTemplates();

  }, [])

  if (loading) {
    return (
      <Loading/>
    )
  }

  const styles = StyleSheet.create({
    listContainer: {
      marginHorizontal: Platform.OS === "web" ? 10 : 20,
      marginTop: 10,
      backgroundColor: "#002495",
      borderRadius: 5,
    },
    textInputs: {
      height: 55,
      backgroundColor: "#fff",
      borderRadius: 5,
      borderWidth: 2,
      borderColor: "#002495",
      shadowColor: "#000",
      shadowOffset: { width: 0, height: 0 },
      shadowRadius: 20,
      shadowOpacity: 0.2,
    },
    textInputsContents: {
      paddingLeft: 10,
      color: "#002495",
      fontFamily: listTitle === "" ? "IBMPlexSans-MediumItalic" : "IBMPlexSans-Medium",
      fontSize: 16
    },
    textInputsTitles: {
      fontFamily: "IBMPlexSans-SemiBold",
      fontSize: 14,
      color: "#9E9E9E",
      marginBottom: 3,
    },
    addButton: {
      backgroundColor: "#002495",
      height: 55,
      borderRadius: 5,
      shadowColor: "#000",
      shadowOffset: { width: 0, height: 0 },
      shadowRadius: 10,
      shadowOpacity: 0.1,
      justifyContent: "center",
      alignItems: "center",
      marginHorizontal: 20,
      marginVertical: 10,
    },
    addText: {
      fontFamily: "IBMPlexSans-SemiBold",
      fontSize: 24,
      color: "#FFF",
    },
    listStyle: {
      marginVertical: 20,
    },
    container: {
      borderRadius: 5,
      shadowColor: "#000",
      shadowOffset: { width: 0, height: 0 },
      shadowRadius: 10,
      shadowOpacity: 0.1,
      //alignSelf: "center",
      backgroundColor: "#fff",
      marginHorizontal: 20,
    },
    image: {
      width: 70,
      height: 70,
      borderRadius: 5,
    },
    itemTitle: {
      fontFamily: "IBMPlexSans-Medium",
      fontSize: 16,
      color: "#002495",
    },
    itemPrice: {
      fontFamily: "IBMPlexSans-Light",
      fontSize: 12,
    },
    validationImage: {
      width: 30,
      height: 30,
      alignSelf: "center",
      marginLeft: "auto",
      marginRight: 20,
    }
  })

  return (
    <>
      <View style={{marginVertical: 10, marginHorizontal: 20}}>
        <Text style={styles.textInputsTitles}>Donnez un nom à votre liste</Text>
        <TextInput
          style={[styles.textInputs, styles.textInputsContents]}
          placeholder="Veuillez saisir un nom de liste"
          value={listTitle}
          onChangeText={setListTitle}
        />
      </View>
      <View style={[styles.listContainer, {shadowColor: "#000",
        shadowOffset: { width: 0, height: 0 },
        shadowRadius: 10,
        shadowOpacity: 0.2,
      }]}>
        <ScrollView style={styles.listStyle}>
          {
            products?.map((item, key) => (
              <View key={key}>
                <TemplateProductItem
                  key={key}
                  item={item}
                  styles={styles}
                  index={key}
                  selectedProducts={selectedProducts}
                />
                {key < products.length - 1 ? <View style={{height: 20}}></View> : null}
              </View>
            ))
          }
        </ScrollView>
      </View>
      <TouchableOpacity style={styles.addButton} onPress={() => {
        if (listTitle === "")
          Alert.alert('Erreur', 'Veuillez saisir un nom de liste')
        else {
          const templateList = {label: listTitle, productsIdList: selectedProducts.map(product => product.item.id)};
          createTemplateList(user.id, templateList).then(response => {
            if (response.status === 200) {
              Alert.alert('Succès', 'Votre liste a bien été créée');
              setShowTemplateCreation(false);
              dispatch(hasToBeUpdateTemplates(true));
            }
            else {
              Alert.alert('Erreur', 'Une erreur est survenue lors de la création de votre liste');
            }
          });
        }
      }}>
        <Text style={styles.addText}>Finaliser la liste 📝</Text>
      </TouchableOpacity>
    </>
  )
}

function TemplateProductItem({item, styles, index, selectedProducts}) {
  const [selected, setSelected] = React.useState(false);
  const itemPriceString = item.price.toString() + "€ / unité";
  const dispatch = useDispatch();

  if (selected) {
    const productToAdd = {item: item, index: index};
    selectedProducts.push(productToAdd);
  } else {
    selectedProducts.filter(product => product.index !== index);
  }

  return (
    <TouchableOpacity style={[styles.container, {flexDirection: "row"}]} onPress={() => {
      setSelected(!selected);
    }}>
      <View style={{flexDirection: "row", flexWrap: "wrap"}}>
        <Image source={{uri: item.imageUrl}} resizeMode="contain" style={styles.image}></Image>
        <View style={{alignSelf: 'center', gap: 5}}>
          <Text style={styles.itemTitle}>
            {item.label}
          </Text>
          <Text style={styles.itemPrice}>
            {itemPriceString}
          </Text>
        </View>
      </View>
      {
        selected
          ?
          <Image source={require("../../../images/list/check.png")} style={styles.validationImage}/>
          :
          null
      }
    </TouchableOpacity>
  )
}