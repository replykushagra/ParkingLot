import React, { Component } from "react";
import { render } from "react-dom";
import { Home } from "../../components/Home";
import { Header } from "../../components/Header";
import { Input } from "react-bootstrap";

export class App extends Component {
    render() {
        return (
            <div>
                <Home />
                <Header />
                <Button />
                <Input type="text" />
            </div>
        );
    }
}
render (<App/>, window.document.getElementById("app"));
