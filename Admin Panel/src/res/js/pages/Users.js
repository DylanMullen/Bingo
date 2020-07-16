import React from 'react';
import { Table, Button, ButtonGroup,Row } from 'react-bootstrap';

export default class UserPage extends React.Component {
    state = {
        users: [],
        contacts: []
    };
F
    render() {
        return (
            <Table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Usergroup</th>
                        <th>Manage</th>
                    </tr>
                </thead>
                <tbody>
                    {this.state.users.map((element) => {
                        return (
                            <tr>
                                <td>{element.name}</td>
                                <td>{element.userGroup}</td>
                                <td>
                                    <Row>
                                    <Button variant="warning">Edit</Button>
                                    <Button variant="danger">Delete</Button>
                                    </Row>
                                </td>
                            </tr>    
                        );
                    })}
                </tbody>

            </Table>

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
                            name: element.name,
                            userGroup: element.description
                        };
                    })
                });
            });
    }
}