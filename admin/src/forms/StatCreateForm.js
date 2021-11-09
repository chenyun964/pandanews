import { Modal, InputNumber, Form, DatePicker } from 'antd';

export const StatCreateForm = ({ visible, onCreate, onCancel }) => {
    const [form] = Form.useForm();
    return (
        <Modal
            visible={visible}
            title="New Covid Statistic"
            okText="Create"
            cancelText="Cancel"
            onCancel={onCancel}
            onOk={() => {
                form
                    .validateFields()
                    .then((values) => {
                        form.resetFields();
                        onCreate(values);
                    })
                    .catch((info) => {
                        console.log('Validate Failed:', info);
                    });
            }}
        >
            <Form
                form={form}
                labelCol={{ span: 7 }}
                wrapperCol={{ span: 18 }}
                layout="horizontal"
                name="form_in_modal"
                requiredMark={false}
            >
                <Form.Item
                    name="date"
                    label="Date"
                    rules={[{
                        required: true,
                        message: 'Please select the date!',
                    }]}
                >
                    <DatePicker style={{ width: '100%' }} />
                </Form.Item>
                <Form.Item
                    name="newCases"
                    label="New Cases"
                    rules={[{
                        required: true,
                        message: 'Please input new cases!',
                    }]}
                >
                    <InputNumber min={0} style={{ width: '100%' }} />
                </Form.Item>
                <Form.Item
                    name="newDeaths"
                    label="New Deaths"
                    rules={[{
                        required: true,
                        message: 'Please input new deaths!',
                    }]}
                >
                    <InputNumber min={0} style={{ width: '100%' }} />
                </Form.Item>
                <Form.Item
                    name="newRecovery"
                    label="New Recovery"
                    rules={[{
                        required: true,
                        message: 'Please input new covery!',
                    }]}
                >
                    <InputNumber min={0} style={{ width: '100%' }} />
                </Form.Item>
            </Form>
        </Modal>
    );
};