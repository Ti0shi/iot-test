import {
  Text,
  View,
  StyleSheet,
  Image,
  Dimensions,
  FlatList,
  ActivityIndicator,
  TouchableOpacity,
  Alert, Platform
} from "react-native";
import React from "react";
import { deleteInListProduct, getProductById } from "../../api";
import { useDispatch } from "react-redux";
import { hasToBeUpdate } from "../../reducers/store";

export default function InListProducts ({ products, refreshing, refreshProducts }) {
  return (
    <View>
      <Text style={styles.title}>Votre liste de course !</Text>
      <View style={[styles.listContainer, {shadowColor: "#000",
        shadowOffset: { width: 0, height: 0 },
        shadowRadius: 10,
        shadowOpacity: 0.2,
      }]}>
        <FlatList
          style={styles.listStyle}
          data={products}
          renderItem={({ item, index }) => (
            <>
              <Product item={item}/>
              {index < products.length - 1 ? <View style={{height: 20}}></View> : null}
            </>
          )}
          refreshing={refreshing}
          onRefresh={refreshProducts}
        />
      </View>
    </View>
  )
}

function Product ({ item }) {
  const dispatch = useDispatch();
  const quantityInCaddie = item.quantity + "/" + item.wantedQuantity + " dans le caddie";
  const [product, setProduct] = React.useState(null);
  const [loading, setLoading] = React.useState(true);

  React.useEffect(() => {
    async function getProduct() {
      const response = await getProductById(item.productId);
      if (response.status === 200) {
        setProduct(response.data);
      }

      setLoading(false);
    }
    getProduct()
  }, []);

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#000000" />
      </View>
    );
  }

  const itemPriceString = product.price.toString() + "€ / unité";

  return (
    <View style={styles.container}>
      <View style={{flexDirection: "row", flexWrap: "wrap"}}>
        <Image source={{uri: product.imageUrl}} resizeMode="contain" style={styles.image}></Image>
        <View style={{alignSelf: 'center', gap: 5}}>
          <Text style={styles.itemTitle}>
            {product.label}
          </Text>
          <Text style={styles.itemPrice}>
            {itemPriceString}
          </Text>
        </View>
        <TouchableOpacity style={styles.deleteContainer} onPress={async () => {
          if (item.quantity === 0) {
            const response = await deleteInListProduct(item.id);
            if (response.status === 200) {
              dispatch(hasToBeUpdate(true));
            }
          }
          else {
            Alert.alert("Erreur", "Vous ne pouvez pas supprimer un produit qui est dans le caddie");
          }
        }}>
          <Image source={require("../../images/list/delete.png")} style={{width: 30, height: 30, alignSelf: "center"}}></Image>
          <Text style={styles.itemStock}>{quantityInCaddie}</Text>
        </TouchableOpacity>
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
  itemStock: {
    fontFamily: "IBMPlexSans-Medium",
    fontSize: 10,
  },
  listStyle: {
    marginVertical: 20,
  },
  listContainer: {
    marginHorizontal: Platform.OS === "web" ? 10 : 20,
    marginTop: 10,
    backgroundColor: "#002495",
    borderRadius: 5,
  },
  loadingContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  deleteContainer: {
    justifyContent: "flex-end",
    alignSelf: "center",
    marginLeft: "auto",
    marginRight: 20,
  },
  title: {
    fontSize: 24,
    fontFamily: "IBMPlexSans-Bold",
    marginHorizontal: 20,
  },
});