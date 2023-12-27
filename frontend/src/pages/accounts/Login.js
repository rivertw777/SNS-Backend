import React, { useState, useEffect } from "react";
import { Card, Form, Input, Button, notification } from "antd";
import { SmileOutlined, FrownOutlined } from "@ant-design/icons";
import { useHistory, useLocation} from "react-router-dom";
import { useAxios, axiosInstance } from "api";
import { useAppContext } from "store";
import { setToken } from "store";
import { parseErrorMessages } from "utils/forms";
import "./Login.scss";

export default function Login() {
  const { dispatch } = useAppContext();
  const location = useLocation();
  const history = useHistory();
  const [fieldErrors, setFieldErrors] = useState({});

  const { from: loginRedirectUrl } = location.state || {
    from: { pathname: "/" }
  };

  const onFinish = values => {
    async function fn() {
      const { name, password } = values;

      setFieldErrors({});

      const data = { name, password };
      try {
        const response = await axiosInstance.post("api/users/login", data);
        const {
          data: { token: jwtToken }
        } = response;
        
        dispatch(setToken(jwtToken));
    
        notification.open({
          message: "로그인 성공",
          icon: <SmileOutlined style={{ color: "#108ee9" }} />
        });

        history.push(loginRedirectUrl);
      } catch (error) {
        if (error.response) {
          notification.open({
            message: "로그인 실패",
            description: "아이디/암호를 확인해주세요.",
            icon: <FrownOutlined style={{ color: "#ff3333" }} />
          });

          const { data: fieldsErrorMessages } = error.response;
          setFieldErrors(parseErrorMessages(fieldsErrorMessages));
        }
      }
    }
    fn();
  };

  return (
    <div className="Login">
    <Card title="로그인">
      <Form
        {...layout}
        onFinish={onFinish}
        //   onFinishFailed={onFinishFailed}
        autoComplete={"false"}
      >
        <Form.Item
          label="Username"
          name="name"
          rules={[
            { required: true, message: "Please input your username!" },
            { min: 5, message: "5글자 입력해주세요." }
          ]}
          hasFeedback
          {...fieldErrors.username}
          {...fieldErrors.non_field_errors}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Password"
          name="password"
          rules={[{ required: true, message: "Please input your password!" }]}
          {...fieldErrors.password}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item {...tailLayout}>
          <Button type="primary" htmlType="submit">
            Submit
          </Button>
        </Form.Item>

        <Form.Item {...tailLayout}>
          <Button type="primary">
            <a href="/users/signup">Signup</a>
          </Button>
        </Form.Item>     
      </Form>
    </Card>
    </div>
  );
}

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 10}
};
  
const tailLayout = {
  wrapperCol: { offset: 11}
};