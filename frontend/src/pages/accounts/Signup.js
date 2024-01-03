import React, { useState, useEffect } from "react";
import { Card, Form, Input, Button, notification } from "antd";
import { SmileOutlined, FrownOutlined } from "@ant-design/icons";
import { useHistory } from "react-router-dom";
import { useAxios, axiosInstance } from "api";
import "./Signup.scss";

export default function Signup() {
  const history = useHistory();
  const [fieldErrors, setFieldErrors] = useState({});

  const onFinish = async (values) => {
    const { name, password } = values;

    setFieldErrors({});

    const data = { name, password };
    try {
      await axiosInstance.post("/api/users", data);

      notification.open({
        message: "회원가입 성공",
        description: "로그인 페이지로 이동합니다.",
        icon: <SmileOutlined style={{ color: "#108ee9" }} />,
      });

      history.push("/users/login");
    } catch (error) {
      if (error.response) {
        const { status, data: { errorMessage } } = error.response;

        notification.open({
          message: `회원가입 실패`,
          description: errorMessage,
          icon: <FrownOutlined style={{ color: "#ff3333" }} />,
        });

        setFieldErrors((prevErrors) => {
          const updatedErrors = {};

          for (const [fieldName, errors] of Object.entries(prevErrors)) {
            const errorMessage = errors instanceof Array ? errors.join(" ") : errors;
            updatedErrors[fieldName] = {
              validateStatus: "error",
              help: errorMessage,
            };
          }

          return {
            ...prevErrors,
            ...updatedErrors,
          };
        });
      }
    }
  };

  return (
    <div className="Signup">
      <Card title="회원가입">
        <Form {...layout} onFinish={onFinish}>
          <Form.Item
            label="Username"
            name="name"
            rules={[
              { required: true, message: "Please input your username!" },
              { min: 5, message: "5글자 입력해주세요." },
            ]}
            hasFeedback
            {...fieldErrors.username}
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
        </Form>
      </Card>
    </div>
  );
}

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 10 },
};

const tailLayout = {
  wrapperCol: { offset: 11 },
};
