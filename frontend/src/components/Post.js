import React from "react";
import { Avatar, Card, Comment, Tooltip } from "antd";
import { HeartOutlined, HeartTwoTone, UserOutlined } from "@ant-design/icons";
import "./Post.scss";
import { useAppContext } from "store";
import CommentList from "./CommentList";

function Post({ post }) {
  const { caption, location, photoPaths } = post;

  return (
    <div className="post">
      <Card
        hoverable
        cover={<img src={photoPaths} alt={caption} />}
      >
        <Card.Meta
          title={location}
          description={caption}
          style={{ marginBottom: "0.5em" }}
        />

        <CommentList post={post} />
      </Card>

      {/* <img src={photo} alt={caption} style={{ width: "100px" }} />
      {caption}, {location} */}
    </div>
  );
}

export default Post;
