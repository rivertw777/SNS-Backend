import React from "react";
import {Route} from "react-router-dom";
import Login from "./Login";
import Profile from "./Profile";
import Signup from "./Signup";
import Logout from "./Logout";
import LoginRequiredRoute from "utils/LoginRequiredRoute";

function Routes({ match }) {
    return (
      <>
        <LoginRequiredRoute exact path={match.url + "/profile"} component={Profile}/>
        <LoginRequiredRoute exact path={match.url + "/logout"} component={Logout}/>
        <Route exact path={match.url + "/login"} component={Login} />
        <Route exact path={match.url + "/signup"} component={Signup} />
      </>
    );
  }

export default Routes;