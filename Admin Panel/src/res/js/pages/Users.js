import React from 'react';
import { Table, Button, ButtonGroup, Row, Container, Jumbotron, Card, Nav, NavDropdown } from 'react-bootstrap';
import UserInformation from '../components/UserInformation';

export default class UserPage extends React.Component {
    state = {
        users: [],
        contacts: []
    };

    constructor(props)
    {
        super(props);
        this.fetchUsers();

    }

    render() {
        return (
            <main>
                <Jumbotron className="text-center">
                    <h1>User Management</h1>
                </Jumbotron>
                <Container> 
                    <Card>
                        <Card.Header as="h4" className="text-center">
                            <h2>Users</h2>
                            {this.showTabs()}
                        </Card.Header>

                        <Card.Body>
                            <Container fluid>
                                <Row>
                                    {this.state.users.map(x => {
                                        return <UserInformation
                                            username={x.name}
                                            usergroup={x.userGroup}
                                            image="https://upload.wikimedia.org/wikipedia/commons/e/ee/Flag_Admirals_of_the_Blue_Squadron_Royal_Navy.png" />
                                    })}
                                </Row>
                            </Container>
                        </Card.Body>
                    </Card>
                </Container>
            </main>
        );
    }

    componentDidMount() {
    }

    fetchUsers() {
        fetch("https://api.github.com/users/DylanMullen/repos")
            .then(response => response.json())
            .then(data => {
                this.setState({
                    users: data.map((element) => {
                        return {
                            name: "TwixDylan",
                            userGroup: "Founder"
                        };
                    })
                });
            });
    }

    showTabs() {
        return (
            <Nav justify fill variant="tabs">
                <Nav.Item>
                    <Nav.Link>All Users</Nav.Link>
                </Nav.Item>
                <NavDropdown title="Staff">
                    <NavDropdown.Item>All Staff</NavDropdown.Item>
                    <NavDropdown.Divider />
                    <NavDropdown.Item>Admin</NavDropdown.Item>
                    <NavDropdown.Item>Developer</NavDropdown.Item>
                    <NavDropdown.Item>Founder</NavDropdown.Item>
                </NavDropdown>
            </Nav>
        );
    }
}