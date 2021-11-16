import { Modal, Select, Input, Form } from 'antd';

const { Option } = Select;

export const TestSpotCreateForm = ({ visible, onCreate, onCancel }) => {
    const [form] = Form.useForm();
    return (
        <Modal
            visible={visible}
            title="Create a new swab test spot"
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
                    name="name"
                    label="Name"
                    rules={[{
                        required: true,
                        message: 'Please input name of the swab test spot!',
                    }]}
                >
                    <Input placeholder="Please input name of the swab test spot" />
                </Form.Item>
                <Form.Item
                    name="address"
                    label="Address"
                    rules={[{
                        required: true,
                        message: 'Please input address!',
                    }]}
                >
                    <Input.TextArea placeholder="Please input address" />
                </Form.Item>
                <Form.Item
                    name="type"
                    label="Type"
                    hasFeedback
                    rules={[{
                        required: true,
                        message: 'Please select type of test!'
                    }]}
                >
                    <Select placeholder="Please select type of test">
                        <Option value="PCR">PCR</Option>
                        <Option value="ART">ART</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    name="opHours"
                    label="Operating Hours"
                    rules={[{
                        required: true,
                        message: 'Please input operating hours!',
                    }]}
                >
                    <Input.TextArea placeholder="Please input operating hours" />
                </Form.Item>
                <Form.Item
                    name="contact"
                    label="Contact"
                    rules={[{
                        required: true,
                        message: 'Please input contact!',
                    }]}
                >
                    <Input placeholder="Please input contact" />
                </Form.Item>
            </Form>
        </Modal>
    );
};