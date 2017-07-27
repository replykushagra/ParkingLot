import React from "react";
import { render } from "react-dom";

export class Header extends React.Component {

    render() {
        return (
            <nav className="navbar navar-default">
                <div className="container">
                    <div className="nav navbar-nav">
                        <ul className="nav navbar-nav">
                            <li><a href="#">Home</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        );
    }
}
