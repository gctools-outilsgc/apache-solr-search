
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

def siteAddr = this.args[0]
def solrAddr = this.args[1]
def api_key = this.args[2]

println "Site Address: " + siteAddr + " | Solr Address: " + solrAddr

def offset = 0

while (true) {

	URL apiURL = new URL("$siteAddr/services/api/rest/json/?method=get.user_list&offset=$offset&api_key=$api_key")

	def slurper = new JsonSlurper()
	def api_response = slurper.parseText(apiURL.text)
	def results = api_response.result

	if (results == null) break

	for (result in results) {

		if (result.guid == null) continue

		def name = result.name
		def text_en = "$name $result.email $result.username" 
		def text_fr = "$name $result.email $result.username"

		def json_string = new JsonBuilder([
			"guid": result.guid, 
			"name": "$name", 
			"text_en": "$text_en",
			"text_fr": "$text_fr",
			"type": "$result.type", 
			"username": "$result.username", 
			"email": "$result.email",
			"date_created": "$result.date_created",
			"date_modified": "$result.date_modified",
			"platform": "$result.platform"
		])

		def process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" --data-binary '" + json_string.toString() + "' $solrAddr/solr/elgg-core/update/json/docs?commit=true" ].execute().text

		println "$result.subtype | $offset | $result.guid | " + process
	}
	offset = offset + 10
}