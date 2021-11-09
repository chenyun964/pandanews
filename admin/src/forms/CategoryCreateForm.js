import { Modal, Input, Form } from 'antd';

export const CategoryCreateForm = ({ visible, onCreate, onCancel }) => {
    const [form] = Form.useForm();
    return (
        <Modal
            visible={visible}
            title="New News Category"
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
                    name="title"
                    label="Title"
                    rules={[{
                        required: true,
                        message: 'Please input the title!',
                    }]}
                >
                    <Input style={{ width: '100%' }} />
                </Form.Item>
            </Form>
        </Modal>
    );
};