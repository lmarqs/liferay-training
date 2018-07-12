# Introduction to Search Extension Points

This sample shows how to create a custom search on Liferay Portal.

## Steps

All changes are made on the guestbook-search-extension, 
[search](https://github.com/lmarqs/liferay-training/tree/master/modules/guestbook/guestbook-service/src/main/java/br/com/objective/training/search) package.
The steps are merged from this PR: 
[#3 - Search Extension Points 7.1](https://github.com/lmarqs/liferay-training/pull/3)

1. Add dependencies.
    
    Add the api, spi and elasticsearch6 packages as dependencies on build.gradle.
    ```.gradle
    compileOnly group: "com.liferay", name: "com.liferay.portal.search.api", version: "2.0.0"
    compileOnly group: "com.liferay", name: "com. liferay.portal.search.spi", version: "2.0.0"
    compileOnly group: "com.liferay", name: "com.liferay.portal.search.elasticsearch6.api", version: "2.0.0"
    ```
    
2. Define the Elasticsearch indexing.

    According to [elasticsearch guide](https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping.html):
    > Mapping is the process of defining how a document, and the fields it contains, are stored and indexed.    

    Liferay's search engine provides an API to define custom mappings. To use it:
     
    1. Define the new mapping.
    In this sample, the mapping is defined on `META-INF/mappings/resources/guestbook-type-mappings.json` file.
    Notice that the default document on Liferay is called `LiferayDocumentType`.
    The mapping's features can be found at [elastic search's docs](https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping.html).  
            
    2. Put the mapping into Elasticsearch.
    The `IndexSettingsContributor` components are invoked during the reindexing and receive a `TypeMappingsHelper` as a hook to add new mappings.   
    In this sample, this feature is implemented by `GuestbookIndexSettingsContributor` class that reads the `.json` add its content to Liferay's default index.
        
3. Build a search document.

    Once the mapping is defined, it is needed to select the entity’s fields to build the documents indexed by the search engine.
    On Liferay 6.2 ~ 7.0 those documents are build on `IndexerPostProcessor#postProcessDocument` components, and on 7.1 it was introduced the `ModelDocumentContributor` to do the same.

4. Add the fields to contribute to search relevance.

  
## Use cases

### Happy day

> Coming soon 
