import React from "react";
import { Avatar, Button } from "antd";
import { API_HOST } from "../Constants";
import "./Suggestion.scss";

export default function Suggestion({ suggestionUser, onFollowUser }) {
  const { username, name, avatar_url, is_follow }  = suggestionUser;
  return (
    <div className="suggestion">
      <div className="avatar">
        <Avatar
          size="small"
          icon={
            <img
              src={ API_HOST + avatar_url}
              alt={`${username}'s avatar`}
            />
          }
        />
      </div>
      <div className="username">{name.length === 0 ? username : name}</div>
      <div className="action">
        {is_follow && "팔로잉 중"}
        {!is_follow && <Button size="small" onClick={() => onFollowUser(username)}>
          Follow
        </Button>}
      </div>
    </div>
  );
}