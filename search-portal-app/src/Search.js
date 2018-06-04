import React, { Component } from "react";
import axios from "axios";



const URL = "http://192.168.1.18:8983/solr/elgg-core/select?q=*:*";

const results = [];

export default class Search extends Component {
//const results = this.state.


  constructor(props) {
    super(props);
    this.state = {
    	value: '', 
    	results: [],
    	numDocs: ''
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

    //this.state = this.state.bind(this);
  }


  displayResults() {
    
  }

  handleChange(event) {
    this.setState({value: event.target.value});
  }

  handleSubmit(event) {
    //alert('A name was submitted: ' + this.state.value);
    event.preventDefault();


    fetch(URL, {
      //mode: "no-cors",
      headers: {"Accept": "application/json"},
      method: "GET"
    })
    .then(response => {
      //console.log(response);

      //if (response.type === "opaque" || response.ok) {
        return response.json()
      //}
     
    }).then((response) => {

      //console.log(response);
      //this.setState({results: JSON.stringify(response)});  
    
      //console.log("assemble the response to a readable format " + JSON.stringify(response.response));
      var jsonResponse = response.response;
      var jsonNumFound = jsonResponse.numFound;
      var jsonStart = jsonResponse.start;
      var jsonDocs = jsonResponse.docs;

      this.setState({numDocs: jsonNumFound});
      console.log("number of documents " + jsonResponse.numFound);

      var arrDocs = [];
      var i = 0;
      //var parsed = JSON.parse(jsonDocs);
      for (var x in jsonDocs) {
      	//console.log("x: " + x + " jsonDocs: " + jsonDocs[x]);
      	//console.log("info: " + jsonDocs[x].title);
      	//console.log("info: " + jsonDocs[x].description);
      	//console.log("info: " + jsonDocs[x].access_id);

      	var arr1 = [];
      	var arr1 = [{
      		"id": x,
      		"title": jsonDocs[x].title,
      		"description": jsonDocs[x].description,
      		"access_id": jsonDocs[x].access_id
      	}];


      	arrDocs.push(arr1);
      	i++;
      	//arrDocs
      }

      //console.log("array: " + JSON.stringify(arrDocs));

      this.setState({results: arrDocs});


    });

  }



  render() {


    return (

      <div id="searchPage" class="parent">

      <form class="searchForm" onSubmit={this.handleSubmit}>
        <input class="searchInput" name="searchText" onChange={this.handleChange.bind(this)} type="text"/>
        <button class="searchButton" type="submit">SEARCH</button>
      </form>
      


      	<div class="resultsInformation">{ this.state.numDocs ? "About " + this.state.numDocs + " results (time in seconds to retrieve)" : "" }</div>
      	

      	<div>
      		{
      			this.state.results.map((name, index) => {
      				var subName = name;
      				var result_array = [];
      				subName.map((obj, idx) => {
      					result_array.push(
      				
      					<div key='{idx}' class="searchResult"> 
      						<h3 class="searchResultTitle">{obj.title}</h3>
      						<div class="searchResultCite"><cite class="searchResultURL">www.sample-results.org</cite></div>
      						<span class="searchResultDescription">{obj.description}</span>
      						<div class="searchResultAdditionalInfo">Platform | Access | Type</div>
      					</div>
      					
      					);

      				})
      					
      				return <div>{result_array}</div>
      			})
      		}
      	</div>
  

      </div>



    );


  }


  
}

