import React, { useState } from 'react';
import { Modal, Button } from 'antd';

const App = (props) => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const btnTxt = props.btnTxt;
  const code = props.code;

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  return (
    <>
      <Button type="primary" onClick={showModal}>
        {btnTxt}
      </Button>
      <Modal title="Add New Employee" visible={isModalVisible} footer={null}>
        <h5>Send the following link to the new Employee</h5>
        <div>{code}</div>
      </Modal>
    </>
  );
};

export default App;