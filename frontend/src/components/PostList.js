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
    if (originPostList && originPostList.length > 0) {
      const sortedPostList = originPostList.sort(
        (a, b) => b.postId - a.postId
      );
      setPostList(sortedPostList);
    }
  }, [originPostList]);

  const handleLike = async ({ post, isLike }) => {
    const apiUrl = `/api/posts/${post.postId}/like`;
    const method = isLike ? "POST" : "DELETE";

    try {
      const response = await axiosInstance({
        url: apiUrl,
        method,
        headers
      });
      console.log("response :", response);

      setPostList(prevList => {
        return prevList.map(currentPost =>
          currentPost === post
            ? { ...currentPost, isLike : isLike }
            : currentPost
        );
      });
    } catch (error) {
      console.log("error :", error);
    }
  };

  return (
    <div>
      {postList && postList.length === 0 && (
        <Alert type="warning" message="포스팅이 없습니다. :-(" />
      )}
      {postList &&
        postList.map(post => (
          <Post post={post} key={post.postId} handleLike={handleLike} />
        ))}
    </div>
  );
}

export default PostList;
