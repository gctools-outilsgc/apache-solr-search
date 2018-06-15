import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

def siteAddr = this.args[0]
def solrAddr = this.args[1]
def api_key = this.args[2]

println "Site Address: " + siteAddr + " | Solr Address: " + solrAddr

def offset = 0
	
while (true) {

	URL apiURL = new URL("$siteAddr/services/api/rest/json/?method=get.group_list&offset=$offset&api_key=$api_key")

	def slurper = new JsonSlurper()
	def api_response = slurper.parseText(apiURL.text)
	def results = api_response.result

	if (results == null) break

	for (result in results) {

		if (result.guid == null) continue

		def name = result.name
		def description = result.description

		def name_en = (name.en).replaceAll("\"", " ")
		def name_fr = (name.fr).replaceAll("\"", " ")
		def description_en = (description.en).replaceAll("\"", " ")
		def description_fr = (description.fr).replaceAll("\"", " ")

		def text_en = "$name_en $description_en" 
		def text_fr = "$name_fr $description_fr"

		def json_string = new JsonBuilder([
			"guid": result.guid, 
			"name_en": "$name_en", 
			"name_fr": "$name_fr", 
			"description_en": "$description_en",
			"description_fr": "$description_fr",
			"text_en": "$text_en",
			"text_fr": "$text_fr",
			"type": "$result.type", 
			"access_id": result.access_id,
			"date_created": "$result.date_created",
			"date_modified": "$result.date_modified",
			"url": "$result.url",
			"platform": "$result.platform"
		])

		def process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" --data-binary '" + json_string.toString() + "' $solrAddr/solr/elgg-core/update/json/docs?commit=true" ].execute().text

		println "$result.subtype | $offset | $result.guid | " + process
		
	}
	offset = offset + 10
}