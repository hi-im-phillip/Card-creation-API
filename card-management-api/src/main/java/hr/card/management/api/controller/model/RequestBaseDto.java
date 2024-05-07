package hr.card.management.api.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBaseDto {

    private static final Integer DEFAULT_PAGE = 0;

    private static final Integer DEFAULT_PER_PAGE = 25;

    private String searchTerm;

    private String sortBy;

    private Boolean includePublic;

    private Integer page;

    private Integer perPage;

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        return page;
    }

    public Integer getPerPage() {
        if (perPage == null) {
            return DEFAULT_PER_PAGE;
        }
        return perPage;
    }
}
