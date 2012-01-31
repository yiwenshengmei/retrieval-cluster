<?xml version="1.0" encoding="UTF-8"?>
<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema#">
	<${parent_name_en} rdf:ID="${uri}#${uri_name}">
		<label>${label}</label>
		<name>${name}</name>
		<desc>${desc}</desc>
		<images>
			<#list images as image_item>
			<item>${image_item}</item>
			</#list>
		</images>
		<userfields>
			<#list user_fields as user_item>
			<field key="${user_item.key}">${user_item.value}</field>
			</#list>
		</userfields>
	</${parent_name_en}>
</rdf:RDF>