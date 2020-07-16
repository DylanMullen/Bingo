import React from 'react';
import { Card } from 'react-bootstrap';

class Section extends React.Component
{
    render()
    {
        return (
            <Card>
                <Card.Body>
                    <Card.Title>{this.props.title}</Card.Title>
                    {this.props.children}
                    <Card.Text>{this.props.text}</Card.Text>
                </Card.Body>
            </Card>
        );
    }
}

export default Section;