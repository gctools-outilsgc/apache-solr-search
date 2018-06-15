import groovy.json.JsonSlurper
import groovy.json.JsonBuilder
import groovy.xml.*

def siteAddr = this.args[0]
def solrAddr = this.args[1]
def api_key = this.args[2]

println "Site Address: " + siteAddr + " | Solr Address: " + solrAddr

// @TODO documentations
// http://192.168.1.18/gcconnex/services/api/rest/json/?method=delete.updated_index_list&guids[]=test&guids[]=2&test=hello

URL apiURL = new URL("$siteAddr/services/api/rest/json/?method=get.list_of_deleted_records&api_key=$api_key")

def slurper = new JsonSlurper()
def api_response = slurper.parseText(apiURL.text)

def list_of_documents_to_delete = []
def guids = ""
def results = api_response.result
def is_first = true

for (result in results) {

	def xml_string = "<delete><id>$result.guid</id></delete>"
	def process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/xml\" --data-binary '" + xml_string + "' $solrAddr/solr/elgg-core/update?commit=true" ].execute().text

	println xml_string.toString()
	println process

	def responses = new XmlSlurper().parseText(process)
	def response_text = responses.lst.find { it.'@name' == 'error'}.str.text()
	
	if (response_text == '') {
		list_of_documents_to_delete.push(result.guid)
		guids = (is_first) ? guids + "guids[]=$result.guid" : guids + "&guids[]=$result.guid"
		is_first = false
	}
}

def process = [ 'bash', '-c', "curl -X POST -d '" + guids + "' $siteAddr/services/api/rest/json/?method=delete.updated_index_list" ].execute().text

println "$result.guid | " + process

 