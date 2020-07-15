import React from 'react';
import Section from '../components/section'
import { Container, Row, Col } from 'react-bootstrap';
import UserInformation from '../components/UserInformation';

export default class Homepage extends React.Component {

    render() {
        return (
            <Container fluid>
                <Row>
                    <Col xs={12} sm={8}>
                        <UserInformation />
                    </Col>
                    <Col xs={12} sm={4}>

                    </Col>
                </Row>
            </Container>
        );
    }
}