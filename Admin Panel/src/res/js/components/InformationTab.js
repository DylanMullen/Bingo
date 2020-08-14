import React from 'react';
import Card from 'react-bootstrap/Card';

class InformationTab extends React.Component {
    render() {
        return (
            <Card className="flex-row">
                <Card.Img src={this.props.image} className="information-tab-img"/>
                <Card.Body>
                    <Card.Title>{this.props.header}</Card.Title>
                    <Card.Text>{this.props.text}</Card.Text>
                </Card.Body>
            </Card>
        );
    }
}

export { InformationTab };