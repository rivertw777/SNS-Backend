import React from "react";
import { Avatar, Card, Comment, Tooltip } from "antd";
import { HeartOutlined, HeartTwoTone, UserOutlined } from "@ant-design/icons";
import "./Post.scss";
import { useAppContext } from "store";
import CommentList from "./CommentList";

function Post({ post }) {

  const { author, caption, location, photoUrl } = post;
  const { username, avatarUrl } = author;


  return (
    <div className="post">
      <Card
        hoverable
        cover={
          < div style={{ padding: "29px" , paddingBottom: "10px"}}>
            <img src={photoUrl} alt="사진" style={{ width: "620px", height: "440px" }}/>
          </div>
        }
        actions={[

        ]}
      >
        <Card.Meta
          avatar={
            <Avatar
              size="large"
              icon={<img src={avatarUrl} alt={username} />}
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