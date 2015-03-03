[Documentation table of contents](../../toc.md) / [DataStore Reference](../../datastore.md) / add-monitoringdata

# Add Monitoring Data

When adding a monitoring datum to the system (using the REST API [POST-monitoring-data](../../doc/rest/monitoring-data/POST-monitoring-data), here are the pieces of information added in the datastore.

As a reference, this is the RDF/JSON code used for the request:

```json
{ 
  "b499a3d3-248a-4bbe-859c-e345d47ed479" : { 
    "http://www.modaclouds.eu/rdfs/1.0/monitoringdata#metric" : [ { 
      "type" : "literal" ,
      "value" : "FrontendCPUUtilization"
    }
     ] ,
    "http://www.modaclouds.eu/rdfs/1.0/monitoringdata#timestamp" : [ { 
      "type" : "literal" ,
      "value" : "1425399022110" ,
      "datatype" : "http://www.w3.org/2001/XMLSchema#integer"
    }
     ] ,
    "http://www.modaclouds.eu/rdfs/1.0/monitoringdata#value" : [ { 
      "type" : "literal" ,
      "value" : "0.11416644992590952e0" ,
      "datatype" : "http://www.w3.org/2001/XMLSchema#double"
    }
     ] ,
    "http://www.modaclouds.eu/rdfs/1.0/monitoringdata#resourceId" : [ { 
      "type" : "literal" ,
      "value" : "frontend1" ,
      "datatype" : "http://www.w3.org/2001/XMLSchema#string"
    }
     ]
  }
}
```

| g | s | p | o |
|--------------------------------|-----------------------------------------|--------------------------------|------------------------------------|
| <http://www.modaclouds.eu/historydb/monitoring-data/1425398400000>          | <mo:b499a3d3-248a-4bbe-859c-e345d47ed479>                                         | <http://www.modaclouds.eu/rdfs/1.0/monitoringdata#metric>       | "FrontendCPUUtilization"                                                          |
| <http://www.modaclouds.eu/historydb/monitoring-data/1425398400000>          | <mo:b499a3d3-248a-4bbe-859c-e345d47ed479>                                         | <http://www.modaclouds.eu/rdfs/1.0/monitoringdata#timestamp>    | 1425399022110                                                                     |
| <http://www.modaclouds.eu/historydb/monitoring-data/1425398400000>          | <mo:b499a3d3-248a-4bbe-859c-e345d47ed479>                                         | <http://www.modaclouds.eu/rdfs/1.0/monitoringdata#resourceId>   | "frontend1"^^<http://www.w3.org/2001/XMLSchema#string>                            |
| <http://www.modaclouds.eu/historydb/monitoring-data/1425398400000>          | <mo:b499a3d3-248a-4bbe-859c-e345d47ed479>                                         | <http://www.modaclouds.eu/rdfs/1.0/monitoringdata#value>        | 0.11416644992590952e0                                                             |
