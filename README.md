# Introduction to Liferay development

This source is a implementation of the [training materials](https://dev.liferay.com/en/develop/tutorials) to Liferay development.

All steps to create this app can be found at [Developing a Web Application](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/developing-a-web-application) section.

## Update [Leveraging Search](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/leveraging-search) to Liferay 7.1

More information about how Liferay search api works can be found at:


| Section | 7.0   | 7.1   |
| :------ | :---: | :---: |
| Search | [Link](https://dev.liferay.com/en/discover/portal/-/knowledge_base/7-0/search) | Coming soon
| Introduction to liferay search | [Link](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/introduction-to-liferay-search) | Coming soon
| Leveraging Search | [Link](https://dev.liferay.com/en/develop/tutorials/-/knowledge_base/7-0/leveraging-search) | Coming soon


### Steps

1. Update dependencies.
    Add the api and spi search packages as dependencies
    
2. Refactor the `BaseIndexer` constructor.

    1. Implement `BaseSearcher`.
        * Sets the default selected field names;
        * Makes the search results permissions-aware at search time, as well as in the index;
        * Sets filter search to true, enabling a document-by-document check of the search results’ VIEW permissions.

    2. Implement `SearchRegistrar`.
        * Sets the default selected field names;
        * Sets the default selected localized field names;
        * Allow search on all languages.
        
3. Implement `ModelPreFilterContributor`.

    The same feature of `BaseIndexer#postProcessContextBooleanFilter`.

4. Implement `KeywordQueryContributor`.

    The same feature of `BaseIndexer#postProcessSearchQuery`.
    * Add the localized values of any full text fields that might contribute to search relevance.

5. Implement `ModelDocumentContributor`.

    The same feature of `BaseIndexer#doGetDocument`.
    * Select the entity’s fields to build a search document that’s indexed by the search engine.

5. Implement `ModelSummaryContributor`.

    The same feature of `BaseIndexer#createSummary`.
    * Show a condensed, text-based version of the entity that can be displayed generically.

7. Implement `ModelIndexerWriterContributor`.

    The same feature of `BaseIndexer#doReindex` with an addition of a batch re-indexer.
    * Implements the classes which gets called when an entity is updated or a user explicitly triggers a reindex.
    * Reindex the Entry records when the Guestbook is guestbook has its name changed.

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










 