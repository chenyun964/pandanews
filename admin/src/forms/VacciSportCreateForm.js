import { Modal, Select, Input, Form } from 'antd';

const { Option } = Select;

export const VacciSpotCreateForm = ({ visible, onCreate, onCancel }) => {
    const [form] = Form.useForm();
    return (
        <Modal
            visible={visible}
            title="Create New Vaccination Spot"
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
                labelCol={{ span: 6 }}
                wrapperCol={{ span: 16 }}
                layout="horizontal"
                name="form_in_modal"
                requiredMark={false}
            >
                <Form.Item
                    name="name"
                    label="Name"
                    rules={[{
                        required: true,
                        message: 'Please input name of the vaccination spot!',
                    }]}
                >
                    <Input placeholder="Please input name of the vaccination spot" />
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
                        message: 'Please select type of building!'
                    }]}
                >
                    <Select placeholder="Please select type of building">
                        <Option value="Vaccination Centre">Vaccination Centre</Option>
                        <Option value="Polyclinic">Polyclinic</Option>
                        <Option value="Clinic">Clinic</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    name="region"
                    label="Region"
                    hasFeedback
                    rules={[{
                        required: true,
                        message: 'Please select region!'
                    }]}
                >
                    <Select placeholder="Please select region">
                        <Option value="Central">Central</Option>
                        <Option value="North">North</Option>
                        <Option value="West">West</Option>
                        <Option value="East">East</Option>
                        <Option value="North East">North East</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    name="vacciType"
                    label="Vaccine Type"
                    hasFeedback
                    rules={[{
                        required: true,
                        message: 'Please select vaccine type!'
                    }]}
                >
                    <Select placeholder="Please select vaccine type">
                        <Option value="Moderna">Moderna</Option>
                        <Option value="Pfizer/Comirnaty">Pfizer/Comirnaty</Option>
                    </Select>
                </Form.Item>
            </Form>
        </Modal>
    );
};