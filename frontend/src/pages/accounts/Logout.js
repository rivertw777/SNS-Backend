import React, { useEffect } from "react";
import { useAppContext, deleteToken } from "store";
import { useHistory } from "react-router-dom";
import { notification } from "antd";
import { MehOutlined  } from "@ant-design/icons";

export default function Logout() {
  const { dispatch } = useAppContext();
  const history = useHistory();

  useEffect(() => {
    dispatch(deleteToken());
    history.push("/");
  }, [dispatch, history]);

  notification.open({
    message: "로그아웃 완료",
    icon: <MehOutlined  style={{ color: "#fa8c16" }} />
  });
}