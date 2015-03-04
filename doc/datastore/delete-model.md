[Documentation table of contents](../../toc.md) / [DataStore Reference](../../datastore.md) / delete-model

# Delete Model

When deleting a model from the system (using the REST API [DELETE-model](../../doc/rest/model/DELETE-model), a number of triples are inserted in the datastore.

Let's assume that:

* the sub-component that has to save the model in the datastore received it in a certain time (e.g. with a timestamp `1425399022110`, corresponding to the date *Tue, 03 Mar 2015 17:10:22 GMT+1:00*),
* the `:id` parameter in the API had value `id1425399019932`.

The assumption that no other model is considered at the exact same time is done. Then, a graph with the name containing the timestamp above is created (e.g. `http://www.modaclouds.eu/historydb/model/cancellations/1425400773874`).

The graph will contain two triples, both with the value of the `:id` parameter as the subject. Both the predicates will have the base prefix `http://www.modaclouds.eu/rdfs/1.0/deletedmodel#`, and there are:

* `id`, the id of the model that we want to delete,
* `timestamp`, the timestamp in which the message was received.

Another graph with the name associated to the hour in which the timestamp is contained (e.g. `http://www.modaclouds.eu/historydb/meta/model/cancellations/1425398400000` for the example above, where `1425398400000` corresponds to the date *Tue, 03 Mar 2015 17:00:00 GMT+1:00*) is created or used if it already exists.
Only one triple is added, and this has as the subject the name of the graph considered before (e.g. `http://www.modaclouds.eu/historydb/model/cancellations/1425400773874`), the predicate `<mo:timestamp>` and the actual timestamp as the object of the triple (e.g. `1425400773874`).