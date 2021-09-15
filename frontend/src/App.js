import logo from './logo.svg';
import './App.css';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <MapContainer center={[1.290270, 103.851959]} zoom={12} scrollWheelZoom={false} style={{"height": "600px", "width": "100%"}}>
          <TileLayer
          attribution="Map data &copy; <a href=&quot;https://www.openstreetmap.org/&quot;>OpenStreetMap</a> contributors, <a href=&quot;https://creativecommons.org/licenses/by-sa/2.0/&quot;>CC-BY-SA</a>, Imagery &copy; <a href=&quot;https://www.mapbox.com/&quot;>Mapbox</a>"
            url="https://api.mapbox.com/styles/v1/chenyun964/cktlgyjzg0fw217pkjio01i55/tiles/256/{z}/{x}/{y}@2x?access_token=pk.eyJ1IjoiY2hlbnl1bjk2NCIsImEiOiJja3RsaDF0ZHcwY25vMnVuMnkyYXFyMDJyIn0.kFaj_c8O1tuzAtGejkXbUw
            "
          />
          <Marker position={[1.30270, 103.851959]}>
            <Popup>
              A pretty CSS3 popup. <br /> Easily customizable.
            </Popup>
          </Marker>
        </MapContainer>
      </header>
    </div>
  );
}

export default App;
