import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<App />, div);
  ReactDOM.unmountComponentAtNode(div);
});

class App extends Component {
	render() {
	return (
		<div className="App">
			<div className="App-Header">
				<h2>Hello World</h2>
			</div>
			<p>Let's get started!</p>
		</div>
		);
	}
}

export default App;