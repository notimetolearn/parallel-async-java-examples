package com.notimetolearn.java.api;

import com.notimetolearn.java.domain.github.GithubJobPosition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class GithubApiTest {

    ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 10 * 1024)).build();

    WebClient client = WebClient.builder().baseUrl("https://jobs.github.com")
            .exchangeStrategies(exchangeStrategies).build();

    GithubApi api = new GithubApi(client);

    @Test
    void getJobsFromGithub() {

        //given
        String jobDescription = "JavaScript";
        int pageNum = 1;

        //when
        List<GithubJobPosition> positions = api.getJobsFromGithub(jobDescription, pageNum);

        //then
        assertTrue(positions.size() > 0);
    }

    @Test
    void getMultipleJobsFromGithub() {

        //given
        String jobDescription = "JavaScript";
        List<Integer> pageNumbers = Arrays.asList(1,2,3);

        //when
        List<GithubJobPosition> positions = api.getMultipleJobsFromGithub(jobDescription, pageNumbers);

        //then
        assertTrue(positions.size() > 0);
    }

    @Test
    void getMultipleJobsFromGithubAsync() {
        //given
        String jobDescription = "JavaScript";
        List<Integer> pageNumbers = Arrays.asList(1,2,3);

        //when
        List<GithubJobPosition> positions = api.getMultipleJobsFromGithubAsync(jobDescription, pageNumbers);

        //then
        assertTrue(positions.size() > 0);
    }
}