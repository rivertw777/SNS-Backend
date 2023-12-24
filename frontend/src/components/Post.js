import React from "react";
import { Avatar, Card, Comment, Tooltip } from "antd";
import { HeartOutlined, HeartTwoTone, UserOutlined } from "@ant-design/icons";
import "./Post.scss";
import { useAppContext } from "store";
import CommentList from "./CommentList";

function Post({ post, handleLike }) {

  const { author, caption, location, photoUrl, isLike } = post;
  const { name, avatarUrl } = author;

  console.log(post);

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
          isLike ? (
            <HeartTwoTone
              twoToneColor="#eb2f96"
              onClick={() => handleLike({ post, isLike: false })}
            />
          ) : (
            <HeartOutlined onClick={() => handleLike({ post, isLike: true })} />
          )
        ]}
      >
        <Card.Meta
          avatar={
            <Avatar
              size="large"
              icon={<img src={avatarUrl} alt={name} />}
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