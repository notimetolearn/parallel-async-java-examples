package com.notimetolearn.java.api;

import com.notimetolearn.java.domain.github.GithubJobPosition;
import com.notimetolearn.java.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GithubApi {

    private final WebClient webClient;

    public List<GithubJobPosition> getJobsFromGithub(String description, int pageNumber ) {

        String uri = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("description", description)
                .queryParam("page", pageNumber)
                .buildAndExpand().toUriString();

        List<GithubJobPosition> positions = webClient.get().uri(uri)
                .retrieve().bodyToFlux(GithubJobPosition.class)
                .collectList()
                .block();

        return positions;
    }

    public List<GithubJobPosition> getMultipleJobsFromGithub(String description, List<Integer> pageNumbers ) {
        CommonUtil.startTimer();
        List<GithubJobPosition> positions = pageNumbers.stream().map(num -> getJobsFromGithub(description, num))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        CommonUtil.timeTaken();
        return positions;
    }

    public List<GithubJobPosition> getMultipleJobsFromGithubAsync(String description, List<Integer> pageNumbers ) {
        CommonUtil.startTimer();

        List<CompletableFuture<List<GithubJobPosition>>> futureListPositions = pageNumbers
                .stream()
                .map(num -> CompletableFuture.supplyAsync(() -> getJobsFromGithub(description, num)))
                .collect(Collectors.toList());

        List<GithubJobPosition> positions = futureListPositions.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        CommonUtil.timeTaken();

        return positions;
    }
}
