package com.notimetolearn.java.domain.github;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GithubJobPosition {

    private String id;
    private String type;
    private String url;
    private String created_at;
    private String company;
    private String company_url;
    private String location;
    private String title;
    private String description;
    private String how_to_apply;
    private String company_logo;
}
