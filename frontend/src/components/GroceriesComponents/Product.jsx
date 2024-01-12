import {
  Text,
  TouchableOpacity,
  View,
  StyleSheet,
  Image,
  Dimensions,
  FlatList,
  TextInput,
  Alert,
  Platform,
} from "react-native";
import React from "react";
import ModalHalf from "../Modal";
import { addProductToGroceryList } from "../../api";
import { useDispatch, useSelector } from "react-redux";
import { hasToBeUpdate } from "../../reducers/store";
import CheckBox from "expo-checkbox";
import Filters from "./Filters";

function sortProductsAlphabetically (products) {
  return products.sort((a, b) => {
    if (a.label < b.label)
      return -1;
    if (a.label > b.label)
      return 1;
    return 0;
  });
}

function sortProductsByPrice (products) {
  return products.sort((a, b) => {
    if (a.price < b.price)
      return -1;
    if (a.price > b.price)
      return 1;
    return 0;
  });
}

export default function Products ({ products, refreshing, refreshProducts }) {
  const groceryList = useSelector((state) => state.associatedGroceryList.groceryList);
  const categories = useSelector((state) => state.products.category);

  const [sortAlphabetically, setSortAlphabetically] = React.useState(false);
  const [sortPrice, setSortPrice] = React.useState(false);

  if (sortPrice)
    products = sortProductsByPrice(products);
  if (sortAlphabetically)
    products = sortProductsAlphabetically(products);

  return (
    <>
      <Text style={styles.title}>Les produits {categories?.label}</Text>
      <Filters
        sortPrice={sortPrice}
        setSortPrice={setSortPrice}
        sortAlphabetically={sortAlphabetically}
        setSortAlphabetically={setSortAlphabetically}
      />
      <View style={[styles.listContainer, {shadowColor: "#000",
        shadowOffset: { width: 0, height: 0 },
        shadowRadius: 10,
        shadowOpacity: 0.2,
      }]}>
        <FlatList
          style={styles.listStyle}
          data={products}
          renderItem={({ item }) => (
            <Product item={item} groceryList={groceryList}/>
          )}
          refreshing={refreshing}
          onRefresh={refreshProducts}
        />
      </View>
    </>
  )
}

export  function Product ({ item, groceryList }) {
  const modalRef = React.useRef(null);
  const dispatch = useDispatch();
  const [count, setCount] = React.useState(1);

  const openModal = () => {
    modalRef.current.open();
  };

  const closeModal = () => {
    modalRef.current.close();
  };

  return (
    <>
      <TouchableOpacity style={styles.container} onPress={openModal}>
        <Image source={require("../../images/list/fadeBorder.png")} style={{width: "100%", height: 100, position: "absolute", alignSelf: "flex-end", borderRadius: 5}}/>
        <ProductContent item={item} />
      </TouchableOpacity>
      <ModalHalf ref={modalRef}>
        <View style={styles.modalContent}>
          <Text style={styles.mainText}> Ajouter un article au panier </Text>
          <View style={styles.container} onPress={openModal}>
            <ProductContent item={item} />
          </View>
          <View style={{flexDirection: "row", gap: 10, alignContent: "center", justifyContent: "center"}}>
            <TouchableOpacity style={[styles.addButton, {width: 55}]} onPress={() => {
              if (count > 1)
                setCount(count - 1);
            }}>
              <Text style={styles.addText}> - </Text>
            </TouchableOpacity>
            <TextInput style={styles.textInputs} editable={false} value={count.toString()}>
            </TextInput>
            <TouchableOpacity style={[styles.addButton, {width: 55}]} onPress={() => {
              if (count < item.quantity)
                setCount(count + 1);
            }}>
              <Text style={styles.addText}> + </Text>
            </TouchableOpacity>
          </View>
          <TouchableOpacity style={[styles.addButton, {backgroundColor: item.quantity === 0 ? "#3f5599" : "#002495"}]} onPress={async () => {
            const inListProduct = {
              id: "NULL", // will be generated by the backend
              groceryListId: groceryList.id,
              productId: item.id,
              quantity: 0,
              wantedQuantity: count,
              context: "FROM_GROCERY",
            }

            const response = await addProductToGroceryList({inListProduct})
            if (response.status === 200) {
              Alert.alert("Succès", "Le produit à été ajouté avec succès");
              dispatch(hasToBeUpdate(true));
            }

            closeModal();
          }}
                            disabled={item.quantity === 0}
          >
            <Text style={styles.addText}> Ajouter à la liste 📝 </Text>
          </TouchableOpacity>
        </View>
      </ModalHalf>
    </>
  )
}

function ProductContent ({ item }) {
  const itemPriceString = item.price.toString() + "€ / unité";

  let itemStockString = "🟢 " + item.quantity.toString() + " en stock !";
  if (item.quantity <= 0) {
    itemStockString = "🔴 Rupture de stock !";
  }
  else if (item.quantity <= 10) {
    itemStockString = "🟠 " + item.quantity.toString() + " en stock !";
  }

  return (
    <View style={{flexDirection: "row", flexWrap: "wrap"}}>
      <Image source={{uri: item.imageUrl}} resizeMode="contain" style={styles.image}></Image>
      <View style={{alignSelf: 'center'}}>
        <Text style={styles.itemTitle}>
          {item.label}
        </Text>
        <Text style={styles.itemPrice}>
          {itemPriceString}
        </Text>
        <Text style={[styles.itemStock]}>
          {itemStockString}
        </Text>

      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    borderRadius: 5,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 10,
    shadowOpacity: 0.1,
    //alignSelf: "center",
    backgroundColor: "#fff",
    width: "100%",
    marginTop: 20,
  },
  image: {
    width: 100,
    height: 100,
    borderRadius: 5,
  },
  itemTitle: {
    fontFamily: "IBMPlexSans-Medium",
    fontSize: 24,
    color: "#002495",
  },
  itemPrice: {
    fontFamily: "IBMPlexSans-Light",
    fontSize: 18,
  },
  itemStock: {
    fontFamily: "IBMPlexSans-Medium",
    fontSize: 16,
  },
  modalContent: {
    backgroundColor: "white",
    paddingVertical: 10,
    paddingHorizontal: 15,
    gap: 20,
    height: Dimensions.get("window").height / 2,
  },
  mainText: {
    fontSize: 24,
    fontFamily: "IBMPlexSans-Medium",
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
  },
  textInputs: {
    textAlign: "center",
    height: 55,
    width: 55,
    backgroundColor: "#fff",
    borderRadius: 5,
    borderWidth: 2,
    borderColor: "#002495",
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 20,
    shadowOpacity: 0.2,
  },
  addText: {
    fontFamily: "IBMPlexSans-SemiBold",
    fontSize: 24,
    color: "#FFF",
  },
  listStyle: {
    paddingHorizontal: Platform.OS === "web" ? 10 : 20,
    marginBottom: 20,
  },
  listContainer: {
    marginHorizontal: Platform.OS === "web" ? 10 : 20,
    backgroundColor: "#002495",
    borderRadius: 5,
    marginBottom: 8,
    flex: 1
  },
  title: {
    fontSize: 24,
    fontFamily: "IBMPlexSans-Bold",
    marginHorizontal: 20,
  },
});