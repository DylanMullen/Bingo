import React from 'react';
import { Table, Button, ButtonGroup, Row, Container, Jumbotron, Card } from 'react-bootstrap';
import UserInformation from '../components/UserInformation';

export default class UserPage extends React.Component {
    state = {
        users: [],
        contacts: []
    };
    F
    render() {
        return (
            <main>
                <Jumbotron className="text-center">
                    <h1>User Management</h1>
                </Jumbotron>
                <Container>
                    <Card>
                        <Card.Header as="h4" className="text-center">Registered Users</Card.Header>
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
        this.fetchUsers();
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
}