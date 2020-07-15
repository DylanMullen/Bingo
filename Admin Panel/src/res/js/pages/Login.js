import React from 'react';
import { Form, Card, Button } from 'react-bootstrap';

export default class LoginPage extends React.Component {
    render() {
        return (
            <section id="login">
                <LoginForm />
            </section>
        );
    }
}

class LoginForm extends React.Component {
    render() {
        return (
            <Card>
                <Card.Body>
                    <Form>
                        <Form.Group>
                            <Form.Label>Email Address</Form.Label>
                            <Form.Control type="email" placeholder="Email" />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password" />
                        </Form.Group>
                        <Button variant="primary" type="submit" block>Login</Button>
                    </Form>
                </Card.Body>
            </Card>
        );
    }

}