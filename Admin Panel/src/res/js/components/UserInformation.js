import React from 'react';
import { Card, Row, Col, Table, Image, Button, ButtonGroup, Accordion, Container, Badge } from 'react-bootstrap';
const userGroupClasses = {
    user: "user",
    admin: "admin",
    founder: "founder",
    developer: "developer"
};

export default class UserInformation extends React.Component {
    render() {
        return (
            <Col sm={12} md={12} lg={6} className="mt-2 mb-2">
                <Accordion>
                    <Card className="flex-wrap user-information">
                        <Card.Img src={this.props.image} className="profile-avatar p-2 rounded-circle" />
                        <Card.Body className="user-information-content">
                            <UserDisplay username={this.props.username} usergroup={this.props.usergroup} />
                            <Accordion.Toggle as="button" eventKey="0" className="close">&times;</Accordion.Toggle>
                        </Card.Body>
                        <Accordion.Collapse eventKey="0" className="user-information-drop">
                            <Card.Footer className="user-information-footer">
                                <UserManagement />
                            </Card.Footer>
                        </Accordion.Collapse>
                    </Card>
                </Accordion>
            </Col>
        );
    }
}

class UserDisplay extends React.Component {
    render() {
        return (
            <div class="user-display">
                <UserGroupPill group={this.props.usergroup} />
                <h2>{this.props.username}</h2>
            </div>
        );
    }
}

export class UserHomeDisplay extends React.Component
{
    render()
    {
        return (
            <Card>
                <Card.Body>
                    <Row>
                        <Col md={4}>
                            <Image thumbnail fluid src={this.props.image} className="shadow-sm" />
                        </Col>
                        <Col xs={12} md={8}>
                            <Container>
                                <Row>
                                    <Col><h1>Welcome {this.props.name}</h1></Col>
                                </Row>
                                <hr />
                                <Row>
                                    <Col md={4}>
                                        <Badge pill variant="primary" className="full-width">10 Bingo Games</Badge>
                                        <Badge pill variant="warning" className="full-width">10 Reports</Badge>
                                        <Badge pill variant="success" className="full-width">10 Active Members</Badge>
                                    </Col>
                                    <Col md={8}>
                                    </Col>
                                </Row>
                            </Container>
                        </Col>
                    </Row>
                    <hr />
                    {this.props.children}
                </Card.Body>
            </Card>
        );
    }
}

export class UserGroupPill extends React.Component {

    render() {
        return (
            <span className={this.getClassName()}>{this.props.group}</span>
        )
    }

    getClassName() {

        return "mr-2 p-2 user-pill pill-" + userGroupClasses[this.props.group.toLowerCase()];
    }
}

export class UserManagement extends React.Component {

    render() {
        return (
            <div className="user-management">
                <ButtonGroup>
                    <Button variant="warning">Edit</Button>
                    <Button variant="danger">Delete</Button>
                </ButtonGroup>
            </div>
        );
    }
}