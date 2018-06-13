package br.com.objective.training.search;

import br.com.objective.training.model.Guestbook;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;
import org.osgi.service.component.annotations.Component;

@Component(
        immediate = true,
        property = "model.class.name=br.com.objective.training.model.Guestbook",
        service = BaseSearcher.class
)
public class GuestbookSearcher extends BaseSearcher {

    public static final String CLASS_NAME = Guestbook.class.getName();

    public GuestbookSearcher() {
        setDefaultSelectedFieldNames(
                Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.CONTENT,
                Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
                Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID
        );
        setPermissionAware(true);
        setFilterSearch(true);
    }

    @Override
    public String getClassName() {
        return CLASS_NAME;
    }

}