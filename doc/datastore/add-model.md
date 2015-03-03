[Documentation table of contents](../../toc.md) / [DataStore Reference](../../datastore.md) / add-model

# Add Model

When adding a model to the system (using the REST API [PUT-model](../../doc/rest/model/PUT-model), here are the pieces of information added in the datastore.

As a reference, this is the JSON code used for the request:

```json
{
  "cloudProviders": [
    {
      "id": "amazon", 
      "type": "IaaS"
    }
  ], 
  "internalComponents": [
    {
      "id": "mic1", 
      "providedMethods": [
        "mic1-register", 
        "mic1-answerQuestions", 
        "mic1-saveAnswers"
      ], 
      "requiredComponents": [
        "frontend1"
      ], 
      "type": "Mic"
    },
    {
      "id": "mic2", 
      "providedMethods": [
        "mic2-register", 
        "mic2-answerQuestions", 
        "mic2-saveAnswers"
      ], 
      "requiredComponents": [
        "frontend2"
      ], 
      "type": "Mic"
    }
  ], 
  "methods": [
    {
      "id": "mic1-answerQuestions", 
      "type": "answerQuestions"
    }, 
    {
      "id": "mic1-saveAnswers", 
      "type": "saveAnswers"
    }, 
    {
      "id": "mic1-register", 
      "type": "register"
    },
    {
      "id": "mic2-answerQuestions", 
      "type": "answerQuestions"
    }, 
    {
      "id": "mic2-saveAnswers", 
      "type": "saveAnswers"
    }, 
    {
      "id": "mic2-register", 
      "type": "register"
    }
  ], 
  "vMs": [
    {
      "cloudProvider": "amazon", 
      "id": "frontend1", 
      "numberOfCPUs": 2, 
      "type": "Frontend"
    },
    {
      "cloudProvider": "amazon", 
      "id": "frontend2", 
      "numberOfCPUs": 2, 
      "type": "Frontend"
    }
  ]
}
```

| g | s | p | o |
|--------------------------------|-----------------------------------------|--------------------------------|------------------------------------|
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1>                                 | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#InternalComponent>                    |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.InternalComponent"           |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "mic1"                                                                            |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "Mic"                                                                             |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#providedMethods>    | <http://www.modaclouds.eu/rdfs/1.0/entities#f6d35882-bf97-41c5-b208-0c28064acf3a> |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#requiredComponents> | <http://www.modaclouds.eu/rdfs/1.0/entities#26c166ce-ecbb-499c-b990-75bed0e9c446> |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-answerQuestions>                 | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#Method>                               |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-answerQuestions>                 | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.Method"                      |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-answerQuestions>                 | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "mic2-answerQuestions"                                                            |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-answerQuestions>                 | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "answerQuestions"                                                                 |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2>                                 | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#InternalComponent>                    |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.InternalComponent"           |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "mic2"                                                                            |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "Mic"                                                                             |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#providedMethods>    | <http://www.modaclouds.eu/rdfs/1.0/entities#7c38058b-084a-4b22-bfb1-a74337ff48a0> |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2>                                 | <http://www.modaclouds.eu/rdfs/1.0/entities#requiredComponents> | <http://www.modaclouds.eu/rdfs/1.0/entities#9f77e454-2ec0-477a-b9cc-f482fb5d03dc> |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-register>                        | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#Method>                               |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-register>                        | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.Method"                      |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-register>                        | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "mic1-register"                                                                   |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-register>                        | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "register"                                                                        |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend1>                            | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#VM>                                   |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend1>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.VM"                          |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend1>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "frontend1"                                                                       |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend1>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "Frontend"                                                                        |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend1>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#numberOfCPUs>       | "0"                                                                               |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend1>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#cloudProvider>      | "amazon"                                                                          |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#amazon>                               | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#CloudProvider>                        |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#amazon>                               | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.CloudProvider"               |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#amazon>                               | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "amazon"                                                                          |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#amazon>                               | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "IaaS"                                                                            |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend2>                            | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#VM>                                   |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend2>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.VM"                          |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend2>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "frontend2"                                                                       |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend2>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "Frontend"                                                                        |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend2>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#numberOfCPUs>       | "0"                                                                               |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#frontend2>                            | <http://www.modaclouds.eu/rdfs/1.0/entities#cloudProvider>      | "amazon"                                                                          |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-saveAnswers>                     | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#Method>                               |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-saveAnswers>                     | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.Method"                      |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-saveAnswers>                     | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "mic2-saveAnswers"                                                                |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-saveAnswers>                     | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "saveAnswers"                                                                     |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-answerQuestions>                 | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#Method>                               |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-answerQuestions>                 | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.Method"                      |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-answerQuestions>                 | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "mic1-answerQuestions"                                                            |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-answerQuestions>                 | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "answerQuestions"                                                                 |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-saveAnswers>                     | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#Method>                               |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-saveAnswers>                     | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.Method"                      |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-saveAnswers>                     | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "mic1-saveAnswers"                                                                |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic1-saveAnswers>                     | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "saveAnswers"                                                                     |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-register>                        | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.modaclouds.eu/rdfs/1.0/entities#Method>                               |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-register>                        | <http://www.modaclouds.eu/rdfs/1.0/entities#class>              | "it.polimi.modaclouds.qos_models.monitoring_ontology.Method"                      |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-register>                        | <http://www.modaclouds.eu/rdfs/1.0/entities#id>                 | "mic2-register"                                                                   |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#mic2-register>                        | <http://www.modaclouds.eu/rdfs/1.0/entities#type>               | "register"                                                                        |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#7c38058b-084a-4b22-bfb1-a74337ff48a0> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag>                                  |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#7c38058b-084a-4b22-bfb1-a74337ff48a0> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#_0>                 | "mic2-register"                                                                   |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#7c38058b-084a-4b22-bfb1-a74337ff48a0> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#_1>                 | "mic2-answerQuestions"                                                            |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#7c38058b-084a-4b22-bfb1-a74337ff48a0> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#_2>                 | "mic2-saveAnswers"                                                                |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#9f77e454-2ec0-477a-b9cc-f482fb5d03dc> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag>                                  |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#9f77e454-2ec0-477a-b9cc-f482fb5d03dc> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#_0>                 | "frontend2"                                                                       |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#f6d35882-bf97-41c5-b208-0c28064acf3a> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag>                                  |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#f6d35882-bf97-41c5-b208-0c28064acf3a> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#_0>                 | "mic1-register"                                                                   |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#f6d35882-bf97-41c5-b208-0c28064acf3a> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#_1>                 | "mic1-answerQuestions"                                                            |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#f6d35882-bf97-41c5-b208-0c28064acf3a> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#_2>                 | "mic1-saveAnswers"                                                                |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#26c166ce-ecbb-499c-b990-75bed0e9c446> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>               | <http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag>                                  |
| <http://www.modaclouds.eu/historydb/model/1425400773874>      | <http://www.modaclouds.eu/rdfs/1.0/entities#26c166ce-ecbb-499c-b990-75bed0e9c446> | <http://www.w3.org/1999/02/22-rdf-syntax-ns#_0>                 | "frontend1"                                                                       |
| <http://www.modaclouds.eu/historydb/meta/model/1425337200000> | <http://www.modaclouds.eu/historydb/model/1425400773874>                          | <mo:timestamp>                                                  | 1425400773874                                                                     |