import React from "react";
import { Avatar, Card, Comment, Tooltip } from "antd";
import { HeartOutlined, HeartTwoTone, UserOutlined } from "@ant-design/icons";
import "./Post.scss";
import { useAppContext } from "store";
import CommentList from "./CommentList";

function Post({ post }) {

  const { author, caption, location, photo_url } = post;
  const { username, avatar_url } = author;

  return (
    <div className="post">
      <Card
        hoverable
        cover={<img src={photo_url} alt={caption} />}
        actions={[

        ]}
      >
        <Card.Meta
          avatar={
            <Avatar
              size="large"
              icon={<img src={avatar_url} alt={username} />}
            />
          }
          title={location}
          description={caption}
          style={{ marginBottom: "0.5em" }}
        />

        <CommentList post={post} />
      </Card>
    </div>
  );
}

export default Post;