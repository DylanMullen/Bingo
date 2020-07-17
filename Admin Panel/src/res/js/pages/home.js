import React from 'react';
import { Container, Row, Col, Card, Jumbotron } from 'react-bootstrap';
import UserInformation, { UserHomeDisplay } from '../components/UserInformation';
import UserPage from './Users';
import { InformationTab } from '../components/InformationTab'

export default class Homepage extends React.Component {

    render() {
        return (
            <main id="home">

                <Container fluid className="pt-3 pb-3">
                    <Jumbotron>

                    </Jumbotron>
                    {this.showInformationRow()}
                    <Row className="mt-3">
                        {this.showProfileContainer()}
                    </Row>
                    <Row className="mt-3">
                        <Col>
                            {this.currentStats()}
                        </Col>
                    </Row>
                </Container>
            </main>
        );
    }

    showInformationRow() {
        return (<Row>
            <Col>
                <InformationTab header="Users Connected"
                    text="100 Users"
                    image="https://upload.wikimedia.org/wikipedia/commons/e/ee/Flag_Admirals_of_the_Blue_Squadron_Royal_Navy.png" />
            </Col>
            <Col>
                <InformationTab header="Bingo Games"
                    text="??? Active Games"
                    image="https://upload.wikimedia.org/wikipedia/commons/e/ee/Flag_Admirals_of_the_Blue_Squadron_Royal_Navy.png" />

            </Col>
            <Col>
                <InformationTab header="Mass Currency"
                    text="$??? in Circulation"
                    image="https://upload.wikimedia.org/wikipedia/commons/e/ee/Flag_Admirals_of_the_Blue_Squadron_Royal_Navy.png" />
            </Col>
        </Row>);
    }

    showProfileContainer() {
        return (
            <Col xs={12} md={8}>
                <UserHomeDisplay image="https://upload.wikimedia.org/wikipedia/commons/e/ee/Flag_Admirals_of_the_Blue_Squadron_Royal_Navy.png">

                </UserHomeDisplay>
            </Col>
        )
    }

    currentStats() {
        return (<Card>
            <Card.Header as="h3" className="">Current Statistics</Card.Header>
            <Card.Body>

            </Card.Body>
        </Card>);
    }


}