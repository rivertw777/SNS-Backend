import React from "react";
import { Avatar, Comment as AntdComment, Tooltip } from "antd";
import moment from "moment";

export default function Comment({ comment }) {

  console.log(comment);

  const {
    author: { username, avatarUrl },
    message,
    createdAt
  } = comment;
  const displayName = username;
  return (
    <AntdComment
      author={displayName}
      avatar={<Avatar src={avatarUrl} alt={displayName} />}
      content={<p>{message}</p>}
      datetime={
        <Tooltip title={moment().format(createdAt)}>
          <span>{moment(createdAt).fromNow()}</span>
        </Tooltip>
      }
    />
  );
}
  