import { Component } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import VacciSpotModel from '../model/VacciSpotModel';

class Map extends Component {
    constructor(props) {
        super(props);
        this.state = {
            type: props.type,
            markers: [],
            loading: true,
        }
    }

    componentDidMount() {
        this.getMarkers();
    }

    getMarkers() {
        VacciSpotModel.getByType(this.state.type).then((res) => {
            this.setState({markers: res.data, loading: false});
        }).catch(error => {
            console.log(error);
        })
    }

    render() {
        return (
            <div className="map">
                <MapContainer center={[1.36, 103.82]} zoom={12} scrollWheelZoom={false} style={{ "height": "600px", "width": "100%" }}>
                    <TileLayer
                        attribution="Map data &copy; <a href=&quot;https://www.openstreetmap.org/&quot;>OpenStreetMap</a> contributors, <a href=&quot;https://creativecommons.org/licenses/by-sa/2.0/&quot;>CC-BY-SA</a>, Imagery &copy; <a href=&quot;https://www.mapbox.com/&quot;>Mapbox</a>"
                        url="https://api.mapbox.com/styles/v1/chenyun964/cktlgyjzg0fw217pkjio01i55/tiles/256/{z}/{x}/{y}@2x?access_token=pk.eyJ1IjoiY2hlbnl1bjk2NCIsImEiOiJja3RsaDF0ZHcwY25vMnVuMnkyYXFyMDJyIn0.kFaj_c8O1tuzAtGejkXbUw"
                    />
                    {this.state.markers.length > 0 && this.state.markers.map((marker) => (
                        <Marker position={[
                                marker.latitude, 
                                marker.longitude
                            ]}
                        >
                            <Popup>
                                <b>Name:</b> {marker.name} <br />
                                <b>Building Type:</b> {marker.type} <br />
                                <b>Address:</b> {marker.address} <br />
                                <b>Vaccine Type:</b> {marker.vacciType}
                            </Popup>
                        </Marker>
                    ))}
                </MapContainer>
            </div>
        );
    }

}

export default Map;

