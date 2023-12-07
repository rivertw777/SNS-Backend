import React from "react";
import { Input, Menu } from "antd";
import "./AppLayout.scss";
import LogoImage from "assets/logo.png";

function AppLayout({ children, sidebar }) {
  return (
    <div className="app">
      <div className="header">
        <h1 className="page-title">
          <a href="/">
            <img src={LogoImage} alt="logo" />
          </a>
        </h1>
        <div className="search">
          <Input.Search />
        </div>
        <div className="topnav">
          <Menu mode="horizontal">
            <Menu.Item>
              <a href="users/profile">profile</a>
            </Menu.Item>
            <Menu.Item>
              <a href="users/logout">logout</a>
            </Menu.Item>
          </Menu>
        </div>
      </div>
      <div className="contents">{children}</div>
      <div className="sidebar">{sidebar}</div>
      <div className="footer">&copy; 2023. Taewon Company.</div>
    </div>
  );
}

export default AppLayout;