import React from "react";
import { Avatar, Button } from "antd";
import { API_HOST } from "../Constants";
import "./Suggestion.scss";

export default function Suggestion({ suggestionUser, onFollowUser }) {
  const { name, avatarUrl, isFollow }  = suggestionUser;

  console.log(suggestionUser);

  return (
    <div className="suggestion">
      <div className="avatar">
        <Avatar
          size="small"
          icon={
            <img
              src={avatarUrl}
              alt={`${name}'s avatar`}
            />
          }
        />
      </div>
      <div className="username">{name}</div>
      <div className="action">
        {isFollow && "팔로잉 중"}
        {!isFollow && <Button size="small" onClick={() => onFollowUser(name)}>
          Follow
        </Button>}
      </div>
    </div>
  );
}