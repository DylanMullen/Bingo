import React from 'react';

class Navbar extends React.Component {
    render() {
        return (
          <NavigationMenu>
              <NavigationItem active={false} link="#" text="Home"/>
              <NavigationItem active={false} link="#" text="Users"/>
              <NavigationItem active={false} link="#" text="Bingo Games"/>
              <NavigationItem active={false} link="#" text="Leaderboard"/>
              <NavigationItem active={false} link="#" bottom={true} text="Logout"/>
          </NavigationMenu>
        );
    }
}

class NavigationMenu extends React.Component
{
    render()
    {
        return (
            <nav className="navbar">
                <ul>
                    {this.props.children}
                </ul>
            </nav>
        );
    }
}

class NavigationItem extends React.Component {
    render() 
    {
        return (
            <li className={this.getClassName()}>
                <a href={this.props.link}>
                    <span className="nav-icon">{this.props.icon}</span>
                    {this.props.text}
                </a>
            </li>
        );
    }

    isActive()
    {
        return this.props.active;
    }

    isBottom()
    {
        if(this.props.bottom ==null)
            return false;
        else
            return this.props.bottom;
    }

    getClassName()
    {
        return "nav-item " + (this.isActive() ? "nav-active " : "") + (this.isBottom() ?"nav-bottom" : "");
    }
}

class NavigationLogo extends React.Component{
    render()
    {
        return (
            <li>
                <a href={this.props.link}>
                    <img src={this.props.image} alt={this.props.alt}/>
                </a>
            </li>

        );
    }
}

export default Navbar;