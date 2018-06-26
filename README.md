# Introduction to Liferay development

This source is a implementation of the [training materials](https://dev.liferay.com/en/develop/tutorials) to Liferay development.

All steps to create this app can be found at [Developing a Web Application](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/developing-a-web-application) section.

## Update [Search](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/leveraging-search) to Liferay 7.1

More information about how Liferay search api works can be found at:


| Section | 7.0   | 7.1   |
| :------ | :---: | :---: |
| Search | [Link](https://dev.liferay.com/en/discover/portal/-/knowledge_base/7-0/search) | Coming soon
| Introduction to liferay search | [Link](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/introduction-to-liferay-search) | Coming soon
| Leveraging Search | [Link](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/leveraging-search) | Coming soon


### Steps
All changes are made on the guestbook-service module, 
[search](https://github.com/lmarqs/liferay-training/tree/master/modules/guestbook/guestbook-service/src/main/java/br/com/objective/training/search) package.
The steps are merged from this PR: 
[#2 - Update Searching to Liferay 7.1](https://github.com/lmarqs/liferay-training/pull/2)

1. Update dependencies.
    
    Add the api and spi search packages as dependencies on build.gradle.
    ```.gradle
    compileOnly group: "com.liferay", name: "com.liferay.portal.search.api", version: "2.0.0"
    compileOnly group: "com.liferay", name: "com. liferay.portal.search.spi", version: "2.0.0"
    ```
    
    See [build.gradle](https://github.com/lmarqs/liferay-training/pull/2/commits/57113b53f4500bc1a68b457616900876c4cf1b61).
    
2. Refactor the `BaseIndexer` constructor.

    1. Implement the interface `BaseSearcher`.

        * Sets the default selected field names;
        * Makes the search results permissions-aware at search time, as well as in the index;
        * Sets filter search to true, enabling a document-by-document check of the search results’ VIEW permissions.

        See [`GuestbookSearcher`](https://github.com/lmarqs/liferay-training/pull/2/commits/473cf5bd64fe11cdc2e9b1796f4c3bb8135f5495).

    2. Create the SearchRegistrar components to register the new search service
        
        Those services do the following:
        * Sets the default selected field names;
        * Sets the default selected localized field names;
        * Sets the contributors (`modelIndexWriterContributor` and `modelSummaryContributor`); 
        * Allow search on all languages.
        
        See [`EntrySearchRegistrar` and `GuestbookSearchRegistrar`](https://github.com/lmarqs/liferay-training/pull/2/commits/ede75e7956dc7765b03a082a56e881c8f888ed1b). 
        
3. Implement `ModelPreFilterContributor`.

    The same feature of `BaseIndexer#postProcessContextBooleanFilter`. 
    This method is invoked while the main search query is being constructed. 
    * Ensures that entities with the status STATUS_IN_TRASH aren’t added to the query.
     
    See [`EntryModelPreFilterContributor` and `GuestbookModelPreFilterContributor`](https://github.com/lmarqs/liferay-training/pull/2/commits/a4e712cced85767c127b8895b0223a5125b01283).

4. Implement `KeywordQueryContributor`.

    The same feature of `BaseIndexer#postProcessSearchQuery`.
    * Add the localized values of any full text fields that might contribute to search relevance.
    
    See [`EntryKeywordQueryContributor` and `GuestbookKeywordQueryContributor`](https://github.com/lmarqs/liferay-training/pull/2/commits/08a47cd22eca0f6ac442708cb8c51dbc48e70b21).

5. Implement `ModelDocumentContributor`.

    The same feature of `BaseIndexer#doGetDocument`.
    * Select the entity’s fields to build a search document that’s indexed by the search engine.

    See [`EntryModelDocumentContributor` and `GuestbookModelDocumentContributor`](https://github.com/lmarqs/liferay-training/pull/2/commits/d7b796a0dd69c5ef4ea776b0f667343e979d11a9).

5. Implement `ModelSummaryContributor`.

    The same feature of `BaseIndexer#createSummary`.
    * Show a condensed, text-based version of the entity that can be displayed generically.

    See [`EntryModelSummaryContributor` and `GuestbookModelSummaryContributor`](https://github.com/lmarqs/liferay-training/pull/2/commits/08b4fb835a8b9a00cf075884826c5510839f9ade).
    
7. Implement `ModelIndexerWriterContributor`.

    The same feature of `BaseIndexer#doReindex` with an addition of a batch re-indexer.
    * Implements the classes which gets called when an entity is updated or a user explicitly triggers a reindex.
    * Reindex the Entry records when the Guestbook is guestbook has its name changed.

    See [`EntryModelSummaryContributor` and `GuestbookModelSummaryContributor`](https://github.com/lmarqs/liferay-training/pull/2/commits/08b4fb835a8b9a00cf075884826c5510839f9ade).
    
8. Deprecate the implementations of BaseIndexer.


> *Notice*
>
> 1. The implementation of `hasPermission` and `doDelete` is no longer needed.
>
> 2. On the original doc ([setting-the-guestbook-status](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/setting-the-guestbook-status) and [setting-the-entry-workflow-status](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/setting-the-entry-workflow-status))
> does not mention the use of `@Indexable` annotation above the `updateStatus` methods.
> It turns out that without this annotation, records are not displayed in search results after being approved in the workflow.
> Those annotation had been added on [this commit](https://github.com/lmarqs/liferay-training/commit/eacbde58c6bb8fe76cad66def2dde2cd36767720)
> before the updating to 7.1.


## Use cases

### Happy day
A simple example of how the search works.

![Happy day - Sequence diagram](https://g.gravizo.com/source/svg?https://raw.githubusercontent.com/lmarqs/liferay-training/master/docs/specifications/happy-day/seq.plantuml?v=1.0.1) 

### Using permissions

*Coming soon*

### Using workflow

Workflow is a review process that ensures a submitted entity isn’t published before it’s reviewed.
Enabling workflow in the guestbook application also prevents unapproved entries from appearing in search results.


![Using workflow - Sequence diagram](https://g.gravizo.com/source/svg?https://raw.githubusercontent.com/lmarqs/liferay-training/master/docs/specifications/using-workflow/seq.plantuml?v=1.0.1)