import DropDownPicker from "react-native-dropdown-picker";
import { FlatList, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import React, { useState } from "react";
import { getAllCategories, getAllSubcategories } from "../../api";
import { Min, Max } from "../../misc/Maths"
import { useSelector } from "react-redux";
import { setCategory } from "../../reducers/store";
import Promotions from "../ProfileComponents/Promotions";

export default function Categories({ navigation, dispatch }) {
  const [categories, setCategories] = React.useState(null);
  const supermarket = useSelector((state) => state.currentSupermarket.supermarket);

  React.useEffect(() => {
    async function getCategories() {
      const response = await getAllCategories(supermarket.id);
      if (response.status === 200) {
        setCategories(response.data);
      }
    }
    getCategories();
  }, []);

  return (
    <>
      <ScrollView style={styles.scrollView}>
        {categories?.map((item, key) => (
          <CategoryItem key={key} item={item} navigation={navigation} dispatch={dispatch} />
        ))}
        <Promotions navigation={navigation} width={"100%"}/>
        <View style={{height: 50}}/>
      </ScrollView>
    </>
  )
}

export function CategoryItem({ item, navigation, dispatch }) {
  const [open, setOpen] = useState(false);
  const [items, setItems] = useState([]);
  const [value, setValue] = useState(null);
  const [valChanged, setValChanged] = useState(null);
  const [loading, setLoading] = useState(false);

  React.useEffect(() => {
    async function getSubcatagories() {
      const response = await getAllSubcategories(item.id);

      setItems([]); // Reset items
      if (response.status === 200) {
        response.data.map((subcategory) => {
          setItems((items) => [
            ...items,
            {label: subcategory.label, value: subcategory.id},
          ]);
        });
      }
    }
    getSubcatagories();
  }, []);

  const hasValChanged = async () => {
    if (value !== valChanged) {
      const category = await items.find((item) => item.value === value);
      dispatch(setCategory(category));
      navigation.navigate("Products");
      setValChanged(null);
      setValue(null);
    }
  }
  hasValChanged();

  return (
    <DropDownPicker
      style={[styles.dropdown, {marginBottom: open ? Min(Max(1, items.length), 5) * 40 + 20 : 20}]}
      loading={loading}
      placeholderStyle={{
        fontFamily: "IBMPlexSans-SemiBold",
        fontSize: 19,
        color: "#fff",
      }}
      arrowIconStyle={{
        tintColor: "#fff",
      }}
      placeholder={item.label}
      setValue={setValue}
      items={items}
      open={open}
      setOpen={setOpen}
      dropDownDirection="BOTTOM"
      listMode={"SCROLLVIEW"}
      value={null}
      ArrowUpIconComponent={() => (
        <Image source={{uri: item.imageUrl}} style={{width: 40, height: 40}}/>
      )}
      ArrowDownIconComponent={() => (
        <Image source={{uri: item.imageUrl}} style={{width: 40, height: 40}}/>
      )}
    ></DropDownPicker>
  )
}

const styles = StyleSheet.create({
  scrollView: {
    flex: 1,
    paddingHorizontal: 20,
    paddingVertical: 20,
  },
  dropdown: {
    backgroundColor: "#002495",
    borderRadius: 5,
    alignSelf: "center",
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 10,
    shadowOpacity: 0.2,
  },
});