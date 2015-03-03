[Documentation table of contents](../../toc.md) / [DataStore Reference](../../datastore.md) / delete-model

# Delete Model

When deleting a model from the system (using the REST API [DELETE-model](../../doc/rest/model/DELETE-model), here are the pieces of information added in the datastore.

Here it is assumed that the `:id` parameter in the API had value `id1425399019932`.

| g | s | p | o |
|--------------------------------|-----------------------------------------|--------------------------------|------------------------------------|
| <http://www.modaclouds.eu/historydb/meta/model/cancellations/1425337200000> | <http://www.modaclouds.eu/historydb/model/cancellations/1425399019955>            | <mo:timestamp>                                                  | 1425399019955                                                                     |
| <http://www.modaclouds.eu/historydb/model/cancellations/1425399019955>      | <mo:id1425399019932>                                                              | <http://www.modaclouds.eu/rdfs/1.0/deletedmodel#id>             | "id1425399019932"^^<http://www.w3.org/2001/XMLSchema#string>                      |
| <http://www.modaclouds.eu/historydb/model/cancellations/1425399019955>      | <mo:id1425399019932>                                                              | <http://www.modaclouds.eu/rdfs/1.0/deletedmodel#timestamp>      | 1425399019955                                                                     | 