import React, { useEffect, useState } from "react";
import { Card } from "antd";
import Suggestion from "./Suggestion";
import { useAxios, axiosInstance } from "api";
import { useAppContext } from "store";
import "./SuggestionList.scss";

export default function SuggestionList({ style }) {
  const {
    store: { jwtToken }
  } = useAppContext();

  const [userList, setUserList] = useState();

  const headers = { Authorization: `Bearer ${jwtToken}` };

  const [{ data: origUserList, loading, error }, refetch] = useAxios({
    url: "api/users/suggestions",
    headers
  });

  useEffect(() => {
    if (!origUserList) setUserList([]);
    else setUserList(origUserList.map(user => ({...user, isFollow: false })));
  }, [origUserList]); 

  const onFollowUser = name => {
    const data = { name };
    const config = { headers };
    axiosInstance.post("api/users/follow", data, config)
      .then(response => {
        setUserList(prevUserList =>
          prevUserList.map(user =>
            user.name !== name ? user : { ...user, isFollow: true }
          )
        );
      })
    .catch(error => {
      console.error(error); 
    });
  };

  return (
    <div style={style}>
      {loading && <div>Loading ...</div>}
      {error && <div>로딩 중에 에러가 발생했습니다.</div>}

      <Card title="Suggestions for you " size="small">
        {userList && userList.map(suggestionUser => (
          <Suggestion 
            key={suggestionUser.name} 
            suggestionUser={suggestionUser} 
            onFollowUser={onFollowUser}
          />
        ))}
      </Card>
    </div>
  );
}
