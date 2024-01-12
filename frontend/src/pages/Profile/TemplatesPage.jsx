import React from "react";
import { Image, TouchableOpacity, View, ScrollView } from "react-native";
import CreationButton from "../../components/ProfileComponents/Templates/CreationButton";
import HorizontalSeparator from "../../components/HorizontalSeparator";
import ProductListPicker from "../../components/ProfileComponents/Templates/ProductListPicker";
import TemplatesDisplayer from "../../components/ProfileComponents/Templates/TemplatesDisplayer";

export default function TemplatesPage ({navigation}) {
  const [showTemplateCreation, setShowTemplateCreation] = React.useState(false);

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
  }, [])

  return (
    <ScrollView nestedScrollEnabled={true}>
      <TemplatesDisplayer/>
      <HorizontalSeparator/>
      <CreationButton showTemplateCreation={showTemplateCreation} setShowTemplateCreation={setShowTemplateCreation}/>
      {
        showTemplateCreation
          ?
          <ProductListPicker setShowTemplateCreation={setShowTemplateCreation}/>
          :
          null
      }
    </ScrollView>
  )
}