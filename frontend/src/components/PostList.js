import React, { useEffect, useState } from "react";
import { Alert } from "antd";
import { useAxios, axiosInstance } from "api";
import Post from "./Post";
import { useAppContext } from "store";

function PostList() {
  const {
    store: { jwtToken }
  } = useAppContext();

  const [postList, setPostList] = useState([]);

  const headers = { Authorization: `Bearer ${jwtToken}` };

  const [{ data: originPostList, loading, error }, refetch] = useAxios({
    url: "/api/posts",
    headers
  });

  useEffect(() => {
    setPostList(originPostList);
  }, [originPostList]);

  return (
    <div> 
      {postList && postList.length === 0 && (
        <Alert type="warning" message="포스팅이 없습니다. :-(" />
      )}
      {postList &&
        postList.reverse().map(post => (
          <Post post={post} key={post.id} />
        ))}
    </div>
  );
}

export default PostList;
