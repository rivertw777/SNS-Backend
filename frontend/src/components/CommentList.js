import React, { useState } from "react";
import { Input, Button, Alert } from "antd";
import { useAppContext } from "store";
import { axiosInstance, useAxios } from "api";
import Comment from "./Comment";

export default function CommentList({ post }){

  const { store: {jwtToken}, dispatch } = useAppContext();
    
  const [commentContent, setCommentContent] = useState("");

  const headers = { Authorization: `Bearer ${jwtToken}` };
  
  const [{ data: commentList, loading, error }, refetch] = useAxios({
    url : `/api/posts/${post.postId}/comments`,
    headers
  });

  const handleCommentSave = async () => {
    const apiUrl = `/api/posts/${post.postId}/comments`;

    console.group("handleCommentSave");
    try {
      const response = await axiosInstance.post(
        apiUrl, 
        { message: commentContent },
        { headers }
      );
      console.log(response)
      setCommentContent("");
      refetch();
    } catch (error) {
      console.log(error);
    }

    console.groupEnd();
  }

  return (
    <div>
      {commentList &&
        commentList.map(comment => (
          <Comment key={comment.commmentId} comment={comment} />
      ))}
      <Input.TextArea
        style={{ marginBottom: ".5em" }}
        value={commentContent}
        onChange={e => setCommentContent(e.target.value)}
      />
      <Button
        block
        type="primary"
        disabled={commentContent.length === 0}
        onClick={handleCommentSave}
      >
        댓글 쓰기
      </Button>
    </div>
  );

}