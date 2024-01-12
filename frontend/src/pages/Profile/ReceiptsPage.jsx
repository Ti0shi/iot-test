import { FlatList, Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { setCategory } from "../../reducers/store";
import React from "react";
import { useDispatch, useSelector } from "react-redux";
import Loading from "../../components/Loading";
import { getFinishedGroceryLists } from "../../api";
import ReceiptItem from "../../components/ProfileComponents/Receipts/ReceiptItem";

export default function ReceiptsPage ({navigation}) {
  const [loading, setLoading] = React.useState(true);
  const [receipts, setReceipts] = React.useState([]);
  const [refreshing, setRefreshing] = React.useState(false);

  const user = useSelector((state) => state.account.user);

  React.useEffect(() => {
    navigation.setOptions({
      headerLeft: () => (
        <View style={{ marginLeft: 10 }}>
          <TouchableOpacity onPress={() => {
            navigation.goBack();
          }} style={{ padding: 10 }}>
            <Image
              source={require("../../images/left-arrow.png")}
              style={{ width: 16, height: 16 }}
              resizeMode="contain"
            />
          </TouchableOpacity>
        </View>
      ),
    });

    async function getReceipts () {
      const response = await getFinishedGroceryLists(user.id);

      if (response.status === 200)
        setReceipts(response.data);

      setLoading(false);
    }
    getReceipts();

  }, [])

  const refreshReceipts = async () => {
    const startingTime = Date.now();
    setRefreshing(true);

    const response = await getFinishedGroceryLists(user.id);

    if (response.status === 200)
      setReceipts(response.data);

    const endingTime = Date.now();
    const timeElapsed = endingTime - startingTime;

    if (timeElapsed < 600) {
      setTimeout(() => {
        setRefreshing(false);
      }, 600 - timeElapsed);
      return;
    }
  };

  if (loading) {
    return (
      <Loading />
    )
  }

  return (
    <FlatList
      data={receipts}
      renderItem={({ item }) => <ReceiptItem item={item}/>}
      refreshing={refreshing}
      onRefresh={refreshReceipts}
    />
  )
}
