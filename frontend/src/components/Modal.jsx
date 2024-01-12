import { Dimensions } from "react-native";
import React from "react";
import RBSheet from "react-native-raw-bottom-sheet";

const Modal = React.forwardRef((props, ref) => {
  return (
    <RBSheet
      ref={ref}
      height={Dimensions.get("window").height * 0.5}
      openDuration={250}
      closeOnDragDown={true}
      dragFromTopOnly={true}
      minClosingHeight={Dimensions.get("window").height * 0.4}
      customStyles={{
        draggableIcon: {
          backgroundColor: "#565656",
          width: 40,
          height: 4,
          marginBottom: 15,
        },
        container: {
          borderTopLeftRadius: 16,
          borderTopRightRadius: 16,
        },
      }}
      onClose={props.onClose}
    >
      {props.children}
    </RBSheet>
  );
});

export default Modal;
