import {
    View,
    StyleSheet,
} from "react-native";
import React, { useState } from "react";
import SearchBar from "../../components/GroceriesComponents/SearchBar";
import Categories from "../../components/GroceriesComponents/Categories";
import { useNavigation } from "@react-navigation/native";
import { useDispatch, useSelector } from "react-redux";
import SearchProducts from "../../components/GroceriesComponents/SearchProducts";
import NoCaddie from "../../components/NoCaddie";
import HorizontalSeparator from "../../components/HorizontalSeparator";

export default function Index() {
    const navigation = useNavigation();
    const dispatch = useDispatch();

    const caddie = useSelector((state) => state.caddie.usedCaddie);

    const [showCategories, setShowCategories] = useState(true);
    const [searchText, changeSearchText] = React.useState('');

    return (
      <>
          {caddie
            ?
            <>
                <View style={{ paddingHorizontal: 20, marginTop: 20 }}>
                    <SearchBar show={setShowCategories} searchText={searchText} changeSearchText={changeSearchText}/>
                </View>
                {showCategories
                  ?
                  <Categories navigation={navigation} dispatch={dispatch}/>
                  :
                  <>
                      <HorizontalSeparator/>
                      <SearchProducts searchText={searchText}/>
                  </>
                }
            </>
             :
            <NoCaddie/>
          }
        </>
    )
}