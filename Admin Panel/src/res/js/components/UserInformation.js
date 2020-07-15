import React from 'react';
import { Card, Row, Col, Table, Image } from 'react-bootstrap';
import Container from './section';

export default class UserInformation extends React.Component {
    render() {
        return (
            <Card >
                <Card.Header />
                <Card.Body>
                    <Container fluid>
                        <Row>
                            <this.UserPicture />
                            <Col sm={3}>
                                <Table bordered={false}>
                                    <tr>
                                        <td>Username</td>
                                        <td>TwixDylan</td>
                                    </tr>
                                </Table>
                            </Col>
                        </Row>
                    </Container>
                </Card.Body>
            </Card>
        );
    }

    UserPicture()
                {
        return (<Col sm={3}>
                    <Image src="https://www.tremark.co.uk/wp-content/uploads/2016/02/placeholder-male.png" roundedCircle fluid />
                </Col>);
    }
}