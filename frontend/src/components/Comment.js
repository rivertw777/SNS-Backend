import React from "react";
import { Avatar, Comment as AntdComment, Tooltip } from "antd";
import moment from "moment";

export default function Comment({ comment }) {

  const {
    author: { name, avatarUrl },
    message,
    createdAt
  } = comment;
  const displayName = name;
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
  