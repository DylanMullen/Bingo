import React from 'react';

class Container extends React.Component
{
    render()
    {
        return (
            <section id={this.props.id}>
                <div className="section-header">
                    <h1>{this.props.header}</h1>
                </div>
                <div className="section-content">
                    {this.props.children}
                </div>
                <div className="section-footer" />
            </section>
        );
    }
}

export default Container;