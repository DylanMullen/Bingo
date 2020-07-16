import React from 'react';
import { Form, Card, Button, Container, Jumbotron } from 'react-bootstrap';

export default class LoginPage extends React.Component {
    render() {
        return (
            <main id="login">
                <Container fluid>
                    <LoginForm />
                </Container>
            </main>
        );
    }
}

class LoginForm extends React.Component {
    render() {
        return (
            <section>
                <Card className="shadow-lg">
                    <Card.Body>
                        <Jumbotron>
                            <Container className="text-center">
                                <h1>Please Login</h1>
                                <p>You must login to proceed further</p>
                            </Container>
                        </Jumbotron>
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
            </section>
        );
    }

}