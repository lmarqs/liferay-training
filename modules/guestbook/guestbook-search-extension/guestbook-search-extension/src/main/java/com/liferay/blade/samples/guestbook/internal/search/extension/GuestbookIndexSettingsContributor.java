package com.liferay.blade.samples.guestbook.internal.search.extension;

import com.liferay.portal.search.elasticsearch6.settings.IndexSettingsContributor;
import com.liferay.portal.search.elasticsearch6.settings.IndexSettingsHelper;
import com.liferay.portal.search.elasticsearch6.settings.TypeMappingsHelper;
import org.osgi.service.component.annotations.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        service = IndexSettingsContributor.class
)
public class GuestbookIndexSettingsContributor implements IndexSettingsContributor {
    @Override
    public void contribute(String indexName, TypeMappingsHelper typeMappingsHelper) {
        try {
            String mappings = this.readMappings("guestbook-type-mappings.json");
            typeMappingsHelper.addTypeMappings(indexName, mappings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readMappings(String filename) throws UnsupportedEncodingException {

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            ClassLoader classLoader = GuestbookIndexSettingsContributor.class.getClassLoader();

            inputStream = classLoader.getResourceAsStream("META-INF/resources/mappings/" + filename);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8.name());

            bufferedReader = new BufferedReader(inputStreamReader);

            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (UnsupportedEncodingException e) {
            throw e;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ignored) {
            }

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void populate(IndexSettingsHelper indexSettingsHelper) {
    }

    @Override
    public int compareTo(IndexSettingsContributor indexSettingsContributor) {
        return -1;
    }


}
