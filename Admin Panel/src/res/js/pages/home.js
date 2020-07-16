import React from 'react';
import { Container, Row, Col, Card } from 'react-bootstrap';
import UserInformation from '../components/UserInformation';
import UserPage from './Users';
import { InformationTab } from '../components/InformationTab'
import Section from '../components/section';

export default class Homepage extends React.Component {

    render() {
        return (
            <Container fluid>
                {this.showInformationRow()}
                <Row>
                </Row>
            </Container>
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

    


}